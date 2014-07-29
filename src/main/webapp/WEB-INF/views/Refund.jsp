<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Возврат билета</title>
    <!-- Bootstrap -->
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>
 </head>

<body>
<div class="Refund">
    <center>
        <form class="form-inline" name="findTicket" action="${pageContext.request.contextPath}/Refund/Find.do"
              method="post">
            <br>
            <h3 class="panel-heading text-info">Поиск по номеру билета</h3><br>
            <div>
                <input class="form-control" type="text" name="ticketId" size=20 maxlength=10 required pattern="^[0-9]+$"
                       title="Целые числа без пробелов" style="text-align:center "
                       required placeholder="Введите ID билета"/>
                <button type="submit" name="action" class="btn btn-primary">Поиск</button>

                <h4 style="text-align:center; color:Red">${errorRefund}</h4>
            </div>
            <br> <br>

            <div class="panel-body" style=" width:60%;">
                <c:if test="${ticket!=null}">
                    <div class="table responsive">
                        <table class="table table-bordered table-bordered">
                            <thead>
                            <th> ID</th>
                            <th>Мероприятие</th>
                            <th>Дата</th>
                            <th>Сектор</th>
                            <th>Ряд</th>
                            <th>Место</th>
                            <th>Цена</th>
                            </thead>
                            <tbody>
                            <td>${ticket.id}</td>
                            <td>${ticket.sector.event.description}</td>
                            <td><fmt:formatDate value="${ticket.sector.event.date}" pattern="dd.MM.yyyy  HH:mm"/></td>
                            <td>${ticket.sector.name}</td>
                            <td>${ticket.row}</td>
                            <td>${ticket.seat}</td>
                            <td>${ticket.sector.price}</td>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </form>
        <table>
            <c:if test="${ticket!=null}">
            <tr>
                <td>
                    <div class="control-group">
                        <div class="col-md-1 column">
                            <form name="cancelTicket" action="${pageContext.request.contextPath}/Refund/Cancel.do"
                                  method="get">
                                <h3 style="text-align:center">
                                    <input type="submit" name="Cancel" class="btn  btn-danger" value="Отмена">
                                </h3>
                            </form>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="col-md-3 column">
                        <h3 style="text-align:center">
                            <button class="btn btn-primary" data-toggle="modal" data-target="#ConfirmRefund">Возврат
                            </button>
                        </h3>
                    </div>
                </td>
                </c:if>
        </table>
        <h4 style="text-align:center; color:Green">${messageRefund}</h4>
    </center>
</div>

<div class="modal" id="ConfirmRefund" aria-labelledby="myModalLabel" aria-hidden="true">
    <center>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                </div>
                <div class="modal-body">
                    <h4 style="text-align:center">Вы действительно хотите вернуть билет с ID = ${ticket.id}?</h4>

                    <div class="row clearfix">
                        <br><br>
                        <center>
                            <table>
                                <td>
                                    <div class="control-group">
                                        <div class="col-md-1 column">
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">Нет
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="col-md-3 column">
                                        <form action="${pageContext.request.contextPath}/Refund/Delete.do"
                                              method="post">
                                            <button type="submit" class="btn btn-primary" value=""> Да</button>
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



