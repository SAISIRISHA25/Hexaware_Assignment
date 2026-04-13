package com.example.practice;

import org.springframework.beans.factory.annotation.Autowired;

public class Collage {
    int c_id;
    public Collage(int c_id) {
        this.c_id = c_id;
    }
    public Collage(int c_id, String c_name, Student s1) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.s1 = s1;
    }
    String c_name;
    @Override
    public String toString() {
        return "Collage [c_id=" + c_id + ", c_name=" + c_name + "]";
    }
    @Autowired
    Student s1;
    public int getC_id() {
        return c_id;
    }
    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
    public String getC_name() {
        return c_name;
    }
    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
    public Student getS1() {
        return s1;
    }
    public void setS1(Student s1) {
        this.s1 = s1;
    }
    public Collage() {
    }
    

}
