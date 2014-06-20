<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 19.06.2014
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booking</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/Booking/setSectors" method="post" >
    <p><select multiple size="10" name="eventId">
        <c:forEach items="${events}" var="event">
            <option value="${event.id}" onclick="this.form.submit()">${event.description} ${event.date.day}.${event.date.month} ${event.date.hours}:${event.date.minutes}</option>
        </c:forEach>
    </select></p>
</form>

<form action="${pageContext.request.contextPath}/Booking/setRow" method="post" >
    <p><select multiple size="10" name="SectworId">
        <c:forEach items="${sectorsMap}" var="sectorEntry">
            <option value="${sectorEntry.key}" onclick="this.form.submit()">${sectorEntry.key.name} price ${sectorEntry.key.price} free: ${sectorEntry.value}</option>
        </c:forEach>
    </select></p>
</form>

</body>
</html>
