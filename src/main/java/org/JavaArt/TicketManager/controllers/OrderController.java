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
        "row", "rowsMap", "seat", "seatsMap", "orderList", "orderPrice", "message", "error"})
public class OrderController {
    private EventService eventService = new EventService();
    private TicketService ticketService = TicketService.getInstance();
    private SectorService sectorService = new SectorService();

    private ArrayList<Ticket> order = new ArrayList<>();
    private double orderPrice = 0;
    private Event currentEvent = null;
    private Sector currentSector = null;
    private Integer currentRow = 1;
    private Integer currentSeat = 1;

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) {
        model.addAttribute("pageName", 1);
        List<Event> events = eventService.getFutureEvents();
        if (events != null && events.size() > 0) {
            if (currentEvent == null) currentEvent = events.get(0);
            model.addAttribute("event", currentEvent);
            model.addAttribute("events", events);

            List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
            if (sectors != null && sectors.size() > 0) {
                if (currentSector == null) currentSector = sectors.get(0);
                model.addAttribute("sector", currentSector);

                Map<Sector, Short> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector,(short) ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMap", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
                model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));

                Map<Byte, Byte> rowsMap1 = new TreeMap<>();
                for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
                    rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
                }
                model.addAttribute("rowsMap", rowsMap1);
                Map<Integer, Integer> seatsMap1 = new TreeMap<>();
                for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
                    seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
                }
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
        Map<Sector, Short> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, (short)ticketService.getFreeTicketsAmountBySector(sector));
        }
        List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
        model.addAttribute("legenda", sectorService.getLegenda(sectorsOrderPrice));
        model.addAttribute("sectorsMap", sectorsMap);
        currentSector = sectors.get(0);
        model.addAttribute("sector", currentSector);
        Map<Byte, Byte> rowsMap1 = new TreeMap<>();
        for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow = 1;
        currentSeat = 1;
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        model.addAttribute("message", "");
        model.addAttribute("error", "");
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) int sectorId,
                              Model model) {
        currentSector = sectorService.getSectorById(sectorId);
        model.addAttribute("sector", currentSector);
        Map<Byte, Byte> rowsMap1 = new TreeMap<>();
        for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMap", rowsMap1);
        currentRow = 1;
        currentSeat = 1;
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        model.addAttribute("message", "");
        model.addAttribute("error", "");
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row,
                               @ModelAttribute Sector sector, Model model) {
        currentSector = sector;
        currentRow = row;
        currentSeat = 1;
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("row", currentRow);
        model.addAttribute("seat", currentSeat);
        model.addAttribute("seatsMap", seatsMap1);
        model.addAttribute("message", "");
        model.addAttribute("error", "");
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@ModelAttribute(value = "row") Integer row,
                                 @ModelAttribute(value = "sector") Sector sector,
                                 @RequestParam(value = "seat") int[] seat, Model model) {
        StringBuilder error = new StringBuilder(300);
        error.append("");
        model.addAttribute("error", "");
        if (row==null) row=currentRow;
        if (sector==null) sector=currentSector;
        if (order.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : order) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                 model.addAttribute("error", "Билет ID = " + deletingTicket.get(0).getId() +
                         " автоматически удален из заказа, так как не был куплен в течении 5 минут");
                 order.remove(deletingTicket.get(0));
                 orderPrice -= deletingTicket.get(0).getSector().getPrice();
             }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                     order.remove(tic);
                     orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("error", "Билеты ID = "+ builder +
                        " автоматически удалены из заказа, так как не были куплены в течении 5 минут");
            }
        }
        for (int seat1:seat){
            Ticket ticket = new Ticket();
            ticket.setSector(sector);
            ticket.setRow(row);
            ticket.setSeat(seat1);
            currentSeat = seat1;
            if (order.size() > 0) {
                for (Ticket ord : order) {
                    if (ord.getSector().equals(sector) && ord.getSeat() == seat1 && ord.getRow() == row)
                        return "redirect:/Order/Order.do";
                }
            }
            if (ticketService.isPlaceFree(sector, row, seat1)==0){
                ticketService.addTicket(ticket);
                order.add(ticket);
                orderPrice += sector.getPrice();
            }
            else error.append( "Билет ").append(sector.getEvent().getDescription()).append(" Сектор: ").
                 append(sector.getName()).append(" Ряд: ").append(row).append(" Место: ").append(seat1).
                 append(" заблокирован<br>");
        }
        model.addAttribute("message", error);
        model.addAttribute("seat", currentSeat);
       //if (seat.length==1) model.addAttribute("message", "Билет добавлен в заказ");
       // if (seat.length>1) model.addAttribute("message", "Билеты добавлены в заказ");
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/delTicket.do", method = RequestMethod.POST)
    public String orderDelTicket(@RequestParam(value = "orderId") int orderId, Model model){
        model.addAttribute("error","");
        model.addAttribute("message", "");
        if (order.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : order) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                model.addAttribute("error", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 5 минут");
                order.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
            }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                    order.remove(tic);
                    orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("error", "Билеты ID = "+ builder +
                        " автоматически удалены из заказа, так как не были куплены в течении 5 минут");
            }
        }
        for (Ticket ord : order) {
            if (ord.getId() == orderId) {
                ticketService.deleteTicket(ord);
                order.remove(ord);
                orderPrice-=ord.getSector().getPrice();
                model.addAttribute("message", "Билет ID = "+ord.getId()+" удалён из заказа");
                break;
            }
        }
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/Buy.do", method = RequestMethod.POST)
    public String orderBuy(Model model) {
        StringBuilder idBuy = new StringBuilder(200);
        model.addAttribute("error","");
        model.addAttribute("message", "");
        if (order.size()==0)  return "redirect:/Order/Order.do";
        if (order.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : order) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                model.addAttribute("error", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 5 минут");
                order.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
            }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                    order.remove(tic);
                    orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("error", "Билеты ID = "+ builder +
                        " автоматически удалены из заказа, так как не были куплены в течении 5 минут");
            }
        }
        for (Ticket ticket : order) {
            if (ticketService.getTicketById(ticket.getId()).isConfirmed()) {
                if (ticketService.getTicketById(ticket.getId()).isReserved())
                    model.addAttribute("error", "ОШИБКА! Билет ID = " + ticket.getId() + " уже забронирован");
                else model.addAttribute("error", "ОШИБКА! Билет ID = " + ticket.getId() + " уже продан");
                order.remove(ticket);
                orderPrice -= ticket.getSector().getPrice();
                return "redirect:/Order/Order.do";
            }
        }
        for (Ticket ticket : order) {
             ticket.setConfirmed(true);
             idBuy.append(ticket.getId()).append("  ");
             ticketService.addTicket(ticket);
        }
        if (order.size()==1) model.addAttribute("message", "Билет ID = "+ idBuy +" из заказа куплен");
        if (order.size()>1) model.addAttribute("message", "Билеты ID = "+ idBuy +" из заказа куплены");
        order.clear();
        orderPrice = 0;
        return "redirect:/Order/Order.do";
    }

}
