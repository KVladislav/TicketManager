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
@SessionAttributes({"pageName", "eventErrorMessage", "errorMessageEdit", "events", "event",
        "sector", "sectors", "allSectors", "eventEditHidden", "eventTime", "eventDescriptions", "eventBookingTimeOut",
        "dateEvent", "sectorName", "maxRows", "maxSeats", "newPrice"})

public class EventsController {
    public Event editEvent;
    private EventService eventService = EventService.getInstance();
    private SectorService sectorService = new SectorService();
    private SectorDefaultsService sectorDefaultsService = new SectorDefaultsService();

    @RequestMapping(value = "Events/Events.do", method = RequestMethod.GET)
    public String eventGet(Model model) {
        model.addAttribute("pageName", 4);
        List<Event> events = eventService.getAllEvents();
        if (events != null && events.size() > 0) {
            Collections.sort(events);
        }
        if (events != null && events.size() > 0) {
            model.addAttribute("event", events.get(0));
            model.addAttribute("events", events);
        }
        return "Events";
    }

    @RequestMapping(value = "AddEditEvent/NewEvent.do", method = RequestMethod.GET)
    public String newEventGet(Model model) throws ParseException {
        model.addAttribute("pageName", 4);
        String eventErrorMessage = (String) model.asMap().get("eventErrorMessage");
        String errorMessageEdit = (String) model.asMap().get("errorMessageEdit");
        if ((errorMessageEdit != null) || (eventErrorMessage != null)) {
            model.addAttribute("errorMessageEdit", "");
            model.addAttribute("eventErrorMessage", "");
        }
        model.addAttribute("eventDescriptions", "");
        model.addAttribute("eventBookingTimeOut", 0);
        List<SectorDefaults> sectorsDefaults = sectorDefaultsService.getAllSectorDefaults();
        if (sectorsDefaults != null && sectorsDefaults.size() != 0) {
            Collections.sort(sectorsDefaults);
        }
        Map<String, Sector> allSectors = new TreeMap<>();
        if (sectorsDefaults != null && sectorsDefaults.size() != 0) {
            allSectors.clear();
            List copy = new ArrayList(sectorsDefaults);
            for (Iterator<SectorDefaults> it = copy.iterator(); it.hasNext(); ) {
                SectorDefaults sectorDefaults = it.next();
                Sector sector = new Sector();
                sector.setName(sectorDefaults.getSectorName());
                sector.setMaxRows(sectorDefaults.getMaxRows());
                sector.setMaxSeats(sectorDefaults.getMaxSeats());
                sector.setPrice(sectorDefaults.getDefaultPrice());
                sectorService.addSector(sector);
                model.addAttribute("price" + sector.getId(), sector.getId());
                allSectors.put(sector.getName(), sector);
            }
        }
        if (allSectors != null && allSectors.size() > 0) {
            model.addAttribute("allSectors", allSectors);
        }
        Date today = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(today);
        int year = gc.get(GregorianCalendar.YEAR);
        int mon = gc.get(GregorianCalendar.MONTH);
        int day = gc.get(GregorianCalendar.DATE);
        GregorianCalendar calendarN = new GregorianCalendar();
        calendarN.set(year, mon, day + 1, 0, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String localisedDate = dateFormat.format(calendarN.getTime());
        Date dateEvent = dateFormat.parse(localisedDate);
        String eventTime = "12:00";
        model.addAttribute("eventTime", eventTime);
        model.addAttribute("dateEvent", dateEvent);
        return "AddEditEvent";
    }

    @RequestMapping(value = "Events/setDelete.do", method = RequestMethod.POST)
    public String eventsSetDelete(@RequestParam(value = "eventId", required = true) Long eventId,
                                  SessionStatus status, Model model) {
        Event event = eventService.getEventById(eventId);
        if (eventService.busyEvent(event) == false) {
            eventService.deleteEvent(event);
            status.setComplete();
            return "redirect:/Events/Events.do";
        } else {
            String eventsErrorMessage = "Это мероприятие удалить нельзя, так как на него уже куплены билеты!";
            model.addAttribute("eventsErrorMessage", eventsErrorMessage);

            return "Events";
        }
    }

    @RequestMapping(value = "Events/Redirect.do", method = RequestMethod.POST)
    public String eventsRedirect() {
        return "redirect:/AddEditEvent/NewEvent.do";
    }

    @RequestMapping(value = "AddEditEvent/addEvent.do", method = RequestMethod.POST)
    public String addEvent(Model model,
                           SessionStatus status, HttpServletRequest request) throws SQLException, ParseException {
        List<Event> events = eventService.getAllEvents();
        String eventErrorMessage = null;
        Map allSectors = (TreeMap) model.asMap().get("allSectors");

        String dateEvent = request.getParameter("dateEvent");
        String eventTime = request.getParameter("eventTime");
        String eventDescriptions = request.getParameter("eventDescriptions");
        String eventBookingTimeOutS = request.getParameter("eventBookingTimeOut");
        int eventBookingTimeOut = 0;
        if (eventBookingTimeOutS != null) {
            eventBookingTimeOut = Integer.parseInt(eventBookingTimeOutS);
        }
        String action = request.getParameter("delete");
        Long idSectorDel;
        if (action != null) {
            idSectorDel = Long.parseLong(action);
            Sector sector = sectorService.getSectorById(idSectorDel);
            if (sectorService.busySector(sector) == false) {
                sectorService.deleteSector(sector);
                allSectors.values().remove(sector);
                Iterator<Sector> sectorNewList;
                if (allSectors != null) {
                    sectorNewList = allSectors.values().iterator();
                } else {
                    allSectors = new TreeMap<>();
                    sectorNewList = allSectors.values().iterator();
                }
                while (sectorNewList.hasNext()) {
                    Sector sectorNew = sectorNewList.next();
                    String price = request.getParameter("price" + sectorNew.getId());
                    if (price != null) {
                        double priceNew = Double.parseDouble(price);
                        if (sectorNew.getPrice() != priceNew) {
                            sectorNew.setPrice(priceNew);
                            sectorService.updateSector(sectorNew);
                        }
                    }
                    model.addAttribute("price" + sectorNew.getId(), sectorNew.getId());
                    allSectors.put(sectorNew.getName(), sectorNew);
                }

                if (allSectors != null && allSectors.size() > 0) {
                    model.addAttribute("allSectors", allSectors);
                }
                model.addAttribute("eventDescriptions", eventDescriptions);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
                int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
                String timeEvent;
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            } else {
                String sectorErrorMessage = "Этот сектор удалить нельзя, так как на мероприятие уже куплены билеты!";
                model.addAttribute("sectorErrorMessage", sectorErrorMessage);
                if (allSectors != null) {
                    model.addAttribute("allSectors", allSectors);
                }
                model.addAttribute("eventDescriptions", eventDescriptions);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
                int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
                String timeEvent;
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            }
        }
        String action1 = request.getParameter("action");
        if (action1 != null && action1.equals("save")) {
            if (allSectors == null || allSectors.size() == 0) {
                eventErrorMessage = " Ошибка - нельзя создавать мероприятия без секторов!" + "<br>";
                model.addAttribute("eventErrorMessage", eventErrorMessage);
                model.addAttribute("eventDescriptions", eventDescriptions);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Iterator<Sector> sectorNewList = null;
                if (allSectors != null) {
                    sectorNewList = allSectors.values().iterator();
                } else {
                    allSectors = new TreeMap<>();
                    sectorNewList = allSectors.values().iterator();
                }
                while (sectorNewList.hasNext()) {
                    Sector sectorNew = sectorNewList.next();
                    String price = request.getParameter("price" + sectorNew.getId());
                    try {
                        Double priceNew = Double.parseDouble(price);
                        sectorNew.setPrice(priceNew);
                        model.addAttribute("id" + sectorNew.getId(), sectorNew.getId());
                        allSectors.put(sectorNew.getName(), sectorNew);

                    } catch (Exception e) {
                    }
                }

                model.addAttribute("allSectors", allSectors);
                Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
                int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
                String timeEvent = "";
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            }
            if (!eventDescriptions.equals("") && !dateEvent.equals("") && (eventService.dateValid(dateEvent) != false) && !eventTime.equals("")
                    && eventBookingTimeOut != 0) {
                Event event = new Event();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date trueDate = format.parse(dateEvent);
                int intHour = 0;
                int intMin = 0;
                if ((eventTime != null) && (eventTime != "")) {
                    String[] str = eventTime.split(":");
                    intHour = Integer.parseInt(str[0]);
                    intMin = Integer.parseInt(str[1]);
                }
                Calendar rightAgain = Calendar.getInstance();
                rightAgain.setTime(trueDate);
                rightAgain.add(Calendar.HOUR, intHour);
                rightAgain.add(Calendar.MINUTE, intMin);
                Date trueDateNew = rightAgain.getTime();

                List<Event> list = eventService.getEventsByDate(trueDateNew);
                if ((list == null) || (list.size() == 0)) {
                    if (eventService.doMatch(eventDescriptions) == false) {
                        String eventNameErrorMessage = "Поле Наименование не прошло валидацию и содержит запрещенные символы!";
                        model.addAttribute("eventNameErrorMessage", eventNameErrorMessage);
                        if (allSectors != null) {
                            model.addAttribute("allSectors", allSectors);
                        }
                        model.addAttribute("eventDescriptions", eventDescriptions);
                        model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                        Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
                        model.addAttribute("dateEvent", simpleDate);
                        int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
                        int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
                        String timeEvent;
                        if (min == 0) {
                            timeEvent = "" + hour + ":" + "00";
                        } else {
                            timeEvent = "" + hour + ":" + min;
                        }
                        model.addAttribute("eventTime", timeEvent);
                        return "AddEditEvent";
                    }
                    event.setDate(trueDateNew);
                    boolean isDeleted = false;
                    event.setDeleted(isDeleted);
                    event.setDescription("" + eventDescriptions.trim());
                    Date nowDate = new Date();
                    event.setTimeStamp(nowDate);
                    event.setBookingTimeOut(new Date(event.getDate().getTime() - eventBookingTimeOut * 60000));
                    eventService.addEvent(event);
                    events.add(event);
                    Iterator<Sector> sectorNewList;
                    if (allSectors != null) {
                        sectorNewList = allSectors.values().iterator();
                    } else {
                        allSectors = new TreeMap<>();
                        sectorNewList = allSectors.values().iterator();
                    }
                    while (sectorNewList.hasNext()) {
                        Sector sectorNew = sectorNewList.next();
                        String price = request.getParameter("price" + sectorNew.getId());
                        try {
                            Double priceNew = Double.parseDouble(price);
                            sectorNew.setPrice(priceNew);
                            sectorNew.setEvent(event);
                        } catch (Exception e) {
                        }
                        sectorService.updateSector(sectorNew);
                    }
                    event.setDate(trueDate);
                } else {
                    eventErrorMessage = " Мероприятие на эту дату уже существует!" + "<br>";
                }
            } else {
                if (eventErrorMessage == null) {
                    eventErrorMessage = "" + "<br>";
                }
                if (eventDescriptions.equals("")) {
                    eventErrorMessage += " Заполните наименование мероприятия!" + "<br>";
                }
                if (eventService.dateValid(dateEvent) == false) {
                    eventErrorMessage += " Некорректно заполненная дата - дата может быть только в формате 'дд.мм.гггг' и больше текущей!" + "<br>";
                }
                if (dateEvent.equals("")) {
                    eventErrorMessage += " Заполните день мероприятия!" + "<br>";
                }
                if (eventTime.equals("")) {
                    eventErrorMessage += " Заполните время мероприятия!" + "<br>";
                }
                if (eventBookingTimeOut == 0) {
                    eventErrorMessage += " Заполните время удаления брони мероприятия!" + "<br>";
                }
            }
        }
        String action2 = request.getParameter("newSector");
        if (action2 != null && action2.equals("newSector")) {
            model.addAttribute("pageName", 4);
            Iterator<Sector> sectorNewList;
            if (allSectors != null) {
                sectorNewList = allSectors.values().iterator();
            } else {
                allSectors = new TreeMap<>();
                sectorNewList = allSectors.values().iterator();
            }
            while (sectorNewList.hasNext()) {
                Sector sectorNew = sectorNewList.next();
                String price = request.getParameter("price" + sectorNew.getId());
                if (price != null) {
                    double priceNew = Double.parseDouble(price);
                    if (sectorNew.getPrice() != priceNew) {
                        sectorNew.setPrice(priceNew);
                        sectorService.updateSector(sectorNew);
                    }
                }
                model.addAttribute("price" + sectorNew.getId(), sectorNew.getId());
                allSectors.put(sectorNew.getName(), sectorNew);
            }
            if (allSectors != null) {
                model.addAttribute("allSectors", allSectors);
            }
            Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
            model.addAttribute("dateEvent", simpleDate);
            int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
            int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
            String timeEvent = "";
            if (min == 0) {
                timeEvent = "" + hour + ":" + "00";
            } else {
                timeEvent = "" + hour + ":" + min;
            }
            model.addAttribute("eventEdit", editEvent);
            model.addAttribute("eventTime", timeEvent);
            if (eventDescriptions != null) {
                model.addAttribute("eventDescriptions", eventDescriptions);
            }
            if (eventBookingTimeOut != 0) {
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
            }
            return "NewSector";
        }
        if (eventErrorMessage != null && !eventErrorMessage.equals("")) {
            model.addAttribute("eventErrorMessage", eventErrorMessage);
            model.addAttribute("eventDescriptions", eventDescriptions);
            model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
            Iterator<Sector> sectorNewList = null;
            if (allSectors != null) {
                sectorNewList = allSectors.values().iterator();
            } else {
                allSectors = new TreeMap<>();
                sectorNewList = allSectors.values().iterator();
            }
            while (sectorNewList.hasNext()) {
                Sector sectorNew = sectorNewList.next();
                String price = request.getParameter("price" + sectorNew.getId());
                try {
                    Double priceNew = Double.parseDouble(price);
                    sectorNew.setPrice(priceNew);
                    model.addAttribute("id" + sectorNew.getId(), sectorNew.getId());
                    allSectors.put(sectorNew.getName(), sectorNew);

                } catch (Exception e) {
                }
            }

            model.addAttribute("allSectors", allSectors);
            Date simpleDate = (Date) eventService.getDateByString(dateEvent, eventTime).get(0);
            model.addAttribute("dateEvent", simpleDate);
            int hour = (int) eventService.getDateByString(dateEvent, eventTime).get(1);
            int min = (int) eventService.getDateByString(dateEvent, eventTime).get(2);
            String timeEvent = "";
            if (min == 0) {
                timeEvent = "" + hour + ":" + "00";
            } else {
                timeEvent = "" + hour + ":" + min;
            }
            model.addAttribute("eventTime", timeEvent);
            return "AddEditEvent";
        }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "Events/Edit.do", method = RequestMethod.POST)
    public String eventsEdit(@RequestParam(value = "evnt") Long evnt) {
        this.editEvent = eventService.getEventById(evnt);
        return "redirect:/AddEditEvent/EditEvent.do";
    }

    @RequestMapping(value = "AddEditEvent/EditEvent.do", method = RequestMethod.GET)
    public String editEventGet(Model model)
            throws SQLException, ParseException {
        model.addAttribute("pageName", 4);
        model.addAttribute("eventEdit", editEvent);
        String eventErrorMessage = (String) model.asMap().get("eventErrorMessage");
        String errorMessageEdit = (String) model.asMap().get("errorMessageEdit");
        if ((errorMessageEdit != null) || (eventErrorMessage != null)) {
            model.addAttribute("errorMessageEdit", "");
            model.addAttribute("eventErrorMessage", "");
        }
        Map<String, Sector> allSectors = new TreeMap<>();

        List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
        if (sectors != null) {
            if (sectors.size() != 0) {
                List copy = new ArrayList(sectors);
                for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                    Sector sector = it.next();
                    model.addAttribute("id" + sector.getId(), sector.getId());
                    allSectors.put(sector.getName(), sector);
                }
            }
        }
        model.addAttribute("allSectors", allSectors);
        model.addAttribute("eventDescriptions", (editEvent.getDescription()).trim());
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
        String timeEvent;
        if (min == 0) {
            timeEvent = "" + hour + ":" + "00";
        } else {
            timeEvent = "" + hour + ":" + min;
        }
        model.addAttribute("eventTime", timeEvent);
        return "AddEditEvent";
    }

    @RequestMapping(value = "AddEditEvent/editEventNow.do", method = RequestMethod.POST)
    public String editEvent(Model model,
                            Long eventEditHidden,
                            SessionStatus status, HttpServletRequest request)
            throws SQLException, ParseException {
        String errorMessageEdit = "";
        Map allSectors = (TreeMap) model.asMap().get("allSectors");

        String dateEventN = request.getParameter("dateEvent");
        String eventTimeN = request.getParameter("eventTime");
        String eventDescriptionsN = request.getParameter("eventDescriptions");
        String eventBookingTimeOutN = request.getParameter("eventBookingTimeOut");

        String action = request.getParameter("delete");
        Long idSectorDel;
        if (action != null) {
            idSectorDel = Long.parseLong(action);
            int eventBookingTimeOut = 0;
            if (eventBookingTimeOutN != null) {
                eventBookingTimeOut = Integer.parseInt(eventBookingTimeOutN);
            }
            Sector sector = sectorService.getSectorById(idSectorDel);
            Event event = eventService.getEventById(eventEditHidden);
            if (eventService.busyEvent(event) == false) {
                sectorService.deleteSector(sector);
                allSectors.values().remove(sector);

                Iterator<Sector> sectorNewList;
                if (allSectors != null) {
                    sectorNewList = allSectors.values().iterator();
                } else {
                    allSectors = new TreeMap<>();
                    sectorNewList = allSectors.values().iterator();
                }
                while (sectorNewList.hasNext()) {
                    Sector sectorNew = sectorNewList.next();
                    String price = request.getParameter("price" + sectorNew.getId());
                    if (price != null) {
                        double priceNew = Double.parseDouble(price);
                        if (sectorNew.getPrice() != priceNew) {
                            sectorNew.setPrice(priceNew);
                            sectorService.updateSector(sectorNew);
                        }
                    }
                    model.addAttribute("price" + sectorNew.getId(), sectorNew.getId());
                    allSectors.put(sectorNew.getName(), sectorNew);
                }
                model.addAttribute("allSectors", allSectors);
                model.addAttribute("eventDescriptions", eventDescriptionsN);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
                int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
                String timeEvent;
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventEdit", editEvent);
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            } else {
                String sectorErrorMessage = "Этот сектор удалить нельзя, так как на мероприятие уже куплены билеты!";
                model.addAttribute("sectorErrorMessage", sectorErrorMessage);
                if (allSectors != null) {
                    model.addAttribute("allSectors", allSectors);
                }
                model.addAttribute("eventDescriptions", eventDescriptionsN);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
                int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
                String timeEvent;
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventEdit", editEvent);
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            }
        }
        int eventBookingTimeOut = 0;
        if (eventBookingTimeOutN != null) {
            eventBookingTimeOut = Integer.parseInt(eventBookingTimeOutN);
        }
        String action1 = request.getParameter("action");
        if (action1 != null && action1.equals("save")) {
            if (allSectors == null || allSectors.size() == 0) {
                errorMessageEdit = " Ошибка - нельзя создавать мероприятия без секторов!" + "<br>";
                model.addAttribute("eventErrorMessage", errorMessageEdit);
                model.addAttribute("eventDescriptions", eventDescriptionsN);

                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                Iterator<Sector> sectorNewList = null;
                if (allSectors != null) {
                    sectorNewList = allSectors.values().iterator();
                } else {
                    allSectors = new TreeMap<>();
                    sectorNewList = allSectors.values().iterator();
                }
                while (sectorNewList.hasNext()) {
                    Sector sectorNew = sectorNewList.next();
                    String price = request.getParameter("price" + sectorNew.getId());
                    try {
                        Double priceNew = Double.parseDouble(price);
                        sectorNew.setPrice(priceNew);
                        model.addAttribute("id" + sectorNew.getId(), sectorNew.getId());
                        allSectors.put(sectorNew.getName(), sectorNew);

                    } catch (Exception e) {
                    }
                }

                model.addAttribute("allSectors", allSectors);
                Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
                int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
                String timeEvent;
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventTime", timeEvent);
                return "AddEditEvent";
            }
            if (!eventDescriptionsN.equals("") && !dateEventN.equals("") && (eventService.dateValid(dateEventN) != false) && !eventTimeN.equals("") && !eventBookingTimeOutN.equals("")) {
                Event event = eventService.getEventById(eventEditHidden);
                Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                int intHour = 0;
                int intMin = 0;
                if ((eventTimeN != null) && (eventTimeN != "")) {
                    String[] str = eventTimeN.split(":");
                    intHour = Integer.parseInt(str[0]);
                    intMin = Integer.parseInt(str[1]);
                }
                Calendar rightAgain = Calendar.getInstance();
                rightAgain.setTime(simpleDate);
                rightAgain.add(Calendar.HOUR, intHour);
                rightAgain.add(Calendar.MINUTE, intMin);
                Date trueDate = rightAgain.getTime();

                List<Event> list = eventService.getEventsByDateFromEvent(trueDate, event);
                eventBookingTimeOut = 0;
                if (eventBookingTimeOutN != null) {
                    eventBookingTimeOut = Integer.parseInt(eventBookingTimeOutN);
                }
                if (list.size() == 0) {
                    if (eventService.doMatch(eventDescriptionsN) == false) {
                        String eventNameErrorMessage = "Поле Наименование не прошло валидацию и содержит запрещенные символы!";
                        model.addAttribute("eventNameErrorMessage", eventNameErrorMessage);
                        if (allSectors != null) {
                            model.addAttribute("allSectors", allSectors);
                        }
                        model.addAttribute("eventEdit", editEvent);
                        model.addAttribute("eventDescriptions", eventDescriptionsN);
                        model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
                        Date editDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                        model.addAttribute("dateEvent", editDate);
                        int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
                        int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
                        String timeEvent;
                        if (min == 0) {
                            timeEvent = "" + hour + ":" + "00";
                        } else {
                            timeEvent = "" + hour + ":" + min;
                        }
                        model.addAttribute("eventTime", timeEvent);
                        return "AddEditEvent";
                    }
                    event.setDate(trueDate);
                    boolean isDeleted = false;
                    event.setDeleted(isDeleted);
                    event.setDescription("" + eventDescriptionsN.trim());
                    Date nowDate = new Date();
                    event.setTimeStamp(nowDate);
                    int time = Integer.parseInt(eventBookingTimeOutN);
                    event.setBookingTimeOut(new Date(event.getDate().getTime() - time * 60000));
                    if (eventService.busyEvent(event) == false) {
                        eventService.updateEvent(event);
                        Iterator<Sector> sectorNewList = allSectors.values().iterator();
                        while (sectorNewList.hasNext()) {
                            Sector sectorNew = sectorNewList.next();
                            String price = request.getParameter("price" + sectorNew.getId());
                            try {
                                Double priceNew = Double.parseDouble(price);
                                sectorNew.setPrice(priceNew);
                                sectorNew.setEvent(event);
                            } catch (Exception e) {
                            }
                            sectorService.updateSector(sectorNew);
                        }
                    } else {
                        errorMessageEdit = " Мероприятие c проданными билетами не редактируется!" + "<br>";
                    }
                } else {
                    errorMessageEdit = " Мероприятие на эту дату уже существует!" + "<br>";
                }
            } else {

                if (errorMessageEdit == null) {
                    errorMessageEdit = "" + "<br>";
                }
                if (eventDescriptionsN.equals("")) {
                    errorMessageEdit += " Заполните наименование мероприятия!" + "<br>";
                }
                if (eventService.dateValid(dateEventN) == false) {
                    errorMessageEdit += " Некорректно заполненная дата - дата может быть только в формате 'дд.мм.гггг' и больше текущей!" + "<br>";
                }
                if (dateEventN.equals("")) {
                    errorMessageEdit += " Заполните день мероприятия!" + "<br>";
                }
                if (eventTimeN.equals("")) {
                    errorMessageEdit += " Заполните время мероприятия!" + "<br>";
                }
                if (eventBookingTimeOutN.equals("")) {
                    errorMessageEdit += " Заполните время удаления брони мероприятия!" + "<br>";
                }
            }
            model.addAttribute("allSectors", allSectors);
            if (errorMessageEdit != null && !errorMessageEdit.equals("")) {
                model.addAttribute("errorMessageEdit", errorMessageEdit);
                model.addAttribute("eventDescriptions", eventDescriptionsN);
                int time = Integer.parseInt(eventBookingTimeOutN);
                model.addAttribute("eventBookingTimeOut", time);
                List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
                if (sectors.size() != 0) {
                    List copy = new ArrayList(sectors);
                    for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                        Sector sector = it.next();
                        model.addAttribute("id" + sector.getId(), sector.getId());
                        allSectors.put(sector.getName(), sector);
                    }
                }
                model.addAttribute("allSectors", allSectors);
                Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
                model.addAttribute("dateEvent", simpleDate);
                int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
                int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
                String timeEvent = "";
                if (min == 0) {
                    timeEvent = "" + hour + ":" + "00";
                } else {
                    timeEvent = "" + hour + ":" + min;
                }
                model.addAttribute("eventTime", timeEvent);
                model.addAttribute("eventEdit", editEvent);
                return "AddEditEvent";
            }
        }
        String action2 = request.getParameter("newSector");
        if (action2 != null && action2.equals("newSector")) {
            model.addAttribute("pageName", 4);
            Iterator<Sector> sectorNewList;
            if (allSectors != null) {
                sectorNewList = allSectors.values().iterator();
            } else {
                allSectors = new TreeMap<>();
                sectorNewList = allSectors.values().iterator();
            }
            while (sectorNewList.hasNext()) {
                Sector sectorNew = sectorNewList.next();
                String price = request.getParameter("price" + sectorNew.getId());
                if (price != null) {
                      double priceNew = Double.parseDouble(price);
                    if (sectorNew.getPrice() != priceNew) {
                        sectorNew.setPrice(priceNew);
                        sectorService.updateSector(sectorNew);
                    }
                }
                model.addAttribute("price" + sectorNew.getId(), sectorNew.getId());
                allSectors.put(sectorNew.getName(), sectorNew);
            }
            if (eventEditHidden != null) {
                model.addAttribute("eventEditHidden", eventEditHidden);
            }
            if (editEvent != null) {
                model.addAttribute("eventEdit", eventService.getEventById(eventEditHidden));
            }
            if (allSectors != null) {
                model.addAttribute("allSectors", allSectors);
            }
            Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
            model.addAttribute("dateEvent", simpleDate);
            int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
            int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
            String timeEvent = "";
            if (min == 0) {
                timeEvent = "" + hour + ":" + "00";
            } else {
                timeEvent = "" + hour + ":" + min;
            }
            model.addAttribute("eventEdit", editEvent);
            model.addAttribute("eventTime", timeEvent);
            if (eventDescriptionsN != null) {
                model.addAttribute("eventDescriptions", eventDescriptionsN);
            }
            if (eventBookingTimeOutN != null) {
                eventBookingTimeOut = Integer.parseInt(eventBookingTimeOutN);
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
            }
            return "NewSector";
        }
        model.addAttribute("allSectors", allSectors);
        if (errorMessageEdit != null && !errorMessageEdit.equals("")) {
            model.addAttribute("errorMessageEdit", errorMessageEdit);
            model.addAttribute("eventDescriptions", eventDescriptionsN);
            int time = Integer.parseInt(eventBookingTimeOutN);
            model.addAttribute("eventBookingTimeOut", time);

            List<Sector> sectors = sectorService.getSectorsByEvent(editEvent);
            if (sectors.size() != 0) {
                List copy = new ArrayList(sectors);
                for (Iterator<Sector> it = copy.iterator(); it.hasNext(); ) {
                    Sector sector = it.next();
                    model.addAttribute("id" + sector.getId(), sector.getId());
                    allSectors.put(sector.getName(), sector);
                }
            }
            model.addAttribute("allSectors", allSectors);
            Date simpleDate = (Date) eventService.getDateByString(dateEventN, eventTimeN).get(0);
            model.addAttribute("dateEvent", simpleDate);
            int hour = (int) eventService.getDateByString(dateEventN, eventTimeN).get(1);
            int min = (int) eventService.getDateByString(dateEventN, eventTimeN).get(2);
            String timeEvent = "";
            if (min == 0) {
                timeEvent = "" + hour + ":" + "00";
            } else {
                timeEvent = "" + hour + ":" + min;
            }
            model.addAttribute("eventEdit", editEvent);
            model.addAttribute("eventTime", timeEvent);
            return "AddEditEvent";
        }
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @RequestMapping(value = "AddEditEvent/setDeleteSector.do", method = RequestMethod.POST)
    public String setDeleteSector(@RequestParam(value = "evnt", required = true) Long evnt,
                                  SessionStatus status) {
        Event event = eventService.getEventById(evnt);
        eventService.deleteEvent(event);
        status.setComplete();
        return "redirect:/Events/Events.do";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Events/Cancel.do", method = RequestMethod.GET)
    public String cancel(Model model) {
        model.addAttribute("pageName", 4);
        List<Event> events = eventService.getAllEvents();
        Collections.sort(events);
        if (events != null && events.size() > 0) {
            model.addAttribute("event", events.get(0));
            model.addAttribute("events", events);
        }
        String eventErrorMessage = (String) model.asMap().get("eventErrorMessage");
        String errorMessageEdit = (String) model.asMap().get("errorMessageEdit");
        if ((errorMessageEdit != null) || (eventErrorMessage != null)) {
            model.addAttribute("errorMessageEdit", "");
            model.addAttribute("eventErrorMessage", "");
        }
        model.addAttribute("eventDescriptions", "");
        model.addAttribute("eventBookingTimeOut", 0);
        return "Events";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "NewSector/Cancel.do", method = RequestMethod.GET)
    public String cancelNewSector(Model model, SessionStatus status) {
        model.addAttribute("pageName", 4);
        Map allSectors = (TreeMap) model.asMap().get("allSectors");
        Date dateEv = (Date) model.asMap().get("dateEvent");
        String timeEv = (String) model.asMap().get("eventTime");
        String eventDescriptions = (String) model.asMap().get("eventDescriptions");
        Integer eventBookingTimeOut = (Integer) model.asMap().get("eventBookingTimeOut");
        model.addAttribute("dateEvent", dateEv);
        model.addAttribute("eventTime", timeEv);
        if (eventDescriptions != null) {
            model.addAttribute("eventDescriptions", eventDescriptions);
        }
        if (eventBookingTimeOut != null) {
            model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
        }
        model.addAttribute("eventEdit", editEvent);
        model.addAttribute("allSectors", allSectors);
        return "AddEditEvent";
    }

    @RequestMapping(value = "NewSector/addSector.do", method = RequestMethod.POST)
    public String addSector(Model model,
                            @RequestParam(value = "newPrice") String newPrice,
                            @RequestParam(value = "sectorName") String sectorName,
                            @RequestParam(value = "maxRows") Integer maxRows,
                            @RequestParam(value = "maxSeats") Integer maxSeats)
            throws SQLException, ParseException {

        model.addAttribute("pageName", 4);

        Map allSectors = (TreeMap) model.asMap().get("allSectors");
        Date dateEv = (Date) model.asMap().get("dateEvent");
        String timeEv = (String) model.asMap().get("eventTime");
        String eventDescriptions = (String) model.asMap().get("eventDescriptions");
        Integer eventBookingTimeOut = (Integer) model.asMap().get("eventBookingTimeOut");
        double newPriceD = 0d;
        if (newPrice != null) {
            newPriceD = Double.parseDouble(newPrice);
        }

        Sector sector = new Sector();
        if (allSectors == null || !allSectors.containsKey(sectorName)) {
            sector.setName(sectorName);
            sector.setMaxRows(maxRows);
            sector.setMaxSeats(maxSeats);
            sector.setPrice(newPriceD);
            Event editEvent = this.editEvent;
            if (editEvent != null) {
                sector.setEvent(editEvent);
            }
            sectorService.addSector(sector);
            model.addAttribute("id" + sector.getId(), sector.getId());
            if (sector != null && sector.getName() != null) {
                if (allSectors != null) {
                    allSectors.put(sector.getName(), sector);
                } else {
                    allSectors = new TreeMap<>();
                    allSectors.put(sector.getName(), sector);
                }
            }
            if (dateEv != null) {
                model.addAttribute("dateEvent", dateEv);
            }
            if (timeEv != null) {
                model.addAttribute("eventTime", timeEv);
            }
            if (eventDescriptions != null) {
                model.addAttribute("eventDescriptions", eventDescriptions);
            }
            if (eventBookingTimeOut != null) {
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
            }
            if (editEvent != null) {
                model.addAttribute("eventEdit", editEvent);
            }
            if (allSectors != null) {
                model.addAttribute("allSectors", allSectors);
            }
            String eventErrorMessage = (String) model.asMap().get("eventErrorMessage");
            String errorMessageEdit = (String) model.asMap().get("errorMessageEdit");
            if ((errorMessageEdit != null) || (eventErrorMessage != null)) {
                model.addAttribute("errorMessageEdit", "");
                model.addAttribute("eventErrorMessage", "");
            }
            return "AddEditEvent";
        } else {
            String errorMessageSector = "Ошибка! Сектор с именем " + sectorName + " уже существует";
            model.addAttribute("errorMessageSector", errorMessageSector);
            if (editEvent != null) {
                model.addAttribute("eventEdit", editEvent);
            }
            if (allSectors != null) {
                model.addAttribute("allSectors", allSectors);
            }
            if (dateEv != null) {
                model.addAttribute("dateEvent", dateEv);
            }
            if (timeEv != null) {
                model.addAttribute("eventTime", timeEv);
            }
            if (eventDescriptions != null) {
                model.addAttribute("eventDescriptions", eventDescriptions);
            }
            if (eventBookingTimeOut != null) {
                model.addAttribute("eventBookingTimeOut", eventBookingTimeOut);
            }
             if (sectorName != null) {
                model.addAttribute("sectorName", sectorName);
            }
            if (maxRows != null) {
                model.addAttribute("maxRows", maxRows);
            }
            if (maxSeats != null) {
                model.addAttribute("maxSeats", maxSeats);
            }
            if (newPrice != null) {
                model.addAttribute("newPrice", newPrice);
            }

            return "NewSector";
        }
    }

}
