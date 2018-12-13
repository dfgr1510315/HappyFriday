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
        tbody tr td:not(:first-child){
            padding-top: 32px !important;
        }

        td button{
            margin-top: -7px;
        }

        .cover{
            width: 100px;height: 65px;margin-right: 5px
        }
    </style>
    <script type="text/javascript">
        function get_Class() {
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "/get_teaching",
                dataType: 'json',
                success: function (json) {
                    $(json).each(function(){
                        var Title = this.Title.toString();
                        var All_Title = encryption(Title);
                        $("tbody").append(
                            '<tr>\n' +
                                '<td><img src="${pageContext.request.contextPath}'+this.封面地址 +'" class="cover">'+this.Title+'</td> \n'+
                                '<td>'+this.学员数+'</td> \n'+
                                '<td>'+this.状态+'</td> \n'+
                                '<td><a target="_blank" href="Myclass.jsp?'+ All_Title+'"><button class="btn btn-outline-primary" >管理</button></a></td>\n'+
                            '<tr>\n '
                        )
                    })
                }
            });
        }
    </script>

</head>
<body onload="checkCookie();ifActive();addClass(3);get_Class()">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;margin: 80px auto auto;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <div style="width: 100%;height: 100%;">
                <h3 class="container_right_head">
                    在线课程
                    <button class="btn btn-outline-primary" style="float: right;margin-top: 28px">
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
                    <tbody>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

</body>
</html>
