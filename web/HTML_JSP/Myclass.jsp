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
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>课程管理</title>
   <%-- <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>--%>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="../webuploader-0.1.5/css/webuploader.css">
    <script type="text/javascript" src="../webuploader-0.1.5/jekyll/js/webuploader.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
    <script type="text/javascript" src="../JS/Myclass.js"></script>
    <script type="text/javascript" src="../JS/drag.js"></script>

    <style type="text/css">
        .dndArea {
            width: 200px;
            height: 100px;
            border: dashed red;
        }

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

        .over > *{
            display: none;
        }

        .card-header{
            height: 57.8px;
        }
    </style>

</head>
<body onload="checkCookie();ifActive();getHTML()">

<jsp:include page="navigation.jsp"/>
<jsp:include page="LoginPC.jsp"/>

<div style="width: 100%;margin: 80px auto auto;height: 450px">
    <div style="background: url(../image/bacg2.jpg) center top no-repeat #000;background-size: cover;height: 148px;margin-top: -21px">
        <div style="width: 80%;height: 100%;margin: auto">
            <div style="margin-left: 0;height: 148px;width: 148px;float: left">
                <div style="    border: 4px solid #FFF;box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);width: 148px;height: 148px;position: relative;border-radius: 50%;background: #fff;top: 24px;">
                    <img src="../image/68296699_p0.png"
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
                        <button id="use_drag" type="button" class="btn btn-primary" onclick="drag(this)">拖动排序</button>
                        <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
                            <span class="caret"></span>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" onclick="set_Unit_Listener(this)" href="#">章节排序</a>
                            <a class="dropdown-item" onclick="set_Class_Listener(this)" href="#">课时排序</a>
                        </div>
                        <button type="button" class="btn btn-success" onclick="saveClass()">保存</button>
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
                    <textarea id="unit_name" style="height: 40px ;resize:none;" class="form-control"
                              title="unit"></textarea>
                    <button type="button" class="btn btn-primary" style="float: right;margin-top: 10px"
                            onclick="addSection()">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="Change_section"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--更改章节名称--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">请重新输入章节名称</h5>
                    <button id="Change_section_Close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="Change_unit_name" style="height: 40px ;resize:none;" class="form-control"
                              title="unit"></textarea>
                    <button id="Change_sure_button" type="button" class="btn btn-primary"
                            style="float: right;margin-top: 10px"
                            onclick="">确定
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
                    <textarea id="class_name" style="height: 40px ;resize:none;" class="form-control"
                              title="class"></textarea>
                    <button id="Add_sure_button" type="button" class="btn btn-primary"
                            style="float: right;margin-top: 10px"
                            onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="Change_class"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--更改课时名称--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">请重新输入课时名称</h5>
                    <button id="Change_Class_Close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="Change_class_name" style="height: 40px ;resize:none;" class="form-control"
                              title="unit"></textarea>
                    <button id="Change_class_sure_button" type="button" class="btn btn-primary"
                            style="float: right;margin-top: 10px"
                            onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="Delete_Unit"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--删除章节--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">提示</h5>
                    <button id="Delete_Unit_Close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否删除此章节</h6>
                    <button id="Delete_Unit_sure_button" type="button" class="btn btn-primary"
                            style="float: right;margin-top: 10px"
                            onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="Delete_Class"
         style="background-color: transparent; width: 100%; top: 80px;"> <%--删除章节--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">提示</h5>
                    <button id="Delete_Class_Close" class="close" data-dismiss="modal">&times;</button>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否删除此课时</h6>
                    <button id="Delete_Class_sure_button" type="button" class="btn btn-primary"
                            style="float: right;margin-top: 10px"
                            onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>

</body>

</html>
