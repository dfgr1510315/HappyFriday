var page_length;
var contextPath = getContextPath();
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html', function () {
        $('#VerticalNav').load('VerticalNav.html', function () {
            addClass(4);
            $.when(get_user_infor()).done(function () {
                get_All_ask();
                $('#radio_cover_img').attr('src', contextPath + head_image);
            });
        });
    });
});

function get_All_ask() {
    var page = window.location.search.replace('?', '');
    if (page === '') page = '1';
    $.ajax({
        url: contextPath + "/postask",
        data: {
            page: page,
            user: user,
            action: 'get_all_ask'
        },
        type: "POST",
        dataType: "json",
        asynch: "false",
        success: function (jsonObj) {
            console.log(jsonObj);
            var ask_ul = $('#ask_ul');
            for (var i = 0; i < jsonObj.ask.length; i++) {
                ask_ul.append(
                    ' <li>\n' +
                    '                            <div class="ui-box">\n' +
                    '                                <div class="headslider qa-medias l">\n' +
                    '                                    <a class="media" target="_blank" href="" title="' + jsonObj.ask[i].class_title + '">\n' +
                    '                                        <img src="' + contextPath + jsonObj.ask[i].cover_address + '" width="40px" height="40px" alt="">\n' +
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
                    '                                            <a href="Learn_list.html?class_id=' + jsonObj.ask[i].belong_class_id + '" target="_blank">' + jsonObj.ask[i].class_title + '</a>\n' +
                    '                                        </div>\n' +
                    '                                        <em class="r">' + jsonObj.ask[i].ask_time + '</em>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>'
                );
                if (jsonObj.ask[i].new_answer === '') {
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

            if (ask_ul.html().length === 0) {
                ask_ul.append(
                    '<div class="no_find_class">暂无讨论</div>'
                )
            } else {
                add_page(jsonObj.count, page);
            }
        }
    });
}

function add_page(count, page) {
    var page_ul = $('#page');
    page_length = Math.ceil(count / 6);
    if ((parseInt(page) - 1) > 0) page_ul.append('<li class="page-item"><a class="page-link" href="?' + (parseInt(page) - 1) + '">Previous</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

    if (page - 1 > 2 && page_length - page >= 2) {
        for (var i = page - 2; i <= page + 2; i++) {
            if (i < page_length + 1) {
                if (i === parseInt(page)) {
                    page_ul.append('<li class="page-item active"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
                } else page_ul.append('<li class="page-item"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
            }
        }
    } else {
        if (page <= 3) {
            for (i = 1; i < 6; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
                    } else page_ul.append('<li class="page-item"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
                }
            }
        } else {
            for (i = page_length - 4; i <= page_length; i++) {
                if (i < page_length + 1) {
                    if (i === parseInt(page)) {
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
                    } else page_ul.append('<li class="page-item"><a class="page-link" href="?' + i + '">' + i + '</a></li>');
                }
            }
        }

    }

    if ((parseInt(page) + 1) <= page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?' + (parseInt(page) + 1) + '">Next</a></li>');
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
    $('#count_page').text('/ ' + page_length);
}

function go() {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode === 13) {
        var jump = $('#jump');
        if (jump.val() > 0 && jump.val() <= page_length && jump.val() % 1 === 0) {
            window.location.href = "Student_commentary.html?" + jump.val();
        } else alert('请重新输入页号');
    }
}
