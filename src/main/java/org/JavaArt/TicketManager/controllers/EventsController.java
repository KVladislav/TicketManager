package org.JavaArt.TicketManager.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 23.06.2014
 * Time: 12:03
 */

import org.JavaArt.TicketManager.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;

@Controller
@SessionAttributes({"pageName"})
public class EventsController {
    private Service service = new Service();

    @RequestMapping(value = "/Events.do", method = RequestMethod.GET)
    public String eventsGet(Model model) throws SQLException {
        model.addAttribute("pageName", 4);//set menu page number
        return "Events";
    }
}