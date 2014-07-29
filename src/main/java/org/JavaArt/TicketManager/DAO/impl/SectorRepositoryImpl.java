package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.TicketService;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 11:01
 */

@Repository

public class SectorRepositoryImpl implements SectorRepository {
    private TicketService ticketService = TicketService.getInstance();

    @Override
    public void addSector(Sector sector) {
        if (sector == null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sector.setOperator(operator);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sector);
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateSector(Sector sector) {
        if (sector == null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sector.setOperator(operator);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sector);
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public Sector getSectorById(int id) {
        Session session = null;
        Sector sector = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            sector = (Sector) session.get(Sector.class, id);

            sector = (Sector) session.createCriteria(Sector.class).
                    add(Restrictions.eq("isDeleted", false)).
                    add(Restrictions.eq("id", id)).uniqueResult();

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sector;
    }

    @Override
    public void deleteSector(Sector sector) {
        if (sector == null) return;
        sector.setDeleted(true);
        List<Ticket> tickets = ticketService.getAllTicketsBySector(sector);
        if (tickets.size() != 0) {
            ticketService.deleteTickets(tickets);
        }
        updateSector(sector);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sector> getSectorsByEvent(Event event) {
        Session session = null;
        List<Sector> sectors = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Sector where event =" + event.getId() + " and isDeleted = false ORDER BY id");
            sectors = query.list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectors;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sector> getSectorsByEventOrderPrice(Event event) {
        if (event == null) return null;
        Session session = null;
        List<Sector> sectors = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Sector where event =" + event.getId() + " and isDeleted = false ORDER BY price");
            sectors = query.list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectors;
    }

    @Override
    public boolean busySector(Sector sector) {
        if (sector == null) return false;
        int freeTickets = ticketService.getFreeTicketsAmountBySector(sector);
        int dif = sector.getMaxRows() * sector.getMaxSeats() - freeTickets;
        if (dif == 0) {
            return false;
        }
        return true;
    }

}
