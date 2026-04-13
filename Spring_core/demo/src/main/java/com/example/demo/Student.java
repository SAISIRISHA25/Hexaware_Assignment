package com.example.demo;

public class Student {
    private int rollNo;
    private String name;
    private Result resobj; 

    public Student() {}

    public void setRollNo(int rollNo) { this.rollNo = rollNo; }
    public void setName(String name) { this.name = name; }
    public void setResobj(Result resobj) { this.resobj = resobj; }

    @Override
    public String toString() {
        return "Student [RollNo=" + rollNo + ", Name=" + name + ", " + resobj + "]";
    }
}