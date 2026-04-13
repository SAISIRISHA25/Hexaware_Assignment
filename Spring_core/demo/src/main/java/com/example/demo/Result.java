package com.example.demo;

public class Result {
    private String grade;
    private double percentage;

    public String getGrade() {
        return grade;
    }

    public double getPercentage() {
        return percentage;
    }

    public Result() {}

    public void setGrade(String grade) { this.grade = grade; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    @Override
    public String toString() {
        return "Result [Grade=" + grade + ", Percentage=" + percentage + "%]";
    }
}