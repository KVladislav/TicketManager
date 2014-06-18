package org.JavaArt.TicketManager.repositories;


import org.JavaArt.TicketManager.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
