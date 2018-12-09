<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/9 0009
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">

    <link href="${pageContext.request.contextPath}/CSS/navigationBar.css" rel="stylesheet">

</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-2">
            <div class="panel panel-default" style="">
                <!-- <div class="panel-heading">图解CSS3</div>-->
                <div class="panel-body">
                    <div class="list-group" >
                        <div class="list-group-item-heading">账号设置</div>
                        <a href="basicInformation.html" target="information" class="list-group-item " style="border:0px"><i class="glyphicon glyphicon-user"></i>基础信息</a>
                        <a href="uploadImg.html" target="uploading" class="list-group-item " style="border:0px" ><i class="glyphicon glyphicon-picture"></i>头像设置</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-lock"></i>密码修改</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-lock"></i>解除账号绑定</a>
                    </div>
                    <div class="list-group" >
                        <div class="list-group-item-heading">我的学习</div>
                        <a href="MyCourse.html" target="mycourse" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-education"></i>我的课程</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-question-sign"></i>我提出的评论</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-ok-sign"></i>我回复的评论</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-tags"></i>    我的笔记</a>
                    </div>
                    <div class="list-group" >
                        <div class="list-group-item-heading">订单</div>
                        <a href="" class="list-group-item" style="border:0px">我的订单</a>

                    </div>
                    <div class="list-group" >
                        <div class="list-group-item-heading">我的消息</div>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-comment"></i>我的通知</a>
                        <a href="" class="list-group-item" style="border:0px"><i class="glyphicon glyphicon-envelope"></i>我的私信</a>
                    </div>
                </div>

            </div>
        </div>


    </div>
</div>
<div class="col-lg-10">
    ghertyertuer
</div>

</body>
</html>
