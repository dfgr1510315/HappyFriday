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
        .ui-box {
            border-radius: 5px;
            box-shadow: 0 6px 16px 0 rgba(7,17,27,.1);
            /* border: 1px solid #ddddcf;*/
            background-color: white;
            padding: 20px;
            margin-bottom: 15px;
            width: 92%;
            margin-left: auto;
            margin-right: auto;
            max-height: 425px;
        }
        #note_box .post_action{
            float: left;
            margin-left: 20px;
            color: #9199a1;
            font-size: 12px;
            overflow: hidden;
            cursor: pointer;
            line-height: 34px;
        }
        #note_box .post_action:hover{
            text-decoration: none;
            color: #4d555d;
        }
        #note_box .post_action i{
            margin-right: 5px;
        }

        .post span{
            margin-left: 20px;
            color: #9199a1;
            font-size: 12px;
            line-height: 34px;
        }
        a:hover{
            text-decoration: none;
        }
        .title a {
            font-size: 14px;
            color: #1c1f21;
            font-weight: 700;
        }
        .title a:hover{
            color: #007bff
        }
        .unit a{
            font-size: 12px;
            color: #93999f;
            line-height: 24px;
        }
        .Unit a:hover{
            color: #1c1f21;
        }


    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/postnote",
                data: {
                    action:'get_all_note',
                    author:user
                },
                dataType: 'json',
                success: function (jsonObject){
                    for (var i=0;i< jsonObject.text.length;i++){
                        $('#note_box').prepend(' <div class="ui-box">\n' +
                            '                            <div style="float:left;">\n' +
                            '                                <a target="_blank" href="Learn_list.jsp?'+jsonObject.title_no[i]+'">\n' +
                            '                                    <image src="'+jsonObject.image_address[i]+'" style="width: 40px;height: 40px;border-radius: 20px;"></image>\n' +
                            '                                </a>\n' +
                            '                            </div>\n' +
                            '                            <div style="margin-left: 60px">\n' +
                            '                                <div class="title">\n' +
                            '                                    <a target="_blank" href="Learn_list.jsp?'+jsonObject.title_no[i]+'" >'+jsonObject.title[i]+'</a>\n' +
                            '                                </div>\n' +
                            '                                <div class="unit">\n' +
                            '                                    <a target="_blank" href="Play.jsp?'+jsonObject.title_no[i]+'/'+jsonObject.class_no[i]+'" >'+jsonObject.class_no[i]+'&nbsp&nbsp&nbsp'+jsonObject.class_title[i]+'</a>\n' +
                            '                                </div>\n' +
                            '                                <div style="display: block;">\n' +
                            '                                    <div style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;max-height: 63%;min-height=20%">\n' +
                            '                                        '+jsonObject.text[i]+'\n' +
                            '                                    </div>\n' +
                            '                                    <div class="post" style=" margin-left: 44px;">\n' +
                            '                                        <a class="post_action" href="javascript:void(0);" onclick="edit_note(this)"><i class="fa fa-eyedropper"></i>编辑</a>\n' +
                            '                                        <a data-id="delete_note'+i+'" id="delete_note'+i+'" class="post_action" data-toggle="modal"  data-target="#Delete_Note"><i class="fa fa-window-close-o"></i>删除</a>\n' +
                            '                                        <a class="post_action">查看全文</a>\n' +
                            '                                        <span >'+jsonObject.time[i]+'</span>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                                <div style="display: none;">  <%--修改笔记区--%>\n' +
                            '                                    <div id="edit_editor'+i+'" style="height: 60%;margin-bottom: 14px"></div>\n' +
                            '                                    <div style="height: 30px;">\n' +
                            '                                        <button type="button" class="btn btn-primary" style="float:right;" onclick="change_note(this)">提交</button>\n' +
                            '                                        <button type="button" class="btn btn-outline-secondary" style="float:right;margin-right: 10px;" onclick="out_edit_note(this)">取消</button>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </div>');
                    }
                }
            });
        });
    </script>

</head>
<body onload="checkCookie();ifActive();addClass(8)">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <h3 class="container_right_head">
                我的笔记
            </h3>
            <div id="note_box">

            </div>
        </div>
    </div>
</div>

</body>
</html>
