package com.example.spring;

public class Engine {
    private String model;

    public Engine() {
        this.model = "Standard V6";
    }

    public Engine(String model) {
        this.model = model;
    }

    public void start() {
        System.out.println(model + " Engine starting... Vroom!");
    }
}