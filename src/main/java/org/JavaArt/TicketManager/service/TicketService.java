package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.*;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.*;
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

    public Map<Integer,String> seatStatus(Sector sector, int row) throws SQLException{
        Map<Integer, String> seatsMap = new TreeMap<>();
        List <Ticket> ticket = ticketRepository.getAllTicketsBySectorAndRow(sector,row);
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            if (ticketRepository.isPlaceFree(sector, row, i)) seatsMap.put(i, "Статус:  в продаже");
            else {
                for (Ticket tic : ticket) {
                    if (tic.getSeat() == i) {
                        if (tic.getReserved()) seatsMap.put(i, "Статус: забронирован");
                        else seatsMap.put(i, "Статус: выкуплен");
                    }
                }
            }
        }
        return seatsMap;
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

