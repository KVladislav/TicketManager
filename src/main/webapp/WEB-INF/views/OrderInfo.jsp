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
        <div class="col-md-8 col-md-offset-2 column">
            <table class="table table-hover table-condensed">
                <caption>
                    <c:if test="${orderConfirmation.size()==1}">
                        <h3 class="panel-heading text-info">Билет успешно куплен</h3>
                    </c:if>
                    <c:if test="${orderConfirmation.size()>1}">
                        <h3 class="panel-heading text-info">Билеты успешно куплены</h3>
                    </c:if>
                </caption>
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
                    <c:forEach items="${orderConfirmation}" var="ord">
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
                <h4 style="text-align:center;">Стоимость заказа: ${priceConfirmation} грн.</h4>
                <br>
            </table>
            <div class="control-group" style="margin-bottom: 70px">
                <form name="clear" action="${pageContext.request.contextPath}/OrderInfo/Clear.do"
                      method="get">
                    <h3 style="text-align:center">
                        <button type="submit" name="action" class="btn btn-primary">Назад</button>
                    </h3>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="footer.jsp" %>
</html>
