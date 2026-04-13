package com.example.practice;

public class Engine {
    private String type; 
    private int horsepower;

    public String getType() {
        return type;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public Engine() {}

    public Engine(String type, int horsepower) {
        this.type = type;
        this.horsepower = horsepower;
    }

    public void setType(String type) { this.type = type; }
    public void setHorsepower(int horsepower) { this.horsepower = horsepower; }

    @Override
    public String toString() {
        return "Engine [Type=" + type + ", HP=" + horsepower + "]";
    }

    
}