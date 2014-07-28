<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <c:forEach items="${bookingErrorMessages}" var="errorMessage">
                <div class="alert alert-warning" role="alert">${errorMessage}</div>
            </c:forEach>
            <%--</c:if>--%>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-8 col-md-offset-2 column" style="margin-bottom: 50px">
            <table class="table table-hover table-condensed">
                <caption>
                    <h3 class="panel-heading text-info">Подтверждение бронирования от <fmt:formatDate
                            value="${bookingClient.timeStamp}"
                            pattern="dd.MM.yy H:mm"/></h3>
                    <br>
                    <h4>Клиент: ${bookingClient.name}</h4>
                </caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th colspan="2">Мероприятие</th>
                    <th colspan="1">Дата</th>
                    <th colspan="1">Сектор</th>
                    <th colspan="1">Ряд</th>
                    <th colspan="1">Место</th>
                    <th colspan="1">Цена</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bookingTickets}" var="ticket">
                    <tr>
                        <td>${ticket.id}</td>
                            <%--<c:set var="number" value="${number+1}"/>--%>
                        <td colspan="2">
                            <small>${ticket.sector.event.description}</small>
                        </td>
                        <td colspan="1">
                            <small><fmt:formatDate value="${ticket.sector.event.date}"
                                                   pattern="dd.MM.yy H:mm"/></small>
                        </td>
                        <td>
                            <small>${ticket.sector.name}</small>
                        </td>
                        <td>
                            <small>${ticket.row}</small>
                        </td>
                        <td>
                            <small>${ticket.seat}</small>
                        </td>
                        <td>
                            <small>${ticket.sector.price}</small>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        <strong>Стоимость заказа <fmt:formatNumber pattern="0.00" value="${bookingPrice}"/></strong>
                    </td>
                    <td colspan="8">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-2 col-md-offset-5 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}\Booking\GetClient.do">Ok</a>
        </div>
    </div>

</div>
</body>
<%@include file="footer.jsp" %>
</html>
