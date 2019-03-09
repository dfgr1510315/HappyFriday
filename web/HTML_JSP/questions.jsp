<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/2/15 0015
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>讨论</title>
    <link rel="stylesheet" type="text/css" href="../CSS/Learn_list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation_dark.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Learn_list.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
</head>
<style type="text/css">
    .questions_main {
        width: 80%;
        margin-left: auto;
        margin-right: auto;
    }

    .qa-header {
        position: relative;
        height: 40px;
        padding-top: 16px;
    }

    .qa-author img {
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
        display: inline-block;
        vertical-align: middle;
        margin-right: 7px;
    }

    .qa-content {
        padding: 15px 0;
        margin: 0 0;
        border-bottom: 1px solid #edf1f2;
    }

    .qa-wenda-title {
        word-break: break-all;
        word-wrap: break-word;
        overflow: hidden;
        font-weight: bolder;
        font-size: 16px;
        line-height: 32px;
    }

    .rich-text {
        margin-top: 5px;
    }

    .qa-disscus, .qa-wenda {
        line-height: 20px;
        font-size: 14px;
        word-break: break-all;
        word-wrap: break-word;
        overflow: hidden;
    }

    .qa-content-addon {
        position: relative;
        padding-top: 15px;
        font-size: 12px;
        color: #d0d6d9;
    }

    .qa-createtime {
        margin-left: 20px;
        color: #c8cdd2;
        line-height: 24px;
    }

    .qa-total-comment, .qa-view-num {
        margin-left: 20px;
        line-height: 24px;
        color: #c8cdd2;
    }

    .qa-total-comment, .qa-view-num {
        margin-left: 20px;
        line-height: 24px;
        color: #c8cdd2;
    }

    .l {
        float: left;
    }

    .r {
        float: right;
    }

    .qa-comment {
        position: relative;
        min-height: 80px;
        border-bottom: 1px solid #edf1f2;
    }

    .qa-comment {
        position: relative;
        min-height: 80px;
        border-bottom: 1px solid #edf1f2;
    }

    .qa-comment-wrap {
        padding: 10px 0;
        position: relative;
    }

    .qa-comment-author {
        float: left;
        line-height: 1em;
        width: 90px;
        padding-top: 10px;
        text-align: center;
    }

    .qa-comment-author img {
        width: 40px;
        border-radius: 50%;
    }

    .qa-comment-nick {
        display: block;
        font-size: 13px;
        line-height: 1.2em;
        height: 18px;
        margin: 3px 10px auto;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .qa-comment-d {
        position: relative;
        margin-left: 90px;
        padding: 10px 0 10px 20px;
    }

    .qa-reply {
        clear: both;
        margin-top: 9px;
        border-bottom: 0;
    }

    .qa-comment-addon {
        position: relative;
        padding: 10px 0 0;
        font-size: 12px;
        color: #c8cdd2;
    }

    .qa-comment-c {
        line-height: 20px;
        font-size: 14px;
        word-break: break-all;
        word-wrap: break-word;
        overflow: hidden;
    }

    .js-qa-tr-num .fa-commenting-o {
        margin-right: 3px;
    }

    .js-qa-tr-num {
        margin-left: 10px;
        cursor:pointer;
    }
    .js-qa-tr-num:hover {
        color: #007bff;
    }

    .qa-reply {
        clear: both;
        margin-top: 9px;
        border-bottom: 0;
    }

    .qa-replies {
        margin-bottom: -1px;
    }

    .qa-reply-c {
        position: relative;
    }

    .qa-reply-item {
        position: relative;
        border-bottom: 1px solid #edf1f2;
    }

    .qa-reply-item-author img {
        width: 40px;
        border-radius: 50%;
    }

    .qa-reply-item-inner {
        margin-left: 50px;
        font-size: 12px;
        padding: 17px 0 17px 10px;
    }

    .q-reply-item-c {
        font-size: 12px;
        line-height: 1.7em;
        padding-top: 3px;
        word-break: break-all;
        word-wrap: break-word;
        overflow: hidden;
    }

    .qa-reply-item-foot {
        color: #c8cdd2;
        font-size: 12px;
        padding: 10px 0 7px;
    }

    .qa-reply-item-author {
        position: absolute;
        left: 0;
        top: 20px;
        line-height: 1em;
    }

    .qa-reply {
        border-top: 1px solid #edf1f2;
        padding-left: 110px;
    }

    .qa-reply-ibox {
        padding: 20px 0;
        border-bottom: 1px solid #e3e5e5;
    }

    .qa-reply-iavator {
        width: 60px;
        text-align: center;
    }

    .qa-reply-iavator img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .qa-reply-iwrap {
        padding-left: 7px;
        overflow: hidden;
        margin-left: 60px;
    }

    .qa-reply-iwrap textarea {
        padding: 7px;
        resize: none;
        height: 80px;
        line-height: 1.7em;
        width: 100%;
        box-sizing: border-box;
        font-size: 12px;
    }

    .qa-reply-ifoot {
        padding: 10px 0 10px 66px;
    }

    button.btn-mini {
        height: 29px;
        padding: 0 15px;
        font-size: 12px;
        width: auto;
    }

    .qa-comment-input {
        position: relative;
        padding: 20px 0 40px 40px;
    }

    .qa-ci-avator {
        position: absolute;
        top: 10px;
        left: 0;
        width: 120px;
        text-align: center;
        padding-top: 10px;
    }

    .qa-ci-avator img {
        width: 40px;
        border-radius: 50%;
    }

    .qa-ci-wrap {
        margin-left: 70px;
    }

    .qa-ci-footer {
        padding: 10px 0;
        position: relative;
    }

    .wenda-my {
        margin-bottom: 30px;
    }

    .wenda-my .user-info {
        position: relative;
        height: 40px;
        padding-left: 50px;
    }

    .wenda-my .user-info a.user-img {
        position: absolute;
        left: 0;
        top: 0;
        width: 40px;
        height: 40px;
        border-radius: 20px;
        overflow: hidden;
    }

    .wenda-my .user-info img {
        width: 40px;
        height: 40px;
    }

    .wenda-my .user-info .username {
        font-size: 12px;
        color: #1c1f21;
        padding-top: 10px;
    }

    .panel {
        padding: 0 20px;
        background-color: #fff;
        margin-bottom: 20px;
    }

    .panel-heading {
        border-bottom: solid 1px #d0d6d9;
    }

    .panel-title {
        height: 58px;
        line-height: 58px;
        font-size: 16px;
    }

    .about-ques .panel-body {
        padding: 15px 0 5px;
    }

    .about-ques .mkhotlist {
        padding: 9px 0 10px;
        border: 0 none;
        line-height: 21px;
    }

    .about-ques .mkhotlist a {
        color: #787d82;
    }

    .box_right{
        width:20%;
        float:right;
    }

    .w-e-text-container{
        height: 150px !important;/*!important是重点，因为原div是行内样式设置的高度300px*/
    }

</style>
<body onload="checkCookie();ifActive();">
<jsp:include page="navigation.jsp"/>
<div class="questions_main">
    <nav class="breadcrumb" style="display:block">
        <a target="_blank" class="breadcrumb-item" href="homepage.jsp">首页</a>
        <a target="_blank" class="breadcrumb-item" href=""></a>
        <a target="_blank" class="breadcrumb-item" href=""></a>
        <span class="breadcrumb-item active">问答</span>
    </nav>
    <div style="width:70%;float: left" class="box_left">
        <div class="box_l1">
            <div class="qa-header">
                <div class="qa-header-right r">
                    <em class="split l"></em>
                    <a href="javascript:void(0)"  class="l wenda-add-collection js-collection-btn">
                        <i class="icon-heart"></i>
                    </a>
                </div>
                <a id="asker" href="" target="_blank" class="qa-author">
                    <img  src="" width="40" height="40">
                    <span></span>
                </a>
            </div>
            <div class="qa-content">
                <div class="qa-content-inner aimgPreview">
                    <div class="js-content-main">
                        <h1 id="ask_describe" class="js-qa-wenda-title qa-wenda-title"> </h1>
                        <div id="ask_rich_text" class="qa-disscus rich-text">
                        </div>
                    </div>
                </div>
            </div>
            <div class="qa-content-addon clearfix">
                <a id="class_no" target="_blank" href="" class="qa-course-from"> </a>
                <span id="ask_time" class="qa-createtime r"></span>
                <span id="times" class="qa-view-num r"></span>
                <span id="answer_count" class="qa-total-comment r"></span>
            </div>
        </div>

    </div>

    <div  class="box_right">
        <%--<div class="wenda-my">
            <div class="user-info">
                <a class="user-img js-header-avator" href="">
                    <img src="//img4.mukewang.com/5bcedb920001efb312000848-100-100.jpg" alt="">
                </a>
                <p class="username js-header-nickname">xian_17</p>
            </div>
        </div>--%>
        <div class="panel about-ques">
            <div class="panel-heading">
                <h2 class="panel-title">本节其他问题</h2>
            </div>
            <div id="other_ask" class="panel-body clearfix">

            </div>
        </div>
    </div>


</div>
</body>
</html>
<script type="text/javascript">
    var edit_editor;
    $(document).ready(function(){
        get_reply();
    });
    var No = window.location.search.substr(1);
    var edit_configure = [
        'bold',
        'italic',
        'underline',
        'justify',  // 对齐方式
        'link',  // 插入链接
        'image',  // 插入图片
        'undo',  // 撤销
        'redo', // 重复
        'quote',
        'code'];
    var E = window.wangEditor;
    var answer_count = 0;

    function get_reply() {
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/postask",
            data: {
                action: 'get_reply',
                No: No
            },
            dataType: 'json',
            success: function (jsonObj) {
                $('#asker').children().eq(1).text(jsonObj.asker);
                $('#asker').children().eq(0).attr('src',"${pageContext.request.contextPath}"+jsonObj.asker_head);
                $('#ask_describe').text(jsonObj.ask_describe);
                $('#ask_rich_text').append(jsonObj.ask_text);
                $('#class_no').attr('href','Play.jsp?'+jsonObj.class_no+'/'+jsonObj.unit_no).text('问题来自：'+jsonObj.ask_title+''+jsonObj.unit_no);
                $('#ask_time').text(jsonObj.ask_time);
                $('#answer_count').text(jsonObj.answer.length+' 回答');
                $('#times').text(jsonObj.times+' 浏览');
                var class_type;
                switch (jsonObj.type) {
                    case 1:
                        class_type='前端设计';
                        break;
                    case 2:
                        class_type='后台设计';
                        break;
                    case 3:
                        class_type='基础理论';
                        break;
                    case 4:
                        class_type='嵌入式';
                        break;
                    case 5:
                        class_type='移动开发';
                        break;
                    case 6:
                        class_type='项目发布';
                        break;
                }
                $('.breadcrumb').children().eq(1).attr('href','course.jsp?type='+jsonObj.type).text(class_type);
                $('.breadcrumb').children().eq(2).attr('href','Learn_list.jsp?='+jsonObj.class_no).text(jsonObj.ask_title);
                for (var i=0;i<jsonObj.other_ask_no.length;i++){
                    $('#other_ask').append(
                        '<div  class="mkhotlist padtop">\n' +
                        '      <a href="questions.jsp?'+jsonObj.other_ask_no[i]+'" target="_blank">'+jsonObj.other_ask_title[i]+'</a>\n' +
                        '</div>'
                    )
                }
                for (answer_count=0;answer_count<jsonObj.answer.length;answer_count++){
                    $('.box_l1').append(
                        '<div class="qa-comments" >\n' +
                        '            <div class="qa-comment js-qa-comment">\n' +
                        '                <div class="qa-comment-wrap clearfix ">\n' +
                        '                    <div class="qa-comment-author">\n' +
                        '                        <a href="">\n' +
                        '                            <img src="'+"${pageContext.request.contextPath}"+jsonObj.answer_head[answer_count]+'" width="40"\n' +
                        '                                 height="40">\n' +
                        '                            <span class="qa-comment-nick">'+jsonObj.answer[answer_count]+'</span>\n' +
                        '                        </a>\n' +
                        '                    </div>\n' +
                        '                    <div class="qa-comment-d ">\n' +
                        '                        <div class="qa-comment-inner">\n' +
                        '                            <div class="qa-comment-c aimgPreview">\n' +
                        '                                <div class="rich-text">\n' +
                        '                                    <p>'+jsonObj.answer_text[answer_count]+'</p>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-comment-addon">\n' +
                        '                                <span class="qa-comment-time">'+jsonObj.answer_time[answer_count]+'</span>\n' +
                        '                                <span class="js-qa-tr-num" onclick="textarea_show('+answer_count+',this)">\n' +
                        '                                      <i class="fa fa-commenting-o" ></i>回复\n' +
                        '                                </span>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="qa-reply" id="qa-repky'+answer_count+'">\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>\n' +
                        '\n' +
                        '                        <div class="js-qa-reply-ibox qa-reply-ibox  clearfix" style="display:none">\n' +
                        '                            <div class="qa-reply-iavator l js-header-avator">\n' +
                        '                                <a href="">\n' +
                        '                                    <img src="'+"${pageContext.request.contextPath}"+head_image+'" alt="">\n' +
                        '                                </a>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-reply-iwrap">\n' +
                        '                                <textarea id="textarea'+answer_count+'" class="js-reply-ipt-default ipt" placeholder="写下你的评论..."></textarea>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-reply-ifoot clearfix">\n' +
                        '                                <div class="qa-reply-footright r">\n' +
                        '                                    <button type="button" class="btn btn-outline-secondary btn-mini" onclick="textarea_hide('+answer_count+')">取消</button>\n' +
                        '                                    <button type="button" class="btn btn-outline-success btn-mini" onclick="post_reply('+jsonObj.answer_no[answer_count]+',this,'+answer_count+')">提交</button>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </div>'

                    );
                    var ask_no = 'ask_no'+(answer_count);
                    for (var j=0;j<jsonObj[ask_no].reply_text.length;j++){
                        $('#qa-repky'+answer_count).append(
                            '<div class="qa-replies">\n' +
                            '                        <div class="qa-reply-c">\n' +
                            '                            <div class="qa-reply-item">\n' +
                            '                                <a class="qa-reply-item-author" href="">\n' +
                            '                                    <img src="'+"${pageContext.request.contextPath}"+jsonObj[ask_no].reply_head[j]+'" width="40"\n' +
                            '                                         height="40">\n' +
                            '                                </a>\n' +
                            '                                <div class="qa-reply-item-inner">\n' +
                            '                                    <p>\n' +
                            '                                        <a href="" class="qa-reply-nick">'+jsonObj[ask_no].reply[j]+'</a>\n' +
                            '                                        <span class="asker" style="display:none ;">(提问者)</span>\n' +
                            '                                    </p>\n' +
                            '                                    <p class="q-reply-item-c">\n' +
                            '                                       '+jsonObj[ask_no].reply_text[j]+'\n' +
                            '                                    </p>\n' +
                            '                                    <div class="qa-reply-item-foot clearfix">\n' +
                            '                                        <span>'+jsonObj[ask_no].reply_time[j]+'</span>\n' +
                            '                                        <span class="js-qa-tr-num" onclick="textarea_show_to('+answer_count+',this)">\n' +
                            '                                            <i class="fa fa-commenting-o"></i>回复\n' +
                            '                                        </span>\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </div>\n'
                        )
                    }
                }
                $('.box_left').append(
                    '<div class="qa-comment-input js-msg-context  clearfix">\n' +
                    '            <div class="qa-ci-avator l js-header-avator">\n' +
                    '                <a href="">\n' +
                    '                    <img width="40" height="40" src="'+"${pageContext.request.contextPath}"+head_image+'">\n' +
                    '                </a>\n' +
                    '            </div>\n' +
                    '            <div class="qa-ci-wrap">\n' +
                    '                <div class="js-reply-editor-box">\n' +
                    '                    <div id="edit" >\n' +
                    '\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div id="js-qa-ci-footer" class="qa-ci-footer clearfix">\n' +
                    '                    <div class="qa-ci-footright r">\n' +
                    '                        <button type="button" class="btn btn-outline-success" onclick="post_answer()" >回答</button>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </div>\n' +
                    '        </div>'
                );
                edit_editor = new E('#edit');
                edit_editor.customConfig.menus = edit_configure;
                edit_editor.create();
            }
        })
    }
    function textarea_show(i,event) {
        var textarea= $('#textarea'+i);
        textarea.attr('placeholder','写下你的评论...').parent().parent().css('display','block');
        textarea.data('reply_to',$(event).parent().parent().parent().prev().children().children().eq(1).text());
        alert(textarea.data('reply_to'));
    }
    function textarea_show_to(i,event) {
        var reply_to = $(event).parent().prev().prev().children().eq(0).text();
        var textarea= $('#textarea'+i);
        textarea.attr('placeholder','回复 '+reply_to).parent().parent().css('display','block');
        textarea.data('reply_to',reply_to);
    }
    function textarea_hide(i) {
        $('#textarea'+i).parent().parent().css('display','none');
    }
    function post_reply(ask_no,event,i) {
        var reply_edit = $(event).parent().parent().prev().children();
        var reply_text;
        if (reply_edit.attr('placeholder')==='写下你的评论...') reply_text=reply_edit.val();
        else reply_text=reply_edit.attr('placeholder')+' : '+reply_edit.val();
        var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/postask",
            data: {
                action: 'post_reply',
                No: ask_no,
                reply:user,
                reply_text:reply_text,
                time: time,
                reply_to:reply_edit.data('reply_to')
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (jsonObj.msg==='1'){
                    $('#qa-repky'+i).append(
                        '<div class="qa-replies">\n' +
                        '                        <div class="qa-reply-c">\n' +
                        '                            <div class="qa-reply-item">\n' +
                        '                                <a class="qa-reply-item-author" href="">\n' +
                        '                                    <img src="'+"${pageContext.request.contextPath}"+head_image+'" width="40"\n' +
                        '                                         height="40">\n' +
                        '                                </a>\n' +
                        '                                <div class="qa-reply-item-inner">\n' +
                        '                                    <p>\n' +
                        '                                        <a href="" class="qa-reply-nick">'+user+'</a>\n' +
                        '                                        <span class="asker" style="display:none ;">(提问者)</span>\n' +
                        '                                    </p>\n' +
                        '                                    <p class="q-reply-item-c">\n' +
                        '                                       '+reply_text+'\n' +
                        '                                    </p>\n' +
                        '                                    <div class="qa-reply-item-foot clearfix">\n' +
                        '                                        <span>'+time+'</span>\n' +
                        '                                        <span class="js-qa-tr-num" onclick="textarea_show_to('+i+',this)">\n' +
                        '                                            <i class="fa fa-commenting-o"></i>回复\n' +
                        '                                        </span>\n' +
                        '                                    </div>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>\n'
                    );
                    $('#textarea'+i).val('').parent().parent().css('display','none');
                }
            }
        })
    }

    function post_answer(){
        if(edit_editor.txt.text().replace(/&nbsp;/ig, "").trim().length<1){
            alert('请输入内容');
            return;
        }
        var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
        $.ajax({
            type: "POST",
            asynch: "false",
            url: "${pageContext.request.contextPath}/postask",
            data: {
                action: 'post_answer',
                answer:user,
                No:No,
                answer_text:edit_editor.txt.html(),
                time: time
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (jsonObj.msg==='1'){
                    answer_count++;
                    $('.box_l1').append(
                        '<div class="qa-comments" >\n' +
                        '            <div class="qa-comment js-qa-comment">\n' +
                        '                <div class="qa-comment-wrap clearfix ">\n' +
                        '                    <div class="qa-comment-author">\n' +
                        '                        <a href="">\n' +
                        '                            <img src="'+"${pageContext.request.contextPath}"+head_image+'" width="40"\n' +
                        '                                 height="40">\n' +
                        '                            <span class="qa-comment-nick">'+user+'</span>\n' +
                        '                        </a>\n' +
                        '                    </div>\n' +
                        '                    <div class="qa-comment-d ">\n' +
                        '                        <div class="qa-comment-inner">\n' +
                        '                            <div class="qa-comment-c aimgPreview">\n' +
                        '                                <div class="rich-text">\n' +
                        '                                    '+edit_editor.txt.html()+'\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-comment-addon">\n' +
                        '                                <span class="qa-comment-time">'+time+'</span>\n' +
                        '                                <span class="js-qa-tr-num" onclick="textarea_show('+answer_count+',this)">\n' +
                        '                                      <i class="fa fa-commenting-o" ></i>回复\n' +
                        '                                </span>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="qa-reply" id="qa-repky'+answer_count+'">\n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>\n' +
                        '\n' +
                        '                        <div class="js-qa-reply-ibox qa-reply-ibox  clearfix" style="display:none">\n' +
                        '                            <div class="qa-reply-iavator l js-header-avator">\n' +
                        '                                <a href="">\n' +
                        '                                    <img src="'+"${pageContext.request.contextPath}"+head_image+'" alt="">\n' +
                        '                                </a>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-reply-iwrap">\n' +
                        '                                <textarea id="textarea'+answer_count+'" class="js-reply-ipt-default ipt" placeholder="写下你的评论..."></textarea>\n' +
                        '                            </div>\n' +
                        '                            <div class="qa-reply-ifoot clearfix">\n' +
                        '                                <div class="qa-reply-footright r">\n' +
                        '                                    <button type="button" class="btn btn-outline-secondary btn-mini" onclick="textarea_hide('+answer_count+')">取消</button>\n' +
                        '                                    <button type="button" class="btn btn-outline-success btn-mini" onclick="post_reply('+jsonObj.answerID+',this,'+answer_count+')">提交</button>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </div>'
                    );
                    edit_editor.txt.text('<br>');
                }
            }
        })
    }

    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

</script>
