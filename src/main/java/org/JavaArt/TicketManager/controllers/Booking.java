package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

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
@SessionAttributes({"events", "event", "sectorsMap", "sector", "row", "rowsMap", "seatsMap"})

public class Booking {
    private Service service = new Service();

    @RequestMapping(value = "/Booking.do", method = RequestMethod.GET)
    public String bookingGet(Model model) throws SQLException {
        List<Event> events = service.getAllEvents();
        if (events != null && events.size()>0) {
            model.addAttribute("event", events.get(0));

        model.addAttribute("events", events);

        List<Sector> sectors = service.getSectorsByEvent(events.get(0));
        if (sectors!=null && sectors.size()>0) {
            model.addAttribute("sector", sectors.get(0));

        Map<Sector,Integer> sectorsMap = new TreeMap<>();
        for (Sector sector:sectors) {
            sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
        }
        model.addAttribute("sectorsMap", sectorsMap);

        Map<Integer,Integer> rowsMap = new TreeMap<>();
        for (int i = 0; i < sectors.get(0).getMaxRows(); i++) {
            rowsMap.put(i,service.getFreeTicketsAmountBySectorRow(sectors.get(0), i));
        }
        model.addAttribute("rowsMap", rowsMap);

        Map<Integer,Boolean> seatsMap = new TreeMap<>();
        for (int i = 0; i < sectors.get(0).getMaxSeats(); i++) {
            seatsMap.put(i,service.isPlaceFree(sectors.get(0), 1, i));
        }
        model.addAttribute("row", 1);
        model.addAttribute("seatsMap", seatsMap);
        }  }
            return "Booking";
    }

    @RequestMapping(value = "Booking/setSectors.do", method = RequestMethod.POST)
    public String bookingSetSectors(@RequestParam(value = "eventId", required=true) int eventId, Model model) throws SQLException {
        Event event = service.getEventById(eventId);
        model.addAttribute("event", event);
        List<Sector> sectors = service.getSectorsByEvent(event);
        Map<Sector,Integer> sectorsMap = new TreeMap<>();
        for (Sector sector:sectors) {
            sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
        }
        model.addAttribute("sectorsMap", sectorsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow.do", method = RequestMethod.POST)
    public String bookingSetRow(@RequestParam(value = "sectorId", required=true) int sectorId, Model model) throws SQLException {
        Sector sector = service.getSectorById(sectorId);
        model.addAttribute("sector", sector);
        Map<Integer,Integer> rowsMap = new TreeMap<>();
        for (int i = 0; i < sector.getMaxRows(); i++) {
            rowsMap.put(i,service.getFreeTicketsAmountBySectorRow(sector, i));
        }
        model.addAttribute("rowsMap", rowsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSeat.do", method = RequestMethod.POST)
    public String bookingSetSeat(@RequestParam(value = "row", required=true) int row, @ModelAttribute Sector sector, Model model) throws SQLException {
        Map<Integer,Boolean> seatsMap = new TreeMap<>();
        for (int i = 0; i < sector.getMaxSeats(); i++) {
            seatsMap.put(i,service.isPlaceFree(sector, row, i));
        }
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking.do", method = RequestMethod.POST)
    public String bookingOrder(@ModelAttribute(value = "row") int row , @RequestParam(value = "seats", required=true) int[] seats, @ModelAttribute Sector sector,  SessionStatus status, Model model) throws SQLException {
        for (int seat : seats) {
            if (service.isPlaceFree(sector, row, seat)) {
                Ticket ticket = new Ticket();
                ticket.setSector(sector);
                ticket.setRow(row);
                ticket.setSeat(seat);
                service.addTicket(ticket);
            }
        status.setComplete();
        }
        return "redirect:/Booking.do";
    }
}
