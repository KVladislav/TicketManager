package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.DAO.impl.EventRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.getAllEvents();
        return events;
    }

    public List<Event> getFutureEvents() {
        List<Event> events = eventRepository.getFutureEvents();
        return events;
    }

    public Event getEventById(int id) {
        Event event = eventRepository.getEventById(id);
        return event;
    }

    public void addEvent(Event event) {
        eventRepository.addEvent(event);
    }

    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }

    public Event getEventByDate(Date date) {
        Event event = eventRepository.getEventByDate(date);
        return event;
    }
    public List<Event> getFutureBookableEvents() {
        return eventRepository.getFutureBookableEvents();
    }
}
