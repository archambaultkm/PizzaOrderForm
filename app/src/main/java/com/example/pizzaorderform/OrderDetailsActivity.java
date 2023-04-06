package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings, enSize, nlSize, enCrust, nlCrust, enCheese, nlCheese, enToppings, nlToppings;

    private TextView tvOrderDetailsTitle, tvOrderID, tvPizzaDetails, tvCustomerDetails;

    private Button btnEdit, btnConfirm, btnFinish;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Pizza pizza;
    private Customer customer;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = (Order) extras.getSerializable("order");
        }

        pizza = order.getPizza();
        customer = order.getCustomer();

        initWidgets();
        addToLists();
        setListeners();

        enStrings = getResources().getStringArray(R.array.en_reviewactivity);
        nlStrings = getResources().getStringArray(R.array.nl_reviewactivity);

        //Special arrays for printing out user-friendly pizza info (which got stored as ints for db consistency)
        enSize = getResources().getStringArray(R.array.en_size);
        nlSize = getResources().getStringArray(R.array.nl_size);
        enCrust = getResources().getStringArray(R.array.en_crust);
        nlCrust = getResources().getStringArray(R.array.nl_crust);
        enCheese = getResources().getStringArray(R.array.en_cheese);
        nlCheese = getResources().getStringArray(R.array.nl_cheese);
        enToppings = getResources().getStringArray(R.array.en_toppings);
        nlToppings = getResources().getStringArray(R.array.nl_toppings);

        setLang(MainActivity.getLanguage());

        showOrderData();
    }

    private void showOrderData() {

        tvOrderID.setText(tvOrderID.getText() + " " + order.getID());
        tvPizzaDetails.setText(printPizzaInfo());
        tvCustomerDetails.setText(customer.toString());
    }

    private void initWidgets() {

        tvOrderDetailsTitle = findViewById(R.id.tvOrderDetailsTitle);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvPizzaDetails = findViewById(R.id.tvPizzaDetails);
        tvCustomerDetails = findViewById(R.id.tvCustomerDetails);
        btnEdit = findViewById(R.id.btnEdit);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnFinish = findViewById(R.id.btnFinish);

        //if they're working on a new order, they'll be prompted to confirm and place order. If they're
        //reviewing an existing order they'll be returned to the order review activity
        btnConfirm.setVisibility(OrderRecordActivity.orders.contains(order) ? View.GONE : View.VISIBLE);
        btnFinish.setVisibility(OrderRecordActivity.orders.contains(order) ? View.VISIBLE : View.GONE);
    }

    private void addToLists() {

        uiComponents.add(tvOrderDetailsTitle);
        uiComponents.add(tvOrderID);
        uiComponents.add(btnEdit);
        uiComponents.add(btnConfirm);
        uiComponents.add(btnFinish);
    }

    private void setListeners() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OrderDetailsActivity.this, PizzaBaseActivity.class);
                i.putExtra("order", order);

                startActivity(i);
            }
        });

        //this button is visible to the user if they're working on a new order
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the current date for the record
                Date date = new Date();
                order.setDate(date);

                //add this order to the list for review
                OrderRecordActivity.orders.add(order);
                //also add it to the database
                SQLiteAdapter sqLiteAdapter = SQLiteAdapter.instanceOfDatabase(OrderDetailsActivity.this);
                sqLiteAdapter.addOrderToDatabase(order);

                Intent i = new Intent(OrderDetailsActivity.this, ConfirmationActivity.class);

                //the time of ordering will be shown on the thank-you page
                i.putExtra("order_date", date);

                startActivity(i);
            }
        });

        //this button is visible if they're reviewing an existing order
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteAdapter sqLiteAdapter = SQLiteAdapter.instanceOfDatabase(OrderDetailsActivity.this);
                sqLiteAdapter.updateOrderInDatabase(order);

                startActivity(new Intent(OrderDetailsActivity.this, OrderRecordActivity.class));
            }
        });
    }

    private String printPizzaInfo() {

        StringBuilder sb = new StringBuilder();
        String delim = "";

        if (MainActivity.getLanguage()) {
            sb.append(getResources().getString(R.string.nlsize) + ": ");
            sb.append(nlSize[pizza.getSize()]);
            sb.append("\n");
            sb.append(nlCrust[pizza.getCrust()]);
            sb.append(" ").append(getResources().getString(R.string.nlcrust)).append(", ");
            sb.append(nlCheese[pizza.getCheese()]);
            sb.append(" ").append(getResources().getString(R.string.nlcheese));
            sb.append("\n");
            for (int topping : pizza.getToppingsList()) {
                sb.append(delim).append(nlToppings[topping]);
                delim = ", "; //re-assign this after the first pass so the commas are appropriate
            }
            sb.append("\n");

        } else {
            sb.append(getResources().getString(R.string.size)).append(": ");
            sb.append(enSize[pizza.getSize()]);
            sb.append("\n");
            sb.append(enCrust[pizza.getCrust()]);
            sb.append(" ").append(getResources().getString(R.string.crust)).append(", ");
            sb.append(enCheese[pizza.getCheese()]);
            sb.append(" ").append(getResources().getString(R.string.cheese));
            sb.append("\n");
            for (int topping : pizza.getToppingsList()) {
                sb.append(delim).append(enToppings[topping]);
                delim = ", "; //re-assign this after the first pass so the commas are appropriate
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void setLang(boolean dutch) {

        if (dutch) {

            for (int i=0;i<uiComponents.size();i++) {
                uiComponents.get(i).setText(nlStrings[i]);
            }
        } else {

            for (int i=0;i<uiComponents.size();i++) {
                uiComponents.get(i).setText(enStrings[i]);
            }
        }
    }
}