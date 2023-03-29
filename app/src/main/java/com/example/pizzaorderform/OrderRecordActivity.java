package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderRecordActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings;

    private TextView tvOrderRecordTitle;
    private Button btnBackToMenu;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    //this needs to never be null
    public static ArrayList<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);

        initWidgets();
        addToLists();
        setListeners();

        enStrings = getResources().getStringArray(R.array.en_recordactivity);
        nlStrings = getResources().getStringArray(R.array.nl_recordactivity);

        setLang(MainActivity.getLanguage());
    }

    private void initWidgets() {

        tvOrderRecordTitle = findViewById(R.id.tvOrderRecordTitle);
        btnBackToMenu = findViewById(R.id.btnMenu);

        RecyclerView rvOrders = findViewById(R.id.rvOrders);

        OrderAdapter adapter = new OrderAdapter(this, orders);

        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners() {

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OrderRecordActivity.this, MainActivity.class));
            }
        });
    }

    private void addToLists() {

        uiComponents.add(tvOrderRecordTitle);
        uiComponents.add(btnBackToMenu);
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