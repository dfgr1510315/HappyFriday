<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/9/11
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:v-on="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>用户登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_404812_5ylqhr7dqsg1ra4i.css" type="text/css" media="all">
    <link rel="stylesheet" href="LoginPC.css" type="text/css" media="all">
    <script src="zepto.js"></script>
    <script src="vue.js"></script>
    <script src="uic.js"></script>
    <style>

        ul.daohang {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            position: fixed;
            top: 0;
            width: 100%;
            font-size: 14px;
            border-color: #0c60ee;
            background-color: #387ef5;
            height: 44px;
            border-width: 0;
            border-style: solid;
            border-top: 1px solid transparent;
            border-bottom: 1px solid #ddd;
        }

        ul.daohang li {
            float: left;
        }

        ul.daohang li a {
            display: block;
            color: white;
            text-align: center;
            padding: 12px 16px;
            text-decoration: none;
        }

        ul.daohang li a:hover:not(.active3) {
            background-color: #3A5FCD;
        }

        .active3 {
            background-color: #3A5FCD;
        }

        ul.daohang li.li3 {
            float: right;
            margin-right: 50px;
        }

        ul.daohang li.li1 {
            margin-left: 50px;
        }
    </style>
</head>
<body>
<div id="id1367" class="id1367">
    <div class='a1'>
        <ul class="daohang">
            <li class="li1"><a class="active1" href="homepage.jsp">首页</a></li>
            <li class="li2"><a class="active2" href="Resources.jsp">文档</a></li>
            <li class="li3"><a class="active3"
                               href="LoginPC.jsp" <%--onclick="document.getElementById('login').style.display='block'"--%>>登录</a>
            </li>
        </ul>
    </div>

    <div class='a2'></div>
    <div class='a3'>
        <div class='b1'>
            <div class='c1'>
                <div class='d1'>用户登录</div>
            </div>
            <div class='c2'>
                <img src="" class='d1'>
                <div class='d2'>
                    <form id="formCheck" action="http://localhost:8080/register"  method="post" onsubmit="return ifnull()">
                        <div class='e1'>
                            <div class='f1'>登录名</div>
                            <input type="text" id="name" placeholder="用户登录名" class='f2' name="username">
                        </div>
                        <div class='e2'>
                            <div class='f1'>密码</div>
                            <input type="password" id="pasw" placeholder="请输入登录密码" class='f2' name="password">
                        </div>
                        <div class='e3'>
                            <div class='f2'>
                                <input type="submit" value="登录"  class='g1' id="login">
                                <input type="button" value="注册" onclick="location.href='registerPC.jsp'" class='g2'>
                            </div>
                        </div>
                        <div class="e4" style="display:none">
                            <input type="text" value="login" name="statu">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class='a4'></div>
</div>
<script type="text/javascript">
    /*var v = new Vue({
        el: "#id1367",
    });*/

    function ifnull() {
        if (formCheck.name.value==""){
            formCheck.name.focus();
            alert("账号不能为空!");
            return false;
        }else if (formCheck.pasw.value=="") {
            formCheck.pasw.focus();
            alert("密码不能为空!");
            return false;
        }
        return true;
    }


</script>
</body>
</html>
