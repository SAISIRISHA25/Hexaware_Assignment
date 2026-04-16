package com.library.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.library.entity.Book;
import com.library.entity.Member;

public class HibernateUtil {

    private SessionFactory factory;

    public HibernateUtil() {
        try {
            this.factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(Member.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error initializing SessionFactory: " + e.getMessage());
        }
    }

    public SessionFactory getSessionFactory() {
        return factory;
    }

    public void closeFactory() {
        if (factory != null) {
            factory.close();
        }
    }
}