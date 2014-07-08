package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorDefaultsRepository;
import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 11:01
 */

@Repository

public class SectorDefaultsRepositoryImpl implements SectorDefaultsRepository {
    @Override
    public void addSectorDefaults(SectorDefaults sectorDefaults) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sectorDefaults);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateSectorDefaults(SectorDefaults sectorDefaults) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sectorDefaults);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public SectorDefaults getSectorDefaultsById(int id) {
        Session session = null;
        SectorDefaults sectorDefaults = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sectorDefaults = (SectorDefaults) session.get(SectorDefaults.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectorDefaults;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectorDefaults> getAllSectorDefaults() {
        Session session = null;
        List<SectorDefaults> sectorDefaults = null;//new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sectorDefaults = session.createCriteria(SectorDefaults.class).
                    add(Restrictions.eq("isDeleted", false)).
                    addOrder(Order.asc("sectorName")).list();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectorDefaults;
    }

    @Override
    public void deleteSectorDefaults(SectorDefaults sectorDefaults) {
        sectorDefaults.setDeleted(true);
        updateSectorDefaults(sectorDefaults);
    }
}