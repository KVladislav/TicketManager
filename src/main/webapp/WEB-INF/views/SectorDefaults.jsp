<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="ru">
<head>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-3 column"></div>
        <div class="col-md-6 column">
            <table class="table table-hover table-condensed">
                <caption>
                    <h1><code>Настройка секторов стадиона</code>
                    </h1></caption>
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
                              method="post" role="form">
                            <tr>
                                <td colspan="2">
                                    <input class="span2" size="16" maxlength="10" type="text" name="sectorName" required
                                           placeholder="Название сектора" value="${sector.sectorName}"
                                           pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}"
                                           title="Не пустое, не начинатся с пробела, до 10 знаков">
                                </td>
                                <td>
                                    <input class="span1" size="16"  name="maxRows" required
                                           placeholder="Рядов" value="${sector.maxRows}"
                                           pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                                </td>
                                <td>
                                    <input class="span1" size="16" name="maxSeats" required
                                           placeholder="Мест" value="${sector.maxSeats}"
                                           pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                                </td>
                                <td>
                                    <input class="span2" size="16" type="text" maxlength="8" name="defaultPrice" required
                                           placeholder="Цена" value="${sector.defaultPrice}"
                                           required pattern="[1-9][0-9]{0,4}\.{0,1}[0-9]{0,2}"
                                           title="В интервале [1-99999] до двух знаков после запятой">
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
        </div>
    </div>
    <div class="col-md-3 column"></div>
</div>
</body>
</html>
