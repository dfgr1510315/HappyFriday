let E = window.wangEditor;
let edit_configure = [
    'head',
    'bold',
    'italic',
    'underline',
    'justify',  // 对齐方式
    'foreColor',  // 文字颜色
    'fontSize',  // 字号
    'quote',
    'code'];
let user = cookie.get('user');
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(8);});
    get_note();
});

function get_note() {
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/postnote",
        data: {
            action:'get_my_all_note',
            author:user
        },
        dataType: 'json',
        success: function (jsonObject){
            //console.log(jsonObject);
            let note_box = $('#note_box');
            for (let i=0;i< jsonObject.note.length;i++){
                note_box.prepend('<div class="post-row">\n' +
                    '                            <div style="float:left;">\n' +
                    '                                <a target="_blank" href="Learn_list.html?class_id='+jsonObject.note[i].belong_class_id+'">\n' +
                    '                                    <img src="'+contextPath+jsonObject.note[i].cover_address+'" style="width:40px;height:40px;" alt="">\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div style="margin-left:60px">\n' +
                    '                                <div class="title">\n' +
                    '                                    <a target="_blank" href="Learn_list.html?class_id='+jsonObject.note[i].belong_class_id+'" >'+jsonObject.note[i].class_title+'</a>\n' +
                    '                                </div>\n' +
                    '                                <div class="unit">\n' +
                    '                                    <a target="_blank" href="Play.html?'+jsonObject.note[i].belong_class_id+'/'+jsonObject.note[i].unit_no+'" >'+jsonObject.note[i].unit_no+'&nbsp&nbsp&nbsp'+jsonObject.note[i].lesson_title+'</a>\n' +
                    '                                </div>\n' +
                    '                                <div style="display:block;">\n' +
                    '                                    <div class="note_text">\n' +
                    '                                        '+jsonObject.note[i].text+'\n' +
                    '                                    </div>\n' +
                    '                                    <div class="post" style="margin-left:445px;">\n' +
                    '                                        <a class="post_action" href="javascript:void(0);" onclick="edit_note(this)"><i class="fa fa-eyedropper"></i>编辑</a>\n' +
                    '                                        <a id="delete_note'+jsonObject.note[i].note_id+'" class="post_action" data-toggle="modal"  data-target="#Delete_Note" data-noteno="'+jsonObject.note[i].note_id+'"><i class="fa fa-window-close-o"></i>删除</a>\n' +
                    '                                        <span >'+jsonObject.note[i].note_time+'</span>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                                <div style="display:none;">\n' +
                    '                                    <div id="edit_editor'+i+'" style="margin-bottom:14px"></div>\n' +
                    '                                    <div style="height:30px;">\n' +
                    '                                        <button type="button" class="btn btn-outline-success btn-mini" style="float:right;" onclick="change_note(this)" data-noteno="'+jsonObject.note[i].note_id+'">提交</button>\n' +
                    '                                        <button type="button" class="btn btn-outline-secondary btn-mini" style="float:right;margin-right: 10px;" onclick="out_edit_note(this)">取消</button>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>');
                let edit_editor = new E('#edit_editor'+i);
                edit_editor.customConfig.menus = edit_configure;
                edit_editor.create();
                edit_editor.txt.html(jsonObject.note[i].text);
            }
            if (note_box.html().length === 0) {
                note_box.append(
                    '<div class="no_find_class">暂无笔记</div> '
                )
            }
        }
    });
}

function change_note(event) {
    let new_note = $(event).parent().prev().children().eq(1).children().html().replace(/(^\s*)|(\s*$)/g, "").replace(/\u200B/g,'');
    let time = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/postnote",
        data: {
            action:'change',
            note_no:$(event).data('noteno'),
            note_editor:new_note,
            time:time
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (true===jsonObj){
                $(event).parent().parent().prev().children().eq(0).html(new_note);
                $(event).next().click();
            }
        }
    });
}

$(function () {
    $('#Delete_Note').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let button_id = button.data('noteno'); //获取呼出模态框的按钮ID
        let obj = document.getElementById("Delete_Note_sure_button");
        obj.setAttribute("onclick", "delete_note(" + button_id + ")");
    })
});

function delete_note(noteno) {
    //alert($(event).parent().prev().html().replace(/(^\s*)|(\s*$)/g, ""));
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/postnote",
        data: {
            action:'delete',
            note_no:noteno
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (true===jsonObj){
                $('#delete_note'+noteno).parent().parent().parent().parent().remove();
                $("#Delete_Note_Close").click();
            }
        }
    });
}

function edit_note(event) {
    $(event).parent().parent().css('display','none');
    $(event).parent().parent().next().css('display','block');
}
function out_edit_note(event) {
    $(event).parent().parent().css('display','none');
    $(event).parent().parent().prev().css('display','block');
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