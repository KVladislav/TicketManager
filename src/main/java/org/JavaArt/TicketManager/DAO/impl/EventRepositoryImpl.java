package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository

public class EventRepositoryImpl implements EventRepository {
    @Override
    public void addEvent(Event event) {
        if (event==null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setOperator(operator);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateEvent(Event event) {
        if (event==null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setOperator(operator);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public Event getEventById(int id) {
        Session session = null;
        Event event = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            event = (Event) session.get(Event.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }

        }
        return event;


    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() {
        Session session = null;
        List<Event> events = null;//new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            events = session.createCriteria(Event.class)
                    .add(Restrictions.eq("isDeleted", new Boolean("false")))
                    .addOrder(Order.asc("date")).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return events;

    }

    @Override
    public List<Event> getFutureEvents() {
        Session session = null;
        List<Event> events = new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery ("FROM Event WHERE isDeleted = false and date > :currientDate order by date");
            query.setTimestamp("currientDate", new Date());
            events = (List<Event>) query.list();

            }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session != null && session.isOpen())  session.close();
        }
        return events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventByDate(Date inputDate) {
        if (inputDate==null) return new ArrayList<>();
        Session session = null;
        List<Event> events = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            events = session.createCriteria(Event.class)
                    .add(Restrictions.eq("isDeleted", new Boolean("false")))
                    .add(Restrictions.eq("date", inputDate)).list();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventByDateFromEvent(Date inputDate, Event event) {
        if (inputDate==null || event==null) return new ArrayList<>();
        Session session = null;
        List<Event> events = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            events = session.createCriteria(Event.class)
                    .add(Restrictions.eq("isDeleted", new Boolean("false")))
                    .add(Restrictions.ne("id", event.getId()))
                    .add(Restrictions.eq("date", inputDate)).list();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getFutureBookableEvents() {
        Session session = null;
        List<Event> events = new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery ("FROM Event WHERE isDeleted = false and bookingTimeOut > :currientDate order by date");
            query.setTimestamp("currientDate", new Date());
            events = (List<Event>) query.list();

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session != null && session.isOpen())  session.close();
        }
        return events;
    }

//    @Override
//    public void deleteEvent(Event event) {
//        Session session = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(event);
//            session.getTransaction().commit();
//            session.flush();
//        }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
//        }
//        finally {
//            if (session!=null && session.isOpen()) {
//                session.close();
//            }
//        }
//
//    }


}
