package com.example.spring;


public class Item {

    private int id;
    private String name;
    private double price;

    public Item() {
        System.out.println("Item object created: " + this);
    }

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        System.out.println("Item created with values: " + this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayItem() {
        System.out.println("Item Details → ID: " + id + 
                           ", Name: " + name + 
                           ", Price: " + price);
    }

    @Override
    public String toString() {
        return "Item{id=" + id + 
               ", name='" + name + '\'' + 
               ", price=" + price + '}';
    }
}