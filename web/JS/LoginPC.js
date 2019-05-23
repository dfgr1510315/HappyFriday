let contextPath = getContextPath();
let username_model = /^.{3,20}$/;
let email_model = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
let pasw_model = /^[a-zA-Z]\w{5,17}$/;

function get_cookie() {
    $('#username').val(cookie.get('username'));
    $('#pwd').val(cookie.get('password'));
}

/*$(function () {
    console.log($('#LoginModal').text());
    $('#LoginModal').on('show.bs.modal', function () {
        console.log('/?');
        $('#username').val(cookie.get('username'));
        $('#pwd').val(cookie.get('password'));
    })
});*/

function login() {
    let loginError = $("#loginError");
    loginError.hide();
    let username = $("#username").val();
    let password = $("#pwd").val();
    if (!username_model.test(username)){
        loginError.text("用户名以字母开头，允许5-16字节，允许字母数字下划线").show();
        return;
    }
    if(!pasw_model.test(password)){
        loginError.text("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线").show();
        return;
    }
    if (slideVerify.slideFinishState!==true){
        loginError.text("请进行验证").show();
        return;
    }
    let data = {username:username,password:password,state:'login'};
    $.ajax({
        type:"POST",
        url:contextPath+"/register",
        data:data,
        dataType:'json',
        success:function (msg) {
            //console.log(msg);
            if (msg.state === 1) {
                $("#loginError").text("用户名错误或未激活").show();
            }
            else if (msg.state === 3){
                $("#loginError").text("密码错误").show();
            }
            else if (msg.state === 2) {
                setCookie(msg,username,password);
                //load_history();
                location.reload();
            }else {
                alert('未知错误,请与管理员联系');
            }
        }
    });
}

function setCookie(msg,username,password) {
    if ($('#remember').is(':checked')){
        cookie.set('username',username);
        cookie.set('password',password);
    }else {
        cookie.del('username');
        cookie.del('password');
    }
    let class_id = [];
    let class_title = [];
    let last_time = [];
    let schedule = [];
    for (let i=0;i<msg.history.length;i++){
        class_id.push(msg.history[i].class_id);
        class_title.push(msg.history[i].class_title);
        last_time.push(msg.history[i].last_time);
        schedule.push(msg.history[i].schedule);
    }
    cookie.set('user',username);
    cookie.set('class_id',class_id);
    cookie.set('class_title',class_title);
    cookie.set('last_time',last_time);
    cookie.set('schedule',schedule);
    cookie.set('head_image',msg.head_image);
    cookie.set('email',msg.email);
    cookie.set('usertype',msg.usertype);
}


function deleteCookie() {
    $.ajax({
        type:"POST",
        url:contextPath+"/register",
        data:{
            state:"Logout"
        },
        dataType:'json'
    });
    cookie.del('user');
    location.reload();
}

function register() {
    let username = $('#register_username').val();
    let email = $('#register_email').val();
    let pasw = $('#register_pwd').val();
    let error_waring = $('#error_waring');
    if (!username_model.test(username)){
        error_waring.css('display','block').children().text('用户名长度为3~20');
        return;
    }
    if (!email_model.test(email)){
        error_waring.css('display','block').children().text('邮箱格式不正确，请重新输入');
        return;
    }
    if (!pasw_model.test(pasw)){
        error_waring.css('display','block').children().text('密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线');
        return;
    }
    if ($('#register_sure_pwd').val()!==pasw){
        error_waring.css('display','block').children().text('两次输入的密码不一致');
        return;
    }
    error_waring.css('display','none');
    $.ajax({
        type:"POST",
        url:contextPath+"/register",
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
    let forget_user = $('#forget_user').val();
    if (!username_model.test(forget_user)){
        alert('请输入正确的用户名格式');
        return;
    }
    $.ajax({
        type:"POST",
        url:contextPath+"/register",
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

function getContextPath(){
    let pathName = document.location.pathname;
    //console.log(pathName);
    let index = pathName.substr(1).indexOf("/");
    //console.log(index);
    //console.log(pathName.substr(0, index));
    if ('HTML_JSP'===pathName.substr(1, index)) return '';
    return pathName.substr(0, index+1);
}
