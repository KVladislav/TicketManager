package org.JavaArt.TicketManager.bootstrap;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.utils.HibernateUtil;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 10.06.2014
 * Time: 15:13
 */
public class Starter {
    public static void main(String[] args) throws SQLException {
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        SectorRepository sectorRepository = new SectorRepositoryImpl();
        EventRepository eventRepository = new EventRepositoryImpl();
        ClientRepository clientRepository = new ClientRepositoryImpl();
        OperatorRepository operatorRepository = new OperatorRepositoryImpl();

        Operator operator = new Operator();
        operator.setName("User");
        operator.setSurname("Userovich");
        operator.setLogin("user");
        operator.setPassword("asdfqwerty");
        operator.setDescription("first user");
        operatorRepository.addOperator(operator);

        Client client = new Client();
        client.setName("Client");
        client.setDescription("");
        clientRepository.addClient(client);


        Event event = new Event();
        event.setDate(new Date());
        event.setDescription("Chernomorec Zaporozhe");
        event.setOperator(operator);
        eventRepository.addEvent(event);
        Sector sector=null;
        for (int i = 0; i < 28; i++) {
            sector = new Sector();
            sector.setEvent(event);
            sector.setName("Sector " + i);
            sector.setMaxRows(20);
            sector.setMaxSeats(50);
            sector.setPrice(Double.valueOf(60 + i));
            sector.setOperator(operator);
            Ticket ticket = new Ticket();
            ticket.setRow(10);
            ticket.setSeat(15);
            ticket.setSector(sector);
            ticket.setOperator(operator);
            ticket.setClient(client);
            ticket.setReserved(true);
            sectorRepository.addSector(sector);
            ticketRepository.addTicket(ticket);}
        System.out.println(eventRepository.getEventById(event.getId()).getDescription());

        HibernateUtil.shutdown();

    }
}
