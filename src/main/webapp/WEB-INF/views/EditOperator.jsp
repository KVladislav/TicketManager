<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <%--<link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">--%>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
</head>

<body>

<h3 style="text-align:center; color:Blue">Редактирование оператора</h3>
<center>
    <form action="${pageContext.request.contextPath}/EditOperator/Save.do" method="post">
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
            <input type="text" name="login" size=20 maxlength=10 required pattern="^[a-zA-Z0-9]{3,}$"
                   title="Логин должен состоять из латинских букв и цифр от 3 до 10 символов."
                   style="text-align:center" value="${operator.login}">
        </div>
        <br>
        <label>Старый пароль*</label>
        <div>
            <input type="password" name="password" size=20 maxlength=15 required pattern="^[a-zA-Z0-9]{6,}$"
                   title="Пароль должен состоять из латинских букв и цифр от 6 до 10 символов."
                   style="text-align:center" required placeholder="Обязательное поле">
        </div>
        <br>
        <label>Новый пароль</label>
        <div>
            <input type="password" name="passwordNew" size=38 maxlength=15
                   style="text-align:center" placeholder="Обязательно при изменении пароля">
        </div>
        <br>
        <label>Подтвердите новый пароль</label>
        <div>
            <input type="password" name="passwordNewRepeat" size=38 maxlength=15
                   style="text-align:center" placeholder="Обязательно при изменении пароля">
        </div>
        <br>
        <label>Дополнительная информация</label>
        <div>
            <input type="text" name="description" size=50 maxlength=50
                   style="text-align:center" value="${operator.description}">
        </div>
        <h4 style="text-align:center; color:Red">${errorOperator}</h4>
        <table>
            <tr>
                <td>
                    <form name = "delTicket" action="${pageContext.request.contextPath}/EditOperator/Save.do" method="post">
                        <h3 style="text-align:center">
                        <button  type="submit" name="action" class="btn btn-primary" >Сохранить</button></h3>
                    </form>
                </td>
                <td>
                    <div class="control-group">
                        <div class="col-md-1 column">
                            <form name = "cancelTicket" action="${pageContext.request.contextPath} /EditOperator/Cancel.do"
                                  method="get">
                                <h3 style="text-align:center">
                                    <input type="submit" name="Cancel" class="btn  btn-danger" value="Отмена">
                                </h3>
                            </form>
                        </div>
                    </div>
                </td>
        </table>
     </form>
</center>

</body>


</html>
