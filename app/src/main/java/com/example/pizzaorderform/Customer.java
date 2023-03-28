package com.example.pizzaorderform;

import java.io.Serializable;

public class Customer implements Serializable {

    private String name;
    private String phoneNumber;
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

        //customer values formatted like a delivery address
        return name + "\n" + address + "\n" + city + "\n" + postalCode + "\n" + phoneNumber;
    }
}
