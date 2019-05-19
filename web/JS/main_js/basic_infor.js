let flag1 = true;
let flag2 = true;
let No;
$(document).ready(function(){
    No = GetQueryString('class_id');
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html',function (){get_information()});
    $('#course_manag_nav').load('course_manag_nav.html',function () {addAction(1)});
});

function get_information(){
    let title = $("#title");
    let title_waring = $('#title_waring');
    title.focus(function(){
        title_waring.css('display','none');
    });
    title.blur(function(){
        if (title.val().length<2 || title.val().indexOf(" ") !== -1){
            title_waring.css('display','block');
            flag1 = false;
        }else flag1 = true;
    });

    let sel1 = $("#sel1");
    let sel1_waring = $('#sel1_waring');
    sel1.focus(function(){
        sel1_waring.css('display','none');
    });
    sel1.blur(function(){
        if (sel1.val()==='类型'){
            sel1_waring.css('display','block');
            flag2 = false;
        }else flag2 = true;
    });

    $.ajax({
        type: "POST",
        url: contextPath+"/SaveClassInfor",
        data: {
            No:No,
            Read_or_Save:'get_infor'
        },
        dataType: 'json',
        success: function (jsonObj) {
            $('#title').val(jsonObj.title);
            $('#sel1').val(jsonObj.type);
            $('#outline').val(jsonObj.outline);
        }
    });
}

function GetQueryString(name)
{
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null) return  unescape(r[2]); return null;
}

function save_information() {
    if (flag1===true && flag2===true){
        $.ajax({
            type: "POST",
            url: contextPath+"/SaveClassInfor",
            data: {
                No:No,
                title:$('#title').val(),
                sel1: $('#sel1').val(),
                outline:$('#outline').val(),
                Read_or_Save:'set_infor'
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (jsonObj===true) alert('修改成功');
            }
        });
    }
}