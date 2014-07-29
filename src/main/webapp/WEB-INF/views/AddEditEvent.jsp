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


    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">--%>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/resources/css/bootstrap.css"></script>--%>
    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css">
    <%--<script type="text/javascript"--%>
            <%--src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery.js" />'></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>--%>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>

    <script src="http://malsup.github.com/jquery.form.js"></script>
    <%--<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css" rel="stylesheet">--%>
    <%--<link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.0/css/bootstrap-responsive.css"--%>
          <%--rel="stylesheet">--%>
    <%--<link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.0/css/bootstrap.css" rel="stylesheet">--%>

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
    <caption><h1 class="panel-heading text-info">Создание нового мероприятия</h1></caption>
</h1>
<c:if test="${eventErrorMessage!=null}">

    <h4 style="color: red"> ${eventErrorMessage}</h4>

</c:if>
<form action="${pageContext.request.contextPath}/AddEditEvent/addEvent.do" method="post">
</c:if>
<c:if test="${not empty eventEdit.id}">
<h1>
    <caption><h1 class="panel-heading text-info">Редактирование мероприятия</h1></caption>
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
                    <div class="control-group">
                        <input type="text" name="dateEvent" class="form-control" readonly id="dateEvent" style="width: 420px"
                               value="<fmt:formatDate value='${dateEvent}' type='date' />"/>

                        <p>
                            <script type="text/javascript"> calendar.set("dateEvent");</script>
                        </p>
                    </div>
            </tr>
            <tr>
                <label class="my-control-label  text-info" for="eventTime" style="margin-top: 20px;"> Время мероприятия </label>

                <div class="control-group text-info">
                    <select class="form-control" name="eventTime" id="eventTime" style="width: 420px">

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
    <div class="control-group" style="margin-top: 20px;">
        <label class="my-control-label text-info" for="eventDescriptions"> Наименование </label>
        <div class="my-controls">
            <input class="form-control" style="resize: none; width: 420px" type="text" size="20" id="eventDescriptions" maxlength="50" data-min-length=1
                   name="eventDescriptions"
                   required pattern="[A-Za-zА-Яа-яЁё0-9][\s\S]{0,49}"
                   title="Не пустое,не начинается с пробела, начинается с буквы или цифры, до 50 знаков"
                   value="${eventDescriptions}"/>
        </div>
    </div>
</tr>
<tr>
    <div class="control-group" style="margin-top: 20px;">
        <label class="my-control-label text-info" for="eventBookingTimeOut"> Установка времени удаления брони в
            минутах

           <img src="${pageContext.request.contextPath}/resources/img/Question.png"
                onMouseOver="helpBox('Подсказка', 'Поле позволяет установить время до начала ' +
                 'мероприятия, по достижению которого бронь полностью снимается (в минутах, максимум три цифры)')"
                onMouseOut="helpBox()">

        </label>

        <div class="my-controls">
            <div>
            <input class= "form-control span2" style="width: 420px;" type="text" id="eventBookingTimeOut" size="20" maxlength="3" name="eventBookingTimeOut"
                   value="${eventBookingTimeOut}"
                   required pattern="[1-9]\d{0,2}?" title="Только целое положительное число от одной до трех цифр!">
            <!-- это элемент который вызывает подсказку при наведении курсора мыши на нее, и скрывает, когда курсор убирается-->
            </div>

            <!--Это сам слой, который является всплывающей посказкой, состоит из трех дивов, общий контейнер, тайтл и текст-->
            <div id="help" class="helpBox" style="display:none; position:absolute;"><p id="helpTitle" class="helpTitle">
                Поле позволяет установить время до начала мероприятия, по достижению которого бронь
                полностью снимается (в минутах, максимум три цифры)</p>
                <p id="helpText" class="helpText">Help text</p></div>
        </div>
    </div>
</tr>

</div>
</div>
</td>
</table>

<c:if test="${sectorErrorMessage!=null}">
    <div class="alert alert-error" style="color: red" class="panel-heading text-info" style="text-align:center;">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <h4 class="panel-heading text-info" style="text-align:center;">"Этот сектор удалить нельзя, так как на
            мероприятие уже куплены билеты!"</h4>
    </div>
</c:if>


<div class="panel-body" style="padding:30px; width:40%;">
    <div class="table responsive">
        <table class="table table-hover" style="text-align:center;">
        <thead>
            <tr>
                <th>Сектор</th>
                <th>Цена</th>
                <th width="5%">Удалить</th>
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
                                class="btn btn-default btn-xm">
                            <span class="glyphicon glyphicon-trash"></span></button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div class="form-group">
            <div class="col-md-3 column">
                <a class="btn btn-danger btn-md" href="${pageContext.request.contextPath}/Events/Cancel.do"
                   role="button">Отменить</a>
            </div>
            <div class="col-md-3 column">
                <button type="submit" name="action" value="save" class="btn btn-primary btn-md">Сохранить</button>
            </div>

            <div class="col-md-3 column">
                <input type="hidden" name="dateEvent"
                       value="<fmt:formatDate value='${dateEvent}' type='date'/>"/>
                <input type="hidden" name="eventTime" value="${eventTime}">
                <input type="hidden" name="eventDescriptions" value="${eventDescriptions}">
                <input type="hidden" name="eventBookingTimeOut" value="${eventBookingTimeOut}">

                <a href="${pageContext.request.contextPath}/NewSector/NewSector.do" class="btn btn-info btn-md"
                   title="Перед заполнением формы можно добавить недостающие сектора!" role="button">+ Добавить
                    сектор</a>
            </div>

        </div>
    </div>
</div>
</form>
</center>


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

</body>
<%@include file="footer.jsp" %>
</html>
