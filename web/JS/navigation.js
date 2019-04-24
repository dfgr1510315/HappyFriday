var user;
var head_image ;
var usertype ;
var contextPath = getContextPath();
function get_user_infor() {
    var dtd = $.Deferred();
    $.ajax({
        type: "POST",
        url: contextPath+"/register",
        data: {
            state:'user_infor'
        },
        dataType: 'json',
        success: function (jsonObj) {
            head_image = cookie.get('head_image');
            usertype = cookie.get('usertype');
            user = jsonObj.user;
            if (user===undefined){
                $("#loginButton").show();
                $('.personalCenter').remove();
                return
            }
            if (jsonObj.read===0)  $('.count-cart').css('display','inline');
            checkCookie();
            load_history();
            card_show();
            history_show();
            dtd.resolve();
        }
    });
    return dtd;
}

function checkCookie() {
    $("#loginButton").remove();
    $(".personalCenter").show();
    $("#head_image").attr('src',contextPath+head_image);
}

var cookie = {
    set: function(name, value) {
        var Days = 30*12*5;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString();
    },
    get: function(name) {
        var arr, reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)');
        if(arr=document.cookie.match(reg)) {
            return unescape(arr[2]);
        } else {
            return null;
        }
    },
    del: function(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = cookie.get(name);
        if(cval != null) {
            document.cookie = name + '=' + cval + ';expires=' + exp.toGMTString();
        }
    }
};

$(document).ready(function(){
    search_tips();
    $("#search_input").blur(function(){
        setTimeout(function(){
            $('.title_list_box').hide();
        },500)
    });
});

function load_history() {
    var load_history_class_id = cookie.get('class_id').toString().split(',');
    var load_schedule = cookie.get('schedule').toString().split(',');
    var load_last_time = cookie.get('last_time').toString().split(',');
    var load_title = cookie.get('class_title').toString().split(',');
    for (var i=0;i<load_history_class_id.length;i++){
        if ('0' === load_history_class_id[i])  continue;
        var unit = load_last_time[i].split(':');
        $('.history_ul').append(
            '   <li  style="width: 100%;text-align: left;line-height: 10px">\n' +
            '                        <a href="/HTML_JSP/Play.html?'+load_history_class_id[i].trim()+'/'+unit[0].trim()+'" target="_blank" title="'+load_last_time[i].trim()+'" class="clearfix">\n' +
            '                            <div class="link">'+load_last_time[i].trim()+'</div>\n' +
            '                            <div style="float: right">\n' +
            '                                <div class="state" style="display: inline;">\n' +
            '                                    <span class="page">'+load_title[i]+'</span>\n' +
            '                                    <span class="split">|</span>\n' +
            '                                    <span>'+load_schedule[i]+'%</span>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </a>\n' +
            '                    </li>'
        )
    }
}

function history_show() {
    var timeout = null;
    var history_card = $(".history");
    history_card.mouseover(function(){
        $("#history_card").show();
        if( timeout != null ) clearTimeout(timeout);
    });

    history_card.mouseout(function(){
        timeout = setTimeout(function(){
            $("#history_card").hide();
        },1000);
    });
}

function card_show() {
    var timeout = null;
    var user_card = $(".user");
    user_card.mouseover(function(){
        $("#user_card").show();
        if( timeout != null ) clearTimeout(timeout);
    });

    user_card.mouseout(function(){
        timeout = setTimeout(function(){
            $("#user_card").hide();
        },1000);
    });
}

function search() {
    var keyword = $('#search_input').val();
    if (keyword.trim()==='') return;
    window.open(contextPath+'/HTML_JSP/Search/video.html?keyword='+encodeURI(keyword),'_blank');
}

function search_tips() {
    $('.title_list_box').hide();
    var timeout;
    $('#search_input').keyup(function(){
        clearTimeout(timeout);
        var keyword = $('#search_input').val();
        if(keyword==='') return;
        var data = {
            Read_or_Save:'search_tips',
            keyword:keyword
        };
        timeout = setTimeout(function() {
            $.ajax({
                type:"POST",
                url:contextPath+"/SaveClassInfor",
                data:data,
                success:function(html){
                    var title_list_box = $('.title_list_box');
                    var title_list = $('.title_list');
                    if (html==='') {
                        title_list_box.hide();
                        return;
                    }
                    title_list_box.show();
                    title_list.html(html);
                    var list_tips = $('.list_tips');
                    list_tips.hover(
                        function(){
                            $(this).addClass('hover');
                        },
                        function(){
                            $(this).removeClass('hover');
                        }
                    );
                    list_tips.click(function(){
                        $('#search_input').val($(this).text());
                        $('.title_list_box').hide();
                    });
                }
            });
        }, 600);
        return false;
    });
}
