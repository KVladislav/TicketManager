package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.service.SectorDefaultsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by GalAnt on 17.07.2014.
 */

@Controller

public class IndexController {
    SectorDefaultsService sectorDefaultsService = new SectorDefaultsService();


    @RequestMapping("/")
    public String showIndex(ModelMap model) {
        model.addAttribute("pageName", 0);
        Operator operator = (Operator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (operator.getLogin().equals("root")) {

            if (sectorDefaultsService.countSectorDefaults() == 0) {
                initStadiumSettings();
            }
        }

        return "Index";
    }

    private void initStadiumSettings() {
        SectorDefaults sectorDefaults;
        for (int i = 1; i <= 9; i++) {
            sectorDefaults = new SectorDefaults();
            sectorDefaults.setSectorName("0" + i);
            sectorDefaultsService.addSectorDefaults(sectorDefaults);
        }

        for (int i = 10; i <= 25; i++) {
            sectorDefaults = new SectorDefaults();
            sectorDefaults.setSectorName("" + i);
            sectorDefaultsService.addSectorDefaults(sectorDefaults);
        }

        sectorDefaults = new SectorDefaults();
        sectorDefaults.setSectorName("VIP A");
        sectorDefaults.setMaxRows(10);
        sectorDefaults.setMaxSeats(20);
        sectorDefaultsService.addSectorDefaults(sectorDefaults);
        sectorDefaults = new SectorDefaults();
        sectorDefaults.setSectorName("VIP D");
        sectorDefaults.setMaxRows(10);
        sectorDefaults.setMaxSeats(20);
        sectorDefaultsService.addSectorDefaults(sectorDefaults);
    }
}
