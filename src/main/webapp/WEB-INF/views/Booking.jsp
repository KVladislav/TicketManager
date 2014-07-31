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
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>

    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>


    <script type='text/javascript'>//<![CDATA[
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
        <div class="col-md-8 column">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <strong class="text-info">Мероприятие</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setSectors.do" method="post">
                        <p><select size="10" name="eventId" data-size="3" class="form-control">
                            <c:forEach items="${bookingEvents}" var="evnt">
                                <c:if test="${bookingEvent.id==evnt.id}">
                                    <option value="${evnt.id}" onclick="this.form.submit()"
                                            selected>${evnt.description} <fmt:formatDate value="${evnt.date}"
                                                                                         pattern="d.MM.yyyy H:mm"/></option>
                                </c:if>
                                <c:if test="${bookingEvent.id!=evnt.id}">
                                    <option value="${evnt.id}"
                                            onclick="this.form.submit()">${evnt.description} <fmt:formatDate
                                            value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <strong class="text-info">Сектор</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setRow.do" method="post">
                        <p><select size="10" name="sectorId" class="form-control">
                            <c:forEach items="${bookingSectorsMap}" var="sectorEntry">
                                <c:if test="${bookingSector.id==sectorEntry.key.id}">
                                    <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                            selected>${sectorEntry.key.name} цена ${sectorEntry.key.price}
                                        свободно: ${sectorEntry.value}</option>
                                </c:if>
                                <c:if test="${bookingSector.id!=sectorEntry.key.id}">
                                    <option value="${sectorEntry.key.id}"
                                            onclick="this.form.submit()">${sectorEntry.key.name}
                                        цена ${sectorEntry.key.price} свободно: ${sectorEntry.value}</option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <strong class="text-info">Ряд</strong>

                    <form action="${pageContext.request.contextPath}/Booking/setSeat.do" method="post">
                        <p><select size="11" name="row" class="form-control">
                            <c:forEach items="${bookingRowsMap}" var="rowEntry">
                                <c:if test="${rowEntry.key==bookingRow}">
                                    <option value="${rowEntry.key}" onclick="this.form.submit()"
                                            selected>${rowEntry.key}
                                        свободно: ${rowEntry.value}</option>
                                </c:if>
                                <c:if test="${bookingRow!=rowEntry.key}">
                                    <option value="${rowEntry.key}" onclick="this.form.submit()">${rowEntry.key}
                                        свободно: ${rowEntry.value}</option>
                                </c:if>
                            </c:forEach>
                        </select></p>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <%--<strong class="text-info">Место</strong>--%>

                    <form action="${pageContext.request.contextPath}/Booking/addTicket.do" method="post">
                        <select multiple="multiple" id="my-select" name="seats" name="my-select[]">
                            <c:forEach items="${bookingSeatsMap}" var="seatEntry">
                                <c:if test="${seatEntry.value==1}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [в заказе]
                                    </option>
                                </c:if>
                                <c:if test="${seatEntry.value==2}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [забронирован]
                                    </option>
                                </c:if>
                                <c:if test="${seatEntry.value==3}">
                                    <option disabled="disabled" value="${seatEntry.key}">${seatEntry.key} [продан]
                                    </option>
                                </c:if>
                                <c:if test="${seatEntry.value==0}">
                                    <option value="${seatEntry.key}">${seatEntry.key}</option>
                                </c:if>
                            </c:forEach>
                            <%--<c:forEach items="${seatsMap}" var="seatEntry">--%>
                            <%--<option value="${seatEntry}">${seatEntry}</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $('#my-select').multiSelect({
                                    selectableHeader: "<div class='custom-header'><strong class=' text-info'>Все места</strong></div>",
                                    selectionHeader: "<div class='custom-header'><strong class=' text-info'>Выбранные места</strong></div>"
//                                        selectableFooter: "<div class='custom-header'>Selectable footer</div>",
//                                        selectionFooter: "<div class='custom-header'>Selection footer</div>"
                                });
                            });
                        </script>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <c:if test="${bookingTimeOut!=null}">
                        <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                           title="Осталось до отмены заказа">
                            <div class="progress">
                                <div class="progress-bar progress-bar-danger" id="progress" role="progressbar">
                                </div>
                            </div>
                        </a>
                        <script>
                            $("a.my-tool-tip").tooltip();
                        </script>
                    </c:if>
                </div>
                <div class="col-md-4 column">
                    <a class="btn btn-danger btn-md" href="${pageContext.request.contextPath}/Booking/Cancel.do"
                       role="button">Отмена</a>
                </div>
                <div class="col-md-4 column">
                    <input type="submit" name="Order" class="btn btn-primary btn-md" value="Добавить">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-4 column">
            <table class="table table-hover table-condensed" style=" margin-bottom: 50px;">
                <caption>
                    <strong class="text-info">Сектора по ценам</strong>
                </caption>
                <thead>
                </thead>

                <tbody>
                <c:forEach items="${sectorsGroupedMap}" var="mapEntry">
                    <tr style="background-color: #D1D1D1; ">
                        <td>
                            <strong>${mapEntry.key} грн:</strong>
                        </td>
                    </tr>
                    <c:forEach items="${mapEntry.value}" var="sortedSector">
                        <tr>
                            <td>
                                Сектор: ${sortedSector.name} свободно: ${bookingSectorsMap[sortedSector]}
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>
</div>

<button style="display: none;" class="btn btn-danger btn-sm" id="bookingCancelNotificationButton" data-toggle="modal"
        data-target="#bookingCancelNotification">
    Отмена брони
</button>
<!-- Modal bookingCancelNotification-->
<div class="modal" id="bookingCancelNotification" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
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
                                    class="btn btn-danger btn-lg" value="">Закрыть
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="footer.jsp" %>
</html>
