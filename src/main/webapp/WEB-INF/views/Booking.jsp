<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#my-select').multiSelect()
        });
    </script>

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <strong>Мероприятие</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setSectors.do" method="post">
                        <p><select size="10" name="eventId" data-size="3" class="form-control">
                            <c:forEach items="${events}" var="evnt">
                                <c:if test="${event.id==evnt.id}">
                                    <option value="${evnt.id}" onclick="this.form.submit()"
                                            selected>${evnt.description} ${evnt.date.day}.${evnt.date.month} ${event.date.hours}:${event.date.minutes}</option>
                                </c:if>
                                <c:if test="${event.id!=evnt.id}">
                                    <option value="${evnt.id}"
                                            onclick="this.form.submit()">${evnt.description} ${evnt.date.day}.${evnt.date.month} ${evnt.date.hours}:${evnt.date.minutes}</option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <strong>Сектор</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setRow.do" method="post">
                        <p><select size="10" name="sectorId" class="form-control">
                            <c:forEach items="${sectorsMap}" var="sectorEntry">
                                <c:if test="${sector.id==sectorEntry.key.id}">
                                    <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                            selected>${sectorEntry.key.name} price ${sectorEntry.key.price}
                                        free: ${sectorEntry.value}</option>
                                </c:if>
                                <c:if test="${sector.id!=sectorEntry.key.id}">
                                    <option value="${sectorEntry.key.id}"
                                            onclick="this.form.submit()">${sectorEntry.key.name}
                                        price ${sectorEntry.key.price} free: ${sectorEntry.value}</option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <strong>Ряд</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setSeat.do" method="post">
                        <p><select size="10" name="row" class="form-control">
                            <c:forEach items="${rowsMap}" var="rowEntry">
                                <c:if test="${rowEntry.key==row}">
                                    <option value="${rowEntry.key}" onclick="this.form.submit()"
                                            selected>${rowEntry.key}
                                        free: ${rowEntry.value}</option>
                                </c:if>
                                <c:if test="${row!=rowEntry.key}">
                                    <option value="${rowEntry.key}" onclick="this.form.submit()">${rowEntry.key}
                                        free: ${rowEntry.value}</option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <strong>Место</strong>

                    <form action="${pageContext.request.contextPath}/Booking/addTicket.do" method="post">
                        <p><select multiple="multiple" id="my-select" name="seats" name="my-select[]">
                            <%--<c:forEach items="${seatsMap}" var="seatEntry">--%>
                            <%--<c:if test="${! seatEntry.value}">--%>
                            <%--<option disabled="disabled" value="${seatEntry.key}">${seatEntry.key}</option>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${seatEntry.value}">--%>
                            <%--<option value="${seatEntry.key}">${seatEntry.key}</option>--%>
                            <%--</c:if>--%>

                            <%--</c:forEach>--%>
                            <c:forEach items="${seatsMap}" var="seatEntry">
                                <option value="${seatEntry}">${seatEntry}</option>
                            </c:forEach>
                        </select>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-4 column">
                </div>
                <div class="col-md-4 column">
                    <input type="submit" name="Order" class="btn btn-primary btn-lg" value="Добавить">
                    </form>
                    <%--<form action="${pageContext.request.contextPath}/Booking/Finish.do" method="post">--%>
                    <%--<input type="submit" name="Order" class="btn btn-primary btn-lg" value="Оформить"></form>--%>
                </div>
                <div class="col-md-4 column">
                    <form action="${pageContext.request.contextPath}/Booking/Cancel.do" method="post">
                        <input type="submit" name="Order" class="btn btn-primary btn-lg" value="Отмена"></form>
                </div>
            </div>
        </div>
        <div class="col-md-6 column">
            <table class="table table-hover table-condensed">
                <caption><strong>Заказ</strong></caption>
                <thead>
                <tr>
                    <th>Мероприятие</th>
                    <th>Сектор</th>
                    <th>Ряд</th>
                    <th>Место</th>
                    <th>Цена</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${tickets}" var="ticket">
                    <tr>
                        <form name = "delTicket" action="${pageContext.request.contextPath}/Booking/delTicket.do" method="post">
                            <td>${ticket.sector.event.description}</td>
                            <td>${ticket.sector.name}</td>
                            <td>${ticket.row}</td>
                            <td>${ticket.seat}</td>
                            <td>${ticket.sector.price}</td>
                            <td>
                                <input type="hidden" name="ticketId" value="${ticket.id}">
                                    <button class="btn btn-default btn-xs" onclick="document.delTicket.submit();"><span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>

                    </tr>
                </c:forEach>
                <tr>
                    <td><p class="alert-warning">${errorMessage}</p></td>
                </tr>
                <tr>
                    <td>
                        <strong>Стоимость заказа ${bookingPrice}</strong>
                    </td>
                    <td></td>
                    <td></td>
                    <td><i class="icon-search"></i></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Booking/Finish.do" method="post">
                            <input type="submit" name="Order" class="btn btn-primary btn-sm" value="Оформить"></form>

                    </td>

                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
