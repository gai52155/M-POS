package com.mpos.controller;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.mpos.model.ModelLocation;

/**
 *
 * @author Katawut
 */
public class ControllerDelete {
    public static void deletor(int id){
    SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "DELETE FROM mpos_location_demo WHERE mposdemo_id = '"+id+"'";
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
