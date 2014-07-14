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

<div class="panel-heading" style="text-align:center;"><b>Создание нового оператора</b></div>
    <center>
     <form action="${pageContext.request.contextPath}/NewOperator/OperatorsAdd.do" method="post">
         <div>
             <input path="id" errors path="error"  id="name" required pattern="^[a-zA-Z]+$"  title="Буквы без пробелов"
                    maxlength="12" style="text-align:center" required placeholder="Имя"  name="name">
         </div>
         <br>
         <div>
             <input path="id" errors path="error" id="surname" required pattern="^[a-zA-Z]+$"
                    title="Буквы без пробелов" maxlength="15" style="text-align:center"
                    required placeholder="Фамилия"  name="surname">
         </div>
         <br>
         <div>
             <input path="id" errors path="error"  id="login" style="text-align:center" required placeholder="Логин"  name="login">
         </div>
         <br>
         <div>
             <input path="id" errors path="error"  id="password" required pattern="^[a-zA-Z0-9]+$"
                      minlength="6"  maxlength="15" title="Буквы и цифры без пробелов. Минимум 6 символов."
                    style="text-align:center" required placeholder="Пароль" type="password"  name="password">
         </div>
         <br>

         <div class="control-group">
             <div class="my-controls">
              <textarea rows="5" id="description" style="text-align:center"
                  name="description"></textarea>
             </div>
         </div>
         <h3 style="text-align:center; color:Red">${error}</h3>
        <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
     </form>
</center>


