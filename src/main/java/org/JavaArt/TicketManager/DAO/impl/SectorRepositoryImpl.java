package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class SectorRepositoryImpl implements SectorRepository {
    @Autowired
    HibernateUtil hibernateUtil;


    @Override
    public void addSector(Sector sector) {
        if (sector == null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sector.setOperator(operator);
        Session session = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
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
            session = hibernateUtil.getSessionFactory().openSession();
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
    public Sector getSectorById(Long id) {
        Session session = null;
        Sector sector = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Sector> getSectorsByEvent(Event event) {
        Session session = null;
        List<Sector> sectors = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
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
            session = hibernateUtil.getSessionFactory().openSession();
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

    public void deleteSector(Sector sector) {
        if (sector == null) return;
        sector.setDeleted(true);
        updateSector(sector);
    }


}
