var ContextPath = getContextPath();
function getContextPath(){
    var pathName = document.location.pathname;
    console.log(pathName);
    var index = pathName.substr(1).indexOf("/");
    console.log(index);
    console.log(pathName.substr(0, index));
    if ('HTML_JSP'===pathName.substr(1, index)) return '';
    return pathName.substr(0, index+1);
}
function login() {
    var loginError = $("#loginError");
    loginError.hide();
    var username = $("#username").val();
    var password = $("#pwd").val();
    if (!username_model.test(username)){
        loginError.text("用户名以字母开头，允许5-16字节，允许字母数字下划线").show();
        return;
    }
    if(!pasw_model.test(password)){
        loginError.text("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线").show();
        return;
    }
    var data = {username:username,password:password,state:'login'};
    $.ajax({
        type:"POST",
        url:ContextPath+"/register",
        data:data,
        dataType:'json',
        success:function (msg) {
            console.log(msg);
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
    var class_id = [];
    var class_title = [];
    var last_time = [];
    var schedule = [];
    for (var i=0;i<msg.history.length;i++){
        class_id.push(msg.history[i].class_id);
        class_title.push(msg.history[i].class_title);
        last_time.push(msg.history[i].last_time);
        schedule.push(msg.history[i].schedule);
    }
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
        url:ContextPath+"/register",
        data:{
            state:"Logout"
        },
        dataType:'json'
    });
    location.reload();
}
