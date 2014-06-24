package org.JavaArt.TicketManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;

@Controller
@SessionAttributes({"pageName"})
public class RefundController {
    @RequestMapping(value = "Refund/Refund.do", method = RequestMethod.GET)
    public String refundGet(Model model) throws SQLException {
        model.addAttribute("pageName", 3);//set menu page number
        return "Refund";
    }
}
