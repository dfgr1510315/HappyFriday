var contextPath = getContextPath();
var note_flag = 0;
var ask_flag = 0;
var post_note_flag = 0;
var post_ask_flag = 0;
var No = window.location.search.replace("?", '').split("/");
var Unit_length;
var Unit = [];
var class_no = No[1];
No = No[0];
var editor_count = 0;
var E = window.wangEditor;
var configure = [
    'head',
    'bold',
    'italic',
    'underline',
    'foreColor',  // 文字颜色
    'fontSize',  // 字号
    'link',
    'quote',
    'list',  // 列表
    'image',
    'justify',  // 对齐方式
    'table',  // 表格
    'code'
];
var edit_configure = [
    'head',
    'bold',
    'italic',
    'underline',
    'justify',  // 对齐方式
    'foreColor',  // 文字颜色
    'fontSize',  // 字号
    'quote',
    'code'];

$(document).ready(function () {
    $('#navigation').load('navigation.html', function () {
        get_user_infor();
    });
    get_Class();
    get_Video()
});

function get_Class() {
    var note_editor = new E('#note_editor');
    var ask_editor = new E('#ask_editor');

    note_editor.customConfig.menus = configure;
    ask_editor.customConfig.menus = configure;

    note_editor.create();
    ask_editor.create();

    $('#back').attr('href', 'Learn_list.html?class_id=' + No);
    $.ajax({
        type: "POST",
        url: contextPath + "/learn_list",
        data: {
            action: 'get_play_class',
            No: No
        },
        dataType: 'json',
        success: function (jsonObject) {
            //console.log(jsonObject);
            $("title").text(jsonObject.title);
            Unit_length = jsonObject.chapter.length;
            var unit_titles = [];
            for (i = 0; i < jsonObject.chapter.length; i++) {
                if (-1 === unit_contain(unit_titles, jsonObject.chapter[i].unit_title)) unit_titles.push(jsonObject.chapter[i].unit_title);
            }
            for (var i = 0; i < unit_titles.length; i++) {
                $("#chapter").append(
                    '<ul id="Unit' + i + '" class="" draggable="true"  >' +
                    '<li class="sec-title">' +
                    '   <span  style="margin-right:20px;color:#999;font-weight:bold;">第' + (i + 1) + '章 ' + '</span>' +
                    '   <span >' + unit_titles[i] + '</span>' +
                    '</li>' +
                    '</ul>'
                );
            }
            for (i = 0; i < jsonObject.chapter.length; i++) {
                Unit.push(jsonObject.chapter[i].unit_no);
                var belong_unit = unit_contain(unit_titles, jsonObject.chapter[i].unit_title);
                $("#Unit" + belong_unit).append(
                    '<li class="sec-li">' +
                    '    <a href=' + window.location.search.replace(class_no, '') + jsonObject.chapter[i].unit_no + '>' +
                    '       <div class="ui-box1">' +
                    '               <i class="fa fa-play-circle-o"></i>' +
                    '               <span style="margin-right:20px;">课时' + jsonObject.chapter[i].unit_no.charAt(jsonObject.chapter[i].unit_no.length - 1) + '</span>' +
                    '               <span >' + jsonObject.chapter[i].lesson_title + '</span>' +
                    '       </div>' +
                    '    </a>' +
                    ' </li>'
                );
            }
            $('#Unit' + (class_no.substring(0, class_no.indexOf('-')) - 1)).children().eq((class_no.substring(class_no.indexOf('-') + 1))).find('.ui-box1').parent().addClass('playing').append(
                '<i class="fa fa-adjust" style="position: relative;left:370px;top: 5px" id="adjust"></i>'
            )
            //alert(Serial_No+"\n"+Unit_Name+"\n"+Class_Name+"\n"+Video_Src+"\n"+Editor+"\n"+File_Href+"\n"+State);
        }
    });
}

function unit_contain(unit_titles, unit_title) {
    for (var i = 0; i < unit_titles.length; i++) {
        if (unit_titles[i] === unit_title) return i
    }
    return -1
}

function get_ask() {
    if (ask_flag === 1) return;
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postask",
        data: {
            action: 'get',
            No: No,
            class_No: class_no,
            author: user
        },
        dataType: 'json',
        success: function (jsonObject) {
            console.log(jsonObject);
            for (var i = 0; i < jsonObject.ask.length; i++) {
                add_ask_box(jsonObject.ask[i].ask_id, jsonObject.ask[i].belong_class_id, jsonObject.ask[i].ask_title, jsonObject.ask[i].ask_time, jsonObject.ask[i].answer_count, jsonObject.ask[i].visits_count, jsonObject.ask[i].new_answerer, jsonObject.ask[i].new_answer);
            }
        }
    });
    ask_flag = 1;
}

function get_note() {
    if (note_flag === 1) return;
    $.ajax({
        type: "POST",
        url: contextPath + "/postnote",
        data: {
            action: 'get',
            No: No,
            class_No: class_no,
            author: user
        },
        dataType: 'json',
        success: function (jsonObject) {
            console.log(jsonObject);
            for (var i = 0; i < jsonObject.note.length; i++) {
                $('#note_box').prepend(' <div class="ui-box">\n' +
                    '                            <div style="float:left;">\n' +
                    '                                <a href="PersonalCenter.html">\n' +
                    '                                    <image src="' + contextPath + head_image + '" style="width: 40px;height: 40px;border-radius: 20px;"></image>\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div style="margin-left: 60px">\n' +
                    '                                <div class="author">\n' +
                    '                                    <a  href="PersonalCenter.html" >' + user + '</a>\n' +
                    '                                </div>\n' +
                    '                                <div style="display: block;">\n' +
                    '                                    <div style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;max-height: 63%;min-height=20%">\n' +
                    '                                        ' + jsonObject.note[i].text + '\n' +
                    '                                    </div>\n' +
                    '                                    <div class="post" style=" margin-left: 12%;">\n' +
                    '                                        <a class="post_action" href="javascript:void(0);" onclick="edit_note(this)"><i class="fa fa-eyedropper"></i>编辑</a>\n' +
                    '                                        <a id="delete_note' + jsonObject.note[i].note_id + '" class="post_action" data-toggle="modal"  data-target="#Delete_Note" data-noteno="' + jsonObject.note[i].note_id + '"><i class="fa fa-window-close-o" ></i>删除</a>\n' +
                    '                                        <a class="post_action">查看全文</a>\n' +
                    '                                        <span >' + jsonObject.note[i].note_time + '</span>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                                <div style="display: none;">\n' +
                    '                                    <div id="edit_editor' + i + '" style="height: 322px;margin-bottom: 20px"></div>\n' +
                    '                                    <div style="height: 30px;">\n' +
                    '                                        <button type="button" class="btn btn-outline-success btn-mini" style="float:right;" onclick="change_note(this)" data-noteno="' + jsonObject.note[i].note_id + '">提交</button>\n' +
                    '                                        <button type="button" class="btn btn-outline-secondary btn-mini" style="float:right;margin-right: 10px;" onclick="out_edit_note(this)">取消</button>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>');
                var edit_editor = new E('#edit_editor' + i);
                edit_editor.customConfig.menus = edit_configure;
                edit_editor.create();
                edit_editor.txt.html(jsonObject.note[i].text);
                editor_count++;
            }
        }
    });
    note_flag = 1
}

var player;
var videoID;
var cookieTime;

function live() {
    var title = $('#adjust').prev().children().eq(2).text();
    cookie.set('class_no_' + user + No, class_no + ',' + title);
    var done_times = 0;
    for (var a = 0; a < Unit_length; a++) {
        var ifDone = cookie.get('time_' + user + No + Unit[a]);
        if (ifDone === 'done') done_times++;
    }
    var percentage = parseInt(done_times / Unit_length * 100);
    $.ajax({
        type: "POST",
        url: contextPath + "/students",
        data: {
            action: 'save_schedule',
            No: No,
            user: user,
            schedule: percentage,
            last_time: class_no + ':' + title
        },
        dataType: 'json'
    });
    var class_id = cookie.get('class_id').split(',');
    var last_time = cookie.get('last_time').split(',');
    var schedule = cookie.get('schedule').split(',');
    for (var i = 0; i < class_id.length; i++) {
        if (class_id[i] === No) {
            last_time[i] = class_no + ':' + title;
            schedule[i] = percentage + '';
            cookie.set('last_time', last_time);
            cookie.set('schedule', schedule);
            return;
        }
    }
    cookie.set('class_id', cookie.get('class_id') + ',' + No);
    cookie.set('class_title', cookie.get('class_title') + ',' + $("title").text());
    cookie.set('last_time', cookie.get('last_time') + ',' + class_no + ':' + title);
    cookie.set('schedule', cookie.get('schedule') + ',' + percentage);
}

/*  function loadHandler() {
      player.addListener('time', timeHandler); //监听播放时间
      player.addListener('ended', endedHandler);
  }

  function timeHandler(t) {
      cookie.set('time_' + videoID, t); //当前视频播放时间写入cookie
  }

  function endedHandler(e) {
      alert('!!');
      player.videoPause();
  }*/


function get_Video() {
    $.ajax({
        type: "POST",
        url: contextPath + "/play",
        data: {
            No: No,
            class_No: class_no
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            if (jsonObj.material.permit === false) {
                $('#video').empty().append(
                    '<div class="no_find_video">请先加入本课程学习</div>'
                );
                note_flag = 1;
                ask_flag = 1;
                post_note_flag = post_ask_flag = 1;
                $('#video_li').hide();
                $('#text_li').hide();
                $('#file_li').hide();
                return;
            }
            if (jsonObj.material.video_address === '' && jsonObj.material.image_text === '<p><br></p>' && jsonObj.material.file_address === '') {
                $('#video').empty().append(
                    '<div class="no_find_video">本课时无内容或尚未发布</div>'
                );
                note_flag = 1;
                ask_flag = 1;
                post_note_flag = post_ask_flag = 1;
                $('#video_li').hide();
                $('#text_li').hide();
                $('#file_li').hide();
                return;
            }
            if (jsonObj.material.video_address !== '') {
                videoID = user + No + class_no; //视频的区分ID，每个视频分配一个唯一的ID
                cookieTime = cookie.get('time_' + videoID); //调用已记录的time
                if (!cookieTime || cookieTime === 'done') { //如果没有记录值，则设置时间0开始播放
                    cookieTime = 0;
                }
                /* if(cookieTime > 0) {
                     alert('本视频记录的上次观看时间(秒)为：' + cookieTime);
                 }*/

                $('#video1').children().attr('src', jsonObj.material.video_address);

                player = videojs('video1', {});

                player.on('ended', function () {
                    cookie.set('time_' + videoID, 'done');
                });

                player.on('timeupdate', function () {
                    cookie.set('time_' + videoID, player.currentTime()); //当前视频播放时间写入cookie
                });

                player.on('loadedmetadata', function () {
                    if (cookieTime > 0) { //如果记录时间大于0，则设置视频播放后跳转至上次记录时间
                        player.currentTime(cookieTime)
                    }
                });
            } else {
                $('#video').empty().append(
                    '<div class="no_find_video">本课时无视频教案</div>'
                );
                $('#video_li').hide();

            }
            if (jsonObj.material.image_text === '<p><br></p>') {
                $('#text_li').hide()
            } else {
                $('#text').append(
                    jsonObj.material.image_text
                );
            }

            if (jsonObj.material.file_address === '') {
                $('#file_li').hide();
            } else {
                var file_address = jsonObj.material.file_address.split('|');
                var file_name = jsonObj.material.file_name.split('|');
                for (var i = 0; i < file_address.length - 1; i++) {
                    $('#file_group').append(
                        '<a target="_blank" href="' + file_address[i] + '" class="list-group-item list-group-item-action">' + file_name[i] + '</a>'
                    )
                }
            }
        }
    });
}

function add_width() {
    $("#center_box").css('width', '59%');
    $("#right_box").css('width', '36%');
}

function sub_width() {
    $("#center_box").css('width', '72%');
    $("#right_box").css('width', '23%');
}

$(function () {
    $('#Delete_Note').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('noteno'); //获取呼出模态框的按钮ID
        var obj = document.getElementById("Delete_Note_sure_button");
        obj.setAttribute("onclick", "delete_note(" + button_id + ")");
    })
});

function change_note(event) {
    var new_note = $(event).parent().prev().children().eq(1).children().html().replace(/(^\s*)|(\s*$)/g, "").replace(/\u200B/g, '');
    var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postnote",
        data: {
            action: 'change',
            note_no: $(event).data('noteno'),
            note_editor: new_note,
            time: time
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (true === jsonObj) {
                $(event).parent().parent().prev().children().eq(0).html(new_note);
                $(event).next().click();
            }
        }
    });
}

function post_note() {
    if (post_note_flag === 1) return;
    var note_editor = $('#note_editor');
    var note_text = note_editor.children().eq(1).children().html().replace(/(^\s*)|(\s*$)/g, "").replace(/\u200B/g, '');
    if (note_editor.text().trim().length === 0) {
        alert('内容不能为空');
    } else {
        var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
        $.ajax({
            type: "POST",
            asynch: "false",
            url: contextPath + "/postnote",
            data: {
                action: 'post',
                No: No,
                class_No: class_no,
                author: user,
                note_editor: note_text,
                time: time2
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (0 !== jsonObj) {
                    var note_editor = $('#note_editor');
                    editor_count++;
                    $('#note_box').prepend(' <div class="ui-box">\n' +
                        '                            <div style="float:left;">\n' +
                        '                                <a href="PersonalCenter.html">\n' +
                        '                                    <img src="' + contextPath + head_image + '" style="width: 40px;height: 40px;border-radius: 20px;">\n' +
                        '                                </a>\n' +
                        '                            </div>\n' +
                        '                            <div style="margin-left: 60px">\n' +
                        '                                <div class="author">\n' +
                        '                                    <a  href="PersonalCenter.html" >' + user + '</a>\n' +
                        '                                </div>\n' +
                        '                                <div style="display: block;">\n' +
                        '                                    <div style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;max-height: 63%;min-height=20%">\n' +
                        '                                        ' + note_editor.children().eq(1).children().html() + '\n' +
                        '                                    </div>\n' +
                        '                                    <div class="post" style=" margin-left: 12%;">\n' +
                        '                                        <a class="post_action" href="javascript:void(0);" onclick="edit_note(this)"><i class="fa fa-eyedropper"></i>编辑</a>\n' +
                        '                                        <a id="delete_note' + jsonObj + '" class="post_action" data-toggle="modal"  data-target="#Delete_Note" data-noteno="' + jsonObj + '"><i class="fa fa-window-close-o"></i>删除</a>\n' +
                        '                                        <a class="post_action">查看全文</a>\n' +
                        '                                        <span >' + time2 + '</span>\n' +
                        '                                    </div>\n' +
                        '                                </div>\n' +
                        '                                <div style="display: none;">\n' +
                        '                                    <div id="edit_editor' + editor_count + '" style="height: 60%;margin-bottom: 14px"></div>\n' +
                        '                                    <div style="height: 30px;">\n' +
                        '                                        <button type="button" class="btn btn-outline-success btn-mini" style="float:right;" onclick="change_note(this)" data-noteno="' + jsonObj + '">提交</button>\n' +
                        '                                        <button type="button" class="btn btn-outline-secondary btn-mini" style="float:right;margin-right: 10px;" onclick="out_edit_note(this)">取消</button>\n' +
                        '                                    </div>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>');
                    var edit_editor = new E('#edit_editor' + editor_count);
                    edit_editor.customConfig.menus = edit_configure;
                    edit_editor.create();
                    edit_editor.txt.html(note_editor.children().eq(1).children().html());
                    note_editor.children().eq(1).children().html('<br>');
                }
            }
        });
    }
}

function edit_note(event) {
    $(event).parent().parent().css('display', 'none');
    $(event).parent().parent().next().css('display', 'block');
}

function out_edit_note(event) {
    $(event).parent().parent().css('display', 'none');
    $(event).parent().parent().prev().css('display', 'block');
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

function delete_note(noteno) {
    //alert($(event).parent().prev().html().replace(/(^\s*)|(\s*$)/g, ""));
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postnote",
        data: {
            action: 'delete',
            note_no: noteno
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (true === jsonObj) {
                $('#delete_note' + noteno).parent().parent().parent().parent().remove();
                $("#Delete_Note_Close").click();
            }
        }
    });
}

function post_ask() {
    if (post_ask_flag === 1) return;
    var ask_editor = $('#ask_editor');
    var ask_title = $('#problem').val();
    var ask_text = ask_editor.children().eq(1).children().html().replace(/(^\s*)|(\s*$)/g, "").replace(/\u200B/g, '');
    if (ask_editor.text().trim().length === 0 || ask_title.trim().length === 0) {
        alert('内容不能为空');
    } else {
        var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
        $.ajax({
            type: "POST",
            asynch: "false",
            url: contextPath + "/postask",
            data: {
                action: 'post',
                No: No,
                class_No: class_no,
                author: user,
                note_editor: ask_text,
                time: time2,
                ask_title: ask_title
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (jsonObj !== 0) {
                    add_ask_box(jsonObj, ask_text, ask_title, time2, 0, 0, '', '');
                    ask_editor.children().eq(1).children().html('<p><br></p>');
                    $('#problem').val('');
                } else alert('提交失败')
            }
        });
    }
}


function add_ask_box(ask_id, ask_text, ask_title, time2, answer, browse, new_answerer, new_answer) {
    $('#ask_box').prepend(' <div class="ui-box">\n' +
        '                            <div style="float:left;">\n' +
        '                                <a target="_blank" href="questions.html?' + ask_id + '">\n' +
        '                                    <img src="' + contextPath + head_image + '" style="width: 40px;height: 40px;border-radius: 20px;">\n' +
        '                                </a>\n' +
        '                            </div>\n' +
        '                            <div style="margin-left: 60px">\n' +
        '                                <div class="author">\n' +
        '                                    <a target="_blank" href="questions.html?' + ask_id + '" >' + ask_title + '</a>\n' +
        '                                </div>\n' +
        '                                <div style="display: block;margin-top: 8px;">\n' +
        '                                    <div style="overflow: hidden">\n' +
        '                                        <div class="l replydes" id="new_reply' + ask_id + '">\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                    <div class="post" style=" margin-left: 30%;">\n' +
        '                                        <a class="post_action" href="javascript:void(0);"><span>' + answer + '回答</span><span>' + browse + '浏览</span></a>\n' +
        '                                        <span >' + time2 + '</span>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>');
    if (new_answerer === '') {
        $('#new_reply' + ask_id).append(
            '<button type="button" class="btn btn-light" onclick="window.open(\'questions.html?' + ask_id + '\')">我答我自己</button>\n'
        )
    } else {
        $('#new_reply' + ask_id).append(
            '<span class="replysign">\n' +
            ' 最新回复 /\n' +
            '   <a class="nickname" target="_blank" href="questions.html?' + ask_id + '">' + new_answerer + '</a>\n' +
            ' </span>\n' +
            ' <div class="replydet">' + new_answer + '</div>\n'
        )
    }
}