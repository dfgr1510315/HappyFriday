<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/9/12
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>首页</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <style type="text/css">

        body {
            background: #fffef5;
            overflow: auto;
        }

        .b1 {
            height: 80px;
            width: 80%;
            background-color: #f4f4f4;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
            font-size: 30px;
            line-height: 70px;
            border: 1px solid #dddddd;
            box-shadow: 0px 1px 1px rgba(209, 212, 221, 0.15);
            border-radius: 0;
            margin-top: 45px;
        }

        .c1 {
            height: 50px;
            width: 80%;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
            margin-top: 40px;
            font-family: "Helvetica", "Hiragino Sans GB", "Microsoft Yahei",
            sans-serif;
            font-size: 15px;
        }

        .d1 ul li {
            width: 200px;
            display: inline-block;
        }

        .carousel-inner img{
            width: 100%;
            height: 100%;
        }

        .label{
            float: left;margin-left: 13px; font-size: 12px;
        }

    </style>
</head>

<body >

<div  style="position:fixed;top:0;left:0;right:0;width: 100% ;padding-left: 40px;padding-top: 9px;padding-bottom: 9px;background-color: white; z-index: 6;">
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link active" href="#">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Resources.jsp">文档</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" href="Resources.jsp">登录</a>
        </li>
    </ul>
</div>

<div class="modal fade" id="LoginModal" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">

            <%-- 登录框头部--%>
            <div class="modal-header">
                <h4 class="modal-title">登录</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <%--登录界面--%>
            <div class="modal-body">
                登录内容。。。
            </div>

            <%--登录底部--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">。。。</button>
            </div>
        </div>
    </div>
</div>


<div id="demo" class="carousel slide" data-ride="carousel" style="height: 450px; margin-top: 56px;">

    <%--<!-- 指示符 -->
    <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="0" class="active"></li>
        <li data-target="#demo" data-slide-to="1"></li>
    </ul>--%>

    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="http://static.runoob.com/images/mix/img_fjords_wide.jpg">
            <div class="carousel-caption">
                <h3>计科吴彦祖：</h3>
            </div>
        </div>
        <div class="carousel-item">
            <img src="http://static.runoob.com/images/mix/img_nature_wide.jpg">
            <div class="carousel-caption">
                <h3>咸鱼桑</h3>
            </div>
        </div>
    </div>

    <!-- 左右切换按钮 -->
    <a class="carousel-control-prev" href="#demo" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#demo" data-slide="next">
        <span class="carousel-control-next-icon"></span>
    </a>
</div>


<%--
<div class="b1">
    <div class="retrieval" style="margin: 16px;  padding-left: 10%; padding-right: 10%;">
        <input type="text" placeholder="请输入关键字"
               style="width: 80% ;padding: 5px; padding-left: 10px;border-radius: 6px; float: left">
        <input type="submit" value="搜索" style="height: 34px;" onclick="alert('还得先搭建文档数据库');">
    </div>
</div>
--%>
<div class="b1">
    <div class="input-group mb-3">
        <input type="text" class="form-control" placeholder="检索...">
        <div class="input-group-append">
            <button class="btn btn-success" type="submit">Go</button>
        </div>
    </div>
    <span class="badge badge-pill badge-primary label" >标签</span>
    <span class="badge badge-pill badge-success label" >标签</span>
    <span class="badge badge-pill badge-info label" >标签</span>
    <span class="badge badge-pill badge-warning label" >标签</span>
    <span class="badge badge-pill badge-danger label" >标签</span>
</div>

<div class="c1">
    <div>
        <div style="width: 10%; float: left; margin-top: 10px;">资源分类</div>
        <hr style="width:80% ;  margin-top: 20px;margin-bottom: 20px; float: left;    border: solid 1px #dddddd;     border-bottom-width: 0px;">
    </div>
</div>

<div class="d1" style=" width: 80%;height: 400px;   margin-left: auto;margin-right: auto; ">
    <ul style="float: left; width: 100%;height: 54px; list-style:none; display:inline; padding-left: 70px;">
        <li>
            <a href="">HTML\CSS</a>
        </li>
        <li>
            <a href="">JavaScript</a>
        </li>
        <li>
            <a href="">服务端</a>
        </li>
        <li>
            <a href="">数据库</a>
        </li>
        <li>
            <a href="">移动端</a>
        </li>
        <li>
            <a href="">ASP.NET</a>
        </li>
        <li>
            <a href="">Web Service</a>
        </li>
        <li>
            <a href="">开发工具</a>
        </li>
        <li>
            <a href="">网站建设</a>
        </li>
        <li>
            <a href="">网站建设</a>
        </li>
        <li>
            <a href="">网站建设</a>
        </li>
        <li>
            <a href="">网站建设</a>
        </li>
    </ul>
</div>


</body>

</html>
