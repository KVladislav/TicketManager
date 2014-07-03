package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:28
 */

@Repository

public interface EventRepository {
    public void addEvent(Event event);
    public void updateEvent(Event event);
    public Event getEventById(int id);
    public List<Event> getFutureEvents();
    public List<Event> getAllEvents();
//    public void deleteEvent(Event event);
}
