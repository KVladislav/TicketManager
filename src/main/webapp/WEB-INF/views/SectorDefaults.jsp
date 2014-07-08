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
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>

<table class="table table-hover table-condensed">
    <caption>
        <a href="#" class="btn btn-large btn-primary disabled">Настройка секторов стадиона
    </caption>
    <thead>
    <tr>
        <th colspan="2">Название</th>
        <th>Кол-во рядов</th>
        <th>Мест в ряду</th>
        <th>Цена</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${sectorDefaultsList}" var="sector">
        <form name="sectorDefaults" action="${pageContext.request.contextPath}/Sectors/Modify.do"
              method="post">
            <tr>
                <td colspan="2">
                    <input class="span1" size="16" type="text" name="sectorName" required
                           placeholder="Название сектора" value="${sector.sectorName}">
                </td>
                <td>
                    <input class="span1" size="16" type="text" name="maxRows" required
                           placeholder="Рядов" value="${sector.maxRows}">
                </td>
                <td>
                    <input class="span1" size="16" type="text" name="maxSeats" required
                           placeholder="Мест" value="${sector.maxSeats}">
                </td>
                <td>
                    <input class="span1" size="16" type="text" name="defaultPrice" required
                           placeholder="Цена" value="${sector.defaultPrice}">
                <td>
                    <input type="hidden" name="sectorDefaultsId" value="${sector.id}">
                    <button type="submit" name="action" value="save" class="btn btn-default btn-xs">
                        <span class="glyphicon glyphicon-floppy-disk"></span></button>
                </td>
                <td>
                    <button type="submit" name="action" value="clone" class="btn btn-default btn-xs">
                        <span class="glyphicon glyphicon-export"></span></button>
                </td>
                <td>
                    <button type="submit" name="action" value="delete" class="btn btn-default btn-xs">
                        <span class="glyphicon glyphicon-trash"></span></button>
                </td>
            </tr>
        </form>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
