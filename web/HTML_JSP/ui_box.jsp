<%@ page import="Server.ConnectSQL" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div class="ui-box" >
    <div style="height: 30%;">
        <nav class="breadcrumb">
            <a class="breadcrumb-item" href="homepage.jsp">首页</a>
            <a class="breadcrumb-item" href="PersonalCenter.jsp">个人中心</a>
            <a class="breadcrumb-item" href="Teaching.jsp">在教课程</a>
            <span class="breadcrumb-item active">课程管理</span>
        </nav>
    </div>
    <div style="height: 70%;margin-top: -3px;" >
        <div style="width:18%;height: 100%;float: left;text-align: center">
            <img id="ui-box-cover" class="img-fluid" src="" style="height: 103%;width: 100%;border-bottom-left-radius: 12px;">
        </div>
        <div style="width: 82%;height: 100%;float: left">
            <div class="card" style="border: none;">
                <div class="card-body" style="padding-bottom: 0;">
                    <span class="badge badge-primary" style="float: left;margin-top: 5px;margin-right: 7px;font-size: 15px;">点播课程</span>
                    <h4   id="curriculum_Name" style="float: left"></h4>
                    <span style="float: right;">
                                <button id="curriculum_button" type="button" class="btn " data-toggle="modal" ></button>
                                <button type="button" class="btn btn-outline-primary">课程主页</button>
                                <a id="preview"  target="_blank"><button type="button" class="btn btn-outline-primary">预览</button></a>
                            </span>
                </div>
            </div>
            <span  style="padding-left: 20px;color: #999;">教师：<a id="teacher_Name" href=""></a></span>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var cover_address;
    $(document).ready(function(){
        $.ajax({
            url: "${pageContext.request.contextPath}/save_image",
            data: {
                action:'get_cover',
                No:No
            },
            type: "POST",
            dataType: "json",
            async: false,
            success: function (jsonObj) {
                cover_address = jsonObj.cover_address;
                $('#ui-box-cover').attr('src',"${pageContext.request.contextPath}"+cover_address);
                $('#radio_cover').attr('src',"${pageContext.request.contextPath}"+cover_address);
            }
        });
    })
</script>
</html>
