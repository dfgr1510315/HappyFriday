<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.InforBean" %><%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/8
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ page import="Server.DBPoolConnection" %>
<%@ page import="com.alibaba.druid.pool.DruidPooledConnection" %>
<%@ page import="java.sql.*" %>
<%
    //String path = request.getContextPath();
    //String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String username=(String) session.getAttribute("user_id");
%>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <script src="${pageContext.request.contextPath}/JS/LoginPC.js"></script>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>

    <%--<base href="<%=basePath%>">--%>
    <title>个人中心</title>
    <style type="text/css">

        body {
            background-color: #f8fafc;
            overflow: auto;
        }

        a:hover{
            text-decoration:none
        }

        .user_id_span {
            float: left;
            font-size: 14px;
            color: #4d4d4d;
            height: 32px;
        }

        #localUl li {
            height: 32px;
        }

        #upload label {
            float: left;
            margin-right: 15px;
        }

        #upload input {
            height: 28px;
        }

        .input1 {
            width: 50%;
        }

        input[type=date]::-webkit-inner-spin-button {
            visibility: hidden;
        }

        .form-control{
            height: calc(2.25rem + 2px);
        }

    </style>
</head>
<body onload="addClass(0)">

<jsp:include page="navigation.html"/>


<div style="width: 100%;height: 450px">

    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <div >
                <h3 class="container_right_head">
                    个人资料
                </h3>

                <div style="width: 100%; ">
                    <div style="width: 10%;float: left;height: 100%;">
                        <%--头像区--%>
                        <%--<img src="http://static.runoob.com/images/mix/img_nature_wide.jpg"
                             style="width: 100px;height: 100px;border-radius: 50%;margin: 0 auto;margin-top: 16px;">--%>
                        <img id="pc_head_image" src=""
                             style="width: 100px;height: 100px;border-radius: 50%;margin: 16px auto 0;">
                        <div style="text-align: center;width: 100px; margin-top: 7px">
                            <label for="head_iamge" class=" btn btn-primary" style="font-size: 12px;">更换头像</label>
                            <input id="head_iamge" type="file" style="display:none" onchange="post_head_image(this)">
                        </div>
                    </div>
                    <div style="width: 85%;height: 100%;float: right;">
                        <%--详细资料区--%>
                        <div style="margin-top: 16px;height: 32px">
                            <%--用户id--%>
                            <span class="user_id_span">ID:<%=username%></span>
                            <a data-toggle="modal" data-target="#information" href="#" style="float: right;">修改资料</a>
                        </div>
                        <%
                            DBPoolConnection dbp = DBPoolConnection.getInstance();
                            DruidPooledConnection con =null;
                            try {
                                con = dbp.getConnection();
                                String sql = "select * from personal_table where username='" + username + "'";
                                Statement statement = con.createStatement();
                                ResultSet rs = statement.executeQuery(sql);
                                ArrayList<InforBean> list = new ArrayList<InforBean>();
                                while (rs.next()) {
                                    InforBean inforBean = new InforBean();
                                    inforBean.setNike(rs.getString("nike"));
                                    inforBean.setSex(rs.getString("sex"));
                                    inforBean.setBirth(rs.getString("birth"));
                                    inforBean.setInformation(rs.getString("information"));
                                    inforBean.setTeacher(rs.getString("teacher"));
                                    list.add(inforBean);
                                }
                                request.setAttribute("list", list);
                                rs.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                if (con!=null)
                                    try{
                                        con.close();
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                            }

                            ArrayList list = (ArrayList) request.getAttribute("list");
                            //System.out.println(list.size());
                            for (int i = 0; i < list.size(); i++) {
                                InforBean inforBean = (InforBean) list.get(i);
                        %>
                        <ul id="localUl" style="list-style: none;padding-left: 0">
                            <li id="liNike">
                                昵称：&nbsp<%=inforBean.getNike() %>
                            </li>
                            <li id="liSex">
                                性别：&nbsp<%=inforBean.getSex() %>
                            </li>
                            <li id="liBirth">
                                生日：&nbsp<%=inforBean.getBirth() %>
                            </li>
                            <li id="liTeacher">
                                指导老师：<%=inforBean.getTeacher() %>
                            </li>
                            <li id="liIntro">
                                简介：&nbsp<%=inforBean.getInformation() %>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <div class="modal fade" id="information" style="background-color: transparent; width: 100%; top: 80px;">
        <div class="modal-dialog">
            <div class="modal-content">

                <%-- 修改头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">修改资料</h5>
                    <a id="ChangeClose" class="close" data-dismiss="modal">&times;</a>
                </div>

                <%--修改界面--%>
                <div class="modal-body">
                    <form id="upload" action="http://localhost:8080/ChangeInfor" method="POST">
                        <div class="form-group">
                            <label for="nike">昵称:</label>
                            <input type="text" class="form-control input1" id="nike">
                        </div>
                        <%-- <div class="form-group">
                             <label for="name">实名:</label>
                             <input type="text" class="form-control input1" id="name">
                         </div>--%>
                        <div class="form-group">
                            <label for="sex">性别:</label>
                            <div class="dropdown">
                                <button id="sex" type="button" class="btn btn-light dropdown-toggle"
                                        data-toggle="dropdown" style="font-size: 15px">
                                    请选择
                                </button>
                                <div class="dropdown-menu" style="min-width: 88px;">
                                    <a class="dropdown-item" style="font-size: 13px"
                                       onclick="sexChoose('男')">男</a>
                                    <a class="dropdown-item" style="font-size: 13px"
                                       onclick="sexChoose('女')">女</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="birth">生日:</label>
                            <input type="date" id="birth" class="form-control input1">
                        </div>
                        <div class="form-group">
                            <label for="teacher">指导老师:</label>
                            <input type="text" class="form-control" id="teacher" style="width: 43%">
                        </div>
                        <div class="form-group">
                            <label for="introduction">简介:</label>
                            <textarea id="introduction" style="width: 80%;height: 60px; resize:none;font-size: 14px;"
                                      class="form-control"></textarea>
                        </div>

                        <button id="sure" type="button" class="btn btn-primary" style="margin-top: 15px"
                                onclick="changeInfor() ">确定
                        </button>
                    </form>
                </div>

                <%--登录底部--%>
                <%--<div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">。。。</button>
                </div>--%>
            </div>
        </div>
    </div>
</div>

<script>
    function sexChoose(sex) {
        $("#sex").text(sex);
    }

    $('#pc_head_image').attr('src',"${pageContext.request.contextPath}"+head_image);
    $("#nike").val('<%=inforBean.getNike() %>');
    $("#sex").text('<%=inforBean.getSex() %>');
    $("#birth").val('<%=inforBean.getBirth() %>');
    $("#teacher").val('<%=inforBean.getTeacher() %>');
    $("#introduction").val('<%=inforBean.getInformation() %>');
    <%  }%>

    function changeInfor() {
        var nike = $.trim($("#nike").val());
        var sex = $.trim($("#sex").text());
        if (sex === '请选择') sex = '';
        var birth = $.trim($("#birth").val());
        var teacher = $.trim($("#teacher").val());
        var introduction = $.trim($("#introduction").val());
        var user = '<%=username%>';
        var data = {
            action:'1', //更改用户信息
            ID: user,
            nike: nike,
            sex: sex,
            birth: birth,
            teacher: teacher,
            introduction: introduction
        };
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/changeInfor",
            data: data,
            dataType: 'json',
            success: function (msg) {
                if (2 === msg) {
                    $("#ChangeClose").click();
                    $("#liNike").text("昵称：       " + nike);
                    $("#liSex").text("性别：        " + sex);
                    $("#liBirth").text("生日：        " + birth);
                    $("#liIntro").text("简介：        " + introduction);
                    $("#liTeacher").text("指导老师：" + teacher);
                }
                else {
                    alert("数据更新失败")
                }
            }
        });
    }

    function post_head_image(event) {
        var animateimg = $(event).val(); //获取上传的图片名 带//
        var imgarr=animateimg.split('\\'); //分割
        var myimg=imgarr[imgarr.length-1]; //去掉 // 获取图片名
        var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
        var ext = myimg.substring(houzui, myimg.length).toUpperCase();  //切割 . 获取文件后缀
        var file = $(event).get(0).files[0]; //获取上传的文件
        var fileSize = file.size;           //获取上传的文件大小
        var maxSize = 1048576;              //最大1MB
        if(ext !=='.PNG' && ext !=='.GIF' && ext !=='.JPG' && ext !=='.JPEG' && ext !=='.BMP'){
            alert('文件类型错误,请上传图片类型');
            return false;
        }else if(parseInt(fileSize) >= parseInt(maxSize)){
            alert('上传的文件不能超过1MB');
            return false;
        }else {
            var fileObj = event.files[0]; // js 获取文件对象
            var formFile = new FormData();
            formFile.append("file", fileObj); //加入文件对象
            $.ajax({
                url: "${pageContext.request.contextPath}/uploadimage",
                data: formFile,
                type: "POST",
                dataType: "json",
                async: true,
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                success: function (jsonObj) {
                    $('#pc_head_image').attr('src',"${pageContext.request.contextPath}"+jsonObj.head_address);
                    $('#head_image').attr('src',"${pageContext.request.contextPath}"+jsonObj.head_address);
                    $('#radio_cover').attr('src',"${pageContext.request.contextPath}"+head_image);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/save_image",
                        data: {
                            action:'set_head',
                            image: jsonObj.head_address
                        },
                        type: "POST",
                        dataType: "json",
                        async: true
                    });
                }
            });

        }
    }

</script>

</body>
</html>
