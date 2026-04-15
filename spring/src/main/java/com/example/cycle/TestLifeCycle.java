package com.example.cycle;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLifeCycle {

    public static void main(String[] args) {

        AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("cycle.xml");

        Employee emp = (Employee) context.getBean("emp");

        System.out.println(emp);

        context.registerShutdownHook();
    }
}