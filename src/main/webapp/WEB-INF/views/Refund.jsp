<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>
<center>
    <br><br><br><br><br>
    <b> Поиск по номеру/фио</b>

<form class="form-search">

    <div class="input-append">
        <input type="text" class="span3 search-query">
        <button type="submit" class="btn">Поиск</button>
    </div>
    </form>

</center>
</body>
</html>
