package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.Event;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 18.06.2014
 * Time: 21:56
 */
public class Service {
    private TicketRepository ticketRepository = new TicketRepositoryImpl();
    private SectorRepository sectorRepository = new SectorRepositoryImpl();
    private EventRepository eventRepository = new EventRepositoryImpl();
    private ClientRepository clientRepository = new ClientRepositoryImpl();
    private OperatorRepository operatorRepository = new OperatorRepositoryImpl();

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = eventRepository.getAllEvents();
        return events;
    }


}
