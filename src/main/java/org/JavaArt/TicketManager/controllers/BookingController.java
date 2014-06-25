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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 19.06.2014
 * Time: 18:06
 */
@Controller
@SessionAttributes({"pageName", "events", "event", "sectorsMap", "sector", "row", "rowsMap", "seatsMap", "tickets","bookingPrice"})

public class BookingController {
    private String errorMessage = "";
    private Service service = new Service();
    private List<Ticket> tickets = new ArrayList<>();
    private Double bookingPrice=Double.valueOf(0);

    @RequestMapping(value = "Booking/Booking.do", method = RequestMethod.GET)
    public String bookingGet(Model model) throws SQLException {
        model.addAttribute("pageName", 2);//set menu page number
        List<Event> events = service.getAllEvents();
        if (errorMessage!=null) {
            model.addAttribute("errorMessage", errorMessage);
//            errorMessage = null;
        }
        model.addAttribute("bookingPrice", bookingPrice);
        if (events != null && events.size() > 0) {
            Event lastEvent = null;
            Sector lastSector = null;
            if (tickets.size() > 0) {
                lastEvent = tickets.get(tickets.size() - 1).getSector().getEvent();
                lastSector = tickets.get(tickets.size() - 1).getSector();
            }
            if (lastEvent == null) {
                lastEvent = events.get(0);
            }

            model.addAttribute("event", lastEvent);
            model.addAttribute("events", events);
            model.addAttribute("tickets", tickets);
            model.addAttribute("row", 0); //init set to fix crush for add button

            List<Sector> sectors = service.getSectorsByEvent(lastEvent);

            if (lastSector == null) {
                lastSector = sectors.get(0);
            }

            if (sectors != null && sectors.size() > 0) {
                model.addAttribute("sector", lastSector);

                Map<Sector, Integer> sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
                }
                model.addAttribute("sectorsMap", sectorsMap);

//                Map<Integer, Integer> rowsMap = new TreeMap<>();
//                for (int i = 1; i <= lastSector.getMaxRows(); i++) {
//                    rowsMap.put(i, service.getFreeTicketsAmountBySectorRow(lastSector, i));
//                }
//                model.addAttribute("rowsMap", rowsMap);

//                Map<Integer, Boolean> seatsMap = new TreeMap<>();
//                for (int i = 0; i < lastSector.getMaxSeats(); i++) {
//                    seatsMap.put(i, service.isPlaceFree(lastSector, 1, i));
//                }
//                model.addAttribute("row", lastRow);
//                model.addAttribute("seatsMap", seatsMap);
            }
        }
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSectors.do", method = RequestMethod.POST)
    public String bookingSetSectors(@RequestParam(value = "eventId", required = true) int eventId, Model model) throws SQLException {
        Event event = service.getEventById(eventId);
        model.addAttribute("event", event);
        List<Sector> sectors = service.getSectorsByEvent(event);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors) {
            sectorsMap.put(sector, service.getFreeTicketsAmountBySector(sector));
        }
        model.addAttribute("sectorsMap", sectorsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow.do", method = RequestMethod.POST)
    public String bookingSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model model) throws SQLException {
        Sector sector = service.getSectorById(sectorId);
        model.addAttribute("sector", sector);
        Map<Integer, Integer> rowsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxRows(); i++) {
            rowsMap.put(i, service.getFreeTicketsAmountBySectorRow(sector, i));
        }
        model.addAttribute("rowsMap", rowsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSeat.do", method = RequestMethod.POST)
    public String bookingSetSeat(@RequestParam(value = "row", required = true) int row, @ModelAttribute Sector sector, Model model) throws SQLException {
//        Map<Integer, Boolean> seatsMap = new TreeMap<>();
//        for (int i = 1; i <= sector.getMaxSeats(); i++) {
//            seatsMap.put(i, service.isPlaceFree(sector, row, i));
//        }
        Set<Integer> seatsMap = new TreeSet<>();
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            if (service.isPlaceFree(sector, row, i)) seatsMap.add(i);
        }
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        return "Booking";
    }

    @RequestMapping(value = "Booking/addTicket.do", method = RequestMethod.POST)
    public String bookingAddTicket(@ModelAttribute(value = "row") int row, @RequestParam(value = "seats", required = false) int[] seats, @ModelAttribute Sector sector, SessionStatus status, Model model) throws SQLException {
        if (seats == null) return "redirect:/Booking/Booking.do";
        errorMessage=" ";
        for (int seat : seats) {
            if (service.isPlaceFree(sector, row, seat)) {
                Ticket ticket = new Ticket();
                ticket.setSector(sector);
                ticket.setRow(row);
                ticket.setSeat(seat);
                bookingPrice=bookingPrice+sector.getPrice();
                service.addTicket(ticket);
                tickets.add(ticket);
            } else {
                errorMessage += sector.getEvent().getDescription() + " Сектор:" + sector.getName() + " Ряд: " + row + " Место:" + seat + " уже продан" + "<br>";
            }

        }
        status.setComplete();
        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/Finish.do", method = RequestMethod.POST)
    public String bookingFinish(SessionStatus status) throws SQLException {
        tickets.clear();
        bookingPrice=Double.valueOf(0);
        status.setComplete();
        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/Cancel.do", method = RequestMethod.POST)
    public String bookingCancel(SessionStatus status) throws SQLException {
        service.deleteTickets(tickets);
        tickets.clear();
        bookingPrice=Double.valueOf(0);
        status.setComplete();
        errorMessage=null;
        return "redirect:/Booking/Booking.do";
    }
}
