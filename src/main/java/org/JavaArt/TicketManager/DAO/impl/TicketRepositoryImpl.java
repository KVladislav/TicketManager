package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 11:00
 */

@Repository

public class TicketRepositoryImpl implements TicketRepository {

//    @Override
//    public void saveOrUpdateTicket(Ticket ticket) {
//        Session session = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.saveOrUpdateTicket(ticket);
//            session.getTransaction().commit();
//            session.flush();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
//        } finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
//    }

    @Override
    public void saveOrUpdateTicket(Ticket ticket) {
        Session session = null;
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
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTicket(Ticket ticket) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(ticket);
            session.getTransaction().commit();
            session.flush();
            session.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            updateTicket(ticket);
        }
    }

    @Override
    public void saveOrUpdateTickets(List<Ticket> tickets) {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        Session session = null;
        List<Ticket> tickets = null;// = new ArrayList<Ticket>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createCriteria(Ticket.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @Override
    public List<Ticket> getNonConfirmedTickets() {
        Session session = null;
        List<Ticket> tickets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createCriteria(Ticket.class).add(Restrictions.eq("isConfirmed", false)).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;


    }


    @Override
    public void deleteTicket(Ticket ticket) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            String hql;
//            if (ticket.getId()!=null) {
//                hql = "delete from Ticket where id = " + ticket.getId();
//            } else
//            {hql = String.format("delete from Ticket where sector = %d and row = %d and seat = %d", ticket.getSector().getId(), ticket.getRow(), ticket.getSeat());
//            }
//            System.out.println(hql);
//            session.createQuery(hql).executeUpdate();
            if (getTicketById(ticket.getId()) != null) {
                session.beginTransaction();
                session.delete(ticket);
                session.getTransaction().commit();
                session.flush();
                session.clear();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public int getFreeTicketsAmountBySector(Sector sector) {
        Session session = null;
        int counter = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//              counter = ((Long) session.createCriteria(Ticket.class).setProjection(Projections.rowCount()).add(Restrictions.eq("sector", sector)).list().get(0)).intValue();

            Query query = session.createQuery("select count (*) from Ticket where sector =" + sector.getId());
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
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
            Query query = session.createQuery("select count (*) from Ticket where sector =" + sector.getId() + " and row=" + row);
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sector.getMaxSeats() - counter;
    }

    @Override
    public List<Ticket> getAllTicketsBySectorAndRow(Sector sector, int row) {
        Session session = null;
        List<Ticket> tickets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tickets = session.createQuery("from Ticket where sector =" + sector.getId() + " and row=" + row).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tickets;
    }

    @Override
    public boolean isPlaceFree(Sector sector, int row, int seat) {
        Session session = null;
        int status = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("select count (*) from Ticket where sector =" + sector.getId() + " and row=" + row + " and seat=" + seat);
            status = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return status == 0;
    }

    @Override
    public void deleteTickets(List<Ticket> tickets) {
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                deleteTicket(ticket);
            }
        }

    }
}
