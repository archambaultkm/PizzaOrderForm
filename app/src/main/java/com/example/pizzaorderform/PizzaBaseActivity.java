package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

//TODO:disable the toppings button until they've picked a size

public class PizzaBaseActivity extends AppCompatActivity {

    String[] enStrings, nlStrings;

    private TextView tvPizzaBaseTitle, tvSize, tvCrust, tvCheese;

    private Button btnSize1, btnSize2, btnSize3, btnSize4, btnCrust1, btnCrust2, btnCrust3,
            btnCheese1, btnCheese2, btnCheese3, btnToppingSelection;

    private ArrayList<Button> sizeButtons, crustButtons, cheeseButtons;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Customer customer;
    private Pizza pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_base);

        sizeButtons = new ArrayList<>();
        crustButtons  = new ArrayList<>();
        cheeseButtons  = new ArrayList<>();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            pizza = (Pizza) extras.getSerializable("pizza");
            customer = (Customer) extras.getSerializable("customer");
        }

        //set string values:
        enStrings = getResources().getStringArray(R.array.en_baseactivity);
        nlStrings = getResources().getStringArray(R.array.nl_baseactivity);

        initWidgets();
        addToLists();
        setListeners();

        initPizza();

        //set the string values displayed in views based on the language chosen in mainactivity
        setLang(MainActivity.getLanguage());

    }// end onCreate

    private void initPizza() {

        //make ui reflect the current pizza
        if (pizza == null) {

            pizza = new Pizza();

            //default a new pizza to have regular cheese and sauce
            pizza.setCrust(1);
            pizza.setCheese(1);

            //update the cheese and crust buttons to reflect data already stored for the pizza
            btnCrust2.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));
            btnCheese2.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

        } else {

            sizeButtons.get(pizza.getSize()).setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));
            crustButtons.get(pizza.getCrust()).setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));
            cheeseButtons.get(pizza.getCheese()).setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));
        }
    }

    private void initWidgets() {

        tvPizzaBaseTitle = findViewById(R.id.tvPizzaBaseTitle);
        tvSize = findViewById(R.id.tvSize);
        tvCrust = findViewById(R.id.tvCrust);
        tvCheese = findViewById(R.id.tvCheese);

        btnSize1=findViewById(R.id.btnSize1);
        btnSize2=findViewById(R.id.btnSize2);
        btnSize3=findViewById(R.id.btnSize3);
        btnSize4=findViewById(R.id.btnSize4);

        btnCrust1=findViewById(R.id.btnCrust1);
        btnCrust2=findViewById(R.id.btnCrust2);
        btnCrust3=findViewById(R.id.btnCrust3);

        btnCheese1=findViewById(R.id.btnCheese1);
        btnCheese2=findViewById(R.id.btnCheese2);
        btnCheese3=findViewById(R.id.btnCheese3);

        btnToppingSelection=findViewById(R.id.btnToppingSelection);
    }

    private void addToLists() {

        //it''s important that everything in this list matches its index in the strings array
        uiComponents.add(tvPizzaBaseTitle);
        uiComponents.add(tvSize);
        uiComponents.add(btnSize1);
        uiComponents.add(btnSize2);
        uiComponents.add(btnSize3);
        uiComponents.add(btnSize4);
        uiComponents.add(tvCrust);
        uiComponents.add(btnCrust1);
        uiComponents.add(btnCrust2);
        uiComponents.add(btnCrust3);
        uiComponents.add(tvCheese);
        uiComponents.add(btnCheese1);
        uiComponents.add(btnCheese2);
        uiComponents.add(btnCheese3);
        uiComponents.add(btnToppingSelection);

        //make groups of related buttons
        sizeButtons.add(btnSize1);
        sizeButtons.add(btnSize2);
        sizeButtons.add(btnSize3);
        sizeButtons.add(btnSize4);

        crustButtons.add(btnCrust1);
        crustButtons.add(btnCrust2);
        crustButtons.add(btnCrust3);

        cheeseButtons.add(btnCheese1);
        cheeseButtons.add(btnCheese2);
        cheeseButtons.add(btnCheese3);
    }

    private void setListeners() {
        //set onClick listeners
        for (Button btn : sizeButtons) {
            btn.setOnClickListener(onSizeClicked);
        }

        for (Button btn : crustButtons) {
            btn.setOnClickListener(onCrustClicked);
        }

        for (Button btn : cheeseButtons) {
            btn.setOnClickListener(onCheeseClicked);
        }

        btnToppingSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PizzaBaseActivity.this, PizzaToppingsActivity.class);

                //carry over count of correctly answered questions
                i.putExtra("pizza", pizza);
                i.putExtra("customer", customer);

                startActivity(i);
            }
        });
    }

    public View.OnClickListener onSizeClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //this only needs to happen the first time one is clicked but idk how to do that
            btnToppingSelection.setEnabled(true);

            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //when changing selection, make previously selected button normal again
            resetButtonSet(sizeButtons);
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {
                case R.id.btnSize1:
                    pizza.setSize(0);
                    break;
                case R.id.btnSize2:
                    pizza.setSize(1);
                    break;
                case R.id.btnSize3:
                    pizza.setSize(2);
                    break;
                case R.id.btnSize4:
                    pizza.setSize(3);
                    break;
            }
        }
    };

    public View.OnClickListener onCrustClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //when changing selection, make previously selected button normal again
            resetButtonSet(crustButtons);
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {
                case R.id.btnCrust1:
                    pizza.setCrust(0);
                    break;
                case R.id.btnCrust2:
                    pizza.setCrust(1);
                    break;
                case R.id.btnCrust3:
                    pizza.setCrust(2);
                    break;
            }
        }
    };

    public View.OnClickListener onCheeseClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //when changing selection, make previously selected button normal again
            resetButtonSet(cheeseButtons);
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {

                case R.id.btnCheese1:
                    pizza.setCheese(0);
                    break;
                case R.id.btnCheese2:
                    pizza.setCheese(1);
                    break;
                case R.id.btnCheese3:
                    pizza.setCheese(2);
                    break;
            }
        }
    };

    private void resetButtonSet(ArrayList<Button> buttons) {

        for (Button btn : buttons) {
            btn.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        }
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