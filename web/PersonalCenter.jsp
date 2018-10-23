<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.InforBean" %><%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/8
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">--%>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <%--<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>--%>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <%--<base href="<%=basePath%>">--%>
    <title>个人中心</title>
    <style type="text/css">
        body {
            background: #fffef5;
            overflow: auto;
        }

        span {
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

    </style>
</head>
<body onload="checkCookie();">

<jsp:include page="navigation.jsp"/>

<jsp:include page="LoginPC.jsp"/>

<div style="width: 100%;margin: auto;margin-top: 80px;height: 450px">
    <jsp:include page="VerticalNav.jsp"/>
    <%
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usermanager";
        String user = "root";
        String Mysqlpassword = "138859";
    %>
    <div style="width: 80%;
    background-color: white;
    min-height: 95%;
    padding: 0 32px 32px;
    float: left;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.05);
    margin-bottom: 10px;
    height: 100%;">

        <div style="height: 100%;width: 100%;">
            <h3 style=" font-size: 20px;color: #3d3d3d;height: 90px;line-height: 90px;border-bottom: 1px solid #e0e0e0;">
                个人资料
            </h3>

            <div style="width: 100%; ">
                <div style="width: 10%;float: left;height: 100%;">
                    <%--头像区--%>
                    <%--<img src="http://static.runoob.com/images/mix/img_nature_wide.jpg"
                         style="width: 100px;height: 100px;border-radius: 50%;margin: 0 auto;margin-top: 16px;">--%>
                        <img src="image/68296699_p0.png"
                             style="width: 100px;height: 100px;border-radius: 50%;margin: 0 auto;margin-top: 16px;">
                    <div style="text-align: center;width: 100px; margin-top: 7px">
                        <label for="file" class=" btn btn-primary" style="font-size: 12px;">更换头像</label>
                        <input id="file" type="file" style="display:none">
                    </div>
                </div>
                <div style="width: 90%;height: 100%;float: left;">
                    <%--详细资料区--%>
                    <div style="margin-top: 16px;height: 32px">
                        <%--用户id--%>
                        <span>ID:admin</span>
                        <a data-toggle="modal" data-target="#information" href="#" style="float: right;">修改资料</a>
                    </div>
                        <%
                            try {
                                Class.forName(driver);
                                Connection con = DriverManager.getConnection(url,user,Mysqlpassword);
                                if (!con.isClosed()) System.out.println("数据库连接上了");
                                String sql = "select * from personal_data where ID='"+"admin"+"'";
                                Statement statement = con.createStatement();
                                ResultSet rs = statement.executeQuery(sql);
                                ArrayList<InforBean> list = new ArrayList<InforBean>();
                                while (rs.next()){
                                    InforBean inforBean = new InforBean();
                                    inforBean.setNike(rs.getString("nike"));
                                    inforBean.setName(rs.getString("name"));
                                    inforBean.setSex( rs.getString("sex"));
                                    inforBean.setBirth(rs.getString("birth"));
                                    inforBean.setInformation(rs.getString("information"));
                                    inforBean.setTeacher(rs.getString("teacher"));
                                    list.add(inforBean);
                                }
                                request.setAttribute("list", list);
                                rs.close();
                                con.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            ArrayList list = (ArrayList) request.getAttribute("list");
                            for (int i=0;i<list.size();i++){
                                InforBean inforBean = (InforBean) list.get(i);
                        %>
                    <ul id="localUl" style="list-style: none;padding-left: 0" >
                        <li id="liNike">
                            昵称：&nbsp<%=inforBean.getNike() %>
                        </li>
                        <li id="liName">
                            实名：&nbsp<%=inforBean.getName() %>
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
    <div class="modal fade" id="information" style="background-color: transparent; width: 100%; top: 80px;">
        <div class="modal-dialog">
            <div class="modal-content">

                <%-- 修改头部--%>
                <div class="modal-header">
                    <h5 class="modal-title">修改资料</h5>
                    <button id="ChangeClose" class="close" data-dismiss="modal">&times;</button>
                </div>

                <%--修改界面--%>
                <div class="modal-body">
                    <form id="upload" action="http://localhost:8080/ChangeInfor" method="POST">
                        <div class="form-group">
                            <label for="nike">昵称:</label>
                            <input type="text" class="form-control input1" id="nike">
                        </div>
                        <div class="form-group">
                            <label for="name">实名:</label>
                            <input type="text" class="form-control input1" id="name">
                        </div>
                        <div class="form-group">
                            <label for="sex">性别:</label>
                            <div class="dropdown">
                                <button id="sex" type="button" class="btn btn-light dropdown-toggle"
                                        data-toggle="dropdown" style="font-size: 15px" >
                                    请选择
                                </button>
                                <div class="dropdown-menu" style="min-width: 88px;">
                                    <a class="dropdown-item"  style="font-size: 13px"
                                       onclick="sexChoose('男')">男</a>
                                    <a class="dropdown-item"  style="font-size: 13px"
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
    
   /* function showInfor() {
        alert("showInfor调用");
        $.ajax({
            type:"POST",
            asynch: "false",
            url: "http://localhost:8080/changeInfor",
            data: {requestShow:"showInfor",ID:getCookie("username")},
            dataType: 'json'
            /!*success: function (nike,name,sex,birth,teacher,introduction) {
                alert("success成功");
                $("#liNike").text("昵称：" + nike);
                $("#liName").text("实名：" + name);
                $("#liSex").text("性别：" + sex);
                $("#liBirth").text("生日：" + birth);
                $("#liIntro").text("简介：" + introduction);
                $("#liTeacher").text("指导老师：" + teacher);
            },*!/
            success: function x() {
            alert("success")
        }
        });
    }*/

    $("#nike").val('<%=inforBean.getNike() %>');
    $("#name").val('<%=inforBean.getName() %>');
    $("#sex").text('<%=inforBean.getSex() %>');
    $("#birth").val('<%=inforBean.getBirth() %>');
    $("#teacher").val('<%=inforBean.getTeacher() %>');
    $("#introduction").val('<%=inforBean.getInformation() %>');
    <%  }%>
    function changeInfor() {
        var nike = $.trim($("#nike").val());
        var name = $.trim($("#name").val());
        var sex = $.trim($("#sex").text());
        if (sex=='请选择')  sex = '';
        var birth = $.trim($("#birth").val());
        var teacher = $.trim($("#teacher").val());
        var introduction = $.trim($("#introduction").val());
        var user = getCookie("username");
        var data = {
            ID: user,
            nike: nike,
            name: name,
            sex: sex,
            birth: birth,
            teacher: teacher,
            introduction: introduction
        };
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "http://localhost:8080/changeInfor",
            data: data,
            dataType: 'json',
            success: function (msg) {
                if (msg==2){
                    $("#ChangeClose").click();
                    $("#liNike").text("昵称：       "+ nike);
                    $("#liName").text("实名：        " + name);
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

</script>

</body>
</html>
