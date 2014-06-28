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

<head>
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.js">
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js">
    </script>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        $(document).on("hover", ".cont", function () {
            $(this).children(".overlay").fadeIn("fast");
        }, function () {
            $(this).children(".overlay").fadeOut("fast");
        });
    </script>


</head>

<div class="panel-heading" style="text-align:center;"><b>The new event</b></div>
<center>


    <div class="well">
        <label class="my-control-label" for="dateEvent">Date of event</label>

        <div id="datetimepicker1" class="input-append date">
            <input data-format="dd-MM-yyyy" type="text" name="date" id="dateEvent"></input>
    <span class="add-on">
      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
      </i>
    </span>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $('#datetimepicker1').datetimepicker({
                language: 'pt-BR'
            });
        });
    </script>


    <div class="well">
        <label class="my-control-label" for="timeEvent">Time of event</label>

        <div id="datetimepicker3" class="input-append">
            <input data-format="hh:mm" type="text" name="time" id="timeEvent"></input>
    <span class="add-on">
      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
      </i>
    </span>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $('#datetimepicker3').datetimepicker({
                pickDate: false
            });
        });
    </script>


    <div class="control-group">
        <label class="my-control-label" for="outputName">Name</label>

        <div class="my-controls">
            <textarea rows="1" id="outputName" name="name"></textarea>
        </div>
    </div>


    <div class="control-group">
        <label class="my-control-label" for="outputDescription">Description</label>

        <div class="my-controls">
            <textarea rows="5" id="outputDescription" name="description"></textarea>
        </div>
    </div>


    <div class="well">
        <label class="my-control-label" for="timeRemoveBooking">Removing the booking</label>

        <div id="datetimepicker3" class="input-append">
            <input data-format="hh:mm" type="text" name="timeRemoveBooking" id="timeRemoveBooking"></input>
    <span class="add-on">
      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
      </i>
            </span>
        </div>
        <img src="${pageContext.request.contextPath}/resources/img/Question.jpg"
             alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
             title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
    </div>
    <script type="text/javascript">
        $(function () {
            $('#datetimepicker3').datetimepicker({
                pickDate: false
            });
        });
    </script>


    <div class="panel-body" style="padding:20px; width:50%; margin-left: 10%">
        <div class="table responsive">
            <table class="table table-bordered" style="display:block;height:200px;overflow:auto;">
                <thead>
                <tr>
                    <th>Sector</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="iter" begin="1" end="27">

                    <tr>
                        <td>
                            <div>
                                    ${iter} <br/>
                            </div>
                        </td>
                        <td>
                            <div>
                                <input type="text" id="inputPrice" name="price">
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    &MediumSpace;
    &MediumSpace;
    <button type="submit" name="action" value="saveRec" class="btn btn-primary">Save</button>
    &MediumSpace;
    &MediumSpace;
</center>

