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
</head>
<h3 style="text-align:center; color:Blue"><b>Редактирование оператора</b></h3>
<center>
    <form action="${pageContext.request.contextPath}/EditOperator/OperatorsEditSave.do" method="post">
        <input type="hidden" name="operatorId" value="${operator.id}">
        <label>Имя*</label>
        <div>
              <input type="text" name="name" size=20 maxlength=12 required pattern="^[a-zA-Zа-яА-Я]+$"
                     title="Буквы без пробелов." style="text-align:center" value="${operator.name}">
        </div>
        <br>
        <label>Фамилия*</label>
        <div>
            <input type="text" name="surname" size=20 maxlength=15 required pattern="^[a-zA-Zа-яА-Я]+$"
                   title="Буквы без пробелов." style="text-align:center" value="${operator.surname}">
        </div>
        <br>
        <label>Логин*</label>
        <div>
            <input type="text" name="login" size=20 maxlength=10 required pattern="^[a-zA-Z0-9]+$"
                   title="Логин должен состоять из латинских букв и цифр."
                   style="text-align:center" value="${operator.login}">
        </div>
        <br>
        <label>Старый пароль*</label>
        <div>
            <input type="password" name="password" size=20 maxlength=15 required pattern="^[a-zA-Z0-9]+$"
                   title="Пароль должен состоять из латинских букв и цифр."
                   style="text-align:center" required placeholder="Обязательное поле">
        </div>
        <br>
        <label>Новый пароль*</label>
        <div>
            <input type="password" name="passwordNew" size=34 maxlength=15
                   style="text-align:center" placeholder="Обязательно при смене пароля">
        </div>
        <br>
        <label>Подтвердите новый пароль*</label>
        <div>
            <input type="password" name="passwordNewRepeat" size=34 maxlength=15
                   style="text-align:center" placeholder="Обязательно при смене пароля">
        </div>
        <br>
        <label>Дополнительная информация*</label>
        <div>
            <input type="text" name="description" size=50 maxlength=50
                   style="text-align:center" value="${operator.description}">
        </div>
        <h4 style="text-align:center; color:Red">${error}</h4>
        <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
     </form>
</center>


