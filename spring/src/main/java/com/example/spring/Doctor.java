package com.example.spring;

public class Doctor {
    private String name;
    private String specialization;

    public Doctor() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return "Doctor [name=" + name + ", specialization=" + specialization + "]";
    }
}