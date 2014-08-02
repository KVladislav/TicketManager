package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.DAO.impl.EventRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.*;


@Service
public class EventService {
    private static EventService eventService;
    private EventRepository eventRepository = new EventRepositoryImpl();
    public static final Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9-_#,:\\.\\s]{0,49}$");
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

    public Event getEventById(Long id) {
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

    public boolean doMatch(String word) {
        Matcher matcher = pattern.matcher(word);
        if (matcher.matches())
            return true;
        else
            return false;
    }
}
