var domainname="http://www.uicweb.cn/ledou/";
function login() {
    location.href=domainname+'?page=wxlogin&url='+location.href;
}
function uajax(z) {
    var url=domainname;
    if (z[0]=="get") {
        var r={};
        $.ajaxSettings.async = false;
        $.get(url+z[1],z[2],function (s,status){
            if (status="success") {r=s;}else{r={status:false};}
            $.ajaxSettings.async = true;
        })
        return r;
    }
    if (z[0]=="post") {
        var r={};
        $.ajaxSettings.async = false;
        $.post(url+z[1],z[2],function (s,status){
            if (status="success") {r=s;}else{r={status:false};}
            $.ajaxSettings.async = true;
        })
        return r;
    }
}

//校验不能为空
function isnotnull(s) {
    if (typeof(s) == "undefined" || s=="") { return false}
    return true
}
//校验汉字
function ishanzi(s) {
    //var patrn = /^[\u2E80-\u9FFF]$/; //Unicode编码中的汉字范围  /[^\x00-\x80]/
    var patrn = /[^\x00-\x80]/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验登录名：只能输入4-20个以字母开头、可带数字、“_”、“.”的字串
function isloginname(s) {
    var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){3,19}$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验用户姓名：只能输入4-30个以字母开头的字串
function isusername(s) {
    var patrn = /^[a-zA-Z]{4,30}$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验密码：只能输入6-20个字母、数字、下划线
function ispassword(s) {
    var patrn = /^[a-zA-Z0-9]{6,20}$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
function istel(s) {
    var patrn = /^[+]{0,1}(d){1,4}[ ]?([-]?((d)|[ ]){1,12})+$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验手机号码
function isphone(s) {
    var patrn = /^1[0-9][0-9]{1}[0-9]{8}$|15[012356789]{1}[0-9]{8}$|18[012356789]{1}[0-9]{8}$|14[57]{1}[0-9]$/;
    if (!patrn.exec(s)) return false
    return true
}

//校验邮政编码
function ispostalcode(s) {
    var patrn = /^[a-zA-Z0-9 ]{3,12}$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验是否IP地址
function isid(s) {
    var patrn = /^[0-9.]{1,20}$/;
    if (!patrn.exec(s)) return false
    return true
}
 
//校验EMail
function isemail(s) {
    //var regex = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    //var reg =   /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    var patrn = /^([0-9A-Za-z\-_\.]+)@([0-9A-Za-z]+\.[A-Za-z]{2,3}(\.[A-Za-z]{2})?)$/g;
    if (!patrn.exec(s)) return false
    return true
}

//校验用户编号是否重复
function notchnumber(s) {
    $.ajaxSettings.async = false;
    var a=true;
    $.get("index.php?api=uicsel&name=uic_user&paged=50&user_number="+s,function (s){
        s=JSON.parse(s).data;
        if (s.list.length!=0) {a=false;}
        $.ajaxSettings.async = true;
    })
    return a;
}
//校验管理员是否有订单
function notchadmin(s) {
    $.ajaxSettings.async = false;
    var a=true;
    $.get("index.php?api=uicsel&name=uic_bill&paged=50&bill_user2id="+s,function (s){
        s=JSON.parse(s).data;
        if (s.list.length!=0) {a=false;}
        $.ajaxSettings.async = true;
    })
    return a;
}

//校验way是否有关联订单
function iswayabout(s) {
    $.ajaxSettings.async = false;
    var a=true;
    $.get("index.php?api=uicsel&name=uic_way_bill&paged=50&way_bill_wayid="+s,function (s){
        s=JSON.parse(s).data;
        if (s.list.length!=0) {a=false;}
        $.ajaxSettings.async = true;
    })
    return a;
}


//弹层
function uicpop(z,c) {
	$.get(z, function(s) {
	    s=s.split('<body>')[1]
	    s=s.split('</body>')[0]
        s=s.replace(/var v=new Vue/g, 'var v'+c+'=new Vue');
	    //alert(s)
        $('#pop'+c).remove();
        $("body").append('<div id="pop'+c+'">'+s+'</div>');
	});
}
function uicpopclose(c) {
  $('#pop'+c).remove();
}


//eval("window."+pagename()+"body =$('body').html();")
//无刷新跳转链接
function uichref(z) {
	$.get(z, function(s) {
	    s=s.split('<body onscroll="v.scrolltop=document.body.scrollTop;">')[1]
	    s=s.split('</body>')[0]
	    
	    if (!window.backpage){backpage={};};
        backpage[pagename()]=eval(pagename()+"body");
	    window.addEventListener('popstate',uicback, false);
	    window.history.pushState(null, null, z);
	    scrollTo(0,0);
	    $("body").html(s);
	});
}

function urlbm(z) {
	return encodeURIComponent(JSON.stringify(z));
}

function urljm(z) {
	return JSON.parse(decodeURIComponent(parseQueryString(location.href)[z]));
}
// window.onbeforeunload = function() {
//     var n = window.event.screenX - window.screenLeft;
//     var b = n > document.documentElement.scrollWidth - 20;
//     if (!(b && window.event.clientY < 0 || window.event.altKey)) {
//         // alert("开始刷新页面了....");
//         // window.event.returnValue = "真的要刷新页面么？";     //这里可以放置你想做的操作代码
//         window.addEventListener('popstate',uicback, true);
//     }
// } 
// window.addEventListener('popstate',uicback, true);
function uicback(){
	if (!window.backpage){location.href=location.href;}
    $("body").html(backpage[pagename()]);
}

function pagename() {
	var u=location.href.split("/");u=u[u.length-1];u=u.split(".html")[0];
	return u;
}

var parseParam = function(param, key) {
    var paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        $.each(param, function(i) {
            var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};

function parseQueryString(url)
{
var obj={};
var keyvalue=[];
var key="",value=""; 
var paraString=url.substring(url.indexOf("?")+1,url.length).split("&");
for(var i in paraString)
{
keyvalue=paraString[i].split("=");
key=keyvalue[0];
value=keyvalue[1];
obj[key]=value; 
} 
return obj;
}