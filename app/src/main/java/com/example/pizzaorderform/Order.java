package com.example.pizzaorderform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private int id;
    private int pizzaID;
    private int customerID;
    private Pizza pizza;
    private Customer customer;
    private Date date;

    public int getID() {return id;}

    public void setPizzaID(int id) {this.pizzaID = id;}

    public void setCustomerID(int id) {this.customerID = id;}

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

        //this counter initialization in the pizza, customer, and order
        // classes ensures there's no duplicate id's to those in database
        int lastOrder = OrderRecordActivity.orders.get(OrderRecordActivity.orders.size() - 1).getID();
        this.id = lastOrder + 1;
    }

    public Order(int id, Pizza pizza, Customer customer, Date date) {

        //this constructor gets called from the database, so the ids should be unique and autoincrementing/match what was passed in on save
        this.id = id;
        this.pizza = pizza;
        this.pizzaID = pizza.getID();
        this.customer = customer;
        this.customerID = customer.getID();
        this.date = date;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final Order other = (Order) obj;
        return id == other.id; //this is used after reviewing an order to determine if the order already exists in the db
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pizzaID, customerID, pizza, customer, date);
    }
}
