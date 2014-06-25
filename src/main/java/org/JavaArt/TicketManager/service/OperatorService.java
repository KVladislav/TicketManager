package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.OperatorRepository;
import org.JavaArt.TicketManager.DAO.impl.OperatorRepositoryImpl;
import org.springframework.stereotype.Service;


@Service
public class OperatorService {
    private OperatorRepository operatorRepository = new OperatorRepositoryImpl();

}
