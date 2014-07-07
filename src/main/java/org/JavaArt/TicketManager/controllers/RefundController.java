package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"pageName", "DeletingTicket",})

public class RefundController {
    private TicketService ticketService = TicketService.getInstance();
    private Ticket ticket;
    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.GET)
    public String refundGet(Model model) {
        model.addAttribute("pageName", 3);
        return "Refund";
    }

    @RequestMapping(value = "Refund/Find.do", method = RequestMethod.POST)
    public String refundPost(@RequestParam(value = "ticketId", required = true) int ticketId, Model model){
       /* if (bindingResult.hasErrors()) {
            System.out.println("Error");
            return "Refund/Refund.do";
        }*/
        ticket = ticketService.getTicketById(ticketId);
        model.addAttribute(ticket);
        model.addAttribute("DeletingTicket", ticket);
        return "Refund";
    }

    @RequestMapping(value = "Refund/Delete.do", method = RequestMethod.POST)
    public String deletePost(){
        System.out.println(ticket.getId());
        ticketService.deleteTicket(ticket);
        return "redirect:/Refund/Refund.do";
    }
}


