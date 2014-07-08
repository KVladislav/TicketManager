package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 07.06.2014
 * Time: 22:09
 */

@Repository

public interface OperatorRepository {
    public void addOperator(Operator operator);

    public void updateOperator(Operator operator);

    public Operator getOperatorById(int id);

    public List<Operator> getAllOperators();
//    public void deleteOperator(Operator operator);
}
