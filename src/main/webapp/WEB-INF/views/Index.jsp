<%@include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Ticket Manager</title>
    <style>
        .imgCaption{
            font-weight: bold;
            font-size: 20px;
            margin-bottom: 20px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div style="align-content: center; height: 620px; width: 1220px; margin-left: auto; margin-right: auto;">
    <div style="float: right; width: 550px; text-align: center;">
        <div class="imgCaption text-info" >Билетная схема стадиона для концертов</div>
        <img src="${pageContext.request.contextPath}/resources/img/concerts.jpg">
    </div>
    <div style="float: right; width: 550px; text-align: center; margin-right: 120px; ">
        <div class="imgCaption text-info">Билетная схема стадиона для матчей</div>
        <img src="${pageContext.request.contextPath}/resources/img/matches.jpg">
    </div>
</div>
</body>

<%@include file="footer.jsp" %>
</html>
