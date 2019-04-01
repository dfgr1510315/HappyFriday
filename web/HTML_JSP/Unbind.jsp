<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/10 0010
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String email = (String) session.getAttribute("email");
%>
<html>
<head>
    <title>个人中心</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <style type="text/css">
        body{
            background-color: #f8fafc;
        }
        body.modal-open {
            overflow-y: auto !important;
            padding-right: 0!important;
        }
        tbody tr td:not(:first-child){
            padding-top: 32px !important;
        }

        td button{
            margin-top: -7px;
        }

        .cover{
            width: 100px;height: 65px;margin-right: 5px
        }

        .found_class {
            display: inline-block;
            margin-bottom: 5px;
            font-weight: bold;
            margin-top: 5px;
        }

        .found_class_text{
            height: 40px !important;
            resize: none;
            width: 80%;
            float: right;
        }
        .itemBox {
            height: 88px;
            overflow: hidden;
            margin: auto;
            border-bottom: 1px solid #d9dde1;
        }
        .itemBox .left {
            width: 80px;
            float: left;
            font-size: 36px;
            color: #d9dde1;
            line-height: 98px;
            padding-left: 24px;
            padding-top: 18px;
        }
        .itemBox .center {
            padding-top: 22px;
            width: 510px;
            float: left;
        }
        .itemBox p {
            line-height: 24px;
            font-size: 12px;
        }
        .itemBox .font1 {
            color: #2b333b;
            font-size: 16px;
            font-weight: 700;
        }
        .itemBox .font3 {
            color: #2b333b;
            font-size: 14px;
        }
        .itemBox .font2 {
            color: #93999f;
            font-size: 14px;
        }
        .itemBox .right {
            float: right;
            padding-right: 12px;
            margin-top: 28px;
        }
        .itemBox .right .moco-btn-normal {
            padding-left: 15px;
            padding-right: 15px;
        }
        .form-group{
            position: relative;
        }
        .form-group label{
            display: inline-block;
            position: absolute;
            top: 11px;
            left: 9px;
            color: #cecbcb;
        }
        #ChangeEmail_email{
            display: inline-block;
            padding-left: 35px;
            font-size: 13px;
        }
    </style>
    <script type="text/javascript">
        var email = '<%=email%>';
        var email_model = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        $(document).ready(function () {
            $('.font4').text(email);
        });
        function changeBind() {
            var email = $('#ChangeEmail_email').val();
            if (!email_model.test(email)){
                alert('请输入正确的邮箱格式');
                return;
            }
            $.ajax({
                type:"POST",
                asynch :"false",
                url:$("#PageContext").val()+"/register",
                data:{
                    state:'ChangeEmail',
                    email:email,
                    username:user
                },
                dataType:'json',
                success:function (state) {
                    if (state===1){
                        alert('请去邮箱确认');
                        $('#ChangeEmailClose').click();
                    } else if (state===0){
                        alert('邮箱已被使用');
                    } else {
                        alert('未知错误');
                    }
                }
            });
        }
    </script>

</head>
<body onload="ifActive();addClass(2)">
<jsp:include page="navigation.html"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <h3 class="container_right_head">
                账号绑定
            </h3>
            <div class="itemBox">
                <div class="left">	<i class="fa fa-envelope-o"></i></div>
                <div class="center">
                    <p>
                        <span class="font1">邮箱</span>
                        <span class="font3"></span>
                        <span class="font4"></span>
                    </p>
                    <p class="font2">可用邮箱找回密码</p>
                </div>
                <div class="right">
                    <a href="javascript:void(0);" class=" moco-btn-normal" data-toggle="modal" data-target="#ChangeEmail">修改绑定</a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="ChangeEmail" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">
            <%-- 头部--%>
            <div class="modal-header">
                <h5 class="modal-title">请输入邮箱地址</h5>
                <a id="ChangeEmailClose" class="close" data-dismiss="modal">&times;</a>
            </div>
            <%--界面--%>
            <div class="modal-body">
                <div class="form-group">
                    <label for="ChangeEmail_email"><i class="fa fa-at"></i></label>
                    <input type="text"  class="form-control" id="ChangeEmail_email" placeholder="输入邮箱">
                </div>
                <button type="button" class="btn btn-primary"  onclick="changeBind()">确定
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
