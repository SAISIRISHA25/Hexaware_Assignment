package com.example.cycle;



import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestLifeCycleAnnotation1 {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Employee1 emp1 = context.getBean("emp", Employee1.class);

        System.out.println(emp1);

        context.registerShutdownHook();
    }
}