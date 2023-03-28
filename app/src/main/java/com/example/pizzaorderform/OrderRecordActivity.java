package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class OrderRecordActivity extends AppCompatActivity {

    ArrayList<Order> orders;

    private RecyclerView rvOrders;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);

        //TODO link this orders arraylist to database orders

        rvOrders = findViewById(R.id.rvOrders);

        adapter = new OrderAdapter(orders);
        rvOrders.setAdapter(adapter);

    }
}