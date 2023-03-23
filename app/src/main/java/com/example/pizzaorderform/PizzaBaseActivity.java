package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//TODO:figure out how passing in a pizza to edit will work
//TODO:disable the toppings button until they've picked a size
//TODO:make the crust and cheese default to regular

public class PizzaBaseActivity extends AppCompatActivity {

    Button btnSize1, btnSize2, btnSize3, btnSize4, btnCrust1, btnCrust2, btnCrust3,
            btnCheese1, btnCheese2, btnCheese3, btnToppingSelection;

    Pizza pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_base);

        //make room for a new pizza:
        //need to figure out how this works passing in a past pizza to edit
        pizza = new Pizza();

        //instantiate widgets
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

        //set onClick listeners
        btnSize1.setOnClickListener(onSizeClicked);
        btnSize2.setOnClickListener(onSizeClicked);
        btnSize3.setOnClickListener(onSizeClicked);
        btnSize4.setOnClickListener(onSizeClicked);

        btnCrust1.setOnClickListener(onCrustClicked);
        btnCrust2.setOnClickListener(onCrustClicked);
        btnCrust3.setOnClickListener(onCrustClicked);

        btnCheese1.setOnClickListener(onCheeseClicked);
        btnCheese2.setOnClickListener(onCheeseClicked);
        btnCheese3.setOnClickListener(onCheeseClicked);

        btnToppingSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PizzaBaseActivity.this, PizzaToppingsActivity.class);

                //carry over count of correctly answered questions
                i.putExtra("pizza", pizza);

                startActivity(i);
            }
        });
    }// end onCreate

    public View.OnClickListener onSizeClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //casting the view to a button allows using .getText()
            Button clickedButton = (Button) view;

            //when changing selection, make previously selected button normal again
            resetSizeButtons();
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {
                case R.id.btnSize1:
                    pizza.setSize(1);
                    break;
                case R.id.btnSize2:
                    pizza.setSize(2);
                    break;
                case R.id.btnSize3:
                    pizza.setSize(3);
                    break;
                case R.id.btnSize4:
                    pizza.setSize(4);
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
            resetCrustButtons();
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {
                case R.id.btnCrust1:
                    pizza.setCrust(1);
                    break;
                case R.id.btnCrust2:
                    pizza.setCrust(2);
                    break;
                case R.id.btnCrust3:
                    pizza.setCrust(3);
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
            resetCheeseButtons();
            clickedButton.setBackground(getResources().getDrawable(R.drawable.alternate_button_highlighted, getTheme()));

            switch (clickedButton.getId()) {

                case R.id.btnCheese1:
                    pizza.setCheese(1);
                    break;
                case R.id.btnCheese2:
                    pizza.setCheese(2);
                    break;
                case R.id.btnCheese3:
                    pizza.setCheese(3);
                    break;
            }
        }
    };

    private void resetSizeButtons() {

        btnSize1.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnSize2.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnSize3.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnSize4.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
    }

    private void resetCrustButtons() {

        btnCrust1.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnCrust2.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnCrust3.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
    }

    private void resetCheeseButtons() {

        btnCheese1.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnCheese2.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
        btnCheese3.setBackground(getResources().getDrawable(R.drawable.alternate_button, getTheme()));
    }

}