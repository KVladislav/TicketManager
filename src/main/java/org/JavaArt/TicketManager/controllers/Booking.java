package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 19.06.2014
 * Time: 18:06
 */
@Controller
@SessionAttributes({"events", "event", "sectorsMap", "sectorId"})

public class Booking {
    private Service service = new Service();

    @RequestMapping(value = "/Booking", method = RequestMethod.GET)
    public String BookingGet(Model model) throws SQLException {
        List<Event> events = service.getAllEvents();
        model.addAttribute("event");
        model.addAttribute("events", events);
        return "Booking";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String BookingPost(@ModelAttribute("eventId") String eventID) throws SQLException {
//        System.out.println(eventID + "sdg ");
//        return "Booking";
//    }

    @RequestMapping(value = "Booking/setSectors", method = RequestMethod.POST)
    public String BookingSetSector(@RequestParam(value = "eventId", required=true) int eventId, Model model) throws SQLException {
        Event event = service.getEventById(eventId);
        System.out.println(eventId + " " + event);
        model.addAttribute("event", event);
        List<Sector> sectors = service.getSectorsByEvent(event);
        Map<Sector,Integer> sectorsMap = new TreeMap<>();
        for (Sector sector:sectors) {
            sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
        }
        model.addAttribute("sectorsMap", sectorsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow", method = RequestMethod.POST)
    public String BookingSetTicket(@RequestParam(value = "sector", required=true) Sector sector, Model model) throws SQLException {


        return "Booking";
    }


}
