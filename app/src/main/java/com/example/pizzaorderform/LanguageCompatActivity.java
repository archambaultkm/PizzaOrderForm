package com.example.pizzaorderform;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

abstract class LanguageCompatActivity extends AppCompatActivity {

    String[] enStrings, nlStrings;
    ArrayList<TextView> uiComponents = new ArrayList<>();
    boolean language;
    int layoutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layoutID);

        initWidgets();
        addToLists();
        setListeners();

        setLanguage(language);
    }//end oncreate

    abstract void initWidgets();
    abstract void addToLists();
    abstract void setListeners();

    //this method is the exact same across all my activities in this app which is why I made this superclass
    protected void setLanguage(boolean dutch) {

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
