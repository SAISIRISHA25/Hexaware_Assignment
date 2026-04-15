package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class Hospital {
    private int h_id;
    private String h_name;

    @Autowired
    private Doctor doctorObj; 
    public Hospital() {}

    public int getH_id() { return h_id; }
    public void setH_id(int h_id) { this.h_id = h_id; }

    public String getH_name() { return h_name; }
    public void setH_name(String h_name) { this.h_name = h_name; }

    public Doctor getDoctorObj() { return doctorObj; }
    public void setDoctorObj(Doctor doctorObj) { this.doctorObj = doctorObj; }

    @Override
    public String toString() {
        return "Hospital [h_id=" + h_id + ", h_name=" + h_name + "]";
    }
}