package com.example.inner;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestNestedBean {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("inner.xml");

        Department dept = (Department) context.getBean("dept1");

        System.out.println(dept);
    }
}