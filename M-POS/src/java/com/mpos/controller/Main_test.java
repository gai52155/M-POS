/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpos.controller;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.mpos.model.ModelLocation;
import org.hibernate.cfg.AnnotationConfiguration;
/**
 *
 * @author Katawut
 */
public class Main_test {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT * FROM mpos_location_demo";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(ModelLocation.class);
            List location = query.list();
            if (location.isEmpty()) {
                System.out.println("No data");
            }
            for (Iterator iterator
                    = location.iterator(); iterator.hasNext();) {
                ModelLocation place = (ModelLocation) iterator.next();
                System.out.print("ID : " + place.getlocation_id());
                System.out.print("name : " + place.getname());
                System.out.println("lat : " + place.getlat());
                System.out.print("lng : " + place.getlng());
                System.out.print("iso : " + place.getiso());
                System.out.println("province : " + place.getprovince());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}