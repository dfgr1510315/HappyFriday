let user = cookie.get('user');
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(7);});
    get_my_reply_ask();
});

function get_my_reply_ask() {
    let page = GetQueryString('page');
    if(page == null) page='1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath + "/postask",
        data: {
            action: 'get_my_reply_ask',
            page:page,
            answer:user
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            let ask_ul = $('#ask_ul');
            add_main(ask_ul,jsonObj);
            if (ask_ul.html().length === 0) {
                ask_ul.append(
                    '<div class="no_find_class">暂无讨论</div>'
                );
                return;
            }
            add_page(page,jsonObj.count);
        }
    });
}