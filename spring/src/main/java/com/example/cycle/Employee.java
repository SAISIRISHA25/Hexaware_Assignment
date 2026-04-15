package com.example.cycle;


public class Employee {

    private int empId;
    private String name;

    public Employee() {
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

    public void start() {
        System.out.println("Init method called: Bean is starting");
    }

    public void stop() {
        System.out.println("Destroy method called: Bean is being destroyed");
    }

    @Override
    public String toString() {
        return "Employee [empId=" + empId + ", name=" + name + "]";
    }
}
