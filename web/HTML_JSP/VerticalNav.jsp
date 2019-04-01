<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/14
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style type="text/css">
    #vertical_group .list-group-item{
        border: none;
        background:none;
        color: #787d82;
    }
    #vertical_group .list-group-item:hover{
        color: #1c1f21;
    }

    #vertical_group .list_action{
        color: white;
        background-color: rgba(0,123,255,0.70);
        border-radius: 8px 0 0 8px;
        pointer-events: none;
    }

    .container_left{
        width: 18%;
        float: left;
        /* background-color: pink;*/
        min-height: 240px;
        margin-top: 32px;
        margin-bottom: 64px;
    }

    .container_right{
        width: 80%;
        float: right;
        margin-top: 32px;
        background-color: white;
        min-height: 95%;
        padding: 0 32px 32px;
        box-shadow: 0 2px 4px 0 rgba(0,0,0,.05);
        margin-bottom: 60px;
        height: auto;
    }
</style>
<script type="text/javascript">
    function addClass(no) {
        if (usertype==='student'){
            $('#vertical_group').append(
                '<a id="Nav0" href="PersonalCenter.jsp" class="list-group-item ">个人资料</a>\n' +
                '<a id="Nav1" href="ChangePW.html" class="list-group-item">密码修改</a>\n' +
                '<a id="Nav2" href="Unbind.jsp" class="list-group-item ">账号绑定</a>\n' +
                '<a id="Nav5" href="LearingClass.jsp" class="list-group-item ">我的课程</a>\n' +
                '<a id="Nav6" href="My_question.jsp" class="list-group-item ">我发布的评论</a>\n' +
                '<a id="Nav7" href="My_answered.jsp" class="list-group-item ">我回复的评论</a>\n' +
                '<a id="Nav8" href="note.jsp" class="list-group-item ">我的笔记</a>\n' +
                '<a id="Nav9" href="notification.jsp" class="list-group-item ">我的通知</a>'
            )
        } else {
            $('#vertical_group').append(
                '<a id="Nav0" href="PersonalCenter.jsp" class="list-group-item ">个人资料</a>\n' +
                '<a id="Nav1" href="ChangePW.html" class="list-group-item">密码修改</a>\n' +
                '<a id="Nav2" href="Unbind.jsp" class="list-group-item ">账号绑定</a>\n' +
                '<a id="Nav3" href="Teaching.jsp" class="list-group-item " >在教课程</a>\n' +
                '<a id="Nav4" href="Student_commentary.jsp" class="list-group-item ">学员讨论</a>\n' +
                '<a id="Nav5" href="LearingClass.jsp" class="list-group-item ">我的课程</a>\n' +
                '<a id="Nav6" href="My_question.jsp" class="list-group-item ">我发布的评论</a>\n' +
                '<a id="Nav7" href="My_answered.jsp" class="list-group-item ">我回复的评论</a>\n' +
                '<a id="Nav8" href="note.jsp" class="list-group-item ">我的笔记</a>\n' +
                '<a id="Nav9" href="notification.jsp" class="list-group-item ">我的通知</a>'
            )
        }
        $("#Nav"+no).addClass('list_action');
    }
</script>
<body>
<div class="container_left" id="verticalNav" >
    <jsp:include page="radio_cover.jsp"/>
    <div class="list-group" id="vertical_group"></div>
</div>
</body>
</html>
