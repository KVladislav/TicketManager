package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 18.06.2014
 * Time: 21:52
 */
@Controller
public class WelcomeController {
    private Service service = new Service();



    @RequestMapping("/AllEvents.do")
    public String allEvents(Model model) throws SQLException{
        List<Event> events = service.getAllEvents();
        model.addAttribute("events", events);
        return "AllEvents";
    }

}
