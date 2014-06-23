package org.JavaArt.TicketManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 23.06.2014
 * Time: 12:03
 */
@Controller
@SessionAttributes({"pageName"})
public class OrderController {
    @RequestMapping(value = "/Order.do", method = RequestMethod.GET)
    public String orderGet(Model model) throws SQLException {
        model.addAttribute("pageName", 1);//set menu page number
        return "Order";
    }
}
