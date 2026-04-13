package com.example.demo;

public class Department {

    int id;
    String name;
    Employee empobj;



    Department() {
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(int id, String name, Employee empobj) {
        this.id = id;
        this.name = name;
        this.empobj = empobj;
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

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", empobj=" + empobj + "]";
    }

    public Employee getEmpobj() {
        return empobj;
    }

    public void setEmpobj(Employee empobj) {
        this.empobj = empobj;
    }

}
