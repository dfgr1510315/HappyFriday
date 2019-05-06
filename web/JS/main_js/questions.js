var edit_editor;
var contextPath = getContextPath();
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html',function () {
        get_user_infor();
    });
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
        url: contextPath+"/postask",
        data: {
            action: 'get_reply',
            No: No
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            var asker = $('#asker');
            asker.children().eq(1).text(jsonObj.ask_infor.asker);
            asker.children().eq(0).attr('src',contextPath+jsonObj.ask_infor.head);
            $('#ask_describe').text(jsonObj.ask_infor.ask_title);
            $('#ask_rich_text').append(jsonObj.ask_infor.ask_text);
            $('#class_no').attr('href','Play.html?'+jsonObj.ask_infor.belong_class_id+'/'+jsonObj.ask_infor.unit_no).text('问题来自：'+jsonObj.ask_infor.class_title+'  '+jsonObj.ask_infor.unit_no);
            $('#ask_time').text(jsonObj.ask_infor.ask_time);
            $('#answer_count').text(jsonObj.ask_infor.answer_count+' 回答');
            $('#times').text(jsonObj.ask_infor.visits_count+' 浏览');
            var class_type;
            switch (jsonObj.ask_infor.class_type) {
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
            var breadcrumb = $('.breadcrumb');
            breadcrumb.children().eq(1).attr('href','course.html?type='+jsonObj.ask_infor.class_type).text(class_type);
            breadcrumb.children().eq(2).attr('href','Learn_list.html?class_id='+jsonObj.ask_infor.belong_class_id).text(jsonObj.ask_infor.class_title);
            for (var i=0;i<jsonObj.ask_other.length;i++){
                $('#other_ask').append(
                    '<div  class="mkhotlist padtop">\n' +
                    '      <a href="questions.html?'+jsonObj.ask_other[0][i]+'" target="_blank">'+jsonObj.ask_other[1][i]+'</a>\n' +
                    '</div>'
                )
            }
            for (answer_count=0;answer_count<jsonObj.ask_answer.length;answer_count++){
                $('.box_l1').append(
                    '<div class="qa-comments" >\n' +
                    '            <div class="qa-comment js-qa-comment">\n' +
                    '                <div class="qa-comment-wrap clearfix ">\n' +
                    '                    <div class="qa-comment-author">\n' +
                    '                        <a href="">\n' +
                    '                            <img src="'+contextPath+jsonObj.ask_answer[answer_count].head+'" width="40"\n' +
                    '                                 height="40" alt="">\n' +
                    '                            <span class="qa-comment-nick">'+jsonObj.ask_answer[answer_count].answerer+'</span>\n' +
                    '                        </a>\n' +
                    '                    </div>\n' +
                    '                    <div class="qa-comment-d ">\n' +
                    '                        <div class="qa-comment-inner">\n' +
                    '                            <div class="qa-comment-c aimgPreview">\n' +
                    '                                <div class="rich-text">\n' +
                    '                                    <p>'+jsonObj.ask_answer[answer_count].answer_text+'</p>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                            <div class="qa-comment-addon">\n' +
                    '                                <span class="qa-comment-time">'+jsonObj.ask_answer[answer_count].answer_time+'</span>\n' +
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
                    '                                    <img src="'+contextPath+head_image+'" alt="">\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div class="qa-reply-iwrap">\n' +
                    '                                <textarea id="textarea'+answer_count+'" class="js-reply-ipt-default ipt" placeholder="写下你的评论..."></textarea>\n' +
                    '                            </div>\n' +
                    '                            <div class="qa-reply-ifoot clearfix">\n' +
                    '                                <div class="qa-reply-footright r">\n' +
                    '                                    <button type="button" class="btn btn-outline-secondary btn-mini" onclick="textarea_hide('+answer_count+')">取消</button>\n' +
                    '                                    <button type="button" class="btn btn-outline-success btn-mini" onclick="post_reply('+jsonObj.ask_answer[answer_count].answer_id+',this,'+answer_count+')">提交</button>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </div>'

                );
                var answer_no = jsonObj.ask_answer[answer_count];
                for (var j=0;j<answer_no.replys.length;j++){
                    $('#qa-repky'+answer_count).append(
                        '<div class="qa-replies">\n' +
                        '                        <div class="qa-reply-c">\n' +
                        '                            <div class="qa-reply-item">\n' +
                        '                                <a class="qa-reply-item-author" href="">\n' +
                        '                                    <img src="'+contextPath+answer_no.replys[j].head+'" width="40"\n' +
                        '                                         height="40" alt="">\n' +
                        '                                </a>\n' +
                        '                                <div class="qa-reply-item-inner">\n' +
                        '                                    <p>\n' +
                        '                                        <a href="" class="qa-reply-nick">'+answer_no.replys[j].replyer+'</a>\n' +
                        '                                        <span class="asker" style="display:none ;">(提问者)</span>\n' +
                        '                                    </p>\n' +
                        '                                    <p class="q-reply-item-c">\n' +
                        '                                       '+answer_no.replys[j].text+'\n' +
                        '                                    </p>\n' +
                        '                                    <div class="qa-reply-item-foot clearfix">\n' +
                        '                                        <span>'+answer_no.replys[j].reply_time+'</span>\n' +
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
                '                    <img width="40" height="40" src="'+contextPath+head_image+'" alt="">\n' +
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
        url: contextPath+"/postask",
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
            if (jsonObj!==0){
                $('#qa-repky'+i).append(
                    '<div class="qa-replies">\n' +
                    '                        <div class="qa-reply-c">\n' +
                    '                            <div class="qa-reply-item">\n' +
                    '                                <a class="qa-reply-item-author" href="">\n' +
                    '                                    <img src="'+contextPath+head_image+'" width="40"\n' +
                    '                                         height="40" alt="">\n' +
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
        url: contextPath+"/postask",
        data: {
            action: 'post_answer',
            answer:user,
            No:No,
            answer_text:edit_editor.txt.html(),
            time: time
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            if (jsonObj!==0){
                answer_count++;
                $('.box_l1').append(
                    '<div class="qa-comments" >\n' +
                    '            <div class="qa-comment js-qa-comment">\n' +
                    '                <div class="qa-comment-wrap clearfix ">\n' +
                    '                    <div class="qa-comment-author">\n' +
                    '                        <a href="">\n' +
                    '                            <img src="'+contextPath+head_image+'" width="40"\n' +
                    '                                 height="40" alt="">\n' +
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
                    '                                    <img src="'+contextPath+head_image+'" alt="">\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div class="qa-reply-iwrap">\n' +
                    '                                <textarea id="textarea'+answer_count+'" class="js-reply-ipt-default ipt" placeholder="写下你的评论..."></textarea>\n' +
                    '                            </div>\n' +
                    '                            <div class="qa-reply-ifoot clearfix">\n' +
                    '                                <div class="qa-reply-footright r">\n' +
                    '                                    <button type="button" class="btn btn-outline-secondary btn-mini" onclick="textarea_hide('+answer_count+')">取消</button>\n' +
                    '                                    <button type="button" class="btn btn-outline-success btn-mini" onclick="post_reply('+jsonObj+',this,'+answer_count+')">提交</button>\n' +
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