package com.example.pizzaorderform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Pizza implements Serializable {

    //this counter initialization in the pizza, customer, and order
    // classes ensures there's no duplicate id's to those in database
    private int counter;

    //values will start at 0
    private int id;
    private int size;
    private int crust;
    private int cheese;
    private ArrayList<Integer> toppingsList = new ArrayList<>();

    public int getID() {return id;}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCrust() {
        return crust;
    }

    public void setCrust(int crust) {
        this.crust = crust;
    }

    public int getCheese() {
        return cheese;
    }

    public void setCheese(int cheese) {
        this.cheese = cheese;
    }

    public ArrayList<Integer> getToppingsList() {
        return toppingsList;
    }

    public void addTopping(int topping) {
        toppingsList.add(topping);
    }

    //calling delete topping will remove all of that topping type from the array
    public void deleteTopping(int topping) {
        toppingsList.removeAll(Arrays.asList(topping));
    }

    public Pizza() {
        counter = OrderRecordActivity.orders.size()+ 1;
        this.id = counter++;
    }

    public Pizza(int id, int size, int crust, int cheese, ArrayList<Integer> toppingsList){

        //this constructor gets called from the database, so the ids should be unique and autoincrementing/match what was passed in on save
        this.id = id;
        this.size = size;
        this.crust = crust;
        this.cheese = cheese;
        this.toppingsList = toppingsList;
    }

    @Override
    public String toString() { // unused because it only displays numeric values

        return size + "\n" + crust + "\n" + cheese + "\n" + Arrays.toString(toppingsList.toArray());
    }
}
