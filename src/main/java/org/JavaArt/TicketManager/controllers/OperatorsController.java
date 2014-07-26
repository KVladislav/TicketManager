package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@SessionAttributes({"pageName", "operators", "operator", "errorOperator"})
public class OperatorsController {

    OperatorService operatorService = new OperatorService();


    @RequestMapping(value = "Operators/Operators.do", method = RequestMethod.GET)
    public String operatorsGet(Model model) {
        model.addAttribute("pageName", 5);//set menu page number
        List<Operator> operators = operatorService.getAllOperators();
        model.addAttribute("operators", operators);
        model.addAttribute("errorOperator", "");
        return "Operators";
    }

    @RequestMapping(value = "NewOperator/NewOperator.do", method = RequestMethod.GET)
    public String newOperatortGet(Model model) {
        model.addAttribute("pageName", 8);
        model.addAttribute("errorOperator", "");
        return "NewOperator";
    }

    @RequestMapping(value = "Operators/Delete.do", method = RequestMethod.POST)
    public String operatorDelete(@RequestParam(value = "operatorId") int operatorId) {
        operatorService.deleteOperator(operatorId);
        return "redirect:/Operators/Operators.do";
    }

    @RequestMapping(value = "EditOperator/Edit.do", method = RequestMethod.GET)
    public String editOperatorGet(@RequestParam(value = "operatorId", required = true) int operatorId, Model model) {
        model.addAttribute("pageName", 9);
        Operator operator = operatorService.getOperatorById(operatorId);
        model.addAttribute("operator", operator);
        model.addAttribute("errorOperator", "");
        return "EditOperator";
    }

    @RequestMapping(value = "EditOperator/Save.do", method = RequestMethod.POST)
    public String editOperatorSave(@RequestParam("operatorId") int id,
                                   @RequestParam("name") String name,
                                   @RequestParam("surname") String surname,
                                   @RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("passwordNew") String passwordNew,
                                   @RequestParam("passwordNewRepeat") String passwordNewRepeat,
                                   @RequestParam("description") String description, Model model) {
        Operator operator = operatorService.getOperatorById(id);
        if (!password.equals(operator.getPassword())) {
            model.addAttribute("errorOperator", "Пароль введён неверно.");
            return "EditOperator";
        }
        if (passwordNew.length()>0&&passwordNew.length()<6){
            model.addAttribute("errorOperator", "Измените новый пароль: минимум 6 символов. ");
            return "EditOperator";
        }

        if (passwordNew.length()>0&&!passwordNew.equals(passwordNewRepeat)) {
            model.addAttribute("errorOperator", "Повторите ввод нового пароля.");
            return "EditOperator";
        }
        if (passwordNew.length()>0) operator.setPassword(passwordNew);
        else operator.setPassword(password);
        operator.setName(name);
        operator.setSurname(surname);
        operator.setLogin(login);
        operator.setDescription(description);
        List<Operator> operators = operatorService.getAllOperators();
        for (Operator oper: operators){
            if (oper.getLogin().equals(operator.getLogin())&&!oper.getId().equals(operator.getId())){
                model.addAttribute("errorOperator", "Такой логин занят. Введите другой логин.");
                return "EditOperator";
            }
        }
        operatorService.editOperator(operator);
        return "redirect:/Operators/Operators.do";
    }


    @RequestMapping(value = "NewOperator/Save.do", method = RequestMethod.POST)
    public String operatorAdd(@RequestParam("name") String name,
                              @RequestParam("surname") String surname,
                              @RequestParam("login") String login,
                              @RequestParam("password") String password,
                              @RequestParam("passwordRepeat") String passwordRepeat,
                              @RequestParam("description") String description,
                              Model model) {
        if (!password.equals(passwordRepeat)) {
            model.addAttribute("errorOperator", "Повторите ввод пароля.");
            return "NewOperator";
        }
        Operator operator = new Operator();
        operator.setName(name);
        operator.setSurname(surname);
        operator.setLogin(login);
        operator.setPassword(password);
        operator.setDescription(description);
        List<Operator> operators = operatorService.getAllOperators();
        for (Operator oper: operators){
            if (oper.getLogin().equals(operator.getLogin())){
                model.addAttribute("errorOperator", "Такой логин занят. Введите другой логин.");
                return "NewOperator";
            }
        }
        operatorService.addOperator(operator);
        return "redirect:/Operators/Operators.do";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "EditOperator/Cancel.do")
    public String operatorCancel(Model model) {
        model.addAttribute("errorOperator", "");
        return "Operators";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "NewOperator/Cancel.do")
    public String operatorNewCancel(Model model) {
        model.addAttribute("errorOperator", "");
        return "Operators";
    }
}
