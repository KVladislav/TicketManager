package org.JavaArt.TicketManager.DAO.impl;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

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
        if (operator==null) return;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(operator);
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
    public void updateOperator(Operator operator) {
        if (operator==null) return;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(operator);
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
    public Operator getOperatorById(int id) {
        Session session = null;
        Operator operator = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operator = (Operator) session.get(Operator.class, id);
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return operator;
    }

    @SuppressWarnings("unchecked")
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
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
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

    private long countOperators() {
        Session session = null;
        int counter = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(String.format("select count (*) from Operator where isDeleted = false"));
            counter = ((Long) query.uniqueResult()).intValue();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return counter;
    }

    @Override
    public UserDetails getOperatorByUserName(String userName) throws UsernameNotFoundException {
        if (userName==null) throw new UsernameNotFoundException("Пользователь " + userName + " не найден!");
        if (countOperators()==0 && userName.equals("root")) {
            Operator operator = new Operator();
            operator.setLogin("root");
            operator.setPassword("root");
            return operator;

        }
        Session session = null;
        Operator operator = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operator = (Operator) session.createCriteria(Operator.class)
                    .add(Restrictions.eq("login", userName))
                    .add(Restrictions.eq("isDeleted", false))
                    .uniqueResult();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        if (operator == null) {
            throw new UsernameNotFoundException("Пользователь " + userName + " не найден!");
        }

//        String username = operator.getLogin();
//        String password = operator.getPassword();
//        GrantedAuthority auth = new GrantedAuthority() {
//            private static final long serialVersionUID = 1L;
//
//            public String getAuthority() {
//                return "ROLE_USER";
//            }
//        };
//        Set<GrantedAuthority> set = new HashSet<>();
//        set.add(auth);
//
//        user = new User(username, password, set);

        return operator;
    }

    @Override
    public Operator getOperatorBylogin(String login) {
        Session session = null;
        Operator operator = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            operator = (Operator) session.createCriteria(Operator.class)
                    .add(Restrictions.eq("login", login))
                    .add(Restrictions.eq("isDeleted", false))
                    .uniqueResult();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return operator;
    }

}



