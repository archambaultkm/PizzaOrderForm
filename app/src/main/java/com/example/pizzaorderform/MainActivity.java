package com.example.pizzaorderform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends LanguageCompatActivity {

    private SharedPreferences preferences;
    private static boolean savedLanguage;

    private TextView tvWelcome;
    private Button btnGetStarted, btnViewPastOrders;
    private Switch tglLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.enStrings = getResources().getStringArray(R.array.en_mainactivity);
        this.nlStrings = getResources().getStringArray(R.array.nl_mainactivity);

        layoutID = R.layout.activity_main;
        super.onCreate(savedInstanceState);

        //fetch stored data from sharedpreferences
        preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);

        //true will be dutch, default will be false (english)
        savedLanguage = preferences.getBoolean("language", false);
        language = savedLanguage;
        //make the toggle button reflect the saved language
        tglLanguage.setChecked(language);

        OrderRecordActivity.orders.clear(); //empty the list any time it'll be populated from the db so there are no duplicates
        loadFromDB(); //load saved orders into the order review activity
    }//end oncreate

    //this function will load saved orders into the orders array in the order record activity
    private void loadFromDB() {
        SQLiteAdapter sqLiteAdapter = SQLiteAdapter.instanceOfDatabase(this);
        sqLiteAdapter.populateOrderList();
    }

    @Override
    protected void initWidgets() {

        tvWelcome = findViewById(R.id.tvWelcome);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnViewPastOrders = findViewById(R.id.btnViewPastOrders);
        tglLanguage = findViewById(R.id.tglLanguage);
    }

    @Override
    protected void addToLists() {

        uiComponents.add(tvWelcome);
        uiComponents.add(btnGetStarted);
        uiComponents.add(btnViewPastOrders);
        uiComponents.add(tglLanguage);
    }

    @Override
    protected void setListeners() {

        btnGetStarted.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, PizzaBaseActivity.class));
            }
        });

        btnViewPastOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, OrderRecordActivity.class));
            }
        });

        tglLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                language = isChecked;
                savedLanguage = language;
                MainActivity.super.setLanguage(language);

                preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                //write preferences to the sharedpreferences when the app is closed based on where the switch is
                editor.putBoolean("language", language);
                editor.apply();
            }
        });
    }

    public static boolean getLanguage() {
        return savedLanguage;
    }
}//end class