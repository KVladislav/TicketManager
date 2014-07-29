<%--suppress ALL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<head>
    <title>Управление операторами</title>
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

<body>
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
                    <th>
                        <a data-placement="right" data-toggle="tooltip" type="button"
                           href="${pageContext.request.contextPath}/Operators/NewOperator.do"
                           class="btn btn-md my-tool-tip" title="Создать нового оператора">
                            +<span class="glyphicon glyphicon-user"></span></a>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${operators}" var="operator">
                    <tr>
                        <td width="30%" style="vertical-align: middle;">${operator.name}</td>
                        <td width="30%" style="vertical-align: middle;">${operator.surname}</td>
                        <td width="30%" style="vertical-align: middle;">${operator.login}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/Operators/OperatorEdit.do"
                                  method="POST">
                                <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                                   title="Изменить">
                                    <button type="submit" name="operatorId" value="${operator.id}"
                                            class="btn btn-default btn-md">
                                        <span class="glyphicon glyphicon-edit"></span></button>
                                </a>
                            </form>
                        </td>
                        <td>
                            <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                               title="Удалить">
                                <button type="button" class="btn btn-default btn-md deleteOperator"
                                        data-id="${operator.id}"
                                        data-toggle="modal" data-target="#operatorDeleteConfirmation">
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
</body>
<%@include file="footer.jsp" %>