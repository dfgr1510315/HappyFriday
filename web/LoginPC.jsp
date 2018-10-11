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
</head>
<body>
<div class="modal fade" id="LoginModal" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">

            <%-- 登录框头部--%>
            <div class="modal-header">
                <h4 class="modal-title">登录</h4>
                <button id="loginClose" class="close" data-dismiss="modal">&times;</button>
            </div>

            <%--登录界面--%>
            <div class="modal-body">
                <form id="login_form" action="http://localhost:8080/register" method="POST">
                    <div class="form-group">
                        <label for="username">用户名:</label>
                        <input type="text" class="form-control" id="username" placeholder="Enter username">
                    </div>
                    <div class="form-group">
                        <label for="pwd">密码:</label>
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password">
                    </div>
                    <div class="form-check">
                        <label class="form-check-label">
                            <input class="form-check-input" type="checkbox"> 记住我
                            <input style="display:none" type="text" value="login" name="statu" id="statu">
                            <span id="loginError" style="display: none; color:red;margin-left: 20px"></span>
                        </label>
                    </div>
                    <button id="login_btn" type="button" class="btn btn-primary" style="margin-top: 15px"
                            onclick="login()">登录
                    </button>
                </form>
            </div>

            <%--登录底部--%>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">。。。</button>
            </div>--%>
        </div>
    </div>
</div>

<script>
    function login() {
        var username = $.trim($("#username").val());
        var password = $.trim($("#pwd").val());
        var statu = $.trim($("#statu").val());
        if (username==""){
            $("#loginError").text("请输入用户名").show();
            return false;
        } else if(password==""){
            $("#loginError").text("请输入密码").show();
            return false;
        }
        var data = {username:username,password:password,statu:statu};
        $.ajax({
            type:"POST",
            asynch :"false",
            url:"http://localhost:8080/register",
            data:data,
            dataType:'json',
            success:function (msg) {
                if (msg == 1) {
                    $("#loginError").text("用户名或密码错误").show();
                }
                else{
                    setCookie("username",username,30);
                    $("#loginClose").click();
                    $("#loginButton").hide();
                    $("#personalCenter").show();
                    $("#showname").text(username);
                }
            }
        });
    }
    function PerCenter() {
        var username = $.trim($("#username").val());
        window.open("http://localhost:8080/PersonalCenter.jsp?username="+username);
    }

    function setCookie(cname,cvalue,exday) {
        var d = new Date();
        d.setTime(d.getTime()+(exday*24*1000*60*60));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname+"="+cvalue+";"+expires;
    }
    function getCookie(cname) {
        var name = cname+"=";
        var ca = document.cookie.split(';');
        for(var i=0;i<ca.length;i++){
            var c = ca[i].trim();
            if (c.indexOf(name)==0) return c.substring(name.length,c.length);
        }
        return "";
    }
    function checkCookie() {
        var user = getCookie("username");
        if (user!=""){
            $("#loginButton").hide();
            $("#personalCenter").show();
            $("#showname").text(user);
        }
    }
    function deleteCookie() {
        $("#personalCenter").hide();
        $("#loginButton").show();
        document.cookie="username=; expires=Thu,01 Jan 1970 00:00:00 GMT";
    }
</script>

</body>
</html>
