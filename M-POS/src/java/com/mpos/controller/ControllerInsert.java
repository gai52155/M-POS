/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpos.controller;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Windows 8
 */
public class ControllerInsert {
    public static void Insertor (){
    SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "INSERT INTO mpos_location_demo (name,lat,lng,iso,province,abbrevilation_id,country) VALUES ('qqq',11,11,'th','bangkok',1,'thailand')";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
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
    }
}
