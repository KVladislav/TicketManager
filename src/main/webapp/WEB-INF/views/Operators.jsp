<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        function confirmDelete(operator) {
            return confirm("Вы действительно хотите удалить оператора " + operator + "?")
        }
    </script>
</head>

<br><br>
<div class="panel-heading" style="text-align:center; "><b>
    <a href="/NewOperator/NewOperator.do" role="button" class="btn btn-success btn-lg" data-toggle="modal">Добавить нового оператора</a>
</b></div>

<br>
<h4><div class="panel-heading" style="text-align:center; "><b>Список операторов</b></div></h4>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%;margin-bottom: 50px  ">
    <div class="table responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Логин</th>
                <th>Редактировать</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${operators}" var="operator">
                <tr>
                    <td>${operator.id}</td>
                    <td>${operator.name}</td>
                    <td>${operator.surname}</td>
                    <td>${operator.login}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/EditOperator/OperatorsEditGet.do" method="get">
                            <button type="submit" name="operatorId" value="${operator.id}"
                                    class="btn">Редактировать</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Operators/OperatorsDelete.do" method="post"
                              onSubmit="return confirmDelete('${operator.name} ${operator.surname}')">
                            <button type="submit" name="operatorId" value="${operator.id}" class="btn">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@include file="footer.jsp" %>