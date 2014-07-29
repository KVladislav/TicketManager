<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <title>Управление мероприятиями</title>

    <!-- Bootstrap -->
    <%--  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
      <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" rel="stylesheet" media="screen">
      <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
      <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

      <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" media="screen" rel="stylesheet"
            type="text/css">
      <script type="text/javascript"
              src='<c:url value="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" />'></script>
      <script type="text/javascript"
              src='<c:url value="${pageContext.request.contextPath}/resources/js/bootstrap.js" />'></script>


      <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
              type="text/javascript"></script>
      <script type="text/javascript">
          $(document).ready(function () {
              $('#my-select').multiSelect()
          });
      </script>
      <%--
      <script type="text/javascript">
          function confirmAction(event) {
              return confirm("Вы действительно хотите удалить событие " + event + " ?")
          }
      </script>
--%>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".deleteEvent").click(function () {
                var data_id = ''
                var data_name = ''
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                if (typeof $(this).data('name') !== 'undefined') {
                    data_name = $(this).data('name');
                }
                $('#eventId').val(data_id);
                $('#eventName').val(data_name);
            })
        });
    </script>
    <%--
<pre>
       <script type="text/javascript">
           document.write(data_name);
       </script>
    </pre>
--%>
</head>


<div class="panel-heading text-info" style="text-align:center;"><b>
    <a href="/AddEditEvent/NewEvent.do" role="button" class="btn btn-primary" data-toggle="modal">Создание нового
        мероприятия</a>
</b></div>

<c:if test="${eventsErrorMessage!=null}">
    <div class="alert alert-error" style="color: red" class="panel-heading text-info" style="text-align:center;">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <h4 class="panel-heading text-info" style="text-align:center;">Ошибка! Это мероприятие удалить нельзя, так как
            на него уже куплены билеты!</h4>
    </div>
</c:if>

<h1>
    <caption><h1 class="panel-heading text-info" style="text-align:center;">Список мероприятий</h1></caption>
</h1>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%; margin-bottom: 50px;">
    <div class="table responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th width="5%">#</th>
                <th>Наименование</th>
                <th>Дата</th>
                <th width="5%"></th>
                <th width="5%"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${events}" var="evnt" varStatus="theCount">
                <tr>
                    <td style="vertical-align: middle;">${theCount.count}</td>
                    <td style="vertical-align: middle;">${evnt.description}</td>
                    <td style="vertical-align: middle;"><fmt:formatDate value="${evnt.date}" pattern="dd.MM.yyyy H:mm"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/Edit.do" method="post">
                            <a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Редактировать">
                                <button type="submit" name="evnt" value="${evnt.id}" class="btn btn-default">
                                    <span class="glyphicon glyphicon-edit"></span></button>
                            </a>
                        </form>
                    </td>
                    <td>
                        <a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Удалить">
                            <button type="button" class="btn btn-default btn-md deleteEvent" data-id="${evnt.id}"
                                    data-name="${evnt.description}"
                                    data-toggle="modal" data-target="#Cancel">
                                <span class="glyphicon glyphicon-trash"></span></button>
                        </a>
                        <script>
                            $("a.my-tool-tip").tooltip();
                        </script>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
 </div>

<div class="modal" id="Cancel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                <h4 id="myModalLabel">Вопрос!</h4>
            </div>
            <div class="modal-body">
                <h4 value="$('#eventName')">Подтвердите удаление мероприятия !!!</h4>


            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Отменить</button>
                    </div>
                    <div class="col-md-2 column">
                        <form action="${pageContext.request.contextPath}/Events/setDelete.do" method="post">
                            <%--       onSubmit="return confirmAction('${evnt.description}')"   --%>
                            <button type="submit" name="eventId" value="eventId" class="btn btn-danger btn-md"
                                    id="eventId">Удалить
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