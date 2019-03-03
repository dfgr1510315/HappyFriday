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
<style type="text/css">
    .btn-outline-success{
        margin-bottom: 15px;
        margin-top: 15px
    }
    .label-link {
        float: right;
        font-size: 12px;
    }
    .modal-content{
        width: 80%;
        margin-left: auto;
        margin-right: auto;
        position: relative;
    }
    .close{
        position: absolute;
        top: -35px;
        right: 0;
        color: white !important;
        font-size: 35px;
    }
    .login_head_ul{
        width: 100%;
    }
    .login_head_ul .nav-item{
        margin-bottom: -1px;
        width: 50%;
        text-align: center;
    }
    .modal-header{
        border-bottom:0
    }
    .login_control{
        display: inline-block;
        padding-left: 35px;
        font-size: 13px;
    }
    .form-group{
        position: relative;
    }
    .login_control label{
        display: inline-block;
        position: absolute;
        top: 11px;
        left: 9px;
        color: #cecbcb;
    }
    .card-body{
        font-size: 12px;
        padding: 16px;
    }
</style>
<script type="text/javascript">
    var username_model = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
    var email_model = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    var pasw_model = /^[a-zA-Z]\w{5,17}$/;

    $(function () {
        $('#LoginModal').on('show.bs.modal', function () {
            $('#username').val(cookie.get('username'));
            $('#pwd').val(cookie.get('password'));
        })
    });

    function register() {
        var username = $('#register_username').val();
        var email = $('#register_email').val();
        var pasw = $('#register_pwd').val();
        if (!username_model.test(username)){
            $('#error_waring').css('display','block').children().text('用户名以字母开头，允许5-16字节，允许字母数字下划线');
            return;
        }
        if (!email_model.test(email)){
            $('#error_waring').css('display','block').children().text('邮箱格式不正确，请重新输入');
            return;
        }
        if (!pasw_model.test(pasw)){
            $('#error_waring').css('display','block').children().text('密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线');
            return;
        }
        if ($('#register_sure_pwd').val()!==pasw){
            $('#error_waring').css('display','block').children().text('两次输入的密码不一致');
            return;
        }
        $('#error_waring').css('display','none');
        $.ajax({
            type:"POST",
            asynch :"false",
            url:$("#PageContext").val()+"/register",
            data:{
                state:'register',
                username:username,
                password:pasw,
                email:email
            },
            dataType:'json',
            success:function (state) {
                if (state===1){
                    alert('注册成功,请去邮箱激活账号');
                    $('#loginClose').click();
                } else if (state===0){
                    alert('用户名已被使用');
                } else {
                    alert('未知错误');
                }
            }
        });
    }

    function forgetPW() {
        var forget_user = $('#forget_user').val();
        if (!username_model.test(forget_user)){
            alert('请输入正确的用户名格式');
            return;
        }
        $.ajax({
            type:"POST",
            asynch :"false",
            url:$("#PageContext").val()+"/register",
            data:{
                state:'forgetPW',
                forget_user:forget_user
            },
            dataType:'json',
            success:function (state) {
                if (state===1){
                    alert('我们已将你当前密码发送至你的邮箱中');
                    $('#forgetClose').click();
                } else if (state===0){
                    alert('未能通过电子邮件地址找到用户');
                } else {
                    alert('未知错误,请与管理员联系');
                }
            }
        });
    }
</script>
<body>

<div class="modal fade" id="LoginModal" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">
            <a id="loginClose" class="close" data-dismiss="modal">&times;</a>
            <%-- 登录框头部--%>
            <div class="modal-header">
                <ul class="nav nav-tabs login_head_ul" role="tablist" >
                    <li class="nav-item">
                        <a class="nav-link active modal-title" data-toggle="tab" href="#login">登录</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link modal-title" data-toggle="tab" href="#register">注册</a>
                    </li>
                </ul>
            </div>

            <%--登录界面--%>
            <div class="modal-body tab-content">
                <div id="login" class="container tab-pane active">
                    <div class="form-group" >
                        <label for="username" ><i class="fa fa-user-o"></i></label>
                        <input type="text" class="form-control login_control" id="username" placeholder="输入用户名">
                    </div>
                    <div class="form-group">
                        <label for="pwd"><i class="fa fa-key"></i></label>
                        <input type="password" class="form-control login_control" id="pwd" placeholder="输入密码">
                    </div>
                    <div class="form-check">
                        <label class="form-check-label" style="font-size: 12px;line-height: 21px;">
                            <input id="remember" class="form-check-input" type="checkbox" >记住我
                            <span id="loginError" style="display: none; color:red;margin-left: 20px"></span>
                        </label>
                        <a class="label-link" data-toggle="modal" data-target="#forget" href="">忘记密码?</a>
                    </div>
                    <button id="login_btn" type="button" class="btn btn-outline-success btn-sm btn-block"
                            onclick="login()">登录
                    </button>
                </div>
                <div id="register" class="container tab-pane ">
                    <div class="form-group">
                        <label for="register_username"><i class="fa fa-user-o"></i></label>
                        <input type="text" class="form-control login_control" id="register_username" placeholder="输入用户名">
                    </div>
                    <div class="form-group">
                        <label for="register_email"><i class="fa fa-at"></i></label>
                        <input type="text" class="form-control login_control" id="register_email" placeholder="输入邮箱">
                    </div>
                    <div class="form-group">
                        <label for="register_pwd"><i class="fa fa-key"></i></label>
                        <input type="password" class="form-control login_control" id="register_pwd" placeholder="输入密码">
                    </div>
                    <div class="form-group">
                        <label for="register_sure_pwd"><i class="fa fa-key"></i></label>
                        <input type="password" class="form-control login_control" id="register_sure_pwd" placeholder="确认密码">
                    </div>
                    <div class="form-check">
                        <label class="form-check-label">
                            <span id="registerError" style="display: none; color:red;margin-left: 20px"></span>
                        </label>
                    </div>
                    <button id="register_btn" type="button" class="btn btn-outline-success btn-sm btn-block"
                            onclick="register()">注册
                    </button>
                    <div class="card bg-danger text-white" id="error_waring" style="display: none">
                        <div class="card-body"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="forget" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">
            <%-- 头部--%>
            <div class="modal-header">
                <h5 class="modal-title">请输入账号</h5>
                <div style="font-size: 14px;margin-top: 7px;">我们会将密码发送至账户绑定的邮箱</div>
                <a id="forgetClose" class="close" data-dismiss="modal">&times;</a>
            </div>
            <%--界面--%>
            <div class="modal-body">
                <div class="form-group">
                    <label for="forget_user"><i class="fa fa-user"></i></label>
                    <input type="text" class="form-control" id="forget_user" placeholder="输入用户名">
                </div>
                <button type="button" class="btn btn-primary"  onclick="forgetPW()">确定
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
