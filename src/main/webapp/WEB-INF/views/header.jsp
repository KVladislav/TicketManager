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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="/resources/css/bootstrap.css" rel="stylesheet" media="screen">
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">TicketManager</a>
        <ul class="nav">
            <c:choose>
                <c:when test="${pageName==1}"><li class="active"><a href="Order.do">Продажа</a></li></c:when>
                <c:otherwise><li><a href="Order.do">Продажа</a></li></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pageName==2}"><li class="active"><a href="Booking.do">Бронирование</a></li></c:when>
                <c:otherwise><li><a href="Booking.do">Бронирование</a></li></c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${pageName==3}"><li class="active"><a href="Refund.do">Возврат</a></li></c:when>
                <c:otherwise><li><a href="Refund.do">Возврат</a></li></c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${pageName==4}"><li class="active"><a href="Events.do">Мероприятия</a></li></c:when>
                <c:otherwise><li><a href="Events.do">Мероприятия</a></li></c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${pageName==5}"><li class="active"><a href="Operators.do">Операторы</a></li> </c:when>
                <c:otherwise><li><a href="Operators.do">Операторы</a></li></c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
