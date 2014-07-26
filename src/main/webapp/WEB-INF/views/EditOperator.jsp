<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
</head>
<body>

<div class="container" style="margin-bottom: 50px">
    <div class="row clearfix">
        <div class="col-md-6 col-md-offset-4 column">
            <c:if test="${operator.id!=null}"><h3 class="panel-heading text-info">Редактирование оператора</h3></c:if>
            <c:if test="${operator.id==null}"><h3 class="panel-heading text-info">Создание нового оператора</h3></c:if>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-6  col-md-offset-3 column">
            <%--<c:if test="${errorOperator.!=null}">--%>
            <c:forEach items="${errorMessages}" var="errorMessage">
                <div class="alert alert-warning" role="alert">${errorMessage}</div>
            </c:forEach>
            <%--</c:if>--%>
        </div>
    </div>

    <div class="row clearfix">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/Operators/OperatorEditSave.do"
              method="post" role="form">
            <input type="hidden" name="operatorId" value="${operator.id}">

            <div class="form-group">
                <label class="col-md-4 control-label text-info" for="OperatorName">Имя*</label>

                <div class="col-md-4 column">
                    <input class="form-control input-md" type="text" name="name" size=20 maxlength=12 required
                           pattern="^[a-zA-Zа-яА-Я]{1,12}$" id="OperatorName"
                           title="Буквы без пробелов" value="${operator.name}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label text-info" for="OperatorSureName">Фамилия*</label>

                <div class="col-md-4 column">
                    <input class="form-control input-md" type="text" name="surname" size=20 maxlength=15 required
                           pattern="^[a-zA-Zа-яА-Я]{1,15}$" id="OperatorSureName"
                           title="Буквы без пробелов" value="${operator.surname}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label text-info" for="OperatorLogin">Логин*</label>

                <div class="col-md-4 column">
                    <input class="form-control input-md" type="text" name="login" size=20 maxlength=10 required
                           pattern="^[a-zA-Z0-9]{3,10}$" id="OperatorLogin"
                           title="Латинские буквы и цифры от 3 до 10 символов" value="${operator.login}">
                </div>
            </div>
            <c:if test="${operator.id!=null}">
                <div class="form-group">
                    <label class="col-md-4 control-label text-info" for="OperatorOldPass">Текущий пароль*</label>

                    <div class="col-md-4 column">
                        <input class="form-control input-md" type="password" name="password" size=20 maxlength=15
                               required
                               pattern="^[a-zA-Z0-9]{0,15}$" id="OperatorOldPass"
                               title="Латинские буквы и цифры от 6 до 15 символов">
                    </div>
                </div>
            </c:if>

            <div class="form-group">


                <c:if test="${operator.id!=null}">
                    <label class="col-md-4 control-label text-info" for="OperatorNewPass1">Новый пароль</label>

                    <div class="col-md-4 column">
                        <input class="form-control input-md" type="password" name="passwordNew" size=20 maxlength=15
                               pattern="^[a-zA-Z0-9]{0,15}$" id="OperatorNewPass1"
                               title="Латинские буквы и цифры от 6 до 15 символов">
                    </div>
                </c:if>


                <c:if test="${operator.id==null}">
                    <label class="col-md-4 control-label text-info" for="OperatorNewPass1">Новый пароль*</label>

                    <div class="col-md-4 column">
                        <input class="form-control input-md" type="password" name="passwordNew" size=20 maxlength=15
                               required pattern="^[a-zA-Z0-9]{6,15}$" id="OperatorNewPass1"
                               title="Латинские буквы и цифры от 6 до 15 символов">
                    </div>
                </c:if>

            </div>

            <div class="form-group">


                <c:if test="${operator.id!=null}">
                    <label class="col-md-4 control-label text-info" for="OperatorNewPass2">Подтвердите новый
                        пароль</label>

                    <div class="col-md-4 column">
                        <input class="form-control input-md" type="password"
                               name="passwordNewRepeat" size=20 maxlength=15
                               pattern="^[a-zA-Z0-9]{0,15}$" id="OperatorNewPass2"
                               title="Латинские буквы и цифры от 6 до 15 символов">
                    </div>

                </c:if>
                <c:if test="${operator.id==null}">
                    <label class="col-md-4 control-label text-info" for="OperatorNewPass2">Подтвердите новый
                        пароль*</label>

                    <div class="col-md-4 column">
                        <input class="form-control input-md" type="password" name="passwordNewRepeat" size=20
                               maxlength=15
                               required pattern="^[a-zA-Z0-9]{6,15}$" id="OperatorNewPass2"
                               title="Латинские буквы и цифры от 6 до 15 символов">
                    </div>

                </c:if>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label text-info" for="OperatorDescription">Дополнительная
                    информация</label>

                <div class="col-md-4 column">
                    <textarea style="resize:none" rows="3" maxlength="200" class="form-control" name="description"
                              id="OperatorDescription">${operator.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-2 col-md-offset-4 column">
                    <a class="btn btn-danger btn-md"
                       href="${pageContext.request.contextPath}/Operators/Operators.do"
                       role="button">Отмена</a>
                </div>
                <div class="col-md-2 column">
                    <button type="submit" name="action" class="btn btn-primary">Сохранить</button>
                </div>

            </div>
        </form>
</body>
<%@include file="footer.jsp" %>

