<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/10
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body >
<div  style="position:fixed;top:0;left:0;right:0;width: 100% ;padding-left: 40px;padding-top: 9px;padding-bottom: 9px;background-color: white; z-index: 6;">
    <ul class="nav nav-pills" style="display: inline">
        <li class="nav-item">
            <a class="nav-link" href="homepage.jsp" style="float: left;">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Resources.jsp" style="float:left;">文档</a>
        </li>
        <li class="nav-item" id="loginButton">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" href="#" style="float: right">登录</a>
        </li>
        <li class="nav-item dropdown" id="personalCenter" style="display: none;float: right">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="" id="showname"></a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#" onclick="PerCenter()">个人中心</a>
                <a class="dropdown-item" href="upload.jsp">上传资源</a>
                <a class="dropdown-item" href="#">账号设置</a>
                <a class="dropdown-item" href="#">我的消息</a>
                <a class="dropdown-item" href="" onclick="deleteCookie()">退出登录</a>
            </div>
        </li>
    </ul>
</div>
<script>
    function ifActive() {
        var obj = document.getElementsByTagName("body"); //获取当前body的id
        /*var obj = document.getElementById(id[0].getAttribute("id")+'');*/
        var id = obj[0].getAttribute("id");
        switch (id) {
            case id="homepage":
                document.getElementsByTagName("a")[0].className += ' active';
                break;
            case id="resources":
                document.getElementsByTagName("a")[1].className += ' active';
                break;
        }
    }
</script>
</body>
</html>
