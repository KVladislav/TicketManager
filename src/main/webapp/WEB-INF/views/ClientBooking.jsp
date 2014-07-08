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
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">--%>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
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
                            bar.style.width = Math.floor((${bookingTime} + time++ ) * 100 / max) + '%';
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
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <form action="${pageContext.request.contextPath}/Booking/ClientSave.do" method="post"
                  name="NewClient">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <strong>Клиент</strong>
                    </div>
                </div>
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <input class="span6" size="16" type="text" name="clientName" required
                           placeholder="Введите ФИО клиента" value="${bookingClient.name}"> <br>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <textarea rows="3" class="span6" name="clientDescription"  placeholder="Описание">${bookingClient.description}</textarea>

                </div>
            </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <button class="btn btn-primary btn-sm" type="submit">Сохранить</button>
                    </div>
                </div>
            </form>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="has-error">${bookingErrorMessage}</div>
                </div>
            </div>
        </div>
        <div class="col-md-6 column">
            <table class="table table-hover table-condensed">
                <caption>
                    <a href="#" class="btn btn-large btn-primary disabled">Бронь от <fmt:formatDate value="${bookingClient.timeStamp}" pattern="d.MM.yyyy H:mm"/></a>
                </caption>
                <thead>
                <tr>
                    <th colspan="3">Мероприятие</th>
                    <th colspan="2">Дата</th>
                    <th colspan="1">Сектор</th>
                    <th colspan="1">Ряд</th>
                    <th colspan="1">Место</th>
                    <th colspan="1">Цена</th>
                    <th colspan="1"></th>
                </tr>
                </thead>

                <tbody>
                <form name="delTicket" action="${pageContext.request.contextPath}/Booking/DelBookedTicket.do"
                      method="post">
                    <c:forEach items="${bookingTickets}" var="ticket">

                        <c:if test="${ticket.confirmed==false}"><tr class="error"></c:if>
                        <c:if test="${ticket.confirmed==true}"><tr></c:if>

                            <td colspan="3"><small>${ticket.sector.event.description}</small></td>
                            <td colspan="2"><small><fmt:formatDate value="${ticket.sector.event.date}" pattern="d.MM.yyyy H:mm"/></small></td>
                            <td><small>${ticket.sector.name}</small></td>
                            <td><small>${ticket.row}</small></td>
                            <td><small>${ticket.seat}</small></td>
                            <td><small>${ticket.sector.price}</small></td>

                            <td>
                                <button type="submit" name="ticketId" value="${ticket.id}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span ></button>
                            </td>
                        </tr>

                    </c:forEach>
                </form>
                <tr>
                    <td colspan="3">
                        <strong>Стоимость заказа ${bookingPrice}</strong>
                    </td>
                    <td colspan="7">
                        <div class="progress progress-danger">
                        <div class="bar" id="progress">
                        </div>
                        </div>
                    </td>
                    </tr>

                <tr>
                    <td><form action="${pageContext.request.contextPath}/Booking/CancelOrder.do" method="post">
                        <input type="submit" name="Order" class="btn btn-primary btn-sm" value="Отмена брони"></form>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><form action="${pageContext.request.contextPath}/Booking/Booking.do" method="post">
                        <input type="submit" name="Order" class="btn btn-primary btn-sm" value="Добавить"></form>
                    </td>
                    <td><form action="${pageContext.request.contextPath}/Booking/FinishOrder.do" method="post">
                        <input type="submit" name="Order" class="btn btn-primary btn-sm" value="Забронировать"></form>
                    </td>
                    <td></td>
                    <td></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Booking/BookingPayment.do" method="post">
                            <input type="submit" name="Order" class="btn btn-primary btn-sm" value="Выкуп брони">
                        </form>
                    </td>


                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
