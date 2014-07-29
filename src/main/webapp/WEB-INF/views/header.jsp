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
    <link href="${pageContext.request.contextPath}/resources/ico/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/"><img src="${pageContext.request.contextPath}/resources/ico/favicon.ico"/>TicketManager</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${pageName==1}">
                        <li class="active"><a href="${pageContext.request.contextPath}/Order/Order.do">Продажа</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Order/Order.do">Продажа</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==2}">
                        <li class="active"><a href="${pageContext.request.contextPath}/Booking/GetClient.do">Бронирование</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Booking/GetClient.do">Бронирование</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==3}">
                        <li class="active"><a href="${pageContext.request.contextPath}/Refund/Refund.do">Возврат</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Refund/Refund.do">Возврат</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==4}">
                        <li class="active"><a
                                href="${pageContext.request.contextPath}/Events/Events.do">Мероприятия</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Events/Events.do">Мероприятия</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==5}">
                        <li class="active"><a
                                href="${pageContext.request.contextPath}/Operators/Operators.do">Операторы</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Operators/Operators.do">Операторы</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageName==7}">
                        <li class="active"><a href="${pageContext.request.contextPath}/Sectors/ViewSectors.do">Настройка
                            стадиона</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/Sectors/ViewSectors.do">Настройка
                            стадиона</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active">
                    <a href="<c:url value="/j_spring_security_logout" />" title="Выйти"><strong> Вы зашли
                        под ${pageContext.request.userPrincipal.name}</strong></a>
                </li>
            </ul>
        </div>
    </div>
</nav>

</body>
</html>