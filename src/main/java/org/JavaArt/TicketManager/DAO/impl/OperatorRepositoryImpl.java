package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.entities.Operator;
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
 * Date: 07.06.2014
 * Time: 22:14
 */

@Repository

public class OperatorRepositoryImpl implements OperatorRepository {

    @Override
    public void addOperator(Operator operator) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(operator);
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
    public void updateOperator(Operator operator) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(operator);
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
    public Operator getOperatorById(int id) {
        Session session = null;
        Operator operator = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operator = (Operator) session.get(Operator.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return operator;
    }

    @Override
    public List<Operator> getAllOperators() {
        Session session = null;
        List<Operator> operators = null;//new ArrayList<Event>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operators = session.createCriteria(Operator.class)
                    .add(Restrictions.eq("isDeleted", new Boolean("false")))
                    .addOrder(Order.asc("id")).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return operators;
    }

//    @Override
//    public void deleteOperator(Operator operator) {
//        Session session = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(operator);
//            session.getTransaction().commit();
//            session.flush();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
//        } finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
//
//    }
}