package org.JavaArt.TicketManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

    @RequestMapping ("/about")
    public String showIndex(ModelMap model){
        model.addAttribute("pageName", 0);
        return "About";
    }

}
