<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 19.06.2014
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="/resources/css/bootstrap.css" rel="stylesheet" media="screen">
</head>
<body>

<div class="row">
    <div class="span3">

        <form action="${pageContext.request.contextPath}/Booking/setSectors.do" method="post">
            <p><select class="selectpicker" size="10" name="eventId" data-size="3">
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


        <form action="${pageContext.request.contextPath}/Booking/setRow.do" method="post">
            <p><select size="10" name="sectorId">
                <c:forEach items="${sectorsMap}" var="sectorEntry">
                    <c:if test="${sector.id==sectorEntry.key.id}">
                        <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                selected>${sectorEntry.key.name} price ${sectorEntry.key.price}
                            free: ${sectorEntry.value}</option>
                    </c:if>
                    <c:if test="${sector.id!=sectorEntry.key.id}">
                        <option value="${sectorEntry.key.id}" onclick="this.form.submit()">${sectorEntry.key.name}
                            price ${sectorEntry.key.price} free: ${sectorEntry.value}</option>
                    </c:if>
                </c:forEach>
            </select></p>
        </form>

    </div>
    <div class="span3">
        <form action="${pageContext.request.contextPath}/Booking/setSeat.do" method="post">
            <p><select size="10" name="row">
                <c:forEach items="${rowsMap}" var="rowEntry">
                    <c:if test="${row==rowEntry.key}">
                        <option value="${rowEntry.key}" onclick="this.form.submit()" selected>${rowEntry.key}
                            free: ${rowEntry.value}</option>
                    </c:if>
                    <c:if test="${row!=rowEntry.key}">
                        <option value="${rowEntry.key}" onclick="this.form.submit()">${rowEntry.key}
                            free: ${rowEntry.value}</option>
                    </c:if>
                </c:forEach>
            </select></p>
        </form>

        <form action="${pageContext.request.contextPath}/Booking/Booking.do" method="post">
            <p><select multiple size="10" name="seats">
                <c:forEach items="${seatsMap}" var="seatEntry">
                    <option value="${seatEntry.key}">${seatEntry.key} free: ${seatEntry.value}</option>
                </c:forEach>
                <input type="submit" name="Order">
            </select></p>
        </form>

</body>
</html>
