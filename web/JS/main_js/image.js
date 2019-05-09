var new_cover;
var contextPath = getContextPath();
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html',function () {
        get_user_infor();
    });
    $('#ui_box').load('ui_box.html',function () {
        getHTML();
        $.when(get_cover()).done(function () {
            new_cover = cover_address;
            $('#cover').attr('src',contextPath+cover_address);
        });
    });
    $('#course_manag_nav').load('course_manag_nav.html',function () {
        addHref();
        addAction(2)
    });
});

function post_cover_image(event) {
    var animateimg = $(event).val(); //获取上传的图片名 带//
    var imgarr=animateimg.split('\\'); //分割
    var myimg=imgarr[imgarr.length-1]; //去掉 // 获取图片名
    var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    var ext = myimg.substring(houzui, myimg.length).toUpperCase();  //切割 . 获取文件后缀
    var file = $(event).get(0).files[0]; //获取上传的文件
    var fileSize = file.size;           //获取上传的文件大小
    var maxSize = 1048576*20;              //最大20MB
    if(ext !=='.PNG' && ext !=='.GIF' && ext !=='.JPG' && ext !=='.JPEG' && ext !=='.BMP'){
        alert('文件类型错误,请上传图片类型');
        return false;
    }else if(parseInt(fileSize) >= parseInt(maxSize)){
        alert('上传的文件不能超过20MB');
        return false;
    }else {
        var fileObj = event.files[0]; // js 获取文件对象
        var formFile = new FormData();
        formFile.append("file", fileObj); //加入文件对象
        $.ajax({
            url: contextPath+"/uploadimage",
            data: formFile,
            type: "POST",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (jsonObj) {
                $('#cover').attr('src',contextPath+jsonObj.head_address);
                new_cover = jsonObj.head_address;
            }
        });
    }
}
function save_cover_image() {
    $.ajax({
        url: contextPath+"/save_image",
        data: {
            action:'set_cover',
            image:new_cover,
            No:No
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            if (jsonObj===true) {
                $('#ui-box-cover').attr('src',contextPath+new_cover);
                $('#radio_cover').attr('src',contextPath+cover_address);
                alert('保存成功');
            }
        }
    });
}