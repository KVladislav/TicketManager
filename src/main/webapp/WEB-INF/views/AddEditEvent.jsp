<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 27.06.2014
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html lang="ru">
<head>

    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/date.js"></script>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/css/bootstrap.css"></script>
    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery.js" />'></script>
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>

    <script type="text/javascript">
        $(document).on("hover", ".cont", function () {
            $(this).children(".overlay").fadeIn("fast");
        }, function () {
            $(this).children(".overlay").fadeOut("fast");
        });
    </script>


    <title>
        <c:if test="${empty eventEdit.id}"> Создание нового мероприятия </c:if>
        <c:if test="${not empty eventEdit.id}"> Редактирование мероприятия </c:if>
    </title>

</head>
<%--
--%>
<center>

<c:if test="${empty eventEdit.id}">
<h1>
    <div class="panel-heading" style="text-align:center;"><b> Создание нового мероприятия </b></div>
</h1>&MediumSpace;&MediumSpace;
<p class="alert-error">${eventErrorMessage}</p>

<form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post"></c:if>
<c:if test="${not empty eventEdit.id}">
<h1>
    <div class="panel-heading" style="text-align:center;"><b> Редактирование мероприятия </b></div>
</h1>&MediumSpace;&MediumSpace;
<p class="alert-error">${errorMessageEdit}</p>

<form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do" method="post"></c:if>
<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

<center>
<label class="my-control-label" required warning for="dateEvent"> Дата мероприятия </label>

<center>

    <div class="control-group">
        <input type="text" name="dateEvent" readonly id="dateEvent"
               value="<fmt:formatDate value='${dateEvent}' type='date' />"/>

        <p>
            <script type="text/javascript"> calendar.set("dateEvent");</script>
        </p>
    </div>

    <label class="my-control-label" for="inputTime"> Время мероприятия </label>

    <div class="control-group">
        <select name="inputTime" id="inputTime">

            <c:if test="${eventTime.equals(inputTime)}">
                <option selected value="${eventTime}">${eventTime}</option>
            </c:if>

            <c:if test="${eventTime.equals('10-00')}">
                <option selected value="10-00">10-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('10-00')}">
                <option value="10-00">10-00</option>
            </c:if>
            <c:if test="${eventTime.equals('10-30')}">
                <option selected value="10-30">10-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('10-30')}">
                <option value="10-30">10-30</option>
            </c:if>
            <c:if test="${eventTime.equals('11-00')}">
                <option selected value="11-00">11-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('11-00')}">
                <option value="11-00">11-00</option>
            </c:if>
            <c:if test="${eventTime.equals('11-30')}">
                <option selected value="11-30">11-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('11-30')}">
                <option value="11-30">11-30</option>
            </c:if>
            <c:if test="${eventTime.equals('12-00')}">
                <option selected value="12-00">12-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('12-00')}">
                <option value="12-00">12-00</option>
            </c:if>
            <c:if test="${eventTime.equals('12-30')}">
                <option selected value="12-30">12-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('12-30')}">
                <option value="12-30">12-30</option>
            </c:if>
            <c:if test="${eventTime.equals('13-00')}">
                <option selected value="13-00">13-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('13-00')}">
                <option value="13-00">13-00</option>
            </c:if>
            <c:if test="${eventTime.equals('13-30')}">
                <option selected value="13-30">13-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('13-30')}">
                <option value="13-30">13-30</option>
            </c:if>
            <c:if test="${eventTime.equals('14-00')}">
                <option selected value="14-00">14-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('14-00')}">
                <option value="14-00">14-00</option>
            </c:if>
            <c:if test="${eventTime.equals('14-30')}">
                <option selected value="14-30">14-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('14-30')}">
                <option value="14-30">14-30</option>
            </c:if>
            <c:if test="${eventTime.equals('15-00')}">
                <option selected value="15-00">15-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('15-00')}">
                <option value="15-00">15-00</option>
            </c:if>
            <c:if test="${eventTime.equals('15-30')}">
                <option selected value="15-30">15-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('15-30')}">
                <option value="15-30">15-30</option>
            </c:if>
            <c:if test="${eventTime.equals('16-00')}">
                <option selected value="16-00">16-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('16-00')}">
                <option value="16-00">16-00</option>
            </c:if>
            <c:if test="${eventTime.equals('16-30')}">
                <option selected value="16-30">16-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('16-30')}">
                <option value="16-30">16-30</option>
            </c:if>
            <c:if test="${eventTime.equals('17-00')}">
                <option selected value="17-00">17-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('17-00')}">
                <option value="17-00">17-00</option>
            </c:if>
            <c:if test="${eventTime.equals('17-30')}">
                <option selected selected value="17-30">17-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('17-30')}">
                <option value="17-30">17-30</option>
            </c:if>
            <option value="18-00">18-00</option>
            <c:if test="${eventTime.equals('18-00')}">
                <option selected value="18-00">18-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('18-00')}">
                <option value="18-00">18-00</option>
            </c:if>
            <option value="18-00">18-30</option>
            <c:if test="${eventTime.equals('18-30')}">
                <option selected value="18-30">18-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('18-30')}">
                <option value="18-30">18-30</option>
            </c:if>
            <option value="19-00">19-00</option>
            <c:if test="${eventTime.equals('19-00')}">
                <option selected value="19-00">19-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('19-00')}">
                <option value="19-00">19-00</option>
            </c:if>
            <c:if test="${eventTime.equals('19-30')}">
                <option selected value="19-30">19-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('19-30')}">
                <option value="19-30">19-30</option>
            </c:if>
            <c:if test="${eventTime.equals('20-00')}">
                <option selected value="20-00">20-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('20-00')}">
                <option value="20-00">20-00</option>
            </c:if>
            <c:if test="${eventTime.equals('20-30')}">
                <option selected value="20-30">20-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('20-30')}">
                <option value="20-30">20-30</option>
            </c:if>
            <c:if test="${eventTime.equals('21-00')}">
                <option selected value="21-00">21-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('21-00')}">
                <option value="21-00">21-00</option>
            </c:if>
            <c:if test="${eventTime.equals('21-30')}">
                <option selected value="21-30">21-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('21-30')}">
                <option value="21-30">21-30</option>
            </c:if>
            <c:if test="${eventTime.equals('22-00')}">
                <option selected value="22-00">22-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('22-00')}">
                <option value="22-00">22-00</option>
            </c:if>
            <c:if test="${eventTime.equals('22-30')}">
                <option selected value="22-30">22-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('22-30')}">
                <option value="22-30">22-30</option>
            </c:if>

        </select>
    </div>
</center>

<div class="control-group">
    <label class="my-control-label" for="description"> Наименование </label>

    <div class="my-controls">
        <textarea rows="5" id="description" maxlength="50" name="description" required pattern="^[a-z][A-Z]*$"
                  title="Наименование не должно быть пустым - до 50 знаков!">${eventDescriptions.trim().replaceAll("\\u00A0", "")}</textarea>
    </div>
</div>

<div class="control-group">
    <label class="my-control-label" for="timeRemoveBooking"> Установка времени удаления брони </label>

    <div class="my-controls">
        <input type="text" id="timeRemoveBooking" name="timeRemoveBooking" value="${eventBookingTimeOut}"
               required pattern="[1-9]\d{0,2}?" title="Только целое положительное число от одной до трех цифр!">
        <img src="${pageContext.request.contextPath}/resources/img/Question.png"
             alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
             title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
    </div>
</div>

<div class="panel-body" style="padding:30px; width:40%;">
    <div class="table responsive">
        <table class="table table-bordered" style="display:block;height:400px;overflow:auto;">
            <thead>
            <tr>
                <th>Сектор</th>
                <th>Цена</th>
            </tr>
            </thead>
            `
            <tbody>

            <c:forEach items="${sectors}" var="sector" varStatus="theCount">

                <tr>
                    <td>
                        <input type="text" name="name${sector.name}" required placeholder="Сектор"
                               value="${sector.name}">
                    </td>
                    <td>

                        <div>
                            <input type="text" required pattern="^\d+\.\d{0,2}$"
                                   title="только double числа с точкой и от нуля до двух знаков после запятой"
                                   name="id${sector.id}" placeholder="Цена" value="${sector.price}">
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        &MediumSpace;
        &MediumSpace;
        <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
        &MediumSpace;
        &MediumSpace;
        <%--
        <c:if test="${empty name}">
            <div class="alert  alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <h4>Error!</h4>Поле "Наименование" должно быть заполнено. Проверьте его.
            </div>
        </c:if>
        --%>


</form>


</center>


