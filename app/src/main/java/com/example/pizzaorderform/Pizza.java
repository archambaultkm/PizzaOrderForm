package com.example.pizzaorderform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Pizza implements Serializable {

    private static int counter = 0;

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
    public String toString() {

        //TODO this is where an enum or hash will be useful to get user-readable values
        return size + "\n" + crust + "\n" + cheese + "\n" + Arrays.toString(toppingsList.toArray());
    }
}
