package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:27
 */

@Repository

public interface ClientRepository {
    public void saveOrUpdateClient(Client client);

    public List<Client> getClientsByName(String clientName);

    public void deleteClientsWithoutOrders(int minutes);

    public Client getClientById(int id);

    public List<Client> getAllClients();
    public void deleteClient(Client client) ;
}
