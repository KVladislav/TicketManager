<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 22.06.2014
  Time: 23:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="navbar">
            <ul class="nav navbar-nav navbar-brand">
                <c:choose>
                    <c:when test="${pageName==0}"><li class="active"><a href="/">TicketManager</a></li></c:when>
                    <c:otherwise><li><a href="/">TicketManager</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==1}"><li class="active"><a href="${pageContext.request.contextPath}/Order/Order.do">Продажа</a></li></c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Order/Order.do">Продажа</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==2}"><li class="active"><a href="${pageContext.request.contextPath}/Booking/GetClient.do">Бронирование</a></li></c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Booking/GetClient.do">Бронирование</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==3}"><li class="active"><a href="${pageContext.request.contextPath}/Refund/Refund.do">Возврат</a></li></c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Refund/Refund.do">Возврат</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==4}"><li class="active"><a href="${pageContext.request.contextPath}/Events/Events.do">Мероприятия</a></li></c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Events/Events.do">Мероприятия</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==5}"><li class="active"><a href="${pageContext.request.contextPath}/Operators/Operators.do">Операторы</a></li> </c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Operators/Operators.do">Операторы</a></li></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==7}"><li class="active"><a href="${pageContext.request.contextPath}/Sectors/ViewSectors.do">Настройка стадиона</a></li> </c:when>
                    <c:otherwise><li><a href="${pageContext.request.contextPath}/Sectors/ViewSectors.do">Настройка стадиона</a></li></c:otherwise>
                </c:choose>
            </ul>
    </div>
</body>
</html>