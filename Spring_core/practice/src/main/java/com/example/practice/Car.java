package com.example.practice;

public class Car {
    private String modelName;
    private Engine engineObj; 

    public Car() {}

    public void setModelName(String modelName) { this.modelName = modelName; }
    public void setEngineObj(Engine engineObj) { this.engineObj = engineObj; }

    @Override
    public String toString() {
        return "Car [Model=" + modelName + ", " + engineObj + "]";
    }

    public Car(String modelName, Engine engineObj) {
        this.modelName = modelName;
        this.engineObj = engineObj;
    }

    public String getModelName() {
        return modelName;
    }

    public Engine getEngineObj() {
        return engineObj;
    }
}