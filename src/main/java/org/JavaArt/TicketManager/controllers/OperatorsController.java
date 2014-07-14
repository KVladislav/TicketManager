package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "operators", "operator", "error"})
public class OperatorsController {

    OperatorService operatorService = new OperatorService();


    @RequestMapping(value = "Operators/Operators.do", method = RequestMethod.GET)
    public String operatorsGet(Model model) {
        model.addAttribute("pageName", 5);//set menu page number
        List<Operator> operators = operatorService.getAllOperators();
        model.addAttribute("operators", operators);
        model.addAttribute("error", "");
        return "Operators";
    }

    @RequestMapping(value = "NewOperator/NewOperator.do", method = RequestMethod.GET)
    public String newOperatortGet(Model model) {
        model.addAttribute("pageName", 8);//set menu page number
        model.addAttribute("error", "");
        return "NewOperator";
    }

    @RequestMapping(value = "Operators/OperatorsDelete.do", method = RequestMethod.POST)
    public String operatorDelete(@RequestParam(value = "operatorId", required = true) int operatorId, Model model, SessionStatus status) {
        operatorService.deleteOperator(operatorId);
        status.setComplete();
        return "redirect:/Operators/Operators.do";
    }

    @RequestMapping(value = "EditOperator/OperatorsEditGet.do", method = RequestMethod.GET)
    public String editOperatorGet(@RequestParam(value = "operatorId", required = true) int operatorId, Model model, SessionStatus status) {
        model.addAttribute("pageName", 9);//set menu page number
        Operator operator = operatorService.getOperatorById(operatorId);
        model.addAttribute("operator", operator);

        return "EditOperator";
    }

    @RequestMapping(value = "EditOperator/OperatorsEditSave.do", method = RequestMethod.POST)
    public String editOperatorSave(@RequestParam("operatorId") int id, @RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("login") String login,
                                   @RequestParam("password") String password, @RequestParam("description") String description, SessionStatus status) {

        Operator operator = operatorService.getOperatorById(id);
        operator.setName(name);
        operator.setSurname(surname);
        operator.setLogin(login);
        operator.setPassword(password);
        operator.setDescription(description);
        operatorService.editOperator(operator);
        status.setComplete();
        return "redirect:/Operators/Operators.do";
    }


    @RequestMapping(value = "NewOperator/OperatorsAdd.do", method = RequestMethod.POST)
    public String operatorAdd(@RequestParam("name") String name,
                              @RequestParam("surname") String surname,
                              @RequestParam("login") String login,
                              @RequestParam("password") String password,
                              @RequestParam("password1") String password1,
                              @RequestParam("description") String description,
                              Model model) {

        if (!password.equals(password1)) {
            model.addAttribute("error", "Повторите ввод пароля");
            return "NewOperator";
        }
        if (name.length()>12){
            model.addAttribute("error", "Имя: максимум 12 символов");
            return "NewOperator";
        }
        if (surname.length()>15){
            model.addAttribute("error", "Фимилия: максимум 15 символов");
            return "NewOperator";
        }
        if (login.length()<3||login.length()>12){
            model.addAttribute("error", "Логин: минимум 3 символа, максимум 12 ");
            return "NewOperator";
        }
        if (password.length()<6||password.length()>15){
            model.addAttribute("error", "Пароль: минимум 6 символа, максимум 15 ");
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
            if (oper.getName().equals(operator.getName())&&oper.getSurname().equals(operator.getSurname())){
                model.addAttribute("error", "Оператор с таким именем и фамилией уже существует");
                operator=null;
                return "NewOperator";
            }
            if (oper.getLogin().equals(operator.getLogin())){
                model.addAttribute("error", "Такой логин уже есть. Введите другой лигин");
                operator=null;
                return "NewOperator";
            }
        }
        operatorService.addOperator(operator);
        return "redirect:/Operators/Operators.do";
    }


}
