package com.example.pizzaorderform;

import java.io.Serializable;
import java.util.Arrays;

public class Customer implements Serializable {

    private String name;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String postalCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Customer() {}

    @Override
    public String toString() {

        //this is where an enum or hash will be useful to get user-readable values
        //TODO: figure out address 2, it should be appended before address1 if it isn't null
        return name + "\n" + address1 + "\n" + city + "\n" + postalCode + "\n" + phoneNumber;
    }
}
