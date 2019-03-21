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
    <title>个人中心</title>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/My_question.css">
    <script type="text/javascript" src="../JS/LoginPC.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            get_my_Ask();
        });

        var PageContext = $("#PageContext").val();

        function GetQueryString(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null) return  unescape(r[2]); return null;
        }

        function get_my_Ask() {
            var page = GetQueryString('page_ask');
            if(page == null) page='1';
            $.ajax({
                type: "POST",
                asynch: "false",
                url: PageContext + "/postask",
                data: {
                    action: 'get_my_ask',
                    page:page,
                    author:user
                },
                dataType: 'json',
                success: function (jsonObj) {
                    var ask_ul = $('#ask_ul');
                    for (i=0;i<jsonObj.ask_no.length;i++){
                        ask_ul.append(
                            ' <li>\n' +
                            '                            <div class="ui-box">\n' +
                            '                                <div class="headslider qa-medias l">\n' +
                            '                                    <a class="media" target="_blank" href="" title="'+user+'">\n' +
                            '                                        <img src="'+"${pageContext.request.contextPath}"+head_image+'" width="40px" height="40px">\n' +
                            '                                    </a>\n' +
                            '                                </div>\n' +
                            '                                <div class="wendaslider qa-content">\n' +
                            '                                    <h2 class="wendaquetitle qa-header">\n' +
                            '                                        <div class="wendatitlecon qa-header-cnt clearfix">\n' +
                            '                                            <a class="qa-tit" target="_blank" href="questions.jsp?'+jsonObj.ask_no[i]+'">\n' +
                            '                                                <i>'+jsonObj.describe[i]+'</i>\n' +
                            '                                            </a>\n' +
                            '                                        </div>\n' +
                            '                                    </h2>\n' +
                            '                                    <div class="replycont qa-body clearfix">\n' +
                            '                                        <div class="l replydes" id="new_reply'+jsonObj.ask_no[i]+'">\n' +
                            '                                        </div>\n' +
                            '                                    </div>\n' +
                            '                                    <div class="replymegfooter qa-footer clearfix">\n' +
                            '                                        <div class="l-box l">\n' +
                            '                                            <a class="replynumber static-count " target="_blank" href="questions.jsp?'+jsonObj.ask_no[i]+'">\n' +
                            '                                                <span class="static-item answer">'+jsonObj.answer_count[i]+' 回答</span>\n' +
                            '                                                <span class="static-item">'+jsonObj.times[i]+' 浏览</span>\n' +
                            '                                            </a>\n' +
                            '                                            <a href="Learn_list.jsp?='+jsonObj.class_no[i]+'" target="_blank">'+jsonObj.class_title[i]+'</a>\n' +
                            '                                            <a href="Play.jsp?'+jsonObj.class_no[i]+'/'+jsonObj.title_No_list[i]+'" target="_blank">'+jsonObj.title_No_list[i]+'  '+jsonObj.title[i]+'</a>\n' +
                            '                                        </div>\n' +
                            '                                        <em class="r">'+jsonObj.time[i]+'</em>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </li>'
                        );
                        if (jsonObj.new_answer[i]===null){
                            $('#new_reply'+jsonObj.ask_no[i]).append(
                                '<button type="button" class="btn btn-light" onclick="window.open(\'questions.jsp?'+jsonObj.ask_no[i]+'\')">我来回答</button>\n'
                            )
                        } else {
                            $('#new_reply'+jsonObj.ask_no[i]).append(
                                '<span class="replysign">\n' +
                                ' 最新回复 /\n' +
                                '   <a class="nickname" target="_blank" href="">'+jsonObj.new_answer[i]+'</a>\n' +
                                ' </span>\n' +
                                ' <div class="replydet">'+jsonObj.new_answer_text[i]+'</div>\n'
                            )
                        }
                    }

                    if (ask_ul.html().length === 0) {
                        ask_ul.append(
                            '<div class="no_find_class">暂无讨论</div> '
                        )
                    }else {
                        var page_ul =  $('#page_ask');
                        page_length = Math.ceil(jsonObj.count/6);
                        if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?page_ask='+(parseInt(page)-1)+'">Previous</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                        if (page-1>2&&page_length-page>=2){
                            for (var i = page-2;i<=page+2;i++){
                                if (i<page_length+1){
                                    if (i===parseInt(page)){
                                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                    }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                }
                            }
                        }else {
                            if (page<=3){
                                for (i=1;i<6;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                        }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }else {
                                for (i=page_length-4;i<=page_length;i++){
                                    if (i<page_length+1){
                                        if (i===parseInt(page)){
                                            page_ul.append(' <li class="page-item active"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                        }else page_ul.append(' <li class="page-item"><a class="page-link" href="?page_ask='+i+'">'+i+'</a></li>');
                                    }
                                }
                            }

                        }

                        if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?page_ask='+(parseInt(page)+1)+'">Next</a></li>');
                        else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>');

                        page_ul.append(
                            '<li class="page-item last">\n' +
                            '<a >\n' +
                            '<label>\n' +
                            '<input id="jump" type="text" class="form-control" onkeydown="go();">\n' +
                            '</label>\n' +
                            '<span id="count_page"> </span>\n' +
                            '</a>\n' +
                            '</li>');
                        $('#count_page').text('/ '+page_length);
                    }
                }
            });
        }
        function go(){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode  === 13) {
                var jump = $('#jump');
                if (jump.val()>0&&jump.val()<=page_length&&jump.val()%1 === 0){
                    window.location.href="Student_commentary.jsp?"+jump.val();
                }else alert('请重新输入页号');
            }
        }
    </script>

</head>
<body onload="ifActive();addClass(6)">
<jsp:include page="navigation.jsp"/>
<div>
    <div style="width: 80%;margin: 10px auto auto;">
        <jsp:include page="VerticalNav.jsp"/>
        <div class="container_right">
            <h3 class="container_right_head">
                我的讨论
            </h3>
            <div class="comment-list">
                <ul id="ask_ul" style="padding-left: 0"></ul>
            </div>
            <ul id="page_ask" class="pagination pagination-sm"></ul>
        </div>
    </div>
</div>

</body>
</html>
