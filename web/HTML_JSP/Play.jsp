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
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/ckplayer/ckplayer/ckplayer.js"></script>


<body>
<div>
    <div id="video" style="width:600px;height:400px;"></div>
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
