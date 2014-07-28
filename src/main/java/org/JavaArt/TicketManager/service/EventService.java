package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.DAO.impl.EventRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class EventService {
    private static EventService eventService;
    private EventRepository eventRepository = new EventRepositoryImpl();

    // private EventService() {

    // }

    public static EventService getInstance() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }
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

    public List<Event> getEventsByDate(Date date) {
        List<Event> events = (List<Event>) eventRepository.getEventByDate(date);
        return events;
    }
    public List<Event> getFutureBookableEvents() {
        return eventRepository.getFutureBookableEvents();
    }

    public List<Event> getEventsByDateFromEvent(Date date, Event event) {
        List<Event> events = eventRepository.getEventByDateFromEvent(date, event);
        return events;
    }

    public void deleteEvent(Event event) {
        eventRepository.deleteEvent(event);
    }

    public boolean busyEvent(Event event) {
        return eventRepository.busyEvent(event);
    }

}
