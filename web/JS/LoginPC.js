
function login() {
    var username = $.trim($("#username").val());
    var password = $.trim($("#pwd").val());
    var state = $.trim($("#state").val());
    if (username===""){
        $("#loginError").text("请输入用户名").show();
        return false;
    } else if(password===""){
        $("#loginError").text("请输入密码").show();
        return false;
    }
    var data = {username:username,password:password,state:state};
    $.ajax({
        type:"POST",
        asynch :"false",
        url:"/register",
        data:data,
        dataType:'json',
        success:function (msg) {
            if (msg === 1) {
                $("#loginError").text("用户名或密码错误").show();
            }
            else{
                //setCookie("username",username,30);
                $("#loginClose").click();
                $("#loginButton").hide();
                $("#personalCenter").show();
                $("#showname").text(username);
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
        url:"/register",
        data:{
            state:"Logout"
        },
        dataType:'json'
    });
    $("#personalCenter").hide();
    $("#loginButton").show();
    //document.cookie="JSESSIONID=B7D4B3487644149C29480AE7F03501B1;expires=Thu,01 Jan 1970 00:00:00 GMT";
}

function checkCookie() {
    var user = document.cookie;
    if (user !== "") {
        $("#loginButton").hide();
        $("#personalCenter").show();
        $("#showname").text(user);
    }
}

function encryption(Title) {
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
}
