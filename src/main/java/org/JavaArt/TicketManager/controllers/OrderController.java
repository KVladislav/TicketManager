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
import org.springframework.web.bind.support.SessionStatus;

import java.util.*;


@Controller
@SessionAttributes({"pageName", "eventsOrder", "eventOrder", "sectorsMapOrder", "sectorOrder", "legendaOrder",
     "rowOrder", "rowsMapOrder", "seatOrder", "seatsMapOrder", "orderList", "orderPrice", "messageOrder", "errorOrder",
     "orderConfirmation", "priceConfirmation"})
public class OrderController
{
    private EventService eventService = new EventService();
    private TicketService ticketService = TicketService.getInstance();
    private SectorService sectorService = new SectorService();

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) {
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        Event currentEvent = (Event) model.asMap().get("eventOrder");
        Sector currentSector = (Sector) model.asMap().get("sectorOrder");
        Integer currentRow = (Integer) model.asMap().get("rowOrder");
        Integer currentSeat = (Integer) model.asMap().get("seatOrder");
        if (orderTickets == null) orderTickets = new ArrayList<>();
        if (orderPrice==null) orderPrice = 0.0;
        model.addAttribute("pageName", 1);
        List<Event> events = eventService.getFutureEvents();
        if (events != null && events.size() > 0) {
            if (currentEvent==null || eventService.getEventById(currentEvent.getId())==null ||
                    currentEvent.getDate().before(new Date()))
                currentEvent = events.get(0);
            model.addAttribute("eventOrder", currentEvent);
            model.addAttribute("eventsOrder", events);
            List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
            if (sectors != null && sectors.size() > 0) {
                if (currentSector==null || sectorService.getSectorById(currentSector.getId())==null||
                                        currentSector.getEvent().getDate().before(new Date())){
                    currentSector = sectors.get(0);
                    currentRow = 1;
                    currentSeat = 1;
                }
                model.addAttribute("sectorOrder", currentSector);
                Map<Sector, Short> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector,(short) ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMapOrder", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
                model.addAttribute("legendaOrder", sectorService.getLegenda(sectorsOrderPrice));
                Map<Byte, Byte> rowsMap1 = new TreeMap<>();
                for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
                    rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
                }
                model.addAttribute("rowsMapOrder", rowsMap1);
                Map<Integer, Integer> seatsMap1 = new TreeMap<>();
                for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
                    seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
                }
                model.addAttribute("rowOrder", currentRow);
                model.addAttribute("seatOrder", currentSeat);
                model.addAttribute("seatsMapOrder", seatsMap1);
                model.addAttribute("orderPrice", orderPrice);
                model.addAttribute("orderList", orderTickets);
                model.addAttribute("messageOrder", "");
                model.addAttribute("errorOrder", "");
            }
        }
        return "Order";
    }

    @RequestMapping(value = "Order/setSectors.do", method = RequestMethod.POST)
    public String orderSetSectors(@RequestParam(value = "eventId", required = true)
                                  int eventId, Model model) {
        Event currentEvent = eventService.getEventById(eventId);
        model.addAttribute("eventOrder", currentEvent);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        model.addAttribute("sectorOrder", sectors.get(0));
        model.addAttribute("rowOrder", 1);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model model) {
        Sector currentSector = sectorService.getSectorById(sectorId);
        model.addAttribute("sectorOrder", currentSector);
        model.addAttribute("rowOrder", 1);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }

    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row, Model model) {
        Integer currentRow = row;
        model.addAttribute("rowOrder", currentRow);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@RequestParam(value = "seat", required = false) int[] seat, Model model,
                                 @ModelAttribute("eventOrder") Event currentEvent,
                                 @ModelAttribute("sectorOrder") Sector currentSector,
                                 @ModelAttribute("rowOrder") Integer currentRow){
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        if (orderTickets == null) orderTickets = new ArrayList<>();
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        if (orderPrice==null) orderPrice = 0.0;
        model.addAttribute("errorOrder", "");
        model.addAttribute("messageOrder", "");
        StringBuilder message = new StringBuilder(500);
        boolean doubleTicket;
        message.append("");
        if (orderTickets!=null && orderTickets.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                 model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                         " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderTickets.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
             }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                    orderTickets.remove(tic);
                    orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("errorOrder", "Билеты ID = "+ builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }
        }
        for (int seat1:seat){
            Ticket ticket = new Ticket();
            ticket.setSector(currentSector);
            ticket.setRow(currentRow);
            ticket.setSeat(seat1);
            doubleTicket=false;
            if (orderTickets.size() > 0) {
                for (Ticket ord : orderTickets) {
                    if (ord.getSector().equals(currentSector) && ord.getSeat() == seat1 && ord.getRow()==currentRow){
                        doubleTicket=true;
                        break;
                    }
                }
            }
            if (!doubleTicket){
                if (ticketService.isPlaceFree(currentSector, currentRow, seat1)==0&&
                        !sectorService.getSectorById(currentSector.getId()).isDeleted()){
                    ticketService.addTicket(ticket);
                    orderPrice += currentSector.getPrice();
                    orderTickets.add(ticket);
                }
                else message.append( "Билет на ").append(currentSector.getEvent().getDescription()).append(" Сектор: ").
                        append(currentSector.getName()).append(" Ряд: ").append(currentRow).append(" Место: ").
                        append(seat1).append(" уже продан<br>");
            }
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId())==null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Short> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector,(short) ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId())==null||
                currentSector.getEvent().getDate().before(new Date())){
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Byte, Byte> rowsMap1 = new TreeMap<>();
        for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMapOrder", rowsMap1);
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("eventOrder", currentEvent);
        model.addAttribute("sectorOrder", currentSector);
        model.addAttribute("rowOrder", currentRow);
        model.addAttribute("seatsMapOrder", seatsMap1);
        model.addAttribute("messageOrder", message);
        model.addAttribute("seatOrder", seat[(seat.length-1)]);
        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("orderList", orderTickets);
        return "Order";
    }

    @RequestMapping(value = "Order/delTicket.do", method = RequestMethod.POST)
    public String orderDelTicket(@RequestParam(value = "orderId") int orderId, Model model,
                                 @ModelAttribute("eventOrder") Event currentEvent,
                                 @ModelAttribute("sectorOrder") Sector currentSector,
                                 @ModelAttribute("rowOrder") Integer currentRow){
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        model.addAttribute("errorOrder","");
        model.addAttribute("messageOrder", "");
        if (orderTickets!=null && orderTickets.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderTickets.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
            }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                    orderTickets.remove(tic);
                    orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("errorOrder", "Билеты ID = "+ builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }
        }
        for (Ticket ord : orderTickets) {
            if (ord.getId() == orderId) {
                ticketService.deleteTicket(ord);
                orderTickets.remove(ord);
                orderPrice-=ord.getSector().getPrice();
                model.addAttribute("messageOrder", "Билет ID = "+ord.getId()+" удалён из заказа");
                break;
            }
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId())==null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Short> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector,(short) ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId())==null||
                currentSector.getEvent().getDate().before(new Date())){
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Byte, Byte> rowsMap1 = new TreeMap<>();
        for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMapOrder", rowsMap1);
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("eventOrder", currentEvent);
        model.addAttribute("sectorOrder", currentSector);
        model.addAttribute("rowOrder", currentRow);
        model.addAttribute("seatsMapOrder", seatsMap1);
        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("orderList", orderTickets);
        return "Order";
    }

    @RequestMapping(value = "Order/Buy.do", method = RequestMethod.POST)
    public String orderBuy(Model model, @ModelAttribute("sectorOrder") Sector currentSector,
                                        @ModelAttribute("rowOrder") Integer currentRow,
                                        @ModelAttribute("eventOrder") Event currentEvent) {
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        StringBuilder idBuy = new StringBuilder(200);
        model.addAttribute("errorOrder","");
        model.addAttribute("messageOrder", "");
        if (orderTickets!=null && orderTickets.size()>0){
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size()==1){
                model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderTickets.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
            }
            if (deletingTicket.size()>1){
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic: deletingTicket){
                    builder.append(tic.getId()).append("  ");
                    orderTickets.remove(tic);
                    orderPrice -= tic.getSector().getPrice();
                }
                model.addAttribute("errorOrder", "Билеты ID = "+ builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }
        }
        for (Ticket ticket : orderTickets) {
            if (ticketService.getTicketById(ticket.getId()).isConfirmed()) {
                if (ticketService.getTicketById(ticket.getId()).isReserved())
                    model.addAttribute("errorOrder", "ОШИБКА! Билет ID = " + ticket.getId() + " уже забронирован");
                else model.addAttribute("errorOrder", "ОШИБКА! Билет ID = " + ticket.getId() + " уже продан");
                orderTickets.remove(ticket);
                orderPrice -= ticket.getSector().getPrice();
                model.addAttribute("orderPrice", orderPrice);
                return "Order";
            }
        }
        for (Ticket ticket : orderTickets) {
             ticket.setConfirmed(true);
             idBuy.append(ticket.getId()).append("  ");
             ticketService.addTicket(ticket);
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId())==null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Short> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector,(short) ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId())==null||
                currentSector.getEvent().getDate().before(new Date())){
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Byte, Byte> rowsMap1 = new TreeMap<>();
        for (byte i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, (byte)ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
        }
        model.addAttribute("rowsMapOrder", rowsMap1);
        Map<Integer, Integer> seatsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxSeats(); i++) {
            seatsMap1.put(i, ticketService.isPlaceFree(currentSector, currentRow, i));
        }
        model.addAttribute("eventOrder", currentEvent);
        model.addAttribute("sectorOrder", currentSector);
        model.addAttribute("rowOrder", currentRow);
        model.addAttribute("seatsMapOrder", seatsMap1);
        model.addAttribute("orderConfirmation", orderTickets);
        model.addAttribute("priceConfirmation", orderPrice);
        model.addAttribute("orderPrice", 0.0);
        model.addAttribute("orderList", new ArrayList<Ticket>());
        return "OrderInfo";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "OrderInfo/Clear.do")
    public String  orderInfoClear(Model model) {
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderConfirmation");
        orderTickets.clear();
        model.addAttribute("orderConfirmation", orderTickets);
        model.addAttribute("priceConfirmation", 0.0);
        return "Order";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Order/Cancel.do")
    public String  orderCancel(Model model) {
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        if (orderTickets!=null){
            for (Ticket ord : orderTickets) {
                ticketService.deleteTicket(ord);
            }
            orderTickets.clear();
            model.addAttribute("orderList", orderTickets);
        }
        model.addAttribute("orderPrice", 0.0);
        return "redirect:/Order/Order.do";
   }
}
