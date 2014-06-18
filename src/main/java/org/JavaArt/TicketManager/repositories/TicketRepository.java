package org.JavaArt.TicketManager.repositories;


import org.JavaArt.TicketManager.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository <Ticket, Long>{
}
