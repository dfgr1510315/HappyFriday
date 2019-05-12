var contextPath = getContextPath();
let No = GetQueryString('class_id');

let pass = -1;
let ask_flag = 0;
let note_flag = 0;
let get_class_flag = 0;
let ask_count = -1;
let note_count = -1;
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html', function () {
        get_user_infor();
    });
    $('#loginPC').load('LoginPC.html');
    get_Class();
    if (GetQueryString('page_ask') != null) get_Ask();
    else if (GetQueryString('page_note') != null) get_Note();
});

function no_count(ask_count, note_count) {
    if (ask_count === 0) {
        ask_flag = 1;
        $('#two').append(
            '<div class="no_find_class">暂无问答</div>'
        )
    }
    if (note_count === 0) {
        note_flag = 1;
        $('#three').append(
            '<div class="no_find_class">暂无笔记</div>'
        )
    }
}

function type_text(class_type) {
    let class_text;
    switch (class_type) {
        case 1:
            class_text = '前端设计';
            break;
        case 2:
            class_text = '后台设计';
            break;
        case 3:
            class_text = '基础理论';
            break;
        case 4:
            class_text = '嵌入式';
            break;
        case 5:
            class_text = '移动开发';
            break;
        case 6:
            class_text = '项目发布';
            break;
    }
    return class_text;
}

function get_Class() {
    $.ajax({
        type: "POST",
        url: contextPath + "/learn_list",
        data: {
            action: 'get_class',
            No: No
        },
        dataType: 'json',
        success: function (jsonObject) {
            //console.log(jsonObject);
            if (jsonObject.release_status === 0) {
                pass = 0;
                alert('该课程尚未发布');
                window.location.href = "homepage.html";
                return
            }
            get_schedule();
            $("title").text(jsonObject.class_infor.class_title);
            $("#title").text(jsonObject.class_infor.class_title);
            $("#teacher").text(jsonObject.class_infor.teacher);
            $('#head').attr('src', contextPath + jsonObject.class_infor.teacher_head);
            $('#student_number').text(jsonObject.class_infor.student_count);
            ask_count = jsonObject.ask_count;
            note_count = jsonObject.note_count;
            no_count(ask_count, note_count);
            let ask_nav = $('#ask_nav');
            let note_nav = $('#note_nav');
            ask_nav.prev().text(ask_count);
            note_nav.prev().text(note_count);
            let class_type = type_text(jsonObject.class_infor.class_type);
            let other_class = $('#other_class');
            let one = $('#one');
            other_class.text(class_type + '相关课程');
            for (let i = 0; i < jsonObject.recommend.length; i++) {
                other_class.append(
                    '<div class="sno2-allbox">\n' +
                    '      <img src="' + contextPath + jsonObject.recommend[i].cover_address + '" class="imagesize" alt="">\n' +
                    '           <div class="coursecount">\n' +
                    '           <a href="Learn_list.html?class_id=' + jsonObject.recommend[i].class_id + '" class="coursecount-font">' + jsonObject.recommend[i].class_title + '</a>\n' +
                    '      </div>\n' +
                    '</div>'
                )
            }
            $('#class_type').text(class_type);
            $('#Bread_crumb_navigation').append(
                '  <a target="_blank" href="homepage.html">首页</a>\n' +
                '  <i class="i">\\</i>\n' +
                '  <a target="_blank" href="course.html?type=' + jsonObject.class_infor.class_type + '">' + class_type + '</a>\n' +
                '  <i class="i">\\</i>\n' +
                '  <a href="">' + jsonObject.class_infor.class_title + '</a>\n' +
                '  <i class="i">\\</i>'
            );
            if (GetQueryString('page_ask') == null && GetQueryString('page_note') == null) {
                $('#unit-nav').addClass('active show');
                one.addClass('active show');
            } else if (GetQueryString('page_note') == null) {
                ask_nav.addClass('active show');
                $('#two').addClass('active show');
            } else {
                note_nav.addClass('active show');
                $('#three').addClass('active show');
            }

            $('.sno1-1 .course-info-tip').append(
                '      <dl class="first">\n' +
                '          <dt>课程概要</dt>\n' +
                '          <dd class="autowrap">' + jsonObject.class_infor.outline + '</dd>\n' +
                '      </dl>'
            );

            let unit_titles = [];
            for (let i = 0; i < jsonObject.chapter.length; i++) {
                if (-1 === unit_contain(unit_titles, jsonObject.chapter[i].unit_title)) unit_titles.push(jsonObject.chapter[i].unit_title);
            }

            for (let i = 0; i < unit_titles.length; i++) {
                one.append(
                    '<div id="Unit' + i + '" class="ui-box" draggable="true"  >' +
                    '<div class="unit-title">' +
                    '<span  style="margin-right:20px;color:#999;font-weight:bold;">第' + (i + 1) + '章 ' + '</span>' +
                    '<span >' + unit_titles[i] + '</span>' +
                    '</div>'+
                    '</div>'
                );
            }
            for (let i = 0; i < jsonObject.chapter.length; i++) {
                let belong_unit = unit_contain(unit_titles, jsonObject.chapter[i].unit_title);
                $("#Unit" + belong_unit).append(
                    '<div class="list_box" >' +
                    '   <a  href="Play.html?' + GetQueryString('class_id') + '/' + jsonObject.chapter[i].unit_no + '">' +
                    '       <div class="ui-box1">' +
                    /*            '           <i class="fa fa-adjust"></i>' +*/
                    '               <span style="margin-right:20px;color:#999;">课时' + jsonObject.chapter[i].unit_no.charAt(jsonObject.chapter[i].unit_no.length - 1) + '</span>' +
                    '               <span >' + jsonObject.chapter[i].lesson_title + '</span>' +
                    '       </div>' +
                    '    </a>' +
                    ' </div>'
                );
            }
            if (one.html().length === 0) {
                one.append(
                    '<div class="no_find_class">暂无发布的章节</div>'
                )
            }
        }
    });

}

function unit_contain(unit_titles, unit_title) {
    for (let i = 0; i < unit_titles.length; i++) {
        if (unit_titles[i] === unit_title) return i
    }
    return -1
}

function get_schedule() {
    if (user === undefined) {
        $('.sno1-1 .learn-btn').append(
            '<button type="button" class="studyfont btn btn-outline-primary" data-toggle="modal" data-target="#LoginModal" >开始学习</button>\n'
        )
    } else {
        $.ajax({
            url: contextPath + "/students",
            data: {
                No: No,
                student: user,
                action: 'get_schedule'
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                let percentage = jsonObj.schedule;
                let last_time = jsonObj.last_time;
                if (percentage === undefined) {
                    $('.sno1-1 .learn-btn').append(
                        '<button type="button" class="studyfont btn btn-outline-primary" data-toggle="modal" data-target="#join_class">开始学习</button>\n'
                    )
                } else {
                    $('.sno1-1 .learn-btn').append(
                        '      <div class="learn-info"><span>已学 ' + percentage + '%</span></div>\n' +
                        '      <div class="progress">\n' +
                        '         <div class="progress-bar" style="width:' + percentage + '%"></div>\n' +
                        '     </div>\n' +
                        '     <div class="learn-info-media" data-class="' + last_time + '">上次学至 ' + last_time + '</div>\n' +
                        '     <button type="button" class="studyfont btn btn-outline-primary" onclick="continue_class(this)">继续学习</button>\n'
                    )
                }
            }
        });
    }
}

function add_page(page_ul_id, page, count, page_type) {
    const page_ul = $(page_ul_id);
    console.log(count);
    page_length = Math.ceil(count / 6);
    if ((parseInt(page) - 1) > 0) page_ul.append('<li class="page-item"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + (parseInt(page) - 1) + '">Previous</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

    if (page - 1 > 2 && page_length - page >= 2) {
        for (let i = page - 2; i <= page + 2; i++) {
            if (i < page_length + 1) {
                if (i === parseInt(page)) {
                    page_ul.append('<li class="page-item active"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
                } else page_ul.append('<li class="page-item"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
            }
        }
    } else {
        if (page <= 3) {
            for (let i = 1; i < 6; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
                    } else page_ul.append('<li class="page-item"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
                }
            }
        } else {
            for (let i = page_length - 4; i <= page_length; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
                    } else page_ul.append('<li class="page-item"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + i + '">' + i + '</a></li>');
                }
            }
        }

    }

    if ((parseInt(page) + 1) <= page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?class_id=' + No + '&' + page_type + '=' + (parseInt(page) + 1) + '">Next</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
}

function get_Note() {
    if (note_flag === 1) return;
    let page = GetQueryString('page_note');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        url: contextPath + "/postnote",
        data: {
            action: 'get_this_class_note',
            No: No,
            page: page
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            for (let i = 0; i < jsonObj.note.length; i++) {
                $('#note_ul').append(
                    '<li class="post-row js-find-txt">\n' +
                    '                            <div class="media">\n' +
                    '                                <a href="" target="_blank">\n' +
                    '                                    <img src="' + contextPath + jsonObj.note[i].head + '" width="40" height="40" alt="">\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div class="bd">\n' +
                    '                                <div class="tit">\n' +
                    '                                    <a href="" target="_blank">' + jsonObj.note[i].author + '</a>\n' +
                    '                                </div>\n' +
                    '                                <div class="js-note-main">\n' +
                    '                                    <div class="note-media">\n' +
                    '                                        <a target="_blank" href="Play.html?' + No + '/'+jsonObj.note[i].unit_no+'" class="from l">' + jsonObj.note[i].unit_no + '&nbsp&nbsp' + jsonObj.note[i].lesson_title + '</a>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="js-notelist-content notelist-content">\n' +
                    '                                        <div class="autowrap note-content">' + jsonObj.note[i].text + '</div>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="footer clearfix">\n' +
                    /*         '                                        <div class="actions l">\n' +
                             '                                            <a href="" target="_blank" class="post-action">查看全文</a>\n' +
                             '                                        </div>\n' +*/
                    '                                        <span class="r timeago">' + jsonObj.note[i].note_time + '</span>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>'
                )
            }
            setTimeout(function () {
                if (ask_count === -1) setTimeout(this, 500);
                else add_page('#page_note', page, note_count, 'page_note');
            }, 500);
        }
    });
    note_flag = 1;
}

function get_Ask() {
    if (ask_flag === 1) return;
    let page = GetQueryString('page_ask');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        url: contextPath + "/postask",
        data: {
            action: 'get_this_class_ask',
            No: No,
            page: page
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            for (let i = 0; i < jsonObj.ask.length; i++) {
                $('#ask_ul').append(
                    ' <li>\n' +
                    '                            <div class="ui-box">\n' +
                    '                                <div class="headslider qa-medias l">\n' +
                    '                                    <a class="media" target="_blank" href="" title="' + jsonObj.ask[i].asker + '">\n' +
                    '                                        <img src="' + contextPath + jsonObj.ask[i].head + '" width="40px" height="40px">\n' +
                    '                                    </a>\n' +
                    '                                </div>\n' +
                    '                                <div class="wendaslider qa-content">\n' +
                    '                                    <h2 class="wendaquetitle qa-header">\n' +
                    '                                        <div class="wendatitlecon qa-header-cnt clearfix">\n' +
                    '                                            <a class="qa-tit" target="_blank" href="questions.html?' + jsonObj.ask[i].ask_id + '">\n' +
                    '                                                <i>' + jsonObj.ask[i].ask_title + '</i>\n' +
                    '                                            </a>\n' +
                    '                                        </div>\n' +
                    '                                    </h2>\n' +
                    '                                    <div class="replycont qa-body clearfix">\n' +
                    '                                        <div class="l replydes" id="new_reply' + jsonObj.ask[i].ask_id + '">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="replymegfooter qa-footer clearfix">\n' +
                    '                                        <div class="l-box l">\n' +
                    '                                            <a class="replynumber static-count " target="_blank" href="questions.html?' + jsonObj.ask[i].ask_id + '">\n' +
                    '                                                <span class="static-item answer">' + jsonObj.ask[i].answer_count + ' 回答</span>\n' +
                    '                                                <span class="static-item">' + jsonObj.ask[i].visits_count + ' 浏览</span>\n' +
                    '                                            </a>\n' +
                    '                                            <a href="Play.html?'+No+'/'+jsonObj.ask[i].unit_no+'" target="_blank">' + jsonObj.ask[i].unit_no + '&nbsp&nbsp' + jsonObj.ask[i].lesson_title + '</a>\n' +
                    '                                        </div>\n' +
                    '                                        <em class="r">' + jsonObj.ask[i].ask_time + '</em>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>'
                );
                //$('#ask_no'+jsonObj.ask_no[i]).
                if (jsonObj.ask[i].new_answerer === '') {
                    $('#new_reply' + jsonObj.ask[i].ask_id).append(
                        '<button type="button" class="btn btn-light" onclick="window.open(\'questions.html?' + jsonObj.ask[i].ask_id + '\')">我来回答</button>\n'
                    )
                } else {
                    $('#new_reply' + jsonObj.ask[i].ask_id).append(
                        '<span class="replysign">\n' +
                        ' 最新回复 /\n' +
                        '   <a class="nickname" target="_blank" href="">' + jsonObj.ask[i].new_answerer + '</a>\n' +
                        ' </span>\n' +
                        ' <div class="replydet">' + jsonObj.ask[i].new_answer + '</div>\n'
                    )
                }
            }

            setTimeout(function () {
                if (ask_count === -1) setTimeout(this, 500);
                else add_page('#page_ask', page, ask_count, 'page_ask')
            }, 500);
        }
    });
    ask_flag = 1;
}

$(function () {
    $('#join_class').on('show.bs.modal', function (event) {
        if (get_class_flag !== 0) return;
        $.ajax({
            url: contextPath + "/students",
            data: {
                No: No,
                action: 'get_class'
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                console.log(jsonObj);
                let class_box = $('#class_box');
                if (jsonObj.class.length === 0) {
                    class_box.append(
                        '<div class="no_find_join_class">该课程暂未设立班级</div>'
                    );
                    return
                }
                for (let i = 0; i < jsonObj.class.length; i++) {
                    $('#class_table tbody').append(
                        '<tr>\n' +
                        '    <td>' + jsonObj.class[i].name + '</td>\n' +
                        '    <td><button type="button" style="margin-top:0;float:right" class="btn btn-outline-primary btn-sm" onclick="join_class(' + jsonObj.class[i].id + ')">加入班级</button></td>\n' +
                        '</tr>'
                    )
                }
            }
        });
        get_class_flag = 1;
    })
});

function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function join_class(classification) {
    let time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        url: contextPath + "/learn_list",
        data: {
            action: 'join_class',
            time: time2,
            No: No,
            classification:classification
        },
        dataType: 'json',
        success: function (jsonObject) {
            if (jsonObject===true) {
                window.location.href = "Play.html?" + No + "/1-1";
            }
        }
    })
}

function continue_class(event) {
    let last_time = $(event).prev().data('class');
    window.location.href = "Play.html?" + No + "/" + last_time.substring(0, last_time.indexOf(':'));
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
