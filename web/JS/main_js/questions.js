let edit_editor;
let user = cookie.get('user');
let nike = cookie.get('nike');
let Landlord;
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html');
    get_reply();
});
let No = window.location.search.substr(1);
let edit_configure = [
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
let E = window.wangEditor;
let answer_count = 0;

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
            console.log(jsonObj);
            let asker = $('#asker');
            Landlord = jsonObj.ask_infor.username;
            asker.children().eq(1).text(jsonObj.ask_infor.nike);
            asker.children().eq(0).attr('src',contextPath+jsonObj.ask_infor.head);
            $('#ask_describe').text(jsonObj.ask_infor.ask_title);
            $('#ask_rich_text').append(jsonObj.ask_infor.ask_text);
            $('#class_no').attr('href','Play.html?'+jsonObj.ask_infor.belong_class_id+'/'+jsonObj.ask_infor.unit_no).text('问题来自：'+jsonObj.ask_infor.class_title+'  '+jsonObj.ask_infor.unit_no);
            $('#ask_time').text(jsonObj.ask_infor.ask_time);
            $('#answer_count').text(jsonObj.ask_infor.answer_count+' 回答');
            $('#times').text(jsonObj.ask_infor.visits_count+' 浏览');
            let class_type;
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
            let breadcrumb = $('.breadcrumb');
            breadcrumb.children().eq(1).attr('href','course.html?type='+jsonObj.ask_infor.class_type).text(class_type);
            breadcrumb.children().eq(2).attr('href','Learn_list.html?class_id='+jsonObj.ask_infor.belong_class_id).text(jsonObj.ask_infor.class_title);
            for (let i=0;i<jsonObj.ask_other.length;i++){
                $('#other_ask').append(
                    '<div  class="mkhotlist padtop">\n' +
                    '      <a href="questions.html?'+jsonObj.ask_other[i].ask_id+'" target="_blank">'+jsonObj.ask_other[i].ask_title+'</a>\n' +
                    '</div>'
                )
            }
            for (answer_count=0;answer_count<jsonObj.ask_answer.length;answer_count++){
                addBox_l1(jsonObj.ask_answer[answer_count].head,jsonObj.ask_answer[answer_count].nike,jsonObj.ask_answer[answer_count].answer_text,jsonObj.ask_answer[answer_count].answer_time,answer_count,jsonObj.ask_answer[answer_count].answer_id
                ,jsonObj.ask_answer[answer_count].username);
                let answer_no = jsonObj.ask_answer[answer_count];
                for (let j=0;j<answer_no.replys.length;j++){
                    addQa_repky(answer_count,answer_no.replys[j].head,answer_no.replys[j].nike,answer_no.replys[j].text,answer_no.replys[j].reply_time,answer_no.replys[j].username);
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
    let textarea= $('#textarea'+i);
    textarea.attr('placeholder','写下你的评论...').parent().parent().css('display','block');
    textarea.data('reply_to',$(event).parent().parent().parent().prev().children().children().eq(1).data('user'));
}
function textarea_show_to(i,event) {
    let reply_to = $(event).parent().prev().prev().children().eq(0).data('user');
    let reply_to_nike = $(event).parent().prev().prev().children().eq(0).text();
    let textarea= $('#textarea'+i);
    textarea.attr('placeholder','回复 '+reply_to_nike).parent().parent().css('display','block');
    textarea.data('reply_to',reply_to);
}
function textarea_hide(i) {
    $('#textarea'+i).parent().parent().css('display','none');
}
function post_reply(ask_no,event,i) {
    let reply_edit = $(event).parent().parent().prev().children();
    let reply_text;
    if (reply_edit.attr('placeholder')==='写下你的评论...') reply_text=reply_edit.val();
    else reply_text=reply_edit.attr('placeholder')+' : '+reply_edit.val();
    let time = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/postask",
        data: {
            action: 'post_reply',
            No: ask_no,
            reply:user,
            reply_text:reply_text,
            askId:No,
            time: time,
            reply_to:reply_edit.data('reply_to')
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (jsonObj!==0){
                addQa_repky(i,head_image,nike,reply_text,time,user);
                $('#textarea'+i).val('').parent().parent().css('display','none');
            }else alert('回复失败')
        }
    })
}

function post_answer(){
    if(edit_editor.txt.text().replace(/&nbsp;/ig, "").trim().length<1){
        alert('请输入内容');
        return;
    }
    let time = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/postask",
        data: {
            action: 'post_answer',
            answer:user,
            No:No,
            answer_text:edit_editor.txt.html(),
            landlord:Landlord,
            time: time
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            if (jsonObj!==0){
                answer_count++;
                addBox_l1(head_image,nike,edit_editor.txt.html(),time,answer_count,jsonObj,user);
                edit_editor.txt.text('<br>');
            }else alert('回复失败')
        }
    })
}

function addQa_repky(i,head_image,form,reply_text,time,username){
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
        '                                        <a href="" class="qa-reply-nick" data-user="'+username+'">'+form+'</a>\n' +
        '                                        <span class="asker" style="display:none;">(提问者)</span>\n' +
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
}

function addBox_l1(head_image,form,editor,time,answer_count,askId,username){
    $('.box_l1').append(
        '<div class="qa-comments" >\n' +
        '            <div class="qa-comment js-qa-comment">\n' +
        '                <div class="qa-comment-wrap clearfix ">\n' +
        '                    <div class="qa-comment-author">\n' +
        '                        <a href="">\n' +
        '                            <img src="'+contextPath+head_image+'" width="40" height="40">\n' +
        '                            <span data-user="'+username+'" class="qa-comment-nick">'+form+'</span>\n' +
        '                        </a>\n' +
        '                    </div>\n' +
        '                    <div class="qa-comment-d ">\n' +
        '                        <div class="qa-comment-inner">\n' +
        '                            <div class="qa-comment-c aimgPreview">\n' +
        '                                <div class="rich-text">\n' +
        '                                    '+editor+'\n' +
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
        '                                    <button type="button" class="btn btn-outline-success btn-mini" onclick="post_reply('+askId+',this,'+answer_count+')">提交</button>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>'
    );
}

Date.prototype.Format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};