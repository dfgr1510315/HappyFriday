let type;

$(document).ready(function(){
    type = GetQueryString('type');
    if (type==null) type ='0';
    $('#nav_type').children().eq(type).children().addClass('active');
    get_class();
    $('#navigation').load('navigation_dark.html');
    $('.user_navigation .nav-pills li:eq(3) a').css('color','white');
    $('.course_li').addClass('active');
    $('#loginPC').load('LoginPC.html');
});

function add_class(class_no,cover,title,type,teacher,student_number,outline){
    $('.types-content').append(
        '<div class="index-card-container course-card-container container">\n' +
        '                    <a target="_blank" class="course-card" href="Learn_list.html?class_id='+class_no+'">\n' +
        '                        <div class="course-card-top hashadow">\n' +
        '                            <img src="'+contextPath+cover+'"  class="course-banner" alt="">\n' +
        '                        </div>\n' +
        '                        <div class="course-card-content">\n' +
        '                            <h3 class="course-card-name">'+title+'</h3>\n' +
        '                            <div class="clearfix course-card-bottom">\n' +
        '                                <div class="course-card-info">\n' +
        '                                    <span>'+type+'</span>\n' +
        '                                    <span>'+teacher+'</span>\n' +
        '                                    <span><i class="icon-set_sns fa fa-user-o"></i>'+student_number+'</span>\n' +
        '                                </div>\n' +
        '                                <p class="course-card-desc">'+outline+'</p>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </a>\n' +
        '                </div>'
    )
}

function get_class(){
    let page = GetQueryString('page');
    if(page == null) page='1';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/SaveClassInfor",
        data: {
            Read_or_Save:'get_new_class',
            page:page,
            type:type
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            if (jsonObj.count===0) $('.types-content').append(
                '<div class="no_find_class">暂无课程</div>'
            ) ;
            else {
                for (let i=0;i<jsonObj.new_class.length;i++){
                    let class_type = type_text(jsonObj.new_class[i].class_type);
                    add_class(jsonObj.new_class[i].class_id,jsonObj.new_class[i].cover_address,jsonObj.new_class[i].class_title,class_type,jsonObj.new_class[i].teacher,jsonObj.new_class[i].student_count,jsonObj.new_class[i].outline);
                }
                add_page(page,jsonObj.count);
            }
        }
    });
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

function add_page(page,count){
    let page_ul =  $('#page');
    page_length = Math.ceil(count/25);
    if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+(parseInt(page)-1)+'">Previous</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

    if (page-1>2&&page_length-page>=2){
        for (let i = page-2;i<=page+2;i++){
            if (i<page_length+1){
                if (i===parseInt(page)){
                    page_ul.append('<li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                }else page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
            }
        }
    }else {
        if (page<=3){
            for (i=1;i<6;i++){
                if (i<page_length+1){
                    if (i===parseInt(page)){
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                }
            }
        }else {
            for (i=page_length-4;i<=page_length;i++){
                if (i<page_length+1){
                    if (i===parseInt(page)){
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+i+'">'+i+'</a></li>');
                }
            }
        }
    }
    if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?type='+type+'&page='+(parseInt(page)+1)+'">Next</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
}

function GetQueryString(name)
{
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null) return  unescape(r[2]); return null;
}