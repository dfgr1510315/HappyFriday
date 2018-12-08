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

    <script>
        function ifActive() {
            var obj = document.getElementsByTagName("body"); //获取当前body的id
            /*var obj = document.getElementById(id[0].getAttribute("id")+'');*/
            var id = obj[0].getAttribute("id");
            switch (id) {
                case id = "homepage":
                    document.getElementsByTagName("a")[0].className += ' active';
                    break;
                case id = "resources":
                    document.getElementsByTagName("a")[1].className += ' active';
                    break;
            }
        }

        function checkCookie() {
            var user = getCookie("username");
            if (user !== "") {
                $("#loginButton").hide();
                $("#personalCenter").show();
                $("#showname").text(user);
            }
        }

     /*   function Tomanger() {

        }*/
    </script>
</head>
<body>
<div class="user_navigation">
    <ul class="nav nav-pills" style="display: inline">
        <li>
            <img src="../image/HUAS.png" style="height: 40px;width: 40px;float: left;margin-right: 21px">
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://localhost:8080/" style="float: left;">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="../HTML_JSP/Resources.jsp" style="float:left;">文档</a>
        </li>
        <li class="nav-item" id="loginButton">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" href="#" style="float: right">登录</a>
        </li>
        <li class="nav-item dropdown" id="personalCenter" style="display: none;float: right">
            <%-- <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="" id="showname"></a>--%>
            <img class="user_imag" src="../image/68296699_p0.png" style="height: 40px;width: 40px;border-radius: 50%; margin-right: 50px" >
            <%--<div class="dropdown-menu">
                <a class="dropdown-item" href="#" onclick="PerCenter()">个人中心</a>
                <a class="dropdown-item" href="upload.jsp">上传资源</a>
                <a class="dropdown-item" href="#">账号设置</a>
                <a class="dropdown-item" href="#">我的消息</a>
                <a class="dropdown-item" href="management.jsp" >后台管理</a>
                <a class="dropdown-item" href="homepage.jsp" onclick="deleteCookie()">退出登录</a>
            </div>--%>
            <div class="user_card" >
                <div style="padding: 20px;">
                    <a  href="#" onclick="PerCenter()">个人中心</a>
                    <a href="../HTML_JSP/upload.jsp">上传资源</a>
                    <a href="#">账号设置</a>
                    <a  href="#">我的消息</a>
                    <a  href="../HTML_JSP/management.jsp" >后台管理</a>
                    <a href="../HTML_JSP/homepage.jsp" onclick="deleteCookie()">退出登录</a>
                </div>
            </div>
        </li>
    </ul>
</div>
</body>
</html>
