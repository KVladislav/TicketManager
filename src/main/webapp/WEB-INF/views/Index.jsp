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
            color: #777;
        }
    </style>
</head>
<body>
<div style="align-content: center; height: 800px; width: 1120px">
    <div style="float: right; width: 550px; text-align: center;">
        <div class="imgCaption" >Билетная схема стадиона для концертов</div>
        <img src="${pageContext.request.contextPath}/resources/img/concerts.jpg">
    </div>
    <div style="float: right; width: 550px; text-align: center; margin-right: 20px; ">
        <div class="imgCaption">Билетная схема стадиона для матчей</div>
        <img src="${pageContext.request.contextPath}/resources/img/matches.jpg">
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
