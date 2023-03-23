package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PizzaToppingsActivity extends AppCompatActivity {

    Button btnTopping1, btnTopping2, btnTopping3, btnTopping4, btnTopping5, btnTopping6, btnTopping7, btnTopping8,
            btnTopping9, btnTopping10, btnTopping11, btnTopping12, btnTopping13, btnTopping14, btnTopping15, btnTopping16,
            btnBack, btnDeliveryDetails;

    ArrayList<Button> toppingButtons = new ArrayList<>();

    Pizza pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_toppings);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            pizza = (Pizza) getIntent().getSerializableExtra("pizza");
        }

        //instantiate widgets
        btnBack = findViewById(R.id.btnBack);
        btnDeliveryDetails = findViewById(R.id.btnDeliveryDetails);

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

        //set Onclick
        for (Button button : toppingButtons) {
            button.setOnClickListener(onToppingClicked);
        }

    }

    public View.OnClickListener onToppingClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //add in a conditional... if the button is long clicked, unselect it.

            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));


        }
    }; //end onToppingClicked
}