package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;

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

    public List<Event> getFutureEvents() throws SQLException {
        List<Event> events = eventRepository.getFutureEvents();
        return events;
    }

    public Event getEventById(int id) throws SQLException {
        Event event = eventRepository.getEventById(id);
        return event;
    }

    public List<Sector> getSectorsByEvent(Event event){
        List<Sector> sectors = sectorRepository.getSectorsByEvent(event);
        return sectors;
    }

    public Sector getSectorById(int id) throws SQLException {
        Sector sector = sectorRepository.getSectorById(id);
        return sector;
    }

    public int getFreeTicketsAmountBySector(Sector sector) throws SQLException{
        return ticketRepository.getFreeTicketsAmountBySector(sector);
    }

    public int getFreeTicketsAmountBySectorRow(Sector sector, int row) throws SQLException{
        return ticketRepository.getFreeTicketsAmountBySectorRow(sector, row);
    }
    public void deleteTickets(List<Ticket> tickets) throws SQLException {
        ticketRepository.deleteTickets(tickets);
    }

    public boolean isPlaceFree(Sector sector, int row, int seat) throws SQLException{
        return ticketRepository.isPlaceFree(sector, row, seat);
    }

    public void addTicket(Ticket ticket) throws SQLException{
        ticketRepository.addTicket(ticket);
    }

    public void addEvent(Event event) throws SQLException{
        eventRepository.addEvent(event);
    }
    public void updateEvent(Event event) throws SQLException{
        eventRepository.updateEvent(event);
    }

}
