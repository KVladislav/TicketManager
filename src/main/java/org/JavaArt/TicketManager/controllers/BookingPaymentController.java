package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 01.07.2014
 * Time: 22:54
 */
@Controller
@SessionAttributes({"bookingTimeOut", "pageName"})
public class BookingPaymentController {
    private Map<Client,Integer[]> clients;
    private Client client;

    @RequestMapping(value = "Booking/Payment.do", method = RequestMethod.GET)
    public String bookingPaymentInit(Model model) throws SQLException {
        model.addAttribute("pageName", 6);//set menu page number
        return "BookingPayment";
    }

    @RequestMapping(value = "Booking/GetClients.do", method = RequestMethod.POST)
    public String bookingGetClients(@RequestParam(value = "searchWord", required = true) String searchWord, Model model) throws SQLException {
        System.out.println(searchWord);
        model.addAttribute("clients", clients);
        return "BookingPayment";
    }

    @RequestMapping(value = "Booking/ViewClient.do", method = RequestMethod.POST)
    public String bookingViewClient(@RequestParam(value = "clientId", required = true) int clientId, Model model) throws SQLException {
        System.out.println(clientId);

        return "BookingPayment";
    }
}
