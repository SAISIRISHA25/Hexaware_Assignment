package com.example.mba.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.mba.entity.Member;
import com.example.mba.entity.Movie;

public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure("hiber.cfg.xml")
                    .addAnnotatedClass(Movie.class)
                    .addAnnotatedClass(Member.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SessionFactory creation failed");
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
