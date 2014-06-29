package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.service.EventService;
import org.JavaArt.TicketManager.service.SectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "events", "event","sector","sectors"})

public class EventsController {
    private EventService eventService = new EventService();
    private String errorMessage = "";
    private List<Event> events = new ArrayList<>();
    private SectorService sectorService = new SectorService();
    private List<Sector> sectors = new ArrayList<>();

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) throws SQLException {
        model.addAttribute("pageName", 4);//set menu page number
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size()>0) {
        model.addAttribute("event", events.get(0));
        model.addAttribute("events", events);
        }
            return "Events";
    }

    @RequestMapping(value = "NewEvent/NewEvent.do", method = RequestMethod.GET)
    public String newEventGet(Model model) throws SQLException {
        model.addAttribute("pageName", 7);//set menu page number

        return "NewEvent";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "eventId", required=true) int eventId, Model model,SessionStatus status) throws SQLException {
        Event event = eventService.getEventById(eventId);
        event.setDeleted(true);
        eventService.updateEvent(event);
      //  return "Events";
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Redirect.do", method = RequestMethod.POST)
    public String eventsRedirect(Model model,SessionStatus status) throws SQLException {
        return "redirect:/NewEvent/NewEvent.do";
    }

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.POST)
    public String eventOrder(@ModelAttribute(value = "id") int id , @RequestParam(value = "event", required=true) Operator operator, Time time, SessionStatus status, Model model) throws SQLException {
     //   for (int seat : seats) {

            Event event = new Event();
            event.setDate(new Date());
            boolean isDeleted = false;
            event.setDeleted(isDeleted);
            event.setDescription("");
            event.setOperator(operator);
            event.setTimeStamp(time);
            eventService.addEvent(event);

        status.setComplete();
      //  }
        return "redirect:Events/Events.do";
    }

    @RequestMapping(value = "NewEvent/addEvent.do", method = RequestMethod.POST)
    public String bookingAddEvent(@RequestParam(value = "odate", required = true) String odate, String name, int time, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        if (name == null) return "redirect:/NewEvent/NewEvent.do";
                Event event = new Event();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                Date trueDate = format.parse(odate);
                event.setDate(trueDate);
                boolean isDeleted = false;
                event.setDeleted(isDeleted);
                event.setDescription(name);
            //    event.setOperator(operator);
                Date nowDate = new Date();
                event.setTimeStamp(nowDate);
                event.setBookingTimeOut(time);
                eventService.addEvent(event);
                events.add(event);

        for (int i=0; i<27; i++) {
            Sector sector = new Sector();
            sector.setEvent(event);
            sector.setName("+i+");
            String param = request.getParameter("price" + i);
            double somePrice = 0d;
            if (param!=null&&param.isEmpty()==false){
            somePrice = Double.parseDouble(param);}

            sector.setPrice(somePrice);
            sector.setMaxRows(50);
            sector.setDeleted(isDeleted);
            sectorService.addSector(sector);
            sectors.add(sector);
        }
        status.setComplete();
        return "redirect:/Booking/Booking.do";
    }



}
