<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">

<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 26.07.2014
  Time: 5:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание нового сектора</title>
</head>
<body>

<center>
    <caption><h1 class="panel-heading text-info" style="text-align:center;"><b>Создание нового сектора</b></h1>
    </caption>
    <caption><h3 class="panel-heading  text-info" style="text-align:center;"><b>Задайте параметры нового
        сектора:</b></h3></caption>
    <div class="container" style="text-align:center;">
        <div class="row clearfix">


            <div class="col-md-3 column">
            </div>
            <div class="col-md-4 column">
                <form action="${pageContext.request.contextPath}/NewSector/addSector.do" method="post">
                    <table class="table table-hover" style="text-align:center;">

                    <thead>
                        <tr>
                            <th width="30%">Название сектора</th>
                            <th width="20%">Рядов</th>
                            <th width="20%">Мест</th>
                            <th width="30%">Цена</th>

                        </tr>
                        </thead>
                        <tbody>
                        <input type="hidden" name="dateEvent"
                               value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
                        <input type="hidden" name="eventTime" value="${eventTime}">
                        <input type="hidden" name="eventDescriptions" value="${eventDescriptions}">
                        <input type="hidden" name="eventBookingTimeOut" value="${eventBookingTimeOut}">
                        <input type="hidden" name="allSectors" value="${allSectors}">

                        <tr>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="10" type="text"
                                       name="sectorName" required
                                       placeholder="Название сектора" value=""
                                       pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}"
                                       title="Не пустое, не начинатся с пробела, до 10 знаков">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="2" name="maxRows"
                                       required
                                       placeholder="Рядов" value=""
                                       pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="2" name="maxSeats"
                                       required
                                       placeholder="Мест" value=""
                                       pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" type="text" maxlength="8"
                                       name="newPrice"
                                       placeholder="Цена" value=""
                                       required pattern="\d{0,5}(\.\d{0,2}){0,1}"
                                       title="В интервале [0-99999] до двух знаков после запятой">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <button type="submit" name="action" value="save" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
            <div class="col-md-2 column"></div>
        </div>
    </div>
</center>
</body>
</html>
