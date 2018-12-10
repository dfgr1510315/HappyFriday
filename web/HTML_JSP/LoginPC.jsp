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
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
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



</body>
</html>
