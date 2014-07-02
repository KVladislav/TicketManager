package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.ClientRepository;
import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:59
 */
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @Override
    public void saveOrUpdateClient(Client client)  {

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(client);
            session.getTransaction().commit();
            session.flush();
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
    public List<Client> getClientsByName(String clientName) {
        Session session = null;
        List<Client> clients = null;//new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createCriteria(Client.class).add(Restrictions.eq("isDeleted", new Boolean("false"))).add(Restrictions.ilike("name", "%"+clientName + "%")).list();

//                    session.createQuery("FROM Client, Ticket, Sector, Event WHERE Ticket.sector = Sector AND Client = Ticket.client AND Sector.event = Event AND Event.date > ? order by name").setTimestamp(0, new Date()).list();

//                    session.createCriteria(Client.class)
//                    .add(Restrictions.eq("isDeleted", new Boolean("false")))
//                    .addOrder(Order.asc("name")).list();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return clients;
    }

    @Override
    public Client getClientById(int id)  {
        Session session = null;
        Client client = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            client = (Client) session.get(Client.class, id);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return client;
    }



    @Override
    public List<Client> getAllClients()  {
        Session session = null;
        List<Client> clients = null;//new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createCriteria(Client.class).list();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        }
        finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
        return clients;
    }

//    @Override
//    public void deleteClient(Client client)  {
//        Session session = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(client);
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
