<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/8
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <title>个人中心</title>
    <style type="text/css">
        body {
            background: #fffef5;
            overflow: auto;
        }
    </style>
</head>
<body>

<div  style="position:fixed;top:0;left:0;right:0;width: 100% ;padding-left: 40px;padding-top: 9px;padding-bottom: 9px;background-color: white; z-index: 6;">
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link" href="#">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Resources.jsp">文档</a>
        </li>
        <li class="nav-item dropdown" id="personalCenter" >
            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="" id="showname"><%=request.getParameter("username")%></a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">个人中心</a>
                <a class="dropdown-item" href="#">...</a>
                <a class="dropdown-item" href="#">...</a>
                <a class="dropdown-item" href="">退出登录</a>
            </div>
        </li>
    </ul>
</div>

</body>
</html>
