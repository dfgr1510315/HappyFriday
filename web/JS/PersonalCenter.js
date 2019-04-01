var contextPath = getContextPath();
$(document).ready(function () {
    $('#navigation').load('navigation.html',function () {
        get_infor();
    });
});
function get_infor() {
    $.ajax({
        type: "POST",
        url: contextPath+"/changeInfor",
        data: {
            action:'3'
        },
        dataType: 'json',
        success: function (jsonObj) {
            $('#liNike').text('昵称：  '+jsonObj.nike);
            $('#liSex').text('性别：  '+jsonObj.sex);
            $('#liBirth').text('生日：  '+jsonObj.birth);
            $('#liTeacher').text('指导老师：  '+jsonObj.teacher);
            $('#liIntro').text('个人简介：  '+jsonObj.information);
            /* console.log('pc_head_image:'+head_image);
             $('#pc_head_image').attr('src',contextPath+head_image);*/
            $("#nike").val(jsonObj.nike);
            $("#sex").text(jsonObj.sex);
            $("#birth").val(jsonObj.birth);
            $("#teacher").val(jsonObj.teacher);
            $("#introduction").val(jsonObj.information);
            $('#VerticalNav').load('VerticalNav.html',function () {
                addClass(0);
            });
        }
    });
}

function sexChoose(sex) {
    $("#sex").text(sex);
}

function changeInfor() {
    var nike = $.trim($("#nike").val());
    var sex = $.trim($("#sex").text());
    if (sex === '请选择') sex = '';
    var birth = $.trim($("#birth").val());
    var teacher = $.trim($("#teacher").val());
    var introduction = $.trim($("#introduction").val());
    var data = {
        action:'1', //更改用户信息
        ID: user,
        nike: nike,
        sex: sex,
        birth: birth,
        teacher: teacher,
        introduction: introduction
    };
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/changeInfor",
        data: data,
        dataType: 'json',
        success: function (msg) {
            if (2 === msg) {
                $("#ChangeClose").click();
                $("#liNike").text("昵称：       " + nike);
                $("#liSex").text("性别：        " + sex);
                $("#liBirth").text("生日：        " + birth);
                $("#liIntro").text("简介：        " + introduction);
                $("#liTeacher").text("指导老师：" + teacher);
            }
            else {
                alert("数据更新失败")
            }
        }
    });
}

function post_head_image(event) {
    var animateimg = $(event).val(); //获取上传的图片名 带//
    var imgarr=animateimg.split('\\'); //分割
    var myimg=imgarr[imgarr.length-1]; //去掉 // 获取图片名
    var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    var ext = myimg.substring(houzui, myimg.length).toUpperCase();  //切割 . 获取文件后缀
    var file = $(event).get(0).files[0]; //获取上传的文件
    var fileSize = file.size;           //获取上传的文件大小
    var maxSize = 1048576;              //最大1MB
    if(ext !=='.PNG' && ext !=='.GIF' && ext !=='.JPG' && ext !=='.JPEG' && ext !=='.BMP'){
        alert('文件类型错误,请上传图片类型');
        return false;
    }else if(parseInt(fileSize) >= parseInt(maxSize)){
        alert('上传的文件不能超过1MB');
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
            async: true,
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (jsonObj) {
                $('#pc_head_image').attr('src',contextPath+jsonObj.head_address);
                $('#head_image').attr('src',contextPath+jsonObj.head_address);
                $('#radio_cover').attr('src',contextPath+head_image);
                $.ajax({
                    url: contextPath+"/save_image",
                    data: {
                        action:'set_head',
                        image: jsonObj.head_address
                    },
                    type: "POST",
                    dataType: "json",
                    async: true
                });
            }
        });

    }
}