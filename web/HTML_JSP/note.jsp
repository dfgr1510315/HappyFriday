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
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
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
        .unit a:hover{
            color: #1c1f21;
        }

        #note_box .post-row {
            position: relative;
            margin-bottom: 8px;
            padding: 32px;
            background: #fff;
            box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);
            border-radius: 12px;
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
        button.btn-mini {
            height: 29px;
            padding: 0 15px;
            font-size: 12px;
            width: auto;
        }
    </style>
    <script type="text/javascript">
        var E = window.wangEditor;
        var edit_configure = [
            'head',
            'bold',
            'italic',
            'underline',
            'justify',  // 对齐方式
            'foreColor',  // 文字颜色
            'fontSize',  // 字号
            'quote',
            'code'];
        $(document).ready(function(){
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/postnote",
                data: {
                    action:'get_my_all_note',
                    author:user
                },
                dataType: 'json',
                success: function (jsonObject){
                    var note_box = $('#note_box');
                    for (var i=0;i< jsonObject.text.length;i++){
                        note_box.prepend(' <div class="post-row">\n' +
                            '                            <div style="float:left;">\n' +
                            '                                <a target="_blank" href="Learn_list.jsp?class_id='+jsonObject.title_no[i]+'">\n' +
                            '                                    <image src="'+"${pageContext.request.contextPath}"+jsonObject.image_address[i]+'" style="width: 40px;height: 40px;border-radius: 20px;"></image>\n' +
                            '                                </a>\n' +
                            '                            </div>\n' +
                            '                            <div style="margin-left: 60px">\n' +
                            '                                <div class="title">\n' +
                            '                                    <a target="_blank" href="Learn_list.jsp?class_id='+jsonObject.title_no[i]+'" >'+jsonObject.title[i]+'</a>\n' +
                            '                                </div>\n' +
                            '                                <div class="unit">\n' +
                            '                                    <a target="_blank" href="Play.jsp?'+jsonObject.title_no[i]+'/'+jsonObject.class_no[i]+'" >'+jsonObject.class_no[i]+'&nbsp&nbsp&nbsp'+jsonObject.class_title[i]+'</a>\n' +
                            '                                </div>\n' +
                            '                                <div style="display: block;">\n' +
                            '                                    <div style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;max-height: 63%;min-height=20%">\n' +
                            '                                        '+jsonObject.text[i]+'\n' +
                            '                                    </div>\n' +
                            '                                    <div class="post" style=" margin-left: 445px;">\n' +
                            '                                        <a class="post_action" href="javascript:void(0);" onclick="edit_note(this)"><i class="fa fa-eyedropper"></i>编辑</a>\n' +
                            '                                        <a id="delete_note'+jsonObject.note_no[i]+'" class="post_action" data-toggle="modal"  data-target="#Delete_Note" data-noteno="'+jsonObject.note_no[i]+'"><i class="fa fa-window-close-o"></i>删除</a>\n' +
                            '                                        <span >'+jsonObject.time[i]+'</span>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                                <div style="display: none;">  <%--修改笔记区--%>\n' +
                            '                                    <div id="edit_editor'+i+'" style="margin-bottom: 14px"></div>\n' +
                            '                                    <div style="height: 30px;">\n' +
                            '                                        <button type="button" class="btn btn-outline-success btn-mini" style="float:right;" onclick="change_note(this)" data-noteno="'+jsonObject.note_no[i]+'">提交</button>\n' +
                            '                                        <button type="button" class="btn btn-outline-secondary btn-mini" style="float:right;margin-right: 10px;" onclick="out_edit_note(this)">取消</button>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </div>');
                        var edit_editor = new E('#edit_editor'+i);
                        edit_editor.customConfig.menus = edit_configure;
                        edit_editor.create();
                        edit_editor.txt.html(jsonObject.text[i]);
                    }
                    if (note_box.html().length === 0) {
                        note_box.append(
                            ' <div class="no_find_class">暂无笔记</div> '
                        )
                    }
                }
            });
        });

        function change_note(event) {
            var new_note = $(event).parent().prev().children().eq(1).children().html().replace(/(^\s*)|(\s*$)/g, "").replace(/\u200B/g,'');
            var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/postnote",
                data: {
                    action:'change',
                    note_no:$(event).data('noteno'),
                    note_editor:new_note,
                    time:time
                },
                dataType: 'json',
                success: function (jsonObj) {
                    if ('1'===jsonObj.msg){
                        $(event).parent().parent().prev().children().eq(0).html(new_note);
                        $(event).next().click();
                    }
                }
            });
        }

        $(function () {
            $('#Delete_Note').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var button_id = button.data('noteno'); //获取呼出模态框的按钮ID
                var obj = document.getElementById("Delete_Note_sure_button");
                obj.setAttribute("onclick", "delete_note(" + button_id + ")");
            })
        });

        function delete_note(noteno) {
            //alert($(event).parent().prev().html().replace(/(^\s*)|(\s*$)/g, ""));
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/postnote",
                data: {
                    action:'delete',
                    note_no:noteno
                },
                dataType: 'json',
                success: function (jsonObj) {
                    if ('1'===jsonObj.msg){
                        $('#delete_note'+noteno).parent().parent().parent().parent().remove();
                        $("#Delete_Note_Close").click();
                    }
                }
            });
        }

        function edit_note(event) {
            $(event).parent().parent().css('display','none');
            $(event).parent().parent().next().css('display','block');
        }
        function out_edit_note(event) {
            $(event).parent().parent().css('display','none');
            $(event).parent().parent().prev().css('display','block');
        }
        Date.prototype.Format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "H+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        };
    </script>

</head>
<body onload="ifActive();addClass(8)">
<jsp:include page="navigation.jsp"/>
<div>
    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <h3 class="container_right_head">
                我的笔记
            </h3>
            <div id="note_box"></div>
        </div>
    </div>
</div>
<div class="modal fade" id="Delete_Note">
    <div class="modal-dialog">
        <div class="modal-content">
            <%-- 头部--%>
            <div class="modal-header">
                <h5 class="modal-title">确定删除这条笔记吗？</h5>
                <a id="Delete_Note_Close" class="close" data-dismiss="modal">&times;</a>
            </div>
            <%--界面--%>
            <div class="modal-body">
                <div>笔记删除将无法恢复</div>
                <button id="Delete_Note_sure_button" type="button" class="btn btn-primary" onclick="" style="float: right; margin-top: 10px;">确定
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
