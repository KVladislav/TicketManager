package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketService {
//    private static TicketService ticketService;
@Autowired
    private TicketRepository ticketRepository;// = new TicketRepositoryImpl();

    public TicketService() {
    }

//    public static TicketService getInstance() {
//        if (ticketService == null) {
//            ticketService = new TicketService();
//        }
//        return ticketService;
//    }

    public void deleteExpiredBookedTickets() {
        ticketRepository.deleteExpiredBookedTickets();
    }

    public int getTicketsAmountByClient(Client client) {
        return ticketRepository.getTicketsAmountByClient(client);

    }

    public List<Ticket> getTicketsByClient(Client client) {
        return ticketRepository.getTicketsByClient(client);
    }

    public List<Ticket> getBoughtTicketsByClient(Client client) {
        return ticketRepository.getBoughtTicketsByClient(client);
    }


    public int getFreeTicketsAmountBySector(Sector sector) {
        return ticketRepository.getFreeTicketsAmountBySector(sector);
    }

    public int getFreeTicketsAmountBySectorRow(Sector sector, int row) {
        return ticketRepository.getFreeTicketsAmountBySectorRow(sector, row);
    }

    public void deleteTickets(List<Ticket> tickets) {
        ticketRepository.deleteTickets(tickets);
    }

    public int isPlaceFree(Sector sector, int row, int seat) {
        return ticketRepository.isPlaceFree(sector, row, seat);
    }

    public void addTicket(Ticket ticket) {
        ticketRepository.saveOrUpdateTicket(ticket);
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.getTicketById(ticketId);
    }

    public void deleteTicket(Ticket ticket) {
        ticketRepository.deleteTicket(ticket);
    }

    public void saveOrUpdateTicket(Ticket ticket) {
        ticketRepository.saveOrUpdateTicket(ticket);
    }

    public void saveOrUpdateTickets(List<Ticket> tickets) {
        ticketRepository.saveOrUpdateTickets(tickets);
    }

    public void deleteNonConfirmedTickets(int minutes) {
        ticketRepository.deleteNonConfirmedTickets(minutes);
    }

    public List<Ticket> getAllTicketsBySector(Sector sector) {
        return ticketRepository.getAllTicketsBySector(sector);
    }

    public List<Ticket> getOrderTicketsByOperator(Operator operator) {
        return ticketRepository.getOrderTicketsByOperator(operator);
    }
}


