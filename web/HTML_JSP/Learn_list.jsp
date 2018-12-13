<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/11/25 0025
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Test</title>
    <script type="text/javascript" src="../JS/Learn_list.js"></script>
    <link rel="stylesheet" type="text/css" href="../CSS/Learn_list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function get_Class() {
            var title = decrypt(window.location.search.replace("?",'').split("/"));
            $("#title").text(title);
            $.ajax({
                type: "POST",
                asynch: "false",
                url: "/learn_list",
                data: {
                    title:title
                },
                dataType: 'json',
                success: function (jsonObject) {
                    var Serial_No = jsonObject.Serial_No.toString().replace("[",'').replace("]",'').split(",");
                    var Unit_Name = jsonObject.Unit_Name.toString().replace("[",'').replace("]",'').split(",");
                    var Class_Name = jsonObject.Class_Name.toString().replace("[",'').replace("]",'').split(",");
                    var State = jsonObject.State.toString().replace("[",'').replace("]",'').split(",");
                    $("#teacher").text(jsonObject.teacher);
                    var flag='';var class_count = 0;
                    for(var i=0;i<Unit_Name.length;i++){
                        if (!flag.match(Unit_Name[i].trim())){
                            flag = flag+','+Unit_Name[i].trim();
                        }
                    }
                    flag = flag.substring(1).split(",");
                    for (i=0;i<flag.length;i++){
                        $("#one").append(
                            '<div id="Unit'+i+'" class="ui-box" draggable="true"  > ' +
                            '<span class="part_title">第'+(i+1)+'章 '+flag[i]+'</span>'+
                            '</div>'
                        );
                        var Class_flag='';
                        for (var j=0;j<Serial_No.length;j++)  if (Serial_No[j].match((i+1)+'_'))  Class_flag+=','+Serial_No[j];
                        Class_flag = Class_flag.substring(1).split(",");
                        for (var k=0;k<Class_flag.length;k++){
                            if (State[class_count].trim()==='已发布'){
                                $("#Unit"+i).append(
                                    '<div  >' +
                                    '    <a href="">' +
                                    '       <div class="ui-box1">' +
                                    '           <i class="iconfont icon-bofang"></i>'+
                                    Class_flag[k]+ Class_Name[class_count]+
                                    '       </div>'+
                                    '    </a>'+
                                    ' </div>'
                                );
                            }
                            class_count++;
                        }
                        //alert(Class_flag);
                    }
                    //alert(Serial_No+"\n"+Unit_Name+"\n"+Class_Name+"\n"+Video_Src+"\n"+Editor+"\n"+File_Href+"\n"+State);
                }
            });

        }
    </script>
</head>

<body onload="checkCookie();ifActive();get_Class()" style="background-color: #f8fafc;">
<jsp:include page="navigation.jsp"/>
<jsp:include page="LoginPC.jsp"/>
<div class="main">
    <div class="inside">
        <div class="first">
            <a href="#">课程</a>
            <i class="i">\</i>
            <a href="#">前端开发</a>
            <i class="i">\</i>
            <a href="#">JavaScript</a>
            <i class="i">\</i>
            <a href="#">JavaScript入门篇</a>
            <i class="i">\</i>
        </div>
        <div class="second">
            <h2 id="title" class="left"></h2>
        </div>
        <div class="third">
            <div class="t1">
                <div class="t11"><a href="" target="_blank"><img src="../image/68296699_p0.png" width="48px" height="48px"></a></div>
                <div id="teacher" class="t2">admin<%--<div class="t3">页面重构设计</div>--%></div>
                <%--<div class="t22"><img src="慕课网首页.jpg" width="16" height="16" ></div>--%>
            </div>
            <div class="fouth">
                <span class="f1">难度</span>
                <span class="f1">入门</span>
            </div>
            <div class="fouth">
                <span class="f1">时长</span>
                <span class="f1">1小时53分</span>
            </div>
            <div class="fouth">
                <span class="f1">学习人数</span>
                <span class="f1">611884</span>
            </div>
            <div class="fouth">
                <span class="f1">综合评分</span>
                <span class="f1">9.6</span>
            </div>
        </div>
    </div>
</div>
<!-- 课程菜单 -->
<div class="under">
    <div class="w">
        <ul>
            <li><a href="#" onClick="change_one()" class="col">课程章节</a></li>
            <li><a href="#" onClick="change_two()">问答评论</a></li>
            <li><a href="#" onClick="change_three()">同学笔记</a></li>
            <li><a href="#" onClick="change_four()">用户评价</a></li>
            <li><a href="#" onClick="change_five()">wiki</a></li>
        </ul>
    </div>
</div>

<!-- 课程面板 -->
<div class="include">
    <div class="course">
        <!-- <iframe name="mainFrame" width="800px" height="450px" scrolling="yes" norsize="norsize" src="链接文件/1.html"> -->
        <!-- 下方显示 start-->
        <!-- 课程章节盒子 -->
        <div id="one" class="container">
            <div class="ui-box">
                简介
            </div>
        </div>
        <!-- 问答评论 -->
        <div id="two" class="container">
            <div class="circle">
                <ul>
                    <li><a href="#"><div class="col_1">最新</div></a></li>
                    <li><a href="#"><div>评论</div></a></li>
                    <li><a href="#"><div>问答</div></a></li>
                </ul>
            </div>
            <div class="comment-list">
                <ul>
                    <li>
                        <div class="ui-box">
                            1
                        </div>
                    </li>
                    <li>
                        <div class="ui-box">
                            2
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 同学笔记 -->
        <div id="three" class="container">
            <div class="circle">
                <ul>
                    <li><a href="#"><div class="col_1">最新</div></a></li>
                    <li><a href="#"><div>点赞</div></a></li>
                </ul>
            </div>
            <div class="comment-list">
                <ul>
                    <li>
                        <div class="ui-box">
                            1
                        </div>
                    </li>
                    <li>
                        <div class="ui-box">
                            2
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 用户评价 -->
        <div id="four" class="container">
            <div class="ui-box">
                用户评价
            </div>
        </div>
        <div id="five" class="container">
            <div class="ui-box">
                JavaScript
            </div>
        </div>
        <!-- 下方显示 end-->

        <!-- 侧边栏调整 -->
        <div class="aside right">
            <div class="course-introduction-main">
                <div class="son1">
                    <div class="sno1-1">
                        <a href="#" class="studyfont">开始学习</a>
                    </div>
                    <div class="inbelow">
                        <h3 class="fontset-headline">课程须知</h3>
                        <p class="fontbody">适合对React视图工具有一定的实际开发经验，特别是对redux有一定的使用经验，想了解其它类似解决方案的同学</p>
                        <h3 class="fontset-headline">老师告诉你能学到什么？</h3>
                        <p class="fontbody">mobx的使用方法，对React项目的性能优化经验</p>
                    </div>
                </div>
                <div class="fontset-headline">推荐课程
                    <div class="sno2-allbox">
                        <img src="https://img3.mukewang.com/szimg/58a68f000001262805400300-360-202.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img3.mukewang.com/szimg/58a68f000001262805400300-360-202.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">111111111111111111111111111111111111111</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img2.mukewang.com/szimg/5a17ef670001292c05400300-360-202.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">111111111111111111111111111111111111111</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img3.mukewang.com/szimg/59006d090001508305400300-360-202.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">111111111111111111111111111111111111111</a>
                            </div>
                    </div>
                    <div><img src="https://img3.mukewang.com/szimg/58a68f000001262805400300-360-202.jpg" class="imagesize">
                        <div class="sno2-allbox">
                            <img src="https://img4.mukewang.com/szimg/58a68f000001262805400300-360-202.jpg" class="imagesize">
                                <div class="coursecount" >
                                    <a href="#" class="coursecount-font">111111111111111111111111111111111111111</a>
                                </div>
                        </div>
                </div>
                <div class="son3 fontset-headline">热门专题标签
                    <div class="hotrecommend">
                        <div >
                            <a href="#" class="hot1">用Query实现一小应用</a>
                            </br>
                            <a href="#" class="hot2">Web前端常用框架</a>
                            <a href="#" class="hot2">防站开发</a>
                            <a href="#" class="hot3">最实用得前端开发框架教程</a>
                            </br>
                            <a href="#" class="hot1">js入门基础</a>
                            <a href="#" class="hot4">全栈开发技能</a>
                        </div>
                    </div>
                </div>
                <div class="sno4 fontset-headline">相关课程
                    <div class="sno2-allbox">
                        <img src="https://img1.mukewang.com/5b8e323900017f7406000338-240-135.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img3.mukewang.com/5bc44d080001049906000338-240-135.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img4.mukewang.com/5b28da010001930906000338-240-135.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img2.mukewang.com/5b4ed6590001d9ee06000338-240-135.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                    <div class="sno2-allbox">
                        <img src="https://img2.mukewang.com/5b7bfaa20001ec6006000338-240-135.jpg" class="imagesize">
                            <div class="coursecount">
                                <a href="#" class="coursecount-font">在本章中，将会通过编写一个简单的高阶组件，来加深对高阶组件概念的理解；</a>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
<%--<body onload="get_Class()">

<div id="list_body">

</div>
</body>--%>

</html>
