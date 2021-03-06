package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@SessionAttributes({"pageName", "eventsOrder", "eventOrder", "sectorsMapOrder", "sectorOrder", "legendaOrder",
        "rowOrder", "rowsMapOrder", "seatOrder", "seatsMapOrder", "orderList", "orderPrice", "messageOrder", "errorOrder",
        "orderConfirmation", "priceConfirmation"})
public class OrderController
{
    @Autowired
    private EventService eventService;// = new EventService();
    @Autowired
    private TicketService ticketService;// = new TicketService();
    @Autowired
    private SectorService sectorService;// = new SectorService();
    @Autowired
    private OperatorService operatorService;// = new OperatorService();

    @RequestMapping(value = "Order/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) {
        model.addAttribute("pageName", 1);
        Operator operator = operatorService.getOperatorByLogin(SecurityContextHolder.
                            getContext().getAuthentication().getName());
        List<Ticket> orderTickets = (List) model.asMap().get("orderList");
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        Event currentEvent = (Event) model.asMap().get("eventOrder");
        Sector currentSector = (Sector) model.asMap().get("sectorOrder");
        Integer currentRow = (Integer) model.asMap().get("rowOrder");
        Integer currentSeat = (Integer) model.asMap().get("seatOrder");
        /**
         * Find all do not Confirmed tickets of current operator after reload
        **/
            if (orderTickets == null) {
            orderTickets = ticketService.getOrderTicketsByOperator(operator);
            if (orderTickets != null) {
                orderPrice = 0.0;
                for (Ticket tic : orderTickets) {
                    orderPrice += tic.getSector().getPrice();
                }
            } else orderTickets = new ArrayList<>();
        }
        if (orderPrice == null) orderPrice = 0.0;
        List<Event> events = eventService.getFutureEvents();
        if (events != null && events.size() > 0) {
            if (currentEvent == null || eventService.getEventById(currentEvent.getId()) == null ||
                    currentEvent.getDate().before(new Date()))
                currentEvent = events.get(0);
            model.addAttribute("eventOrder", currentEvent);
            model.addAttribute("eventsOrder", events);
            List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
            if (sectors != null && sectors.size() > 0) {
                if (currentSector == null || sectorService.getSectorById(currentSector.getId()) == null ||
                        currentSector.getEvent().getDate().before(new Date())) {
                    currentSector = sectors.get(0);
                    currentRow = 1;
                    currentSeat = 1;
                }
                model.addAttribute("sectorOrder", currentSector);
                Map<Sector, Integer> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMapOrder", sectorsMap);
                List<Sector> sectorsOrderPrice = sectorService.getSectorsByEventOrderPrice(currentEvent);
                model.addAttribute("legendaOrder", sectorService.getLegenda(sectorsOrderPrice));
                Map<Integer, Integer> rowsMap1 = new TreeMap<>();
                for (int i = 1; i <= currentSector.getMaxRows(); i++) {
                    rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
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
    /**
     * Select of event in table "Выбор Мероприятия"
     **/
    @RequestMapping(value = "Order/setSectors.do", method = RequestMethod.POST)
    public String orderSetSectors(@RequestParam(value = "eventId", required = true)
                                  Long eventId, Model model) {
        Event currentEvent = eventService.getEventById(eventId);
        model.addAttribute("eventOrder", currentEvent);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        model.addAttribute("sectorOrder", sectors.get(0));
        model.addAttribute("rowOrder", 1);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }
    /**
     * Select of sector in table "Выбор Сектора"
     **/
    @RequestMapping(value = "Order/setRow.do", method = RequestMethod.POST)
    public String orderSetRow(@RequestParam(value = "sectorId", required = true) Long sectorId, Model model) {
        Sector currentSector = sectorService.getSectorById(sectorId);
        model.addAttribute("sectorOrder", currentSector);
        model.addAttribute("rowOrder", 1);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }
    /**
     * Select of row in table "Выбор Ряда"
     **/
    @RequestMapping(value = "Order/setSeat.do", method = RequestMethod.POST)
    public String orderSetSeat(@RequestParam(value = "row", required = true) int row, Model model) {
        Integer currentRow = row;
        model.addAttribute("rowOrder", currentRow);
        model.addAttribute("seatOrder", 1);
        return "redirect:/Order/Order.do";
    }
    /**
     * Select of seat in table "Выбор Места".
     * Add tickets to order and verification of booking tickets.
     * Update table on page "Order".
     **/
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Order/addTicket.do", method = RequestMethod.POST)
    public String orderAddTicket(@RequestParam(value = "seat", required = false) int[] seat, Model model,
                                 @ModelAttribute("eventOrder") Event currentEvent,
                                 @ModelAttribute("sectorOrder") Sector currentSector,
                                 @ModelAttribute("rowOrder") Integer currentRow) {
        if  (seat==null) {
            return "Order";
        }
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        if (orderTickets == null) orderTickets = new ArrayList<>();
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        if (orderPrice == null) orderPrice = 0.0;
        model.addAttribute("errorOrder", "");
        model.addAttribute("messageOrder", "");
        StringBuilder message = new StringBuilder(500);
        boolean doubleTicket;
        message.append("");
        if (orderTickets != null && orderTickets.size() > 0) {
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null){
                    deletingTicket.add(ord);
                    break;
                }
            }
            if (deletingTicket.size() > 0){
                for (Ticket ord : orderTickets) {
                    if (ticketService.getTicketById(ord.getId()) != null) ticketService.deleteTicket(ord);
                }
                model.addAttribute("errorOrder", "Заказ автоматически удален, так как не был утверждён в течении 10 минут");
                orderTickets.clear();
                orderPrice = 0.0;
            }
            /*if (deletingTicket.size() == 1) {
                model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
                orderTickets.remove(deletingTicket.get(0));
            }
            if (deletingTicket.size() > 1) {
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic : deletingTicket) {
                    builder.append(tic.getId()).append("  ");
                    orderPrice -= tic.getSector().getPrice();
                    orderTickets.remove(tic);
                }
                model.addAttribute("errorOrder", "Билеты ID = " + builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }*/
        }
        for (int seat1 : seat) {
            Ticket ticket = new Ticket();
            ticket.setSector(currentSector);
            ticket.setRow(currentRow);
            ticket.setSeat(seat1);
            doubleTicket = false;
            if (orderTickets.size() > 0) {
                for (Ticket ord : orderTickets) {
                    if (ord.getSector().equals(currentSector) && ord.getSeat()==seat1 && ord.getRow()==(int)currentRow) {
                        doubleTicket = true;
                        break;
                    }
                }
            }
            if (!doubleTicket) {
                if (ticketService.isPlaceFree(currentSector, currentRow, seat1) == 0 &&
                        !sectorService.getSectorById(currentSector.getId()).isDeleted()) {
                    ticketService.addTicket(ticket);
                    orderPrice += currentSector.getPrice();
                    orderTickets.add(ticket);
                } else {
                    if (ticketService.isPlaceFree(currentSector, currentRow, seat1) == 1)
                        message.append("Билет на  ").append(currentSector.getEvent().getDescription()).append("  Сектор: ").
                            append(currentSector.getName()).append(" Ряд: ").append(currentRow).append(" Место: ").
                            append(seat1).append("  заказан другим оператором<br>");
                    if (ticketService.isPlaceFree(currentSector, currentRow, seat1) == 2)
                        message.append("Билет на  ").append(currentSector.getEvent().getDescription()).append("  Сектор: ").
                                append(currentSector.getName()).append(" Ряд: ").append(currentRow).append(" Место: ").
                                append(seat1).append("  забронирован другим оператором<br>");
                    if (ticketService.isPlaceFree(currentSector, currentRow, seat1) == 3)
                        message.append("Билет на  ").append(currentSector.getEvent().getDescription()).append("  Сектор: ").
                                append(currentSector.getName()).append(" Ряд: ").append(currentRow).append(" Место: ").
                                append(seat1).append("  продан другим оператором<br>");
                }
            }
        }
        for (Ticket ord : orderTickets) {
            ord.setTimeStamp(new Date());
            ticketService.saveOrUpdateTicket(ord);
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId()) == null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector,ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId()) == null ||
                currentSector.getEvent().getDate().before(new Date())) {
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
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
        model.addAttribute("seatOrder", seat[(seat.length - 1)]);
        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("orderList", orderTickets);
        return "Order";
    }
    /**
     * Delete one ticket from order.
     * Verification of all booking tickets.
     * Update table on page "Order".
     **/
    @RequestMapping(value = "Order/delTicket.do", method = RequestMethod.POST)
    public String orderDelTicket(@RequestParam(value = "orderId", required = true) Long orderId, Model model,
                                 @ModelAttribute("eventOrder") Event currentEvent,
                                 @ModelAttribute("sectorOrder") Sector currentSector,
                                 @ModelAttribute("rowOrder") Integer currentRow,
                                 @ModelAttribute("orderList") ArrayList<Ticket> orderTickets,
                                 @ModelAttribute("orderPrice") Double orderPrice) {
        model.addAttribute("errorOrder", "");
        model.addAttribute("messageOrder", "");
        if (orderTickets != null && orderTickets.size() > 0) {
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size() > 0){
                for (Ticket ord : orderTickets) {
                    if (ticketService.getTicketById(ord.getId()) != null) ticketService.deleteTicket(ord);
                }
                model.addAttribute("errorOrder", "Заказ автоматически удален, так как не был утверждён в течении 10 минут");
                orderTickets.clear();
                orderPrice = 0.0;
            }
            /*if (deletingTicket.size() == 1) {
                model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
                orderTickets.remove(deletingTicket.get(0));
            }
            if (deletingTicket.size() > 1) {
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic : deletingTicket) {
                    builder.append(tic.getId()).append("  ");
                    orderPrice -= tic.getSector().getPrice();
                    orderTickets.remove(tic);
                }
                model.addAttribute("errorOrder", "Билеты ID = " + builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }*/
        }
        if (ticketService.getTicketById(orderId) != null) {
            for (Ticket ord : orderTickets) {
                if (ord.getId() == (long)orderId) {
                    orderPrice -= ord.getSector().getPrice();
                    model.addAttribute("messageOrder", "Билет ID = " + ord.getId() + " удалён из заказа");
                    ticketService.deleteTicket(ticketService.getTicketById(orderId));
                    orderTickets.remove(ord);
                    break;
                }
            }
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId()) == null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId()) == null ||
                currentSector.getEvent().getDate().before(new Date())) {
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
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
    /**
     * Buy all tickets from order.
     * Verification of all booking tickets.
     * Update table on page "Order".
     **/
    @RequestMapping(value = "Order/Buy.do", method = RequestMethod.POST)
    public String orderBuy(Model model, @ModelAttribute("sectorOrder") Sector currentSector,
                           @ModelAttribute("rowOrder") Integer currentRow,
                           @ModelAttribute("eventOrder") Event currentEvent) {
        Double orderPrice = (Double) model.asMap().get("orderPrice");
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        model.addAttribute("errorOrder", "");
        model.addAttribute("messageOrder", "");
        if (orderTickets != null && orderTickets.size() > 0) {
            ArrayList<Ticket> deletingTicket = new ArrayList<>();
            for (Ticket ord : orderTickets) {
                if (ticketService.getTicketById(ord.getId()) == null) deletingTicket.add(ord);
            }
            if (deletingTicket.size() > 0){
                for (Ticket ord : orderTickets) {
                    if (ticketService.getTicketById(ord.getId()) != null) ticketService.deleteTicket(ord);
                }
                model.addAttribute("errorOrder", "Заказ автоматически удален, так как не был утверждён в течении 10 минут");
                orderTickets.clear();
                orderPrice = 0.0;
            }
            /*if (deletingTicket.size() == 1) {
                model.addAttribute("errorOrder", "Билет ID = " + deletingTicket.get(0).getId() +
                        " автоматически удален из заказа, так как не был куплен в течении 10 минут");
                orderTickets.remove(deletingTicket.get(0));
                orderPrice -= deletingTicket.get(0).getSector().getPrice();
            }
            if (deletingTicket.size() > 1) {
                StringBuilder builder = new StringBuilder(200);
                for (Ticket tic : deletingTicket) {
                    builder.append(tic.getId()).append("  ");
                    orderPrice -= tic.getSector().getPrice();
                    orderTickets.remove(tic);
                }
                model.addAttribute("errorOrder", "Билеты ID = " + builder.toString() +
                        " автоматически удалены из заказа, так как не были куплены в течении 10 минут");
            }*/
        }
        for (Ticket ticket : orderTickets) {
            if (ticketService.getTicketById(ticket.getId()).isConfirmed()) {
                if (ticketService.getTicketById(ticket.getId()).isReserved())
                    model.addAttribute("messageOrder", "ОШИБКА! Билет ID = " + ticket.getId() + " уже забронирован");
                else model.addAttribute("messageOrder", "ОШИБКА! Билет ID = " + ticket.getId() + " уже продан");
                orderPrice -= ticket.getSector().getPrice();
                orderTickets.remove(ticket);
                model.addAttribute("orderPrice", orderPrice);
                return "Order";
            }
        }
        for (Ticket ticket : orderTickets) {
            ticket.setConfirmed(true);
             ticketService.addTicket(ticket);
        }
        List<Event> events = eventService.getFutureEvents();
        if (eventService.getEventById(currentEvent.getId()) == null || currentEvent.getDate().before(new Date()))
            currentEvent = events.get(0);
        List<Sector> sectors = sectorService.getSectorsByEvent(currentEvent);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
        }
        if (sectorService.getSectorById(currentSector.getId()) == null ||
                currentSector.getEvent().getDate().before(new Date())) {
            currentSector = sectors.get(0);
            currentRow = 1;
        }
        model.addAttribute("sectorsMapOrder", sectorsMap);
        Map<Integer, Integer> rowsMap1 = new TreeMap<>();
        for (int i = 1; i <= currentSector.getMaxRows(); i++) {
            rowsMap1.put(i, ticketService.getFreeTicketsAmountBySectorRow(currentSector, i));
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
        if (orderTickets.size()==0){
            model.addAttribute("orderPrice", 0.0);
            model.addAttribute("orderList", new ArrayList<Ticket>());
            return "Order";
        }
       else{
            model.addAttribute("orderConfirmation", orderTickets);
            model.addAttribute("priceConfirmation", orderPrice);
            model.addAttribute("orderPrice", 0.0);
            model.addAttribute("orderList", new ArrayList<Ticket>());
            return "OrderInfo";
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "OrderInfo/Clear.do")
    public String orderInfoClear(Model model) {
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderConfirmation");
        orderTickets.clear();
        model.addAttribute("orderConfirmation", orderTickets);
        model.addAttribute("priceConfirmation", 0.0);
        return "Order";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Order/Cancel.do")
    public String orderCancel(Model model) {
        ArrayList<Ticket> orderTickets = (ArrayList) model.asMap().get("orderList");
        if (orderTickets != null) {
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
