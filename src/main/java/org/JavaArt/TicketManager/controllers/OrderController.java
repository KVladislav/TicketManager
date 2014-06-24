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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Controller
@SessionAttributes({"pageName", "events", "event", "sectorsMap", "sector", "row", "rowsMap", "seatsMap"})
public class OrderController {

    private Service service = new Service();
    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) throws SQLException {
        model.addAttribute("pageName", 1);//set menu page number
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

                List<Sector> sectorsOrderPrice = service.getSectorsByEventOrderPrice(events.get(0));
                model.addAttribute("legenda", service.getLegenda(sectorsOrderPrice));

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


            }
        }
        return "Order";
    }

    @RequestMapping(value = "Order/setSectors.do", method = RequestMethod.POST)
    public String orderSetSectors(@RequestParam(value = "eventId", required=true) int eventId, Model model) throws SQLException {
        Event event = service.getEventById(eventId);
        model.addAttribute("event", event);
        List<Sector> sectors = service.getSectorsByEvent(event);
        Map<Sector,Integer> sectorsMap = new TreeMap<>();
        for (Sector sector:sectors) {
            sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
        }
        model.addAttribute("sectorsMap", sectorsMap);
        return "Order";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required=true) int sectorId, Model model) throws SQLException {
        Sector sector = service.getSectorById(sectorId);
        model.addAttribute("sector", sector);
        Map<Integer,Integer> rowsMap = new TreeMap<>();
        for (int i = 0; i < sector.getMaxRows(); i++) {
            rowsMap.put(i,service.getFreeTicketsAmountBySectorRow(sector, i));
        }
        model.addAttribute("rowsMap", rowsMap);
        return "Order";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required=true) int row, @ModelAttribute Sector sector, Model model) throws SQLException {
        Map<Integer,Boolean> seatsMap = new TreeMap<>();
        for (int i = 0; i < sector.getMaxSeats(); i++) {
            seatsMap.put(i,service.isPlaceFree(sector, row, i));
        }
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        return "Order";
    }

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.POST)
    public String orderOrder(@ModelAttribute(value = "row") int row , @RequestParam(value = "seats", required=true) int[] seats, @ModelAttribute Sector sector,  SessionStatus status, Model model) throws SQLException {
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
        return "redirect:/Order/Order.do";
    }
}
