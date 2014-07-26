<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title></title>
</head>
<body>
<form:form action="${pageContext.request.contextPath}/NewSector/addSector.do" method="post">
    <h4>Задайте параметры нового сектора:</h4>
    <tr>
    <td>
    <div class="control-group ">
    <label class="my-control-label" for="sectorName">Название сектора
    </label>
    <div class="my-controls">
    <input type="text" id="sectorName" name="sectorName" required pattern=[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}" title="Не пустое, не начинатся с пробела, до 10 знаков">
    </div>
    </div>

    </td>
    <td>
    <div class="control-group ">
    <label class="my-control-label" for="maxRows">Максимальное количество рядов
    </label>
    <div class="my-controls">
    <input type="text" id="maxRows" name="maxRows" required pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
    </div>
    </div>

    </td>
    <td>
    <div class="control-group ">
    <label class="my-control-label" for="maxSeats">Максимальное количество мест
    </label>
    <div class="my-controls">
    <input type="text" id="maxSeats" name="maxSeats" required pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
    </div>
    </div>

    </td>
    <td>
    <div class="control-group ">
    <label class="my-control-label" for="newPrice">Цена
    </label>
    <div class="my-controls">
    <input type="text" id="newPrice" name="newPrice" required pattern="\d{0,5}(\.\d{0,2}){0,1}" title="В интервале [0-99999] до двух знаков после запятой">
    </div>
    </div>

    </td>
    </tr>

    <button type="submit" name="action" value="save" class="btn btn-primary">Сохранить</button>

    </form>
    </body>
    </html>
