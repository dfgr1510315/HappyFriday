$(document).ready(function(){
        $("#up-img-touch").click(function(){
        		  $("#doc-modal-1").modal({width:'600px'});
        });
});
$(function() {
    'use strict';
    // 初始化
    let $image = $('#image');
    $image.cropper({
        aspectRatio: '1',
        autoCropArea:0.8,
        preview: '.up-pre-after'
    });

    // 事件代理绑定事件
    $('.docs-buttons').on('click', '[data-method]', function() {
   
        let $this = $(this);
        let data = $this.data();
        let result = $image.cropper(data.method, data.option, data.secondOption);
        switch (data.method) {
            case 'getCroppedCanvas':
            if (result) {
                // 显示 Modal
                $('#cropped-modal').modal().find('.am-modal-bd').html(result);
                $('#download').attr('href', result.toDataURL('image/jpeg'));
            }
            break;
        }
    });

    // 上传图片
    let $inputImage = $('#inputImage');
    let URL = window.URL || window.webkitURL;
    let blobURL;

    if (URL) {
        $inputImage.change(function () {
            let files = this.files;
            let file;

            if (files && files.length) {
               file = files[0];
               console.log('file.size:'+file.size);

               if (/^image\/\w+$/.test(file.type)) {
                   if (file.size>1048576) {
                       alert('上传的文件不能超过1MB');
                       return;
                   }
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {
                        // Revoke when load complete
                       URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                    window.alert('请选择图片文件');
                    return;
                }
            }

            // Amazi UI 上传文件显示代码
            let fileNames = '';
            $.each(this.files, function() {
                fileNames += '<span class="am-badge">' + this.name + '</span> ';
            });
            $('#file-list').html(fileNames);
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }
    
    //绑定上传事件
    $('#up-btn-ok').on('click',function(){
    	let $modal = $('#my-modal-loading');
    	let $modal_alert = $('#my-alert');
    	let img_src=$image.attr("src");
    	if(img_src===""){
    		set_alert_info("没有选择上传的图片");
    		$modal_alert.modal();
    		return false;
    	}
    	
    	$modal.modal();

    	let canvas=$("#image").cropper('getCroppedCanvas');
    	let data=canvas.toDataURL(); //转成base64
        let _blob = dataURLtoBlob(data);
        post_head_image(_blob);

      /*  $.ajax( {
                url:contextPath+"/uploadimage",
                dataType:'json',  
                type: "POST",  
                data: {"image":data.toString()},  
                success: function(data, textStatus){
                	$modal.modal('close');
                	set_alert_info(data.result);
                	$modal_alert.modal();
                	if(data.result==="ok"){
                		$("#up-img-touch img").attr("src",data.file);
                	
                		let img_name=data.file.split('/')[2];
                		console.log(img_name);
                		$("#pic").text(img_name);
                	}
                },
                error: function(){
                	$modal.modal('close');
                	set_alert_info("上传文件失败了！");
                	$modal_alert.modal();
                	//console.log('Upload error');  
                }  
         });  */
    	
    });
    
});

// 图片 base64 url 转 blob
function dataURLtoBlob(dataurl) {
    let arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    console.log('mime'+mime);
    return new Blob([u8arr], { type: 'image/jpeg' });
}

function rotateimgright() {
$("#image").cropper('rotate', 90);
}


function rotateimgleft() {
$("#image").cropper('rotate', -90);
}

function set_alert_info(content){
	$("#alert_content").html(content);
}



 
