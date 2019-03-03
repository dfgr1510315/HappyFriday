<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>课程管理</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Myclass.css">
    <link rel="stylesheet" type="text/css" href="../webuploader-0.1.5/css/webuploader.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../webuploader-0.1.5/jekyll/js/webuploader.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Myclass.js"></script>
    <script type="text/javascript" src="../JS/drag.js"></script>
</head>
<style type="text/css">
    body.modal-open {
        overflow-y: auto !important;
        padding-right: 0!important;
    }
</style>

<body onload="checkCookie();ifActive();addAction(3);addHref();getHTML()">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;margin-top:30px;height: 450px">
    <div style="background-size: cover;height: 148px;margin-top: -21px">
        <jsp:include page="ui_box.jsp"/>
        <div style="width: 80%;margin: auto">
            <jsp:include page="course_manag_nav.jsp"/>
            <div class="container_right">

            </div>
        </div>
    </div>
</div>

</body>

</html>
