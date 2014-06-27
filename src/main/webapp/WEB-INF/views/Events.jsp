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

<div class="panel-heading" style="text-align:center;"><b>List of events</b></div>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%">
    <div class="table responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>##</th>
                <th>Name of event</th>
                <th>Date</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${events}" var="evnt" varStatus="theCount">
                <tr>
                    <td>${theCount.count}</td>
                    <td>${evnt.description}</td>
                    <td><fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm:ss"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/Edit.do" method="post">
                            <input type="hidden" name="eventId" value="${event.id}">
                            <button type="submit" name="action" value="edit" class="btn">Edit</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/setDelete.do" method="post">
                            <input type="hidden" name="eventId" value="${event.id}">
                            <button type="submit" name="evnt" value="${evnt.id}" class="btn">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    &MediumSpace;
    &MediumSpace;
    <div class="container">
        <div class="row">

            <div style="text-align: center;">
                <div class="col-md-8 col-md-offset-3">
                    <%--     <form action="${pageContext.request.contextPath}/Events/Redirect.do" method="post"> --%>
                         <a href="/NewEvent/NewEvent.do" role="button" class="btn btn-success btn-lg" data-toggle="modal">Create the new
                             event</a>
                 <%--        </form>  --%>
                         <%-- #newEvent<button type="submit" name="Order"class="btn btn-success btn-lg">Create the new event</button> --%>
                </div>
            </div>
        </div>
    </div>


</div>
