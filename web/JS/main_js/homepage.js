$(document).ready(function(){
    $('#navigation').load('navigation.html');
    $('.user_navigation ul li:eq(1) a').addClass('active');
    $('.homepage').addClass('active');
    $('#loginPC').load('LoginPC.html');
    get_new_class();
});

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

function get_new_class() {
    $.ajax({
        type: "POST",
        url: contextPath+"/SaveClassInfor",
        data: {
            Read_or_Save:'get_new_class',
            page:'0'
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            for (let i=0;i<jsonObj.new_class.length;i++){
                let class_type = type_text(jsonObj.new_class[i].class_type);
                $('.types-content').append(
                    '        <div class="width_224 height172 float_l margin_r20 border_shadow jingguoxianshi over_pos bianshou" onclick="window.open(\'Learn_list.html?class_id='+jsonObj.new_class[i].class_id+'\')" >\n' +
                    '            <div class="width100 float_l img_100 z_inx_1">\n' +
                    '                <img src="'+contextPath+jsonObj.new_class[i].cover_address+'">\n' +
                    '            </div>\n' +
                    '            <div class="img_backg2 donghua">\n' +
                    '                <span class="margin_t10 float_l ">'+jsonObj.new_class[i].class_title+'</span>\n' +
                    '                <span class="float_l fon_siz12 line_hei16 color_gray margin_t5 posi_relative ">'+jsonObj.new_class[i].outline+'</span>\n' +
                    '            </div>\n' +
                    '            <div class="width100 float_l padding_lr20 height48 fon_siz12 line_hei48 z_inx_3 posi_relative backg_white">\n' +
                    '                <span class="float_l color_gray" style="margin-right: 5px">'+class_type+'</span>\n' +
                    '                <span class="float_l color_gray">'+jsonObj.new_class[i].teacher+'</span>\n' +
                    '                <span class="float_r color_gray"><span>'+jsonObj.new_class[i].student_count+'</span>人在学</span>\n' +
                    '            </div>\n' +
                    '        </div>'
                )
            }
        }
    });
}