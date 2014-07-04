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
import java.util.*;


@Controller
@SessionAttributes({"pageName", "events", "event", "sector", "sectors"})

public class EventsController {
    private EventService eventService = new EventService();
    private List<Event> events = new ArrayList<>();
    private SectorService sectorService = new SectorService();
    private List<Sector> sectors = new ArrayList<>();
    public Event editEvent;


    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) throws SQLException {
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
    public String newEventGet(Model model) throws SQLException {
        model.addAttribute("pageName", 7);//set menu page number

        return "NewEvent";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "evnt", required = true) int evnt, Model model, SessionStatus status) throws SQLException {
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
    public String eventsRedirect(Model model, SessionStatus status) throws SQLException {
        return "redirect:/NewEvent/NewEvent.do";
    }

    @RequestMapping(value = "NewEvent/addEvent.do", method = RequestMethod.POST)
    public String bookingAddEvent(@RequestParam(value = "dateEvent", required = true) String dateEvent,String inputTime, String description, String timeRemoveBooking, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        if (description == null) return "redirect:/NewEvent/NewEvent.do";
        Event event = new Event();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date trueDate = format.parse(dateEvent);
        int intHour = 0;
        int intMin  = 0;
        if ((inputTime != null) && (inputTime != "")) {
            String[] str = inputTime.split("-");
            intHour = Integer.parseInt(str[0]);
            intMin = Integer.parseInt(str[1]);
        }
        Calendar rightAgain = Calendar.getInstance();
        rightAgain.setTime(trueDate);
        rightAgain.add(Calendar.HOUR, intHour);
        rightAgain.add(Calendar.MINUTE, intMin);
        trueDate = rightAgain.getTime();

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
    public String eventsEdit(@RequestParam(value = "evnt") int evnt, Model model, SessionStatus status, HttpServletRequest request) throws SQLException {
        this.editEvent = eventService.getEventById(evnt);
      //  String param = request.getParameter(String.valueOf(eventId));
      return "redirect:/EditEvent/EditEvent.do";

    }

    @RequestMapping(value = "EditEvent/EditEvent.do", method = RequestMethod.GET)
    public String editEventGet(Model model,SessionStatus statusEvent) throws SQLException, ParseException {
        model.addAttribute("pageName", 8);//set menu page number
        model.addAttribute("eventEdit", this.editEvent);
        model.addAttribute("eventDescriptions", this.editEvent.getDescription());
        model.addAttribute("eventBookingTimeOut", this.editEvent.getBookingTimeOut());
        Date date = editEvent.getDate();

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        int year = gc.get(GregorianCalendar.YEAR);
        int mon = gc.get(GregorianCalendar.MONTH);
        int day = gc.get(GregorianCalendar.DATE);
        int ho = gc.get(GregorianCalendar.HOUR_OF_DAY);
        int mi = gc.get(GregorianCalendar.MINUTE);
        GregorianCalendar calendarN = new GregorianCalendar();
        calendarN.set(year,mon,day,0,0,0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String localisedDate = dateFormat.format(calendarN.getTime());
        Date trueDate = dateFormat.parse(localisedDate);
        model.addAttribute("dateEvent", trueDate);
        String timeEvent = "";
        if (mi==0){
            timeEvent  = ""+ho + "-" + "00";
        }
        else {
            timeEvent = ""+ho + "-" + mi;
        }
        model.addAttribute("eventTime",timeEvent);

        List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
        model.addAttribute("sectors", sectors);

        return "EditEvent";
    }


    @RequestMapping(value = "EditEvent/editEventNow.do", method = RequestMethod.POST)
    public String editEvent(@RequestParam(value = "dateEvent", required = true) String dateEvent,String inputTime,int eventEditHidden, String description, String timeRemoveBooking, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        if (description == null) return "redirect:/EditEvent/EditEvent.do";
        Event event = eventService.getEventById(eventEditHidden);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date trueDate = format.parse(dateEvent);
        int intHour = 0;
        int intMin  = 0;
        if ((inputTime != null) && (inputTime != "")) {
            String[] str = inputTime.split("-");
            intHour = Integer.parseInt(str[0]);
            intMin = Integer.parseInt(str[1]);
        }
        Calendar rightAgain = Calendar.getInstance();
        rightAgain.setTime(trueDate);
        rightAgain.add(Calendar.HOUR, intHour);
        rightAgain.add(Calendar.MINUTE, intMin);
        trueDate = rightAgain.getTime();
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
