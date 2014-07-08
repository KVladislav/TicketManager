<%@include file="header.jsp" %>
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
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form class="form-search" action="${pageContext.request.contextPath}/Booking/ProceedClientName.do"
                  method="post">
                <div class="input">
                    <input class="span8" id="appendedInputButtons" size="16" type="text" name="clientName" required
                           placeholder="Введите ФИО клиента" value="${client.name}">
                    <button class="btn" type="submit" name="action" value="FindClient">Найти</button>
                    <button class="btn" type="submit" name="action" value="NewClient">Новая бронь</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <c:if test="${bookingClients!=null}">
                <table class="table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th>Клиент</th>
                        <th>Дата заказа</th>
                        <th>Билеты</th>
                        <th>Цена</th>
                    </tr>
                    </thead>

                    <tbody>

                    <form name="selectClient" action="${pageContext.request.contextPath}/Booking/ViewClient.do"
                          method="post">
                        <c:forEach items="${bookingClients}" var="clientEntry">
                            <tr>
                                <td>${clientEntry.key.name}</td>
                                <td><fmt:formatDate value="${clientEntry.key.timeStamp}" pattern="d.MM.yyyy H:mm"/></td>
                                <td>${clientEntry.value[0]}</td>
                                <td>${clientEntry.value[1]}</td>
                                <td>
                                    <button class="btn" name="clientId" value="${clientEntry.key.id}" type="submit">Выбрать</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </form>

                    </tbody>
                </table>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>
