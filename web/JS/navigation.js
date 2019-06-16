let head_image ;
let usertype ;
$(document).ready(function(){
    get_user_infor();
    $("#search_input").blur(function(){
        setTimeout(function(){
            $('.title_list_box').hide();
        },500)
    });
});

function get_user_infor() {
   // let dtd = $.Deferred();
    $.ajax({
        type: "POST",
        url: contextPath+"/register",
        //async:false,
        data: {
            state:'user_infor'
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            head_image = cookie.get('head_image');
            usertype = cookie.get('usertype');
            let user = cookie.get('user');
            if (jsonObj.user===undefined){
                cookie.del('user');
                $('#loginButton').show();
                $('.personalCenter').remove();
                return;
            }
            if (jsonObj.user!==user){
                cookie.set('user',jsonObj.user);
                window.location.href="/LJZ/HTML_JSP/404.html";
                return;
            }
            if (jsonObj.read[0]===0)  $('.count-cart').css('display','inline');
            checkCookie();
            load_history();
     /*       card_show();
            history_show();*/
            //dtd.resolve();
        }
    });
}

function checkCookie() {
    $("#loginButton").remove();
    $(".personalCenter").show();
    $("#head_image").attr('src',contextPath+head_image);
}

function load_history() {
    let load_history_class_id = cookie.get('class_id').toString().split(',');
    let load_schedule = cookie.get('schedule').toString().split(',');
    let load_last_time = cookie.get('last_time').toString().split(',');
    let load_title = cookie.get('class_title').toString().split(',');
    for (let i=0;i<load_history_class_id.length;i++){
        if ('0' === load_history_class_id[i])  continue;
        let unit = load_last_time[i].split(':');
        $('.history_ul').append(
            '   <li  style="width: 100%;text-align: left;line-height: 10px">\n' +
            '                        <a href="/LJZ/HTML_JSP/Play.html?'+load_history_class_id[i].trim()+'/'+unit[0].trim()+'" target="_blank" title="'+load_last_time[i].trim()+'" class="clearfix">\n' +
            '                            <div class="link" style="max-width: 70%">'+load_last_time[i].trim()+'</div>\n' +
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

function search() {
    let keyword = $('#search_input').val();
    if (keyword.trim()==='') return;
    window.open(contextPath+'/HTML_JSP/Search/video.html?keyword='+encodeURI(keyword),'_blank');
}

function search_tips() {
    let timeout;
    $('#search_input').keyup(function(){
        clearTimeout(timeout);
        let keyword = $('#search_input').val();
        if(keyword==='') return;
        let data = {
            Read_or_Save:'search_tips',
            keyword:keyword
        };
        timeout = setTimeout(function() {
            $.ajax({
                type:"POST",
                url:contextPath+"/SaveClassInfor",
                data:data,
                success:function(html){
                    let title_list_box = $('.title_list_box');
                    let title_list = $('.title_list');
                    if (html==='') {
                        title_list_box.hide();
                        return;
                    }
                    title_list_box.show();
                    title_list.html(html);
                    let list_tips = $('.list_tips');
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

function hide_tips(){
    $('.title_list').html('');
    $('.title_list_box').hide()
}

let cookie = {
    set: function(name, value) {
        let Days = 30*12*5;
        let exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString();
    },
    get: function(name) {
        let arr, reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)');
        if(arr=document.cookie.match(reg)) {
            //console.log(document.cookie.match(reg));
            return unescape(arr[2]);
        } else {
            return null;
        }
    },
    del: function(name) {
        let exp = new Date();
        exp.setTime(exp.getTime() - 1);
        let cval = cookie.get(name);
        if(cval != null) {
            document.cookie = name + '=' + cval + ';expires=' + exp.toGMTString();
        }
    }
};

/*
function history_show() {
    let timeout = null;
    let history_card = $(".history");
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
    let timeout = null;
    let user_card = $(".user");
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
*/