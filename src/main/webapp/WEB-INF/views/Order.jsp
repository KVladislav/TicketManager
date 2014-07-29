<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Продажа билетов</title>
    <!-- Bootstrap -->
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>
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
            <strong class="text-info">Выбор мероприятия</strong>
            <form action="${pageContext.request.contextPath}/Order/setSectors.do" method="post">
                <p><select size="8" name="eventId" data-size="3" class="form-control">
                    <c:forEach items="${eventsOrder}" var="evnt">
                        <c:if test="${eventOrder.id==evnt.id}">
                            <option value="${evnt.id}" onclick="this.form.submit()"
                                   selected> ${evnt.description},
                                   <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                        </c:if>
                        <c:if test="${eventOrder.id!=evnt.id}">
                            <option value="${evnt.id}"
                                   onclick="this.form.submit()">${evnt.description},
                                   <fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm"/></option>
                        </c:if>
                    </c:forEach>
                </select></p>
            </form>
        </div>
        <div class="col-md-4 column">
            <strong class="text-info">Выбор сектора</strong>
            <form action="${pageContext.request.contextPath}/Order/setRow.do" method="post">
                <p><select size="8" name="sectorId" class="form-control">
                    <c:forEach items="${sectorsMapOrder}" var="sectorEntry">
                        <c:if test="${sectorOrder.id==sectorEntry.key.id}">
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
                        <c:if test="${sectorOrder.id!=sectorEntry.key.id}">
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
            <strong class="text-info">Сектора по возрастанию цены</strong>
            <form action="${pageContext.request.contextPath}/Order/Order.do" method="post">
                <select multiple size="8" name="Legend" class="form-control">
                    <c:forEach items="${legendaOrder}" var="leg">
                        <option value="${leg}"> ${leg}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
   </div>
   <div class="row">
        <div class="col-md-3">
             <strong class="text-info">Выбор ряда</strong>
             <form action="${pageContext.request.contextPath}/Order/setSeat.do" method="post">
                 <p><select size="12" name="row" class="form-control">
                     <c:forEach items="${rowsMapOrder}" var="rowEntry">
                         <c:if test="${rowOrder==rowEntry.key}">
                             <c:if test="${rowEntry.value==0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" style="color:Red"
                                         selected >${rowEntry.key} ряд.  В продаже мест нет</option>
                             </c:if>
                             <c:if test="${rowEntry.value!=0}">
                                 <option value="${rowEntry.key} "onclick="this.form.submit()" selected>${rowEntry.key}
                                         ряд. В продаже ${rowEntry.value} мест</option>
                             </c:if>
                         </c:if>
                         <c:if test="${rowOrder!=rowEntry.key}">
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
             <strong class="text-info">Выбор места</strong>
             <form action="${pageContext.request.contextPath}/Order/addTicket.do" method="post">
                 <p><select multiple size="12" name="seat"  class="form-control">
                     <c:forEach items="${seatsMapOrder}" var="seatEntry">
                         <c:if test="${seatOrder==seatEntry.key}">
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
                                 <option value="${seatEntry.key}" ondblclick="this.form.submit()"
                                         style="color:Green" selected >${seatEntry.key}. (в продаже)</option>
                             </c:if>
                         </c:if>
                         <c:if test="${seatOrder!=seatEntry.key}">
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
                                  <option value="${seatEntry.key}" ondblclick="this.form.submit()"
                                          style="color:Green">${seatEntry.key}. (в продаже)</option>
                             </c:if>
                         </c:if>
                     </c:forEach>
                 </select><p>
             </form>
        </div>
        <div class="col-md-7 col-lg-offset-0 ">
            <h3 class="panel-heading text-info" style="text-align: center">Выбранные билеты</h3>
            <h5 style="text-align:center; color:Green">(Выбранный, но не купленный билет через
                                                        10 мин. возвратится в продажу)</h5>
            <table class="table text-center table-bordered">
                <thead>
                    <th>ID</th>
                    <th>Мероприятие</th>
                    <th>Дата</th>
                    <th>Сектор</th>
                    <th>Ряд</th>
                    <th>Место</th>
                    <th>Время</th>
                    <th>Цена</th>
                    <th></th>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="ord">
                     <tr>
                     <td>${ord.id}</td>
                     <td>${ord.sector.event.description}</td>
                     <td><fmt:formatDate value="${ord.sector.event.date}" pattern="d.MM.yy H:mm"/></td>
                     <td>${ord.sector.name}</td>
                     <td>${ord.row}</td>
                     <td>${ord.seat}</td>
                     <td><fmt:formatDate value="${ord.timeStamp}" pattern="HH:mm"/></td>
                     <td>${ord.sector.price}</td>
                     <td>
                         <form name = "delTicket" action="${pageContext.request.contextPath}/Order/delTicket.do"
                               method="post">
                                 <button type="submit" name="orderId" class="btn btn-default btn-xs" value="${ord.id}">
                                 <span class="glyphicon glyphicon-trash" ></span></button>
                          </form>
                     </td>
                     </tr>
                </c:forEach>
                </tbody>
           </table>
        </div>
        <div class="col-md-7 col-lg-offset-5 ">
            <center>
                <h5 style="text-align:center; color:Green">${messageOrder}</h5>
                <h5 style="text-align:center; color:Red">${errorOrder}</h5>
                <c:if test="${orderList.size()>0}">
                     <h4 style="text-align:center">Стоимость заказа: ${orderPrice.floatValue()} грн.</h4>
                    <tr>
                    <td>
                    <div class="control-group">
                        <div class="col-md-3  col-lg-offset-3">
                            <h3 style="text-align:center">
                                <button class="btn  btn-danger" data-toggle="modal"
                                        data-target="#ConfirmOrderCancel">Отмена</button>
                            </h3>
                        </div>
                    </div>
                    </td>
                    <td>
                        <div class="col-md-3 column" style="margin-bottom: 50px">
                            <h3 style="text-align:center">
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#ConfirmOrderBuy">Купить</button>
                            </h3>
                        </div>
                    </td>
                </c:if>
               </center>
        </div>
   </div>
</div>
</div>

<div class="modal" id="ConfirmOrderBuy"  style="margin-bottom: 50px;" aria-labelledby="myModalLabel"  aria-hidden="true">
    <center>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                </div>
                <div class="modal-body">
                    <h4 style="text-align:center">Вы действительно хотите сделать заказ?</h4>
                    <br><br>
                    <table class="table text-center table-bordered">
                        <thead>
                        <th>ID</th>
                        <th>Мероприятие</th>
                        <th>Дата</th>
                        <th>Сектор</th>
                        <th>Ряд</th>
                        <th>Место</th>
                        <th>Цена</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${orderList}" var="ord">
                            <tr>
                                <td>${ord.id}</td>
                                <td>${ord.sector.event.description}</td>
                                <td><fmt:formatDate value="${ord.sector.event.date}" pattern="d.MM.yy H:mm"/></td>
                                <td>${ord.sector.name}</td>
                                <td>${ord.row}</td>
                                <td>${ord.seat}</td>
                                <td>${ord.sector.price}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <h4 style="text-align:center;">Стоимость заказа: ${orderPrice} грн.</h4>
                    <br><br>
                    <div class="row clearfix">
                        <center>
                            <table>
                                <td>
                                    <div class="control-group">
                                        <div class="col-md-1 column">
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">Нет</button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="col-md-3 column">
                                        <form action="${pageContext.request.contextPath}/Order/Buy.do" method="post">
                                            <button type="submit" class="btn btn-primary" value=""> Да </button>
                                        </form>
                                    </div>
                                </td>
                            </table>
                        </center>
                    </div>
                </div>
            </div>
        </div>
    </center>
</div>

<div class="modal" id="ConfirmOrderCancel"  aria-labelledby="myModalLabel" aria-hidden="true">
    <center>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                </div>
                <div class="modal-body">
                    <h4 style="text-align:center">Вы действительно хотите удалить заказ?</h4>
                    <div class="row clearfix">
                        <br><br>
                        <center>
                            <table>
                                <td>
                                    <div class="control-group">
                                        <div class="col-md-1 column">
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">Нет</button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="col-md-3 column">
                                        <form action="${pageContext.request.contextPath}/Order/Cancel.do" method="get">
                                            <button type="submit" class="btn btn-primary" value=""> Да </button>
                                        </form>
                                    </div>
                                </td>
                            </table>
                        </center>
                    </div>
                </div>
            </div>
        </div>
    </center>
</div>

</body>
<%@include file="footer.jsp" %>
</html>
