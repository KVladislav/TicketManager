package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.JavaArt.TicketManager.DAO.impl.TicketRepositoryImpl;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class TicketService {
    private TicketRepository ticketRepository = new TicketRepositoryImpl();
    private ExecutorService executorService;
    private static TicketService ticketService;

    private TicketService() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new ClearNonConfirmedTickets());
    }

    public static TicketService getInstance() {
        if  (ticketService==null) {
            ticketService = new TicketService();
        }
        return ticketService;
    }

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
        ticketRepository.saveOrUpdateTicket(ticket);
    }

    public Ticket getTicketById(int ticketId) throws SQLException {
        return ticketRepository.getTicketById(ticketId);
    }

    public void deleteTicket(Ticket ticket) throws SQLException {
        ticketRepository.deleteTicket(ticket);
    }

    public void saveOrUpdateTickets(List<Ticket> tickets) throws SQLException {
        ticketRepository.saveOrUpdateTickets(tickets);
    }

    private List<Ticket> getNonConfirmedTickets() throws SQLException {
        return ticketRepository.getNonConfirmedTickets();
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {

        }
    }

    private class ClearNonConfirmedTickets implements Runnable {

        public void run() {
            List<Ticket> tickets;
            List<Ticket> ticketForRemove =new ArrayList<>();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tickets = getNonConfirmedTickets();

                    if (tickets!=null && tickets.size()>0) {
                        for (Ticket ticket : tickets) {
                            if ((new Date().getTime() - ticket.getTimeStamp().getTime())> 60000*5 ) {
                                ticketForRemove.add(ticket);
                            }
                        }
                        if(ticketForRemove.size()>0) {
                            ticketRepository.deleteTickets(ticketForRemove);
                        }
                        ticketForRemove.clear();
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    TimeUnit.MINUTES.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform Thread Shutdown");
            }
        }
    }
}

