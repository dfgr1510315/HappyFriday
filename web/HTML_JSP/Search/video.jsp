<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/3/18 0018
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>搜索结果</title>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/LoginPC.js"></script>
    <style type="text/css">
        a:hover{
            text-decoration: none;
        }
        #main{
            overflow: hidden;
            width: 100%;
        }
        .search-main {
            width: 100%;
            font-family: "Microsoft YaHei","Helvetica Neue","微软雅黑",Tahoma,Arial,sans-serif;
            padding-bottom: 100px;
        }
        .search-main .search-header {
            height: 148px;
            width: 100%;
            background: #d9dde1;
            padding-top: 36px;
            box-sizing: border-box;
        }
        .search-main .search-header .search-form {
            height: 48px;
            width: 840px;
            margin: 0 auto;
            border-radius: 4px;
            background: #fff;
            box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);
            position: relative;
        }
        .search-main .search-header .search-form i {
            position: absolute;
            left: 16px;
            bottom: 12px;
            font-size: 24px;
            color: #d9dde1;
            float: left;
        }
        .search-main .search-header .search-form .search-form-ipt {
            font-size: 16px;
            color: #93999f;
            width: 684px;
            line-height: 48px;
            height: 48px;
            margin-left: 52px;
            float: left;
            outline:none;
            border: none;
        }
        .search-main .search-header .search-form .search-form-btn {
            width: 104px;
            height: 48px;
        }
        .search-main .search-header .search-form .search-history-area {
            width: 736px;
            position: absolute;
            top: 48px;
            margin-top: 1px;
            background: #fff;
            z-index: 100;
            box-sizing: border-box;
            box-shadow: 0 8px 16px 0 rgba(7,17,27,.1);
            display: none;
        }
        .search-main .search-body {
            margin: 0 auto;
            width: 1150px;
            padding-top: 16px;
            box-sizing: border-box;
        }
        .search-main .search-body .search-content .search-classify{
            margin-top: -57px;
        }

        .nav-tabs .nav-link.active{
            background-color: #f8fafc;
            border-bottom-color: #f8fafc;
        }

        .search-main .search-body .search-content .search-classify .search-related {
            line-height: 48px;
            float: left;
            color: #93999f;
            font-size: 12px;
        }
        .course-item {
            overflow: hidden;
            background: #fff;
            margin-bottom: 8px;
            padding-top: 24px;
            padding-right: 32px;
            padding-bottom: 24px;
            box-sizing: border-box;
            box-shadow: 0 4px 8px 0 rgba(7,17,27,.05);
        }
        .course-item img {
            width: 210px;
            height: 120px;
            float: left;
            margin-left: 32px;
            margin-right: 32px;
        }
        .course-item .course-item-detail {
            overflow: hidden;
        }
        .course-item .course-item-detail a {
            font-size: 16px;
            line-height: 32px;
            font-weight: 700;
            /*color: #4d555d;*/
            text-decoration: none;
        }
     /*   .course-item .course-item-detail a :hover {
            color: #1c1f21;
        }*/
        .course-item .course-item-detail .highlight {
            color: red;
        }
        .course-item .course-item-detail .course-item-classify {
            margin-top: 4px;
            margin-bottom: 4px;
        }
        .course-item .course-item-detail .course-item-classify span {
            line-height: 24px;
            color: #4d555d;
            font-size: 12px;
            margin-right: 24px;
            padding-left: 2px;
            vertical-align: middle;
        }
        .course-item .course-item-detail p {
            color: #4d555d;
            font-size: 12px;
            line-height: 24px;
        }
        #page{
            margin: 25px 0 auto;
            overflow: hidden;
            clear: both;
            text-align: center;
        }

        .last a, .last span {
            padding: 0 12px;
            line-height: 2.5;
        }
    </style>
    <script type="text/javascript">
        var keyword = GetQueryString('keyword');
        $(document).ready(function () {
            if (keyword.trim()==='')  return;
            $('#search_ipt').val(keyword);
            search_class();
        });

        function GetQueryString(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = decodeURI(window.location.search).substr(1).match(reg);
            if(r!=null) return  unescape(r[2]); return null;
        }

        function get_class_type(class_type_in) {
            var class_type;
            console.log(class_type_in);
            switch (class_type_in) {
                case 1:
                    class_type='前端设计';
                    break;
                case 2:
                    class_type='后台设计';
                    break;
                case 3:
                    class_type='基础理论';
                    break;
                case 4:
                    class_type='嵌入式';
                    break;
                case 5:
                    class_type='移动开发';
                    break;
                case 6:
                    class_type='项目发布';
                    break;
            }
            return class_type;
        }

        function highlight()
        {
            clearSelection();//先清空一下上次高亮显示的内容；
            var searchText = $('#search_ipt').val();//获取你输入的关键字；
            var regExp = new RegExp(searchText, 'g');//创建正则表达式，g表示全局的，如果不用g，则查找到第一个就不会继续向下查找了；
            $('p').each(function()//遍历文章；
            {
                var html = $(this).html();
                var newHtml = html.replace(regExp, '<span class="highlight" >'+searchText+'</span>');//将找到的关键字替换，加上highlight属性；
                $(this).html(newHtml);//更新文章；
            });
            $('.course-item-detail a').each(function()//遍历文章；
            {
                var html = $(this).html();
                var newHtml = html.replace(regExp, '<span class="highlight" >'+searchText+'</span>');//将找到的关键字替换，加上highlight属性；
                $(this).html(newHtml);//更新文章；
            });
        }

        function clearSelection()
        {
            $('p').each(function()//遍历
            {
                $(this).find('.highlight').each(function()//找到所有highlight属性的元素；
                {
                    $(this).replaceWith($(this).html());//将他们的属性去掉；
                });
            });
            $('.course-item-detail a').each(function()//遍历
            {
                $(this).find('.highlight').each(function()//找到所有highlight属性的元素；
                {
                    $(this).replaceWith($(this).html());//将他们的属性去掉；
                });
            });
        }

        function page_ul(page,count) {
            var page_ul =  $('#page');
            page_length = Math.ceil(count/6);
            if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?page_ask='+(parseInt(page)-1)+'">Previous</a></li>');
            else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

            if (page-1>2&&page_length-page>=2){
                for (var i = page-2;i<=page+2;i++){
                    if (i<page_length+1){
                        if (i===parseInt(page)){
                            page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                        }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                    }
                }
            }else {
                if (page<=3){
                    for (i=1;i<6;i++){
                        if (i<page_length+1){
                            if (i===parseInt(page)){
                                page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                            }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                        }
                    }
                }else {
                    for (i=page_length-4;i<=page_length;i++){
                        if (i<page_length+1){
                            if (i===parseInt(page)){
                                page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                            }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                        }
                    }
                }

            }

            if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?page_ask='+(parseInt(page)+1)+'">Next</a></li>');
            else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>');

            page_ul.append(
                '<li class="page-item last">\n' +
                '<a >\n' +
                '<label>\n' +
                '<input id="jump" type="text" class="form-control" onkeydown="go();" style="width: 50px;">\n' +
                '</label>\n' +
                '<span id="count_page"> </span>\n' +
                '</a>\n' +
                '</li>');
            $('#count_page').text('/ '+page_length);
        }

        function go(){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode  === 13) {
                var jump = $('#jump');
                if (jump.val()>0&&jump.val()<=page_length&&jump.val()%1 === 0){
                    window.location.href="Student_commentary.jsp?"+jump.val();
                }else alert('请重新输入页号');
            }
        }
        
        function search_to() {
            var new_keyword = $('#search_ipt').val();
            console.log(new_keyword+'!!!');
            if (new_keyword===keyword || new_keyword.trim()==='') return;
            window.location.href='${pageContext.request.contextPath}/HTML_JSP/Search/video.jsp?keyword='+encodeURI(new_keyword);
        }

        function search_class() {
            var keyword = $('#search_ipt').val();
            if(keyword.trim()==='') return;
            var page = GetQueryString('page_note');
            if (page == null) page = '1';
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/SaveClassInfor",
                data: {
                    page:page,
                    Read_or_Save:'search_class',
                    keyword:keyword
                },
                dataType: 'json',
                success: function (jsonObj) {
                    $('#count').text('共找到'+jsonObj.count+'个结果');
                    if (jsonObj.class_id.length===0) return;
                    for (var i=0;i<jsonObj.class_id.length;i++){
                        var class_type = get_class_type(jsonObj.class_type[i]);
                        $('.search-content').append(
                            ' <div class="course-item">\n' +
                            '                    <a href="../Learn_list.jsp?='+jsonObj.class_id[i]+'" target="_blank" class="course-detail-title">\n' +
                            '                        <img src="${pageContext.request.contextPath}'+jsonObj.cover_address[i]+'" alt="">\n' +
                            '                    </a>\n' +
                            '                    <div class="course-item-detail">\n' +
                            '                        <a href="../Learn_list.jsp?='+jsonObj.class_id[i]+'" target="_blank" class="course-detail-title">\n' +
                            '                            '+jsonObj.class_title[i]+'\n' +
                            '                        </a>\n' +
                            '                        <div class="course-item-classify">\n' +
                            '                            <span>'+class_type+'</span>\n' +
                            '                            <span>教师：'+jsonObj.teacher[i]+'</span>\n' +
                            '                            <i class="fa fa-user-o" style="font-size: 11px;"></i><span>'+jsonObj.student_count[i]+'</span>\n' +
                            '                        </div>\n' +
                            '                        <p>\n' +
                            '                            '+jsonObj.outline[i]+'\n' +
                            '                        </p>\n' +
                            '                    </div>\n' +
                            '                </div>'
                        )
                    }
                    page_ul(page,jsonObj.count);
                    highlight();
                }
            });
        }

    </script>
</head>
<body style="background: #f8fafc;">
<jsp:include page="../navigation.jsp"/>
<div id="main">
    <div class="search-main">
        <div class="search-header">
            <div class="search-form">
                <i class="fa fa-search"></i>
                <input id="search_ipt" type="text" class="search-form-ipt js-search-ipt" value="数" placeholder="请输入想搜索的内容">
                <button type="button" class="search-form-btn js-search-btn btn btn-light" onclick="search_to()">搜索</button>
                <div class="search-history-area js-search-history"></div>
            </div>
        </div>
        <div class="search-body">
            <div class="search-content">
                <div class="search-classify clearfix">
                    <ul class="nav nav-tabs nav-justified">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">课程</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">用户</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">问答</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">笔记</a>
                        </li>
                    </ul>
                    <span class="search-related js-all-count">
                        <em id="count" style="font-style: normal;"></em>
                    </span>
                </div>
            </div>
            <div id="page" class="pagination"></div>
        </div>

    </div>
</div>
</body>
</html>
