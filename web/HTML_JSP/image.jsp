<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/10/23
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>课程管理</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/navigation.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Myclass.css">
    <link rel="stylesheet" type="text/css" href="../webuploader-0.1.5/css/webuploader.css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../webuploader-0.1.5/jekyll/js/webuploader.js"></script>
    <script type="text/javascript" src="../wangEditor-3.1.1/release/wangEditor.min.js"></script>
    <script type="text/javascript" src="../JS/LoginPC.js"></script>
    <script type="text/javascript" src="../JS/Myclass.js"></script>
    <script type="text/javascript" src="../JS/drag.js"></script>
</head>
<style type="text/css">
    body.modal-open {
        overflow-y: auto !important;
        padding-right: 0!important;
    }
</style>

<body onload="ifActive();addAction(2);addHref();getHTML()">
<jsp:include page="navigation.html"/>
<div style="width: 100%;margin-top:30px;height: 450px">
    <div style="background-size: cover;height: 148px;margin-top: -21px">
        <jsp:include page="ui_box.html"/>
        <div style="width: 80%;margin: auto">
            <jsp:include page="course_manag_nav.html"/>
            <div class="container_right">
                <h3 class="container_right_head">
                    基本信息
                </h3>
                <table class="table">
                    <tr>
                        <td style="width: 80%;text-align: center;">
                            <div class="img">
                                <img  src="" alt="" id="cover" style="height: 200px;">
                            </div>
                            <!--<input type="file" id="file" style="background: orange;border: 1px;width: 100px;height:100px;border-radius: 50%;float: left;">-->

                            <div style="text-align: center; margin-top: 7px">
                                <label for="head_iamge" class=" btn btn-primary" style="font-size: 12px">上传</label>
                                <input id="head_iamge" type="file" style="display:none" onchange="post_cover_image(this)">
                            </div>

                            <div style="border: 0 solid red;margin-top: 10px;">
                                <span style="margin-left: 30px;margin-top: 10px;color: red;">您可以上传jpg,gif,png格式的文件，图片建议尺寸至少为480x300，文件大小不能超过20MB</span>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" style="text-align: center;"><button type="button" class="btn btn-primary" onclick="save_cover_image()" >保存</button></td>
                    </tr>

                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var new_cover = cover_address;
    $(document).ready(function(){
        $('#cover').attr('src',"${pageContext.request.contextPath}"+cover_address);
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
                url: "${pageContext.request.contextPath}/uploadimage",
                data: formFile,
                type: "POST",
                dataType: "json",
                async: false,
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                success: function (jsonObj) {
                    $('#cover').attr('src',"${pageContext.request.contextPath}"+jsonObj.head_address);
                    new_cover = jsonObj.head_address;
                }
            });
        }
    }
    function save_cover_image() {
        $.ajax({
            url: "${pageContext.request.contextPath}/save_image",
            data: {
                action:'set_cover',
                image:new_cover,
                No:No
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                if (jsonObj.msg==='1') {
                    $('#ui-box-cover').attr('src',"${pageContext.request.contextPath}"+new_cover);
                    $('#radio_cover').attr('src',"${pageContext.request.contextPath}"+cover_address);
                    alert('保存成功');
                }
            }
        });
    }
</script>
</body>

</html>
