let user = cookie.get('user');
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(0);});
    get_infor();
});

function get_infor() {
    $.ajax({
        type: "POST",
        url: contextPath + "/changeInfor",
        data: {
            action: '3'
        },
        dataType: 'json',
        success: function (jsonObj) {
            $('#liNike').text('昵称：  ' + jsonObj.user_infor.nike);
            $('#liSex').text('性别：  ' + jsonObj.user_infor.sex);
            $('#liBirth').text('生日：  ' + jsonObj.user_infor.birth);
            $('#liTeacher').text('指导老师：  ' + jsonObj.user_infor.teacher);
            $('#liIntro').text('个人简介：  ' + jsonObj.user_infor.information);
            $("#nike").val(jsonObj.user_infor.nike);
            $("#sex").text(jsonObj.user_infor.sex);
            $("#birth").val(jsonObj.user_infor.birth);
            $("#teacher").val(jsonObj.user_infor.teacher);
            $("#introduction").val(jsonObj.user_infor.information);
            $('#pc_head_image').attr('src', contextPath + cookie.get('head_image'));
        }
    });
}

function sexChoose(sex) {
    $("#sex").text(sex);
}

function changeInfor() {
    let nike = $.trim($("#nike").val());
    let sex = $.trim($("#sex").text());
    if (sex === '请选择') sex = '';
    let birth = $.trim($("#birth").val());
    let teacher = $.trim($("#teacher").val());
    let introduction = $.trim($("#introduction").val());
    let data = {
        action: '1', //更改用户信息
        ID: user,
        nike: nike,
        sex: sex,
        birth: birth,
        teacher: teacher,
        introduction: introduction
    };
    $.ajax({
        type: "POST",
        url: contextPath + "/changeInfor",
        data: data,
        dataType: 'json',
        success: function (msg) {
            if (true === msg) {
                $("#ChangeClose").click();
                $("#liNike").text("昵称：       " + nike);
                $("#liSex").text("性别：        " + sex);
                $("#liBirth").text("生日：        " + birth);
                $("#liIntro").text("简介：        " + introduction);
                $("#liTeacher").text("指导老师：" + teacher);
            } else {
                alert("数据更新失败")
            }
        }
    });
}

function post_head_image(_blob) {
    //let animateimg = $(event).val(); //获取上传的图片名 带//
    //let imgarr=animateimg.split('\\'); //分割
    //let myimg=imgarr[imgarr.length-1]; //去掉 // 获取图片名
    //let houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    //let ext = myimg.substring(houzui, myimg.length).toUpperCase();  //切割 . 获取文件后缀
    //let file = $(event).get(0).files[0]; //获取上传的文件
    /*    let ext = _blob.type;*/
    //let fileSize = _blob.size;           //获取上传的文件大小
    // let maxSize = 1048576;              //最大1MB
    /*if(ext !=='image/png' && ext !=='image/gif' && ext !=='image/jpg' && ext !=='image/jpge' && ext !=='image/bmp'){
        alert('文件类型错误,请上传图片类型');
        return false;
    }else */
    let formFile = new FormData();
    formFile.append("path", "image"); //加入文件对象
    formFile.append("file", _blob); //加入文件对象
    $.ajax({
        url: contextPath + "/UploadFiles.do",
        data: formFile,
        type: "POST",
        dataType: "json",
        cache: false,//上传文件无需缓存
        processData: false,//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function (jsonObj) {
            $('#pc_head_image').attr('src', contextPath + jsonObj.file_add);
            $('#head_image').attr('src', contextPath + jsonObj.file_add);
            $('#radio_cover_img').attr('src', contextPath + jsonObj.file_add);
            $('#radio_cover').attr('src', contextPath + head_image);
            cookie.set('head_image', jsonObj.file_add);
            $.ajax({
                url: contextPath + "/save_image",
                data: {
                    action: 'set_head',
                    image: jsonObj.file_add
                },
                type: "POST",
                dataType: "json",
                success: function (msg) {
                    if (msg === true) {
                        alert('修改成功');
                        $('#am-close_a').click();
                    }
                    else alert('修改失败')
                }
            });
        }
    });

}