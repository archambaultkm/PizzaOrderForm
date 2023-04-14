package com.example.pizzaorderform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryDetailsActivity extends LanguageCompatActivity {

    private TextView tvDeliveryDetailsTitle, tvName, tvPhone, tvAddress1, tvCity, tvPostalCode;

    private EditText txtName, txtPhone, txtAddress1, txtCity, txtPostalCode;

    private Button btnReviewOrder;

    private Customer customer;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        language = MainActivity.getLanguage();

        enStrings = getResources().getStringArray(R.array.en_deliveryactivity);
        nlStrings = getResources().getStringArray(R.array.nl_deliveryactivity);

        layoutID = R.layout.activity_delivery_details;
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = (Order) extras.getSerializable("order");
        }

        customer = order.getCustomer();

        initDetails();
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

    @Override
    protected void initWidgets() {

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

    @Override
    protected void addToLists() {

        //no need to add edittexts here bc we won't be manipulating the language on them
        uiComponents.add(tvDeliveryDetailsTitle);
        uiComponents.add(tvName);
        uiComponents.add(tvPhone);
        uiComponents.add(tvAddress1);
        uiComponents.add(tvCity);
        uiComponents.add(tvPostalCode);
        uiComponents.add(btnReviewOrder);
    }

    @Override
    protected void setListeners() {

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
                    order.setCustomer(customer);

                    Intent i = new Intent(DeliveryDetailsActivity.this, OrderDetailsActivity.class);
                    i.putExtra("order", order);

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
                        underlineText(validateNotEmpty(enteredText), txtName);
                        break;
                    case R.id.txtPhone:

                        underlineText(validatePhone(enteredText), txtPhone);
                        break;
                    case R.id.txtAddress1:

                        underlineText(validateNotEmpty(enteredText), txtAddress1);
                        break;
                    case R.id.txtCity:

                        underlineText(validateNotEmpty(enteredText), txtCity);
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
        underlineText(validateNotEmpty(String.valueOf(txtName.getText())), txtName);
        underlineText(validatePhone(String.valueOf(txtPhone.getText())), txtPhone);
        underlineText(validateNotEmpty(String.valueOf(txtAddress1.getText())), txtAddress1);
        underlineText(validateNotEmpty(String.valueOf(txtCity.getText())), txtCity);
        underlineText(validatePostalCode(String.valueOf(txtPostalCode.getText())), txtPostalCode);

        //only return true if each check field is valid
        return (validateNotEmpty(String.valueOf(txtName.getText())) && validatePhone(String.valueOf(txtPhone.getText())) &&
                validateNotEmpty(String.valueOf(txtAddress1.getText()))  && validateNotEmpty(String.valueOf(txtCity.getText()))
                && validatePostalCode(String.valueOf(txtPostalCode.getText())));
    }

    //multipurpose validation for fields that just have to have something
    private boolean validateNotEmpty(String enteredVal) {

        //validate the string isn't null or empty
        return (!(enteredVal == null) && !(enteredVal.trim().isEmpty()));
    }

    private boolean validatePhone(String enteredVal) {

        //adapted from https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
         return enteredVal.matches("^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
    }

    private boolean validatePostalCode(String enteredVal) {

        //check that it's a valid Canadian post code
        //adapted from https://stackoverflow.com/questions/15774555/efficient-regex-for-canadian-postal-code-function
        return enteredVal.toUpperCase().matches("^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]?\\d[ABCEGHJ-NPRSTV-Z]\\d$");
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
}