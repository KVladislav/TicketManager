package org.JavaArt.TicketManager.controllers;

import org.JavaArt.TicketManager.entities.Operator;
import org.JavaArt.TicketManager.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;


@Controller
@SessionAttributes({"pageName", "operators", "operator"})
public class OperatorsController {

    OperatorService operatorService = new OperatorService();


    @RequestMapping(value = "Operators/Operators.do", method = RequestMethod.GET)
    public String operatorsGet(Model model) throws SQLException {
        model.addAttribute("pageName", 5);//set menu page number
        List<Operator> operators = operatorService.getAllOperators();
        model.addAttribute("operators", operators);
        return "Operators";
    }

    @RequestMapping(value = "NewOperator/NewOperator.do", method = RequestMethod.GET)
    public String newOperatortGet(Model model) throws SQLException {
        model.addAttribute("pageName", 8);//set menu page number

        return "NewOperator";
    }

    @RequestMapping(value = "Operators/OperatorsDelete.do", method = RequestMethod.POST)
    public String operatorDelete(@RequestParam(value = "operatorId", required=true) int operatorId, Model model,SessionStatus status) throws SQLException {
        operatorService.deleteOperator(operatorId);
        status.setComplete();
        return "redirect:/Operators/Operators.do";
    }

    @RequestMapping(value = "EditOperator/OperatorsEditGet.do", method = RequestMethod.GET)
    public String editOperatorGet(@RequestParam(value = "operatorId", required=true) int operatorId, Model model,SessionStatus status) throws SQLException {
        model.addAttribute("pageName", 9);//set menu page number
        Operator operator = operatorService.getOperatorById(operatorId);
        model.addAttribute("operator", operator);

        return "EditOperator";
    }

    @RequestMapping(value = "EditOperator/OperatorsEditSave.do", method = RequestMethod.POST)
    public String editOperatorSave(@RequestParam ("operatorId") int id, @RequestParam ("name") String name,@RequestParam ("surname") String surname,@RequestParam ("login") String login,
                                   @RequestParam ("password") String password,@RequestParam ("description") String description, SessionStatus status) throws SQLException {

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
    public String operatorAdd (@RequestParam ("name") String name,@RequestParam ("surname") String surname,@RequestParam ("login") String login,
                               @RequestParam ("password") String password,@RequestParam ("description") String description, SessionStatus status) throws SQLException {

        Operator operator = new Operator();
        operator.setName(name);
        operator.setSurname(surname);
        operator.setLogin(login);
        operator.setPassword(password);
        operator.setDescription(description);
        operatorService.addOperator(operator);
        status.setComplete();
        return "redirect:/Operators/Operators.do";
    }



}
