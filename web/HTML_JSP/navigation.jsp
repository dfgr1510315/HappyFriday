<%@ page import="Server.ConnectSQL" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username=(String) session.getAttribute("user_id");
    String head_image = null;
    String usertype = null;
    String email = null;
    try {
        Class.forName(ConnectSQL.driver);
        Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select head,usertype,email from personal_table,login_table where login_table.username=personal_table.username and login_table.username='"+username+"'");
        while (rs.next()){
            head_image = rs.getString("head");
            usertype = rs.getString("usertype");
            email = rs.getString("email");
        }
        rs.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <script>
        var user = '<%=username%>';
        var head_image = '<%=head_image%>';
        var usertype = '<%=usertype%>';
        var email = '<%=email%>';
        function ifActive() {
            var obj = document.getElementsByTagName("body"); //获取当前body的id
            /*var obj = document.getElementById(id[0].getAttribute("id")+'');*/
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
        })

    </script>
</head>
<body>
<div class="user_navigation">
    <ul class="nav nav-pills" style="display: inline">
        <li>
            <img src="${pageContext.request.contextPath}/image/HUAS.png" style="height: 40px;width: 40px;float: left;margin-right: 21px">
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
        <li class="nav-item" id="loginButton">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" href="#" style="float: right">登录</a>
        </li>
        <li class="nav-item" id="personalCenter" style="display: none;float: right">
            <%-- <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="" id="showname"></a>--%>
            <img id="head_image" class="user_imag user" src="" style="height: 40px;width: 40px;border-radius: 50%; margin-right: 50px" >
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
        </li>
    </ul>
</div>
</body>
</html>
