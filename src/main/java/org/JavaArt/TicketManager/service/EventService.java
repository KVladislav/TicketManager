package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.EventRepository;
import org.JavaArt.TicketManager.DAO.impl.EventRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class EventService {
    private static EventService eventService;
    private EventRepository eventRepository = new EventRepositoryImpl();
    public static final Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9-_#,:\\.\\s]{0,49}$");
    // private EventService() {

    // }

    public static EventService getInstance() {
        if (eventService == null) {
            eventService = new EventService();
        }
        return eventService;
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.getAllEvents();
        return events;
    }

    public List<Event> getFutureEvents() {
        List<Event> events = eventRepository.getFutureEvents();
        return events;
    }

    public Event getEventById(Long id) {
        Event event = eventRepository.getEventById(id);
        return event;
    }

    public void addEvent(Event event) {
        eventRepository.addEvent(event);
    }

    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }

    public List<Event> getEventsByDate(Date date) {
        List<Event> events = (List<Event>) eventRepository.getEventByDate(date);
        return events;
    }

    public List<Event> getFutureBookableEvents() {
        return eventRepository.getFutureBookableEvents();
    }

    public List<Event> getEventsByDateFromEvent(Date date, Event event) {
        List<Event> events = eventRepository.getEventByDateFromEvent(date, event);
        return events;
    }

    public void deleteEvent(Event event) {
        eventRepository.deleteEvent(event);
    }

    public boolean busyEvent(Event event) {
        return eventRepository.busyEvent(event);
    }

    public boolean doMatch(String word) {
        Matcher matcher = pattern.matcher(word);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public List getDateByString(String dateEvent, String eventTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date trueDate = format.parse(dateEvent);
        int intHour = 0;
        int intMin = 0;
        if ((eventTime != null) && (!eventTime.equals(""))) {
            String[] str = eventTime.split(":");
            intHour = Integer.parseInt(str[0]);
            intMin = Integer.parseInt(str[1]);
        }
        Calendar rightAgain = Calendar.getInstance();
        rightAgain.setTime(trueDate);
        rightAgain.add(Calendar.HOUR, intHour);
        rightAgain.add(Calendar.MINUTE, intMin);
        trueDate = rightAgain.getTime();
        Date date = trueDate;
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
        Date simpleDate = dateFormat.parse(localisedDate);
        List allDates = new ArrayList();
        allDates.add(simpleDate);
        allDates.add(hour);
        allDates.add(min);
        return allDates;
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
}
