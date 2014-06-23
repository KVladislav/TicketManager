package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 11:00
 */

@Repository

public class EventRepositoryImpl implements EventRepository {
    @Override
    public void addEvent(Event event) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateEvent(Event event) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public Event getEventById(int id) throws SQLException {
        Session session = null;
        Event event = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            event = (Event) session.get(Event.class, id);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
            return event;
        }


    }

    @Override
    public List<Event> getAllEvents() throws SQLException {
        Session session = null;
        List<Event> events = null;//new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            events = session.createCriteria(Event.class).addOrder( Order.asc("id") ).list();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return events;

    }

    @Override
    public List<Event> getFutureEvents() throws SQLException {
        Session session = null;
        List<Event> events = new ArrayList<Event>();
        Date date = new Date();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Event where eventDate >=:date");
            events = query.list();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return events;
    }

    @Override
    public void deleteEvent(Event event) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(event);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }

    }
}
