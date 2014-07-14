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
               <input  type="text" name="name" required pattern="^[a-zA-Z]+$" title="Буквы без пробелов"
                   style="text-align:center" required placeholder="Имя" >
         </div>
         <br>
         <div>
             <input type="text" name="surname" required pattern="^[a-zA-Z]+$" title="Буквы без пробелов"
                    maxlength="15" style="text-align:center" required placeholder="Фамилия" >
         </div>
         <br>
         <div>
             <input type="text" name="login" required pattern="^[a-zA-Z0-9]+$"
                    title="Буквы и цифры без пробелов."
                    style="text-align:center" required placeholder="Логин">
         </div>
         <br>
         <div>
             <input type="password" name="password"  required pattern="^[a-zA-Z0-9]+$"
                    title="Буквы и цифры без пробелов."
                    style="text-align:center" required placeholder="Пароль" >
         </div>
         <br>
         <div>
             <input type="password" name="password1"  required pattern="^[a-zA-Z0-9]+$"
                    title="Буквы и цифры без пробелов."
                    style="text-align:center" required placeholder="Повторите пароль" >
         </div>
         <br>
         <div class="control-group">
             <div class="my-controls">
              <textarea rows="5" id="description" style="text-align:center"
                  name="description"></textarea>
             </div>
         </div>
         <h4 style="text-align:center; color:Red">${error}</h4>
        <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
     </form>
</center>


