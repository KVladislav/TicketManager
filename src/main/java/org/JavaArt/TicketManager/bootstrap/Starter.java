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
        SectorDefaultsRepository sectorDefaultsRepository = new SectorDefaultsRepositoryImpl();

//        Event event = new Event();
//        event.setDate(new Date());
//        event.setDescription("Черноморец - Шахтер");
//        eventRepository.addEvent(event);
//        Sector sector;
//        for (int i = 1; i < 28; i++) {
//            sector = new Sector();
//            sector.setEvent(event);
//            sector.setName("" + i);
//            sector.setMaxRows(20);
//            sector.setMaxSeats(50);
//            sector.setPrice((double) (50 + (int) (Math.random() * 151)));
//            sectorRepository.addSector(sector);
//        }
//
//        SectorDefaults sectorDefaults = new SectorDefaults();
//        sectorDefaults.setSectorName("1");
//        sectorDefaultsRepository.addSectorDefaults(sectorDefaults);



//        Ticket ticket = new Ticket();
//        ticket.setReserved(true);
//        ticket.setDeleted(false);
//        ticket.setConfirmed(true);
        ticketRepository.deleteExpiredBookedTickets();
        HibernateUtil.shutdown();


    }
}
