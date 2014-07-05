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
@SessionAttributes({"bookingTime", "pageName", "errorMessage", "tickets", "events", "sectorsMap", "rowsMap", "seatsMap", "sector", "event", "bookingPrice", "row", "bookingTimeOut", "client"})
public class BookingController {

    private TicketService ticketService = TicketService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private EventService eventService = new EventService();
    private SectorService sectorService = new SectorService();

//    private String errorMessage = null;
//    private List<Ticket> tickets = new ArrayList<>();
//    private List<Event> events;
//    private List<Sector> sectors;
//    private Map<Sector, Integer> sectorsMap;
//    private Map<Integer, Integer> rowsMap;
//    private Map<Integer, Boolean> seatsMap;
//    private Sector sector;
//    private Event event;
//    private double bookingPrice = 0;
//    private int row = 1;
//    private Date bookingTimeOut;
//    private Client client;

    @RequestMapping(value = "Booking/GetClient.do", method = RequestMethod.GET)
    public String bookingPaymentInit(Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        model.addAttribute("pageName", 2);//set menu page number
        return "Clients";
    }

    @RequestMapping(value = "Booking/ProceedClientName.do", method = RequestMethod.POST)
    public String bookingProceedClientName(Model model, @RequestParam(value = "clientName", required = true) String clientName,
                                           @RequestParam(value = "action", required = true) String action) {
        Map<Client, Integer[]> clients = new HashMap<>();
        if (action.equals("NewClient")) {
            Client client = new Client();
            client.setName(clientName);
            model.addAttribute(client);
            return "NewClient";
        } else {
            List<Client> clientsList = clientService.getClientsByName(clientName);

            if (clientsList != null) {
                for (Client client1 : clientsList) {
                    List<Ticket> tickets1 = ticketService.getTicketsByClient(client1);
                    if (tickets1 != null && tickets1.size() > 0) {
                        int ticketAmount = tickets1.size();
                        int bookingPrice = 0;
                        for (Ticket ticket : tickets1) {
                            bookingPrice += ticket.getSector().getPrice();
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
    public String bookingNewClientSave(Model model, @RequestParam(value = "clientName", required = true) String
            clientName, @RequestParam(value = "clientDescription", required = true) String clientDescription) throws
            SQLException {
        Client client = (Client) model.asMap().get("client");
        client.setName(clientName);
        client.setDescription(clientDescription);
        clientService.saveOrUpdateClient(client);
        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/ViewClient.do", method = RequestMethod.POST)
    public String bookingViewClient(@RequestParam(value = "clientId", required = true) int clientId, Model
            model) {
        System.out.println(clientId);
        Client client = clientService.getClientById(clientId);
        List<Ticket> tickets = ticketService.getTicketsByClient(client);
        model.addAttribute("client", client);
        model.addAttribute("tickets", tickets);

        return "ClientBooking";
    }


    @RequestMapping(value = "Booking/Booking.do", method = RequestMethod.GET)
    public String bookingGet(Model model) {
        if (model.asMap().get("client") == null) {
            return "redirect:/Booking/GetClient.do";
        }

        List<Event> events;
        Event event = null;
        List<Sector> sectors;
        Sector sector = null;
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        Map<Integer, Integer> rowsMap = new TreeMap<>();
        Integer row = 1;
        Map<Integer, Boolean> seatsMap = new TreeMap<>();


        events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            event = events.get(0);

            sectors = sectorService.getSectorsByEvent(event);
            if (sectors != null && sectors.size() > 0) {
                sector = sectors.get(0);

                for (Sector sector1 : sectors) {
                    sectorsMap.put(sector1, ticketService.getFreeTicketsAmountBySector(sector1));
                }

                for (int i = 1; i <= sector.getMaxRows(); i++) {
                    rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
                }

                for (int i = 1; i <= sector.getMaxSeats(); i++) {
                    seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
                }
            }
        }
        model.addAttribute("event", event);
        model.addAttribute("events", events);
        model.addAttribute("sector", sector);
        model.addAttribute("sectorsMap", sectorsMap);
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        model.addAttribute("rowsMap", rowsMap);
        model.addAttribute("bookingPrice", 0d);

        return "Booking";
    }

    @RequestMapping(value = "Booking/setSectors.do", method = RequestMethod.POST)
    public String bookingSetSectors(@RequestParam(value = "eventId", required = true) int eventId, Model
            model) {
        Event event = eventService.getEventById(eventId);
        List<Sector> sectors = sectorService.getSectorsByEvent(event);
        Sector sector = null;
        Map<Sector, Integer> sectorsMap = new TreeMap<>();
        Map<Integer, Integer> rowsMap = new TreeMap<>();
        Integer row = 1;
        Map<Integer, Boolean> seatsMap = new TreeMap<>();

        if (sectors != null && sectors.size() > 0) {
            sector = sectors.get(0);

            for (Sector sector1 : sectors) {
                sectorsMap.put(sector1, ticketService.getFreeTicketsAmountBySector(sector1));
            }

            for (int i = 1; i <= sector.getMaxRows(); i++) {
                rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
            }

            for (int i = 1; i <= sector.getMaxSeats(); i++) {
                seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
            }
        }

        model.addAttribute("event", event);
        model.addAttribute("sector", sector);
        model.addAttribute("sectorsMap", sectorsMap);
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        model.addAttribute("rowsMap", rowsMap);

        if (model.asMap().get("bookingTimeOut") != null) {
            model.addAttribute("bookingTime", (new Date().getTime() - ((Date) model.asMap().get("bookingTimeOut")).getTime()) / 1000);
        }

        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow.do", method = RequestMethod.POST)
    public String bookingSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model
            model) {
        Sector sector = sectorService.getSectorById(sectorId);
        Map<Integer, Integer> rowsMap = new TreeMap<>();
        Integer row = 1;
        Map<Integer, Boolean> seatsMap = new TreeMap<>();

        for (int i = 1; i <= sector.getMaxRows(); i++) {
            rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
        }

        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            seatsMap.put(i, ticketService.isPlaceFree(sector, 1, i));
        }

        model.addAttribute("sector", sector);
        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        model.addAttribute("rowsMap", rowsMap);
        if (model.asMap().get("bookingTimeOut") != null) {
            model.addAttribute("bookingTime", (new Date().getTime() - ((Date) model.asMap().get("bookingTimeOut")).getTime()) / 1000);
        }
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSeat.do", method = RequestMethod.POST)
    public String bookingSetSeat(@RequestParam(value = "row", required = true) Integer row, @ModelAttribute("sector") Sector sector, Model model) throws
            SQLException {


        Map<Integer, Boolean> seatsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            seatsMap.put(i, ticketService.isPlaceFree(sector, row, i));
        }

        model.addAttribute("row", row);
        model.addAttribute("seatsMap", seatsMap);
        if (model.asMap().get("bookingTimeOut") != null) {
            model.addAttribute("bookingTime", (new Date().getTime() - ((Date) model.asMap().get("bookingTimeOut")).getTime()) / 1000);
        }
        return "Booking";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/addTicket.do", method = RequestMethod.POST)
    public String bookingAddTicket(Model model, @RequestParam(value = "seats", required = false) int[] seats,
                                   @ModelAttribute("sector") Sector sector,
                                   @ModelAttribute("row") Integer row,
                                   @ModelAttribute("client") Client client,
                                   @ModelAttribute("bookingPrice") Double bookingPrice) {

        Date bookingTimeOut = (Date) model.asMap().get("bookingTimeOut");
        if (bookingTimeOut == null) {
            bookingTimeOut = new Date();
        }
        if (bookingPrice == null) {
            bookingPrice = 0d;
        }
        List<Ticket> tickets = (List) model.asMap().get("tickets");
        if (tickets == null) {
            tickets = new ArrayList<>();
        }

        String errorMessage = (String) model.asMap().get("errorMessage");
        if (seats != null) {
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
        model.addAttribute("tickets", tickets);
        model.addAttribute("bookingPrice", bookingPrice);
        model.addAttribute("bookingTimeOut", bookingTimeOut);
        model.addAttribute("bookingTime", (new Date().getTime() - bookingTimeOut.getTime()) / 1000);

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "redirect:/Booking/Booking.do";
    }

    @RequestMapping(value = "Booking/Finish.do", method = RequestMethod.POST)
    public String bookingFinish(SessionStatus sessionStatus, @ModelAttribute("tickets") List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticket.setConfirmed(true);
        }
        //TODO сдеать проверку на свободность, если занят, то предупреждение с рефрешем заказа
        ticketService.saveOrUpdateTickets(tickets);
        sessionStatus.setComplete();
        return "redirect:/Booking/GetClient.do";
    }

    @RequestMapping(value = "Booking/Cancel.do")
    public String bookingCancel(SessionStatus sessionStatus, @ModelAttribute("tickets") List<Ticket> tickets) {
        ticketService.deleteTickets(tickets);
        sessionStatus.setComplete();
        return "redirect:/Booking/GetClient.do";
    }

    @RequestMapping(value = "Booking/delTicket.do", method = RequestMethod.POST)
    public String bookingDelTicket(@RequestParam(value = "ticketId", required = true) int ticketId, Model
            model, @ModelAttribute("tickets") List<Ticket> tickets) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        ticketService.deleteTicket(ticket);
        for (Ticket tckt : tickets) {
            if (tckt.getId() == ticketId) {
                tickets.remove(tckt);
                break;
            }
        }
        if (model.asMap().get("bookingTimeOut") != null) {
            model.addAttribute("bookingTime", (new Date().getTime() - ((Date) model.asMap().get("bookingTimeOut")).getTime()) / 1000);
        }
        return "Booking";
    }

}
