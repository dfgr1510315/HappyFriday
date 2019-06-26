let new_cover;
let No = GetQueryString('class_id');
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html');
    $('#course_manag_nav').load('course_manag_nav.html',function () {addAction(2)});
    new_cover = cookie.get('class_cover'+No);
    $('#cover').attr('src',contextPath+new_cover);
});

function post_cover_image(event) {
    let animateimg = $(event).val(); //获取上传的图片名 带//
    let imgarr=animateimg.split('\\'); //分割
    let myimg=imgarr[imgarr.length-1]; //去掉 // 获取图片名
    let houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    let ext = myimg.substring(houzui, myimg.length).toUpperCase();  //切割 . 获取文件后缀
    let file = $(event).get(0).files[0]; //获取上传的文件
    let fileSize = file.size;           //获取上传的文件大小
    let maxSize = 1048576*20;              //最大20MB
    if(ext !=='.PNG' && ext !=='.GIF' && ext !=='.JPG' && ext !=='.JPEG' && ext !=='.BMP'){
        alert('文件类型错误,请上传图片类型');
        return false;
    }else if(parseInt(fileSize) >= parseInt(maxSize)){
        alert('上传的文件不能超过20MB');
        return false;
    }else {
        let fileObj = event.files[0]; // js 获取文件对象
        let formFile = new FormData();
        formFile.append("type", 'image');
        formFile.append("file", fileObj); //加入文件对象
        $.ajax({
            url: contextPath+"/UploadFiles.do",
            data: formFile,
            type: "POST",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (jsonObj) {
               // console.log(jsonObj);
                $('#cover').attr('src',contextPath+jsonObj.file_add);
                new_cover = jsonObj.file_add;
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
            //console.log(jsonObj);
            if (jsonObj===1) {
                cookie.set('class_cover'+No,new_cover);
                $('#ui-box-cover').attr('src',contextPath+new_cover);
                $('#radio_cover').attr('src',contextPath+cookie.get('class_cover'+No));
                alert('保存成功');
            }else alert('保存失败')
        }
    });
}