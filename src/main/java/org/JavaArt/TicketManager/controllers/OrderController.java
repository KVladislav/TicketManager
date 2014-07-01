package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Controller
@SessionAttributes({"pageName", "events", "event", "sectorsMap", "sector", "legenda",
                    "row", "rowsMap", "seat", "seatsMap", "orderList", "orderPrice"})
public class OrderController {
    private EventService eventService = new EventService();
    private TicketService ticketService = TicketService.getInstance();
    private SectorService sectorService = new SectorService();
    static public List<Ticket> order = new ArrayList<>();
    static public double orderPrice=0;
    private Event currentEvent=null;
    private Sector currentSector=null;
    private int currentRow=1;
    private int currentSeat=1;

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) throws SQLException {
        model.addAttribute("pageName", 1);
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            if (currentEvent==null) currentEvent=events.get(0);
            model.addAttribute("event", currentEvent);
            model.addAttribute("events", events);

            List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
            if (sectors != null && sectors.size() > 0) {
                if (currentSector==null) currentSector=sectors.get(0);
                model.addAttribute("sector", currentSector);

                Map<Sector, Integer> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMap", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
                model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));

                Map<Integer, Integer> rowsMap1 = new TreeMap<>();
                for (int i = 1; i <=  currentSector.getMaxRows(); i++) {
                    rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow( currentSector, i));
                }
                model.addAttribute("rowsMap", rowsMap1);
                Map<Integer, String> seatsMap1 = ticketService.seatStatus( currentSector, currentRow);
                model.addAttribute("row", currentRow);
                model.addAttribute("seat", currentSeat);
                model.addAttribute("seatsMap", seatsMap1);
                model.addAttribute("orderPrice", orderPrice);
                model.addAttribute("orderList", order);
            }
        }
        return "Order";
    }

    @RequestMapping(value = "Order/setSectors.do", method = RequestMethod.POST)
    public String orderSetSectors(@RequestParam(value = "eventId", required = true)
                                  int eventId, Model model) throws SQLException {
        currentEvent = eventService.getEventById(eventId);
        model.addAttribute("event", currentEvent);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
        }
        List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
        model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));
        model.addAttribute("sectorsMap", sectorsMap);
        currentSector=sectors.get(0);
        model.addAttribute("sector", currentSector);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow=1;
        currentSeat=1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow);
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) int sectorId,
                              Model model) throws SQLException {
        currentSector= sectorService.getSectorById(sectorId);
        model.addAttribute("sector", currentSector);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow=1;
        currentSeat=1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow);
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row,
                               @ModelAttribute Sector sector, Model model) throws SQLException {

        currentSector=sector;
        currentRow=row;
        currentSeat=1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow);
        model.addAttribute("row",currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@ModelAttribute(value = "row") int row, @RequestParam(value = "seat",
                  required = true) int seat, @ModelAttribute Sector sector) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setSector(sector);
        ticket.setRow(row);
        ticket.setSeat(seat);
        ticket.setReserved(false);
        ticket.setConfirmed(false);
        currentSeat=seat;
        if (order.size()>0){
            for (Ticket ord: order){
                if(ord.getSector()==sector&&ord.getSeat()==seat&&ord.getRow()==row)
                    return "redirect:/Order/Order.do";
            }
        }
        orderPrice+=sector.getPrice();
        ticketService.addTicket(ticket);
        order.add(ticket);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/delTicket.do", method = RequestMethod.POST)
         public String orderDelTicket(@RequestParam(value = "ticketId", required = true)
                                       int ticketId) throws SQLException {
        ticketService.deleteTicket( ticketService.getTicketById(ticketId));
        for (Ticket ticket : order) {
            if ((int)ticket.getId() == ticketId) {
                order.remove(ticket);
                orderPrice-=ticket.getSector().getPrice();
                break;
            }
        }
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/Buy.do", method = RequestMethod.POST)
     public String orderBuy() throws SQLException {
         for (Ticket ticket : order) ticket.setConfirmed(true);
         ticketService.updateTickets(order);
         order.clear();
         orderPrice=0;
         return "redirect:/Order/Order.do";
     }
}
