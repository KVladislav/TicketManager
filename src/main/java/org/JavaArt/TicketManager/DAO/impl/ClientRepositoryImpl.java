package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.ClientRepository;
import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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
    public void saveOrUpdateClient(Client client) {
        if (client==null) return;
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        client.setOperator(operator);

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(client);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Client> getClientsByName(String clientName) {
        if (clientName==null) return new ArrayList<>();
        Session session = null;
        List<Client> clients = null;//new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createCriteria(Client.class).add(Restrictions.eq("isDeleted", new Boolean("false"))).add(Restrictions.ilike("name", "%" + clientName + "%")).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clients;
    }

    @Override
    public Client getClientById(int id) {
        Session session = null;
        Client client = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            client = (Client) session.get(Client.class, id);
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return client;
    }


    @Override
    public List<Client> getAllClients() {
        Session session = null;
        List<Client> clients = null;//new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createCriteria(Client.class).list();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clients;
    }

    @Override
    public void deleteClientsWithoutOrders(int minutes) {
        Session session = null;
        Date date = new Date();
        date.setTime(date.getTime() - 60000 * minutes);


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
//            Query query = session.createQuery("DELETE FROM Client AS client WHERE client not in (SELECT distinct client FROM Ticket AS ticket LEFT JOIN ticket.client AS client) AND client.timeStamp <= :endDate");
            Query query = session.createQuery("DELETE FROM Client AS client WHERE client not in (SELECT distinct ticket.client FROM Ticket AS ticket) AND client.timeStamp <= :endDate");

            query.setTimestamp("endDate", date);
            System.out.println("delete " + query.executeUpdate() + " client(s) without tickets");
            tx.commit();

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "ClientThread" + e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

        @Override
    public void deleteClient(Client client)  {
            if (client==null) return;
            Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            client.setOperator(operator);
        client.setDeleted(true);
            saveOrUpdateClient(client);

    }
}
