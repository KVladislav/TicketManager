package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.DAO.impl.OperatorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperatorService {
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

    public void editOperator(Operator operator) {
        operatorRepository.updateOperator(operator);
    }

    public void addOperator(Operator operator) {
        operatorRepository.addOperator(operator);
    }

    public Operator getOperatorById(int id) {
        Operator operator = operatorRepository.getOperatorById(id);
        return operator;
    }

}
