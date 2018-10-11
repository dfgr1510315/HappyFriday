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
</head>
<body>
<form method="post" action="uploadsec" enctype="multipart/form-data">
    选择文件:
    <input type="file" name="uploadFile">
    <br/>
    <input type="submit" value="上传">
</form>
</body>
</html>
