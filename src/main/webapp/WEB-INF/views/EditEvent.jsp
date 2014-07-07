<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 30.06.2014
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
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


</head>

<h1><div class="panel-heading" style="text-align:center;"><b>Редактирование мероприятия</b></div></h1>&MediumSpace;
<center>

<form action="${pageContext.request.contextPath}/EditEvent/editEventNow.do" method="post">
<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

<center>
 <label class="my-control-label" for="dateEvent">Дата мероприятия</label>
    <div class="control-group">
         <input type="text" name="dateEvent" id="dateEvent" value="<fmt:formatDate value='${dateEvent}' type='date' />" />
        <p> <script type="text/javascript"> calendar.set("dateEvent");</script></p>
    </div>

    <label class="my-control-label" for="inputTime">Время мероприятия</label>
    <div class="control-group">
        <select name="inputTime" id="inputTime">

            <c:if test="${eventTime.equals(inputTime)}">
                <option  selected value="${eventTime}">${eventTime}</option>
            </c:if>
            <%--         --%>

            <c:if test="${eventTime.equals('10-00')}">
                <option selected  value="10-00">10-00</option>
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
        <textarea rows="5" id="description" name="description">${eventDescriptions}</textarea>
    </div>
</div>

<div class="control-group">
    <label class="my-control-label" for="timeRemoveBooking">Установка времени удаления брони</label>
    <div class="my-controls">
        <input type="text" id="timeRemoveBooking" name="timeRemoveBooking" value="${eventBookingTimeOut}">
        <img src="${pageContext.request.contextPath}/resources/img/Question.png"
             alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
             title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
    </div>
</div>

<div class="panel-body" style="padding:20px; width:50%; margin-left: 10%">
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
<%--  <c:forEach var="iter" begin="1" end="27"> --%>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector0" name="sector0" value="1" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice0" required pattern="^[0-9]+$" title="только числа" name="price0"
                   value="${sectors.get(0).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector1" name="sector1" value="2" readonly>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice1" required pattern="^[0-9]+$" title="только числа" name="price1"
                   value="${sectors.get(1).getPrice()}">
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
            <input type="text" id="inputPrice2" required pattern="^[0-9]+$" title="только числа" name="price2"
                   value="${sectors.get(2).getPrice()}">
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
            <input type="text" id="inputPrice3" required pattern="^[0-9]+$" title="только числа" name="price3"
                   value="${sectors.get(3).getPrice()}">
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
            <input type="text" id="inputPrice4" required pattern="^[0-9]+$" title="только числа" name="price4"
                   value="${sectors.get(4).getPrice()}">
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
            <input type="text" id="inputPrice5" required pattern="^[0-9]+$" title="только числа" name="price5"
                   value="${sectors.get(5).getPrice()}">
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
            <input type="text" id="inputPrice6" required pattern="^[0-9]+$" title="только числа" name="price6"
                   value="${sectors.get(6).getPrice()}">
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
            <input type="text" id="inputPrice7" required pattern="^[0-9]+$" title="только числа" name="price7"
                   value="${sectors.get(7).getPrice()}">
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
            <input type="text" id="inputPrice8" required pattern="^[0-9]+$" title="только числа" name="price8"
                   value="${sectors.get(8).getPrice()}">
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
            <input type="text" id="inputPrice9" required pattern="^[0-9]+$" title="только числа" name="price9"
                   value="${sectors.get(9).getPrice()}">
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
            <input type="text" id="inputPrice10" required pattern="^[0-9]+$" title="только числа" name="price10"
                   value="${sectors.get(10).getPrice()}">
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
            <input type="text" id="inputPrice11" required pattern="^[0-9]+$" title="только числа" name="price11"
                   value="${sectors.get(11).getPrice()}">
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
            <input type="text" id="inputPrice12" required pattern="^[0-9]+$" title="только числа" name="price12"
                   value="${sectors.get(12).getPrice()}">
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
            <input type="text" id="inputPrice13" required pattern="^[0-9]+$" title="только числа" name="price13"
                   value="${sectors.get(13).getPrice()}">
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
            <input type="text" id="inputPrice14" required pattern="^[0-9]+$" title="только числа" name="price14"
                   value="${sectors.get(14).getPrice()}">
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
            <input type="text" id="inputPrice15" required pattern="^[0-9]+$" title="только числа" name="price15"
                   value="${sectors.get(15).getPrice()}">
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
            <input type="text" id="inputPrice16" required pattern="^[0-9]+$" title="только числа" name="price16"
                   value="${sectors.get(16).getPrice()}">
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
            <input type="text" id="inputPrice17" required pattern="^[0-9]+$" title="только числа" name="price17"
                   value="${sectors.get(17).getPrice()}">
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
            <input type="text" id="inputPrice18" required pattern="^[0-9]+$" title="только числа" name="price18"
                   value="${sectors.get(18).getPrice()}">
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
            <input type="text" id="inputPrice19" required pattern="^[0-9]+$" title="только числа" name="price19"
                   value="${sectors.get(19).getPrice()}">
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
            <input type="text" id="inputPrice20" required pattern="^[0-9]+$" title="только числа" name="price20"
                   value="${sectors.get(20).getPrice()}">
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
            <input type="text" id="inputPrice21" required pattern="^[0-9]+$" title="только числа" name="price21"
                   value="${sectors.get(21).getPrice()}">
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
            <input type="text" id="inputPrice22" required pattern="^[0-9]+$" title="только числа" name="price22"
                   value="${sectors.get(22).getPrice()}">
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
            <input type="text" id="inputPrice23" required pattern="^[0-9]+$" title="только числа" name="price23"
                   value="${sectors.get(23).getPrice()}">
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
            <input type="text" id="inputPrice24" required pattern="^[0-9]+$" title="только числа" name="price24"
                   value="${sectors.get(24).getPrice()}">
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
            <input type="text" id="inputPrice25" required pattern="^[0-9]+$" title="только числа" name="price25"
                   value="${sectors.get(25).getPrice()}">
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
            <input type="text" id="inputPrice26" required pattern="^[0-9]+$" title="только числа" name="price26"
                   value="${sectors.get(26).getPrice()}">
        </div>
    </td>
</tr>

</tbody>
</table>
</div>
</div>

&MediumSpace;
&MediumSpace;
<button type="submit" name="action" class="btn btn-primary">Сохранить</button>
&MediumSpace;
&MediumSpace;
</form>
</center>
