<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<head>
    <script>
        $(document).ready(function () {
            $(".deleteOperator").click(function () {
                var data_id = '';
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                $('#operatorId').val(data_id);
            })
        });
    </script>

</head>


<div class="container" style="margin-bottom: 50px">
    <div class="row clearfix">
        <div class="col-md-3 column">
        </div>
        <div class="col-md-6 column">

            <table class="table table-hover">
                <caption><h1 class="panel-heading text-info">Список операторов</h1></caption>
                <thead>
                <tr>
                    <th width="30%">Имя</th>
                    <th width="30%">Фамилия</th>
                    <th width="30%">Логин</th>
                    <th></th>
                    <th><a href="/NewOperator/NewOperator.do" role="button" class="btn btn-sm"
                           data-toggle="modal" title="Создать нового оператора">+<span class="glyphicon glyphicon-user"></span></a></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${operators}" var="operator">
                    <tr>
                        <td width="30%">${operator.name}</td>
                        <td width="30%">${operator.surname}</td>
                        <td width="30%">${operator.login}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/EditOperator/OperatorsEditGet.do"
                                  method="get">
                                <button type="submit" name="operatorId" value="${operator.id}"
                                        class="btn btn-default btn-xs" title="Изменить">
                                    <span class="glyphicon glyphicon-edit"></span></button>
                            </form>
                        </td>
                        <td>
                            <button type="button" class="btn btn-default btn-xs deleteOperator" data-id="${operator.id}"
                                    data-toggle="modal" data-target="#operatorDeleteConfirmation" title="Удалить">
                                <span class="glyphicon glyphicon-trash"></span></button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
    <div class="col-md-3 column"></div>
</div>

<!-- Modal operatorDeleteConfirmations-->
<div class="modal" id="operatorDeleteConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Подтвердите удаление оператора</h4>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Отмена</button>
                    </div>
                    <div class="col-md-2 column">
                        <form action="${pageContext.request.contextPath}/Operators/OperatorsDelete.do"
                              method="post" id="submitDelete">
                            <button type="submit" name="operatorId" value="" id="operatorId"
                                    class="btn btn-danger btn-md">Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>