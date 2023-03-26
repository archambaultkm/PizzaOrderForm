package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings;

    private TextView tvOrderDetailsTitle, tvOrderID, tvPizzaDetails, tvCustomerDetails;

    private Button btnEdit, btnConfirm;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Pizza pizza;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            pizza = (Pizza) extras.getSerializable("pizza");
            customer = (Customer) extras.getSerializable("customer");
        }

        initWidgets();
        addToLists();
        setListeners();

        enStrings = getResources().getStringArray(R.array.en_reviewactivity);
        nlStrings = getResources().getStringArray(R.array.nl_reviewactivity);

        setLang(MainActivity.getLanguage());

        showOrderData();
    }

    private void showOrderData() {

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
//        uiComponents.add(tvPizzaDetails);
//        uiComponents.add(tvCustomerDetails);
        uiComponents.add(btnConfirm);
    }

    private void setListeners() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OrderDetailsActivity.this, PizzaBaseActivity.class);

                i.putExtra("pizza", pizza);
                i.putExtra("customer", customer);

                startActivity(i);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO add all the stuff for making a new order and setting the id/date
                //TODO add all the database stuff

                startActivity(new Intent(OrderDetailsActivity.this, ConfirmationActivity.class));
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