<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>


    <div class="panel-heading"  style="text-align:center;"><b>List of events</b></div>
    <div class="panel-body" style="padding:15px; width:50%; margin-left: 25%">
            <div class="table responsive">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>##</th>
                        <th>Name of event</th>
                        <th>Date</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${events}" var="evnt" varStatus="theCount">
                        <tr>
                            <td>${theCount.count}</td>
                            <td>${evnt.description}</td>
                            <td><fmt:formatDate value="${evnt.date}" pattern="d.MM.yyyy H:mm:ss"/></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Events/Edit.do" method="post">
                                    <input type="hidden" name="eventId" value="${event.id}">
                                <button type="submit" name="action" value="edit" class="btn">Edit</button>
                                    </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Events/setDelete.do" method="post">
                                    <input type="hidden" name="eventId" value="${event.id}">
                                <button type="submit" name="evnt" value="${evnt.id}" class="btn">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            &MediumSpace;
            &MediumSpace;
            <div class="container">
                <div class="row">

                   <center> <div class="col-md-8 col-md-offset-3">
                        <button type="submit" name="Order"class="btn btn-success btn-lg">Создать новое мероприятие</button>
                    </div>
                   </center>
                </div>
            </div>
        </form>
    </div>

    <div class="clearfix"/>

    </body>
</center>
</html>

