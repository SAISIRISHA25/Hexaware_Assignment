package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class Car {
    private String brand;
    private Engine engine; // This name must match the XML Bean ID

    public Car() {
        System.out.println("Car: Default constructor called");
    }

    public Car(String brand) {
        this.brand = brand;
        System.out.println("Car: Parameterized constructor called for " + brand);
    }

    public Engine getEngine() {
        return engine;
    }

    @Autowired 
    public void setEngine(Engine engine) {
        System.out.println("Car: Setting engine via Autowire byName...");
        this.engine = engine;
    }

    public void drive() {
        if (engine != null) {
            engine.start();
            System.out.println(brand + " is driving.");
        } else {
            System.out.println("No engine found!");
        }
    }
}