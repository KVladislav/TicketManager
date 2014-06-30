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
    <script src="${pageContext.request.contextPath}/resources/css/bootstrap.css"></script>
    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery.js" />'></script>
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.js">
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js">
    </script>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        $(document).on("hover", ".cont", function () {
            $(this).children(".overlay").fadeIn("fast");
        }, function () {
            $(this).children(".overlay").fadeOut("fast");
        });
    </script>


</head>

<div class="panel-heading" style="text-align:center;"><b>The new operator</b></div>
<center>
<form action="${pageContext.request.contextPath}/NewOperator/OperatorsAdd.do" method="post">





<div class="control-group">
    <label class="my-control-label" for="name">Name</label>

    <div class="my-controls">
        <textarea rows="1" id="name" name="name"></textarea>
    </div>
</div>
<div class="control-group">
    <label class="my-control-label" for="surname">Surname</label>

    <div class="my-controls">
        <textarea rows="1" id="surname" name="surname"></textarea>
    </div>
</div>
<div class="control-group">
    <label class="my-control-label" for="login">Login</label>

    <div class="my-controls">
        <textarea rows="1" id="login" name="login"></textarea>
    </div>
</div>
<div class="control-group">
    <label class="my-control-label" for="password">Password</label>

    <div class="my-controls">
        <textarea rows="1" id="password" name="password"></textarea>
    </div>
</div>
<div class="control-group">
    <label class="my-control-label" for="description">Description</label>

    <div class="my-controls">
        <textarea rows="5" id="description" name="description"></textarea>
    </div>
</div>


&MediumSpace;
&MediumSpace;
<button type="submit" name="action" class="btn btn-primary">Save</button>
&MediumSpace;
&MediumSpace;

</form>

</center>

