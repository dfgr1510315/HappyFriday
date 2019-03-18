<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>课程管理</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Myclass.css">
    <link rel="stylesheet" type="text/css" href="../webuploader-0.1.5/css/webuploader.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../webuploader-0.1.5/jekyll/js/webuploader.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Myclass.js"></script>
    <script type="text/javascript" src="../JS/drag.js"></script>
</head>
<style type="text/css">
    body.modal-open {
        overflow-y: auto !important;
        padding-right: 0!important;
    }
    >
    .biggest {
        width: 820px;
        height: 554px;
    }
    .top-right {
        float: right !important;
    }

    .head .btn-outline-primary{
        float: right;
        margin-left: 20px;
    }

    .main {
        padding: 15px;
    }

    .media .left {
        margin-right: 10px;
    }
    .left {
        float: left !important;
    }
    img {
        width: 48px;
        height: 48px;
    }

    .table thead th{
        width: 18%;
    }

    .progress{
        margin-top: 14px;
    }

    .btn-sm{
        margin-top: 8px;
    }

    .text-sm {
        color: #999;
        font-size: 12px;
        left: 65px;
        top: 45px;
    }

</style>

<body onload="ifActive();addAction(5);addHref();getHTML()">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;margin-top:30px;height: 450px">
    <div style="background-size: cover;height: 148px;margin-top: -21px">
        <jsp:include page="ui_box.jsp"/>
        <div style="width: 80%;margin: auto">
            <jsp:include page="course_manag_nav.jsp"/>
            <div class="container_right">
                <div class="head">
                    <h3 class="container_right_head">
                        学员管理
                    </h3>
                    <form  class="top-right">
                        <button type="button" class="btn btn-outline-primary">导入学员</button>
                    </form>
                    <button type="button" class="btn btn-outline-primary">添加学员</button>
                    <input class="btn btn-outline-primary" type="button" onclick="location.href='/resources/csv/course/demo.csv'" value="下载模板">
                </div>

                <table id="student_table" class="table table-striped">
                    <thead>
                    <tr>
                        <th>学员</th>
                        <th>学习进度</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>

                <ul id="page" class="pagination">

                </ul>
            </div>
        </div>
    </div>
</div>

</body>

<script type="text/javascript">
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null) return  unescape(r[2]); return null;
    }

    function remove_student(event){
        if (confirm("确定移除此学员吗？")) {
            $.ajax({
                url: "${pageContext.request.contextPath}/students",
                data: {
                    No:No,
                    student:$(event).parent().prev().prev().children().eq(0).children().eq(1).text(),
                    action:'remove_student'
                },
                type: "POST",
                dataType: "json",
                asynch: "false",
                success: function (jsonObj) {
                    if (jsonObj===1){
                        $(event).parent().parent().remove();
                    }
                }
            })
        }
    }

    $(document).ready(function(){
        $('#cover').attr('src',cover_address);
        var page = GetQueryString('page');
        if(page===null) page='1';
        $.ajax({
            url: "${pageContext.request.contextPath}/students",
            data: {
                No:No,
                page:page,
                action:'get_class_students'
            },
            type: "POST",
            dataType: "json",
            asynch: "false",
            success: function (jsonObj) {
                for (var i=0;i<jsonObj.user.length;i++){
                    $('#student_table').append(
                        '<tr>\n' +
                        '                        <td style="position: relative;">\n' +
                        '                            <a target="_blank" href="" >\n' +
                        '                                <img src="'+"${pageContext.request.contextPath}"+jsonObj.head[i]+'">\n' +
                        '                                <span>'+jsonObj.user[i]+'</span>\n' +
                        '                            </a>\n' +
                        '                            <span class="text-sm" style="position: absolute;">'+jsonObj.time[i]+' 加入</span>\n' +
                        '                        </td>\n' +
                        '                        <td>\n' +
                        '                            <div class="progress">\n' +
                        '                                <div class="progress-bar progress-bar-striped progress-bar-animated" style="width:'+jsonObj.schedule[i]+'%"></div>\n' +
                        '                            </div>\n' +
                        '                        </td>\n' +
                        '                        <td>\n' +
                        '                            <button type="button" class="btn btn-outline-primary btn-sm">设为班长</button>\n' +
                        '                            <button type="button" class="btn btn-outline-info btn-sm">发送信息</button>\n' +
                        '                            <button type="button" class="btn btn-outline-danger btn-sm" onclick="remove_student(this)">移除学员</button>\n' +
                        '                        </td>\n' +
                        '                    </tr>'
                    )
                }

                var page_ul =  $('#page');
                page_length = Math.ceil(jsonObj.count/6);
                if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)-1)+'">Previous</a></li>');
                else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                if (page-1>2&&page_length-page>=2){
                    for (i = page-2;i<=page+2;i++){
                        if (i<page_length+1){
                            if (i===parseInt(page)){
                                page_ul.append(' <li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                            }else page_ul.append(' <li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                        }
                    }
                }else {
                    if (page<=3){
                        for (i=1;i<6;i++){
                            if (i<page_length+1){
                                if (i===parseInt(page)){
                                    page_ul.append(' <li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                                }else page_ul.append(' <li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                            }
                        }
                    }else {
                        for (i=page_length-4;i<=page_length;i++){
                            if (i<page_length+1){
                                if (i===parseInt(page)){
                                    page_ul.append(' <li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                                }else page_ul.append(' <li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                            }
                        }
                    }

                }

                if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)+1)+'">Next</a></li>');
                else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
            }
        })
    })
</script>

</html>
