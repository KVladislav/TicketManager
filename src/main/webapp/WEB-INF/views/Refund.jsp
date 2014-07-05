<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="ru">
<body>
    <div class="Refund">
    <center>
         <br>
            <h3 class="panel-heading" style=" color:Blue" >Возврат билета</h3>
               <form action="${pageContext.request.contextPath}/Refund/Refund.do" method="post">
                  <br><br>
                  <h4> Поиск по номеру билета(ID)</h4>
                  <td>
                       <div>
                            <input type="text" id="ticketId" name="ticketId" />
                       </div>
                  </td>

                   <button type="submit" name="action" class="btn btn-primary">Delete</button>
               </form>
       </center>
    </div>
</body>
</html>

