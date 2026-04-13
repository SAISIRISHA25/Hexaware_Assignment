package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoApplication {

    public static void main(String[] args) {

        ApplicationContext context
                = new ClassPathXmlApplicationContext("beans.xml");

        //Employee e1 = (Employee) context.getBean("emp1");
        //Employee e2 = (Employee) context.getBean("emp2");

        //System.out.println(e1.toString());
        //System.out.println(e2.toString());

        // Department d1 = (Department) context.getBean("dept1");

        // System.out.println(d1.toString());
        // Employee e12=d1.getEmpobj();
        // System.out.println(e12.toString());

        // Student s1 = (Student) context.getBean("stud1");
        // System.out.println(s1.toString());
        // Result r1 = (Result) context.getBean("res1");
        // System.out.println(r1.toString());

Book myBook = (Book) context.getBean("book1");
System.out.println(myBook.toString());

Author myAuthor = (Author) context.getBean("author1");
System.out.println(myAuthor.toString());
    }
}
