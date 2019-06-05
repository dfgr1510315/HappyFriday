let user = cookie.get('user');
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(9);});
    get_notice();
});

function GetQueryString(name)
{
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null) return  unescape(r[2]); return null;
}

function notice_box_ul(notice,time,notice_id) {
    $('#notice_box_ul').append(
        '<li class="list-group-item">\n' +
        '                        <div class="pull-left">\n' +
        '                            <i class="fa fa-bullhorn"></i>\n' +
        '                        </div>\n' +
        '                        <div class="item_body">\n' +
        '                            <div class="notification-body">'+notice+'</div>\n' +
        '                            <div class="notification-time">'+time+
        '                                <div class="delete">\n' +
        '                                    <i class="fa fa-trash-o" style="font-size:19px" data-id="'+notice_id+'" onclick="delete_notice(this)"></i>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </li>'
    )
}

function delete_notice(event) {
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/notification",
        data: {
            action: 'delete_notice',
            notice_id:$(event).data('id')
        },
        dataType: 'json',
        success: function (jsonObj) {
            if (jsonObj===true){
                $(event).parent().parent().parent().parent().remove();
            }else alert('删除失败')
        }
    })
}

function go(){
    let keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode  === 13) {
        let jump = $('#jump');
        if (jump.val()>0&&jump.val()<=page_length&&jump.val()%1 === 0){
            window.location.href="notification.html?page="+jump.val();
        }else alert('请重新输入页号');
    }
}

function get_notice() {
    let page = GetQueryString('page');
    if (page == null) page = '1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/notification",
        data: {
            action: 'get_notice',
            user: user,
            page:page
        },
        dataType: 'json',
        success: function (jsonObj) {
            console.log(jsonObj);
            for (let i=0;i<jsonObj.notice.length;i++){
                let notice;
                switch (jsonObj.notice[i].notice_type) {
                    case 1:
                        notice = "学员<a href='' target='_blank'>"+jsonObj.notice[i].from_user+"</a>加入了课程<a href='Learn_list.html?class_id="+jsonObj.notice[i].class_id+"' target='_blank'>《"+jsonObj.notice[i].class_title+"》</a>\n";
                        break;
                    case 2:
                        notice = "学员<a href='' target='_blank'>"+jsonObj.notice[i].from_user+"</a>在课程<a href='Learn_list.html?class_id="+jsonObj.notice[i].class_id+"' target='_blank'>《"+jsonObj.notice[i].class_title+"》</a>发表了问题<a href='questions.html?"+jsonObj.notice[i].ask_id+"' target='_blank'>"+jsonObj.notice[i].ask_title+"</a>\n";
                        break;
                    case 3:
                        notice = "用户<a href='' target='_blank'>"+jsonObj.notice[i].from_user+"</a>在你发布的问题<a href='questions.html?"+jsonObj.notice[i].ask_id+"' target='_blank'>"+jsonObj.notice[i].ask_title+"</a>中回答了你";
                        break;
                    case 4:
                        notice = "用户<a href='' target='_blank'>"+jsonObj.notice[i].from_user+"</a>在<a href='questions.html?"+jsonObj.notice[i].ask_id+"' target='_blank'>"+jsonObj.notice[i].ask_title+"</a>中回复了你";
                        break
                }
                notice_box_ul(notice,jsonObj.notice[i].time,jsonObj.notice[i].notice_id)
            }
            let notice_box_ul_var = $('#notice_box_ul');
            if (notice_box_ul_var.html().length === 0) {
                $('#notice_box').prepend(
                    '<div class="no_find_class">暂无通知</div>'
                )
            }else {
                let page_ul =  $('#page_ask');
                page_length = Math.ceil(jsonObj.count/6);
                if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?page='+(parseInt(page)-1)+'">Previous</a></li>');
                else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

                if (page-1>2&&page_length-page>=2){
                    for (i = page-2;i<=page+2;i++){
                        if (i<page_length+1){
                            if (i===parseInt(page)){
                                page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                            }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                        }
                    }
                }else {
                    if (page<=3){
                        for (i=1;i<6;i++){
                            if (i<page_length+1){
                                if (i===parseInt(page)){
                                    page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                            }
                        }
                    }else {
                        for (i=page_length-4;i<=page_length;i++){
                            if (i<page_length+1){
                                if (i===parseInt(page)){
                                    page_ul.append('<li class="page-item active"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                                }else page_ul.append('<li class="page-item"><a class="page-link" href="?page='+i+'">'+i+'</a></li>');
                            }
                        }
                    }
                }

                if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?page='+(parseInt(page)+1)+'">Next</a></li>');
                else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>');

                page_ul.append(
                    '<li class="page-item last">\n' +
                    '<a >\n' +
                    '<label>\n' +
                    '<input id="jump" type="text" class="form-control" onkeydown="go();">\n' +
                    '</label>\n' +
                    '<span id="count_page"></span>\n' +
                    '</a>\n' +
                    '</li>');
                $('#count_page').text('/ '+page_length);
            }
        }
    });
}