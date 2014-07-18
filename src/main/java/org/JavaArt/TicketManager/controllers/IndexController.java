package org.JavaArt.TicketManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by GalAnt on 17.07.2014.
 */

@Controller

public class IndexController {

    @RequestMapping ("/")
    public String showIndex(ModelMap model){
        model.addAttribute("pageName", 0);
        return "Index";
    }
}
