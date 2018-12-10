<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/10 0010
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在教课程</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <style type="text/css">
        body{
            background-color: #f8fafc;
        }
    </style>

</head>
<body onload="checkCookie();ifActive();addClass(3)">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;margin: 80px auto auto;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">

        </div>
    </div>
</div>

</body>
</html>
