<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程管理</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>


    <style>
        .liMyclass {
            background-color: #007bff;
        }

        .aMyclass {
            color: white;
        }

        #verticalNav {
            margin-top: 32px;
        }

        .container_right {
            width: 80%;
            float: right;
            /* background-color: pink;*/
            min-height: 240px;
            margin-top: 32px;
        }

        .container_right_head {
            font-size: 20px;
            color: #3d3d3d;
            height: 90px;
            line-height: 90px;
            border-bottom: 1px solid #e0e0e0;
        }

        .section {
            width: 100%;
            background-color: #8080800d;
            float: left;
            margin-bottom: 10px;
        }

        .Unit {
            border-bottom: none;
            border-left: none;
            background-color: white;
        }

        .Unit_class {
            width: 90%;
            float: right;
        }
    </style>
    <script>
        function ifActive() {
            document.getElementById("idMyclass").className -= ' nav-link';
        }

        var Unitcount = 1;
        var Classcount = 1;


        function addSection() {
            $(".sections").append('<div class="card section Unit">\n' +
                '      <div class="card-header"><span class="badge badge-pill badge-primary">章节</span>\n' +
                '\t\t  <h8>\n' +
                '\t\t  \t\n' +
                '\t\t  </h8>\n' +
                '\t\t  <div class="btn-group" style="float:right">\n' +
                '    \t\t<button id="button_add_class" data-id="button_add_class" type="button" class="btn btn-primary" data-toggle="modal" data-target="#add_class_hour" >增加课时</button>\n' +
                '   \t\t\t<button type="button" class="btn btn-primary">更改名称</button>\n' +
                '\t\t\t<button type="button" class="btn btn-primary">删除章节</button>\n' +
                '\t\t\t<button type="button" class="btn btn-primary">批量上传</button>\n' +
                '    \t\t<button id="Button_collapse" type="button" class="btn btn-prima  ry card-link" data-toggle="collapse" href="">折叠</button>\n' +
                '  \t\t  </div>\n' +
                '      </div>\n' +
                '      <div id="collapse" class="collapse show" data-parent="#accordion">\n' +
                '      \n' +
                '      </div>\n' +
                '    </div>\n' +
                '\t  ');
            document.getElementsByTagName("h8")[Unitcount - 1].className += 'Unit_name' + Unitcount;
            var obj = document.getElementById("collapse");
            obj.setAttribute("id", "collapse" + Unitcount);
            obj = document.getElementById("Button_collapse");
            obj.setAttribute("id", "Button_collapse" + Unitcount);
            obj.setAttribute("href", "#collapse" + Unitcount);
            obj = document.getElementById("button_add_class");
            obj.setAttribute("data-id", "button_add_class" + Unitcount);
            obj.setAttribute("id", "button_add_class" + Unitcount);
            $(".Unit_name" + Unitcount).text($("#unit_name").val());
            Unitcount = Unitcount + 1;
            $("#unit_name").val('');
            document.getElementById("unit_name").autofocus;
            $("#unitClose").click();
        }

        function add_class_hour(add_id) {
            $(add_id).parent().parent().next().append(' <div class="card Unit_class">\n' +
                '      <div class="card-header"><span class="badge badge-secondary badge-pill">课时</span>\n' +
                '\t\t  <h10>\n' +
                '\t\t  \t\n' +
                '\t\t  </h10>\n' +
                '\t\t  <div class="btn-group" style="float:right">\n' +
                '   \t\t\t<button type="button" class="btn btn-secondary btn-sm">更改名称</button>\n' +
                '\t\t\t<button type="button" class="btn btn-secondary btn-sm">删除课时</button>\n' +
                '    \t\t<button id="hour_button_collapse" type="button" class="btn btn-secondary card-link btn-sm" data-toggle="collapse" href="">折叠</button>\n' +
                '  \t\t  </div>\n' +
                '      </div>\n' +
                '      <div id="collapseTwo" class="collapse show" data-parent="#accordion">\n' +
                '      <div class="card-body">\n' +
                '          #2 内容：\n' +
                '        </div>\n' +
                '      </div>\n' +
                '    </div>');
            document.getElementsByTagName("h10")[Classcount - 1].className += 'Class_name' + Classcount;
            $(".Class_name" + Classcount).text($("#class_name").val());
            var obj = document.getElementById("hour_button_collapse");
            obj.setAttribute("id","hour_button_collapse"+Classcount);
            obj.setAttribute("href","#hour_collapse"+Classcount);
            obj =  document.getElementById("collapseTwo");
            obj.setAttribute("id","hour_collapse"+Classcount);
            Classcount = Classcount + 1;
            $("#class_name").val('');
            $("#class_Close").click();
        }


        $(function () {
            $('#add_class_hour').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var button_id = button.data('id');
                var obj = document.getElementById("sure_button");
                obj.setAttribute("onclick","add_class_hour("+button_id+")");
            })
        });

    </script>
</head>
<body onload="checkCookie();ifActive()">
<jsp:include page="navigation.jsp"/>
<jsp:include page="LoginPC.jsp"/>
<div style="width: 100%;margin: auto;margin-top: 80px;height: 450px">
    <div style="background: url(image/bacg2.jpg) center top no-repeat #000;background-size: cover;height: 148px;margin-top: -21px">
        <div style="width: 80%;height: 100%;margin: auto">
            <div style="margin-left: 0;height: 148px;width: 148px;float: left">
                <div style="    border: 4px solid #FFF;box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);width: 148px;height: 148px;position: relative;border-radius: 50%;background: #fff;top: 24px;">
                    <img src="image/68296699_p0.png"
                         style="text-align: center;width: 140px;height: 140px;border-radius: 50%;">
                </div>
            </div>
        </div>
        <div style="width: 80%;margin: auto">
            <jsp:include page="VerticalNav.jsp"/>
            <div class="container_right">
                <h3 class="container_right_head">
                    课程管理
                </h3>
                <form>
                    <div class="sections">

                    </div>
                    <div class="btn-group">
                        <button data-toggle="modal" data-target="#add_section" type="button" class="btn btn-primary">
                            添加章节
                        </button>
                        <button type="submit" class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="add_section"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--添加章节--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">请输入章节名称</h5>
                    <button id="unitClose" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="unit_name" style="height: 40px ;resize:none;" class="form-control"></textarea>
                    <button type="button" class="btn btn-primary" style="float: right;margin-top: 10px"
                            onclick="addSection()">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="add_class_hour"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--添加课时--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">请输入课时名称</h5>
                    <button id="class_Close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="class_name" style="height: 40px ;resize:none;" class="form-control"></textarea>
                    <button id="sure_button" type="button" class="btn btn-primary" style="float: right;margin-top: 10px"
                            onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
