package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes({"events", "event"})

public class Events {
    private Service service = new Service();

    @RequestMapping(value = "/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) throws SQLException {
        List<Event> events = service.getAllEvents();
        if (events != null && events.size()>0) {
        model.addAttribute("event", events.get(0));
        model.addAttribute("events", events);
        }
            return "Events";
    }



    @RequestMapping(value = "Events.do", method = RequestMethod.POST)
    public String eventOrder(@ModelAttribute(value = "id") int id , @RequestParam(value = "event", required=true) Operator operator, Time time, SessionStatus status, Model model) throws SQLException {
     //   for (int seat : seats) {

            Event event = new Event();
            event.setDate(new Date());
            boolean isDeleted = false;
            event.setDeleted(isDeleted);
            event.setDescription("");
            event.setOperator(operator);
            event.setTimeStamp(time);
            service.addEvent(event);

        status.setComplete();
      //  }
        return "redirect:/Events.do";
    }
}
