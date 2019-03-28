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
    #modal_class_box::-webkit-scrollbar {
        display: none;
    }
</style>

<body onload="addAction(5);addHref();getHTML();">
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
                    <button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#create_class">创建班级</button>
                    <form  class="top-right">
                        <button type="button" class="btn btn-outline-primary">导入学员</button>
                    </form>
                    <button type="button" class="btn btn-outline-primary">添加学员</button>
                    <input class="btn btn-outline-primary" type="button" onclick="location.href='/resources/csv/course/demo.csv'" value="下载模板">
                </div>
                <div id="class_box" class="table-responsive"></div>
        <%--        <div id="page_box">
                </div>
                <ul id="page" class="pagination pagination-sm"></ul>--%>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="create_class"  style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">
            <%-- 头部--%>
            <div class="modal-header">
                <div class="modal-title">请输入班级名</div>
                <a id="create_class_Close" class="close" data-dismiss="modal">&times;</a>
            </div>
            <%--界面--%>
            <div class="modal-body">
                <label for="create_class_name"></label>
                <textarea id="create_class_name" class="form-control"></textarea>
                <button type="button" class="btn btn-outline-success btn-sm" onclick="create_class()">确定
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="join_class"  style="background-color: transparent; width: 100%; top: 90px;">
    <div class="modal-dialog">
        <div class="modal-content" >
            <%-- 头部--%>
            <div class="modal-header">
                <div class="modal-title">请选择班级</div>
                <a id="join_class_Close" class="close" data-dismiss="modal">&times;</a>
            </div>
            <%--界面--%>
            <div class="modal-body" id="modal_class_box" style="max-height: 440px;overflow-y: scroll;overflow-x: hidden;">
                <table id="modal_class_table" class="table">
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    var move_student;
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
    
    function create_class() {
        var class_name = $('#create_class_name');
        $('#create_class_Close').click();
        $.ajax({
            url: "${pageContext.request.contextPath}/students",
            data: {
                No:No,
                class_name:class_name.val(),
                action:'create_class'
            },
            type: "POST",
            dataType: "json",
            asynch: "false",
            success: function (jsonObj) {
                if (jsonObj!==-1){
                    $('#class_table').prepend(
                        '<tr>\n' +
                        '   <td>'+class_name.val()+'</td>\n' +
                        '   <td><button type="button" style="margin-top:0;" class="btn btn-outline-primary btn-sm" onclick="get_class_students('+jsonObj+')">查看班级</button>' +
                        '       <button type="button" style="margin-top:0;" class="btn btn-outline-danger btn-sm" onclick="delete_student_class(this,'+jsonObj+')">删除班级</button>\n' +
                        '</td>\n' +
                        '</tr>'
                    );
                    $('#modal_class_table').append(
                        ' <tr>\n' +
                        '     <td>'+class_name.val()+'</td>\n' +
                        '     <td><button type="button" style="margin-top: 0;float: right" class="btn btn-outline-primary btn-sm" onclick="join_class()">移动至此班级</button></td>\n' +
                        '</tr>'
                    );
                    class_name.val('');
                }
            }
        })
    }

    function get_class() {
        $.ajax({
            url: "${pageContext.request.contextPath}/students",
            data: {
                No:No,
                action:'get_class'
            },
            type: "POST",
            dataType: "json",
            asynch: "false",
            success: function (jsonObj) {
                $('#class_box').append(
                    '<table id="class_table" class="table">\n' +
                    '                    <thead>\n' +
                    '                    <tr>\n' +
                    '                        <th>班级</th>\n' +
                    '                        <th>操作</th>\n' +
                    '                    </tr>\n' +
                    '                    </thead>\n' +
                    '                    <tbody></tbody>\n' +
                    '                </table>'
                );
                for (var i=0;i<jsonObj.id.length;i++){
                    $('#class_table').prepend(
                        ' <tr>\n' +
                        '        <td>'+jsonObj.name[i]+'</td>\n' +
                        '        <td>' +
                        '           <button type="button" style="margin-top: 0;" class="btn btn-outline-primary btn-sm" onclick="get_class_students('+jsonObj.id[i]+')">查看班级</button>\n' +
                        '           <button type="button" style="margin-top:0;" class="btn btn-outline-danger btn-sm" onclick="delete_student_class(this,'+jsonObj.id[i]+')">删除班级</button>\n' +
                        '       </td>\n' +
                        '      </tr>'
                    );
                    $('#modal_class_table').append(
                        ' <tr>\n' +
                        '     <td>'+jsonObj.name[i]+'</td>\n' +
                        '     <td><button type="button" style="margin-top:0;float:right" class="btn btn-outline-primary btn-sm" onclick="join_class()">移动至此班级</button></td>\n' +
                        '</tr>'
                    )
                }

            }
        })
    }

    function join_class() {
        $.ajax({
            url: "${pageContext.request.contextPath}/students",
            data: {
                id:id,
                action:'delete_class'
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

    function delete_student_class(event,id) {
        var delete_class = confirm("确定删除此班级吗?");
        if (delete_class) {
            $.ajax({
                url: "${pageContext.request.contextPath}/students",
                data: {
                    id:id,
                    action:'delete_class'
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

    function add_page(page_id,count,page) {
        var page_ul =  $(page_id);
        page_length = Math.ceil(count/6);
        if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)-1)+'">Previous</a></li>');
        else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

        if (page-1>2&&page_length-page>=2){
            for (var i = page-2;i<=page+2;i++){
                if (i<page_length+1){
                    if (i===parseInt(page)){
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                }
            }
        }else {
            if (page<=3){
                for (i=1;i<6;i++){
                    if (i<page_length+1){
                        if (i===parseInt(page)){
                            page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                        }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                    }
                }
            }else {
                for (i=page_length-4;i<=page_length;i++){
                    if (i<page_length+1){
                        if (i===parseInt(page)){
                            page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                        }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                    }
                }
            }

        }

        if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)+1)+'">Next</a></li>');
        else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
    }

    function get_class_students(id){
/*        var page = GetQueryString('page');
        if(page===null) page='1';*/
        $('.table-striped').hide();
        var student_table = $('#student_table'+id);
        console.log(student_table.length);
        if ( student_table.length > 0 ) {
            student_table.show();
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/students",
            data: {
                No:No,
                id:id,
                action:'get_class_students'
            },
            type: "POST",
            dataType: "json",
            asynch: "false",
            success: function (jsonObj) {
                var class_box = $('#class_box');
                if (jsonObj.user.length===0)  {
                    class_box.append(
                        '<div id="student_table'+id+'" class=" table table-striped">\n' +
                        '     <div style="text-align: center;color: #93999f;">此班级没有添加学员</div>\n' +
                        '</div>'
                    );
                    return;
                }

                class_box.append(
                    '  <table id="student_table'+id+'" class="table table-striped">\n' +
                    '                    <thead>\n' +
                    '                    <tr>\n' +
                    '                        <th>学员</th>\n' +
                    '                        <th>学习进度</th>\n' +
                    '                        <th>操作</th>\n' +
                    '                    </tr>\n' +
                    '                    </thead>\n' +
                    '                    <tbody></tbody>\n' +
                    '                </table>'
                );
                for (var i=0;i<jsonObj.user.length;i++){
                    $('#student_table'+id).append(
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
                        '                            <button type="button" class="btn btn-outline-info btn-sm" data-toggle="modal" data-target="#join_class" data-student="'+jsonObj.user[i]+'" onclick="change_stu(this)">移动学员</button>\n' +
                        '                            <button type="button" class="btn btn-outline-danger btn-sm" onclick="remove_student(this)">移除学员</button>\n' +
                        '                        </td>\n' +
                        '                    </tr>'
                    )
                }
                //add_page('#page',jsonObj.count,page);
            }
        })
    }

    function change_stu(event){
        move_student = $(event).data('student');
    }

    $(document).ready(function(){
        $('#cover').attr('src',cover_address);
        get_class();
        //get_class_students();
    })
</script>

</html>
