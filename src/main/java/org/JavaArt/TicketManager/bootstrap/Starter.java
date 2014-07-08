package org.JavaArt.TicketManager.bootstrap;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.utils.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 10.06.2014
 * Time: 15:13
 */
public class Starter {
    public static void main(String[] args) {
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        SectorRepository sectorRepository = new SectorRepositoryImpl();
        EventRepository eventRepository = new EventRepositoryImpl();
        ClientRepository clientRepository = new ClientRepositoryImpl();
        OperatorRepository operatorRepository = new OperatorRepositoryImpl();


//        Event event = new Event();
//        event.setDate(new Date());
//        event.setDescription("Ла-ла-ла-ла-ла-ла");
//        eventRepository.addEvent(event);
//        Sector sector=null;
//        for (int i = 1; i < 28; i++) {
//            sector = new Sector();
//            sector.setEvent(event);
//            sector.setName(""+i);
//            sector.setMaxRows(20);
//            sector.setMaxSeats(50);
//            sector.setPrice((double)(50 + (int)(Math.random() * 151)));
//            Ticket ticket = new Ticket();
//            ticket.setRow(10);
//            ticket.setSeat(15);
//            ticket.setSector(sector);
//            ticket.setReserved(true);
//            sectorRepository.addSector(sector);
//            ticketRepository.saveOrUpdateTicket(ticket);}
//        System.out.println(eventRepository.getEventById(event.getId()).getDescription());
//
//        Ticket ticket =new Ticket();
//        ticketRepository.saveOrUpdateTicket(ticket);
//        ticketRepository.deleteTicket(ticket);
//        System.out.println(ticket.getId());
//        ticketRepository.saveOrUpdateTicket(ticket);
        clientRepository.deleteClientsWithoutOrders(1);
        HibernateUtil.shutdown();

    }
}
