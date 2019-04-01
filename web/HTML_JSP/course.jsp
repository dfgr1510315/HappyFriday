<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/2/15 0015
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程</title>
    <link rel="stylesheet" type="text/css" href="../CSS/Learn_list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Learn_list.js"></script>
    <style type="text/css">
        .types-content {
            width: 100%;
            min-height: 100px;
            margin-left: auto;
            margin-right: auto;
            overflow: hidden;
        }

        .types-content .index-card-container {
            float: left;
            margin-left: -4px;
            border-radius: 4px;
            margin-bottom: 12px;
        }

        .course-card-container {
            width: 247px;
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

        .course-card-container .course-card-content .course-card-info span {
            display: inline-block;
            margin-right: 12px;
        }

        .course-card-container .course-card-content .course-card-info .icon-set_sns {
            margin-right: 2px;
        }

        .hashadow:hover {
            box-shadow: 0 2px 8px #666;
        }

        .course-card-container .course-card-content .course-card-name:hover{
            color: #007bff;
        }

        .course-card-content .course-card-bottom .course-card-desc {
            font-size: 12px;
            font-weight: 300;
            color: #93999f;
            line-height: 22px;
            margin-top: 2px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            height: 44px;
        }

        .no_find_class{
            width: 15%;
            margin-left: auto;
            margin-top: 103px;
            margin-right: auto;
            font-size: 35px;
            font-weight: 300;
            color: #93999f;
        }
    </style>

    <script type="text/javascript">
        function GetQueryString(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null) return  unescape(r[2]); return null;
        }

        function add_class(class_no,cover,title,type,teacher,student_number,outline){
            $('.types-content').append(
                '<div class="index-card-container course-card-container container">\n' +
                '                    <a target="_blank" class="course-card" href="Learn_list.jsp?class_id='+class_no+'">\n' +
                '                        <div class="course-card-top hashadow">\n' +
                '                            <img src="${pageContext.request.contextPath}'+cover+'"  class="course-banner">\n' +
                '                        </div>\n' +
                '                        <div class="course-card-content">\n' +
                '                            <h3 class="course-card-name">'+title+'</h3>\n' +
                '                            <div class="clearfix course-card-bottom">\n' +
                '                                <div class="course-card-info">\n' +
                '                                    <span>'+type+'</span>\n' +
                '                                    <span>'+teacher+'</span>\n' +
                '                                    <span><i class="icon-set_sns fa fa-user-o"></i>'+student_number+'</span>\n' +
                '                                </div>\n' +
                '                                <p class="course-card-desc">'+outline+'</p>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </a>\n' +
                '                </div>'
            )
        }

        $(document).ready(function(){
            $('.user_navigation .nav-pills li:eq(3) a').css('color','white');
            var type = GetQueryString('type');
            if (type==null) type ='0';
            $('#nav_type').children().eq(type).children().addClass('active');
            var page = GetQueryString('page');
            if(page == null) page='1';
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/SaveClassInfor",
                data: {
                    Read_or_Save:'get_new_class',
                    type:type,
                    limit:'0',
                    page:page
                },
                dataType: 'json',
                success: function (jsonObj) {
                    if (jsonObj.title.length===0) $('.types-content').append(
                        '<div class="no_find_class">暂无课程</div>'
                    ) ;
                    else {
                        for (var i=0;i<jsonObj.title.length;i++){
                            add_class(jsonObj.no[i],jsonObj.cover[i],jsonObj.title[i],jsonObj.type[i],jsonObj.teacher[i],jsonObj.student_number[i],jsonObj.outline[i]);
                        }

                        var page_ul =  $('#page');
                        page_length = Math.ceil(jsonObj.count/25);
                        if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+(parseInt(page)-1)+'">Previous</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                        if (page-1>2&&page_length-page>=2){
                            for (i = page-2;i<=page+2;i++){
                                if (i<page_length+1){
                                    if (i===parseInt(page)){
                                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                    }else page_ul.append(' <li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                }
                            }
                        }else {
                            if (page<=3){
                                for (i=1;i<6;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append(' <li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                        }else page_ul.append(' <li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }else {
                                for (i=page_length-4;i<=page_length;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append(' <li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                        }else page_ul.append(' <li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }

                        }

                        if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+(parseInt(page)+1)+'">Next</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
                    }
                }
            });
        });
    </script>
</head>
<body style="background-color: #f8fafc;">
<jsp:include page="navigation.html"/>
<jsp:include page="LoginPC.jsp" />
<div style="width: 80%;margin-right: auto;margin-left: auto;margin-bottom: 50px;">
    <!-- Nav pills -->
    <ul id="nav_type" class="nav nav-pills"  style="margin-top: 48px;">
        <li class="nav-item">
            <a class="nav-link"  href="course.jsp">全部</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=1">前端设计</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=2">后台开发</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=3">移动开发</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=4">嵌入式</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=5">基础理论</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="?type=6">项目发布</a>
        </li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div  class=" tab-pane active"><br>
            <div class="types-content"></div>
            <ul id="page" class="pagination pagination-sm"></ul>
        </div>
    </div>
</div>
</body>
</html>
