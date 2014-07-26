package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.JavaArt.TicketManager.service.SectorDefaultsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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


    @RequestMapping(value = "Sectors/*.do", method = RequestMethod.GET)
    public String sectorsInit(Model model) {
//        sessionStatus.setComplete();
        model.addAttribute("pageName", 7);//set menu page number
        List<SectorDefaults> sectorDefaultsList = sectorDefaultsService.getAllSectorDefaults();
        if (sectorDefaultsList.size() == 0) {
            SectorDefaults sectorDefaults = new SectorDefaults();
            sectorDefaults.setSectorName("1");
            sectorDefaultsList = new ArrayList<>();
            sectorDefaultsList.add(sectorDefaults);
            sectorDefaultsService.addSectorDefaults(sectorDefaults);
        }

        model.addAttribute("sectorDefaultsList", sectorDefaultsList);
        return "SectorDefaults";
    }

//    @RequestMapping(value = "Sectors/Modify.do", method = RequestMethod.GET)
//    public String doNothing(){
//        return "SectorDefaults";
//    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Sectors/Modify.do", method = RequestMethod.POST)
    public String sectorsModify(@RequestParam(value = "action") String action,
                                @RequestParam(value = "sectorDefaultsId") Integer sectorDefaultsId,
                                @RequestParam(value = "defaultPrice") String defaultPriceString,
                                @RequestParam(value = "sectorName") String sectorName,
                                @RequestParam(value = "maxRows") Integer maxRows,
                                @RequestParam(value = "maxSeats") Integer maxSeats,
                                Model model) {


        List<SectorDefaults> sectorDefaultsList = (List) model.asMap().get("sectorDefaultsList");
        String errorMessage=null;
        for (SectorDefaults sectorDefaults : sectorDefaultsList) {
            if (sectorDefaults.getId() == sectorDefaultsId) {

                if (action.equals("delete") && sectorDefaultsList.size()>1) {
                    sectorDefaultsService.deleteSectorDefaults(sectorDefaults);
                    sectorDefaultsList.remove(sectorDefaults);
                    break;
                }

                if (action.equals("save")) {
                    SectorDefaults sectorDefaultsTest = sectorDefaultsService.getSectorDefaultsByName(sectorName);
                    if (sectorDefaultsTest==null || sectorDefaults.getSectorName().equals(sectorName)) {
                        sectorDefaults.setSectorName(sectorName);
                    } else {
                        errorMessage="Ошибка! Сектор с именем " + sectorName + " уже существует";

                    }
                    try {
                        Double defaultPrice = Double.parseDouble(defaultPriceString);
                        sectorDefaults.setDefaultPrice(Math.abs(defaultPrice));
                    } catch (Exception e) {

                    }

                    sectorDefaults.setMaxRows(Math.abs(maxRows));
                    sectorDefaults.setMaxSeats(Math.abs(maxSeats));
                    sectorDefaultsService.updateSectorDefaults(sectorDefaults);
                    break;
                }

                if (action.equals("clone")) {
                    System.out.println(sectorDefaultsService.getSectorDefaultsByName("00 НОВЫЙ"));
                    if (sectorDefaultsService.getSectorDefaultsByName("00 НОВЫЙ") == null) {
                        SectorDefaults sectorDefaultsClone = new SectorDefaults();
                        sectorDefaultsClone.setSectorName("00 НОВЫЙ");
                        sectorDefaultsClone.setMaxRows(sectorDefaults.getMaxRows());
                        sectorDefaultsClone.setMaxSeats(sectorDefaults.getMaxSeats());
                        sectorDefaultsClone.setDefaultPrice(sectorDefaults.getDefaultPrice());
                        sectorDefaultsList.add(0, sectorDefaultsClone);
                        sectorDefaultsService.addSectorDefaults(sectorDefaultsClone);
                        break;
                    } else {
                        errorMessage="Ошибка! Новый сектор уже создан, пожалуйста, заполните данные";
                    }
                }
            }

        }
        model.addAttribute("errorMessage", errorMessage);
        return "SectorDefaults";
    }
}
