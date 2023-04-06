package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings;

    private TextView tvThankYou, tvDateOrdered;

    private Button btnBackToMenu, btnViewPastOrders;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Date dateOrdered;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy, h:mm aa", Locale.CANADA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dateOrdered = (Date) extras.getSerializable("order_date");
        }

        initWidgets();
        addToLists();
        setListeners();

        enStrings = getResources().getStringArray(R.array.en_confirmationactivity);
        nlStrings = getResources().getStringArray(R.array.nl_confirmationactivity);

        setLang(MainActivity.getLanguage());

        tvDateOrdered.setText(tvDateOrdered.getText()+" "+dateFormat.format(dateOrdered));
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