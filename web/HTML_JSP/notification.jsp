<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/10 0010
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/My_question.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <style type="text/css">
        body{
            background-color: #f8fafc;
        }
        body.modal-open {
            overflow-y: auto !important;
            padding-right: 0!important;
        }
        tbody tr td:not(:first-child){
            padding-top: 32px !important;
        }

        td button{
            margin-top: -7px;
        }

        .cover{
            width: 100px;height: 65px;margin-right: 5px
        }

        .found_class {
            display: inline-block;
            margin-bottom: 5px;
            font-weight: bold;
            margin-top: 5px;
        }

        .found_class_text{
            height: 40px !important;
            resize: none;
            width: 80%;
            float: right;
        }
        #notice_box .list-group .list-group-item .pull-left{
            margin-right: 10px;
            float: left;
            margin-top: 4px;
        }
        #notice_box .list-group .list-group-item{
            height: 74px;
            overflow: hidden;
            zoom: 1;
            margin-bottom: 10px;
        }
        #notice_box .list-group .list-group-item .item_body{
            overflow: hidden;
            zoom: 1;
        }
        #notice_box .list-group .list-group-item .item_body .notification-body{
            margin-bottom: 6px;
        }
        #notice_box .list-group .list-group-item .item_body .notification-time{
            font-size: 12px;
            color: #999;
        }
        #notice_box .list-group .list-group-item .item_body .notification-time .delete{
            float: right;
        }
        .no_find_class {
            width: 140px;
            margin-left: auto;
            margin-top: 103px;
            margin-right: auto;
            font-size: 35px;
            font-weight: 300;
            color: #93999f;
        }
        .delete{
            cursor: pointer;
        }
        .delete:hover{
            color: #1c1f21;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            get_notice();
        });

        function GetQueryString(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null) return  unescape(r[2]); return null;
        }

        function notice_box_ul(notice,time,notice_id) {
            $('#notice_box_ul').append(
                '<li class="list-group-item">\n' +
                '                        <div class="pull-left">\n' +
                '                            <i class="fa fa-bullhorn"></i>\n' +
                '                        </div>\n' +
                '                        <div class="item_body">\n' +
                '                            <div class="notification-body">\n' +
                notice +
                '                            </div>\n' +
                '                            <div class="notification-time">\n' +
                time +
                '                                <div class="delete">\n' +
                '                                    <i class="fa fa-trash-o" style="font-size:19px" data-id="'+notice_id+'" onclick="delete_notice(this)"></i>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </li>'
            )
        }

        function delete_notice(event) {
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/notification",
                data: {
                    action: 'delete_notice',
                    notice_id:$(event).data('id')
                },
                dataType: 'json',
                success: function (jsonObj) {
                    if (jsonObj===1){
                        $(event).parent().parent().parent().parent().remove();
                    }
                }
            })
        }
        
        function go(){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode  === 13) {
                var jump = $('#jump');
                if (jump.val()>0&&jump.val()<=page_length&&jump.val()%1 === 0){
                    window.location.href="notification.jsp?page="+jump.val();
                }else alert('请重新输入页号');
            }
        }

        function get_notice() {
            var page = GetQueryString('page');
            if (page == null) page = '1';
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/notification",
                data: {
                    action: 'get_notice',
                    user: user,
                    page:page
                },
                dataType: 'json',
                success: function (jsonObj) {
                    for (var i=0;i<jsonObj.notice_id.length;i++){
                        var notice;
                        switch (jsonObj.notice_type[i]) {
                            case 1:
                                notice = "学员<a href='' target='_blank'>"+jsonObj.user[i]+"</a>加入了课程<a href='Learn_list.jsp?class_id="+jsonObj.class_id[i]+"' target='_blank'>《"+jsonObj.class_title[i]+"》</a>\n";
                                break;
                            case 2:
                                notice = "学员<a href='' target='_blank'>"+jsonObj.user[i]+"</a>在课程<a href='Learn_list.jsp?class_id="+jsonObj.class_id[i]+"' target='_blank'>《"+jsonObj.class_title[i]+"》</a>发表了问题<a href='questions.jsp?"+jsonObj.ask_id[i]+"' target='_blank'>"+jsonObj.describe[i]+"</a>\n";
                                break;
                            case 3:
                                notice = "用户<a href='' target='_blank'>"+jsonObj.user[i]+"</a>在你发布的问题<a href='questions.jsp?"+jsonObj.ask_id[i]+"' target='_blank'>"+jsonObj.describe[i]+"</a>中回答了你";
                                break;
                            case 4:
                                notice = "用户<a href='' target='_blank'>"+jsonObj.user[i]+"</a>在<a href='questions.jsp?"+jsonObj.ask_id[i]+"' target='_blank'>"+jsonObj.describe[i]+"</a>中回复了你";
                                break
                        }
                        notice_box_ul(notice,jsonObj.time[i],jsonObj.notice_id[i])
                    }
                    var notice_box_ul_var = $('#notice_box_ul');
                    if (notice_box_ul_var.html().length === 0) {
                        notice_box_ul_var.append(
                            '<div class="no_find_class">暂无通知</div>'
                        )
                    }else {
                        var page_ul =  $('#page_ask');
                        page_length = Math.ceil(jsonObj.count/6);
                        if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?page='+(parseInt(page)-1)+'">Previous</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                        if (page-1>2&&page_length-page>=2){
                            for (i = page-2;i<=page+2;i++){
                                if (i<page_length+1){
                                    if (i===parseInt(page)){
                                        page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                }
                            }
                        }else {
                            if (page<=3){
                                for (i=1;i<6;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                        }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }else {
                                for (i=page_length-4;i<=page_length;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                        }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }

                        }

                        if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?page='+(parseInt(page)+1)+'">Next</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>');

                        page_ul.append(
                            '<li class="page-item last">\n' +
                            '<a >\n' +
                            '<label>\n' +
                            '<input id="jump" type="text" class="form-control" onkeydown="go();">\n' +
                            '</label>\n' +
                            '<span id="count_page"> </span>\n' +
                            '</a>\n' +
                            '</li>');
                        $('#count_page').text('/ '+page_length);
                    }
                }
            });
        }
    </script>

</head>
<body onload="ifActive();addClass(9)">
<jsp:include page="navigation.html"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <h3 class="container_right_head">
                我的通知
            </h3>
            <div id="notice_box">
                <ul class="list-group" id="notice_box_ul"></ul>
            </div>
            <ul id="page_ask" class="pagination pagination-sm"></ul>
        </div>
    </div>
</div>

</body>
</html>
