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
        <c:if test="${empty eventEdit.id}"> Создание нового мероприяти</c:if>
        <c:if test="${not empty eventEdit.id}"> Редактирование мероприятия </c:if>
    </title>

</head>
<%--
<h1>
    <div class="panel-heading" style="text-align:center;"><b>Создание нового мероприятия</b></div>
</h1>&MediumSpace; --%>
<center>

<c:if test="${empty eventEdit.id}">
<form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post"></c:if>
<c:if test="${not empty eventEdit.id}">
<form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do" method="post"></c:if>
<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

<p class="alert-error">${eventErrorMessage}</p>

<center>
    <label class="my-control-label" required warning for="dateEvent">Дата мероприятия</label>

    <center>

        <div class="control-group">
            <input type="text" name="dateEvent" required pattern="^\d{2}\.\d{2}\.\d{4}$"
                   title="только дата в формате дд.мм.гггг" id="dateEvent"
                   value="<fmt:formatDate value='${dateEvent}' type='date' />"/>

            <p>
                <script type="text/javascript"> calendar.set("dateEvent");</script>
            </p>
        </div>

        <label class="my-control-label" for="inputTime">Время мероприятия</label>

        <div class="control-group">
            <select name="inputTime" id="inputTime">

                <c:if test="${eventTime.equals(inputTime)}">
                    <option selected value="${eventTime}">${eventTime}</option>
                </c:if>
                <%--         --%>

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
        <label class="my-control-label" for="description">Наименование</label>

        <div class="my-controls">
            <textarea rows="5" id="description" maxlength="80" name="description">${eventDescriptions}</textarea>
        </div>
    </div>

    <div class="control-group">
        <label class="my-control-label" for="timeRemoveBooking">Установка времени удаления брони</label>

        <div class="my-controls">
            <input type="text" id="timeRemoveBooking" name="timeRemoveBooking" value="${eventBookingTimeOut}"
                   required pattern="^[1-9][0-9]{1,2}$" title="Только целое положительное число от одной до двух цифр!">
            <img src="${pageContext.request.contextPath}/resources/img/Question.png"
                 alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
                 title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
        </div>
    </div>

    <%--
    <table class="table table-hover table-condensed">
        <caption>
            <a href="#" class="btn btn-large btn-primary disabled">Сектора</a></caption>
        <thead>
        <tr>
            <th colspan="1">Название</th>
            <th>Цена</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${sectorsDefaults}" var="sector">

                <tr>
                    <td colspan="1">
                        <input class="span1" size="16" type="text" name="sectorName" required
                               placeholder="Сектор" value="${sector.sectorName}">
                    </td>
                    <td>
                        <input class="span1" size="16" type="text" name="defaultPrice" required
                               placeholder="Цена" value="${sector.defaultPrice}">
                    </td>

                </tr>

        </c:forEach>
        </tbody>
    </table>
    --%>


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
                <input type="text" name="sectorName" required placeholder="Сектор" value="${sector.name}">
            </td>
            <td>

                <div>
                        <%--         <input type="hidden" name="sectorId" value="${sector.id}">  --%>
                    <input type="text" required pattern="^\d+\.\d{0,2}$"
                           title="только double числа с точкой и от нуля до двух знаков после запятой"
                           name="id${sector.id}" placeholder="Цена" value="${sector.price}">
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%--

<tr>
    <td>
        <div>
            <input type="text" id="inputSector1" name="sector1" value="2" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice1"
                   name="price1">
        </div>
    </td>
</tr>

<tr>
    <td>
        <div>
            <input type="text" id="inputSector2" name="sector2" value="3" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice2"
                   name="price2">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector3" name="sector3" value="4" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice3"
                   name="price3">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector4" name="sector4" value="5" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice4"
                   name="price4">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector5" name="sector5" value="6" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice5"
                   name="price5">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector6" name="sector6" value="7" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice6"
                   name="price6">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector7" name="sector7" value="8" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice7"
                   name="price7">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector8" name="sector8" value="9" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice8"
                   name="price8">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector9" name="sector9" value="10" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice9"
                   name="price9">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector10" name="sector10" value="11" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice10"
                   name="price10">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector11" name="sector11" value="12" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice11"
                   name="price11">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector12" name="sector12" value="13" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice12"
                   name="price12">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector13" name="sector13" value="14" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice13"
                   name="price13">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector14" name="sector14" value="15" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice14"
                   name="price14">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector15" name="sector15" value="16" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice15"
                   name="price15">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector16" name="sector16" value="17" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice16"
                   name="price16">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector17" name="sector17" value="18" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice17"
                   name="price17">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector18" name="sector18" value="19" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice18"
                   name="price18">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector19" name="sector19" value="20" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice19"
                   name="price19">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector20" name="sector20" value="21" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice20"
                   name="price20">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector21" name="sector21" value="22" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice21"
                   name="price21">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector22" name="sector22" value="23" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice22"
                   name="price22">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector23" name="sector23" value="24" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice23"
                   name="price23">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector24" name="sector24" value="25" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice24"
                   name="price24">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector25" name="sector25" value="A" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice25"
                   name="price25">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector26" name="sector26" value="D" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" required pattern="^([0-9]*)\.([0-9]*)+$" title="только double числа" id="inputPrice26"
                   name="price26">
        </div>
    </td>
</tr>

</tbody>
</table>
</div>
</div>
--%>
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


