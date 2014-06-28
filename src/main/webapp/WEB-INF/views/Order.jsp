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
    <style type="text/css">
        .bs-example {
            margin: 20px;
        }
    </style>
</head>
<body>

<div class="order">
    <div class="panel-body" style="padding:1px; width:110%; margin-left: 5%">
    <div class="row">
        <div class="col-md-3">
            <h4 style="text-align:center; color:Blue">Mероприятие</h4>
            <form action="${pageContext.request.contextPath}/Order/setSectors.do" method="post">
                <p><select size="10" name="eventId" data-size="3" class="form-control">
                    <c:forEach items="${events}" var="evnt">
                        <c:if test="${event.id==evnt.id}">
                            <option value="${evnt.id}" onclick="this.form.submit()"
                                   selected> ${evnt.description},
                                   <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                        </c:if>
                        <c:if test="${event.id!=evnt.id}">
                            <option value="${evnt.id}"
                                   onclick="this.form.submit()">${evnt.description},
                                   <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                        </c:if>
                    </c:forEach>
                </select></p>
            </form>
        </div>

        <div class="col-md-3">
            <h4 style="text-align:center; color:Blue">Сектор</h4>
            <form action="${pageContext.request.contextPath}/Order/setRow.do" method="post">
                <p><select size="10" name="sectorId" class="form-control">
                    <c:forEach items="${sectorsMap}" var="sectorEntry">
                        <c:if test="${sector.id==sectorEntry.key.id}">
                            <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                    selected>${sectorEntry.key.name} Сектор ${sectorEntry.key.price}
                                грн. Свободно ${sectorEntry.value} мест</option>
                        </c:if>
                        <c:if test="${sector.id!=sectorEntry.key.id}">
                            <option value="${sectorEntry.key.id}" onclick="this.form.submit()">${sectorEntry.key.name}
                             Сектор ${sectorEntry.key.price} грн. Свободно ${sectorEntry.value} мест</option>
                        </c:if>
                    </c:forEach>
                </select></p>
            </form>
        </div>

        <div class="col-md-3">
            <h4 style="text-align:center; color:Blue">Легенда</h4>
            <form action="${pageContext.request.contextPath}/Order/Order.do" method="post">
                <p><select multiple size="10" name="Legend" class="form-control">
                    <c:forEach items="${legenda}" var="leg">
                        <option value="${leg}"> ${leg}</option>
                    </c:forEach>
                </select></p>
            </form>
        </div>
   </div>

   <div class="row">
        <div class="col-md-2">
            <h4 style="text-align:center; color:Blue">Ряд</h4>
            <form action="${pageContext.request.contextPath}/Order/setSeat.do" method="post">
                <p><select size="10" name="row" class="form-control">
                    <c:forEach items="${rowsMap}" var="rowEntry">
                        <c:if test="${row==rowEntry.key}">
                            <option value="${rowEntry.key}" onclick="this.form.submit()" selected>${rowEntry.key}
                                 ряд. Свободно ${rowEntry.value} мест</option>
                        </c:if>
                        <c:if test="${row!=rowEntry.key}">
                            <option value="${rowEntry.key}" onclick="this.form.submit()">${rowEntry.key}
                                ряд. Свободно ${rowEntry.value} мест</option>
                        </c:if>
                    </c:forEach>
                </select></p>
            </form>
        </div>
        <div class="col-md-2">
            <h4 style="text-align:center; color:Blue">Место</h4>
            <form action="${pageContext.request.contextPath}/Order/addTicket.do" method="post">
                <p><select multiple size="10" name="place" class="form-control">
                    <c:forEach items="${seatsMap}" var="seat">
                        <c:if test="${seat.value=='Статус: выкуплен'||seat.value=='Статус: забронирован'}">
                            <option value="${seat.key}" style="color:Red">${seat.key}.
                                    ${seat.value}</option>
                        </c:if>
                        <c:if test="${seat.value=='Статус:  в продаже'}">
                             <option value="${seat.key}"onclick="this.form.submit()">${seat.key}.
                                ${seat.value}</option>
                        </c:if>
                    </c:forEach>
                </select><p>
            </form>
        </div>

       <div class="col-md-3">
           <h4 style="text-align:center; color:Blue">Выбранные билеты</h4>
            <table class="table table-bordered">
               <thead>
                   <th>Мероприятие</th>
                   <th>Сектор</th>
                   <th>Ряд</th>
                   <th>Место</th>
                   <th>Цена</th>
               </thead>
               <tbody>


               </tbody>
           </table>
       </div>


    </div>
    <br>


    </div>
</div>
</body>
</html>
