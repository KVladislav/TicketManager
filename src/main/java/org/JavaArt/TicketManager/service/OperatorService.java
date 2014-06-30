package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.DAO.impl.OperatorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class OperatorService {
    private OperatorRepository operatorRepository = new OperatorRepositoryImpl();

    public List<Operator> getAllOperators() throws SQLException {
        List<Operator> operators = operatorRepository.getAllOperators();
        return operators;
    }
    
    public void deleteOperator(int id) throws SQLException {
        Operator operator = operatorRepository.getOperatorById(id);
        operator.setDeleted(true);
        operatorRepository.updateOperator(operator);
    }
    
    public void editOperator(Operator operator) throws SQLException {
        operatorRepository.updateOperator(operator);
    }
    
    public void addOperator(Operator operator) throws SQLException {
        operatorRepository.addOperator(operator);
    }

    public Operator getOperatorById(int id) throws SQLException {
        Operator operator = operatorRepository.getOperatorById(id);
        return operator;
    }

}
