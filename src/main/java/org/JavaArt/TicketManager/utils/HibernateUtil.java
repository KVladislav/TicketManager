package org.JavaArt.TicketManager.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 0:05
 */

@Component
public class HibernateUtil {
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public HibernateUtil() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @PreDestroy
    public void shutdown() {
        sessionFactory.close();
    }
}