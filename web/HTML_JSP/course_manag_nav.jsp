<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/1/21 0021
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<style type="text/css">
    .container_left{
        width: 18%;
        float: left;
        /* background-color: pink;*/
        min-height: 240px;
        margin-top: 32px;
        margin-bottom: 100px;
    }
</style>
<body >
    <div class="container_left" >
        <div class="list-group">
            <a href="Myclass.jsp" class="list-group-item ">课时管理</a>
            <a href="basic_information.jsp" class="list-group-item">基本信息</a>
            <a href="image.jsp" class="list-group-item ">课程封面</a>
            <a href="duration.jsp" class="list-group-item ">学员统计</a>
            <a href="instructors.jsp" class="list-group-item ">教师设置</a>
            <a href="students.jsp" class="list-group-item ">学员管理</a>
            <a href="#" class="list-group-item ">删除课程</a>
        </div>
    </div>
</body>
<script type="text/javascript">
    function addHref(){
        var list_group = $(".list-group");
        for (var i=0;i<7;i++){
            var href = list_group.children().eq(i).attr('href');
            list_group.children().eq(i).attr('href',href+location.search);
        }
    }

</script>
</html>
