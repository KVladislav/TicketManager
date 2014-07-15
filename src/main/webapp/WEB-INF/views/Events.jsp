<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html lang="ru">
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
    <a href="/AddEditEvent/NewEvent.do" role="button" class="btn btn-success btn-lg" data-toggle="modal">Создание нового
        мероприятия</a>
</b></div>

&MediumSpace;
&MediumSpace;
<h1><div class="panel-heading" style="text-align:center;"><b>Список мероприятий</b></div></h1>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%">
    <div class="table responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>##</th>
                <th>Наименование</th>
                <th>Дата</th>
                <th>Редактировать</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${events}" var="evnt" varStatus="theCount">
                <tr>
                    <td>${theCount.count}</td>
                    <td>${evnt.description}</td>
                    <td><fmt:formatDate value="${evnt.date}" pattern="dd.MM.yyyy H:mm:ss"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/Edit.do" method="post">
                            <input type="hidden" name="eventId+${theCount.count}+1" value="${event.id}">
                            <button type="submit" name="evnt" value="${evnt.id}" class="btn">Редактировать</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/setDelete.do" method="post">
                            <input type="hidden" name="eventId+${theCount.count}+1" value="${event.id}">
                            <button type="submit" name="evnt" value="${evnt.id}" class="btn">Удалить</button>
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
