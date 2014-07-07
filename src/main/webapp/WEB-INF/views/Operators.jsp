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

    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" />'></script>
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>


    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#my-select').multiSelect()
        });
    </script>

</head>

&MediumSpace;
&MediumSpace;
<div class="panel-heading" style="text-align:center;"><b>
    <a href="/NewOperator/NewOperator.do" role="button" class="btn btn-success btn-lg" data-toggle="modal">Создать нового оператора</a>
</b></div>

&MediumSpace;
&MediumSpace;
<div class="panel-heading" style="text-align:center;"><b>Список операторов</b></div>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%">
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
                            <input type="hidden" name="operatorId" value="${operator.id}">
                            <button type="submit" name="action" value="edit" class="btn">Редактировать</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Operators/OperatorsDelete.do" method="post">
                            <input type="hidden" name="operatorId" value="${operator.id}">
                            <button type="submit" name="action" value="delete" class="btn">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    &MediumSpace;
    &MediumSpace;

</div>
