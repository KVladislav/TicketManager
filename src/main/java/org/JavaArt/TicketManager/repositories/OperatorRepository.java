package org.JavaArt.TicketManager.repositories;

import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.data.repository.CrudRepository;

public interface OperatorRepository extends CrudRepository <Operator, Long>{
     Operator findOneByLogin(String login);
}
