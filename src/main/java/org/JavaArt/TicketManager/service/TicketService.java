package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.JavaArt.TicketManager.DAO.impl.TicketRepositoryImpl;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class TicketService {
    private TicketRepository ticketRepository = new TicketRepositoryImpl();

    public int getFreeTicketsAmountBySector(Sector sector) throws SQLException {
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

    public Ticket getTicketById(int ticketId) throws SQLException {
        return ticketRepository.getTicketById(ticketId);
    }

    public void deleteTicket(Ticket ticket) throws SQLException {
        ticketRepository.deleteTicket(ticket);
    }

    public void updateTickets(List<Ticket> tickets) throws SQLException {
        ticketRepository.updateTickets(tickets);
    }

}

