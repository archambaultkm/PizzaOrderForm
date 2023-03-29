package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private static boolean language;

    private TextView tvWelcome;
    private Button btnGetStarted, btnViewPastOrders;
    private Switch tglLanguage;

    String[] enStrings;
    String[] nlStrings;

    ArrayList<TextView> uiComponents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enStrings = getResources().getStringArray(R.array.en_mainactivity);
        nlStrings = getResources().getStringArray(R.array.nl_mainactivity);

        initWidgets();
        addToLists();
        setListeners();

        //fetch stored data from sharedpreferences
        preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);

        //true will be dutch, default will be false (english)
        language = preferences.getBoolean("language", false);
        //make the toggle button reflect the saved language
        tglLanguage.setChecked(language);

        setLang(language);

    }//end oncreate

    @Override
    protected void onResume() {

        super.onResume();

        //fetch stored data in sharedpreferences
        preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);

        //true will be dutch, default will be false (english)
        language = preferences.getBoolean("language", false);
        setLang(language);

        //make the toggle button reflect the saved language
        tglLanguage.setChecked(language);
    }

    @Override
    protected void onPause() {
        super.onPause();

        preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //write preferences to the sharedpreferences when the app is closed based on where the switch is
        editor.putBoolean("language", tglLanguage.isChecked());
        editor.apply();
    }

    private void initWidgets() {

        tvWelcome = findViewById(R.id.tvWelcome);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnViewPastOrders = findViewById(R.id.btnViewPastOrders);
        //TODO:add onclick for viewpastorders

        tglLanguage = findViewById(R.id.tglLanguage);

    }

    private void addToLists() {

        uiComponents.add(tvWelcome);
        uiComponents.add(btnGetStarted);
        uiComponents.add(btnViewPastOrders);
        uiComponents.add(tglLanguage);
    }

    private void setListeners() {

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
                setLang(language);
            }
        });
    }

    //if they've checked the switch, language changes to dutch. if they've unchecked change back to english.
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

    public static boolean getLanguage() {
        return language;
    }
}//end class