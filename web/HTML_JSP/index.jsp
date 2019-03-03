<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
</head>

<style type="text/css">

</style>

<body>
<div class="form-group">
    <label for="email">邮箱:</label>
    <input type="text" class="form-control" id="email" placeholder="Enter username">
</div>
<div class="form-group">
    <label for="username">用户名:</label>
    <input type="text" class="form-control" id="username" placeholder="Enter username">
</div>
<div class="form-group">
    <label for="pwd">密码:</label>
    <input type="password" class="form-control" id="pwd" placeholder="Enter password">
</div>
<button id="login_btn" type="button" class="btn btn-primary" style="margin-top: 15px" onclick="register()">注册
</button>
</body>
<script type="text/javascript">
    function register(){
        var username=document.getElementById('username').value;
        var password=document.getElementById('pwd').value;
        var email=document.getElementById('email').value;
//先对输入的邮箱格式进行判断
        if(email === ''){
            alert("请输入您的邮箱");
            return;
        }else if(email !== "") {
            var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            isok= reg.test(email );
            if (!isok) {
                alert("邮箱格式不正确，请重新输入！");
                return false;
            }
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/register",
            type : "post",
            data : {
                state:'email_register',
                username:username,
                password:password,
                email:email
            },
            async : false,
            success : function(data) {
                if(data==='No'){
                    alert("注册失败");
                }
                else {
                    alert("注册成功");
                }
            }
        });

    }
</script>

</html>
