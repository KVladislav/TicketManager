package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "events", "event"})

public class EventsController {
    private EventService eventService = new EventService();

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) throws SQLException {
        model.addAttribute("pageName", 4);//set menu page number
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size()>0) {
        model.addAttribute("event", events.get(0));
        model.addAttribute("events", events);
        }
            return "Events";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "eventId", required=true) int eventId, Model model,SessionStatus status) throws SQLException {
        Event event = eventService.getEventById(eventId);
        event.setDeleted(true);
        eventService.updateEvent(event);
      //  return "Events";
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.POST)
    public String eventOrder(@ModelAttribute(value = "id") int id , @RequestParam(value = "event", required=true) Operator operator, Time time, SessionStatus status, Model model) throws SQLException {
     //   for (int seat : seats) {

            Event event = new Event();
            event.setDate(new Date());
            boolean isDeleted = false;
            event.setDeleted(isDeleted);
            event.setDescription("");
            event.setOperator(operator);
            event.setTimeStamp(time);
            eventService.addEvent(event);

        status.setComplete();
      //  }
        return "redirect:Events/Events.do";
    }
}
