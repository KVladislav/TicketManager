package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "operator"})
public class OperatorsController {

    OperatorService operatorService = new OperatorService();


    @RequestMapping(value = "Operators/Operators.do", method = RequestMethod.GET)
    public String operatorsGet(Model model) {
        model.addAttribute("pageName", 5);//set menu page number
        List<Operator> operators = operatorService.getAllOperators();
        model.addAttribute("operators", operators);
        return "Operators";
    }

    @RequestMapping(value = "Operators/NewOperator.do", method = RequestMethod.GET)
    public String newOperatorGet(Model model) {
        model.addAttribute("operator", new Operator());
        return "EditOperator";
    }

    @RequestMapping(value = "Operators/OperatorsDelete.do", method = RequestMethod.POST)
    public String operatorDelete(@RequestParam(value = "operatorId") int operatorId) {
        operatorService.deleteOperator(operatorId);
        return "redirect:/Operators/Operators.do";
    }

    @RequestMapping(value = "Operators/OperatorEdit.do", method = RequestMethod.POST)
    public String editOperator(@RequestParam(value = "operatorId", required = true) int operatorId, Model model) {
        Operator operator = operatorService.getOperatorById(operatorId);
        model.addAttribute("operator", operator);
//        model.addAttribute("errorOperator", "");
        return "EditOperator";
    }

    @RequestMapping(value = "Operators/OperatorEditSave.do", method = RequestMethod.POST)
    public String editOperatorSave(@RequestParam("name") String name,
                                   @RequestParam("surname") String surname,
                                   @RequestParam("login") String login,
                                   @RequestParam(value = "password", required = false) String password,
                                   @RequestParam("passwordNew") String passwordNew,
                                   @RequestParam("passwordNewRepeat") String passwordNewRepeat,
                                   @RequestParam("description") String description, Model model) {

        Operator operator = (Operator) model.asMap().get("operator");
        if (operator == null) {
            return "redirect:/Operators/Operators.do";
        }

        operator.setName(name);
        operator.setSurname(surname);
        operator.setDescription(description);
        operator.setLogin(login);

        List<String> errorMessage = new ArrayList<>();

        Operator dbOperator = operatorService.getOperatorByLogin(login);
        if (dbOperator != null && !dbOperator.getId().equals(operator.getId())) {
            errorMessage.add("Такой логин занят. Введите другой логин");
        }

        if (operator.getId() != null && !password.equals(operator.getPassword())) {
            errorMessage.add("Пароль введён неверно");
        }

        if (passwordNew.length() > 0 && passwordNew.length() < 6) {
            errorMessage.add("Измените новый пароль: минимум 6 символов");
        }

        if (passwordNew.length() > 0 && !passwordNew.equals(passwordNewRepeat)) {
            errorMessage.add("Пароли не совпадают");
        }

        if (operator.getId() == null && passwordNew.length() == 0) {
            errorMessage.add("Введите пароль");
        }

        if (errorMessage.size() > 0) {
            model.addAttribute("errorMessages", errorMessage);
            return "EditOperator";
        }

        if (passwordNew.length() > 0)
            operator.setPassword(passwordNew);


        if (operator.getId() != null) {
            operatorService.updateOperator(operator);
        } else {
            operatorService.addOperator(operator);
        }

        return "redirect:/Operators/Operators.do";
    }


//    @RequestMapping(value = "NewOperator/OperatorsAdd.do", method = RequestMethod.POST)
//    public String operatorAdd(@RequestParam("name") String name,
//                              @RequestParam("surname") String surname,
//                              @RequestParam("login") String login,
//                              @RequestParam("password") String password,
//                              @RequestParam("passwordRepeat") String passwordRepeat,
//                              @RequestParam("description") String description,
//                              Model model) {
//        if (!password.equals(passwordRepeat)) {
//            model.addAttribute("errorOperator", "Повторите ввод пароля.");
//            return "NewOperator";
//        }
//        Operator operator = new Operator();
//        operator.setName(name);
//        operator.setSurname(surname);
//        operator.setLogin(login);
//        operator.setPassword(password);
//        operator.setDescription(description);
//        List<Operator> operators = operatorService.getAllOperators();
//        for (Operator oper : operators) {
//            if (oper.getLogin().equals(operator.getLogin())) {
//                model.addAttribute("errorOperator", "Такой логин занят. Введите другой логин.");
//                return "NewOperator";
//            }
//        }
//        operatorService.addOperator(operator);
//        return "redirect:/Operators/Operators.do";
//    }
}
