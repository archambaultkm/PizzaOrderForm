package com.example.pizzaorderform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private String[] enStrings, nlStrings;

    private TextView tvDeliveryDetailsTitle, tvName, tvPhone, tvAddress1, tvCity, tvPostalCode;

    private EditText txtName, txtPhone, txtAddress1, txtCity, txtPostalCode;

    private Button btnReviewOrder;

    private ArrayList<TextView> uiComponents = new ArrayList<>();

    private Pizza pizza;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            pizza = (Pizza) extras.getSerializable("pizza");
            customer = (Customer) extras.getSerializable("customer");
        }

        initWidgets();
        addToLists();
        setListeners();

        initDetails();

        enStrings = getResources().getStringArray(R.array.en_deliveryactivity);
        nlStrings = getResources().getStringArray(R.array.nl_deliveryactivity);

        setLang(MainActivity.getLanguage());
    }

    private void initDetails() {

        if (customer != null) {

            txtName.setText(customer.getName());
            txtPhone.setText(customer.getPhoneNumber());
            txtAddress1.setText(customer.getAddress());
            txtCity.setText(customer.getCity());
            txtPostalCode.setText(customer.getPostalCode());
        }
    }

    private void initWidgets() {

        tvDeliveryDetailsTitle = findViewById(R.id.tvOrderDetailsTitle);
        tvName = findViewById(R.id.tvName);
        txtName = findViewById(R.id.txtName);
        tvPhone = findViewById(R.id.tvPhone);
        txtPhone = findViewById(R.id.txtPhone);
        tvAddress1 = findViewById(R.id.tvAddress1);
        txtAddress1 = findViewById(R.id.txtAddress1);
        tvCity = findViewById(R.id.tvCity);
        txtCity = findViewById(R.id.txtCity);
        tvPostalCode = findViewById(R.id.tvPostalCode);
        txtPostalCode = findViewById(R.id.txtPostalCode);
        btnReviewOrder = findViewById(R.id.btnReviewOrder);
    }

    private void addToLists() {

        //no need to add edittexts here bc we won't be manipulating the language on them
        uiComponents.add(tvDeliveryDetailsTitle);
        uiComponents.add(tvName);
        uiComponents.add(tvPhone);
        uiComponents.add(tvAddress1);
        uiComponents.add(tvCity);
        uiComponents.add(tvPostalCode);
        uiComponents.add(btnReviewOrder);

    }

    private void setListeners() {

        txtName.setOnFocusChangeListener(validateEntry);
        txtPhone.setOnFocusChangeListener(validateEntry);
        txtAddress1.setOnFocusChangeListener(validateEntry);
        txtCity.setOnFocusChangeListener(validateEntry);
        txtPostalCode.setOnFocusChangeListener(validateEntry);

        btnReviewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateAllEntries()) {

                    createCustomer();

                    Intent i = new Intent(DeliveryDetailsActivity.this, OrderDetailsActivity.class);

                    i.putExtra("pizza", pizza);
                    i.putExtra("customer", customer);

                    startActivity(i);

                } else {

                    if (MainActivity.getLanguage()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.nlValidationPrompt), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.enValidationPrompt), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public View.OnFocusChangeListener validateEntry = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            EditText text = (EditText) view;
            String enteredText = String.valueOf(text.getText());

            // When focus is lost check that the text field has valid values.
            if (!hasFocus) {
                switch (view.getId()) {
                    case R.id.txtName:

                        //underline the editText when the user leaves based on the validity of what they typed
                        underlineText(validateName(enteredText), txtName);
                        break;
                    case R.id.txtPhone:

                        underlineText(validatePhone(enteredText), txtPhone);
                        break;
                    case R.id.txtAddress1:

                        underlineText(validateAddress(enteredText), txtAddress1);
                        break;
                    case R.id.txtCity:

                        underlineText(validateCity(enteredText), txtCity);
                        break;
                    case R.id.txtPostalCode:

                        underlineText(validatePostalCode(enteredText), txtPostalCode);
                        break;
                }
            }
        }
    };

    private void underlineText(Boolean valid, EditText text) {

        if (valid) {
            //text.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.correct)));
            text.setBackground(getResources().getDrawable(R.drawable.edit_text_style_correct, getTheme()));
        } else {
            //text.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.incorrect)));
            text.setBackground(getResources().getDrawable(R.drawable.edit_text_style_incorrect, getTheme()));
        }
    }

    private boolean validateAllEntries() {

        //in case they click review order before editing any or one of the text fields, call the function that will
        //give visual feedback about invalid entries for each edittext
        underlineText(validateName(String.valueOf(txtName.getText())), txtName);
        underlineText(validatePhone(String.valueOf(txtPhone.getText())), txtPhone);
        underlineText(validateAddress(String.valueOf(txtAddress1.getText())), txtAddress1);
        underlineText(validateCity(String.valueOf(txtCity.getText())), txtCity);
        underlineText(validatePostalCode(String.valueOf(txtPostalCode.getText())), txtPostalCode);

        //only return true if each check field is valid
        return (validateName(String.valueOf(txtName.getText())) && validateName(String.valueOf(txtPhone.getText())) &&
                validateAddress(String.valueOf(txtAddress1.getText()))  &&
                validateCity(String.valueOf(txtCity.getText())) && validatePostalCode(String.valueOf(txtPostalCode.getText())));
    }

    //TODO trim duplicate validation methods if you aren't going to do anything different with them.

    private boolean validateName(String enteredVal) {

        //validate the string isn't null or empty
        return (!(enteredVal == null) && !(enteredVal.trim().isEmpty()));
    }

    private boolean validatePhone(String enteredVal) {

        //found https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
         return enteredVal.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    private boolean validateAddress(String enteredVal) {

        //TODO figure out what you want to do for address validation
        //since addresses aren't really a regular language I'm going to start by just checking it's not empty
        return (!(enteredVal == null) && !(enteredVal.trim().isEmpty()));
    }

    private boolean validateCity(String enteredVal) {

        //has to not be null or empty (more in-depth could check api for city names? idk)
        return (!(enteredVal == null) && !(enteredVal.trim().isEmpty()));
    }

    private boolean validatePostalCode(String enteredVal) {

        //check that it's a valid Canadian post code
        // regex found https://stackoverflow.com/questions/15774555/efficient-regex-for-canadian-postal-code-function
        return enteredVal.matches("^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]?\\d[ABCEGHJ-NPRSTV-Z]\\d$");
    }

    private void createCustomer() {

        //only make a new one if you're not working on data passed from another activity
        if (customer == null)
            customer = new Customer();

        customer.setName(String.valueOf(txtName.getText()));
        customer.setPhoneNumber(String.valueOf(txtPhone.getText()));
        customer.setAddress(String.valueOf(txtAddress1.getText()));
        customer.setCity(String.valueOf(txtCity.getText()));
        customer.setPostalCode(String.valueOf(txtPostalCode.getText()));
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