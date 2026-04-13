package com.example.demo;

public class Book {
    private String title;
    private double price;
    private Author authObj; 

    public Book() {}

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Author getAuthObj() {
        return authObj;
    }

    public void setTitle(String title) { this.title = title; }
    public void setPrice(double price) { this.price = price; }
    public void setAuthObj(Author authObj) { this.authObj = authObj; }

    @Override
    public String toString() {
        return "Book [Title=" + title + ", Price=" + price + ", " + authObj + "]";
    }
}