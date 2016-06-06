package com.mpos.controller;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

   

public class HibernateUtil {
    private static SessionFactory factory;
    public static SessionFactory getSessionFactory() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }
        return factory;
    }
}
