<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/16 0016
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Myclass.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ckplayer/ckplayer/ckplayer.js"></script>
    <script type="text/javascript" src="../JS/Myclass.js"></script>
    <script type="text/javascript" src="../JS/drag.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
</head>



<body onload="checkCookie();ifActive()">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;height: 520px;background-color: #1c1f21;padding: 10px;margin-top: 59px">
    <div style="background-color: pink;height:100%;width: 5%;float: left;margin-right: 5%"></div>
    <div id="video" style="width:70%;height:100%;float: left"></div>
    <div  style="background-color: yellow;height: 100%;width:20%;float: left"></div>
</div>

<div class="course">
</div>

</body>

<script type="text/javascript">
    var videoObject = {
        container: '#video',//“#”代表容器的ID，“.”或“”代表容器的class
        variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
        flashplayer:false,//如果强制使用flashplayer则设置成true
        video:'${pageContext.request.contextPath}/Upload/2.mp4'//视频地址
    };
    var player=new ckplayer(videoObject);
</script>
</html>
