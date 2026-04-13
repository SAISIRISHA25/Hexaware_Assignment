package com.example.practice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// import org.springframework.context.ApplicationContext;
// import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoApplication {

    public static void main(String[] args) {

        // ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // System.out.println("--- Car & Engine Inventory ---");

        // Car myCar = (Car) context.getBean("car1");
        // System.out.println(myCar.toString());

        // Engine myEngine = (Engine) context.getBean("eng1");
        // System.out.println(myEngine.toString());

        AnnotationConfigApplicationContext annot=new AnnotationConfigApplicationContext(StudentConfig.class);
        Student s1=(Student)annot.getBean("st1");
        System.out.println(s1);
        Collage c1=(Collage)annot.getBean("clg1");
        System.out.println(c1);

          System.out.println("Student Name: " + c1.getS1().getName());
        System.out.println("Marks: " + c1.getS1().getMarks());





    }
}