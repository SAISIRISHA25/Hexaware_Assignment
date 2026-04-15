package com.example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HospitalConfig {

    @Bean(name="doc1")
    public Doctor getDoctor() {
        Doctor d = new Doctor();
        d.setName("Dr. Sharma");
        d.setSpecialization("Cardiology");
        return d;
    }

    @Bean(name="hosp1")
    public Hospital getHospital() {
        Hospital h = new Hospital();
        h.setH_id(501);
        h.setH_name("City Life Hospital");
        return h;
    }
}