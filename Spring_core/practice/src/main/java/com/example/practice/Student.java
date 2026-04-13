package com.example.practice;

public class Student {
    int rollno;
    String name;
    @Override
    public String toString() {
        return "Student [rollno=" + rollno + ", name=" + name + ", marks=" + marks + "]";
    }
    public Student() {
    }
    double marks;
    public int getRollno() {
        return rollno;
    }
    public Student(int rollno, String name, double marks) {
        this.rollno = rollno;
        this.name = name;
        this.marks = marks;
    }
    public void setRollno(int rollno) {
        this.rollno = rollno;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMarks() {
        return marks;
    }
    public void setMarks(double marks) {
        this.marks = marks;
    }

    

}
