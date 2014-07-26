package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.DAO.impl.OperatorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperatorService implements UserDetailsService {
    @Autowired
    private OperatorRepository operatorRepository = new OperatorRepositoryImpl();

    public List<Operator> getAllOperators() {
        List<Operator> operators = operatorRepository.getAllOperators();
        return operators;
    }

    public void deleteOperator(int id) {
        Operator operator = operatorRepository.getOperatorById(id);
        operator.setDeleted(true);
        operatorRepository.updateOperator(operator);
    }

    public void updateOperator(Operator operator) {
        operatorRepository.updateOperator(operator);
    }

    public void addOperator(Operator operator) {
        operatorRepository.addOperator(operator);
    }

    public Operator getOperatorById(int id) {
        Operator operator = operatorRepository.getOperatorById(id);
        return operator;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return operatorRepository.getOperatorByUserName(userName);
    }

    public Operator getOperatorByLogin(String login) {
        return operatorRepository.getOperatorBylogin(login);
    }
}
