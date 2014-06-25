package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.DAO.impl.EventRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();

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
    public void addEvent(Event event) throws SQLException{
        eventRepository.addEvent(event);
    }
    public void updateEvent(Event event) throws SQLException{
        eventRepository.updateEvent(event);
    }
}
