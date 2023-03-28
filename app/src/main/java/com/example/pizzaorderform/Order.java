package com.example.pizzaorderform;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    private int id; //needs to auto increment- static?
    private Pizza pizza;
    private Customer customer;
    private Date date;

    public Pizza getPizza() {return pizza;}
    public Customer getCustomer() {return customer;}
    public Date getDate() {return date;}

    public ArrayList<Order> orderList = new ArrayList<>();

    public Order() {}

    public Order(int id, Pizza pizza, Customer customer, Date date) {

        this.id = id;
        this.pizza = pizza;
        this.customer = customer;
        this.date = date;
    }
}
