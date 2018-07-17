package com.revolut.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by kubus on 15/07/2018.
 */
public class HibernateUtil {
    private final SessionFactory sessionFactory;

    public HibernateUtil() {
        sessionFactory = initSessionFactory();
    }

    private SessionFactory initSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metaData =
                    new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metaData.getSessionFactoryBuilder().build();
        } catch (Throwable th) {
            System.err.println("SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public Session getSession() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession;
    }
}
