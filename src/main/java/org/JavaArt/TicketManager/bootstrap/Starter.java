package org.JavaArt.TicketManager.bootstrap;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.utils.HibernateUtil;

import java.util.Date;


public class Starter {
    public static void main(String[] args) {
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        SectorRepository sectorRepository = new SectorRepositoryImpl();
        EventRepository eventRepository = new EventRepositoryImpl();
        ClientRepository clientRepository = new ClientRepositoryImpl();
        OperatorRepository operatorRepository = new OperatorRepositoryImpl();

        Operator operator = new Operator();
        operator.setName("User");
        operator.setSurname("Userovich");
        operator.setLogin("user");
        operator.setPassword("qwerty");
        operator.setDescription("first user");
        operator.setDeleted(false);
        operator.setTimeStamp(new Date());
        operatorRepository.addOperator(operator);

        Client client = new Client();
        client.setName("Client");
        client.setDescription("first");
        client.setTimeStamp(new Date());
        client.setDeleted(false);
        client.setOperator(operator);
        clientRepository.saveOrUpdateClient(client);


        Event event = new Event();
        event.setDate(new Date());
        event.setDescription("Черноморец Шахтер");
        event.setOperator(operator);
        event.setDeleted(false);
        event.setTimeStamp(new Date());
        event.setBookingTimeOut(30);
        eventRepository.addEvent(event);
        Sector sector=null;
        for (int i = 1; i < 28; i++) {
            sector = new Sector();
            sector.setEvent(event);
            sector.setName(""+i);
            sector.setMaxRows(20);
            sector.setMaxSeats(50);
            sector.setTimeStamp(new Date());
            sector.setDeleted(false);
            sector.setPrice((double)(50 + (int)(Math.random() * 101)));
            sector.setOperator(operator);
            if (i==26){
                sector.setName("A");
                sector.setMaxRows(10);
                sector.setMaxSeats(20);
                sector.setPrice((double)(200 + (int)(Math.random() * 301)));
            }
            if (i==27){
                sector.setName("D");
                sector.setMaxRows(10);
                sector.setMaxSeats(20);
                sector.setPrice((double)(200 + (int)(Math.random() * 301)));
            }

            Ticket ticket = new Ticket();
            ticket.setRow(10);
            ticket.setSeat(15);
            ticket.setSector(sector);
            ticket.setOperator(operator);
            ticket.setClient(client);
            ticket.setReserved(true);
            ticket.setDeleted(false);
            ticket.setConfirmed(true);
            ticket.setTimeStamp(new Date());
            sectorRepository.addSector(sector);
            ticketRepository.saveOrUpdateTicket(ticket);
        }
        System.out.println(eventRepository.getEventById(event.getId()).getDescription());

        /*Ticket ticket =new Ticket();
        ticketRepository.saveOrUpdateTicket(ticket);
        ticketRepository.deleteTicket(ticket);
        System.out.println(ticket.getId());
        ticketRepository.saveOrUpdateTicket(ticket);*/

        clientRepository.deleteClientsWithoutOrders(5);
        HibernateUtil.shutdown();

    }
}
