package com.example.pizzaorderform;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderRecordActivity extends LanguageCompatActivity {

    private TextView tvOrderRecordTitle, tvEmpty;
    private Button btnBackToMenu;
    private RecyclerView rvOrders;
    private OrderAdapter adapter;

    //this needs to never be null
    public static ArrayList<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        language = MainActivity.getLanguage();

        enStrings = getResources().getStringArray(R.array.en_recordactivity);
        nlStrings = getResources().getStringArray(R.array.nl_recordactivity);

        layoutID = R.layout.activity_order_record;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initWidgets() {

        tvOrderRecordTitle = findViewById(R.id.tvOrderRecordTitle);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnBackToMenu = findViewById(R.id.btnMenu);

        rvOrders = findViewById(R.id.rvOrders);
        adapter = new OrderAdapter(this, orders);

        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        setVisibility();
    }

    @Override
    protected void addToLists() {

        uiComponents.add(tvOrderRecordTitle);
        uiComponents.add(tvEmpty);
        uiComponents.add(btnBackToMenu);
    }

    @Override
    protected void setListeners() {

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OrderRecordActivity.this, MainActivity.class));
            }
        });
    }

    //public because this will also get called from the order adapter
    //TODO don't have this working yet to set the textview to visible when you delete the last item in recyclerview
    public void setVisibility() {
        rvOrders.setVisibility(orders.isEmpty() ? View.GONE : View.VISIBLE);
        tvEmpty.setVisibility(orders.isEmpty() ? View.VISIBLE : View.GONE);
    }
}