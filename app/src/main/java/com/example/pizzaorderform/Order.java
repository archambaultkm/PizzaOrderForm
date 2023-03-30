package com.example.pizzaorderform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private static int counter = 0;

    private int id;
    private Pizza pizza;
    private Customer customer;
    private Date date;

    public int getID() {return id;}

    public Pizza getPizza() {return pizza;}

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {
        this.date = date;
    }

    public Order() {

        this.id = counter++;
    }

    public Order(int id, Pizza pizza, Customer customer, Date date) {

        //this constructor gets called from the database, so the ids should be unique and autoincrementing/match what was passed in on save
        this.id = id;
        this.pizza = pizza;
        this.customer = customer;
        this.date = date;
    }
}
