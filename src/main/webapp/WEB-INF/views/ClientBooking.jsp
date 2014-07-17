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
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>
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
            window.location.replace("${pageContext.request.contextPath}/Booking/UndoOrder.do");
        });
    }//]]>

    </script>
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form class="form" action="${pageContext.request.contextPath}/Booking/ClientSave.do"
                          method="post" name="NewClient">
                        <div class="control-group">
                            <label class="control-label" for="clientName">ФИО</label>

                            <div class="controls">
                                <input class="form-control" maxlength="50" id="clientName" type="text" name="clientName" required
                                       value="${bookingClient.name}"
                                       pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,49}"
                                       title="Не пустое, не начинатся с пробела, до 30 знаков">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="clientDescription">Описание</label>

                            <div class="controls">

                                <textarea style="resize:none" rows="3" class="form-control" id="clientDescription" name="clientDescription"
                                          placeholder="Описание">${bookingClient.description}</textarea> <br>
                                <button class="btn btn-primary btn-sm" type="submit">Сохранить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="has-error">${bookingErrorMessage}</div>
                </div>
            </div>
        </div>
        <div class="col-md-8 column">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-hover table-condensed">
                        <caption>
                            <h3><code>Бронь от <fmt:formatDate value="${bookingClient.timeStamp}"
                                                               pattern="dd.MM.yy H:mm"/></code></h3>
                        </caption>
                        <thead>
                        <tr>
                            <th colspan="3">Мероприятие</th>
                            <th colspan="1">Статус</th>
                            <th colspan="1">Дата</th>
                            <th colspan="1">Отмена</th>
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
                                <tr>
                                    <td colspan="3">
                                        <small>${ticket.sector.event.description}</small>
                                    </td>
                                    <td>
                                        <c:if test="${ticket.confirmed==false}">
                                            <span class="label label-danger">Не подтвержден</span>
                                        </c:if>
                                        <c:if test="${ticket.confirmed==true}">
                                            <small>Забронирован</small>
                                        </c:if>
                                    </td>
                                    <td colspan="1">
                                        <small><fmt:formatDate value="${ticket.sector.event.date}"
                                                               pattern="dd.MM.yy H:mm"/></small>
                                    </td>
                                    <td colspan="1">
                                        <small><fmt:formatDate value="${ticket.sector.event.bookingTimeOut}"
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

                                    <td>
                                        <button type="submit" name="ticketId" value="${ticket.id}"
                                                class="btn btn-default btn-xs">
                                            <span class="glyphicon glyphicon-trash"></span></button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </form>
                        <tr>
                            <td colspan="3">
                                <strong>Стоимость заказа ${bookingPrice}</strong>
                            </td>
                            <td colspan="7">

                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <c:if test="${bookingTimeOut!=null}">
                                <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                                   title="Осталось до отмены заказа">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" id="progress"
                                             role="progressbar">
                                        </div>
                                    </div>
                                </a>
                                <script>
                                    $("a.my-tool-tip").tooltip();
                                </script>
                            </c:if>
                        </div>
                    </div>

                    <div class="row clearfix">
                        <div class="col-md-3 column">
                            <form action="${pageContext.request.contextPath}/Booking/CancelOrder.do" method="post">
                                <input type="submit" name="Order" class="btn btn-danger btn-sm"
                                       value="Отмена брони">
                            </form>
                        </div>
                        <div class="col-md-3 column">
                            <form action="${pageContext.request.contextPath}/Booking/Booking.do" method="post">
                                <input type="submit" name="Order" class="btn btn-primary btn-sm"
                                       value="Добавить билет">
                            </form>
                        </div>
                        <div class="col-md-3 column">
                            <c:if test="${bookingTimeOut!=null}">
                                <form action="${pageContext.request.contextPath}/Booking/FinishOrder.do"
                                      method="post">
                                    <input type="submit" name="Order" class="btn btn-primary btn-lg"
                                           value="Забронировать">
                                </form>
                            </c:if>
                        </div>
                        <div class="col-md-3 column">
                            <c:if test="${bookingTickets!=null}">
                                <form action="${pageContext.request.contextPath}/Booking/BookingPayment.do"
                                      method="post">
                                    <input type="submit" name="Order" class="btn btn-primary btn-lg"
                                           value="Выкуп брони">
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
