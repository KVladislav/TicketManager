package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Ticket;
import org.JavaArt.TicketManager.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;

@Controller
@SessionAttributes({"pageName", "errorRefund", "messageRefund"})

public class RefundController {
    private TicketService ticketService = TicketService.getInstance();
    private Ticket ticket;

    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.GET)
    public String refundRefund(Model model) {
        ticket=null;
        model.addAttribute("pageName", 3);
        model.addAttribute("errorRefund", "");
        model.addAttribute("messageRefund", "");
        return "Refund";
    }

    @RequestMapping(value = "Refund/Find.do", method = RequestMethod.POST)
    public String refundFind(@RequestParam(value = "ticketId", required = true)
                             String ticketId, Model model){
        try{
            int id=Integer.parseInt(ticketId);
            ticket = ticketService.getTicketById(id);
            if (ticket!=null ){
                if (ticket.isConfirmed() && !ticket.isReserved()){
                    if (ticket.getSector().getEvent().getDate().before(new Date())){
                        model.addAttribute("errorRefund", "Билет с № = "+ticketId+" вернуть нельзя! Мероприятие \""+
                                ticket.getSector().getEvent().getDescription()+ "\" уже прошло.");
                        ticket=null;
                    }
                    else {
                        model.addAttribute(ticket);
                        model.addAttribute("errorRefund", "");
                    }
                }
                else  model.addAttribute("errorRefund", "Билет с № = "+ticketId+" не продан");
            }
            else  model.addAttribute("errorRefund", "Билет с № = "+ticketId+" не продан");
        }
        catch (Exception e){
            ticket=null;
            model.addAttribute("errorRefund", "Повторите ввод ID");
        }
        finally {
            model.addAttribute("messageRefund", "");
            return "Refund";
        }
    }

    @RequestMapping(value = "Refund/Delete.do", method = RequestMethod.POST)
    public String refundDelete (Model model){
        if (ticket==null) return "redirect:/Refund/Refund.do";
        ticketService.deleteTicket(ticket);
        ticket=null;
        model.addAttribute("messageRefund", "Билет возвращен в продажу");
        return "Refund";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Refund/Cancel.do")
    public String  refundCancel(Model model) {
        ticket=null;
        model.addAttribute("errorRefund", "");
        model.addAttribute("messageRefund", "");
        return "Refund";
    }
}


