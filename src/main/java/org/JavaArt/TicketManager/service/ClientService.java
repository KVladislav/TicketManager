package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.ClientRepository;
import org.JavaArt.TicketManager.DAO.impl.ClientRepositoryImpl;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Service;


@Service
public class ClientService {
    private ClientRepository clientRepository = new ClientRepositoryImpl();
}
