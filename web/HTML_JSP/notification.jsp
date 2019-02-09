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
    </style>
    <script type="text/javascript">

    </script>

</head>
<body onload="checkCookie();ifActive();addClass(9)">
<jsp:include page="navigation.jsp"/>
<div style="width: 100%;height: 450px">
    <div style="width: 80%;margin: auto">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">

        </div>
    </div>
</div>

</body>
</html>
