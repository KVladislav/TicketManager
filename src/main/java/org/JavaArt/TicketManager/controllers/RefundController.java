package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;

@Controller
@SessionAttributes({"pageName"})

public class RefundController {
    private TicketService ticketService = TicketService.getInstance();

    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.GET)
    public String refundGet(Model model) throws SQLException {
        model.addAttribute("pageName", 3);//set menu page number
        return "Refund";
    }

    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.POST)
    public String refundPost(@RequestParam(value = "ticketId", required = true)
                            int ticketId)throws SQLException {
       /* if (bindingResult.hasErrors()) {
            System.out.println("Error");
            return "Refund/Refund.do";
        }*/
        ticketService.deleteTicket(ticketService.getTicketById(ticketId));
        System.out.println(ticketId);
        return "Refund";
    }
}


