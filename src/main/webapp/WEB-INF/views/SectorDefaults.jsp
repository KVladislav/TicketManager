<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Настройка секторов</title>
    <script>
        $(document).ready(function () {
            $(".deleteSector").click(function () {
                var data_id = '';
                if (typeof $(this).data('id') !== 'undefined') {
                    data_id = $(this).data('id');
                }
                $('#sectorDefaultsId').val(data_id);
            })
        });
    </script>
</head>
<body>
<div class="container" style="margin-bottom: 50px">
    <div class="row clearfix">
        <div class="col-md-2 column">
        </div>
        <div class="col-md-8 column">
            <c:if test="${errorMessage!=null}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:if>
            <table class="table table-hover">
                <caption>
                    <h1 class="panel-heading text-info">Настройка секторов стадиона
                    </h1></caption>
                <thead>
                <tr>
                    <th width="30%">Название</th>
                    <th width="15%">Рядов</th>
                    <th width="15%">Мест</th>
                    <th width="20%">Цена</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${sectorDefaultsList}" var="sector">
                    <form name="sectorDefaults" action="${pageContext.request.contextPath}/Sectors/Modify.do"
                          method="post" role="form">
                        <tr>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="10" type="text"
                                       name="sectorName" required
                                       placeholder="Название сектора" value="${sector.sectorName}"
                                       pattern="[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\s]{0,9}"
                                       title="Не пустое, не начинается с пробела, только буквы и цифры, до 10 знаков">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="2" name="maxRows"
                                       required
                                       placeholder="Рядов" value="${sector.maxRows}"
                                       pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" maxlength="2" name="maxSeats"
                                       required
                                       placeholder="Мест" value="${sector.maxSeats}"
                                       pattern="[1-9][0-9]{0,1}" title="В интервале [1-99]">
                            </td>
                            <td>
                                <input tabindex="1" class="form-control" size="16" type="text" maxlength="8"
                                       name="defaultPrice"
                                       placeholder="Цена" value="${sector.defaultPrice}"
                                       required pattern="\d{0,5}(\.\d{0,2}){0,1}"
                                       title="В интервале [0-99999] до двух знаков после запятой">
                            </td>
                            <td></td>
                            <td>
                                <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                                   title="Сохранить">
                                    <input type="hidden" name="sectorDefaultsId" value="${sector.id}">
                                    <button tabindex="0" type="submit" name="action" value="save"
                                            class="btn btn-default btn-md">
                                        <span class="glyphicon glyphicon-floppy-disk"></span></button>
                                </a>
                            </td>
                            <td>
                                <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                                   title="Сделать копию">
                                    <button tabindex="0" type="submit" name="action" value="clone"
                                            class="btn btn-default btn-md">
                                        <span class="glyphicon glyphicon-export"></span></button>
                                </a>
                            </td>
                            <td>
                                <a data-toggle="tooltip" class="my-tool-tip" data-placement="top"
                                   title="Удалить">
                                    <button tabindex="0" type="button" class="btn btn-default btn-md deleteSector"
                                            data-id="${sector.id}"
                                            data-toggle="modal" data-target="#sectorDeleteConfirmation">
                                        <span class="glyphicon glyphicon-trash"></span></button>
                                        <%--<button type="submit" name="action" value="delete" class="btn btn-default btn-xs">--%>
                                        <%--<span class="glyphicon glyphicon-trash"></span></button>--%>
                                </a>
                                <script>
                                    $("a.my-tool-tip").tooltip();
                                </script>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-2 column"></div>
</div>

<!-- Modal sectorDeleteConfirmations-->
<div class="modal" id="sectorDeleteConfirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Внимание! </h4>
            </div>
            <div class="modal-body">
                <h4>Подтвердите удаление сектора</h4>
            </div>
            <div class="modal-footer">
                <div class="row clearfix">
                    <div class="col-md-8 column">
                        <button type="button" class="btn btn-primary btn-md" data-dismiss="modal">Отмена</button>
                    </div>
                    <div class="col-md-2 column">

                        <form action="${pageContext.request.contextPath}/Sectors/Modify.do"
                              method="post" id="submitDelete">
                            <input type="hidden" name="sectorName" value="1">
                            <input type="hidden" name="maxRows" value="1">
                            <input type="hidden" name="maxSeats" value="1">
                            <input type="hidden" name="defaultPrice" value="1">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" name="sectorDefaultsId" value="" id="sectorDefaultsId"
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
</body>
</html>

