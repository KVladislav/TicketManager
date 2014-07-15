<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 15.07.2014
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/scripts.js"></script>

</head>
<body onload='document.loginForm.username.focus();'>

   <br>
<form class="form-horizontal" name="loginForm" action="<c:url value='j_spring_security_check' />" method='POST'>
    <fieldset>

        <!-- Form Name -->
        <legend>Пожалуйста авторизируйтесь</legend>

        <!-- Text input-->
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <input name="username" type="text" placeholder="Ваше имя пользователя" class="form-control input-md">
            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <input name="password" type="password" placeholder="Ваш пароль" class="form-control input-md">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
        <!-- Button -->
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">${error}</div>
                </c:if>
                <button id="singlebutton" name="singlebutton" class="btn btn-primary">Войти</button>
            </div>
        </div>

    </fieldset>
</form>

</body>
</html>
