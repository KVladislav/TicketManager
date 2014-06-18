package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 11:01
 */
public class SectorRepositoryImpl implements SectorRepository {
    @Override
    public void addSector(Sector sector) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sector);
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
    public void updateSector(Sector sector) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sector);
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
    public Sector getSectorById(int id) throws SQLException {
        Session session = null;
        Sector sector = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sector = (Sector) session.load(Sector.class, id);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
            return sector;
        }

    }

    @Override
    public List<Sector> getAllSectors() throws SQLException {
        Session session = null;
        List<Sector> sectors = null;// = new ArrayList<Zone>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sectors = session.createCriteria(Sector.class).list();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return sectors;    }

    @Override
    public void deleteSector(Sector sector) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(sector);
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