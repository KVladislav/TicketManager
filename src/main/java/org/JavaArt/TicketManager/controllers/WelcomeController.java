package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class WelcomeController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @RequestMapping(value="/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value="/")
    public String welcome(Model model) {

        //ВНИМАНИЕ! ЭТОТ КОД ДЛЯ ЗАПОЛНЕНИЯ БАЗЫ, ДЛЯ ЭТОГО НУЖНО УДАЛИТЬ БАЗУ ЧЕРЕЗ pgAdmin создать заново базу,
        //запустить программу и несклолько раз нажать refresh в бродилке
        //удалить отсюда
//        Operator operator = new Operator();
//        operator.setName("User");
//        operator.setSurname("Userovich");
//        operator.setLogin("user");
//        operator.setPassword("asdfqwerty");
//        operator.setDescription("first user");
//        operatorRepository.save(operator);
//
//        Client client = new Client();
//        client.setName("Client");
//        client.setDescription("");
//        clientRepository.save(client);
//
//
//        Event event = new Event();
//        event.setDate(new Date());
//        event.setDescription("football");
//        event.setOperator(operator);
//        eventRepository.save(event);
//
//        for (int i = 0; i < 28; i++) {
//            Sector zone = new Sector();
//            zone.setEvent(event);
//            zone.setName("Sector " + i);
//            zone.setMaxRows(20);
//            zone.setMaxSeats(50);
//            zone.setPrice(Double.valueOf(50 + i));
//            zone.setOperator(operator);
//            Ticket ticket = new Ticket();
//            ticket.setRow(10);
//            ticket.setSeat(15);
//            ticket.setSector(zone);
//            ticket.setOperator(operator);
//            ticket.setClient(client);
//            ticket.setReserved(true);
//            sectorRepository.save(zone);
//            ticketRepository.save(ticket);}
//
        //удалить до сюда

        model.addAttribute("eventall", eventRepository.findAll());
        model.addAttribute("event", eventRepository.findAllByData());
        model.addAttribute("sector", sectorRepository.findAllByEvent_id(1));
        return "index";
    }

    @RequestMapping(value="/buy_ticket", method = GET)
    public String newTicket(Model model) {

        model.addAttribute("buyticket", new Ticket());
        return "ticketForm";
    }


    @RequestMapping(value="/buy_ticket", method = POST)
    public String newTicket(@Valid @ModelAttribute("buyticket") Ticket buyticket, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ticketform";
        }

        ticketRepository.save(buyticket);

        return "index";
    }
/*
    @RequestMapping(value="/delete_advertisment", method = RequestMethod.GET)
    public String deleteAdvertisment(@RequestParam("id") Long id) {

        advertismentRepository.delete(id);

        return "redirect:/";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/reg", method = RequestMethod.GET)
    public String newUser(Model model) {

        model.addAttribute("reg", new User());
        return "reg";
    }


    @RequestMapping(value="/reg", method = RequestMethod.POST)
    public String newUser(@Valid @ModelAttribute("reg") User reg, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "reg";
        }

        userRepository.save(reg);

        return "redirect:/";
    }
 */


}
