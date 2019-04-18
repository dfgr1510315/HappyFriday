function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    if ('HTML_JSP'===pathName.substr(1, index)) return '';
    return pathName.substr(1, index);
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
        url:getContextPath()+"/register",
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
                if ($('#remember').is(':checked')){
                    cookie.set('username',username);
                    cookie.set('password',password);
                }else {
                    cookie.del('username');
                    cookie.del('password');
                }
                location.reload()
            }else {
                alert('未知错误,请与管理员联系');
            }
        }
    });
}


function deleteCookie() {
    $.ajax({
        type:"POST",
        url:getContextPath()+"/register",
        data:{
            state:"Logout"
        },
        dataType:'json'
    });
    $("#personalCenter").hide();
    $("#loginButton").show();
    //document.cookie="JSESSIONID=B7D4B3487644149C29480AE7F03501B1;expires=Thu,01 Jan 1970 00:00:00 GMT";
}
