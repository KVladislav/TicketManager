package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Controller
@SessionAttributes({"pageName", "events", "event", "sectorsMap", "sector", "legenda", "row", "rowsMap", "seatsMap", "order"})
public class OrderController {
    private EventService eventService = new EventService();
    private TicketService ticketService = TicketService.getInstance();
    private SectorService sectorService = new SectorService();
    private List<Ticket> order = new ArrayList<>();
    private double orderPrice=0;

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) throws SQLException {
        model.addAttribute("pageName", 1);
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            model.addAttribute("event", events.get(0));
            model.addAttribute("events", events);

            List<Sector> sectors = sectorService.getSectorsByEvent(events.get(0));
            if (sectors != null && sectors.size() > 0) {
                model.addAttribute("sector", sectors.get(0));

                Map<Sector, Integer> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMap", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(events.get(0));
                model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));

                Map<Integer, Integer> rowsMap1 = new TreeMap<>();
                for (int i = 1; i <= sectors.get(0).getMaxRows(); i++) {
                    rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(sectors.get(0), i));
                }
                model.addAttribute("rowsMap", rowsMap1);
                Map<Integer, String> seatsMap1 = ticketService.seatStatus(sectors.get(0), 1);
                model.addAttribute("row", 1);
                model.addAttribute("seatsMap", seatsMap1);
            }
        }
        return "Order";
    }

    @RequestMapping(value = "Order/setSectors.do", method = RequestMethod.POST)
    public String orderSetSectors(@RequestParam(value = "eventId", required = true) int eventId, Model model) throws SQLException {
        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        List<Sector> sectors = sectorService.getSectorsByEvent(event);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
        }
        List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(event);
        model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));
        model.addAttribute("sectorsMap", sectorsMap);
        model.addAttribute("sector", sectors.get(0));
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= sectors.get(0).getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(sectors.get(0), i));
        }
        model.addAttribute("rowsMap", rowsMap1);

        Map<Integer, String> seatsMap1 = ticketService.seatStatus(sectors.get(0), 1);
        model.addAttribute("row", 1);
        model.addAttribute("seatsMap", seatsMap1);
        return "Order";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model model) throws SQLException {
        Sector sector = sectorService.getSectorById(sectorId);
        model.addAttribute("sector", sector);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(sector, 1);
        model.addAttribute("row", 1);
        model.addAttribute("seatsMap", seatsMap1);
        return "Order";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row, @ModelAttribute Sector sector, Model model) throws SQLException {
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(sector, row);
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap1);
        return "Order";
    }

    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@Valid @ModelAttribute("row") int row, int place, Sector sector) throws SQLException {
        if (ticketService.isPlaceFree(sector,row, place)) {
            Ticket ticket = new Ticket();
            ticket.setSector(sector);
            ticket.setRow(row);
            ticket.setSeat(place);
            ticketService.addTicket(ticket);
            order.add(ticket);
            System.out.println("order.size()= "+order.size());
            orderPrice+=sector.getPrice();
            System.out.println("orderPrice= "+orderPrice);
        }
        return "redirect:/Order/Order.do";
    }
}
