<%@ page import="Server.ConnectSQL" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username=(String) session.getAttribute("user_id");
    String head_image =(String) session.getAttribute("head_image");
    String usertype = (String) session.getAttribute("usertype");
    String history_class_id = Arrays.toString((int[]) session.getAttribute("history_class_id"));
    String schedule = Arrays.toString((int[]) session.getAttribute("schedule"));
    String last_time = Arrays.toString((String[]) session.getAttribute("last_time"));
    String title = Arrays.toString((String[]) session.getAttribute("title"));
    int new_notice=1;
    try {
        Class.forName(ConnectSQL.driver);
        Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT readed from notice where username='"+username+"' order by time desc limit 1;");
        while (rs.next())  new_notice = rs.getInt("readed");
    }catch (Exception e){
        e.printStackTrace();
    }


%>
<html>
<head>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script>
        var user = '<%=username%>';
        var head_image = '<%=head_image%>';
        var usertype = '<%=usertype%>';
        var new_notice = '<%=new_notice%>';
        function ifActive() {
            var obj = document.getElementsByTagName("body"); //获取当前body的id
            var id = obj[0].getAttribute("id");
            switch (id) {
                case id = "homepage":
                    document.getElementsByTagName("a")[0].className += ' active';
                    break;
                case id = "resources":
                    document.getElementsByTagName("a")[1].className += ' active';
                    break;
            }
        }


        function checkCookie() {
            if (user !== "null") {
                $("#loginButton").hide();
                $("#personalCenter").show();
                $("#showname").text(user);
                $("#head_image").attr('src',"${pageContext.request.contextPath}"+head_image);
            }
        }

        var cookie = {
            set: function(name, value) {
                var Days = 30*12*5;
                var exp = new Date();
                exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
                document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString();
            },
            get: function(name) {
                var arr, reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)');
                if(arr = document.cookie.match(reg)) {
                    return unescape(arr[2]);
                } else {
                    return null;
                }
            },
            del: function(name) {
                var exp = new Date();
                exp.setTime(exp.getTime() - 1);
                var cval = cookie.get(name);
                if(cval != null) {
                    document.cookie = name + '=' + cval + ';expires=' + exp.toGMTString();
                }
            }
        };
        $(document).ready(function(){
            if (user===null) return;
            load_history();
            card_show();
            history_show();
            if (new_notice==='0')  $('.msg_remind').css('display','inline')
        });

        function load_history() {
            var history_class_id = '<%=history_class_id%>'.replace('[','').replace(']','').split(',');
            var schedule = '<%=schedule%>'.replace('[','').replace(']','').split(',');
            var last_time = '<%=last_time%>'.replace('[','').replace(']','').split(',');
            var title = '<%=title%>'.replace('[','').replace(']','').split(',');
            for (var i=0;i<history_class_id.length;i++){
                if (' 0' === history_class_id[i])  continue;
                var unit = last_time[i].split(':');
                console.log(unit[0]);
                $('.history_ul').append(
                    '   <li>\n' +
                    '                        <a href="${pageContext.request.contextPath}/HTML_JSP/Play.jsp?'+history_class_id[i].trim()+'/'+unit[0].trim()+'" target="_blank" title="'+last_time[i].trim()+'" class="clearfix">\n' +
                    '                            <div class="link">'+last_time[i].trim()+'</div>\n' +
                    '                            <div>\n' +
                    '                                <div class="state">\n' +
                    '                                    <span class="page">'+title[i]+'</span>\n' +
                    '                                    <span class="split">|</span>\n' +
                    '                                    <span>'+schedule[i]+'%</span>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </a>\n' +
                    '                    </li>'
                )
            }
        }

        function history_show() {
            var timeout = null;
            var history_card = $(".history");
            history_card.mouseover(function(){
                $("#history_card").show();
                if( timeout != null ) clearTimeout(timeout);
            });

            history_card.mouseout(function(){
                timeout = setTimeout(function(){
                    $("#history_card").hide();
                },1000);
            });
        }
        
        function card_show() {
            var timeout = null;
            var user_card = $(".user");
            user_card.mouseover(function(){
                $("#user_card").show();
                if( timeout != null ) clearTimeout(timeout);
            });

            user_card.mouseout(function(){
                timeout = setTimeout(function(){
                    $("#user_card").hide();
                },1000);
            });
        }
    </script>
    <title></title>
</head>
<body>
<div class="user_navigation">
    <ul class="nav nav-pills" style="display: inline">
        <li>
            <img src="${pageContext.request.contextPath}/image/HUAS.png" style="height: 40px;width: 40px;float: left;margin-right: 21px" alt="湖南文理图标">
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/HTML_JSP/homepage.jsp" style="float: left;">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/HTML_JSP/Resources.jsp" style="float:left;">文档</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/HTML_JSP/course.jsp" style="float:left;">课程类型</a>
        </li>
        <li class="nav-item">
            <div class="input-group mb-3">
                <input id="search_input" type="text" class="form-control" placeholder="搜索">
                <div class="input-group-append">
                    <i class="fa fa-search"></i>
                </div>
            </div>
        </li>
        <li class="nav-item" id="loginButton">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" style="float: right;" href="">登录</a>
        </li>
        <li class="nav-item" id="personalCenter" style="display: none;float: right;position: relative;">
            <%-- <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="" id="showname"></a>--%>
            <img id="head_image" class="user_imag user"  alt="用户头像" src="">
            <div class="user_card user" id="user_card">
                <div style="padding: 20px;">
                    <a  href="${pageContext.request.contextPath}/HTML_JSP/PersonalCenter.jsp" target="_blank">个人中心</a>
                    <%--  <a href="${pageContext.request.contextPath}/HTML_JSP/upload.jsp">上传资源</a>
                      <a href="#">账号设置</a>
                      <a  href="#">我的消息</a>
                      <a  href="${pageContext.request.contextPath}/HTML_JSP/management.jsp" >后台管理</a>--%>
                    <a href="${pageContext.request.contextPath}/HTML_JSP/homepage.jsp" onclick="deleteCookie()">退出登录</a>
                </div>
            </div>
            <a class="notice" target="_blank" href="${pageContext.request.contextPath}/HTML_JSP/notification.jsp">
                <i class="msg_remind"></i>
                <i class="fa fa-bell"></i>
            </a>
            <a class="notice history" target="_blank" href="${pageContext.request.contextPath}/HTML_JSP/notification.jsp">
                <i class="fa fa-history"></i>
            </a>
            <div class="history-record-m mini-wnd-nav history-wnd dd-bubble history" id="history_card">
                <ul class="history_ul"></ul>
            </div>
        </li>
    </ul>
</div>
</body>
</html>
