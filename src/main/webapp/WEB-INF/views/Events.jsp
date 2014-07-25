<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
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
    <script>
        $(document).ready(function () {
            $(".deleteEvent").click(function () {
                var data_id = '';
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                $('#eventId').val(data_id);
            })
        });
    </script>
</head>


&MediumSpace;
&MediumSpace;
<div class="panel-heading" style="text-align:center;"><b>
    <a href="/AddEditEvent/NewEvent.do" role="button" class="btn btn-success btn-lg" data-toggle="modal">Создание нового
        мероприятия</a>
</b></div>

&MediumSpace;
&MediumSpace;
<h1>
    <div class="panel-heading" style="text-align:center;"><b>Список мероприятий</b></div>
</h1>
<div class="panel-body" style="padding:15px; width:50%; margin-left: 25%; margin-bottom: 50px;">
    <div class="table responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>##</th>
                <th>Наименование</th>
                <th>Дата</th>
                <th>Редактировать</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${events}" var="evnt" varStatus="theCount">
                <tr>
                    <td>${theCount.count}</td>
                    <td>${evnt.description}</td>
                    <td><fmt:formatDate value="${evnt.date}" pattern="dd.MM.yyyy H:mm"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Events/Edit.do" method="post">
                            <a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Редактировать">
                                <button type="submit" name="evnt" value="${evnt.id}" class="btn">Редактировать</button>
                                <span class="glyphicon glyphicon-edit"></span>
                        </form>
                    </td>
                    <td>
                        <a data-toggle="tooltip" class="my-tool-tip" data-placement="top" title="Удалить">
                            <button type="button" class="btn btn-default btn-md deleteEvent" data-id="${evnt.id}"
                                    data-toggle="modal" data-target="#Cancel">Удалить
                            </button>
                            <span class="glyphicon glyphicon-trash"></span>
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
    &MediumSpace;
    &MediumSpace;
</div>

<div class="modal" id="Cancel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="myModalLabel">Вопрос!</h4>
            </div>
            <div class="modal-body">
                <h4>Подтвердите удаление мероприятия!!!</h4>
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