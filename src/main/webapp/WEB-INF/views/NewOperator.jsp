<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
</head>
<h3 style="text-align:center; color:Blue"><b>Создание нового оператора</b></h3>
    <center>
     <form action="${pageContext.request.contextPath}/NewOperator/OperatorsAdd.do" method="post">
         <label>Имя*</label>
         <div>

              <input  type="text" name="name" size=20 maxlength=12 required pattern="^[a-zA-Zа-яА-Я]+$"
                       title="Буквы без пробелов." style="text-align:center" required placeholder="Обязательное поле" >
         </div>
         <br>
         <label>Фамилия*</label>
         <div>
              <input type="text" name="surname" size=20 maxlength=15 required pattern="^[a-zA-Zа-яА-Я]+$"
                    title="Буквы без пробелов" style="text-align:center" required placeholder="Обязательное поле" >
         </div>
         <br>
         <label>Логин*</label>
         <div>
              <input type="text" name="login" size=20 maxlength=10 required pattern="^[a-zA-Z0-9]{3,}$"
                    title="Логин должен состоять из латинских букв и цифр от 3 до 10 символов"
                    style="text-align:center" required placeholder="Обязательное поле">
         </div>
         <br>
         <label>Пароль*</label>
         <div>
             <input type="password" name="password" size=20 maxlength=15 required pattern="^[a-zA-Z0-9]{6,}$"
                    title="Пароль должен состоять из латинских букв и цифр от 6 до 15 символов."
                    style="text-align:center" required placeholder="Обязательное поле">
         </div>
         <br>
         <label>Повторите пароль*</label>
         <div>
             <input type="password" name="passwordRepeat" size=20 maxlength=15 required pattern="^[a-zA-Z0-9]{6,}$"
                    title="Пароль должен состоять из латинских букв и цифр от 6 до 15 символов."
                    style="text-align:center" required placeholder="Обязательное поле">
         </div>
         <br>
         <label>Дополнительная информация*</label>
         <div>
             <input type="text" name="description" size=40 maxlength=50
                    style="text-align:center">
         </div>
                  <br>
         <h4 style="text-align:center; color:Red">${error}</h4>
        <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
     </form>
</center>


