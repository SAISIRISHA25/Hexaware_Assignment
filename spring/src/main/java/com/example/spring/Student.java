package com.example.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Student {
    private int rollno;
    private String name;
    private List<String> subjects = new ArrayList<>(); 
    private Set<String> hobbies = new HashSet<>();
    private Map<String, Integer> marksAndSubjects=new HashMap<>();

    public int getRollno() { return rollno; }
    public void setRollno(int rollno) { this.rollno = rollno; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public Set<String> getHobbies() { return hobbies; }
    public void setHobbies(Set<String> hobbies) { this.hobbies = hobbies; }

    public Map<String, Integer> getMarksAndSubjects() { return marksAndSubjects; }
    public void setMarksAndSubjects(Map<String, Integer> marksAndSubjects) { 
        this.marksAndSubjects = marksAndSubjects; 
    }

    @Override
    public String toString() {
        return "Student Detail:\n" +
               "Roll No: " + rollno + "\n" +
               "Name   : " + name + "\n" +
               "Subjects: " + subjects + "\n" +
               "Hobbies : " + hobbies + "\n" +
               "Marks   : " + marksAndSubjects;
    }
}