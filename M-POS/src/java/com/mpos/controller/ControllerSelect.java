/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpos.controller;

import com.mpos.model.ModelLocation;
import com.mpos.model.ModelAbbrevilation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Katawut
 */
public class ControllerSelect {

    public static SessionFactory factory = HibernateUtil.getSessionFactory();

    public static String getISO(String country) {
        String ISO = "";
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT * FROM abbreviation WHERE Country = '" + country + "'";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(ModelAbbrevilation.class);
            List location = query.list();
            if (location.isEmpty()) {
                ISO = "NULL";
            } else {
                for (Iterator iterator
                        = location.iterator(); iterator.hasNext();) {
                    ModelAbbrevilation place = (ModelAbbrevilation) iterator.next();

                    ISO = place.getISO();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return ISO;
    }

    public static ArrayList<String> selector(String country) {

        ArrayList<String> data = new ArrayList<String>();
        String ISO = getISO(country);
        System.out.println(ISO);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT * FROM location WHERE ISO = '" + ISO + "'";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(ModelLocation.class);
            List location = query.list();
            if (location.isEmpty()) {
                data.add("NULL");
            } else {
                for (Iterator iterator
                        = location.iterator(); iterator.hasNext();) {
                    ModelLocation place = (ModelLocation) iterator.next();

                    data.add(place.getName());
                    data.add(String.valueOf(place.getLat()));
                    data.add(String.valueOf(place.getLng()));
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            factory.close(); // CLOSE CONNECTION
        }
        return data;
    }
}
