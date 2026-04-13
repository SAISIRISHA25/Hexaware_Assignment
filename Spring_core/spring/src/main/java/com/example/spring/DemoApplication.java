package com.example.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annot = new AnnotationConfigApplicationContext(HospitalConfig.class);

        Doctor d1 = (Doctor) annot.getBean("doc1");
        System.out.println(d1);

        Hospital h1 = (Hospital) annot.getBean("hosp1");
        System.out.println(h1);

        System.out.println("Doctor Name in Hospital: " + h1.getDoctorObj().getName());
        System.out.println("Specialization: " + h1.getDoctorObj().getSpecialization());
    }
}