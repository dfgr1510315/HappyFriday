<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/24 0024
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录网上银行</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
</head>
<body>
    <div  style="width: 50%;height: 200px;margin: 220px auto auto;">
        <div class="form-group">
            <label for="username">用户名:</label>
            <input type="text" class="form-control" id="username" >
        </div>
        <div class="form-group">
            <label for="pwd">密码:</label>
            <input type="password" class="form-control" id="pwd" >
        </div>
        <div id="type" class="form-group" style="display: none">
            <label>账户类型:</label>
            <label><input name="Fruit" type="radio" value="1" />基本账户 </label>
            <label><input name="Fruit" type="radio" value="2" />一般账户 </label>
            <label><input name="Fruit" type="radio" value="3" />临时账户 </label>
            <label><input name="Fruit" type="radio" value="4" />专业账户 </label>
        </div>
        <div id="person_information" style="display: none;">
            <div class="form-group">
                <label for="name">姓名:</label>
                <input type="text" class="form-control" id="name" >
            </div>
            <div class="form-group">
                <label for="sex">性别:</label>
                <input type="text" class="form-control" id="sex" >
            </div>
            <div class="form-group">
                <label for="country">国籍:</label>
                <input type="text" class="form-control" id="country" >
            </div>
            <div class="form-group">
                <label for="phone">手机号:</label>
                <input type="text" class="form-control" id="phone" >
            </div>
            <div class="form-group">
                <label for="card_type">证件类型:</label>
                <input type="text" class="form-control" id="card_type" >
            </div>
            <div class="form-group">
                <label for="card">证件号码:</label>
                <input type="text" class="form-control" id="card" >
            </div>
            <div class="form-group">
                <label for="address">住址:</label>
                <input type="text" class="form-control" id="address" >
            </div>
            <div class="form-group">
                <label for="Postcodes">邮政编码:</label>
                <input type="text" class="form-control" id="Postcodes" >
            </div>
        </div>
        <div class="form-check" >
            <label class="form-check-label">
                <input style="display:none" type="text" value="login" name="state" id="state">
            </label>
        </div>
        <button id="login_btn" type="button" class="btn btn-primary" style="margin-top: 15px"
                onclick="login()">登录
        </button>
        <button id="register_btn" type="button" class="btn btn-primary" style="margin-top: 15px"
                onclick="register_model()">注册
        </button>
        <button id="sure_btn" type="button" class="btn btn-primary" style="margin-top: 15px;display: none"
                onclick="register()">确定
        </button>
    </div>



</body>
<script type="text/javascript">
    function login() {
        var username = $('#username').val();
        var password = $('#pwd').val();
        var state = $('#state').val();
        //alert(username+password);
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/l_r_bank",
            data: {
                username:username,
                password:password,
                state:state
            },
            dataType: 'json',
            success: function (msg) {
                //alert(msg);
                if (2===msg) {
                    alert("登陆成功");
                    window.location.href="Bank.jsp?"+username;
                }else alert("登录失败");
            }
        });
    }
    function register_model() {
        $('#state').val('register');
        $('#register_btn').css('display','none');
        $('#login_btn').css('display','none');
        $('#sure_btn').css('display','block');
        $('#type').css('display','block');
        $('#person_information').css('display','block');
    }
    function register() {
        var username = $('#username').val();
        var password = $('#pwd').val();
        var user_type =$("input[name='Fruit']:checked").val();
        var state = $('#state').val();
        var name = $('#name').val();
        var sex = $('#sex').val();
        var country = $('#country').val();
        var phone = $('#phone').val();
        var card_type = $('input:radio:checked').val();
        var card_no = $('#card').val();
        var address = $('#address').val();
        var Postcodes = $('#Postcodes').val();
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/l_r_bank",
            data: {
                username:username,
                password:password,
                state:state,
                user_type:user_type,
                name:name,
                sex:sex,
                country:country,
                phone:phone,
                card_type:card_type,
                card_no:card_no,
                address:address,
                Postcodes:Postcodes
            },
            dataType: 'json',
            success: function (msg) {
                //alert(msg);
                if (2===msg) {
                    alert("注册成功");
                    window.location.href="login_bank.jsp";
                }else alert("注册失败");
            }
        });
    }
</script>
</html>
