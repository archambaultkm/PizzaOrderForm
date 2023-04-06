package com.example.pizzaorderform;

import java.io.Serializable;

public class Customer implements Serializable {

    //this counter initialization in the pizza, customer, and order
    // classes ensures there's no duplicate id's to those in database
    private int counter;

    private int id;
    private String phoneNumber;
    private String name;
    private String address;
    private String city;
    private String postalCode;

    public int getID() {return id;}

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

    public Customer() {

        counter = OrderRecordActivity.orders.size()+ 1;
        this.id=counter++;
    }

    public Customer(int id, String phoneNumber, String name, String address, String city, String postalCode) {

        //this constructor gets called from the database, so the ids should be unique and autoincrementing/match what was passed in on save
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {

        //customer values formatted like a delivery address
        return name + "\n" + address + "\n" + city + "\n" + postalCode + "\n" + phoneNumber;
    }
}
