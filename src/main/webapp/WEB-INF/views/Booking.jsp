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

    <script type='text/javascript'>//<![CDATA[
    window.onload = function () {
        function countdown(callback) {
            var bar = document.getElementById('progress'),
                    time = 0, max = 5 * 60,
                    int = setInterval(function () {
                        if (${bookingTime!=null}) {
                            bar.style.width = Math.floor((${bookingTime} +time++ ) * 100 / max) + '%';
                            if (time - 1 + ${bookingTime} >= max) {
                                clearInterval(int);
                                // 600ms - width animation time
                                callback && setTimeout(callback, 600);
                            }
                        }
                    }, 1000);
        }

        countdown(function () {
            alert('Заказ отменен');
            window.location.replace("${pageContext.request.contextPath}/Booking/CancelOrder.do");
        });
    }//]]>

    </script>

    <%--<style type='text/css'>--%>
    <%--body {--%>
    <%--padding: 50px;--%>
    <%--}--%>
    <%--</style>--%>

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-7 column">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <strong>Мероприятие</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setSectors.do" method="post">
                        <p><select size="10" name="eventId" data-size="3" class="form-control">
                            <c:forEach items="${bookingEvents}" var="evnt">
                                <c:if test="${bookingEvent.id==evnt.id}">
                                    <option value="${evnt.id}" onclick="this.form.submit()"
                                            selected>${evnt.description} <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                                </c:if>
                                <c:if test="${bookingEvent.id!=evnt.id}">
                                    <option value="${evnt.id}"
                                            onclick="this.form.submit()">${evnt.description} <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <strong>Сектор</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setRow.do" method="post">
                        <p><select size="10" name="sectorId" class="form-control">
                            <c:forEach items="${bookingSectorsMap}" var="sectorEntry">
                                <c:if test="${bookingSector.id==sectorEntry.key.id}">
                                    <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                            selected>${sectorEntry.key.name} price ${sectorEntry.key.price}
                                        free: ${sectorEntry.value}</option>
                                </c:if>
                                <c:if test="${bookingSector.id!=sectorEntry.key.id}">
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
                            <c:forEach items="${bookingRowsMap}" var="rowEntry">
                                <c:if test="${rowEntry.key==bookingRow}">
                                    <option value="${rowEntry.key}" onclick="this.form.submit()"
                                            selected>${rowEntry.key}
                                        free: ${rowEntry.value}</option>
                                </c:if>
                                <c:if test="${bookingRow!=rowEntry.key}">
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
                            <c:forEach items="${bookingSeatsMap}" var="seatEntry">
                                <c:if test="${seatEntry.value==1}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [в заказе]</option>
                                </c:if>
                                <c:if test="${seatEntry.value==2}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [в резерве]</option>
                                </c:if>
                                <c:if test="${seatEntry.value==3}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [продан]</option>
                                </c:if>
                                <c:if test="${seatEntry.value==0}">
                                    <option value="${seatEntry.key}">${seatEntry.key}</option>
                                </c:if>
                            </c:forEach>
                            <%--<c:forEach items="${seatsMap}" var="seatEntry">--%>
                            <%--<option value="${seatEntry}">${seatEntry}</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <div class="progress">
                    <div class="bar" id="progress">
                    </div>
                    </div>
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
        <div class="col-md-5 column">
            <%--<table class="table table-hover table-condensed">--%>
                <%--<caption>--%>
                    <%--<a href="#" class="btn btn-large btn-primary disabled">Клиент: ${bookingClient.name}</a>--%>
                <%--</caption>--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th>Мероприятие</th>--%>
                    <%--<th>Сектор</th>--%>
                    <%--<th>Ряд</th>--%>
                    <%--<th>Место</th>--%>
                    <%--<th>Цена</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>

                <%--<tbody>--%>
                <%--<form name="delTicket" action="${pageContext.request.contextPath}/Booking/delTicket.do" method="post">--%>
                <%--<c:forEach items="${bookingTickets}" var="ticket">--%>
                    <%--<tr>--%>

                            <%--<td>${ticket.sector.event.description}</td>--%>
                            <%--<td>${ticket.sector.name}</td>--%>
                            <%--<td>${ticket.row}</td>--%>
                            <%--<td>${ticket.seat}</td>--%>
                            <%--<td>${ticket.sector.price}</td>--%>
                            <%--<td>--%>
                                <%--<button type="submit" name="ticketId" value="${ticket.id}" class="btn btn-default btn-xs"><span--%>
                                        <%--class="glyphicon glyphicon-trash"></span></button>--%>
                            <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:forEach>--%>
                <%--</form>--%>
                <%--<tr>--%>
                    <%--<td><p class="alert-warning">${bookingErrorMessage}</p></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>--%>
                        <%--<strong>Стоимость заказа ${bookingPrice}</strong>--%>

                        <%--<div class="progress">--%>
                            <%--<div class="bar" id="progress">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</td>--%>
                    <%--<td>--%>

                    <%--</td>--%>
                    <%--<td></td>--%>
                    <%--<td>--%>

                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<form action="${pageContext.request.contextPath}/Booking/Finish.do" method="post">--%>
                            <%--<input type="submit" name="Order" class="btn btn-primary btn-sm" value="Оформить"></form>--%>

                    <%--</td>--%>

                <%--</tr>--%>
                <%--</tbody>--%>
            <%--</table>--%>
        </div>
    </div>
</div>
</body>
</html>
