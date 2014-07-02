package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Client;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.ClientService;
import org.JavaArt.TicketManager.service.EventService;
import org.JavaArt.TicketManager.service.SectorService;
import org.JavaArt.TicketManager.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@SessionAttributes({"bookingTimeOut", "pageName"})
public class BookingController {
    private String errorMessage = null;
    private TicketService ticketService = TicketService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private EventService eventService = new EventService();
    private SectorService sectorService = new SectorService();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Event> events;
    private List<Sector> sectors;
    private Map<Sector, Integer> sectorsMap;
    private Map<Integer, Integer> rowsMap;
    private Map<Integer, Boolean> seatsMap;
    private Sector sector;
    private Event event;
    private double bookingPrice = 0;
    private int row = 1;
    private Date bookingTimeOut;
    //    private Map<Client, Integer[]> clients;
    private Client client;

    @RequestMapping(value = "Booking/GetClient.do", method = RequestMethod.GET)
    public String bookingPaymentInit(Model model) throws SQLException {
        model.addAttribute("pageName", 2);//set menu page number
        model.addAttribute("client", client);
        return "Clients";
    }

    @RequestMapping(value = "Booking/ProceedClientName.do", method = RequestMethod.POST)
    public String bookingProceedClientName(Model model, @RequestParam(value = "clientName", required = true) String clientName,
                                           @RequestParam(value = "action", required = true) String action) throws SQLException {
        Map<Client, Integer[]> clients = new HashMap<>();
        if (action.equals("NewClient")) {
            client = new Client();
            client.setName(clientName);
            model.addAttribute(client);
            return "NewClient";
        } else {
            List<Client> clientsList = clientService.getClientsByName(clientName);

            if (clientsList != null) {
                for (Client client1 : clientsList) {
                    List<Ticket> tickets1 = ticketService.getTicketsByClient(client1);
                    if (tickets1 != null && tickets1.size()>0) {
                        int ticketAmount = tickets1.size();
                        int bookingPrice=0;
                        for (Ticket ticket : tickets1) {
                            bookingPrice+=ticket.getSector().getPrice();
                        }
                        clients.put(client1, new Integer[]{ticketAmount, bookingPrice});
                    }
                }
            }
        }
        model.addAttribute("clients", clients);
        return "Clients";
    }

    @RequestMapping(value = "Booking/NewClientSave.do", method = RequestMethod.POST)
    public String bookingNewClientSave(@RequestParam(value = "clientName", required = true) String
                                               clientName, @RequestParam(value = "clientDescription", required = true) String clientDescription) throws
            SQLException {
        client.setName(clientName);
        client.setDescription(clientDescription);
        clientService.saveOrUpdateClient(client);
        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/ViewClient.do", method = RequestMethod.POST)
    public String bookingViewClient(@RequestParam(value = "clientId", required = true) int clientId, Model
            model) throws SQLException {
        System.out.println(clientId);

        return "Clients";
    }


    @RequestMapping(value = "Booking/Booking.do", method = RequestMethod.GET)
    public String bookingGet(Model model) throws SQLException {
        if (client == null) {
            return "redirect:/Booking/GetClient.do";
        }

        events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            event = events.get(0);

            sectors = sectorService.getSectorsByEvent(event);
            if (sectors != null && sectors.size() > 0) {
                sector = sectors.get(0);

                sectorsMap = new TreeMap<>();
                for (Sector sector : sectors) {
                    sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
                }

                rowsMap = new TreeMap<>();
                for (int i = 1; i <= sector.getMaxRows(); i++) {
                    rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
                }

                seatsMap = new TreeMap<>();
                for (int i = 1; i <= sector.getMaxSeats(); i++) {
                    seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
                }
                row = 1;
            }
        }
        addAttributes(model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSectors.do", method = RequestMethod.POST)
    public String bookingSetSectors(@RequestParam(value = "eventId", required = true) int eventId, Model
            model) throws SQLException {
        event = eventService.getEventById(eventId);
        sectors = sectorService.getSectorsByEvent(event);
        if (sectors != null && sectors.size() > 0) {
            sector = sectors.get(0);

            sectorsMap = new TreeMap<>();
            for (Sector sector : sectors) {
                sectorsMap.put(sector, ticketService.getFreeTicketsAmountBySector(sector));
            }

            rowsMap = new TreeMap<>();
            for (int i = 1; i <= sector.getMaxRows(); i++) {
                rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
            }

            seatsMap = new TreeMap<>();
            for (int i = 1; i <= sector.getMaxSeats(); i++) {
                seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
            }
            row = 1;
        }
        addAttributes(model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow.do", method = RequestMethod.POST)
    public String bookingSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model
            model) throws SQLException {
        sector = sectorService.getSectorById(sectorId);
        rowsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxRows(); i++) {
            rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
        }

        seatsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
        }
        row = 1;
        addAttributes(model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSeat.do", method = RequestMethod.POST)
    public String bookingSetSeat(@RequestParam(value = "row", required = true) int row, Model model) throws
            SQLException {

        this.row = row;
        seatsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            seatsMap.put(i, ticketService.isPlaceFree(sector, row, i));
        }
        addAttributes(model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/addTicket.do", method = RequestMethod.POST)
    public String bookingAddTicket(@RequestParam(value = "seats", required = false) int[] seats, Model
            model) throws SQLException {
        if (seats != null) {
            if (bookingTimeOut == null) {
                bookingTimeOut = new Date();
                model.addAttribute("bookingTimeOut", bookingTimeOut);
            }
            for (int seat : seats) {
                if (ticketService.isPlaceFree(sector, row, seat)) {
                    Ticket ticket = new Ticket();
                    ticket.setSector(sector);
                    ticket.setRow(row);
                    ticket.setSeat(seat);
                    ticket.setClient(client);
                    ticket.setReserved(true);
                    bookingPrice = bookingPrice + sector.getPrice();
                    ticketService.addTicket(ticket);
                    tickets.add(ticket);
                } else {
                    errorMessage += sector.getEvent().getDescription() + " Сектор:" + sector.getName() + " Ряд: " + row + " Место:" + seat + " уже продан" + "<br>";
                }
            }
        }
        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/Finish.do", method = RequestMethod.POST)
    public String bookingFinish(SessionStatus sessionStatus) throws SQLException {
        for (Ticket ticket : tickets) {
            ticket.setConfirmed(true);
        }
        //TODO сдеать проверку на свободность, если занят, то предупреждение с рефрешем заказа
        ticketService.saveOrUpdateTickets(tickets);
        tickets.clear();
        bookingPrice = 0;
        errorMessage = null;
        bookingTimeOut = null;
        sessionStatus.setComplete();

        return "redirect:/Booking/GetClient.do";
    }

    @RequestMapping(value = "Booking/Cancel.do")
    public String bookingCancel(SessionStatus sessionStatus) throws SQLException {
        ticketService.deleteTickets(tickets);
        tickets.clear();
        bookingPrice = 0;
        errorMessage = null;
        bookingTimeOut = null;
        sessionStatus.setComplete();
        return "redirect:/Booking/GetClient.do";
    }

    @RequestMapping(value = "Booking/delTicket.do", method = RequestMethod.POST)
    public String bookingDelTicket(@RequestParam(value = "ticketId", required = true) int ticketId, Model
            model) throws SQLException {
        Ticket ticket = ticketService.getTicketById(ticketId);
        ticketService.deleteTicket(ticket);
        for (Ticket tckt : tickets) {
            if (tckt.getId() == ticketId) {
                tickets.remove(tckt);
                break;
            }
        }
        addAttributes(model);
        return "Booking";
    }

    private void addAttributes(Model model) {
        model.addAttribute("bookingPrice", bookingPrice);
        model.addAttribute("event", event);
        model.addAttribute("events", events);
        model.addAttribute("sector", sector);
        model.addAttribute("sectorsMap", sectorsMap);
        model.addAttribute("tickets", tickets);
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        model.addAttribute("rowsMap", rowsMap);
        model.addAttribute("client", client);
//        model.addAttribute("bookingTimeOut", bookingTimeOut);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
    }
}
