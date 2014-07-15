package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.*;
import org.JavaArt.TicketManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes({"pageName", "error", "message"})

public class RefundController {
    private TicketService ticketService = TicketService.getInstance();
    private Ticket ticket;

    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.GET)
    public String refundRefund(Model model) {
        ticket=null;
        model.addAttribute("pageName", 3);
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        return "Refund";
    }

    @RequestMapping(value = "Refund/Find.do", method = RequestMethod.POST)
    public String refundFind(@RequestParam(value = "ticketId", required = true)
                             String ticketId, Model model){
        try{
            int id=Integer.parseInt(ticketId);
            ticket = ticketService.getTicketById(id);
            if (ticket!=null && ticket.isConfirmed()==true && ticket.isReserved()==false){
                model.addAttribute(ticket);
                model.addAttribute("error", "");
            }
            else{
                ticket=null;
                model.addAttribute("error", "Такой билет не продан");
            }
        }
        catch (Exception e){
            ticket=null;
            model.addAttribute("error", "Повторите ввод ID");
        }
        finally {
            model.addAttribute("message", "");
            return "Refund";
        }
    }

    @RequestMapping(value = "Refund/Delete.do", method = RequestMethod.POST)
    public String refundDelete (Model model){
        if (ticket==null) return "redirect:/Refund/Refund.do";
        ticketService.deleteTicket(ticket);
        ticket=null;
        model.addAttribute("message", "Билет возвращен в продажу");
        return "Refund";
    }
}


