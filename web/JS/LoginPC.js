
function login() {
    var username = $.trim($("#username").val());
    var password = $.trim($("#pwd").val());
    var statu = $.trim($("#statu").val());
    if (username===""){
        $("#loginError").text("请输入用户名").show();
        return false;
    } else if(password===""){
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
            if (msg === 1) {
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
    window.open("HTML_JSP/PersonalCenter.jsp?username="+username);
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
        if (c.indexOf(name)===0) return c.substring(name.length,c.length);
    }
    return "";
}

function deleteCookie() {
    $("#personalCenter").hide();
    $("#loginButton").show();
    document.cookie="username=; expires=Thu,01 Jan 1970 00:00:00 GMT";
}
