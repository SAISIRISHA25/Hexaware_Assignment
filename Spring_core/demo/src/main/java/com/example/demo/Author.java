package com.example.demo;

public class Author {
    private String name;
    private String country;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Author() {}

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Author [Name=" + name + ", Country=" + country + "]";
    }
}