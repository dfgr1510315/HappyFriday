<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/11/25 0025
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Test</title>
    <link rel="stylesheet" type="text/css" href="../CSS/Learn_list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Learn_list.js"></script>

</head>
<script type="text/javascript">
    function get_Class() {
        $.ajax({
            type: "POST",
            asynch: "false",
            url: PageContext+"/learn_list",
            data: {
                action:'get_class',
                No:No
            },
            dataType: 'json',
            success: function (jsonObject) {
                $("#title").text(jsonObject.title);
                $("#teacher").text(jsonObject.teacher);
                $('#head').attr('src',jsonObject.head);
                $('#student_number').text(jsonObject.student_number);
                var class_type;
                switch (jsonObject.class_type) {
                    case '1':
                        class_type='前端设计';
                        break;
                    case '2':
                        class_type='后台设计';
                        break;
                    case '3':
                        class_type='基础理论';
                        break;
                    case '4':
                        class_type='嵌入式';
                        break;
                    case '5':
                        class_type='移动开发';
                        break;
                    case '6':
                        class_type='项目发布';
                        break;
                }
                $('#class_type').text(class_type);
                var done_times = 0;
                for (var a=0;a<jsonObject.Serial_No.length;a++){
                    var ifDone = cookie.get('time_'+user+No+jsonObject.Serial_No[a]);
                    if (ifDone ==='done')  done_times++;
                }
                var percentage = parseInt(done_times/jsonObject.Serial_No.length*100);
                var last_time = cookie.get('class_no_'+user+No);
                if(!last_time || last_time === undefined) {
                     $('.sno1-1').append(
                         '<button type="button" class="studyfont btn btn-outline-primary" onclick="join_class()">开始学习</button>'
                     )
                 }else {
                     $('.sno1-1').append(
                         '  <div class="learn-btn">\n' +
                         '      <div class="learn-info"><span>已学 '+percentage+'%</span></div>\n' +
                         '      <div class="progress">\n' +
                         '         <div class="progress-bar" style="width:'+percentage+'%"></div>\n' +
                         '     </div>\n' +
                         '     <div class="learn-info-media">上次学至 '+last_time+'</div>\n' +
                         '     <button type="button" class="studyfont btn btn-outline-primary" onclick="continue_class()">继续学习</button>\n' +
                         ' </div>'
                     )
                 }
                var flag='';var class_count = 0;
                for(var i=0;i<jsonObject.Unit_Name.length;i++){
                    if (!flag.match(jsonObject.Unit_Name[i].trim())){
                        flag = flag+','+jsonObject.Unit_Name[i].trim();
                    }
                }
                if (flag!=='') flag = flag.substring(1).split(",");
                for (i=0;i<flag.length;i++){
                    $("#one").append(
                        '<div id="Unit'+i+'" class="ui-box" draggable="true"  > ' +
                        '<span  style="margin-right: 20px;color: #999;font-weight: bold;">第'+(i+1)+'章 '+'</span>'+
                        '<span > '+flag[i]+'</span>'+
                        '</div>'
                    );
                    var Class_flag='';
                    for (var j=0;j<jsonObject.Serial_No.length;j++)  if (jsonObject.Serial_No[j].match((i+1)+'-'))  Class_flag+=','+jsonObject.Serial_No[j];
                    Class_flag = Class_flag.substring(1).split(",");
                    for (var k=0;k<Class_flag.length;k++){
                        if (jsonObject.State[class_count].trim()==='已发布'){
                            $("#Unit"+i).append(
                                '<div class="list_box" >' +
                                '    <a  href="Play.jsp'+window.location.search +'/'+Class_flag[k]+'">' +
                                '       <div class="ui-box1">' +
                                '           <i class="fa fa-adjust"></i>'+
                                '               <span style="margin-right: 20px;color: #999;">课时'+Class_flag[k].charAt(Class_flag[k].length-1) +'</span>'+
                                '               <span >'+jsonObject.Class_Name[class_count]+'</span>'+
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

<body onload="checkCookie();ifActive();get_Class()" style="background-color: #f8fafc;">
<jsp:include page="navigation.jsp"/>
<div class="main">
    <div class="inside">
        <div class="first">
            <a href="homepage.jsp">首页</a>
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
                <div class="t11"><a href="" target="_blank"><img id="head" src="" width="48px" height="48px"></a></div>
            </div>
            <div class="fouth">
                <span class="f1">教师</span>
                <span id="teacher" class="f1"></span>
            </div>

            <div class="fouth">
                <span class="f1">学习人数</span>
                <span id="student_number" class="f1"></span>
            </div>
            <div class="fouth">
                <span class="f1">课程类型</span>
                <span id="class_type" class="f1"></span>
            </div>

        </div>
    </div>
</div>
<!-- 课程菜单 -->
<div class="under">
    <div class="w">
        <ul class="nav nav-tabs" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="pill" href="#one">章节</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#two">问答</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#three">笔记</a>
            </li>
        </ul>
    </div>
</div>

<!-- 课程面板 -->
<div>
    <div class="course">
        <div class="tab-content">
            <div id="one" class="container tab-pane active margin">

            </div>
            <!-- 问答评论 -->
            <div id="two" class="container tab-pane fade margin">
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
            <div id="three" class="container tab-pane fade margin">
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
        </div>


        <!-- 侧边栏调整 -->
        <div class="aside right">
            <div class="course-introduction-main">
                <div class="son1">
                    <div class="sno1-1">

                    </div>
                 <%--   <div class="inbelow">
                        <h3 class="fontset-headline">课程须知</h3>
                        <p class="fontbody">适合对React视图工具有一定的实际开发经验，特别是对redux有一定的使用经验，想了解其它类似解决方案的同学</p>
                        <h3 class="fontset-headline">老师告诉你能学到什么？</h3>
                        <p class="fontbody">mobx的使用方法，对React项目的性能优化经验</p>
                    </div>--%>
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
                            <a href="#" class="hot2">Web前端常用框架</a>
                            <a href="#" class="hot2">防站开发</a>
                            <a href="#" class="hot3">最实用得前端开发框架教程</a>
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
