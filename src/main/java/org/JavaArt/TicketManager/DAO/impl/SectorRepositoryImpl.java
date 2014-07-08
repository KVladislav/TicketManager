package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
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

public class SectorRepositoryImpl implements SectorRepository {
    @Override
    public void addSector(Sector sector) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sector);
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
    public void updateSector(Sector sector) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sector);
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
    public Sector getSectorById(int id) {
        Session session = null;
        Sector sector = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sector = (Sector) session.get(Sector.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return sector;
        }

    }

//    @Override
//    public void deleteSector(Sector sector) {
//        Session session = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(sector);
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

    @Override
    public List<Sector> getSectorsByEvent(Event event) {
        Session session = null;
        List<Sector> sectors = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Sector where event =" + event.getId() + " and isDeleted = false ORDER BY id");
            sectors = query.list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectors;
    }

    @Override
    public List<Sector> getSectorsByEventOrderPrice(Event event) {
        Session session = null;
        List<Sector> sectors = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Sector where event =" + event.getId() + " and isDeleted = false ORDER BY price");
            sectors = query.list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sectors;
    }
}
