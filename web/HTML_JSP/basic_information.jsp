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
    .group{
        margin-top: 15px;
    }
    .group_with{
        width: 85%;
        margin-left: auto;
        margin-right: auto;
    }
    .help-block {
        margin-top: 5px;
        margin-bottom: 10px;
        color: red;
    }
    .basic_control{
        height: calc(2.25rem + 2px);
    }
</style>

<body onload="ifActive();addAction(1);addHref();getHTML()">
<jsp:include page="navigation.html"/>
<div style="width: 100%;margin-top:30px;height: 450px">
    <div style="background-size: cover;height: 148px;margin-top: -21px">
        <jsp:include page="ui_box.jsp"/>
        <div style="width: 80%;margin: auto">
            <jsp:include page="course_manag_nav.jsp"/>
            <div class="container_right">
                <h3 class="container_right_head">
                    基本信息
                </h3>
                <div>
                    <div class="group">
                        <div class="input-group group_with">
                            <div class="input-group-prepend">
                                <span class="input-group-text">标题</span>
                            </div>
                            <input type="text" class="basic_control form-control" id="title">
                        </div>
                        <div class="group_with">
                            <small id="title_waring" data-bv-validator="notEmpty" class="help-block" style="display: none">课程标题的长度为2-60个中文或字母、数字</small>
                        </div>
                    </div>
                    <div class="group">
                        <div class="input-group group_with">
                            <div class="input-group-prepend">
                                <span class="input-group-text">分类</span>
                            </div>
                            <select class="form-control found_class_text basic_control " id="sel1" >
                                <option>类型</option>
                                <option value=1>前端设计</option>
                                <option value=2>后台设计</option>
                                <option value=3>移动开发</option>
                                <option value=4>嵌入式</option>
                                <option value=5>基础理论</option>
                                <option value=6>项目发布</option>
                            </select>
                        </div>
                        <div class="group_with">
                            <small id="sel1_waring" data-bv-validator="notEmpty" class="help-block" style="display: none">请选择课程类型</small>
                        </div>
                    </div>
                    <div class="group">
                        <div class="input-group group_with">
                            <label for="outline" style="margin-right: 9px;margin-left: -16px;">课程概要</label>
                            <textarea type="text" class="form-control basic_control " id="outline" style="height: 90px;resize:none"></textarea>
                        </div>
                    </div>
                    <div class="group_with" style="height: 52px;margin-top: 10px;">
                        <button type="button" class="btn btn-light" style="float: right" onclick="save_information()">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    var flag1 = true;
    var flag2 = true;
    $(document).ready(function(){
        var title = $("#title");
        var title_waring = $('#title_waring');
        title.focus(function(){
            title_waring.css('display','none');
        });
        title.blur(function(){
            if (title.val().length<2 || title.val().indexOf(" ") !== -1){
                title_waring.css('display','block');
                flag1 = false;
            }else flag1 = true;
        });

        var sel1 = $("#sel1");
        var sel1_waring = $('#sel1_waring');
        sel1.focus(function(){
            sel1_waring.css('display','none');
        });
        sel1.blur(function(){
            if (sel1.val()==='类型'){
                sel1_waring.css('display','block');
                flag2 = false;
            }else flag2 = true;
        });

        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/SaveClassInfor",
            data: {
                No:No,
                Read_or_Save:'get_infor'
            },
            dataType: 'json',
            success: function (jsonObj) {
                $('#title').val(jsonObj.title);
                $('#sel1').val(jsonObj.type);
                $('#outline').val(jsonObj.outline);
            }
        });
    });


    function save_information() {
        if (flag1===true && flag2===true){
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/SaveClassInfor",
                data: {
                    No:No,
                    title:$('#title').val(),
                    sel1: $('#sel1').val(),
                    outline:$('#outline').val(),
                    Read_or_Save:'set_infor'
                },
                dataType: 'json',
                success: function (jsonObj) {
                    if (jsonObj.msg==='1') alert('修改成功');
                }
            });
        }
    }
</script>
</html>
