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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 19.06.2014
 * Time: 18:06
 */
@Controller
@SessionAttributes({"pageName", "bookingTickets",
        "bookingEvents", "bookingSectorsMap", "bookingRowsMap", "bookingSeatsMap",
        "bookingSector", "bookingEvent", "bookingPrice", "bookingRow", "bookingTimeOut", "bookingClient", "sectorsGroupedMap"})
public class BookingController {

    private TicketService ticketService = TicketService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private EventService eventService = new EventService();
    private SectorService sectorService = new SectorService();

    //TODO авторизация


    @RequestMapping(value = "Booking/GetClient.do", method = RequestMethod.GET)
    public String bookingPaymentInit(SessionStatus session, ModelMap model) {
        session.setComplete();
        model.addAttribute("pageName", 2);
        return "Clients";
    }

    @RequestMapping(value = "Booking/ProceedClientName.do", method = RequestMethod.POST)
    public String bookingProceedClientName(HttpSession session, ModelMap model, @RequestParam(value = "clientName", required = true) String clientName,
                                           @RequestParam(value = "action", required = true) String action) {
        model.addAttribute("pageName", 2);
        Map<Client, Integer[]> clients = new HashMap<>();
        if (action.equals("NewClient")) {
            Client client = new Client();
            client.setName(clientName);
            clientService.saveOrUpdateClient(client);
            model.addAttribute("bookingClient", client);
            return "ClientBooking";
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
        model.addAttribute("bookingClients", clients);
        return "Clients";
    }

    @RequestMapping(value = "Booking/ViewClient.do")
    public String bookingViewClient(@RequestParam(value = "clientId", required = true) Integer clientId, Model
            model) {

        if (clientId == null) {
            return "redirect:/Booking/GetClient.do";
        }
        Client client = clientService.getClientById(clientId);
        List<Ticket> tickets = ticketService.getTicketsByClient(client);

        double bookingPrice = 0;
        Date date = new Date();
        boolean isConfirmed = true;
        for (Ticket ticket : tickets) {
            if (!ticket.isConfirmed()) {
                isConfirmed = false;
                if (date.getTime() > ticket.getTimeStamp().getTime()) {
                    date = ticket.getTimeStamp();
                }
            }
            bookingPrice += ticket.getSector().getPrice();
        }
        if (!isConfirmed) {
            System.out.printf("found non confirmed");
            model.addAttribute("bookingTimeOut", date);
            model.addAttribute("bookingTime", (new Date().getTime() - date.getTime()) / 1000);
        }
        model.addAttribute("bookingPrice", bookingPrice);
        model.addAttribute("bookingClient", client);
        model.addAttribute("bookingTickets", tickets);

        return "ClientBooking";
    }

    @RequestMapping(value = "Booking/ClientSave.do", method = RequestMethod.POST)
    public String bookingClientSave(ModelMap modelMap, HttpSession session, @RequestParam(value = "clientName", required = true) String
            clientName, @RequestParam(value = "clientDescription", required = true) String clientDescription) throws
            SQLException {
        Client client = (Client) modelMap.get("bookingClient");
        client.setName(clientName);
        client.setDescription(clientDescription);
        clientService.saveOrUpdateClient(client);

        checkEventState(modelMap, session);
        return "ClientBooking";
    }

    @RequestMapping(value = "Booking/DelBookedTicket.do")
    public String bookingDelBookedTicket(HttpSession session, ModelMap modelMap, @RequestParam(value = "ticketId", required = true) int ticketId, @ModelAttribute("bookingTickets") List<Ticket> tickets) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket != null) {
            ticketService.deleteTicket(ticket);
            for (Ticket tckt : tickets) {
                if (tckt.getId() == ticketId) {
                    tickets.remove(tckt);
                    break;
                }
            }
//            double bookingPrice = (Double) modelMap.get("bookingPrice");
//            bookingPrice -= ticket.getSector().getPrice();
//            modelMap.addAttribute("bookingPrice", bookingPrice);

        }
        checkEventState(modelMap, session);
        return "ClientBooking";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/FinishOrder.do", method = RequestMethod.POST)
    public String bookingFinishOrder(SessionStatus sessionStatus, ModelMap modelMap, HttpSession session, @ModelAttribute("bookingClient") Client client) {
        checkEventState(modelMap, session);
        Date bookingTimeOut = (Date) modelMap.get("bookingTimeOut");

        if (bookingTimeOut != null && (new Date().getTime() - bookingTimeOut.getTime()) > 1000 * 60 * 5) {
            return "redirect:Booking/UndoOrder.do";
        }
        List<Ticket> tickets = (List) modelMap.get("bookingTickets");
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                ticket.setConfirmed(true);
            }
            ticketService.saveOrUpdateTickets(tickets);
        }
        //TODO сдеать проверку на свободность, если занят, то предупреждение с рефрешем заказа
//        sessionStatus.setComplete();


        List<Ticket> bookingTickets = ticketService.getTicketsByClient(client);
        modelMap.addAttribute("bookingTickets", bookingTickets);
        modelMap.addAttribute("bookingClient", client);

        return "BookingConfirmation";      //return order confirmation
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/BookingPayment.do", method = RequestMethod.POST)
    public String bookingPayment(SessionStatus sessionStatus, ModelMap modelMap, HttpSession session, @ModelAttribute("bookingClient") Client client) {
        checkEventState(modelMap, session);

        Date bookingTimeOut = (Date) modelMap.get("bookingTimeOut");

        if (bookingTimeOut != null && (new Date().getTime() - bookingTimeOut.getTime()) > 1000 * 60 * 5) {
            return "redirect:Booking/UndoOrder.do";
        }
        List<Ticket> tickets = (List) modelMap.get("bookingTickets");
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                ticket.setConfirmed(true);
                ticket.setReserved(false);
            }
            ticketService.saveOrUpdateTickets(tickets);
        }
//        sessionStatus.setComplete();
        List<Ticket> bookingTickets = ticketService.getBoughtTicketsByClient(client);
        modelMap.addAttribute("bookingTickets", bookingTickets);
        modelMap.addAttribute("bookingClient", client);

        return "BookingPayOutConfirmation";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/CancelOrder.do")
    public String bookingCancelOrder(SessionStatus session, ModelMap modelMap, HttpSession httpSession) {
        checkEventState(modelMap, httpSession);
        List<Ticket> tickets = (List) modelMap.get("bookingTickets");
        if (tickets != null) {
            ticketService.deleteTickets(tickets);
        }
        Client client = (Client) modelMap.get("bookingClient");
        clientService.deleteClient(client);
        session.setComplete();
        return "Clients";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/UndoOrder.do")
    public String bookingUndoOrder(ModelMap modelMap, HttpSession httpSession, SessionStatus session) {
        checkEventState(modelMap, httpSession);
        List<Ticket> tickets = (List) modelMap.get("bookingTickets");
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket != null && !ticket.isConfirmed()) {
                    ticketService.deleteTicket(ticket);
                }
            }
        }
        session.setComplete();
        return "Clients";
    }


    @RequestMapping(value = "Booking/Booking.do", method = RequestMethod.POST)
    public String booking(Model model) {
        if (model.asMap().get("bookingClient") == null) {
            return "redirect:/Booking/GetClient.do";
        }

        List<Event> events;
        Event event = (Event) model.asMap().get("bookingEvent");
        events = eventService.getFutureBookableEvents();
        if (events == null || events.size() == 0) {
            return "redirect:/Booking/GetClient.do";
        }
        if (event == null || eventService.getEventById(event.getId()) == null) event = events.get(0);

        model.addAttribute("bookingEvents", events);
        bookingSetSectors(event.getId(), model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSectors.do")
    public String bookingSetSectors(@RequestParam(value = "eventId", required = true) int eventId, Model
            model) {

        Event event = eventService.getEventById(eventId);
        if (event == null) return "ClientBooking";

        List<Sector> sectors = sectorService.getSectorsByEvent(event);
        Map<Sector, Integer> sectorsMap = new TreeMap<>();

        Sector sector = null;
        if (model.asMap().get("bookingEvent") != null && ((Event) model.asMap().get("bookingEvent")).getId() == eventId) {
            sector = (Sector) model.asMap().get("bookingSector");
        }
        if (sectors == null || sectors.size() == 0) {
            return "redirect:/Booking/GetClient.do";
        }
        if (sector == null) sector = sectors.get(0);

        Map<Double, List<Sector>> sectorsGroupedMap = sectorService.getSectorsByEventSortedByPrice(event);
        for (Sector sector1 : sectors) {
            sectorsMap.put(sector1, ticketService.getFreeTicketsAmountBySector(sector1));
        }

        model.addAttribute("bookingSectorsMap", sectorsMap);
        model.addAttribute("sectorsGroupedMap", sectorsGroupedMap);
        model.addAttribute("bookingEvent", event);

        bookingSetRow(sector.getId(), model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setRow.do", method = RequestMethod.POST)
    public String bookingSetRow(@RequestParam(value = "sectorId", required = true) int sectorId, Model
            model) {
        Sector sector = sectorService.getSectorById(sectorId);
        if (sector == null) return "ClientBooking";

        Map<Integer, Integer> rowsMap = new TreeMap<>();

        if (sector == null) {
            return "redirect:/Booking/GetClient.do";
        }
        for (int i = 1; i <= sector.getMaxRows(); i++) {
            rowsMap.put(i, ticketService.getFreeTicketsAmountBySectorRow(sector, i));
        }
        int row = 1;
        if (model.asMap().get("bookingSector") != null &&
                ((Sector) model.asMap().get("bookingSector")).getId() == sectorId &&
                model.asMap().get("bookingRow") != null) {
            row = (Integer) model.asMap().get("bookingRow");
        }


        model.addAttribute("bookingSector", sector);
        model.addAttribute("bookingRowsMap", rowsMap);

        bookingSetSeat(row, model);
        return "Booking";
    }

    @RequestMapping(value = "Booking/setSeat.do", method = RequestMethod.POST)
    public String bookingSetSeat(@RequestParam(value = "row", required = true) Integer row, Model model) {

        Sector sector = (Sector) model.asMap().get("bookingSector");
        if (sectorService.getSectorById(sector.getId()) == null) return "ClientBooking";
        Map<Integer, Integer> seatsMap = new TreeMap<>();
        for (int i = 1; i <= sector.getMaxSeats(); i++) {
            seatsMap.put(i, ticketService.isPlaceFree(sector, row, i));
        }

        model.addAttribute("bookingRow", row);
        model.addAttribute("bookingSeatsMap", seatsMap);
        if (model.asMap().get("bookingTimeOut") != null) {
            model.addAttribute("bookingTime", (new Date().getTime() - ((Date) model.asMap().get("bookingTimeOut")).getTime()) / 1000);
        }
        return "Booking";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/addTicket.do", method = RequestMethod.POST)
    public String bookingAddTicket(Model model, @RequestParam(value = "seats", required = false) int[] seats,
                                   @ModelAttribute("bookingSector") Sector sector,
                                   @ModelAttribute("bookingRow") Integer row,
                                   @ModelAttribute("bookingClient") Client client) {

        if (sectorService.getSectorById(sector.getId()) == null) return "ClientBooking";
        Date bookingTimeOut = (Date) model.asMap().get("bookingTimeOut");
        if (bookingTimeOut == null) {
            bookingTimeOut = new Date();
        }

        if ((new Date().getTime() - bookingTimeOut.getTime()) > 1000 * 60 * 5) {
            return "redirect:Booking/UndoOrder.do";
        }

        Double bookingPrice = (Double) model.asMap().get("bookingPrice");
        if (bookingPrice == null) {
            bookingPrice = 0d;
        }
        List<Ticket> tickets = (List) model.asMap().get("bookingTickets");
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<Ticket> bookingErrorTickets = new ArrayList<>();


        if (seats != null) {
            for (int seat : seats) {
                Ticket ticket = new Ticket();
                ticket.setSector(sector);
                ticket.setRow(row);
                ticket.setSeat(seat);
                ticket.setClient(client);


                if (ticketService.isPlaceFree(sector, row, seat) == 0) {
                    ticket.setReserved(true);
                    bookingPrice = bookingPrice + sector.getPrice();
                    ticketService.addTicket(ticket);
                    tickets.add(ticket);
                } else {
                    bookingErrorTickets.add(ticket);
                }
            }
        }
        if (bookingErrorTickets.size() > 0) {
            model.addAttribute("bookingErrorTickets", bookingErrorTickets);
        }
        model.addAttribute("bookingTickets", tickets);
        model.addAttribute("bookingPrice", bookingPrice);

        if (tickets.size()>0) {
            model.addAttribute("bookingTimeOut", bookingTimeOut);
            model.addAttribute("bookingTime", (new Date().getTime() - bookingTimeOut.getTime()) / 1000);
        }

        return "ClientBooking";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Booking/Cancel.do")
    public String bookingCancel(ModelMap modelMap, HttpSession session) {
        checkEventState(modelMap, session);

        return "ClientBooking";
    }

    @SuppressWarnings("unchecked")
    public void checkEventState(ModelMap modelMap, HttpSession session) {
        List<Ticket> tickets = (List) modelMap.get("bookingTickets");
        if  (tickets == null || tickets.size()==0) return;
//        List<String> bookingErrorMessages = new ArrayList<>();
        Map<String, String> bookingErrorMessagesMap = new HashMap<>();

        List<Ticket> ticketsForRemove = new ArrayList<>();
        double bookingPrice=0;

        for (Ticket ticket : tickets) {
            Event event =  ticket.getSector().getEvent();
            Sector sector = ticket.getSector();
            Event event1 = eventService.getEventById(event.getId());
            if (event1==null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
                bookingErrorMessagesMap.put("e" + event.getId(), "Внимание, мероприятие " + event.getDescription() + " от " + simpleDateFormat.format(event.getDate())
                        + " удалено, поэтому все билеты удалены также");
                ticketsForRemove.add(ticket);
            } else

            if (sectorService.getSectorById(sector.getId())==null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
                bookingErrorMessagesMap.put("s" + sector.getId(), "Внимание, у мероприятия " + event.getDescription() + " от " + simpleDateFormat.format(event.getDate())
                        + " удален сектор " + sector.getName() + ", поэтому все билеты из этого сектора также удалены");
                ticketsForRemove.add(ticket);
            }

        }
        tickets.removeAll(ticketsForRemove);

        Date date = new Date();
        boolean isConfirmed = true;
        for (Ticket ticket : tickets) {
            if (!ticket.isConfirmed()) {
                isConfirmed = false;
                if (date.getTime() > ticket.getTimeStamp().getTime()) {
                    date = ticket.getTimeStamp();
                }
            }
            bookingPrice += ticket.getSector().getPrice();
        }
        if (!isConfirmed) {
            modelMap.addAttribute("bookingTimeOut", date);
            modelMap.addAttribute("bookingTime", (new Date().getTime() - date.getTime()) / 1000);
        } else {
                modelMap.remove("bookingTimeOut");
                session.removeAttribute("bookingTimeOut");
            }

        Collection<String> bookingErrorMessages = bookingErrorMessagesMap.values();
        modelMap.addAttribute("bookingErrorMessages", bookingErrorMessages);
        modelMap.addAttribute("bookingPrice", bookingPrice);
    }
}
