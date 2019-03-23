<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% String username=(String) session.getAttribute("user_id");
%>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>

    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <input id="user" type="hidden" value="<%=username %>" />
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
</style>

<body onload="ifActive();getHTML();addHref();addAction(0)" onbeforeunload="live();">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;margin-top:30px;height: 450px">
    <div style="background-size: cover;height: 148px;margin-top: -21px">
        <jsp:include page="ui_box.jsp"/>
        <div style="width: 80%;margin: auto">
            <jsp:include page="course_manag_nav.jsp"/>
            <div class="container_right">
                <h3 class="container_right_head">
                    课程管理
                </h3>
                <form>
                    <div class="sections">

                    </div>
                    <div class="btn-group">
                        <button data-toggle="modal" data-target="#add_section" type="button" class="btn btn-primary btn-sm" onclick="cancel_drag()">
                            添加章节
                        </button>
                        <button id="use_drag" type="button" class="btn btn-primary btn-sm" onclick="drag(this)">拖动排序</button>
                        <button type="button" class="btn btn-sm btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
                            <span class="caret"></span>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" onclick="set_Unit_Listener(this)" href="javascript:void(0)">章节排序</a>
                            <a class="dropdown-item" onclick="set_Class_Listener(this)" href="javascript:void(0)">课时排序</a>
                        </div>
                    </div>
                    <button type="button" class="btn btn-outline-success btn-sm" onclick="saveClass()">保存</button>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="add_section"> <%--添加章节--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">请输入章节名称</div>
                    <a id="unitClose" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="unit_name"  class="form-control"
                              title="unit"></textarea>
                    <button type="button" class="btn btn-outline-success btn-sm"  onclick="addSection()">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Change_section"> <%--更改章节名称--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">请重新输入章节名称</div>
                    <a id="Change_section_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="Change_unit_name" class="form-control"
                              title="unit"></textarea>
                    <button id="Change_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="add_class_hour">
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">请输入课时名称</div>
                    <a id="class_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="class_name"  class="form-control"
                              title="class"></textarea>
                    <button id="Add_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Change_class"> <%--更改课时名称--%>
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">请重新输入课时名称</div>
                    <a id="Change_Class_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <textarea id="Change_class_name"  class="form-control"
                              title="unit"></textarea>
                    <button id="Change_class_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Delete_Unit">
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">提示</div>
                    <a id="Delete_Unit_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否删除此章节</h6>
                    <button id="Delete_Unit_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="" >确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Delete_Class">
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">提示</div>
                    <a id="Delete_Class_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否删除此课时</h6>
                    <button id="Delete_Class_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Open_curriculum">
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">提示</div>
                    <a id="Open_curriculum_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否发布此课程</h6>
                    <button id="Open_curriculum_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="Release(1)">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="Close_curriculum">
        <div class="modal-dialog">
            <div class="modal-content">
                <%-- 头部--%>
                <div class="modal-header">
                    <div class="modal-title">提示</div>
                    <a id="Close_curriculum_Close" class="close" data-dismiss="modal">&times;</a>
                </div>
                <%--界面--%>
                <div class="modal-body">
                    <h6>是否关闭此课程</h6>
                    <button id="Close_curriculum_sure_button" type="button" class="btn btn-outline-success btn-sm" onclick="Release(0)">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
