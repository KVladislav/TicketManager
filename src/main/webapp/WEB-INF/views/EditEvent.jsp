<%--
  Created by IntelliJ IDEA.
  User: Lora
  Date: 30.06.2014
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
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

<div class="panel-heading" style="text-align:center;"><b>Edit event</b></div>
<center>


<div class="well">
    <label class="my-control-label" for="dateEvent">Date of event</label>

    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen"
          href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">
    </head>
    <body>
    <div id="datetimepicker" class="input-append date">
        <input type="text" name="dateEvent" id="dateEvent" value="${event.getDate()}">
      <span class="add-on">
        <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
      </span>
    </div>
    <script type="text/javascript"
            src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min.js">
    </script>
    <script type="text/javascript"
            src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js">
    </script>
    <script type="text/javascript"
            src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
    </script>
    <script type="text/javascript">
        $('#datetimepicker').datetimepicker({
            format: 'dd-MM-yyyy hh:mm:ss',
            language: 'pt-BR'
        });
    </script>
</div>

<div class="control-group">
    <label class="my-control-label" for="outputName">Name</label>

    <div class="my-controls">
        <textarea rows="1" id="outputName" name="name" value="${event.getDescription()}"></textarea>
    </div>
</div>


<div class="control-group">
    <label class="my-control-label" for="outputDescription">Description</label>

    <div class="my-controls">
        <textarea rows="5" id="outputDescription" name="description" value="${event.getDescription()}"></textarea>
    </div>
</div>

<div class="control-group">
    <label class="my-control-label" for="timeRemoveBooking">Removing the booking</label>

    <div class="my-controls">
        <textarea rows="1" id="timeRemoveBooking" name="time" value="${event.getBookingTimeOut()}"></textarea>
        <img src="${pageContext.request.contextPath}/resources/img/Question.png"
             alt="Поле позволяет установить время, по истечении которого бронь полностью снимается"
             title="Поле позволяет установить время, по истечении которого бронь полностью снимается"/>
    </div>
</div>

<div class="panel-body" style="padding:20px; width:50%; margin-left: 10%">
<div class="table responsive">
<table class="table table-bordered" style="display:block;height:400px;overflow:auto;">
<thead>
<tr>
    <th>Sector</th>
    <th>Price</th>
</tr>
</thead>
`
<tbody>
<%--  <c:forEach var="iter" begin="1" end="27"> --%>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector1" name="sector1" value="1">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice1" name="price1" value="${sectors.get(0).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector2" name="sector2" value="2">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice2" name="price2" value="${sectors.get(1).getPrice()}">
        </div>
    </td>
</tr>

<tr>
    <td>
        <div>
            <input type="text" id="inputSector3" name="sector3" value="3">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice3" name="price3" value="${sectors.get(2).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector4" name="sector4" value="4">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice4" name="price4" value="${sectors.get(3).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector5" name="sector5" value="4">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice5" name="price5" value="${sectors.get(4).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector6" name="sector6" value="6">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice6" name="price6" value="${sectors.get(5).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector7" name="sector7" value="7">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice7" name="price7" value="${sectors.get(6).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector8" name="sector8" value="8">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice8" name="price8" value="${sectors.get(7).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector9" name="sector9" value="9">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice9" name="price9" value="${sectors.get(8).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector10" name="sector10" value="10">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice10" name="price10" value="${sectors.get(9).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector11" name="sector11" value="11">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice11" name="price11" value="${sectors.get(10).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector12" name="sector12" value="12">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice12" name="price12" value="${sectors.get(11).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector13" name="sector13" value="13">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice13" name="price13" value="${sectors.get(12).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector14" name="sector14" value="14">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice14" name="price14" value="${sectors.get(13).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector15" name="sector15" value="15">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice15" name="price15" value="${sectors.get(14).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector16" name="sector16" value="16">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice16" name="price16"  value="${sectors.get(15).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector17" name="sector17" value="17">
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice17" name="price17" value="${sectors.get(16).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector18" name="sector18" value=18>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice18" name="price18" value="${sectors.get(17).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector19" name="sector19" value=19>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice19" name="price19" value="${sectors.get(18).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector20" name="sector20" value=20>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice20" name="price20" value="${sectors.get(19).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector21" name="sector21" value=21>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice21" name="price21" value="${sectors.get(20).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector22" name="sector22" value=22>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice22" name="price22" value="${sectors.get(21).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector23" name="sector23" value=23>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice23" name="price23" value="${sectors.get(22).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector24" name="sector24" value=24>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice24" name="price24" value="${sectors.get(23).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector25" name="sector25" value=25>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice25" name="price25" value="${sectors.get(24).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector26" name="sector26" value=26>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice26" name="price26" value="${sectors.get(25).getPrice()}">
        </div>
    </td>
</tr>
<tr>
    <td>
        <div>
            <input type="text" id="inputSector27" name="sector27" value=27>
        </div>
    </td>
    <td>
        <div>
            <input type="text" id="inputPrice27" name="price27" value="${sectors.get(26).getPrice()}">
        </div>
    </td>
</tr>

</tbody>
</table>
</div>
</div>

&MediumSpace;
&MediumSpace;
<button type="submit" name="action" class="btn btn-primary">Save</button>
&MediumSpace;
&MediumSpace;

</form>

</center>
