<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <form class="form-search" action="${pageContext.request.contextPath}/Booking/NewClientSave.do" method="post" name="NewClient">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <input class="span6" id="appendedInputButtons" size="16" type="text" name="clientName" required placeholder="Введите ФИО клиента" value="${client.name}">
                <button class="btn" type="submit">Сохранить</button>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
                <textarea rows="3" class="span6" name="clientDescription" placeholder="Описание"></textarea>
            </form>
        </div>
    </div>
    </form>
</div>

</body>
</html>
