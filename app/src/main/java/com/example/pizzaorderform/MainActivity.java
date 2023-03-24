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

        setWidgets();

        //fetch stored data from sharedpreferences
        preferences = getSharedPreferences("LANG_PREFS", MODE_PRIVATE);

        //true will be dutch, default will be false (english)
        language = preferences.getBoolean("language", false);
        setLang(language);

        //make the toggle button reflect the saved language
        tglLanguage.setChecked(language);

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

    private void setWidgets() {

        uiComponents.add(tvWelcome = findViewById(R.id.tvWelcome));

        uiComponents.add(btnGetStarted = findViewById(R.id.btnGetStarted));
        btnGetStarted.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, PizzaBaseActivity.class);

                //pass along language info
                //i.putExtra("language", language);

                startActivity(i);
            }
        });//end onclick

        uiComponents.add(btnViewPastOrders = findViewById(R.id.btnViewPastOrders));
        //TODO:add onclick for viewpastorders

        tglLanguage = findViewById(R.id.tglLanguage);
        tglLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setLang(isChecked);
            }
        });
    }

    //if they've checked the switch, language changes to dutch. if they've unchecked change back to english.
    //TODO: add text value for switch here and in strings.xml
    private void setLang(boolean dutch) {

        //this isn't elegant but I can't figure out how to loop thorugh them without casting to view
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