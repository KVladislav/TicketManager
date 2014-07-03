package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.service.EventService;
import org.JavaArt.TicketManager.service.SectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "events", "event", "sector", "sectors"})

public class EventsController {
    private EventService eventService = new EventService();
    private List<Event> events = new ArrayList<>();
    private SectorService sectorService = new SectorService();
    private List<Sector> sectors = new ArrayList<>();
    public Event editEvent;


    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) {
        model.addAttribute("pageName", 4);//set menu page number
        List<Event> events = eventService.getAllEvents();
        Collections.sort(events);
        if (events != null && events.size() > 0) {
            model.addAttribute("event", events.get(0));
            model.addAttribute("events", events);
        }
        return "Events";
    }

    @RequestMapping(value = "NewEvent/NewEvent.do", method = RequestMethod.GET)
    public String newEventGet(Model model) {
        model.addAttribute("pageName", 7);//set menu page number

        return "NewEvent";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "evnt", required = true) int evnt, Model model, SessionStatus status) {
        Event event = eventService.getEventById(evnt);
        event.setDeleted(true);
        eventService.updateEvent(event);
        List<Sector> sectors = sectorService.getSectorsByEvent(event);
        for (Sector sector : sectors) {
            boolean isDeleted = true;
            sector.setDeleted(isDeleted);
            sectorService.updateSector(sector);
            sectors.add(sector);
        }

        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Redirect.do", method = RequestMethod.POST)
    public String eventsRedirect(Model model, SessionStatus status) {
        return "redirect:/NewEvent/NewEvent.do";
    }

    @RequestMapping(value = "NewEvent/addEvent.do", method = RequestMethod.POST)
    public String bookingAddEvent(@RequestParam(value = "dateEvent", required = true) String dateEvent, String description, String timeRemoveBooking, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException{
        if (description == null) return "redirect:/NewEvent/NewEvent.do";
        Event event = new Event();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date trueDate = format.parse(dateEvent);
        event.setDate(trueDate);
        boolean isDeleted = false;
        event.setDeleted(isDeleted);
        event.setDescription(" " + description);
        //    event.setOperator(operator);
        Date nowDate = new Date();
        event.setTimeStamp(nowDate);
        int time = Integer.parseInt(timeRemoveBooking);
        event.setBookingTimeOut(time);
        eventService.addEvent(event);
        events.add(event);

        for (int i = 0; i < 27; i++) {
            Sector sector = new Sector();
            sector.setEvent(event);
            sector.setName("" + i);
            String param = request.getParameter("price" + i);
            double somePrice = 0d;
            if (param != null && param.isEmpty() == false) {
                somePrice = Double.parseDouble(param);
            }
            sector.setPrice(somePrice);
            sector.setMaxRows(20);
            sector.setMaxSeats(50);
            sector.setDeleted(isDeleted);
            sectorService.addSector(sector);
            sectors.add(sector);
        }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }


    @RequestMapping(value = "Events/Edit.do", method = RequestMethod.POST)
    public String eventsEdit(@RequestParam(value = "evnt") int evnt, Model model, SessionStatus status, HttpServletRequest request) {
        this.editEvent = eventService.getEventById(evnt);
      //  String param = request.getParameter(String.valueOf(eventId));
      return "redirect:/EditEvent/EditEvent.do";

    }

    @RequestMapping(value = "EditEvent/EditEvent.do", method = RequestMethod.GET)
    public String editEventGet(Model model,SessionStatus statusEvent) {
        model.addAttribute("pageName", 8);//set menu page number
        model.addAttribute("eventEdit", this.editEvent);
        model.addAttribute("eventDescriptions", this.editEvent.getDescription());
        model.addAttribute("eventBookingTimeOut", this.editEvent.getBookingTimeOut());
        List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
        model.addAttribute("sectors", sectors);

        return "EditEvent";
    }


    @RequestMapping(value = "EditEvent/editEventNow.do", method = RequestMethod.POST)
    public String editEvent(@RequestParam(value = "dateEvent", required = true) String dateEvent,int eventEditHidden, String description, String timeRemoveBooking, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        if (description == null) return "redirect:/EditEvent/EditEvent.do";
        Event event = eventService.getEventById(eventEditHidden);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date trueDate = format.parse(dateEvent);
        event.setDate(trueDate);
        boolean isDeleted = false;
        event.setDeleted(isDeleted);
        event.setDescription(" " + description);
        //    event.setOperator(operator);
        Date nowDate = new Date();
        event.setTimeStamp(nowDate);
        int time = Integer.parseInt(timeRemoveBooking);
        event.setBookingTimeOut(time);
        eventService.updateEvent(event);
        List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
        int i =0;
        for (Sector sector:sectors) {
            sector.setEvent(event);
            sector.setName("" + i);
            String param = request.getParameter("price" + i);
            i++;
            double somePrice = 0d;
            if (param != null && param.isEmpty() == false) {
                somePrice = Double.parseDouble(param);
            }
            sector.setPrice(somePrice);
            sector.setMaxRows(20);
            sector.setMaxSeats(50);
            sector.setDeleted(isDeleted);
            sectorService.updateSector(sector);
         }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }


}
