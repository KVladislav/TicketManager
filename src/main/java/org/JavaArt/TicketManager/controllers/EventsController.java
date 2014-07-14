package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.service.EventService;
import org.JavaArt.TicketManager.service.SectorDefaultsService;
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
@SessionAttributes({"pageName", "eventErrorMessage", "errorMessageEdit", "events", "event", "sector", "sectors"})

public class EventsController {
    public Event editEvent;
    List<Sector> sectorsAdded = new ArrayList<>();
    private EventService eventService = new EventService();
    private SectorService sectorService = new SectorService();
    private SectorDefaultsService sectorDefaultsService = new SectorDefaultsService();

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) {
        model.addAttribute("pageName", 4);
        List<Event> events = eventService.getAllEvents();
        Collections.sort(events);
        if (events != null && events.size() > 0) {
            model.addAttribute("event", events.get(0));
            model.addAttribute("events", events);
        }

        return "Events";
    }

    @RequestMapping(value = "AddEditEvent/NewEvent.do", method = RequestMethod.GET)
    public String newEventGet(Model model) {
        model.addAttribute("pageName", 7);
        List<SectorDefaults> sectorsDefaults = sectorDefaultsService.getAllSectorDefaults();
        Collections.sort(sectorsDefaults);
        if (sectorsDefaults.size() != 0) {
            sectorsAdded.clear();
            List copy = new ArrayList(sectorsDefaults);
            for (Iterator<SectorDefaults> it = copy.iterator(); it.hasNext(); ) {
                SectorDefaults sectorDefaults = it.next();
                Sector sector = new Sector();
                sector.setName(sectorDefaults.getSectorName());
                sector.setMaxRows(sectorDefaults.getMaxRows());
                sector.setMaxSeats(sectorDefaults.getMaxSeats());
                sector.setPrice(sectorDefaults.getDefaultPrice());
                sectorsAdded.add(sector);
                sectorService.addSector(sector);
                model.addAttribute("id" + sector.getId(), sector.getId());
                model.addAttribute("name" + sector.getName(), sector.getName());
            }
        }
        if (sectorsAdded != null && sectorsAdded.size() > 0) {
            model.addAttribute("sectors", sectorsAdded);
            model.addAttribute("sector", sectorsAdded.get(0));
            model.addAttribute("dateEvent", new Date());
        }
        return "AddEditEvent";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "evnt", required = true) int evnt,
                                  Model model, SessionStatus status) {
        Event event = eventService.getEventById(evnt);
        event.setDeleted(true);
        eventService.updateEvent(event);
        List<Sector> sectors = sectorService.getSectorsByEvent(event);
        if (sectors.size() != 0) {
            List copy = new ArrayList(sectors);
            for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                Sector sector = it.next();
                boolean isDeleted = true;
                sector.setDeleted(isDeleted);
                sectorService.updateSector(sector);
                sectors.add(sector);
            }
        }

        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Redirect.do", method = RequestMethod.POST)
    public String eventsRedirect(Model model, SessionStatus status) {
        return "redirect:/AddEditEvent/NewEvent.do";
    }

    @RequestMapping(value = "AddEditEvent/addEvent.do", method = RequestMethod.POST)
    public String addEvent(Model model, @RequestParam(value = "dateEvent", required = true) String dateEvent,
                           String inputTime,
                           String description,
                           String timeRemoveBooking,
                           SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        List<Event> events = eventService.getAllEvents();
        String eventErrorMessage = (String) model.asMap().get("errorMessage");
        if (!description.equals("") && !dateEvent.equals("") && (dateValid(dateEvent) != false) && !inputTime.equals("")
                && !timeRemoveBooking.equals("")) {
            Event event = new Event();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date trueDate = format.parse(dateEvent);
            int intHour = 0;
            int intMin = 0;
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
            if (isYetEvent(trueDate) == true) {
                event.setDate(trueDate);
            } else {
                eventErrorMessage += " Мероприятие на эту дату уже существует!" + "<br>";
                model.addAttribute("eventErrorMessage", eventErrorMessage);
                return "redirect:/AddEditEvent/NewEvent.do";
            }
            //  event.setDate(trueDate);
            boolean isDeleted = false;
            event.setDeleted(isDeleted);
            event.setDescription("" + description.trim().replaceAll("\\u00A0", ""));
            //    event.setOperator(operator);
            Date nowDate = new Date();
            event.setTimeStamp(nowDate);
            int time = Integer.parseInt(timeRemoveBooking);

            event.setBookingTimeOut(new Date(event.getDate().getTime() - time * 60000));
            //  event.setBookingTimeOut(time);
            eventService.addEvent(event);
            events.add(event);

            List copy = new ArrayList(sectorsAdded);
            for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                Sector sector = it.next();
                String stringPrice = request.getParameter("id" + sector.getId());
                String stringName = request.getParameter("name" + sector.getName());
                double priceTrue = Double.parseDouble(stringPrice);
                sector.setPrice(priceTrue);
                sector.setEvent(event);
                sector.setName(stringName);
                sectorsAdded.add(sector);
                sectorService.addSector(sector);
            }
        } else {
            if (eventErrorMessage == null) {
                eventErrorMessage = "" + "<br>";
            }
            if (description.equals("")) {
                eventErrorMessage += " Заполните наименование мероприятия!" + "<br>";
            }
            if (dateValid(dateEvent) == false) {
                eventErrorMessage += " Некорректно заполненная дата - дата может быть только в формате 'дд.мм.гггг' и больше текущей!" + "<br>";
            }
            if (dateEvent.equals("")) {
                eventErrorMessage += " Заполните день мероприятия!" + "<br>";
            }
            if (inputTime.equals("")) {
                eventErrorMessage += " Заполните время мероприятия!" + "<br>";
            }
            if (timeRemoveBooking.equals("")) {
                eventErrorMessage += " Заполните время удаления брони мероприятия!" + "<br>";
            }
        }
        if (eventErrorMessage != null && !eventErrorMessage.equals("")) {
            model.addAttribute("eventErrorMessage", eventErrorMessage);
            return "redirect:/AddEditEvent/NewEvent.do";
        }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Edit.do", method = RequestMethod.POST)
    public String eventsEdit(@RequestParam(value = "evnt") int evnt) {
        this.editEvent = eventService.getEventById(evnt);
        return "redirect:/AddEditEvent/EditEvent.do";

    }

    @RequestMapping(value = "AddEditEvent/EditEvent.do", method = RequestMethod.GET)
    public String editEventGet(Model model) throws SQLException, ParseException {  // , @RequestParam("eventEdit") Event eventEdit
        model.addAttribute("pageName", 7);
        model.addAttribute("eventEdit", editEvent);
        model.addAttribute("eventDescriptions", (editEvent.getDescription()).trim().replaceAll("\\u00A0", ""));
        Date fullEventBookingTimeOut = editEvent.getBookingTimeOut();
        int eventBookingTimeOut = (int) (editEvent.getDate().getTime() - fullEventBookingTimeOut.getTime()) / 60000;
        model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);

        Date date = editEvent.getDate();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        int year = gc.get(GregorianCalendar.YEAR);
        int mon = gc.get(GregorianCalendar.MONTH);
        int day = gc.get(GregorianCalendar.DATE);
        int hour = gc.get(GregorianCalendar.HOUR_OF_DAY);
        int min = gc.get(GregorianCalendar.MINUTE);
        GregorianCalendar calendarN = new GregorianCalendar();
        calendarN.set(year, mon, day, 0, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String localisedDate = dateFormat.format(calendarN.getTime());
        Date trueDate = dateFormat.parse(localisedDate);
        model.addAttribute("dateEvent", trueDate);
        String timeEvent = "";
        if (min == 0) {
            timeEvent = "" + hour + "-" + "00";
        } else {
            timeEvent = "" + hour + "-" + min;
        }
        model.addAttribute("eventTime", timeEvent);
        List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
        model.addAttribute("sectors", sectors);

        return "AddEditEvent";
    }


    @RequestMapping(value = "AddEditEvent/editEventNow.do", method = RequestMethod.POST)
    public String editEvent(Model model, @RequestParam(value = "dateEvent", required = true) String dateEvent, String inputTime, int eventEditHidden, String description, String timeRemoveBooking, SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        String errorMessageEdit = "";
        if (!description.equals("") && !dateEvent.equals("") && (dateValid(dateEvent) != false) && !inputTime.equals("") && !timeRemoveBooking.equals("")) {
            Event event = eventService.getEventById(eventEditHidden);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date trueDate = format.parse(dateEvent);
            int intHour = 0;
            int intMin = 0;

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
            if (isYetEvent(trueDate) == true) {
                event.setDate(trueDate);
            } else {
                errorMessageEdit += " Мероприятие на эту дату уже существует!" + "<br>";
                model.addAttribute("errorMessageEdit", errorMessageEdit);
                return "redirect:/AddEditEvent/EditEvent.do";
            }
            boolean isDeleted = false;
            event.setDeleted(isDeleted);
            event.setDescription("" + description.trim().replaceAll("\\u00A0", ""));
            //    event.setOperator(operator);
            Date nowDate = new Date();
            event.setTimeStamp(nowDate);
            int time = Integer.parseInt(timeRemoveBooking);
            event.setBookingTimeOut(new Date(event.getDate().getTime() - time * 60000));
            //     event.setBookingTimeOut(time);
            eventService.updateEvent(event);
            List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);

            List copy = new ArrayList(sectors);
            for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                Sector sector = it.next();
                String stringPrice = request.getParameter("id" + sector.getId());
                double priceTrue = Double.parseDouble(stringPrice);
                sector.setPrice(priceTrue);
                sector.setEvent(event);
                sectorService.updateSector(sector);
            }
        } else {
            if (errorMessageEdit == null) {
                errorMessageEdit = "" + "<br>";
            }
            if (description.equals("")) {
                errorMessageEdit += " Заполните наименование мероприятия!" + "<br>";
            }
            if (dateValid(dateEvent) == false) {
                errorMessageEdit += " Некорректно заполненная дата - дата может быть только в формате 'дд.мм.гггг' и больше текущей!" + "<br>";
            }
            if (dateEvent.equals("")) {
                errorMessageEdit += " Заполните день мероприятия!" + "<br>";
            }
            if (inputTime.equals("")) {
                errorMessageEdit += " Заполните время мероприятия!" + "<br>";
            }
            if (timeRemoveBooking.equals("")) {
                errorMessageEdit += " Заполните время удаления брони мероприятия!" + "<br>";
            }
        }
        if (errorMessageEdit != null && !errorMessageEdit.equals("")) {
            model.addAttribute("errorMessageEdit", errorMessageEdit);
            return "redirect:/AddEditEvent/EditEvent.do";
        }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }


    public boolean dateValid(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date ourDate = sdf.parse(inputDate);
            Date now = new Date();
            if (ourDate.getTime() > now.getTime()) {
                return inputDate.equals(sdf.format(ourDate));
            } else return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isYetEvent(Date date) {
        if (eventService.getEventByDate(date) != null) {
            return false;
        } else return true;
    }


}
