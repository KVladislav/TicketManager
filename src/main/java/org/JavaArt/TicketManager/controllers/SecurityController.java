package org.JavaArt.TicketManager.controllers;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 15.07.2014
 * Time: 10:47
 */
@Controller
public class SecurityController extends SimpleUrlAuthenticationFailureHandler {

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public ModelAndView login() {

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;

    }
}
