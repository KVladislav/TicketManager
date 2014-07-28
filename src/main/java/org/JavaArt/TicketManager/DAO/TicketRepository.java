package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:28
 */

@Repository

public interface TicketRepository {
    public void saveOrUpdateTicket(Ticket ticket);

    public List<Ticket> getTicketsByClient(Client client);

    public List<Ticket> getBoughtTicketsByClient(Client client);

    public int getTicketsAmountByClient(Client client);

    public void saveOrUpdateTickets(List<Ticket> tickets);

    public Ticket getTicketById(int id);

    public List<Ticket> getAllTickets();

    public void deleteTicket(Ticket ticket);

    public void deleteTickets(List<Ticket> tickets);

    public int getFreeTicketsAmountBySector(Sector sector);

    public int getFreeTicketsAmountBySectorRow(Sector sector, int row);

    public List<Ticket> getAllTicketsBySectorAndRow(Sector sector, int row);

    public int isPlaceFree(Sector sector, int row, int seat);

    public void deleteNonConfirmedTickets(int minutes);

    public void deleteExpiredBookedTickets();

    public List<Ticket> getAllTicketsBySector(Sector sector);

}
