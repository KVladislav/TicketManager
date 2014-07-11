<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="ru">
<body>
    <div class="Refund">
    <center>
         <h3 class="panel-heading" style=" color:Blue" >Возврат билета</h3>
         <form name = "findTicket"  action="${pageContext.request.contextPath}/Refund/Find.do" method="post">
             <br><h4> Поиск по номеру билета(ID)</h4>
             <td><br>
             <div>
                  <input path="id" errors path="error"  id="ticketId"required pattern="^[0-9]+$"
                         title="Целые числа без пробелов" name="ticketId" />
                  <button type="submit" name="action" class="btn btn-primary">Поиск билета</button>
                  <h3 style="text-align:center; color:Red">${error}</h3>
             </div>
             <br><br>
             <div class="panel-body"  style=" width:60%">
                  <div class="table responsive">
                       <table class="table table-bordered table-bordered">
                       <thead>
                             <th>Номер билета</th>
                             <th>Мероприятие</th>
                             <th>Дата</th>
                             <th>Сектор</th>
                             <th>Ряд</th>
                             <th>Место</th>
                             <th>Цена</th>
                       </thead>
                       <tbody>
                            <td>${ticket.id}</td>
                            <td>${ticket.sector.event.description}</td>
                            <td> <fmt:formatDate value="${ticket.sector.event.date}" pattern="dd.MM.yyyy  HH:mm"/></td>
                            <td>${ticket.sector.name}</td>
                            <td>${ticket.row}</td>
                            <td>${ticket.seat}</td>
                            <td>${ticket.sector.price}</td>
                       </tbody>
                       </table>
                  </div>
             </div>
         </form>
    </center>
    </div>
    <div>
        <form name = "delTicket" action="${pageContext.request.contextPath}/Refund/Delete.do" method="post">
            <h4 style="text-align:center">
                <button  type="submit" name="delete" class="btn btn-primary" >Подтвердите возврат билета</button>
                <h5 style="text-align:center; color:Green">${message}</h5>
            </h4>
        </form>
    </div>
</body>
</html>

