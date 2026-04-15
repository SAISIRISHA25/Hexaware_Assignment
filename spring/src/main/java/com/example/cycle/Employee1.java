package com.example.cycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component("emp")
public class Employee1 {

    private int empId;
    private String name;

    public Employee1() {
        System.out.println("Employee object created");
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void start() {
        empId = 101;
        name = "Sai Sirisha";
        System.out.println("PostConstruct method called: Bean is starting");
    }

    @PreDestroy
    public void stop() {
        System.out.println("PreDestroy method called: Bean is being destroyed");
    }

    @Override
    public String toString() {
        return "Employee [empId=" + empId + ", name=" + name + "]";
    }
}