<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru"
      xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>О нас</title>

    <link href="${pageContext.request.contextPath}/resources/css/gal/css.css" media="screen" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/css/gal/style.css" class="piro_style" media="screen" title="white" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/gal_jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pirobox.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $().piroBox({
                my_speed: 400,
                bg_alpha: 0.3,
                slideShow : true,
                slideSpeed : 3,
                close_all : '.piro_close,.piro_overlay'//

            });
        });
    </script>
</head>
<body>
<body>
<%--<img style="margin-left: 22%" src="${pageContext.request.contextPath}/resources/images/ogo.jpg"  />--%>
<div  style="float:left; margin-left: 30%; width:700px; display:block;">
    <center style="font-size: 100px">JavaArt Team</center>
        <table>
        <tr>
            <td>
                <center>
                    <a href="${pageContext.request.contextPath}/resources/images/1.jpg" class="pirobox_gall" title="Владислав Карпенко">
                    <img width="200" height="200" src="${pageContext.request.contextPath}/resources/images/1.jpg"/></a>
                    </center>
            </td>


            <td style="padding: 11px">
                <center>
                <a href="${pageContext.request.contextPath}/resources/images/2.jpg" class="pirobox_gall" title="Юрий Попов">
                    <img width="145" height="200" src="${pageContext.request.contextPath}/resources/images/2.jpg"/></a>
                </center>
            </td>

            <td>
                <center>
                <a  href="${pageContext.request.contextPath}/resources/images/3.jpg" class="pirobox_gall" title="Екатерина Крылова">
                    <img width="267" height="200" src="${pageContext.request.contextPath}/resources/images/3.jpg"/></a>
                </center>
            </td>

        </tr>
        <tr>
            <td>
                <center>
                <a  href="${pageContext.request.contextPath}/resources/images/5.jpg" class="pirobox_gall" title="Анна Стоянова">
                    <img width="228" height="200" src="${pageContext.request.contextPath}/resources/images/5.jpg"/></a>
                </center>
            </td>

            <td style="padding: 11px">
                <center>
                <a  href="${pageContext.request.contextPath}/resources/images/4.jpg" class="pirobox_gall" title="Лариса Назаренко">
                    <img width="150" height="200" src="${pageContext.request.contextPath}/resources/images/4.jpg"/></a>
                </center>
            </td>

            <td>
                <center>
                <a  href="${pageContext.request.contextPath}/resources/images/6.jpg" class="pirobox_gall" title="Виталий Сипличук">
                    <img width="267" height="200" src="${pageContext.request.contextPath}/resources/images/6.jpg"/></a>
                </center>
            </td>
        </tr>
            <tr>
                <td>
                    <center>
                    <a  href="${pageContext.request.contextPath}/resources/images/7.jpg" class="pirobox_gall" title="Сергей Прокопов">
                        <img width="267" height="200" src="${pageContext.request.contextPath}/resources/images/7.jpg"/></a>
                    </center>
                </td>

                <td style="padding: 11px">
                    <center>

                    <a  href="${pageContext.request.contextPath}/resources/images/8.jpg" class="pirobox_gall" title="Антон Галенда">
                        <img width="133" height="200" src="${pageContext.request.contextPath}/resources/images/8.jpg"/></a>

                    </center>
                </td>

                <td>
               <center>
                    <a  href="${pageContext.request.contextPath}/resources/images/9.jpg" class="pirobox_gall" title="Александр Трибушный">
                        <img width="182" height="200" src="${pageContext.request.contextPath}/resources/images/9.jpg"/></a>

               </center>
                </td>
            </tr>
    </table>
<div>

    <div class="demo">
        <a  href="${pageContext.request.contextPath}/resources/images/logo.jpg" class="pirobox_gall" title="JavaArt Team">
            <img width="0" height="0" src="${pageContext.request.contextPath}/resources/images/logo.jpg"/></a>
    </div>


<%--<div style="float:left; width:90%; display:block; margin:50px 0 0 5px;">--%>
    <%--&lt;%&ndash;<h2><a href="/index">Домой</a></h2>&ndash;%&gt;--%>
<%--</div>--%>

</body>

<%@include file="footer.jsp" %>
</html>
