package com.example.pizzaorderform;

import java.util.ArrayList;
import java.util.Date;

//order doesn't need to be serializable because I'm never passing a whole order using intents.
public class Order {

    private static int count = 0;

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

        this.id = ++ count;
    }

    public Order(int id, Pizza pizza, Customer customer, Date date) {

        //this constructor gets called from the database, so the ids should be unique and autoincrementing/match what was passed in on save
        this.id = id;
        this.pizza = pizza;
        this.customer = customer;
        this.date = date;
    }
}
