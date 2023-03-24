package com.example.pizzaorderform;

import java.io.Serializable;

public class Pizza implements Serializable {

    //I would prefer to refactor these to enums
    //values will start at 0
    private int size;
    private int crust;
    private int cheese;

    private int topping1;
    private int topping2;
    private int topping3;

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

    public int getTopping1() {
        return topping1;
    }

    public void setTopping1(int topping1) {
        this.topping1 = topping1;
    }

    public int getTopping2() {
        return topping2;
    }

    public void setTopping2(int topping2) {
        this.topping2 = topping2;
    }

    public int getTopping3() {
        return topping3;
    }

    public void setTopping3(int topping3) {
        this.topping3 = topping3;
    }

    public Pizza() {};
}
