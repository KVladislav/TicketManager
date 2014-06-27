<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 27.06.2014
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
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
    <link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">
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

<div class="panel-heading" style="text-align:center;"><b>The new event</b></div>
<center>


    <div class="control-group">
        <label class="my-control-label" for="dateEvent">Date of event</label>
        <input type="text" name="date" id="dateEvent" value="dd-mm-yyyy" />
        <p> <script type="text/javascript"> calendar.set("dateEvent");</script></p>
    </div>

<div class="control-group">
    <label class="my-control-label" for="outputTime">Time of event</label>

    <select name="time" id="outputTime">

        <option value="12">12-00</option>
        <option value="13">13-00</option>
        <option value="14">14-00</option>
        <option value="15">15-00</option>
        <option value="16">16-00</option>
        <option value="17">17-00</option>
        <option value="18">18-00</option>
        <option value="16">19-00</option>
        <option value="17">20-00</option>
        <option value="18">21-00</option>

    </select>

</div>




<button type="submit" name="action" value="saveRec" class="btn btn-primary">Save</button>

</center>

