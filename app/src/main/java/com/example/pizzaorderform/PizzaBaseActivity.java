package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PizzaBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_base);
    }
}