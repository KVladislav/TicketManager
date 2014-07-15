package org.JavaArt.TicketManager.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.PreDestroy;


/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 0:05                                                 x
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory==null) {
            sessionFactory = buildSessionFactory();
        }

        return sessionFactory;
    }

    @PreDestroy
    public static void shutdown() {
        getSessionFactory().close();
        System.out.println("Perform Hibernate ShutDown!!!");
    }
}