package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface EventRepository {
    public void addEvent(Event event);

    public void updateEvent(Event event);

    public Event getEventById(int id);

    public List<Event> getFutureEvents();

    public List<Event> getFutureBookableEvents();

    public List<Event> getAllEvents();

    public void deleteEvent(Event event);

    public List<Event> getEventByDate(Date date);

    public List<Event> getEventByDateFromEvent(Date inputDate, Event event);

    public boolean busyEvent(Event event);
}
