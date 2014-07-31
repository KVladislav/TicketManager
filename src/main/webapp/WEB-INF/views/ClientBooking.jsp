<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Бронирование билета</title>
    <!-- Bootstrap -->
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>
    <script>
        $(document).ready(function () {
            $(".deleteTicket").click(function () {
                var data_id = '';
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                $('#ticketId').val(data_id);
            })
        });
    </script>
    <script type='text/javascript'>
        window.onload = function () {
            function countdown(callback) {
                var bar = document.getElementById('progress'),
                        time = 0, max = 10 * 60,
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
                <%--alert('Изменения отменены');--%>
                <%--window.location.replace("${pageContext.request.contextPath}/Booking/UndoOrder.do");--%>
                document.getElementById('bookingCancelNotificationButton').click();
            });
        }
    </script>
</head>
<body>

<div class="container">
<div class="row clearfix">
<div class="col-md-4 column">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <br>

            <form class="form" action="${pageContext.request.contextPath}/Booking/ClientSave.do"
                  method="post" name="NewClient">
                <div class="control-group">
                    <label class="control-label text-info" for="clientName">ФИО</label>

                    <div class="controls">
                        <input class="form-control" maxlength="50" id="clientName" type="text" name="clientName"
                               required
                               value="${bookingClient.name}"
                               pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,49}"
                               title="Не пустое, не начинатся с пробела, до 30 знаков">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label text-info" for="clientDescription">Описание</label>

                    <div class="controls">

                        <textarea style="resize:none" rows="3" maxlength="200" class="form-control"
                                  id="clientDescription"
                                  name="clientDescription"
                                  placeholder="Описание">${bookingClient.description}</textarea> <br>
                        <button class="btn btn-primary btn-md" type="submit">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="col-md-8 column">
<div class="row clearfix">
    <div class="col-md-12 column">
        <c:forEach items="${bookingErrorMessages}" var="errorMessage">
            <div class="alert alert-warning" role="alert">${errorMessage}</div>
        </c:forEach>
        <%--</c:if>--%>
    </div>
</div>
<div class="row clearfix">
<div class="col-md-12 column">
<table class="table table-hover table-condensed">
    <caption>
        <h3 class="panel-heading text-info">Бронь от <fmt:formatDate value="${bookingClient.timeStamp}"
                                                                     pattern="dd.MM.yy H:mm"/></h3>
    </caption>
    <thead>
    <tr>
        <th>ID</th>
        <th colspan="2">Мероприятие</th>
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
    <%--<form name="delTicket" id="delTicket">--%>
    <%--<c:set var="number" value="1"/>--%>
    <c:forEach items="${bookingTickets}" var="ticket">
        <tr>
            <td>${ticket.id}</td>
                <%--<c:set var="number" value="${number+1}"/>--%>
            <td colspan="2">
                <small>${ticket.sector.event.description}</small>
            </td>
            <td>
                <c:if test="${ticket.confirmed==false}">
                    <span class="label label-warning">Не подтвержден</span>
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

                <form action="${pageContext.request.contextPath}/Booking/DelBookedTicket.do"
                      method="post">

                    <button type="submit" class="btn btn-default btn-xs" value="${ticket.id}"
                            name="ticketId">
                        <span class="glyphicon glyphicon-trash"></span>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>


    <c:forEach items="${bookingErrorTickets}" var="ticket">
        <tr class="alert-warning">
            <td><span class="glyphicon glyphicon-warning-sign"></span></td>
            <td colspan="2">
                <small>${ticket.sector.event.description}</small>
            </td>
            <td>
                <span class="label label-danger">УЖЕ ПРОДАН</span>
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

            </td>
        </tr>
    </c:forEach>


    <tr>
        <td colspan="3">
            <c:if test="${!empty bookingTickets}"><strong>Стоимость заказа <fmt:formatNumber pattern="0.00"
                                                                                             value="${bookingPrice}"/></strong></c:if>
        </td>
        <td colspan="8">
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
    <div class="col-md-2 column">
        <button class="btn btn-danger btn-md" data-toggle="modal" data-target="#bookingCancelConfirmation">
            Отмена брони
        </button>
    </div>
    <div class="col-md-2 column">
        <form action="${pageContext.request.contextPath}/Booking/Booking.do" method="post">
            <input type="submit" name="Order" class="btn btn-primary btn-md"
                   value="Добавить билет">
        </form>
    </div>
    <div class="col-md-2 column"></div>
    <div class="col-md-3 column" style="margin-bottom: 60px">
        <c:if test="${!empty bookingTickets && bookingTimeOut!=null}">
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#BookingConfirmation">
                Забронировать
            </button>
        </c:if>
    </div>
    <div class="col-md-3 column">
        <c:if test="${!empty bookingTickets}">
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#CheckoutOrderConfirmation">
                Выкуп брони
            </button>
        </c:if>
    </div>
</div>

<button style="display: none;" class="btn btn-danger btn-sm" id="bookingCancelNotificationButton" data-toggle="modal"
        data-target="#bookingCancelNotification">
    Отмена брони
</button>


<!-- Modal BookingConfirmation-->
<div class="modal fade" id="BookingConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h2 class="modal-title" id="myModalLabel">Внимание! </h2>
            </div>
            <div class="modal-body">
                <h4>Подтвердите бронирование билетов</h4>

                <table class="table table-hover table-condensed">
                    <caption>
                        <h4 class="panel-heading text-info">Клиент: <strong>${bookingClient.name}</strong></h4>
                        <h4 class="panel-heading text-info">Бронь от <fmt:formatDate value="${bookingClient.timeStamp}"
                                                                                     pattern="dd.MM.yy H:mm"/></h4>
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
                    <%--<c:set var="number" value="1"/>--%>
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
            <div class="modal-footer"">
                <div class="row clearfix">
                    <div class="col-md-8 column" >
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Назад</button>
                    </div>
                    <div class="col-md-2 column"style="margin-bottom: 50px">
                        <form action="${pageContext.request.contextPath}/Booking/FinishOrder.do"
                              method="post">
                            <input type="submit" name="Order" class="btn btn-success btn-md"
                                   value="Забронировать">
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>

<!-- Modal CheckoutOrderConfirmation-->
<div class="modal fade" id="CheckoutOrderConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Подтвердите выкуп брони</h4>
                <table class="table table-hover table-condensed">
                    <caption>
                        <h4 class="panel-heading text-info">Клиент: <strong>${bookingClient.name}</strong></h4>
                        <h4 class="panel-heading text-info">Бронь от <fmt:formatDate value="${bookingClient.timeStamp}"
                                                                                     pattern="dd.MM.yy H:mm"/></h4>

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
                    <%--<c:set var="number" value="1"/>--%>
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
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Назад</button>
                    </div>
                    <div class="col-md-2 column">
                        <form action="${pageContext.request.contextPath}/Booking/BookingPayment.do"
                              method="post">
                            <input type="submit" name="Order" class="btn btn-success btn-md"
                                   value="Выкуп брони">
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>

<!-- Modal ticketDeleteConfirmation-->
<%--<div class="modal" id="ticketDeleteConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"--%>
<%--aria-hidden="true">--%>
<%--<div class="modal-dialog">--%>
<%--<div class="modal-content">--%>
<%--<div class="modal-header">--%>
<%--<h4 class="modal-title" id="myModalLabel">Внимание! </h4>--%>
<%--</div>--%>
<%--<div class="modal-body">--%>
<%--<h4>Подтвердите удаление билета</h4>--%>
<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<div class="row clearfix">--%>
<%--<div class="col-md-8 column">--%>
<%--<button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Назад</button>--%>
<%--</div>--%>
<%--<div class="col-md-2 column">--%>

<%--<form action="${pageContext.request.contextPath}/Booking/DelBookedTicket.do"--%>
<%--method="post" id="submitDelete">--%>
<%--<button type="submit" name="ticketId" id="ticketId"--%>
<%--class="btn btn-danger btn-md" value="">Удалить--%>
<%--</button>--%>
<%--</form>--%>


<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

<!-- Modal bookingCancelConfirmation-->
<div class="modal" id="bookingCancelConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Подтвердите отмену бронирования</h4>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-9 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Назад</button>
                    </div>
                    <div class="col-md-2 column">
                        <form action="${pageContext.request.contextPath}/Booking/CancelOrder.do"
                              method="post">
                            <button type="submit"
                                    class="btn btn-danger btn-md" value="">Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal bookingCancelNotification-->
<div class="modal" id="bookingCancelNotification" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span--%>
                <%--class="sr-only">Close</span></button>--%>
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Заказ отменен из-за окончания времени бронирования</h4>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-lg-1 column col-lg-offset-9">
                        <form action="${pageContext.request.contextPath}/Booking/CancelOrder.do"
                              method="post">
                            <button type="submit"
                                    class="btn btn-danger btn-md" value="">Закрыть
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
</div>
</div>
</div>
</div>

</body>

<%@include file="footer.jsp" %>
</html>
