package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.ClientRepository;
import org.JavaArt.TicketManager.DAO.impl.ClientRepositoryImpl;
import org.JavaArt.TicketManager.entities.Client;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientService {
    private ClientRepository clientRepository = new ClientRepositoryImpl();
    private static ClientService clientService;

    private ClientService() {
    }

    public static ClientService getInstance() {
        if (clientService == null) {
            clientService = new ClientService();
        }
        return clientService;
    }

    public void saveOrUpdateClient(Client client) {
        clientRepository.saveOrUpdateClient(client);
    }

    public List<Client> getClientsByName(String clientName){
        return clientRepository.getClientsByName(clientName);
    }

}