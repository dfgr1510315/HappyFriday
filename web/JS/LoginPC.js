
function login() {
    $("#loginError").hide();
    var username = $("#username").val();
    var password = $("#pwd").val();
    var PageContext = $("#PageContext").val();
    if (!username_model.test(username)){
        $("#loginError").text("用户名以字母开头，允许5-16字节，允许字母数字下划线").show();
        return;
    }
    if(!pasw_model.test(password)){
        $("#loginError").text("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线").show();
        return;
    }
    var data = {username:username,password:password,state:'login'};
    $.ajax({
        type:"POST",
        asynch :"false",
        url:PageContext+"/register",
        data:data,
        dataType:'json',
        success:function (msg) {
            if (msg.state === 1) {
                $("#loginError").text("用户名错误或未激活").show();
            }
            else if (msg.state === 3){
                $("#loginError").text("密码错误").show();
            }
            else if (msg.state === 2) {
                //setCookie("username",username,30);
                $('#head_image').attr('src',PageContext+msg.head_image);
                $("#loginClose").click();
                $("#loginButton").hide();
                $("#personalCenter").show();
                $("#showname").text(username);
                if (msg.readed===0)  $('.msg_remind').css('display','inline') ;
                if ($('#remember').is(':checked')){
                    cookie.set('username',username);
                    cookie.set('password',password);
                }else {
                    cookie.del('username');
                    cookie.del('password');
                }
            }else {
                alert('未知错误,请与管理员联系');
            }
        }
    });
}

/*
function setCookie(cname,cvalue,exday) {
    var d = new Date();
    d.setTime(d.getTime()+(exday*24*1000*60*60));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname+"="+cvalue+";"+expires;
}*/
/*function getCookie(cname) {
    var name = cname+"=";
    var ca = document.cookie.split(';');
    for(var i=0;i<ca.length;i++){
        var c = ca[i].trim();
        alert(c.indexOf(name));
        if (c.indexOf(name)===0) return c.substring(name.length,c.length);
    }
    return "";
}*/

function deleteCookie() {
    $.ajax({
        type:"POST",
        asynch :"false",
        url:"../register",
        data:{
            state:"Logout"
        },
        dataType:'json'
    });
    $("#personalCenter").hide();
    $("#loginButton").show();
    //document.cookie="JSESSIONID=B7D4B3487644149C29480AE7F03501B1;expires=Thu,01 Jan 1970 00:00:00 GMT";
}


/*function encryption(Title) {
    var All_Title = '';
    for (var i=0;i<Title.length;i++){
        All_Title +=(Title.charCodeAt(i)+11)+"/"
    }
    return All_Title;
}

function decrypt(Title) {
    var All_Title = '';
    for(var i=0;i<Title.length-1;i++){
        All_Title +=String.fromCharCode(parseInt(Title[i])-11);
    }
    return All_Title;
}*/
