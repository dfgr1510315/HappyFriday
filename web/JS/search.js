let keyword = GetQueryString('keyword');
let keyword_model = /^\w+$/;

/*function change_src() {
    $('#logo img').attr('src', '/image/HUAS.png');
    $('#navigation ul li:eq(1) a').attr('href', '../homepage.html');
    $('#navigation ul li:eq(2) a').attr('href', '../Resources.html');
    $('#navigation ul li:eq(3) a').attr('href', '../course.html');
    $('#notice').attr('href', '../notification.html');
    $('#user_card a:eq(0)').attr('href', '../PersonalCenter.html');
    $('#user_card a:eq(1)').attr('href', '../homepage.html');
}*/


function add_href() {
    $(".nav-justified a").each(function () {
        $(this).attr('href', $(this).attr('href') + '?keyword=' + keyword)
    });
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function get_class_type(class_type_in) {
    let class_type;
    console.log(class_type_in);
    switch (class_type_in) {
        case 1:
            class_type = '前端设计';
            break;
        case 2:
            class_type = '后台设计';
            break;
        case 3:
            class_type = '基础理论';
            break;
        case 4:
            class_type = '嵌入式';
            break;
        case 5:
            class_type = '移动开发';
            break;
        case 6:
            class_type = '项目发布';
            break;
    }
    return class_type;
}

function highlight(type) {
    clearSelection();//先清空一下上次高亮显示的内容；
    let searchText = $('#search_ipt').val();//获取你输入的关键字；
    let regExp = new RegExp(searchText, 'g');//创建正则表达式，g表示全局的，如果不用g，则查找到第一个就不会继续向下查找了；
    let get_high;
    switch (type) {
        case 1:
            get_high = '.course-item-detail p,.course-item-detail a';
            break;
        case 2:
            get_high = '.headline a';
            break;
        case 3:
            get_high = '.ui-box .qa-header .qa-tit i';
            break;
        case 4:
            get_high = '#note_box .post-row .note_text p'
    }
    $(get_high).each(function ()//遍历文章；
    {
        let html = $(this).html();
        let newHtml = html.replace(regExp, '<span class="highlight" >' + searchText + '</span>');//将找到的关键字替换，加上highlight属性；
        $(this).html(newHtml);//更新文章；
    });
}

function clearSelection() {
    $('.course-item-detail p').each(function ()//遍历
    {
        $(this).find('.highlight').each(function ()//找到所有highlight属性的元素；
        {
            $(this).replaceWith($(this).html());//将他们的属性去掉；
        });
    });
    $('.course-item-detail a').each(function ()//遍历
    {
        $(this).find('.highlight').each(function ()//找到所有highlight属性的元素；
        {
            $(this).replaceWith($(this).html());//将他们的属性去掉；
        });
    });
}

function page_ul(page, count) {
    let page_ul = $('#page');
    page_length = Math.ceil(count / 6);
    if ((parseInt(page) - 1) > 0) page_ul.append('<li class="page-item"><a class="page-link" href="?' + keyword + '&page=' + (parseInt(page) - 1) + '">Previous</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

    if (page - 1 > 2 && page_length - page >= 2) {
        for (let i = page - 2; i <= page + 2; i++) {
            if (i < page_length + 1) {
                if (i === parseInt(page)) {
                    page_ul.append(' <li class="page-item active"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
                } else page_ul.append(' <li class="page-item"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
            }
        }
    } else {
        if (page <= 3) {
            for (i = 1; i < 6; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
                    } else page_ul.append(' <li class="page-item"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
                }
            }
        } else {
            for (i = page_length - 4; i <= page_length; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append(' <li class="page-item active"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
                    } else page_ul.append(' <li class="page-item"><a class="page-link" href="?' + keyword + '&page=' + i + '">' + i + '</a></li>');
                }
            }
        }

    }

    if ((parseInt(page) + 1) <= page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?' + keyword + '&page=' + (parseInt(page) + 1) + '">Next</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>');

    page_ul.append(
        '<li class="page-item last">\n' +
        '<a >\n' +
        '<label>\n' +
        '<input id="jump" type="text" class="form-control" onkeydown="go();" style="width: 50px;">\n' +
        '</label>\n' +
        '<span id="count_page"> </span>\n' +
        '</a>\n' +
        '</li>');
    $('#count_page').text('/ ' + page_length);
}

function go() {
    let sp = location.pathname.split('/');
    //console.log(sp[sp.length-1]);
    let keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode === 13) {
        let jump = $('#jump');
        if (jump.val() > 0 && jump.val() <= page_length && jump.val() % 1 === 0) {
            window.location.href = sp[sp.length - 1] + "?keyword=" + keyword + '&page=' + jump.val();
        } else alert('请重新输入页号');
    }
}

function search_to() {
    let sp = location.pathname.split('/');
    let new_keyword = $('#search_ipt').val();
    //console.log(new_keyword+'!!!');
    if (new_keyword === keyword || new_keyword.trim() === '') return;
    /*if (!keyword_model.test(new_keyword)) {
        alert('请勿输入非法字符');
        return;
    }*/
    window.location.href = sp[sp.length - 1] + '?keyword=' + encodeURI(new_keyword);
}

function search_note() {
    let keyword = $('#search_ipt').val();
    if (keyword.trim() === '') return;
    let page = GetQueryString('page');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postnote",
        data: {
            page: page,
            action: 'search_note',
            keyword: keyword
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            $('#count').text('共找到' + jsonObj.count + '个结果');
            let note_box = $('#note_box');
            if (jsonObj.count === 0) {
                note_box.append(
                    '<div class="no_find_class">暂无结果</div>'
                );
                return;
            }
            for (let i = 0; i < jsonObj.note.length; i++) {
                note_box.prepend('<div class="post-row">\n' +
                    '                            <div style="float:left;">\n' +
                    '                                <a target="_blank" href="../Learn_list.html?class_id=' + jsonObj.note[i].belong_class_id + '">\n' +
                    '                                    <img src="' + contextPath + jsonObj.note[i].cover_address + '" style="width: 40px;height: 40px;border-radius: 20px;">\n' +
                    '                                </a>\n' +
                    '                            </div>\n' +
                    '                            <div style="margin-left: 60px">\n' +
                    '                                <div class="title">\n' +
                    '                                    <a target="_blank" href="../Learn_list.html?class_id=' + jsonObj.note[i].belong_class_id + '" >' + jsonObj.note[i].class_title + '</a>\n' +
                    '                                </div>\n' +
                    '                                <div class="unit">\n' +
                    '                                    <a target="_blank" href="../Play.html?' + jsonObj.note[i].belong_class_id + '/' + jsonObj.note[i].unit_no + '" >' + jsonObj.note[i].unit_no + ':' + jsonObj.note[i].lesson_title + '</a>\n' +
                    '                                </div>\n' +
                    '                                <div style="display: block;">\n' +
                    '                                    <div class="note_text">\n' +
                    '                                        ' + jsonObj.note[i].text + '\n' +
                    '                                    </div>\n' +
                    '                                    <div class="post" style="margin-left: 445px;">\n' +
                    '                                        <span >' + jsonObj.note[i].note_time + '</span>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>');
            }
            page_ul(page, jsonObj.count);
            highlight(4);
        }
    })
}

function search_user() {
    let keyword = $('#search_ipt').val();
    if (keyword.trim() === '') return;
    let page = GetQueryString('page');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/changeInfor",
        data: {
            page: page,
            action: '2',
            keyword: keyword
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            $('#count').text('共找到' + jsonObj.user_count + '个结果');
            if (jsonObj.user_count === 0) {
                $('.user-list').append(
                    '<div class="no_find_class">暂无结果</div>'
                );
                return;
            }
            for (let i = 0; i < jsonObj.user_infor.length; i++) {
                $('.user-list').append(
                    '      <li class="up-item">\n' +
                    '                        <div class="up-face">\n' +
                    '                            <a href="javascript:void(0)" target="_blank"  class="face-img">\n' +
                    '                                <div class="lazy-img">\n' +
                    '                                    <img alt="" src="' + contextPath + jsonObj.user_infor[i].head + '">\n' +
                    '                                </div>\n' +
                    '                            </a>\n' +
                    '                        </div>\n' +
                    '                        <div class="info-wrap">\n' +
                    '                            <div class="headline">\n' +
                    '                                <a href="javascript:void(0)"  target="_blank" class="title">' + jsonObj.user_infor[i].nike + '</a>\n' +
                    '                            </div>\n' +
                    '                            <div class="up-info">\n' +
                    '                                <span>性别：' + jsonObj.user_infor[i].sex + '</span>\n' +
                    '                                <span>' + jsonObj.user_infor[i].usertype + '</span>\n' +
                    '                            </div>\n' +
                    '                            <div class="desc">' + jsonObj.user_infor[i].information + '</div>\n' +
                    '                        </div>\n' +
                    '                    </li>'
                )
            }
            page_ul(page, jsonObj.user_count);
            highlight(2);
        }
    })
}

function search_ask() {
    let keyword = $('#search_ipt').val();
    if (keyword.trim() === '') return;
    let page = GetQueryString('page');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postask",
        data: {
            action: 'search_ask',
            page: page,
            keyword: keyword
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            $('#count').text('共找到' + jsonObj.count + '个结果');
            let ask_ul = $('#ask_ul');
            if (jsonObj.count === 0) {
                ask_ul.append(
                    '<div class="no_find_class">暂无结果</div>'
                );
                return;
            }
            for (let i = 0; i < jsonObj.ask.length; i++) {
                ask_ul.append(
                    ' <li>\n' +
                    '                            <div class="ui-box">\n' +
                    '                                <div class="headslider qa-medias l">\n' +
                    '                                    <a class="media" target="_blank" href="javascript:void(0)">\n' +
                    '                                        <img src="' + contextPath + jsonObj.ask[i].cover_address + '" style="width: 50px;height: 50px;border-radius: 0;" alt="">\n' +
                    '                                    </a>\n' +
                    '                                </div>\n' +
                    '                                <div class="wendaslider qa-content">\n' +
                    '                                    <h2 class="wendaquetitle qa-header">\n' +
                    '                                        <div class="wendatitlecon qa-header-cnt clearfix">\n' +
                    '                                            <a class="qa-tit" target="_blank" href="../questions.html?' + jsonObj.ask[i].ask_id + '">\n' +
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
                    '                                            <a class="replynumber static-count " target="_blank" href="../questions.html?' + jsonObj.ask[i].ask_id + '">\n' +
                    '                                                <span class="static-item answer">' + jsonObj.ask[i].answer_count + ' 回答</span>\n' +
                    '                                                <span class="static-item">' + jsonObj.ask[i].visits_count + ' 浏览</span>\n' +
                    '                                            </a>\n' +
                    '                                            <a href="../Learn_list.html?class_id=' + jsonObj.ask[i].belong_class_id + '" target="_blank">' + jsonObj.ask[i].class_title + '</a>\n' +
                    '                                        </div>\n' +
                    '                                        <em class="r">' + jsonObj.ask[i].ask_time + '</em>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>'
                );
                if (jsonObj.ask[i].new_answer === null) {
                    $('#new_reply' + jsonObj.ask[i].ask_id).append(
                        '<button type="button" class="btn btn-light" onclick="window.open(\'../questions.html?' + jsonObj.ask[i].ask_id + '\')">我来回答</button>\n'
                    )
                } else {
                    $('#new_reply' + jsonObj.ask[i].ask_id).append(
                        '<span class="replysign">\n' +
                        ' 最新回复 /\n' +
                        '   <a class="nickname" target="_blank" href="javascript:void(0)">' + jsonObj.ask[i].new_answerer + '</a>\n' +
                        ' </span>\n' +
                        ' <div class="replydet">' + jsonObj.ask[i].new_answer + '</div>\n'
                    )
                }
            }
            page_ul(page, jsonObj.count);
            highlight(3);
        }
    })
}

function search_class() {
    let keyword = $('#search_ipt').val();
    if (keyword.trim() === '') return;
    let page = GetQueryString('page');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/SaveClassInfor",
        data: {
            page: page,
            Read_or_Save: 'search_class',
            keyword: keyword
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            $('#count').text('共找到' + jsonObj.count + '个结果');
            if (jsonObj.count === 0) {
                $('.search-content').append(
                    '<div class="no_find_class">暂无结果</div>'
                );
                return;
            }
            for (let i = 0; i < jsonObj.class.length; i++) {
                let class_type = get_class_type(jsonObj.class[i].class_type);
                $('.search-content').append(
                    ' <div class="course-item">\n' +
                    '                    <a href="../Learn_list.html?class_id=' + jsonObj.class[i].class_id + '" target="_blank" class="course-detail-title">\n' +
                    '                        <img src="' + contextPath + jsonObj.class[i].cover_address + '" alt="">\n' +
                    '                    </a>\n' +
                    '                    <div class="course-item-detail">\n' +
                    '                        <a href="../Learn_list.html?class_id=' + jsonObj.class[i].class_id + '" target="_blank" class="course-detail-title">\n' +
                    '                            ' + jsonObj.class[i].class_title + '\n' +
                    '                        </a>\n' +
                    '                        <div class="course-item-classify">\n' +
                    '                            <span>' + class_type + '</span>\n' +
                    '                            <span>教师：' + jsonObj.class[i].teacher + '</span>\n' +
                    '                            <i class="fa fa-user-o" style="font-size: 11px;"></i><span>' + jsonObj.class[i].student_count + '</span>\n' +
                    '                        </div>\n' +
                    '                        <p>\n' +
                    '                            ' + jsonObj.class[i].outline + '\n' +
                    '                        </p>\n' +
                    '                    </div>\n' +
                    '                </div>'
                )
            }
            page_ul(page, jsonObj.count);
            highlight(1);
        }
    });
}