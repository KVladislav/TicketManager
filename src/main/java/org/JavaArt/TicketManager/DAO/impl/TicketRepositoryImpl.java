package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public void saveOrUpdateTicket(Ticket ticket) {
        if (ticket==null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticket.setOperator(operator);
        Session session = null;
        ticket.setOperator(operator);
        try {
            if (ticket.getId() == null) {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(ticket);
            } else {
                if (getTicketById(ticket.getId()) == null) {
                    ticket.setId(null);
                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    session.save(ticket);
                } else {
                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    session.update(ticket);
                }
            }
            session.getTransaction().commit();
            session.flush();
            session.clear();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void saveOrUpdateTickets(List<Ticket> tickets) {
        if (tickets == null) {
            return;
        }
        for (Ticket ticket : tickets) {
            saveOrUpdateTicket(ticket);
        }
    }

    @Override
    public Ticket getTicketById(int id) {
        Session session = null;
        Ticket ticket = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            ticket = (Ticket) session.get(Ticket.class, id);
            if ((ticket != null) && ticket.isDeleted() == true) ticket = null;
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return ticket;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticket> getAllTickets() {
        Session session = null;
        List<Ticket> tickets = null;// = new ArrayList<Ticket>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createCriteria(Ticket.class).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteNonConfirmedTickets(int minutes) {
        Session session = null;
        Date date = new Date();
        date.setTime(date.getTime() - 60000 * minutes);


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Ticket AS ticket WHERE ticket.isConfirmed = false AND ticket.timeStamp <= :endDate");
            query.setTimestamp("endDate", date);
            System.out.println("delete " + query.executeUpdate() + " non confirmed ticket(s)");
            tx.commit();

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "[TicketThread] " + e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    @Override
    public void deleteTicket(Ticket ticket) {
        if (ticket==null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticket.setOperator(operator);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            if (getTicketById(ticket.getId()) != null) {
                if (!ticket.isConfirmed()) {
                    session.beginTransaction();
                    session.delete(ticket);
                    session.getTransaction().commit();
                    session.flush();
                    session.clear();
                } else {
                    ticket.setDeleted(true);
                    saveOrUpdateTicket(ticket);
                }

            }

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticket> getTicketsByClient(Client client) {
        if (client==null) return new ArrayList<>();
        Session session = null;
        List<Ticket> tickets = null;
//        List<Ticket> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createCriteria(Ticket.class)
                    .add(Restrictions.eq("client", client))
                    .add(Restrictions.eq("isReserved", true))
                    .add(Restrictions.eq("isDeleted", false))
//                    .add(Restrictions.eq("isConfirmed", true))
                    .addOrder(Order.asc("sector")).list();
//            if (tickets != null) {
//                result = new ArrayList<>();
//                for (Ticket ticket : tickets) {
//                    if (ticket.getSector().getEvent().getDate().getTime() > new Date().getTime()) {
//                        result.add(ticket);
//                    }
//                }
//            }
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticket> getBoughtTicketsByClient(Client client) {
        if (client==null) return new ArrayList<>();
        Session session = null;
        List<Ticket> tickets = null;
//        List<Ticket> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createCriteria(Ticket.class)
                    .add(Restrictions.eq("client", client))
                    .add(Restrictions.eq("isDeleted", false))
                    .addOrder(Order.asc("sector")).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @Override
    public int getTicketsAmountByClient(Client client) {
        if  (client==null) return 0;
        Session session = null;
        int counter = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Query query = session.createQuery(String.format("select count (*) from Ticket where isReserved = true and isDeleted = false and isConfirmed = true and client =%d", client.getId()));
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return counter;

    }

    @Override
    public int getFreeTicketsAmountBySector(Sector sector) {
        if (sector==null) return 0;
        Session session = null;
        int counter = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//              counter = ((Long) session.createCriteria(Ticket.class).setProjection(Projections.rowCount()).add(Restrictions.eq("sector", sector)).list().get(0)).intValue();

            Query query = session.createQuery(String.format("select count (*) from Ticket where isDeleted = false and sector =%d", sector.getId()));
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sector.getMaxRows() * sector.getMaxSeats() - counter;
    }

    @Override
    public int getFreeTicketsAmountBySectorRow(Sector sector, int row) {
        Session session = null;
        int counter = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(String.format("select count (*) from Ticket where isDeleted = false and sector =%d and row=%d", sector.getId(), row));
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sector.getMaxSeats() - counter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticket> getAllTicketsBySectorAndRow(Sector sector, int row) {
        if (sector==null) return new ArrayList<>();
        Session session = null;
        List<Ticket> tickets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createQuery(String.format("from Ticket where isDeleted = false and sector =%d and row=%d", sector.getId(), row)).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @Override
    public int isPlaceFree(Sector sector, int row, int seat) {
        if (sector==null) return 3;
        Session session = null;
        Ticket ticket;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            ticket = (Ticket) session.createCriteria(Ticket.class)
                    .add(Restrictions.eq("sector", sector))
                    .add(Restrictions.eq("isDeleted", false))
                    .add(Restrictions.eq("row", row))
                    .add(Restrictions.eq("seat", seat))
                    .uniqueResult();
            if (ticket == null) return 0; //свободен
            if (!ticket.isConfirmed()) return 1; //в обработке
            if (ticket.isReserved()) return 2; //в резерве

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return 3; //куплен
    }

    @Override
    public void deleteTickets(List<Ticket> tickets) {
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                deleteTicket(ticket);
            }
        }

    }

    @Override
    public void deleteExpiredBookedTickets() {
        Session session = null;
        Date dateNow = new Date();


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
//            Query query = session.createQuery("DELETE FROM Client AS client WHERE client not in (SELECT distinct client FROM Ticket AS ticket LEFT JOIN ticket.client AS client) AND client.timeStamp <= :endDate");
//            Query query = session.createQuery("DELETE FROM Client AS client WHERE client not in (SELECT distinct ticket.client FROM Ticket AS ticket) AND client.timeStamp <= :endDate");
            Query query = session.createQuery("UPDATE FROM Ticket AS ticket set ticket.isDeleted=true WHERE ticket.sector in (select sector from Sector as sector where sector.event in (select event FROM Event as event WHERE event.bookingTimeOut <= :dateNow)) and ticket.isConfirmed=true and ticket.isReserved=true and ticket.isDeleted = false");
            query.setTimestamp("dateNow", dateNow);
            System.out.println("delete " + query.executeUpdate() + " expired booked ticket(s)");
            tx.commit();

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "ClientThread" + e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticket> getAllTicketsBySector(Sector sector) {
        if (sector==null) return new ArrayList<>();
        Session session = null;
        List<Ticket> tickets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createQuery(String.format("from Ticket where isDeleted = false and sector =%d", sector.getId())).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

}
