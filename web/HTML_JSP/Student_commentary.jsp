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
    <title>在教课程</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
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

        .fa-comments{
            margin-right: 5px;
        }

        body{
            background-color: #f8fafc;
        }
        div{
            display: block;
            font-family: "Microsoft YaHei",arial !important;
            margin: 0;
        }

        .main-col{
            min-height: 546px;
        }
        .main-default{
            border-color: #ddd;
        }
        .main-col .main-heading {
            background: transparent;
            font-weight: bold;
            color: #393d4d;
            padding: 20px 20px;
        }
        .main-body{
            padding: 15px;
        }
        .media-list {
            padding-left: 0;
            list-style: none;
            margin: 0px;
        }
        ul{
            display: block;
            -webkit-margin-before: 1em;
            -webkit-margin-after: 1em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
            -webkit-padding-start: 40px;
        }
        li{
            display: list-item;
            text-align: -webkit-match-parent;
        }
        .media{
            overflow: hidden;
            zoom: 1;
            margin-top: 15px;
        }
        .media:first-child {
            margin-top: 0;
        }
        .media-list .media:last-child {
            border-bottom: none;
        }
        .media-list .media {
            border-bottom: 1px solid #eee;
            padding-bottom: 15px;
        }
        .media-body {
            overflow: hidden;
            zoom: 1;
        }
        .member{
            margin-bottom: 10px;
        }

        .member a{
            color: #333;
            font-family: "Microsoft YaHei",arial !important;
            text-decoration: none;
            background-color: transparent;
        }

        a:hover {
            text-decoration: none;
        }

        .member a:hover,.text-mute a:hover{
            color: #007bff
        }

        strong {
            font-weight: bold;
        }
        .text-normal {
            font-size: 12px;
        }
        .text-mute {  /*减淡字体颜色，突出显示效果*/
            color: #999;
        }
        .text-mute a {  /*减淡字体颜色，突出显示效果*/
            color: #07111b;
        }

        .media-body .bullet {  /*间隔点*/
            padding: 0 3px;
            font-size: 75%;
            color: #ccc;
            line-height: 1.4;
        }
        .last-row{  /*最后一行*/
            width: 50%;
            margin-left: auto;
            margin-right: auto;
        }

        .last a{
            border-top-right-radius: 4px;
            border-bottom-right-radius: 4px;
        }
        .last  a, .last  span {
            padding: 0 12px;
            line-height: 2.5;
        }
        .form-control{
            display: inline;
            width: 52px
        }

    </style>
    <script type="text/javascript">
        var page_length;
        $(document).ready(function(){
            var page = window.location.search.replace('?','');
            if(page==='') page='1';
            $.ajax({
                url: ${pageContext.request.contextPath}"/postask",
                data: {
                    page:page,
                    user:user,
                    action:'get_All_ask'
                },
                type: "POST",
                dataType: "json",
                asynch: "false",
                success: function (jsonObj) {
                    var page_ul =  $('#page');
                    page_length = Math.ceil(jsonObj.count/6);
                    if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?'+(parseInt(page)-1)+'">Previous</a></li>');
                    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                    if (page-1>2&&page_length-page>=2){
                        for (var i = page-2;i<=page+2;i++){
                            if (i<page_length+1){
                                if (i===parseInt(page)){
                                    page_ul.append(' <li class="page-item active"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                                }else page_ul.append(' <li class="page-item"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                            }
                        }
                    }else {
                        if (page<=3){
                            for (i=1;i<6;i++){
                                if (i<page_length+1){
                                    if (i===parseInt(page)){
                                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                                    }else page_ul.append(' <li class="page-item"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                                }
                            }
                        }else {
                            for (i=page_length-4;i<=page_length;i++){
                                if (i<page_length+1){
                                    if (i===parseInt(page)){
                                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                                    }else page_ul.append(' <li class="page-item"><a class="page-link" href="?'+i+'">'+i+'</a></li>');
                                }
                            }
                        }

                    }

                    if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?'+(parseInt(page)+1)+'">Next</a></li>');
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

                    for (i=0;i<jsonObj.title.length;i++){
                        $('#list').append(
                            '<li class="media">\n' +
                            '     <div class="media-body">\n' +
                            '          <div class="member">\n' +
                            '               <a href="#"><i class="fa fa-comments"></i><strong>'+jsonObj.describe[i]+'</strong></a>\n' +
                            '          </div>\n' +
                            '          <div class="text-normal text-mute">\n' +
                            '               <span>发表于<a href="https://www.bilibili.com/" class="link-info">'+jsonObj.title[i]+'</a></span>\n' +
                            '               <span class="bullet">.</span>\n' +
                            '               <span>0 回复</span>\n' +
                            '               <span class="bullet">.</span>\n' +
                            '               <span>创建于 '+jsonObj.time[i]+'</span>\n' +
                            '          </div>\n' +
                            '      </div>\n' +
                            '</li>'
                        );
                    }
                }
            });
        });

        function go(){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode  === 13) {
                var jump = $('#jump');
                if (jump.val()>0&&jump.val()<=page_length&&jump.val()%1 === 0){
                    window.location.href="Student_commentary.jsp?"+jump.val();
                }else alert('请重新输入页号');
            }
        }


    </script>

</head>
<body onload="checkCookie();ifActive();addClass(4)">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <div>
                <h3 class="container_right_head">
                    学员讨论
                </h3>
                <div class="main-default main-col">
                    <div class="main-body">
                        <ul id="list" class="media-list">


                        </ul>
                        <div class="last-row">
                            <ul id="page" class="pagination">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
