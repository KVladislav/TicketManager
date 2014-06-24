package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.*;
import org.JavaArt.TicketManager.DAO.impl.*;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Sector> getSectorsByEventOrderPrice(Event event){
        List<Sector> sectors = sectorRepository.getSectorsByEventOrderPrice(event);
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

    public List<String> getLegenda (List<Sector> sector) throws SQLException{
        List<Double> sortByPrice = new ArrayList();
        List <String> legenda = new ArrayList();
        int index=1;
        sortByPrice.add(0,sector.get(0).getPrice());
        for(int i=1;i< sector.size();i++){
            if (sector.get(i).getPrice()>sector.get(i-1).getPrice()) {
                sortByPrice.add(index,sector.get(i).getPrice());
                index++;
            }
        }
        StringBuffer buf = new StringBuffer(100);
        for (int j=0;j<sortByPrice.size();j++){
            buf.append(sortByPrice.get(j)).append(" грн.  Sector ");
            for (int i=0;i<sector.size(); i++){
                if((double)sector.get(i).getPrice()==(double)sortByPrice.get(j)) {
                    buf.append(sector.get(i).getName()).append(", ");
                }
            }
            legenda.add(j, buf.toString());
            buf.delete(0,99);
        }
        return legenda;
    }
}
