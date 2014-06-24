package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;


@Controller
public class WelcomeController {
    private EventService eventService = new EventService();

    @RequestMapping("/AllEvents.do")
    public String allEvents(Model model) throws SQLException{
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "AllEvents";
    }
}
