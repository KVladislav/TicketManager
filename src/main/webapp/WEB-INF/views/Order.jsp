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
            margin: 10px;
        }
    </style>
</head>
<body>

<div class="order">
    <div class="panel-body" style="padding:1px; width:115%; margin-left: 3%">
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
                <p><select multiple size="10" id="select" name="seat" class="form-control" name="select">

                    <c:forEach items="${seatsMap}" var="seat">
                        <c:if test="${seat.value=='Статус: выкуплен'||seat.value=='Статус: забронирован'}">
                            <option value="${seat.key}" style="color:Red">${seat.key}.
                                    ${seat.value}</option>
                        </c:if>
                        <c:if test="${seat.value=='Статус: не утверждён'}">
                            <option value="${seat.key}" style="color:Blue">${seat.key}.
                                    ${seat.value}</option>
                        </c:if>
                        <c:if test="${seat.value=='Статус:  в продаже'}">
                             <option value="${seat.key}"onclick="this.form.submit()" style="color:Green">${seat.key}.
                                ${seat.value}</option>
                        </c:if>
                    </c:forEach>
                </select><p>
            </form>
        </div>

       <div class="col-md-5 col-lg-offset-0 ">
           <h4 style="text-align:center; color:Blue">Выбранные билеты</h4>
            <table class="table text-center table-bordered">
               <thead>
               <form name = "delOrder" action="${pageContext.request.contextPath}/Order/addTicket.do" method="post">
                   <th>Мероприятие</th>
                   <th>Дата</th>
                   <th>Сектор</th>
                   <th>Ряд</th>
                   <th>Место</th>
                   <th>Цена</th>
                   <th>Delete</th>
               </form>
               </thead>
               <tbody>
               <c:forEach items="${orderList}" var="ord">
                   <tr>
                       <form name = "delTicket" action="${pageContext.request.contextPath}/Order/delTicket.do" method="post">
                       <td>${ord.sector.event.description}</td>
                       <td> <fmt:formatDate value="${ord.sector.event.date}" pattern="d.MM.yy H:mm"/></td>
                       <td>${ord.sector.name}</td>
                       <td>${ord.row}</td>
                       <td>${ord.seat}</td>
                       <td>${ord.sector.price}</td>
                           <td>
                               <input type="hidden" name="ticketId" value="${ord.id}">
                               <button class="btn btn-default btn-xs" onclick="document.delTicket.submit();"><span class="glyphicon glyphicon-trash"></span></button>
                           </td>
                       </form>
                   </tr>
                </c:forEach>
                </tbody>
           </table>
       </div>

      </div>
    </div>

    <div class="col-md-7 col-lg-offset-4 ">
    <tr>
        <td>
            <h4 style="text-align:center; color:Blue">Стоимость заказа: ${orderPrice} грн.</h4>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/Order/Buy.do" method="post">
                <h4 style="text-align:center">
                    <input type="submit" name="Order" class="btn btn-primary btn-lg" value="Купить">
                </h4>
            </form>
        </td>
     </tr>
    </div>
</div>
</body>
</html>
