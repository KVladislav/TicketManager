<%@include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Фото галерея на jQuery | pcvector.net</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" media="all" />
    <link rel="shortcut icon" href="/favicon.ico" />
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.quicksand.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.easing.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.prettyPhoto.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/resources/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
</head>

<body>


<div class="wrapper">

    <div class="portfolio-content">
        <h1 class="title-page">Java-Art Team</h1>


        <ul class="portfolio-area">
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic1.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic1.jpg" />
                    </a>
                </span>
                </div>
            </li>
            <li >
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic2.jpg" rel="prettyPhoto[gallery]" >
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic2.jpg"/>
                    </a>
                </span>
                </div>
            </li>
            <li >
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic3.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic3.jpg"/>
                    </a>
                </span>
                </div>
            </li>
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic4.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic4.jpg"/>
                    </a>
                </span>
                 </div>
            </li>
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic5.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic5.jpg"/>
                    </a>
                </span>
                </div>
            </li>
            <li >
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic6.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic6.jpg"/>
                    </a>
                </span>
                </div>
            </li>
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic7.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic7.jpg"/>
                    </a>
                </span>
                </div>
            </li>
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic8.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic8.jpg"/>
                    </a>
                </span>

                </div>
            </li>
            <li>
                <div>
                <span class="image-block">
					<a class="image-zoom" href="${pageContext.request.contextPath}/resources/images/big/pic9.jpg" rel="prettyPhoto[gallery]">
                        <img width="100%" height="100%" src="${pageContext.request.contextPath}/resources/images/big/pic9.jpg"/>
                    </a>
                </span>
                </div>
            </li>





        </ul>
    </div>


</div><!-- end wrapper -->


</body>

<%@include file="footer.jsp" %>
</html>