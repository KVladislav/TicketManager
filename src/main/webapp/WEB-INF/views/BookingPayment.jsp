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
            <form class="form-search" action="${pageContext.request.contextPath}/Booking/GetClients.do" method="post"
                  name="SearchClient">
                <div class="input-append">
                    <input type="text" name="searchWord" class="span8 search-query">
                    <button type="submit" class="btn" onclick="document.SearchClient.submit()">Найти</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-condensed">
                <thead>
                <tr>
                    <th>Клиент</th>
                    <th>Билеты</th>
                    <th>Мероприятия</th>
                    <th>Цена</th>
                </tr>
                </thead>

                <tbody>

                <form name="selectClient" action="${pageContext.request.contextPath}/Booking/ViewClient.do"
                      method="post">
                    <c:forEach items="${clients}" var="clientEntry">
                        <tr>
                            <td>${clientEntry.name}</td>
                            <td>${clientEntry.value[0]}</td>
                            <td>${clientEntry.value[1]}</td>
                            <td>${clientEntry.value[2]}</td>
                            <td>
                                <input type="hidden" name="clientId" value="${client.id}">
                                <button class="btn btn-default btn-xs" onclick="document.selectClient.submit();"><span
                                        class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </tr>
                    </c:forEach>
                </form>

                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
