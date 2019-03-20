<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/3/19 0019
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>搜索结果</title>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/search.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/My_question.css">
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/LoginPC.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/search.js"></script>
</head>
<script type="text/javascript">
    $(document).ready(function () {
        if (keyword.trim()==='')  return;
        $('#search_ipt').val(keyword);
        add_href();
        search_ask()
    });
</script>
<body style="background: #f8fafc;">
<jsp:include page="../navigation.jsp"/>
<div id="main">
    <div class="search-main">
        <div class="search-header">
            <div class="search-form">
                <i class="fa fa-search"></i>
                <input id="search_ipt" type="text" class="search-form-ipt js-search-ipt" value="数" placeholder="请输入想搜索的内容">
                <button type="button" class="search-form-btn js-search-btn btn btn-light" onclick="search_to()">搜索</button>
                <div class="search-history-area js-search-history"></div>
            </div>
        </div>
        <div class="search-body">
            <div class="search-content">
                <div class="search-classify clearfix">
                    <ul class="nav nav-tabs nav-justified">
                        <li class="nav-item">
                            <a class="nav-link " href="video.jsp">课程</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="user.jsp">用户</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="javascript:void(0)">问答</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">笔记</a>
                        </li>
                    </ul>
                    <span class="search-related js-all-count">
                        <em id="count" style="font-style: normal;"></em>
                    </span>
                </div>
                <ul id="ask_ul"></ul>
            </div>
            <div id="page" class="pagination"></div>
        </div>

    </div>
</div>
</body>
</html>
