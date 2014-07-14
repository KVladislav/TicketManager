package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.service.SectorDefaultsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 01.07.2014
 * Time: 22:39
 */
@Controller
@SessionAttributes({"pageName", "sectorDefaultsList"})
public class SectorController {
    private SectorDefaultsService sectorDefaultsService = new SectorDefaultsService();


    @RequestMapping(value = "Sectors/ViewSectors.do", method = RequestMethod.GET)
    public String sectorsInit(Model model, SessionStatus sessionStatus) {
//        sessionStatus.setComplete();
        model.addAttribute("pageName", 7);//set menu page number
        List<SectorDefaults> sectorDefaultsList = sectorDefaultsService.getAllSectorDefaults();
        model.addAttribute("sectorDefaultsList", sectorDefaultsList);
        return "SectorDefaults";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Sectors/Modify.do", method = RequestMethod.POST)
    public String sectorsModify(@RequestParam(value = "action") String action,
                                @RequestParam(value = "sectorDefaultsId") Integer sectorDefaultsId,
                                @RequestParam(value = "defaultPrice") Double defaultPrice,
                                @RequestParam(value = "sectorName") String sectorName,
                                @RequestParam(value = "maxRows") Integer maxRows,
                                @RequestParam(value = "maxSeats") Integer maxSeats,
                                Model model) {


        List<SectorDefaults> sectorDefaultsList = (List) model.asMap().get("sectorDefaultsList");
        if (sectorDefaultsList==null) {
            SectorDefaults sectorDefaults = new SectorDefaults();
            sectorDefaults.setSectorName("1");
            sectorDefaultsList=new ArrayList<>();
            sectorDefaultsList.add(sectorDefaults);
            sectorDefaultsService.addSectorDefaults(sectorDefaults);
        }
        for (SectorDefaults sectorDefaults : sectorDefaultsList) {
            if (sectorDefaults.getId() == sectorDefaultsId) {
                if (action.equals("delete")) {
                    sectorDefaultsService.deleteSectorDefaults(sectorDefaults);
                    sectorDefaultsList.remove(sectorDefaults);
                    break;
                }

                if (action.equals("save")) {
                    sectorDefaults.setSectorName(sectorName);
                    sectorDefaults.setDefaultPrice(Math.abs(defaultPrice));
                    sectorDefaults.setMaxRows(Math.abs(maxRows));
                    sectorDefaults.setMaxSeats(Math.abs(maxSeats));
                    sectorDefaultsService.updateSectorDefaults(sectorDefaults);
                    break;
                }

                if (action.equals("clone")) {
                    SectorDefaults sectorDefaultsClone = new SectorDefaults();
                    sectorDefaultsClone.setSectorName(sectorDefaults.getSectorName());
                    sectorDefaultsClone.setMaxRows(sectorDefaults.getMaxRows());
                    sectorDefaultsClone.setMaxSeats(sectorDefaults.getMaxSeats());
                    sectorDefaultsClone.setDefaultPrice(sectorDefaults.getDefaultPrice());
                    sectorDefaultsList.add(sectorDefaultsClone);
                    sectorDefaultsService.addSectorDefaults(sectorDefaultsClone);
                    break;
                }
            }

        }
        return "SectorDefaults";
    }
}
