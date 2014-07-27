<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 27.06.2014
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<html lang="ru">
<head>

    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/date.js"></script>
    <!-- Bootstrap -->


    <%-- <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
     <%--<link href="${pageContext.request.contextPath}/resources/ico/favicon.ico">--%>
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

    <script src="http://malsup.github.com/jquery.form.js"></script>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.0/css/bootstrap-responsive.css"
          rel="stylesheet">
    <link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.0/css/bootstrap.css" rel="stylesheet">

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
            document.getElementById('help').style.top = defPosition(event).y - 40 + "px";
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
    <script>
        $(document).ready(function () {
            $(".deleteSector").click(function () {
                var data_id = '';
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                $('#sectorId').val(data_id);
            })
        });
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
    <caption><h1 class="panel-heading text-info"><b> Создание нового мероприятия </b></h1></caption>
</h1>
<c:if test="${eventErrorMessage!=null}">

    <h4 style="color: red"> ${eventErrorMessage}</h4>

</c:if>
<form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post">
</c:if>
<c:if test="${not empty eventEdit.id}">
<h1>
    <caption><h1 class="panel-heading text-info"><b> Редактирование мероприятия </b></h1></caption>
</h1>

<c:if test="${errorMessageEdit!=null}">

<h4 style="color: red"> ${errorMessageEdit} </h4>

</c:if>
<form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do" method="post">

<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

</c:if>
<center>
    <table>
        <td>
            <tr>
                <label class="my-control-label  text-info" required warning for="dateEvent"> Дата мероприятия </label>

                <center>

                    <div class="control-group">
                        <input type="text" name="dateEvent" readonly id="dateEvent"
                               value="<fmt:formatDate value='${dateEvent}' type='date' />"/>

                        <p>
                            <script type="text/javascript"> calendar.set("dateEvent");</script>
                        </p>
                    </div>
            </tr>
            <tr>
                <label class="my-control-label  text-info" for="eventTime"> Время мероприятия </label>

                <div class="control-group text-info">
                    <select name="eventTime" id="eventTime">

                        <c:if test="${eventTime.equals('10:00')}">
                            <option selected value="10:00">10:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('10:00')}">
                            <option value="10:00">10:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('10:30')}">
                            <option selected value="10:30">10:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('10:30')}">
                            <option value="10:30">10:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('11:00')}">
                            <option selected value="11:00">11:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('11:00')}">
                            <option value="11:00">11:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('11:30')}">
                            <option selected value="11:30">11:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('11:30')}">
                            <option value="11:30">11:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('12:00')}">
                            <option selected value="12:00">12:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('12:00')}">
                            <option value="12:00">12:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('12:30')}">
                            <option selected value="12:30">12:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('12:30')}">
                            <option value="12:30">12:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('13:00')}">
                            <option selected value="13:00">13:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('13:00')}">
                            <option value="13:00">13:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('13:30')}">
                            <option selected value="13:30">13:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('13:30')}">
                            <option value="13:30">13:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('14:00')}">
                            <option selected value="14:00">14:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('14:00')}">
                            <option value="14:00">14:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('14:30')}">
                            <option selected value="14:30">14:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('14:30')}">
                            <option value="14:30">14:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('15:00')}">
                            <option selected value="15:00">15:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('15:00')}">
                            <option value="15:00">15:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('15:30')}">
                            <option selected value="15:30">15:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('15:30')}">
                            <option value="15:30">15:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('16:00')}">
                            <option selected value="16:00">16:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('16:00')}">
                            <option value="16:00">16:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('16:30')}">
                            <option selected value="16:30">16:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('16:30')}">
                            <option value="16:30">16:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('17:00')}">
                            <option selected value="17:00">17:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('17:00')}">
                            <option value="17:00">17:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('17:30')}">
                            <option selected value="17:30">17:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('17:30')}">
                            <option value="17:30">17:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('18:00')}">
                            <option selected value="18:00">18:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('18:00')}">
                            <option value="18:00">18:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('18:30')}">
                            <option selected value="18:30">18:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('18:30')}">
                            <option value="18:30">18:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('19:00')}">
                            <option selected value="19:00">19:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('19:00')}">
                            <option value="19:00">19:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('19:30')}">
                            <option selected value="19:30">19:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('19:30')}">
                            <option value="19:30">19:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('20:00')}">
                            <option selected value="20:00">20:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('20:00')}">
                            <option value="20:00">20:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('20:30')}">
                            <option selected value="20:30">20:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('20:30')}">
                            <option value="20:30">20:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('21:00')}">
                            <option selected value="21:00">21:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('21:00')}">
                            <option value="21:00">21:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('21:30')}">
                            <option selected value="21:30">21:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('21:30')}">
                            <option value="21:30">21:30</option>
                        </c:if>
                        <c:if test="${eventTime.equals('22:00')}">
                            <option selected value="22:00">22:00</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('22:00')}">
                            <option value="22:00">22:00</option>
                        </c:if>
                        <c:if test="${eventTime.equals('22:30')}">
                            <option selected value="22:30">22:30</option>
                        </c:if>
                        <c:if test="${!eventTime.equals('22:30')}">
                            <option value="22:30">22:30</option>
                        </c:if>

                    </select>
                </div>
</center>
</tr>
<tr>
    <div class="control-group">
        <label class="my-control-label text-info" for="eventDescriptions"> Наименование </label>
        <div class="my-controls">
            <input style="resize: none" type="text" size="20" id="eventDescriptions" maxlength="50" data-min-length=1
                   name="eventDescriptions"
                   required pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}"
                   title="Не пустое,не начинается с пробела,только буквы и цифры,до 50 знаков"
                   value="${eventDescriptions}"/>
        </div>
    </div>
</tr>
<tr>
    <div class="control-group">
        <label class="my-control-label text-info" for="eventBookingTimeOut"> Установка времени удаления брони в
            минутах </label>

        <div class="my-controls">

            <input type="text" id="eventBookingTimeOut" size="20" maxlength="3" name="eventBookingTimeOut"
                   value="${eventBookingTimeOut}"
                   required pattern="[1-9]\d{0,2}?" title="Только целое положительное число от одной до трех цифр!">

            <!--Это сам слой, который является всплывающей посказкой, состоит из трех дивов, общий контейнер, тайтл и текст-->

            <div id="help" class="helpBox" style="display:none;position:absolute;"><p id="helpTitle" class="helpTitle">
                Поле
                позволяет установить время, по истечении которого бронь полностью снимается (в минутах, максимум три
                цифры)</p>

                <p id="helpText" class="helpText">Help text</p></div>

            <!-- это элемент который вызывает подсказку при наведении курсора мыши на нее, и скрывает, когда курсор убирается-->

            <img src="${pageContext.request.contextPath}/resources/img/Question.png"
                 onMouseOver="helpBox('Подсказка', 'Поле позволяет установить время, по истечении которого бронь полностью снимается')"
                 onMouseOut="helpBox()">
        </div>
    </div>
</tr>

</div>
</div>
</td>
</table>

<!-- Button to trigger modal -->
<%---<button type="button" data-toggle="modal" data-target="#myModal"> Добавить сектор</button>
<a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Добавить сектор">
<a type="button" class="btn btn-default btn-md" href="${pageContext.request.contextPath}/AddEditEvent/NewSector.do" method="post" >
    Добавить сектор
</a>
---%>


<%--
</a>
data-toggle="modal">
data-target="#sectorAddConfirmation"
<script>
    $("a.my-tool-tip").tooltip();
</script>

--%>

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

            <tbody>

            <c:forEach items="${allSectors}" var="sector" varStatus="theCount">  <%--{sectors} --%>

                <tr>
                    <td>
                        <input tabindex="0" type="text" class="form-control" size="15" style="resize: none" readonly
                               name="name" required
                               placeholder="Сектор"
                               value="${sector.value.name}">
                    </td>
                    <td>

                        <div>
                            <input tabindex="1" type="text" class="form-control" size="15" maxlength="5" required
                                   pattern="^\d{0,5}(\.\d{0,2}){0,1}$"
                                   title="только числа до двух знаков после запятой"
                                   name="price${sector.value.id}" placeholder="Цена" value="${sector.value.price}">
                        </div>
                    </td>
                    <td>
                        <input tabindex="0" type="hidden" name="sector${sector.value.id}" value="${sector.value.id}">
                        <button type="submit" name="delete" value="${sector.value.id}" formnovalidate
                                class="btn btn-default btn-xs">
                            <span class="glyphicon glyphicon-trash"></span></button>

                    <%--   <a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Удалить">
                                   <button tabindex="0" type="button" class="btn btn-default btn-md deleteSector"
                                           data-id="${sector.value.id}"  data-toggle="modal" data-target="#sectorDeleteRequest">
                                       <span class="glyphicon glyphicon-trash"></span></button>
                               </a>
                               <script>
                                   $("a.my-tool-tip").tooltip();
                               </script> --%>


                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div class="form-group">
            <div class="col-md-3 col-md-offset-1 column">
                <a class="btn btn-danger btn-lg btn-primary" href="${pageContext.request.contextPath}/Events/Cancel.do"
                   role="button">Отменить</a>
            </div>
            <div class="col-md-3 column">
            <button type="submit" name="action" value="save" class="btn btn-primary">Сохранить</button>
            </div>
            <div class="col-md-3 column">
                <input type="hidden" name="dateEvent"
                       value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
                <input type="hidden" name="eventTime" value="${eventTime}">
                <input type="hidden" name="eventDescriptions" value="${eventDescriptions}">
                <input type="hidden" name="eventBookingTimeOut" value="${eventBookingTimeOut}">
                <a href="/NewSector/NewSector.do" class="btn btn-info btn-lg" role="button" data-toggle="modal">+
                    Добавить сектор</a>

            </div>
        </div>
    </div>
</div>
</form>
</center>

<%--
<form action=${pageContext.request.contextPath}/NewSector/NewSector.do">
<input type="hidden" name="dateEvent"
       value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
<input type="hidden" name="inputTime" value="${eventTime}">
<input type="hidden" name="description" value="${eventDescriptions}">
<input type="hidden" name="timeRemoveBooking" value="${eventBookingTimeOut}">
<input type="hidden" name="eventEditHidden" value="${eventEdit.id}">

<button type="submit" name="addd" value="" id="addd" class="btn btn-primary btn-md">Добавить сектор?
    +<span class="glyphicon glyphicon-export"></span></button>

</form>
--%>

<div class="modal" id="sectorDeleteRequest" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span></button>
                <h4 class="modal-title" id="myModalLabel">Вопрос! </h4>
            </div>
            <div class="modal-body">
                <h4>Вы действительно хотите удалить сектор?</h4>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Отменить</button>
                    </div>
                    <div class="col-md-2 column">
                        <c:if test="${empty eventEdit.id}">
                            <form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post"
                                  novalidate></c:if>
                            <c:if test="${not empty eventEdit.id}">
                            <form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do"
                                  method="post" novalidate></c:if>
                                <input type="hidden" name="dateEvent"
                                       value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
                                <input type="hidden" name="eventTime" value="${eventTime}">
                                <input type="hidden" name="eventDescriptions" value="${eventDescriptions}">
                                <input type="hidden" name="eventBookingTimeOut" value="${eventBookingTimeOut}">
                                <input type="hidden" name="eventEditHidden" value="${eventEdit.id}">
                                <input type="submit" name="delete" formnovalidate="formnovalidate" value="submit"
                                       id="sectorId"
                                       class="btn btn-danger btn-md">Удалить?
                                </button>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Modal sector-->
<div class="modal" id="sectorAddConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Задайте параметры нового сектора:</h4>
                <tr>
                    <td>
                        <div class="control-group ">
                            <label class="my-control-label" for="sectorName">Название сектора
                            </label>

                            <div class="my-controls">
                                <input type="text" id="sectorName" name="sectorName" required
                                       pattern=[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}"
                                       title="Не пустое, не начинатся с пробела, до 10 знаков">
                            </div>
                        </div>

                    </td>
                    <td>
                        <div class="control-group ">
                            <label class="my-control-label" for="maxRows">Максимальное количество рядов
                            </label>

                            <div class="my-controls">
                                <input type="text" id="maxRows" name="maxRows" required pattern="[1-9][0-9]{0,1}"
                                       title="В интервале [1-99]">
                            </div>
                        </div>

                    </td>
                    <td>
                        <div class="control-group ">
                            <label class="my-control-label" for="maxSeats">Максимальное количество мест
                            </label>

                            <div class="my-controls">
                                <input type="text" id="maxSeats" name="maxSeats" required pattern="[1-9][0-9]{0,1}"
                                       title="В интервале [1-99]">
                            </div>
                        </div>

                    </td>
                    <td>
                        <div class="control-group ">
                            <label class="my-control-label" for="newPrice">Цена
                            </label>

                            <div class="my-controls">
                                <input type="text" id="newPrice" name="newPrice" required
                                       pattern="\d{0,5}(\.\d{0,2}){0,1}"
                                       title="В интервале [0-99999] до двух знаков после запятой">
                            </div>
                        </div>

                    </td>
                <tr>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-danger btn-md" data-dismiss="modal">Отмена</button>
                    </div>
                    <div class="col-md-2 column">
                        <c:if test="${empty eventEdit.id}">
                        <form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post"></c:if>
                            <c:if test="${not empty eventEdit.id}">
                            <form action="${pageContext.request.contextPath}/AddEditEvent/editEventNow.do"
                                  method="post"></c:if>
                                <input type="hidden" name="sectorName" value="${sectorName}">
                                <input type="hidden" name="maxRows" value="${maxRows}">
                                <input type="hidden" name="maxSeats" value="${maxSeats}">
                                <input type="hidden" name="newPrice" value="${newPrice}">
                                <input type="hidden" name="addSector" value="addSector">
                                <input type="hidden" name="dateEvent"
                                       value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
                                <input type="hidden" name="inputTime" value="${eventTime}">
                                <input type="hidden" name="description" value="${eventDescriptions}">
                                <input type="hidden" name="timeRemoveBooking" value="${eventBookingTimeOut}">
                                <input type="hidden" name="eventEditHidden" value="${eventEdit.id}">
                                <input type="hidden" name="allSectors" value="${allSectors}">
                                <button type="submit" name="addSector" value="" id="addSector"
                                        class="btn btn-info btn-lg">+ Добавить сектор
                                </button>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
<%@include file="footer.jsp" %>
</html>
