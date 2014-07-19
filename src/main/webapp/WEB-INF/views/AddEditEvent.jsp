<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 27.06.2014
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html lang="ru">
<head>

    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/date.js"></script>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/css/bootstrap.css"></script>
    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery.js" />'></script>
    <script type="text/javascript"
            src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>

    <script type="text/javascript">
        $(document).on("hover", ".cont", function () {
            $(this).children(".overlay").fadeIn("fast");
        }, function () {
            $(this).children(".overlay").fadeOut("fast");
        });
    </script>

    <script language="JavaScript">
        <!--
        // Пишем функцию, определяющую координаты
        function defPosition(event) {
            var x = y = 0;
            if (document.attachEvent != null) { // Internet Explorer & Opera
                x = window.event.clientX + (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
                y = window.event.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
            } else if (!document.attachEvent && document.addEventListener) { // Gecko
                x = event.clientX + window.scrollX;
                y = event.clientY + window.scrollY;
            } else {
                // Do nothing
            }
            return {x: x, y: y};
        }
        // Простая проверка
        // С помощью document.write выведем координаты прямо в окно браузера
        // Они будут обновлять при движении мыши
        document.onmousemove = function (event) {
            var event = event || window.event;
//document.getElementById('help').innerHTML = "x = " + defPosition(event).x + ", y = " + defPosition(event).y;

//Здесь координаты присваиватся положению слоя относительно окна и к координате х плюсуется 15 пикселов, чтоб курсор не был на подсказке.
            document.getElementById('help').style.left = defPosition(event).x + 15 + "px";
            document.getElementById('help').style.top = defPosition(event).y + "px";
        }

        //Функция, которая делает видимым наш слой и вкладывает в него необходимый текст.
        function helpBox(title, text) {
//Вкладываем текст
            document.getElementById('helpTitle').innerHTML = title;
            document.getElementById('helpText').innerHTML = text;

//Делаем видимым\невидимым
            if (document.getElementById('help').style.display == 'none') {
                document.getElementById('help').style.display = 'block';
            } else {
                document.getElementById('help').style.display = 'none';
            }
        }

        // -->
    </script>

    <script language="JavaScript">
        function ValidFormFields(frm) {
            for (var i = 0; i < frm.elements.length; i++) {
                if (document.frmAddArticle.elements[i].value == "") {
                    alert('Не все поля заполнены!');
                    return false;
                }
                return true;
            }
        }
    </script>
    <script language="JavaScript">
        function confirmAction() {
            return confirm("Do you really want this subscription?")
        }
    </script>

    <title>
        <c:if test="${empty eventEdit.id}"> Создание нового мероприятия </c:if>
        <c:if test="${not empty eventEdit.id}"> Редактирование мероприятия </c:if>
    </title>

</head>
<%--
--%>
<center>

<c:if test="${empty eventEdit.id}">
<h1>
    <div class="panel-heading" style="text-align:center;"><b> Создание нового мероприятия </b></div>
</h1>&MediumSpace;&MediumSpace;
<p class="alert-error">${eventErrorMessage}</p>

<form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post"
      onsubmit="return ValidFormFields(this)"></c:if>
<c:if test="${not empty eventEdit.id}">
<h1>
    <div class="panel-heading" style="text-align:center;"><b> Редактирование мероприятия </b></div>
</h1>&MediumSpace;&MediumSpace;
<p class="alert-error">${errorMessageEdit}</p>

<form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do" method="post"
      onsubmit="return ValidFormFields(this)"></c:if>
<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

<center>
<label class="my-control-label" required warning for="dateEvent"> Дата мероприятия </label>

<center>

    <div class="control-group">
        <input type="text" name="dateEvent" readonly id="dateEvent"
               value="<fmt:formatDate value='${dateEvent}' type='date' />"/>

        <p>
            <script type="text/javascript"> calendar.set("dateEvent");</script>
        </p>
    </div>

    <label class="my-control-label" for="inputTime"> Время мероприятия </label>

    <div class="control-group">
        <select name="inputTime" id="inputTime">

            <c:if test="${eventTime.equals(inputTime)}">
                <option selected value="${eventTime}">${eventTime}</option>
            </c:if>

            <c:if test="${eventTime.equals('10-00')}">
                <option selected value="10-00">10-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('10-00')}">
                <option value="10-00">10-00</option>
            </c:if>
            <c:if test="${eventTime.equals('10-30')}">
                <option selected value="10-30">10-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('10-30')}">
                <option value="10-30">10-30</option>
            </c:if>
            <c:if test="${eventTime.equals('11-00')}">
                <option selected value="11-00">11-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('11-00')}">
                <option value="11-00">11-00</option>
            </c:if>
            <c:if test="${eventTime.equals('11-30')}">
                <option selected value="11-30">11-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('11-30')}">
                <option value="11-30">11-30</option>
            </c:if>
            <c:if test="${eventTime.equals('12-00')}">
                <option selected value="12-00">12-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('12-00')}">
                <option value="12-00">12-00</option>
            </c:if>
            <c:if test="${eventTime.equals('12-30')}">
                <option selected value="12-30">12-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('12-30')}">
                <option value="12-30">12-30</option>
            </c:if>
            <c:if test="${eventTime.equals('13-00')}">
                <option selected value="13-00">13-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('13-00')}">
                <option value="13-00">13-00</option>
            </c:if>
            <c:if test="${eventTime.equals('13-30')}">
                <option selected value="13-30">13-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('13-30')}">
                <option value="13-30">13-30</option>
            </c:if>
            <c:if test="${eventTime.equals('14-00')}">
                <option selected value="14-00">14-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('14-00')}">
                <option value="14-00">14-00</option>
            </c:if>
            <c:if test="${eventTime.equals('14-30')}">
                <option selected value="14-30">14-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('14-30')}">
                <option value="14-30">14-30</option>
            </c:if>
            <c:if test="${eventTime.equals('15-00')}">
                <option selected value="15-00">15-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('15-00')}">
                <option value="15-00">15-00</option>
            </c:if>
            <c:if test="${eventTime.equals('15-30')}">
                <option selected value="15-30">15-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('15-30')}">
                <option value="15-30">15-30</option>
            </c:if>
            <c:if test="${eventTime.equals('16-00')}">
                <option selected value="16-00">16-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('16-00')}">
                <option value="16-00">16-00</option>
            </c:if>
            <c:if test="${eventTime.equals('16-30')}">
                <option selected value="16-30">16-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('16-30')}">
                <option value="16-30">16-30</option>
            </c:if>
            <c:if test="${eventTime.equals('17-00')}">
                <option selected value="17-00">17-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('17-00')}">
                <option value="17-00">17-00</option>
            </c:if>
            <c:if test="${eventTime.equals('17-30')}">
                <option selected value="17-30">17-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('17-30')}">
                <option value="17-30">17-30</option>
            </c:if>
            <c:if test="${eventTime.equals('18-00')}">
                <option selected value="18-00">18-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('18-00')}">
                <option value="18-00">18-00</option>
            </c:if>
            <c:if test="${eventTime.equals('18-30')}">
                <option selected value="18-30">18-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('18-30')}">
                <option value="18-30">18-30</option>
            </c:if>
            <c:if test="${eventTime.equals('19-00')}">
                <option selected value="19-00">19-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('19-00')}">
                <option value="19-00">19-00</option>
            </c:if>
            <c:if test="${eventTime.equals('19-30')}">
                <option selected value="19-30">19-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('19-30')}">
                <option value="19-30">19-30</option>
            </c:if>
            <c:if test="${eventTime.equals('20-00')}">
                <option selected value="20-00">20-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('20-00')}">
                <option value="20-00">20-00</option>
            </c:if>
            <c:if test="${eventTime.equals('20-30')}">
                <option selected value="20-30">20-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('20-30')}">
                <option value="20-30">20-30</option>
            </c:if>
            <c:if test="${eventTime.equals('21-00')}">
                <option selected value="21-00">21-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('21-00')}">
                <option value="21-00">21-00</option>
            </c:if>
            <c:if test="${eventTime.equals('21-30')}">
                <option selected value="21-30">21-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('21-30')}">
                <option value="21-30">21-30</option>
            </c:if>
            <c:if test="${eventTime.equals('22-00')}">
                <option selected value="22-00">22-00</option>
            </c:if>
            <c:if test="${!eventTime.equals('22-00')}">
                <option value="22-00">22-00</option>
            </c:if>
            <c:if test="${eventTime.equals('22-30')}">
                <option selected value="22-30">22-30</option>
            </c:if>
            <c:if test="${!eventTime.equals('22-30')}">
                <option value="22-30">22-30</option>
            </c:if>

        </select>
    </div>
</center>

<div class="control-group">
    <label class="my-control-label" for="description"> Наименование </label>

    <div class="my-controls">
        <input type="text" id="description" maxlength="50" data-min-length=1 name="description" required pattern="^\S+$"
               title="Только полненное значение  до 50 символов!" value="${eventDescriptions}"/>
    </div>
</div>

<div class="control-group">
    <label class="my-control-label" for="timeRemoveBooking"> Установка времени удаления брони </label>

    <div class="my-controls">
        <input type="text" id="timeRemoveBooking" maxlength="10" name="timeRemoveBooking" value="${eventBookingTimeOut}"
               required pattern="[1-9]\d{0,2}?" title="Только целое положительное число от одной до трех цифр!">
        <%--  <img src="${pageContext.request.contextPath}/resources/img/Question.png"
               alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
               title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
          <a class="thumbnail" href="#"><img src="${pageContext.request.contextPath}/resources/img/Question.png" width="100px" height="66px" border="0" />
              <span>Поле позволяет установить время, по истечении которого бронь полностью снимается</span></a>  --%>

        <!--Это сам слой, который является всплывающей посказкой, состоит из трех дивов, общий контейнер, тайтл и текст-->

        <div id="help" class="helpBox" style="display:none;position:absolute;"><p id="helpTitle" class="helpTitle">Поле
            позволяет установить время, по истечении которого бронь полностью снимается</p>

            <p id="helpText" class="helpText">Help text</p></div>

        <!-- это элемент который вызывает подсказку при наведении курсора мыши на нее, и скрывает, когда курсор убирается-->

        <img src="${pageContext.request.contextPath}/resources/img/Question.png"
             onMouseOver="helpBox('Подсказка', 'Поле позволяет установить время, по истечении которого бронь полностью снимается')"
             onMouseOut="helpBox()">


    </div>
</div>

<div class="panel-body" style="padding:30px; width:40%;">
    <div class="table responsive">
        <table class="table table-bordered" style="display:block;height:400px;overflow:auto;">
            <thead>
            <tr>
                <th>Сектор</th>
                <th>Цена</th>
                <th>Удалить</th>
            </tr>
            </thead>
            `
            <tbody>

            <%--
             <c:forEach items="sectors" var="sector" varStatus="theCount">  <%--{sectors} --%

                     <tr>
                         <td>
                             <input type="text" readonly name="name${sector.name}" required placeholder="Сектор"
                                    value="${sector.name}">
                         </td>
                         <td>

                             <div>
                                 <input type="text" maxlength="25" required pattern="^\d+\.{0,1}\d{0,2}$"
                                        title="только числа до двух знаков после запятой"
                                        name="id${sector.id}" placeholder="Цена" value="${sector.price}">
                             </div>
                         </td>
                         <td>
                             <input type="hidden" name="sectorId" value="${sector.id}">
                             <button type="submit" name="action" value="delete" class="btn btn-default btn-xs">
                                 <span class="glyphicon glyphicon-trash"></span></button>
                         </td>
                      </tr>
                 </c:forEach>
                 --%>

            <c:forEach items="${allSectors}" var="sector" varStatus="theCount">  <%--{sectors} --%>

                <tr>
                    <td>
                        <input type="text" readonly name="name" required placeholder="Сектор"
                               value="${sector.value.name}">
                    </td>
                    <td>

                        <div>
                            <input type="text" maxlength="25" required pattern="^\d+\.{0,1}\d{0,2}$"
                                   title="только числа до двух знаков после запятой"
                                   name="price${sector.value.id}" placeholder="Цена" value="${sector.value.price}">
                        </div>
                    </td>
                    <td>
                        <input type="hidden" name="sector${sector.value.id}" value="${sector.value.id}">
                        <button type="submit" name="delete" value="${sector.value.id}" class="btn btn-default btn-xs">
                        <span class="glyphicon glyphicon-trash"></span></button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


        &MediumSpace;
        &MediumSpace;
        <button type="submit" name="action" value="save" class="btn btn-primary">Сохранить</button>
        &MediumSpace;
        &MediumSpace;
</form>
<div class="col-md-4 column">
    <form action="${pageContext.request.contextPath}/AddEditEvent/Cancel.do" method="post">
        <input type="submit" name="Cancel" class="btn  btn-danger" value="Отмена"></form>
</div>

<%--
        <c:if test="${empty name}">
            <div class="alert  alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <h4>Error!</h4>Поле "Наименование" должно быть заполнено. Проверьте его.
            </div>
        </c:if>
        --%>


</center>


