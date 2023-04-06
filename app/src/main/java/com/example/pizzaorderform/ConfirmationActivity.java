package com.example.pizzaorderform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConfirmationActivity extends LanguageCompatActivity {

    private TextView tvThankYou, tvDateOrdered;

    private Button btnBackToMenu, btnViewPastOrders;

    private Date dateOrdered;
    private SimpleDateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy, h:mm aa", Locale.CANADA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        language = MainActivity.getLanguage();

        enStrings = getResources().getStringArray(R.array.en_confirmationactivity);
        nlStrings = getResources().getStringArray(R.array.nl_confirmationactivity);

        layoutID = R.layout.activity_confirmation;
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dateOrdered = (Date) extras.getSerializable("order_date");
        }

        tvDateOrdered.setText(tvDateOrdered.getText()+" "+dateFormat.format(dateOrdered));
    }

    @Override
    protected void initWidgets() {

        tvThankYou = findViewById(R.id.tvThankYou);
        tvDateOrdered = findViewById(R.id.tvDateOrdered);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnViewPastOrders = findViewById(R.id.btnPastOrders);

    }

    @Override
    protected void addToLists() {

        uiComponents.add(tvThankYou);
        uiComponents.add(tvDateOrdered);
        uiComponents.add(btnBackToMenu);
        uiComponents.add(btnViewPastOrders);
    }

    @Override
    protected void setListeners() {

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
}