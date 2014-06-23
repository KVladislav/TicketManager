<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<%--<html>--%>
<%--<head>--%>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--<head><title>Events manager</title></head>--%>
<%--<link href="../css/bootstrap.css" rel="stylesheet">--%>
<%--<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>--%>
<%--<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>--%>
<%--<script src="../js/bootstrap.js"></script>--%>

<%--<script type="text/javascript">--%>
    <%--$(window).on('load', function () {--%>

        <%--$('.selectpicker').selectpicker({--%>
            <%--'selectedText': 'cat'--%>
        <%--});--%>
        <%--// $('.selectpicker').selectpicker('hide');--%>
    <%--});--%>
<%--</script>--%>

<%--<center>--%>
    <%--<div class="container">--%>
        <%--<div class="row">--%>
            <%--<div class="col-md-8 col-md-offset-3"><h2>Events manager</h2></div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="header" style="backgroung-color:red;">--%>
        <%--<ul class="nav nav-tabs">--%>
            <%--<li class="active"><a href="#">Sale tickets</a></li>--%>
            <%--<li><a href="#">BOOKING</a></li>--%>
            <%--<li><a href="#">Return tickets</a></li>--%>
            <%--<li><a href="#">Events manager</a></li>--%>
        <%--</ul>--%>
    <%--</div> &MediumSpace; &MediumSpace; </head>--%>
    <%--<body>--%>

    <div class="panel-heading" style="text-align:center;">List of events</div>
    <div class="panel-body" style="padding:15px;">
        <form action="${pageContext.request.contextPath}/Events.do" method="post">
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
                                <button type="submit" name="action" value="edit" class="btn">Edit</button>
                            </td>
                            <td>
                                <button type="submit" name="action" value="delete" class="btn">Delete</button>
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

                    <div class="col-md-8 col-md-offset-3">
                        <button type="submit" name="Order"class="btn btn-success btn-lg">Создать новое мероприятие</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div class="clearfix"/>

    </body>
</center>
</html>

