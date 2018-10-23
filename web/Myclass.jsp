<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程管理</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <style>
        .liMyclass{
            background-color: #007bff;
        }
        .aMyclass{
            color: white;
        }
    </style>
    <script>
        function ifActive(){
            document.getElementById("idMyclass").className -= ' nav-link';
        }
    </script>
</head>
<body onload="checkCookie();ifActive()">
<jsp:include page="navigation.jsp"/>
<jsp:include page="LoginPC.jsp"/>
<div style="width: 100%;margin: auto;margin-top: 80px;height: 450px">
    <div style="background: url(image/bacg2.jpg) center top no-repeat #000;background-size: cover;height: 148px;margin-top: -21px">
        <div style="width: 80%;height: 100%;margin: auto">
            <div style="margin-left: 0;height: 148px;width: 148px;float: left">
                <div style="    border: 4px solid #FFF;box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);width: 148px;height: 148px;position: relative;border-radius: 50%;background: #fff;top: 24px;">
                    <img src="image/68296699_p0.png" style="text-align: center;width: 140px;height: 140px;border-radius: 50%;">
                </div>
            </div>
        </div>
        <div style="width: 80%;margin: auto">
            <jsp:include page="VerticalNav.jsp"/>
        </div>
    </div>

</div>
</body>
</html>
