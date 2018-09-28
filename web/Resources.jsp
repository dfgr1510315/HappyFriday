<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/9/18
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>资源</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <%--<script type="text/javascript">
        angular.module('ionicApp', ['ionic'])

            .controller('SlideController', function ($scope) {

                $scope.myActiveSlide = 1;

            })

            .controller('MyCtrl', function ($scope) {
                var arr = [["HTML/CSS", "HTML", "HTML5", "CSS", "CSS3", "Bootstrap3", "Font Awesome", "Foundation"],
                    ["JavaScript", "JavaScript", "HTML DOM", "jQuery", "AngularJS2", "Vue.js", "Node.js", "AJAX", "JSON"],
                    ["服务端", "PHP", "Python", "Django", "Linux", "Docker", "Ruby", "Java", "C", "C++", "Servlet", "JSP"],
                    ["数据库", "SQL", "Mysql", "SQLite"],
                    ["移动端", "Android", "Swift", "ionic"],
                    ["XML教程", "XML", "DTD", "XPath", "XQuery"],
                    ["Web Service", "Web Service", "WSDL", "SOAP", "RSS"],
                    ["开发工具", "Eclipse", "Git"],
                    ["网站建设", "HTTP", "浏览器信息", "TCP/IP", "W3C"]];
                $scope.groups = [];
                for (var i = 0; i < arr.length; i++) {
                    $scope.groups[i] = {
                        name: arr[i][0],
                        items: [],
                        show: false
                    };
                    for (var j = 1; j < arr[i].length; j++) {
                        $scope.groups[i].items.push(arr[i][j]);
                    }
                }

                $scope.toggleGroup = function (group) {
                    group.show = !group.show;
                };
                $scope.isGroupShown = function (group) {
                    return group.show;
                };

            });
    </script>--%>

    <style>
        body {
            background: #fffef5;
        }

        .item-top{
            width: 32%;
            margin: 0px 8px 9px 0px;
            text-align: left;
            height: 80px;
            color: #bbb;
            background-color: #f6f6f6;
            border-radius: 5px;
            padding: 6px 16px;
        }

        .item-top:hover{
            background-color: #e5e5e5;
        }

        .codelist{
            float: left;
            width: 95%;
        }

        .codelist a{
            float: left;
        }


        .codelist h4 i{
            display: inline-block;
            background-size: cover;
            background-image: url(image/tags.png);
            height: 19px;
            width: 19px;
        }

        .codelist strong{
            font-size: 12px;
            margin-left: 10px;
        }

    </style>
</head>
<body >
<div  style="position:fixed;top:0;left:0;right:0;width: 100% ;padding-left: 40px;padding-top: 9px;padding-bottom: 9px;background-color: white; z-index: 6;">
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link" href="homepage.jsp">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Resources.jsp">文档</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="modal" data-target="#LoginModal" href="Resources.jsp">登录</a>
        </li>
    </ul>
</div>

<div class="modal fade" id="LoginModal" style="background-color: transparent; width: 100%; top: 165px;">
    <div class="modal-dialog">
        <div class="modal-content">

            <%-- 登录框头部--%>
            <div class="modal-header">
                <h4 class="modal-title">登录</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <%--登录界面--%>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="email">用户名:</label>
                        <input type="email" class="form-control" id="email" placeholder="Enter username">
                    </div>
                    <div class="form-group">
                        <label for="pwd">密码:</label>
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password">
                    </div>
                    <div class="form-check">
                        <label class="form-check-label">
                            <input class="form-check-input" type="checkbox"> 记住我
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary" style="margin-top: 15px">登录</button>
                </form>
            </div>

            <%--登录底部--%>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">。。。</button>
            </div>--%>
        </div>
    </div>
</div>

<div>
    <div style="width: 100%;height: 2350px;margin-top: 44px;margin-left: auto;margin-right: auto;">
        <div style="width: 80%;margin: auto;">
            <div style="width: 15%;height: 100%;float: left">
                <div style="width: 100%;height: 100%;padding-top: 50px;">
                    <i style="display: inline-block; background-size: cover;  background-image: url(image/tags.png);height: 19px; width: 19px; "></i>
                    <span style="color: #999999">文档分类</span>
                    <div  style="margin-top: 20px;  min-height:200px;  background-color: #fff;">
                        <div id="accordion">
                                <%
                                    String arr[][] = {{"HTML/CSS", "HTML", "HTML5", "CSS", "CSS3", "Bootstrap3", "Font Awesome", "Foundation"},
                                            {"JavaScript", "JavaScript", "HTML DOM", "jQuery", "AngularJS2", "Vue.js", "Node.js", "AJAX", "JSON"},
                                            {"服务端", "PHP", "Python", "Django", "Linux", "Docker", "Ruby", "Java", "C", "C++", "Servlet", "JSP"},
                                            {"数据库", "SQL", "Mysql", "SQLite"},
                                            {"移动端", "Android", "Swift", "ionic"},
                                            {"XML教程", "XML", "DTD", "XPath", "XQuery"},
                                            {"Web Service", "Web Service", "WSDL", "SOAP", "RSS"},
                                            {"开发工具", "Eclipse", "Git"},
                                            {"网站建设", "HTTP", "浏览器信息", "TCP/IP", "W3C"}};
                                    for (int i=0;i<arr.length;i++){
                                        out.println("<div class=\"card\" >");
                                        out.println("<div class=\"card-header\">");
                                        out.print("<a class=\"collapsed card-link\" data-toggle=\"collapse\" href=\"#collapse"+i+"\">");
                                        out.println(arr[i][0]);
                                        out.println("</a>");
                                        out.println("</div>");
                                        out.println("<div id=\"collapse"+i+"\" class=\"collapse \" data-parent=\"#accordion\">");
                                        for (int j=1;j<arr[i].length;j++){
                                            out.println("<div class=\"card-body\">");
                                            out.println(arr[i][j]);
                                            out.println("</div>");
                                        }
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                %>
                        </div>
                    </div>
                </div>
            </div>

            <div style="width: 83%;height: 100%;float: right">
                <div style=" margin-top: 93px;width: 100%;border-color: #b2b2b2;border-style: solid;border-width: 1px;border-radius: 3px;min-height: 2200px; margin-bottom: 100px;  padding-left: 20px;padding-top: 20px; padding-right:20px;  background-color: white;">
                    <div class="codelist">
                        <h4 ><i></i>HTML/CSS</h4>
                        <a class="item-top">
                            <h5>【HTML】</h5>
                            <strong>HTML是一种文本标记语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【HTML5】</h5>
                            <strong>HTML5是下一代HTML标准</strong>
                        </a>
                        <a class="item-top">
                            <h5>【CSS】</h5>
                            <strong>层叠样式表</strong>
                        </a>
                        <a class="item-top">
                            <h5>【CSS3】</h5>
                            <strong>CSS3是CSS技术的升级版本</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Bootstrap3】</h5>
                            <strong>来自Twitter，是目前最受欢迎的前端框架</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Font Awesome】</h5>
                            <strong>一套绝佳的图标字体库的CSS框架</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Foundation】</h5>
                            <strong>用于开发响应式的HTML，CSS和JS框架</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>JavaScript</h4>
                        <a class="item-top">
                            <h5>【JavaScript】</h5>
                            <strong>JavaScript是Web的编程语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【HTML DOM】</h5>
                            <strong>HTML DOM定义了访问和操作HTML文档的标准方法</strong>
                        </a>
                        <a class="item-top">
                            <h5>【jQuery】</h5>
                            <strong>是一个JavaScript库</strong>
                        </a>
                        <a class="item-top">
                            <h5>【AngularJS2】</h5>
                            <strong>通过新的属性和表达式扩展了HTML</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Vue.js】</h5>
                            <strong>是一套构建用户界面的渐进式框架</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Node.js】</h5>
                            <strong>是运行在服务器端的JavaScript</strong>
                        </a>
                        <a class="item-top">
                            <h5>【AJAX】</h5>
                            <strong>异步的JavaScript和XML</strong>
                        </a>
                        <a class="item-top">
                            <h5>【JSON】</h5>
                            <strong>是存储和交换文本信息的语法</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>服务端</h4>
                        <a class="item-top">
                            <h5>【PHP】</h5>
                            <strong>通用开源脚本语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Python】</h5>
                            <strong>是一种面向对象、解释型计算机程序设计语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Django】</h5>
                            <strong>是一个开放源代码的Web应用框架，由Python写成</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Linux】</h5>
                            <strong>是一套免费使用和自由传播的类Unix操作系统</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Docker】</h5>
                            <strong>是一个开源的应用容器引擎，基于Go语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Ruby】</h5>
                            <strong>一种为简单快捷的面向对象编程而创的脚本语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Java】</h5>
                            <strong>一种可以撰写跨平台应用软件的面向对象的程序设计语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【C】</h5>
                            <strong>一门通用计算机编程语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【C++】</h5>
                            <strong>C++是在C语言的基础上开发的一种通用编程语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Servlet】</h5>
                            <strong>运行在Web服务器或应用服务器上的程序</strong>
                        </a>
                        <a class="item-top">
                            <h5>【JSP】</h5>
                            <strong>JSP与PHP、ASP、ASP.NET等语言类似，运行在服务器的语言</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>数据库</h4>
                        <a class="item-top">
                            <h5>【SQL】</h5>
                            <strong>结构化查询语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【MySql】</h5>
                            <strong>MySQL是一个关系型数据库管理系统</strong>
                        </a>
                        <a class="item-top">
                            <h5>【SQLite】</h5>
                            <strong>一款轻型的数据库</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>移动端</h4>
                        <a class="item-top">
                            <h5>【Android】</h5>
                            <strong>是一种基于Linux的自由及开放源代码的操作系统，只要使用于移动设备</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Swift】</h5>
                            <strong>是一种支持多编程范式和编译式的编译语言，用于开发IOS，OS X和watchOS应用程序</strong>
                        </a>
                        <a class="item-top">
                            <h5>【ionic】</h5>
                            <strong>是一个强大的HTML5应用程序开发框架</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>XML教程</h4>
                        <a class="item-top">
                            <h5>【XML】</h5>
                            <strong>XML被设计用来传输和存储数据</strong>
                        </a>
                        <a class="item-top">
                            <h5>【DTD】</h5>
                            <strong>DTD(文档类型定义)的作用是定义XML文档的合法构建模块</strong>
                        </a>
                        <a class="item-top">
                            <h5>【XPath】</h5>
                            <strong>XPath是一门在XML文档中查找信息的语言</strong>
                        </a>
                        <a class="item-top">
                            <h5>【XQuery】</h5>
                            <strong>XQuery被设计用来查询XML数据</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>Web Service</h4>
                        <a class="item-top">
                            <h5>【Web Service】</h5>
                            <strong>Web Service脚本平台需要支持XML+HTTP</strong>
                        </a>
                        <a class="item-top">
                            <h5>【WSDL】</h5>
                            <strong>WSDL是一门基于XML的语言，用于描述Web Service以及如何对他们进行访问</strong>
                        </a>
                        <a class="item-top">
                            <h5>【SOAP】</h5>
                            <strong>SOAP是一种简单的基于XML的协议，它使应用程序通过HTTP来交换信息</strong>
                        </a>
                        <a class="item-top">
                            <h5>【RSS】</h5>
                            <strong>RSS基于XML标准，在互联网上被广泛使用的内容包装和投递协议</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>开发工具</h4>
                        <a class="item-top">
                            <h5>【Eclipse】</h5>
                            <strong>Eclipse是一个开放源代码的、基于Java的可扩展开发平台</strong>
                        </a>
                        <a class="item-top">
                            <h5>【Git】</h5>
                            <strong>Git是一个开源的分布式版本控制系统，用于敏捷高效地处理任何或小或大的项目</strong>
                        </a>
                    </div>
                    <div class="codelist">
                        <h4><i></i>网站建设</h4>
                        <a class="item-top">
                            <h5>【HTTP】</h5>
                            <strong>HTTP协议是因特网上应用最为广泛的一种网络传输协议</strong>
                        </a>
                        <a class="item-top">
                            <h5>【浏览器信息】</h5>
                            <strong>对于网站开发人员来说，浏览器信息和统计数据都是非常重要的</strong>
                        </a>
                        <a class="item-top">
                            <h5>【TCP\IP】</h5>
                            <strong>TCP\IP是因特网的通信协议</strong>
                        </a>
                        <a class="item-top">
                            <h5>【W3C】</h5>
                            <strong>W3C让每个人都能在互联网上分享资源</strong>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="foot" style="float: left; background-color: white;width: 100%; height: 200px; margin-bottom: 0;border-color: #b2b2b2;border-style: solid;border-width: 1px;margin-bottom: 20px;">

    </div>
</div>
</body>
</html>
