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

    private String[] enStrings, nlStrings;

    private TextView tvOrderDetailsTitle, tvOrderID, tvPizzaDetails, tvCustomerDetails;

    private Button btnEdit, btnConfirm;

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

        setLang(MainActivity.getLanguage());

        showOrderData();
    }

    private void showOrderData() {

        tvOrderID.setText(tvOrderID.getText() + " " + String.valueOf(order.getID()));
        tvPizzaDetails.setText(pizza.toString());
        tvCustomerDetails.setText(customer.toString());
    }

    private void initWidgets() {

        tvOrderDetailsTitle = findViewById(R.id.tvOrderDetailsTitle);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvPizzaDetails = findViewById(R.id.tvPizzaDetails);
        tvCustomerDetails = findViewById(R.id.tvCustomerDetails);
        btnEdit = findViewById(R.id.btnEdit);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void addToLists() {

        uiComponents.add(tvOrderDetailsTitle);
        uiComponents.add(tvOrderID);
        uiComponents.add(btnEdit);
        uiComponents.add(btnConfirm);
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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the current date for the record
                Date date = new Date();
                order.setDate(date);

                //add this order to the list for review
                OrderRecordActivity.orders.add(order);

                Intent i = new Intent(OrderDetailsActivity.this, ConfirmationActivity.class);

                //the time of ordering will be shown on the thank-you page
                i.putExtra("order_date", date);

                startActivity(i);
            }
        });
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