<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>
<center><form class="form-search">
    Поиск билета
    <div class="input-append">
        <input type="text" class="span2 search-query">
        <button type="submit" class="btn">Search</button>
    </div>
</form>
</center>

</body>
</html>
