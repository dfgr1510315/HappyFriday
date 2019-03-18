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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>

    <style type="text/css">
        body {
            background-color: #f8fafc;
            overflow: auto;
        }

        a:hover{
            text-decoration:none
        }

        body.modal-open {
            overflow-y: auto !important;
            padding-right: 0!important;
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
            box-shadow: 0 1px 1px rgba(209, 212, 221, 0.15);
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

        .c1 hr{
            width:80% ;
            margin-top: 20px;
            margin-bottom: 20px;
            float: left;
            border: solid 1px #dddddd;
            border-bottom-width: 0;
        }

        .d1 {
            width: 80%;
            height: 400px;
            margin-left: auto;
            margin-right: auto;
        }

        .d1 ul{
            float: left;
            width: 100%;
            height: 54px;
            list-style:none;
            display:inline;
            padding-left: 70px;
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
            float: left;
            margin-left: 13px;
            font-size: 12px;
        }

        .types-content{
            width: 80%;
            min-height: 100px;
            margin-left: auto;
            margin-right: auto;
            overflow:hidden
        }

        .types-content .index-card-container {
            float: left;
            margin-left: 18px;
            border-radius: 4px;
            margin-bottom: 12px;
        }

        .course-card-container .course-card-content .course-card-name:hover{
            color: #007bff;
        }

        .course-card-container {
            width: 216px;
            position: relative;
            transition: .3s all linear;
        }

        .course-card-container .course-card-top.hashadow {
            overflow: hidden;
        }

        .course-card-container .course-card-top {
            width: 216px;
            height: 120px;
            position: relative;
            border-radius: 8px;
            transition: all .3s;
        }

        .course-card-container .course-card-top .course-banner {
            width: 100%;
            height: 100%;
            background-color: #f3f5f7;
            border-radius: 8px;
        }

        .hashadow:hover {
            box-shadow: 0 2px 8px #666;
        }

        .course-card-container .course-card-content {
            padding: 12px 8px;
        }

        .course-card-container .course-card-content .course-card-name {
            font-size: 16px;
            color: #07111B;
            line-height: 24px;
            word-wrap: break-word;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            max-height: 46px;
            transition: all .3s;
        }

        .course-card-container .course-card-content .course-card-info {
            font-size: 12px;
            color: #93999F;
            line-height: 24px;
            margin-top: 8px;
            font-weight: 400;
        }

        .course-card-container .course-card-content .course-card-info span {
            display: inline-block;
            margin-right: 12px;
        }
        .course-card-container .course-card-content .course-card-info .icon-set_sns {
            margin-right: 2px;
        }
    </style>
</head>

<body onload="ifActive()" id="homepage">

<jsp:include page="navigation.jsp"  />

<jsp:include page="LoginPC.jsp" />


<div id="demo" class="carousel slide" data-ride="carousel" style="height: 450px;margin: 71px auto auto;width: 80%; ">

    <!-- 指示符 -->
    <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="0" class="active"></li>
        <li data-target="#demo" data-slide-to="1"></li>
    </ul>

    <div class="carousel-inner" style="border-radius:4px;box-shadow: 0 12px 24px 0 rgba(7,17,27,.2);">
        <div class="carousel-item active">
            <img src="http://static.runoob.com/images/mix/img_fjords_wide.jpg">
            <div class="carousel-caption">
                <%--<h3>计科吴彦祖：</h3>--%>
            </div>
        </div>
        <div class="carousel-item">
            <img src="http://static.runoob.com/images/mix/img_nature_wide.jpg">
            <div class="carousel-caption">
                <%-- <h3>咸鱼桑</h3>--%>
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


<div class="c1">
    <div>
        <div style="width: 10%; float: left; margin-top: 10px;">最新课程</div>
        <hr>
    </div>
</div>

<div class="types-content">

</div>

<div class="c1">
    <div>
        <div style="width: 10%; float: left; margin-top: 10px;">资源分类</div>
        <hr>
    </div>
</div>


<div class="d1">
    <ul >
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
<script type="text/javascript">
    $(document).ready(function(){
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/SaveClassInfor",
            data: {
                Read_or_Save:'get_new_class',
                limit:'10',
                type:'0',
                page:'0'
            },
            dataType: 'json',
            success: function (jsonObj) {
                for (var i=0;i<jsonObj.title.length;i++){
                    $('.types-content').append(
                        ' <div class="index-card-container course-card-container container">\n' +
                        '        <a target="_blank" class="course-card" href="Learn_list.jsp?='+jsonObj.no[i]+'">\n' +
                        '            <div class="course-card-top hashadow">\n' +
                        '                <img src="${pageContext.request.contextPath}'+jsonObj.cover[i]+'" alt="" class="course-banner">\n' +
                        '            </div>\n' +
                        '            <div class="course-card-content">\n' +
                        '                <h3 class="course-card-name">'+jsonObj.title[i]+'</h3>\n' +
                        '                <div class="clearfix course-card-bottom">\n' +
                        '                    <div class="course-card-info">\n' +
                        '                        <span>'+jsonObj.type[i]+'</span>\n' +
                        '                        <span>'+jsonObj.teacher[i]+'</span>\n' +
                        '                        <span>\n' +
                        '                            <i class="icon-set_sns fa fa-user-o"></i>'+jsonObj.student_number[i]+'\n' +
                        '                        </span>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </a>\n' +
                        '    </div>'
                    )
                }
            }
        });
    });
</script>
</html>
