<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/11
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body onload="checkCookie()">

<style type="text/css">
    body {
        background: #fffef5;
        overflow: auto;
    }
</style>

<jsp:include page="navigation.jsp"/>
<jsp:include page="LoginPC.jsp" />

<form method="post" action="uploadsec" enctype="multipart/form-data" style="margin-top: 100px">
    选择文件:
    <input type="file" name="uploadFile">
    <br/>
    <input type="submit" value="上传">
</form>
</body>
</html>
