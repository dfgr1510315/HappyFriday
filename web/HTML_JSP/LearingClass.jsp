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
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <style type="text/css">
        body {
            background-color: #f8fafc;
        }

        body.modal-open {
            overflow-y: auto !important;
            padding-right: 0 !important;
        }

        tbody tr td:not(:first-child) {
            padding-top: 32px !important;
        }

        td button {
            margin-top: -7px;
        }

        * {
            list-style: none;
        }

        a:hover {
            text-decoration: none;
        }

        .title-list li {
            float: left;
        }

        .title-list li div {
            height: 33px;
            width: 70px;
            background-color: #fff; /* 鼠标未选中背景颜色 */
            border-radius: 5px;
            text-align: center;
            line-height: 33px;
        }

        .title-list li a {
            color: #333; /* 鼠标未选中字体颜色 */
        }

        .title-list li a div:hover {
            color: green; /* 鼠标悬浮字体颜色 */
            background-color: #ccc; /* 鼠标悬浮背景颜色 */
        }

        .a_course a {
            display: block;
            width: 254px;
            height: 159px;
        }

        .a_course a .picture {
            height: 159px;
            width: 254px;
        }

        .a_course a button {
            height: 30px;
            width: 70px;
            border: 1px solid #285e8e;
            border-radius: 5px;
            outline: none;
            background-color: #428bca;
            text-align: center;
            line-height: 30px;
            color: #fff;
            font-weight: normal;
            margin: 21px 86px;

        }

        .a_course a button:hover {
            background-color: #21629b;
        }

        .course-one {
            padding: 25px 0;
            position: relative;
            border-bottom: 1px solid #eff1f0;
        }

        .tab-content ul {
            padding-left: 25px;
        }

        .l {
            float: left;
        }

        .course-list-cont {
            padding-left: 230px;
        }

        .course-list-cont .study-hd {
            font-size: 18px;
            color: #12171b;
            height: 29px;
            line-height: 29px;
            position: relative;
        }

        .course-list-cont .study-hd a {
            font-weight: 700;
            color: #1c1f21;
            margin-right: 17px;
        }

        .course-list-cont .study-hd a:hover {
            color: #007bff
        }

        .study-hd .share-box {
            top: -8px;
            position: absolute;
            right: 35px;
            width: 72px;
            overflow: visible;
        }

        .study-points {
            padding: 10px 0 22px;
            height: 21px;
        }

        .study-points .i-left {
            color: #f01400;
        }

        .study-points span {
            margin-right: 14px;
        }

        .catog-points {
            position: relative;
            word-wrap: break-word;
        }

        .catog-points span {
            display: inline-block;
            width: 135px;
            margin-top: 20px;
        }

        .span-common {
            font-size: 14px;
            color: #787d82;
        }

        .catog-points a {
            color: #787d82;
        }

        .catog-points a:hover {
            color: #1c1f21;
        }

        .no_find_class {
            width: 16%;
            margin-left: auto;
            margin-top: 103px;
            margin-right: auto;
            font-size: 35px;
            font-weight: 300;
            color: #93999f;
        }
    </style>
    <script type="text/javascript">
        function addUL(_ul,json,i,note,ask) {
            var star;
            var action;
            if (json.collection[i] === '1') {
                star='star';
                action = 0;
            }
            else {
                star = 'star-o';
                action = 1;
            }
            _ul.append(
                '<li class="course-one type' + json.class_type[i] + '">\n' +
                '   <div class="course-list-img l">\n' +
                '   <a href="" target="_blank">\n' +
                '        <img width="200" height="113" src="' +"${pageContext.request.contextPath}"+json.img_address[i] + '" />\n' +
                '   </a>\n' +
                '   </div>\n' +
                '   <div class="course-list-cont">\n' +
                '       <h3 class="study-hd">\n' +
                '            <a href="Learn_list.jsp?=' + json.class_no[i] + '" target="_blank">' + json.title[i] + '</a>\n' +
                '            <div class="share-box">\n' +
                '                  <a href="javascript:void(0);" onclick="collection(this,'+action+')" title="收藏" ><i class="fa fa-'+star+'"></i></a>\n' +
                '                  <a href="javascript:void(0);" onclick="delete_class_no(' + json.class_no[i] + ')" data-toggle="modal"  data-target="#Delete_Class"  title="删除" ><i class="fa fa-trash-o"></i></a>\n' +
                '            </div>\n' +
                '       </h3>\n' +
                '       <div class="study-points">\n' +
                '             <span class="i-left span-common">已学' + json.schedule[i] + '%</span>\n' +
                '             <span class="i-right span-common">学习至' + json.last_time[i] + '</span>\n' +
                '       </div>\n' +
                '       <div class="catog-points">\n' +
                '              <span class="i-left span-common"><a target="_blank" href="Learn_list.jsp?=' + json.class_no[i] + '&page_note=1">笔记 ' + note + '</a></span>\n' +
                '              <span class="i-right span-common"><a target="_blank" href="Learn_list.jsp?=' + json.class_no[i] + '&page_ask=1">问答 ' + ask + '</a></span>\n' +
                '              <button type="button" class="btn btn-outline-primary" style="right: 32px;position: absolute;" onclick="continue_class(' + json.class_no[i] + ',\'' + json.last_time[i] + '\')">继续学习</button>\n' +
                '       </div>\n' +
                '</li>'
            );
        }

        $(document).ready(function () {
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/get_teaching",
                data: {
                    type: 'get_my_class'
                },
                dataType: 'json',
                success: function (json) {
                    var collection_ul = $('#collection_ul');
                    var learning_ul = $('#learning_ul');
                    var learned_ul = $('#learned_ul');
                    var note_count = json.note_count;
                    var note_count_no = json.note_count_no;
                    var ask_count = json.ask_count;
                    var ask_count_no = json.ask_count_no;
                    for (var i = 0; i < json.class_no.length; i++) {
                        var note;
                        if (note_count_no.indexOf(json.class_no[i]) !== -1) {
                            note = note_count[note_count_no.indexOf(json.class_no[i])];
                        } else note = '0';
                        var ask;
                        if (ask_count_no.indexOf(json.class_no[i]) !== -1) {
                            ask = ask_count[ask_count_no.indexOf(json.class_no[i])];
                        } else ask = '0';
                        if (json.schedule[i] === 100) {
                            addUL(learned_ul,json,i,note,ask)
                        } else {
                            addUL(learning_ul,json,i,note,ask)
                        }
                        if (json.collection[i] === '1'){
                            addUL(collection_ul,json,i,note,ask)
                        }
                    }
                    if (learning_ul.html().length === 0) {
                        learning_ul.append(
                            ' <div class="no_find_class">暂无课程</div> '
                        )
                    }
                    if (collection_ul.html().length === 0) {
                        collection_ul.append(
                            ' <div class="no_find_class">暂无课程</div> '
                        )
                    }
                    if (learned_ul.html().length === 0) {
                        learned_ul.append(
                            ' <div class="no_find_class">暂无课程</div> '
                        )
                    }
                }

            });
        });

        function collection(event, action) {
            var class_no = $(event).parent().prev().attr("href");
            var type;
            if (action === 1) type = 'add_collection';
            else type = 'remove_collection';
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/get_teaching",
                data: {
                    class_no: class_no.substring(class_no.lastIndexOf("=") + 1, class_no.length),
                    type: type
                },
                dataType: 'json',
                success: function (json) {
                    if (json.msg === '1') {
                        $(event).children().removeClass("fa-star-o").addClass("fa-star");
                    } else {
                        $(event).children().removeClass("fa-star").addClass("fa-star-o");
                    }
                }
            });
        }

        $(function () {
            $('#Delete_Class').modal("hide");
        });

        function delete_class_no(ID) {
            $('#Delete_Class_sure_button').attr("onclick", 'delete_class(' + ID + ')');
        }

        function delete_class(ID) {
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/get_teaching",
                data: {
                    class_no: ID,
                    type: 'delete_class'
                },
                dataType: 'json',
                success: function (json) {
                    if (json.msg === '1') {
                        $('#Delete_Class_Close').click();
                        location.reload();
                    }
                }
            });
        }

        function screen(event, type) {
            $(event).parent().prev().children().text($(event).text());
            if (type !== 0) {
                for (var i = 1; i <= 6; i++) {
                    if (i === type) $('.type' + i).css('display', 'block');
                    else $('.type' + i).css('display', 'none');
                }
            } else {
                for (i = 1; i <= 6; i++) {
                    $('.type' + i).css('display', 'block');
                }
            }
        }

        function continue_class(No, Unit_no) {
            window.location.href = "Play.jsp?" + No + "/" + Unit_no.substring(0, Unit_no.indexOf(','));
        }
    </script>

</head>
<body onload="checkCookie();ifActive();addClass(5)">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <div>
                <h3 class="container_right_head">
                    我的课程
                </h3>
                <div style="margin-bottom: 12px;">
                    <div style="width: 88%;float: left;">
                        <ul class="title-list nav nav-pills ">
                            <li class="nav-item"><a class="nav-link active" data-toggle="pill" href="#learning">学习中</a>
                            </li>
                            <li class="nav-item"><a class="nav-link" data-toggle="pill" href="#learned">已学完</a></li>
                            <li class="nav-item"><a class="nav-link" data-toggle="pill" href="#collection">收藏</a></li>
                        </ul>
                    </div>

                    <div class="dropdown" style="margin-left: 40%;">
                        <button type="button" class="btn btn-outline-primary dropdown-toggle" data-toggle="dropdown">
                            <span>全部课程</span>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,0)">全部课程</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,1)">前端设计</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,2)">后台设计</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,3)">基础理论</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,4)">嵌入式</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,5)">移动开发</a>
                            <a class="dropdown-item" href="javascript:void(0);" onclick="screen(this,6)">项目发布</a>
                        </div>
                    </div>

                </div>
                <div class="tab-content">
                    <div id="learning" class="tab-pane active">
                        <ul id="learning_ul"></ul>
                    </div>

                    <div id="learned" class="tab-pane fade">
                        <ul id="learned_ul"></ul>
                    </div>

                    <div id="collection" class="tab-pane fade">
                        <ul id="collection_ul"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="Delete_Class">
    <div class="modal-dialog">
        <div class="modal-content" style="margin-top: 40%">
            <%-- 头部--%>
            <div class="modal-header">
                <h5 class="modal-title">提示</h5>
                <button id="Delete_Class_Close" class="close" data-dismiss="modal">&times;</button>
            </div>
            <%--界面--%>
            <div class="modal-body">
                <h6>是否删除在学课程</h6>
                <span style="font-size: 13px;color: gray;">你的笔记和提问将会保留</span>
                <button id="Delete_Class_sure_button" type="button" class="btn btn-primary" onclick=""
                        style="float: right">确定
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
