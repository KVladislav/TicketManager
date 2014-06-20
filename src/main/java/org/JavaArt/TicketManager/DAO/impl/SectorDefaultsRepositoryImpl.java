package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorDefaultsRepository;
import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.sql.SQLException;
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
    public void addSectorDefaults(SectorDefaults sectorDefaults) throws SQLException {
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
    public void updateSectorDefaults(SectorDefaults sectorDefaults) throws SQLException {
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

    @Override
    public List<SectorDefaults> getAllSectorDefaults() throws SQLException {
        Session session = null;
        List<SectorDefaults> zoneDefaults = null;//new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            zoneDefaults = session.createCriteria(SectorDefaults.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return zoneDefaults;
    }

    @Override
    public void deleteSectorDefaults(SectorDefaults sectorDefaults) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(sectorDefaults);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }
}