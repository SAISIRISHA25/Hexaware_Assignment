package com.example.spring;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        Item s1 = (Item) context.getBean("singletonItem");
        Item s2 = (Item) context.getBean("singletonItem");

        System.out.println("Singleton:");
        System.out.println(s1);
        System.out.println(s2);

        Item p1 = (Item) context.getBean("prototypeItem");
        Item p2 = (Item) context.getBean("prototypeItem");

        System.out.println("Prototype:");
        System.out.println(p1);
        System.out.println(p2);

       
    }

}