<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/9/9
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><meta charset="utf-8"><title>用户注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_404812_5ylqhr7dqsg1ra4i.css" type="text/css" media="all">
    <script src="../src/zepto.js"></script>
    <script src="../src/vue.js"></script>
    <script src="../src/uic.js"></script>
</head>
<body>
<link rel="stylesheet" href="http://www.uicweb.cn/usercss/uic1253/css/registerPC.css" type="text/css" media="all">
<div id="id1368" class="id1368" >
    <div class='a1'>
        <div class='b1'>
            <i class='c1'>首页</i>
            <i class='c2'>文档</i>
            <i class='c3'>登录</i>
        </div>
    </div>
    <div class='a2'></div>
    <div class='a3'>
        <div class='b1'>
            <div class='c1'>
                <div class='d1'>用户注册</div>
            </div>
            <div class='c2'>
                <img src="" class='d1'>
                <form id="formCheck" action="http://localhost:8080/register"  method="post" onsubmit="return ifnull()">
                    <div class='d2'>
                        <div class='e1'>
                            <div class='f1'>登录名</div>
                            <input type="text" id="name" placeholder="用户登录名" class='f2' name="username">
                        </div>
                        <div class='e2'>
                            <div class='f1'>密码</div>
                            <input type="password" id="pasw" placeholder="请输入登录密码" class='f2' name="password">
                        </div>
                        <div class='e3'>
                            <div class='f1'></div>
                            <div class='f2'>
                                <input type="submit" id="register" value="注册" class='g1'  >
                            </div>
                        </div>
                        <div class="e4" style="display:none">
                            <input type="text" value="register" name="statu">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class='a4'>

    </div></div>

<script language="javascript" type="text/javascript">
    var v=new Vue({el:"#id1368",});
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