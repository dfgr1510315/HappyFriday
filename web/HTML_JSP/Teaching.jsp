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
        function get_Class() {
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "${pageContext.request.contextPath}/get_teaching",
                data:{
                    type:'get_Class'
                },
                dataType: 'json',
                success: function (json) {
                    var clas_ul = $("tbody");
                    $(json).each(function(){
                        clas_ul.append(
                            '<tr>\n' +
                            '<td><img src="${pageContext.request.contextPath}'+this.封面地址 +'" class="cover">'+this.Title+'</td> \n'+
                            '<td>'+this.学员数+'</td> \n'+
                            '<td>'+this.状态+'</td> \n'+
                            '<td><a target="_blank" href="Myclass.jsp?class_id='+ this.课程编号+'"><button class="btn btn-outline-primary btn-sm" >管理</button></a></td>\n'+
                            '<tr>\n '
                        )
                    });
                    if (clas_ul.html().length === 0) {
                        $('#class_table').append(
                            ' <div class="no_find_class">暂无课程</div> '
                        )
                    }
                }
            });
        }

        function found_class() {
            //alert($('#found_class_name').val());
            var Class_Name = $('#found_class_name').val();
            var Class_Type = $('#sel1').val();
            if ('' === Class_Name||'类型' === Class_Type){
                alert('课程标题或类型不能为空');
            } else {
                $.ajax({
                    type: "POST",
                    asynch: "false",
                    url: "${pageContext.request.contextPath}/get_teaching",
                    data: {
                        type:'found_class',
                        Class_Name:Class_Name,
                        Class_Type:Class_Type
                    },
                    dataType: 'json',
                    success: function (jsonObj) {
                        if ("1"===jsonObj.state) {
                            $('#found_class_Close').click();
                            window.location.href = "Teaching.jsp";
                        }else alert("创建失败")
                    }
                });

            }
        }
    </script>

</head>
<body onload="ifActive();addClass(3);get_Class()">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;min-height: 450px">
    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <div id="class_table">
                <h3 class="container_right_head">
                    在线课程
                    <button class="btn btn-outline-primary" style="float: right;margin-top: 28px" data-id="button_Change_Class"    data-toggle="modal"  data-target="#found_class">
                        创建课程
                    </button>
                </h3>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th style="width:60%">课程名称</th>
                        <th>学员数</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="found_class">
    <div class="modal-dialog">
        <div class="modal-content">
            <%-- 头部--%>
            <div class="modal-header">
                <h5 class="modal-title">创建课程</h5>
                <button id="found_class_Close" class="close" data-dismiss="modal">&times;</button>
            </div>
            <%--界面--%>
            <div class="modal-body ">
                <div style="height: 50px;" >
                    <span class="found_class">课程名称</span>
                    <textarea id="found_class_name"  class=" form-control found_class_text" title="title" ></textarea>
                </div>
                <div style="height: 50px;" >
                    <label for="sel1" class="found_class">课程分类</label>
                    <select class="form-control found_class_text" id="sel1" >
                        <option>类型</option>
                        <option value="1">前端设计</option>
                        <option value="2">后台设计</option>
                        <option value="3">移动开发</option>
                        <option value="4">嵌入式</option>
                        <option value="5">基础理论</option>
                        <option value="6">项目发布</option>
                    </select>
                </div>
                <button id="found_class_sure_button" type="button" class="btn btn-primary" onclick="found_class()" style="float: right;">创建
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
