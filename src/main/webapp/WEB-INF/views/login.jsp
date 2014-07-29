<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Ticket Manager: Авторизация</title>
    <link href="${pageContext.request.contextPath}/resources/ico/tickets.ico" rel="shortcut icon" type="image/x-icon"/>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<body onload='document.loginForm.username.focus();'>
<div class="container">
    <br>

    <form class="form-horizontal" name="loginForm" action="<c:url value='j_spring_security_check' />" method='POST'>
        <fieldset>

            <!-- Form Name -->
            <legend>Пожалуйста, авторизируйтесь</legend>

            <!-- Text input-->
            <div class="form-group">
                <div class="col-md-3 col-md-offset-4">
                    <input name="username" type="text" placeholder="Имя пользователя" title="Только буквы и цифры"
                           pattern="^[А-Яа-яa-zA-Z0-9]{3,}$" maxlength=10 class="form-control input-md"
                           value="<c:out value="${sessionScope.LAST_USERNAME}"/>">
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <div class="col-md-3 col-md-offset-4">
                    <input name="password" type="password" placeholder="Пароль" title="Только буквы и цифры"
                           pattern="^[А-Яа-яa-zA-Z0-9]{0,15}$" maxlength=15 class="form-control input-md">
                </div>
            </div>
            <%--<input type="hidden" name="${_csrf.parameterName}"--%>
            <%--value="${_csrf.token}" />--%>
            <!-- Button -->
            <div class="form-group">
                <div class="col-md-3 col-md-offset-4">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </c:if>
                    <button id="singlebutton" name="singlebutton" class="btn btn-primary">Войти</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>

</body>
<%@include file="footer.jsp" %>
</html>
