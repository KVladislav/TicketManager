package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.EventService;
import org.JavaArt.TicketManager.service.SectorService;
import org.JavaArt.TicketManager.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@SessionAttributes({"pageName", "events", "event", "sectorsMap", "sector", "legenda",
        "row", "rowsMap", "seat", "seatsMap", "orderList", "orderPrice"})
public class OrderController {
    private ArrayList<Ticket> order = new ArrayList<>();
    private double orderPrice = 0;
    private EventService eventService = new EventService();
    private TicketService ticketService = TicketService.getInstance();
    private SectorService sectorService = new SectorService();
    private Event currentEvent = null;
    private Sector currentSector = null;
    private int currentRow = 1;
    private int currentSeat = 1;
    private int currentOrderId=1;

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) {
        model.addAttribute("pageName", 1);
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            if (currentEvent == null) currentEvent = events.get(0);
            model.addAttribute("event", currentEvent);
            model.addAttribute("events", events);

            List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
            if (sectors != null && sectors.size() > 0) {
                if (currentSector == null) currentSector = sectors.get(0);
                model.addAttribute("sector", currentSector);

                Map<Sector, Integer> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMap", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
                model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));

                Map<Integer, Integer> rowsMap1 = new TreeMap<>();
                for (int i = 1; i <= currentSector.getMaxRows(); i++) {
                    rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
                }
                model.addAttribute("rowsMap", rowsMap1);
                Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow, order);
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
                                  int eventId, Model model) {
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
        currentSector = sectors.get(0);
        model.addAttribute("sector", currentSector);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow = 1;
        currentSeat = 1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow, order);
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) int sectorId,
                              Model model) {
        currentSector = sectorService.getSectorById(sectorId);
        model.addAttribute("sector", currentSector);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow = 1;
        currentSeat = 1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow, order);



        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row,
                               @ModelAttribute Sector sector, Model model) {

        currentSector = sector;
        currentRow = row;
        currentSeat = 1;
        Map<Integer, String> seatsMap1 = ticketService.seatStatus(currentSector, currentRow, order);
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@ModelAttribute(value = "row") int row,
                                 @RequestParam(value = "seat",required = true) int seat,
                                 @ModelAttribute(value = "sector") Sector sector) {
        Ticket ticket = new Ticket();
        ticket.setSector(sector);
        ticket.setRow(row);
        ticket.setSeat(seat);
        //ticket.setOperator(operator);
        currentSeat = seat;
        if (order.size() > 0) {
            for (Ticket ord : order) {
                if (ord.getSector().equals(sector) && ord.getSeat() == seat && ord.getRow() == row)
                    return "redirect:/Order/Order.do";
            }
        }
        ticket.setId(currentOrderId++);
        order.add(ticket);
        orderPrice += sector.getPrice();
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/delTicket.do", method = RequestMethod.POST)
         public String orderDelTicket(@RequestParam(value = "orderId", required = true)
                                       int orderId){
        int index=orderId;
        for (Ticket ord : order) {
            if (ord.getId() == orderId) {
                order.remove(ord);
                orderPrice-=ord.getSector().getPrice();
                currentOrderId--;
                break;
            }
        }
        for (Ticket ord : order){
            if (ord.getId()>orderId) ord.setId(index++);
        }
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/Buy.do", method = RequestMethod.POST)
    public String orderBuy() {
        for (Ticket ticket : order){
            ticket.setConfirmed(true);
            ticket.setId(null);
            ticketService.addTicket(ticket);
        }
        order.clear();
        orderPrice = 0;
        currentOrderId=1;
        return "redirect:/Order/Order.do";
    }
}
