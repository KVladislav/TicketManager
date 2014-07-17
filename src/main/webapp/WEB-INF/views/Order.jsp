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
            margin: 0;
        }
    </style>
</head>
<body>

<div class="order">
<div class="panel-body" style="padding:0px; width:92%; margin-left: 4%">
   <div class="row">
        <div class="col-md-4 column">
            <h4 style="text-align:center; color:Blue">Выбор мероприятия</h4>
            <form action="${pageContext.request.contextPath}/Order/setSectors.do" method="post">
                <p><select size="8" name="eventId" data-size="3" class="form-control">
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
        <div class="col-md-4 column">
            <h4 style="text-align:center; color:Blue">Выбор сектора</h4>
            <form action="${pageContext.request.contextPath}/Order/setRow.do" method="post">
                <p><select size="8" name="sectorId" class="form-control">
                    <c:forEach items="${sectorsMap}" var="sectorEntry">
                        <c:if test="${sector.id==sectorEntry.key.id}">
                             <c:if test="${sectorEntry.value==0}">
                                 <option value="${sectorEntry.key.id}" onclick="this.form.submit()"style="color:Red"
                                         selected>${sectorEntry.key.name} Сектор ${sectorEntry.key.price}
                                         грн. Свободных мест нет</option>
                             </c:if>
                             <c:if test="${sectorEntry.value!=0}">
                                 <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                         selected>${sectorEntry.key.name} Сектор ${sectorEntry.key.price}
                                         грн. Свободно ${sectorEntry.value} мест</option>
                             </c:if>
                        </c:if>
                        <c:if test="${sector.id!=sectorEntry.key.id}">
                            <c:if test="${sectorEntry.value==0}">
                                <option value="${sectorEntry.key.id}" onclick="this.form.submit()"style="color:Red"
                                        >${sectorEntry.key.name} Сектор ${sectorEntry.key.price}
                                        грн. Свободных мест нет</option>
                            </c:if>
                            <c:if test="${sectorEntry.value!=0}">
                                <option value="${sectorEntry.key.id}" onclick="this.form.submit()"
                                       >${sectorEntry.key.name} Сектор ${sectorEntry.key.price}
                                       грн. Свободно ${sectorEntry.value} мест</option>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </select></p>
            </form>
        </div>
        <div class="col-md-4 column">
            <h4 style="text-align:center; color:Blue">Сектора по возрастанию цены</h4>
            <form action="${pageContext.request.contextPath}/Order/Order.do" method="post">
                <select multiple size="8" name="Legend" class="form-control">
                    <c:forEach items="${legenda}" var="leg">
                        <option value="${leg}"> ${leg}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
   </div>
   <div class="row">
        <div class="col-md-3">
             <h4 style="text-align:center; color:Blue">Выбор ряда</h4>
             <form action="${pageContext.request.contextPath}/Order/setSeat.do" method="post">
                 <p><select size="12" name="row" class="form-control">
                     <c:forEach items="${rowsMap}" var="rowEntry">
                         <c:if test="${row==rowEntry.key}">
                             <c:if test="${rowEntry.value==0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" style="color:Red"
                                         selected >${rowEntry.key} ряд.  В продаже мест нет</option>
                             </c:if>
                             <c:if test="${rowEntry.value!=0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" selected>${rowEntry.key}
                                         ряд. В продаже ${rowEntry.value} мест</option>
                             </c:if>
                         </c:if>
                         <c:if test="${row!=rowEntry.key}">
                             <c:if test="${rowEntry.value==0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" style="color:Red">
                                         ${rowEntry.key} ряд. В продаже мест нет</option>
                             </c:if>
                             <c:if test="${rowEntry.value!=0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" >${rowEntry.key}
                                         ряд. В продаже ${rowEntry.value} мест</option>
                             </c:if>
                         </c:if>
                     </c:forEach>
                 </select></p>
             </form>
        </div>
        <div class="col-md-2">
             <h4 style="text-align:center; color:Blue">Выбор места</h4>
             <form action="${pageContext.request.contextPath}/Order/addTicket.do" method="post">
                 <p><select multiple size="12" id="select" name="seat" class="form-control" name="select">
                     <c:forEach items="${seatsMap}" var="seatEntry">
                         <c:if test="${seat==seatEntry.key}">
                             <c:if test="${seatEntry.value==3}">
                                 <option value="${seatEntry.key}" style="color:Red" selected >
                                         ${seatEntry.key}. (продан)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==2}">
                                 <option value="${seatEntry.key}" style="color:Red" selected >
                                         ${seatEntry.key}. (забронирован)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==1}">
                                 <option value="${seatEntry.key}" style="color:Blue" selected >${seatEntry.key}.
                                         (не утверждён)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==0}">
                                 <option value="${seatEntry.key} "onclick="this.form.submit()" style="color:Green"
                                         selected >${seatEntry.key}. (в продаже)</option>
                             </c:if>
                         </c:if>
                         <c:if test="${seat!=seatEntry.key}">
                             <c:if test="${seatEntry.value==3}">
                                 <option value="${seatEntry.key}" style="color:Red">
                                         ${seatEntry.key}. (продан)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==2}">
                                 <option value="${seatEntry.key}" style="color:Red">
                                         ${seatEntry.key}. (забронирован)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==1}">
                                 <option value="${seatEntry.key}" style="color:Blue">
                                         ${seatEntry.key}. (не утверждён)</option>
                             </c:if>
                             <c:if test="${seatEntry.value==0}">
                                  <option value="${seatEntry.key} "onclick="this.form.submit()"
                                          style="color:Green">${seatEntry.key}. (в продаже)</option>
                             </c:if>
                         </c:if>
                     </c:forEach>
                 </select><p>
             </form>
        </div>
        <div class="col-md-7 col-lg-offset-0 ">
            <h4 style="text-align:center; color:Blue">Выбранные билеты</h4>
            <table class="table text-center table-bordered">
                <thead>
                    <th>ID</th>
                    <th>Мероприятие</th>
                    <th>Дата</th>
                    <th>Сектор</th>
                    <th>Ряд</th>
                    <th>Место</th>
                    <th>Цена</th>
                    <th>Отмена</th>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="ord">
                     <tr>
                     <form name = "delTicket" action="${pageContext.request.contextPath}/Order/delTicket.do"
                           method="post">
                     <td>${ord.id}</td>
                     <td>${ord.sector.event.description}</td>
                     <td> <fmt:formatDate value="${ord.sector.event.date}" pattern="d.MM.yy H:mm"/></td>
                     <td>${ord.sector.name}</td>
                     <td>${ord.row}</td>
                     <td>${ord.seat}</td>
                     <td>${ord.sector.price}</td>
                          <td>
                               <input type="hidden" name="orderId" value="${ord.id}">
                               <button class="btn btn-default btn-xs" onclick="document.delTicket.submit();">
                                   <span class="glyphicon glyphicon-trash" ></span></button>
                          </td>
                     </form>
                      </tr>
                </c:forEach>
                </tbody>
           </table>
        </div>
        <div class="col-md-7 col-lg-offset-5 ">
            <form action="${pageContext.request.contextPath}/Order/Buy.do" method="post">
                <h5 style="text-align:center; color:Green">${message}</h5>
                <h5 style="text-align:center; color:Red">${error}</h5>
                <h4 style="text-align:center; color:Red">Стоимость заказа: ${orderPrice} грн.</h4>
                <h4 style="text-align:center"><input type="submit" name="Order"
                           class="btn btn-primary btn-lg" value="Купить"></h4>
            </form>
        </div>
   </div>
</div>
</div>
</body>
</html>
