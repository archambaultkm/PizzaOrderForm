package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PizzaToppingsActivity extends AppCompatActivity {

    private int MIN_TOPPINGS = 1;
    private int MAX_TOPPINGS = 3;

    private String[] enStrings, nlStrings;

    private TextView tvToppingsTitle, tvToppingsInfo1, tvToppingsInfo2;

    private Button btnTopping1, btnTopping2, btnTopping3, btnTopping4, btnTopping5, btnTopping6, btnTopping7, btnTopping8,
            btnTopping9, btnTopping10, btnTopping11, btnTopping12, btnTopping13, btnTopping14, btnTopping15, btnTopping16,
            btnTopping17, btnTopping18, btnBack, btnDeliveryDetails;

    private ArrayList<Button> toppingButtons = new ArrayList<>();
    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Pizza pizza;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_toppings);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = (Order) extras.getSerializable("order");
        }

        pizza = order.getPizza();

        initWidgets();
        addToLists();
        setListeners();

        initToppings();

        //set string values:
        enStrings = getResources().getStringArray(R.array.en_toppingsactivity);
        nlStrings = getResources().getStringArray(R.array.nl_toppingsactivity);

        setLang(MainActivity.getLanguage());
    }

    private void initToppings() {

        //make the topping buttons reflect the state of the pizza
        for (int i=0; i< MAX_TOPPINGS;i++) {
            if (i < pizza.getToppingsList().size()) {
                toppingButtons.get(pizza.getToppingsList().get(i)).setSelected(true);
            }
        }
    }

    private void initWidgets() {

        tvToppingsTitle = findViewById(R.id.tvToppingsTitle);
        tvToppingsInfo1 = findViewById(R.id.tvToppingsInfo1);
        tvToppingsInfo2 = findViewById(R.id.tvToppingsInfo2);

        btnBack = findViewById(R.id.btnBack);
        btnDeliveryDetails = findViewById(R.id.btnDeliveryDetails);

        //add each toppings button into an arraylist for easier reading/manipulation
        toppingButtons.add(btnTopping1 = findViewById(R.id.btnTopping1));
        toppingButtons.add(btnTopping2 = findViewById(R.id.btnTopping2));
        toppingButtons.add(btnTopping3 = findViewById(R.id.btnTopping3));
        toppingButtons.add(btnTopping4 = findViewById(R.id.btnTopping4));
        toppingButtons.add(btnTopping5 = findViewById(R.id.btnTopping5));
        toppingButtons.add(btnTopping6 = findViewById(R.id.btnTopping6));
        toppingButtons.add(btnTopping7 = findViewById(R.id.btnTopping7));
        toppingButtons.add(btnTopping8 = findViewById(R.id.btnTopping8));
        toppingButtons.add(btnTopping9 = findViewById(R.id.btnTopping9));
        toppingButtons.add(btnTopping10 = findViewById(R.id.btnTopping10));
        toppingButtons.add(btnTopping11 = findViewById(R.id.btnTopping11));
        toppingButtons.add(btnTopping12 = findViewById(R.id.btnTopping12));
        toppingButtons.add(btnTopping13 = findViewById(R.id.btnTopping13));
        toppingButtons.add(btnTopping14 = findViewById(R.id.btnTopping14));
        toppingButtons.add(btnTopping15 = findViewById(R.id.btnTopping15));
        toppingButtons.add(btnTopping16 = findViewById(R.id.btnTopping16));
        toppingButtons.add(btnTopping17 = findViewById(R.id.btnTopping17));
        toppingButtons.add(btnTopping18 = findViewById(R.id.btnTopping18));
    }

    private void addToLists() {

        uiComponents.add(tvToppingsTitle);
        uiComponents.add(tvToppingsInfo1);
        uiComponents.add(tvToppingsInfo2);

        for (Button component : toppingButtons) {
            uiComponents.add(component);
        }

        uiComponents.add(btnBack);
        uiComponents.add(btnDeliveryDetails);
    }

    private void setListeners() {

        //set Onclicks for toppings
        for (Button button : toppingButtons) {
            button.setOnClickListener(onToppingClicked);
            button.setOnLongClickListener(onToppingLongClicked);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PizzaToppingsActivity.this, PizzaBaseActivity.class);

                i.putExtra("order", order);

                startActivity(i);
            }
        });

        btnDeliveryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pizza.getToppingsList().size() < MIN_TOPPINGS) {
                    if (MainActivity.getLanguage()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.nlToppingsPrompt), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.enToppingsPrompt), Toast.LENGTH_LONG).show();
                    }
                } else {
                    //save changes to this order's pizza
                    order.setPizza(pizza);

                    Intent i = new Intent(PizzaToppingsActivity.this, DeliveryDetailsActivity.class);
                    i.putExtra("order", order);

                    startActivity(i);
                }
            }
        });
    }

    public View.OnClickListener onToppingClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //important that the buttons are in the same order as the toppings they represent in the string array
            if (pizza.getToppingsList().size() < MAX_TOPPINGS) {

                clickedButton.setSelected(true);
                pizza.addTopping(toppingButtons.indexOf(clickedButton));

            } else {

                if (MainActivity.getLanguage()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nlToppingsError), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.enToppingsError), Toast.LENGTH_LONG).show();
                }
            }
        }
    }; //end onToppingClicked

    public View.OnLongClickListener onToppingLongClicked = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {

            Button clickedButton = (Button) view;

            //reset the appearance of the button
            clickedButton.setSelected(false);

            //remove all occurences of the value associated with the button long clicked from the toppings array
            pizza.deleteTopping(toppingButtons.indexOf(clickedButton));

            return true;
        }

    }; //end onToppingLongClicked

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