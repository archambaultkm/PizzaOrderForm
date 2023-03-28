package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmationActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings;

    private TextView tvThankYou, tvDateOrdered;

    private Button btnBackToMenu, btnViewPastOrders;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        initWidgets();
        addToLists();
        setListeners();

        enStrings = getResources().getStringArray(R.array.en_confirmationactivity);
        nlStrings = getResources().getStringArray(R.array.nl_confirmationactivity);

        setLang(MainActivity.getLanguage());
    }

    private void initWidgets() {

        tvThankYou = findViewById(R.id.tvThankYou);
        tvDateOrdered = findViewById(R.id.tvDateOrdered);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnViewPastOrders = findViewById(R.id.btnPastOrders);

    }

    private void addToLists() {

        uiComponents.add(tvThankYou);
        uiComponents.add(tvDateOrdered);
        uiComponents.add(btnBackToMenu);
        uiComponents.add(btnViewPastOrders);
    }

    private void setListeners() {

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ConfirmationActivity.this, MainActivity.class));
            }
        });

        btnViewPastOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ConfirmationActivity.this, OrderRecordActivity.class));
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