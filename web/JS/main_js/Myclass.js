let UnitCount = 0;
let ClassCount = 0;
let files_flag = 0;
let files_list;
let files_count = 0;
let filesName = [];
let filesSize = [];
let No = GetQueryString('class_id');
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html');
    $('#course_manag_nav').load('course_manag_nav.html',function () {addAction(0)});
    get_class_content()
});

function batch_upload(event) {
    let batch_bar = $("#batch_bar");
    let batch_span = $("#batch_span");
    let files =event.files; //获取当前选择的文件对象
    if (files===undefined) return;
    batch_bar.css('display','block');
    for(let m=0;m<files.length;m++){ //循环添加进度条
        filesName.push(files[m].name);
        if (files[m].size> 1024 * 1024){
            filesSize.push( (Math.round(files[m].size /(1024 * 1024))).toString() + 'MB');
        }
        else{
            filesSize.push((Math.round(files[m].size/1024)).toString() + 'KB');
        }
    }
    sendAjax();
    function sendAjax() {
        if (files_count>=files.length){
            $('#batch_file').val('');
            files_count = 0;
            filesName = [];
            filesSize = [];
            batch_bar.css('display','none');
            batch_span.text('');
            alert('上传完成');
            return
        }
        let formData = new FormData();
        formData.append('action','file');//文件上传,告诉服务器不需转码
        formData.append('class_id',No);
        formData.append('files',files[files_count]);
        $.ajax({
            url: contextPath+"/uploadsec",
            type:'POST',
            cache: false,
            data: formData,//文件以formData形式传入
            processData : false,
            //必须false才会自动加上正确的Content-Type
            contentType : false ,
            xhr: function(){   //监听用于上传显示进度
                let xhr = $.ajaxSettings.xhr();
                if(batch_onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress" , batch_onprogress, false);
                    return xhr;
                }
            } ,
            success:function(jsonObj){
                console.log(jsonObj);
                let data1=eval("("+jsonObj+")");
                console.log(data1.src);
                console.log(data1.filename);
                let tbody = $('#file_box tbody');
                add_file_list(tbody,data1.src,data1.filename);
                $("#batch_bar").width('0%');//移除进度条样式
                files_count++; //递归条件
                sendAjax();
                //   }
            },
            error:function(xhr){
                alert("上传出错");
            }
        })
    }
}
function batch_onprogress(evt){
    let loaded = evt.loaded;   //已经上传大小情况
    let tot = evt.total;   //附件总大小
    let per = Math.floor(100*loaded/tot); //已经上传的百分比
    $("#batch_span").text('正在上传\''+filesName[files_count]+'\' '+filesSize[files_count]);
    $("#batch_bar").width(per+"%");
}

//关闭WebSocket连接
function closeWebSocket(websocket) {
    websocket.close();
}

//发送消息
function send(websocket) {
    let message = document.getElementById('text').value;
    websocket.send(message);
}

function saveClass() {
    let unit_no = 0;
    let class_no = 0;
    let Total_data = {
        UUNIt:[]
    };
    for (let i=1;i<=UnitCount;i++){
        let flag  = $("#collapse"+i);
        if (flag.length===0) break;
        unit_no++;
        let j=0;
        let Unit = {
            Unit_Name:flag.prev().children("h8").text(),
            Class:[]
        };
        while (flag.children("div").eq(j).html()!==undefined){
            let source_video_name = flag.children("div").eq(j).children('div').eq(1).find('label').eq(0).text();
            if ('选择视频'===source_video_name)   source_video_name='';
            let state = 0;
            if (flag.children().eq(j).find('button:first').text()==='已发布') state = 1;
            console.log(flag.prev().children("h8").text());
            let Class = {
                //Unit_Name:flag.prev().children("h8").text(),
                Class_Name:flag.children("div").eq(j).children('div').eq(0).children('h10').text(),
                Video_Src:flag.children("div").eq(j).children('div').eq(1).find('video').attr('src'),
                Source_Video_Name:source_video_name,
                Source_Video_Src:flag.children("div").eq(j).children('div').eq(1).find('video').data('cusrc'),
                Editor:flag.children("div").eq(j).children('div').eq(1).find('video').parent().parent().next().children().eq(1).children().html(),
                State:state
            };
            let File_Href="";
            let File_Name="";
            flag.children("div").eq(j).children('div').eq(1).find('video').parent().parent().next().next().find('a').each(function(){
                File_Href=File_Href+$(this).attr('href')+'|';
                File_Name=File_Name+this.text+'|';
            });
            Class.File_Href = File_Href;
            Class.File_Name = File_Name;
            Class.Serial_No = i+"-"+(j+1);
            Unit.Class.push(Class);
            //alert(Unit.Class[j].Serial_No);
            j++;
            class_no++;
            //alert("第0个是"+Unit.Class[0].Class_Name)
        }
        Total_data.UUNIt.push(Unit);
        //alert(Total_data.UUNIt[i-1].Class[0].Video_Src);
    }

    $.ajax({
        type: "POST",
        url: contextPath+"/SaveClassInfor",
        data: {
            Read_or_Save: "save",
            No: No,
            ClassCount: class_no,
            UnitCount: unit_no
        },
        dataType: 'json',
        success: function (msg) {
            if (msg===true) {
                $.ajax({
                    type: "POST",
                    url: contextPath +"/getlearnfile",
                    data: {
                        No:No,
                        action:'post',
                        ds:JSON.stringify(Total_data.UUNIt)
                    },
                    dataType: 'json',
                    success: function (msg) {
                        if (msg===true) {
                            alert("保存成功");
                        }else alert('保存失败')
                    }
                });
            }else alert('保存失败')
        }
    });
}

function add_collapse(collapse,lesson_title,source_video_title,source_video_address,video_address,release_status,i) {
    let status_text = '发布';
    let status_class = 'btn-success';
    if (release_status===1)   {
        status_text= '已发布';
        status_class = 'btn-warning';
    }
    collapse.append(
        '<div class="card Unit_class" draggable="false" >' +
        '   <div class="card-header">' +
        '       <span class="badge badge-secondary badge-pill">课时</span>' +
        '       <h10>'+lesson_title+'</h10>' +
        '       <div class="btn-group" style="float:right">\n' +
        '           <button type="button" class="btn '+status_class+' btn-sm" onclick="Change_Release_Statu(this)">'+status_text+'</button>\n' +
        '           <button type="button" class="btn btn-secondary btn-sm"  data-toggle="modal"  data-target="#Change_class">更改名称</button>\n' +
        '           <button type="button" class="btn btn-secondary btn-sm" data-toggle="modal"  data-target="#Delete_Class">删除课时</button>\n' +
        '           <button id="hour_button_collapse'+(i+1)+'" type="button" class="btn btn-secondary card-link btn-sm collapsed" data-toggle="collapse" href="#hour_collapse'+(i+1)+'">折叠</button>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div id="hour_collapse'+(i+1)+'" class="collapse" >\n' +
        '       <div class="card-body">\n' +
        '           <div class="container">\n' +
        '               <ul class="nav nav-tabs">\n' +
        '                   <li class="nav-item ">\n' +
        '                       <a class="nav-link active" href="javascript:void(0)" onclick="Video_TEXT_FILE(this,1)">视频</a>\n' +
        '                   </li>\n' +
        '                   <li class="nav-item ">\n' +
        '                       <a class="nav-link" href="javascript:void(0)"  onclick="Video_TEXT_FILE(this,2)">图文</a>' +
        '                   </li>\n' +
        '                   <li class="nav-item">\n' +
        '                       <a class="nav-link" href="javascript:void(0)" onclick="Video_TEXT_FILE(this,3)">文件</a>\n' +
        '                   </li>\n' +
        '               </ul>\n' +
        '               <div class="input-file-show" style="">\n' +
        '                   <div>' +
        '                       <div class="custom-file mb-3" >\n' +
        '                           <input type="file" class="custom-file-input" accept="video/*" onchange ="upload(this,1)">\n' +
        '                           <label class="custom-file-label" style="overflow:hidden">'+source_video_title+'</label>\n' +
        '                       </div>\n' +
        '                       <button type="button" class="btn btn-secondary" style="margin-top:4px;margin-left:10px" onclick="delete_video(this)">删除视频</button>\n' +
        '                   </div>\n' +
        '                   <div data-percent=""  class="progressbar">\n' +
        '                       <span class="bfb_span"></span>\n' +
        '                   </div>\n' +
        '                   <div>\n' +
        '                       <video height="180px" width="320px" data-cuSRC="'+source_video_address+'" controls="controls" src="'+video_address+'" type="video/*" />\n' +
        '                   </div>\n' +
        '               </div>' +
        '               <div id="editor'+(i+1)+'" style="display:none;z-index:0"></div>\n' +
        '               <div  class="custom-file mb-3" style="margin-top:25px;height:auto;display:none">' +
        '                   <div style="display: inline-block;width:70%">' +
        '                       <input type="file" class="custom-file-input" onchange ="upload(this,3)">\n' +
        '                       <label class="custom-file-label" style="width:70%">选择文件</label>\n' +
        '                   </div>' +
        '                   <button type="button" class="btn btn-secondary btn-sm" data-toggle="modal"  data-target="#files_bank" style="margin-top:4px;" onclick="get_files(this)">从素材库中选择</button>\n' +
        '                   <div class="list-group" id="file_list'+i+'">\n' +
        '                   </div>\n' +
        '               </div>\n' +
        '           </div>\n' +
        '       </div>\n' +
        '   </div> \n' +
        '</div>');
    let E = window.wangEditor;
    new E('#editor' + (i+1)).create();
}

function get_class_content() {
    $.ajax({
        type: "POST",
        url:contextPath+"/getlearnfile",
        data: {
            No:No,
            action:'get'
        },
        dataType: 'json',
        success: function (jsonObj) {
            //console.log(jsonObj);
            let unit_titles= [];
            for (let i=0;i<jsonObj.class_content.length;i++) {
                if (-1===unit_contain(unit_titles,jsonObj.class_content[i].unit_title)) unit_titles.push(jsonObj.class_content[i].unit_title);
            }
            for (i=0;i<unit_titles.length;i++){
                add_unit(unit_titles[i],i);
            }
            UnitCount = unit_titles.length;
            ClassCount = jsonObj.class_content.length;
            console.log('ClassCount'+ClassCount);
            console.log('UnitCount'+UnitCount);
            for (i=0;i<jsonObj.class_content.length;i++){
                let belong_unit = unit_contain(unit_titles,jsonObj.class_content[i].unit_title)+1;
                let source_video_title = '选择视频';
                if (jsonObj.class_content[i].source_video_title!=='') source_video_title = jsonObj.class_content[i].source_video_title;
                add_collapse($('#collapse'+belong_unit),jsonObj.class_content[i].lesson_title,source_video_title,jsonObj.class_content[i].source_video_address,jsonObj.class_content[i].video_address,jsonObj.class_content[i].release_status,i);
                let file_address = jsonObj.class_content[i].file_address.split('|');
                let file_name = jsonObj.class_content[i].file_name.split('|');
                let file_list = $('#file_list'+i);
                for (let j=0;j<file_address.length;j++){
                    if (file_address[j]==='') break;
                    add_file(file_list,file_address[j],file_name[j]);
                }
            }
        }
    });
}

function add_file(file_list,file_add,file_name) {
    file_list.append(
        '<div class="list-group-item list-group-item-action">\n' +
        '   <a style="text-decoration:none;" target="_Blank" href="'+file_add+'">'+file_name+'</a>\n' +
        '   <button type="button" class="btn btn-light" style="float:right;padding:0;width:26px" onclick="Delete_File(this)"><i class="fa fa-times"></i></button>\n' +
        '</div>'
    )
}

function add_unit(unit_title,collapse_id) {
    $(".sections").append(
        '<div class="card section Unit" draggable="false">\n' +
        '   <div class="card-header"><span class="badge badge-pill badge-primary">章节</span>\n' +
        '       <h8>'+unit_title+'</h8>\n' +
        '       <div class="btn-group" style="float:right">\n' +
        '           <button type="button" class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#add_class_hour" onclick="cancel_drag()">增加课时</button>\n' +
        '           <button type="button" class="btn btn-outline-primary btn-sm"  data-toggle="modal"  data-target="#Change_section">更改名称</button>\n' +
        '           <button type="button" class="btn btn-outline-primary btn-sm" data-toggle="modal"  data-target="#Delete_Unit">删除章节</button>\n' +
        '           <button id="Button_collapse'+(collapse_id+1)+'" type="button" class="btn btn-outline-secondary btn-sm ry card-link" data-toggle="collapse" href="#collapse'+(collapse_id+1)+'">折叠</button>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div id="collapse'+(collapse_id+1)+'" class="collapse show" ></div>\n' +
        '</div>\n'
    );
}

function unit_contain(unit_titles,unit_title) {
    for (let i=0;i<unit_titles.length;i++)
    {
        if (unit_titles[i]===unit_title) return i
    }
    return -1
}

function live() {
    event.returnValue = '确认是否保存';
}

function Release(state) {
        $.ajax({
            url: contextPath+"/SaveClassInfor",
            data: {
                No:No,
                state:state,
                Read_or_Save:'release'
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                if (jsonObj===true) {
                    if (state===1) {
                        $("#Open_curriculum_Close").click();
                        $("#curriculum_button").removeClass('btn-outline-primary').addClass('btn-success').text('已发布').attr('data-target','#Close_curriculum');
                    }else {
                        $("#Close_curriculum_Close").click();
                        $("#curriculum_button").removeClass('btn-success').addClass('btn-outline-primary').text('发布课程').attr('data-target','#Open_curriculum');
                    }
                }else alert('修改状态失败')
            }
        });
}

/*function Change_Unit_Fun(UnitCount, chooseCount) {
   /!* alert("UnitCount:" + UnitCount + " ChooseCount:" + chooseCount);*!/
    let obj = document.getElementById("collapse" + chooseCount);
    obj.setAttribute("id", "collapse" + UnitCount);

    obj = document.getElementById("Button_collapse" + chooseCount);
    obj.setAttribute("id", "Button_collapse" + UnitCount);
    obj.setAttribute("href", "#collapse" + UnitCount);

    obj = document.getElementById("button_add_class" + chooseCount);
    obj.setAttribute("data-id", "button_add_class" + UnitCount);
    obj.setAttribute("id", "button_add_class" + UnitCount);

    obj = document.getElementById("button_Change_Unit" + chooseCount);
    obj.setAttribute("data-id", "button_Change_Unit" + UnitCount);
    obj.setAttribute("id", "button_Change_Unit" + UnitCount);

    obj = document.getElementById("button_Delete_Unit" + chooseCount);
    obj.setAttribute("data-id", "button_Delete_Unit" + UnitCount);
    obj.setAttribute("id", "button_Delete_Unit" + UnitCount);
}

function Change_Class_Fun(ClassCount, chooseCount) {
    //alert("ClassCount:" + ClassCount + " ChooseCount:" + chooseCount);
    let obj = document.getElementById("hour_button_collapse" + chooseCount);
    obj.setAttribute("id", "hour_button_collapse" + ClassCount);
    obj.setAttribute("href", "#hour_collapse" + ClassCount);

    obj = document.getElementById("hour_collapse" + chooseCount);
    obj.setAttribute("id", "hour_collapse" + ClassCount);

    obj = document.getElementById("button_Change_Class" + chooseCount);
    obj.setAttribute("id", "button_Change_Class" + ClassCount);
    obj.setAttribute("data-id", "button_Change_Class" + ClassCount);

    obj = document.getElementById("button_Delete_Class" + chooseCount);
    obj.setAttribute("id", "button_Delete_Class" + ClassCount);
    obj.setAttribute("data-id", "button_Delete_Class" + ClassCount);

    obj = document.getElementById("editor" + chooseCount);
    obj.setAttribute("id", "editor" + ClassCount);
}*/

function addSection() {
    let unit_name = $("#unit_name");
    add_unit(unit_name.val(),UnitCount);
    UnitCount = UnitCount + 1;
    //Change_Unit_Fun(UnitCount, "");
    unit_name.val('');
    //document.getElementById("unit_name").autofocus; 无效果
    $("#unitClose").click();
}

function add_class_hour(add_id) {
    let class_name = $("#class_name");
    add_collapse($('#'+add_id),class_name.val(),'选择视频','','',0,ClassCount);
  /*  $("#hour_collapse" ).prev().children("h10").text(class_name.val());
    /!*alert(($(add_id).parent().parent().parent().next().children("div:last-child").children("div:first-child").children("div:last-child").attr('id')));*!/
    let Next_Class_id = $(add_id).parent().parent().parent().next().children("div:last-child").children("div:first-child").children("div:last-child").attr('id');
    if (Next_Class_id!=null){
        let Next_Class_No = parseInt(Next_Class_id.slice(-1));
        let i = ClassCount-1;
        for(i;i>=Next_Class_No;i--){
            //Change_Class_Fun(i+1,i);
        }
        let E = window.wangEditor;
        let editor = new E('#editor' + Next_Class_No);
        //Change_Class_Fun(Next_Class_No, "");
    }else{
        //Change_Class_Fun(ClassCount,"");
        E = window.wangEditor;
        editor = new E('#editor' + ClassCount);
    }
    editor.create();*/
    class_name.val('');
    ClassCount = ClassCount + 1;
    $("#class_Close").click();
}

function Change_Unit_Name(Change_button_id) {
    let text = $("#Change_unit_name");
    $('#'+Change_button_id).prev().children().eq(1).text(text.val());
    text.val('');
    $("#Change_section_Close").click();
}

function Change_Class_Name(Change_button_id) {
    let text = $("#Change_class_name");
    $('#'+Change_button_id).prev().children().eq(1).text(text.val());
    text.val('');
    $("#Change_Class_Close").click();
}

function Delete_Unit(Delete_button_id) {
    $('#'+Delete_button_id).parent().remove();
    $("#Delete_Unit_Close").click();
}

function Delete_Class(Delete_button_id) {
    $('#'+Delete_button_id).parent().remove();
    $("#Delete_Class_Close").click();
}

function delete_video(event){
    //console.log('val:'+$(event).prev().children().val());
    $(event).prev().children().val('');
    $(event).parent().next().next().children().attr("src","");
    $(event).parent().next().next().children().attr("data-cusrc","");
    $(event).prev().find('label').text('选择视频');
}

$(function () {
    $('#add_class_hour').on('show.bs.modal', function (event) {
        $('#Add_sure_button').attr('onclick','add_class_hour("'+$(event.relatedTarget).parent().parent().next().attr('id')+'")')
    })
});

$(function () {
    $('#Change_section').on('show.bs.modal', function (event) {
        $("#Change_unit_name").val($(event.relatedTarget).parent().prev().text());
        $('#Change_sure_button').attr('onclick','Change_Unit_Name("'+$(event.relatedTarget).parent().parent().next().attr('id')+'")')
    })
});

$(function () {
    $('#Change_class').on('show.bs.modal', function (event) {
        $("#Change_class_name").val($(event.relatedTarget).parent().prev().text());
        $('#Change_class_sure_button').attr('onclick','Change_Class_Name("'+$(event.relatedTarget).parent().parent().next().attr('id')+'")')
    })
});

$(function () {
    $('#Delete_Unit').on('show.bs.modal', function (event) {
        $('#Delete_Unit_sure_button').attr('onclick','Delete_Unit("'+$(event.relatedTarget).parent().parent().next().attr('id')+'")')
    })
});

$(function () {
    $('#Delete_Class').on('show.bs.modal', function (event) {
        $('#Delete_Class_sure_button').attr('onclick','Delete_Class("'+$(event.relatedTarget).parent().parent().next().attr('id')+'")')
    })
});

function onprogress(evt){
    let loaded = evt.loaded;                  //已经上传大小情况
    let tot = evt.total;                      //附件总大小
    let per = Math.floor(100*loaded/tot);     //已经上传的百分比
    local_bar.children().html( per +'%'+'('+loaded+'/'+tot+')' );
    local_bar.css('width' , per +'%');
    if (per===100)  local_bar.css('display','none');
}

function upload(event, type) {
    let fileObj = event.files[0]; // js 获取文件对象
    /*if ("undefined" === typeof (fileObj) || fileObj.size <= 0) {
        alert("请选择图片");
        return;
    }*/
    if (fileObj===undefined) return;
    if (type===1&&parseInt(fileObj.size)/1024>100000) {
        alert('上传的视频不能大于100M');
        return;
    }
    let formFile = new FormData();
    formFile.append("action", 'vedio');
    formFile.append("file", fileObj); //加入文件对象
    let data = formFile;
    let local_bar = $(event).parent().parent().next();
    let websocket = null;
    $.ajax({
        url: contextPath+"/uploadsec",
        data: data,
        type: "POST",
        dataType: "json",
        cache: false,//上传文件无需缓存
        processData: false,//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        xhr: function(){
            //取得xmlhttp异步监听
            let xhr = $.ajaxSettings.xhr();
            local_bar.css('display','block');
            if(onprogress && xhr.upload) {
                xhr.upload.addEventListener('progress' , function (evt) {
                    let loaded = evt.loaded;                  //已经上传大小情况
                    let tot = evt.total;                      //附件总大小
                    let per = Math.floor(100*loaded/tot);     //已经上传的百分比
                    local_bar.children().html( per +'%'+'('+loaded+'/'+tot+')' );
                    local_bar.css('width' , per +'%');
                    if (per===100)  {
                        local_bar.css('width','0');
                        //判断当前浏览器是否支持WebSocket
                        if ('WebSocket' in window) {
                            websocket = new WebSocket("ws:"+window.location.host+contextPath+"/websocket/"+user+fileObj.name);
                            //websocket = new WebSocket("ws://localhost:8080/websocket/"+document.getElementById('user').value+fileObj.name);
                        }
                        else {
                            alert('当前浏览器 Not support websocket')
                        }
                        //连接发生错误的回调方法
                        websocket.onerror = function () {
                            console.log("WebSocket连接发生错误");
                        };
                        //连接成功建立的回调方法
                        websocket.onopen = function () {
                            console.log("WebSocket连接成功");
                        };
                        //接收到消息的回调方法
                        websocket.onmessage = function (event) {
                            local_bar.children().html('正在进行转码'+event.data+'%');
                            local_bar.css('width' , event.data +'%');
                            //console.log(event.data);
                        };
                        //连接关闭的回调方法
                        websocket.onclose = function () {
                            //local_bar.css('display','none');
                            console.log("WebSocket连接关闭");
                        };
                        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
                        window.onbeforeunload = function () {
                            closeWebSocket(websocket);
                        };
                    }
                }, false);
                return xhr;
            }
        },
        success: function (jsonObj) {
            //alert(jsonObj.src);
            if (1 === type) {
                //alert(contextPath+"/" + jsonObj.src);
                $(event).parent().parent().next().next().children().attr("data-cuSRC", contextPath+"/" + jsonObj.cuSRC);
                $(event).parent().parent().next().next().children().attr("src", contextPath+"/" + jsonObj.src);
                $(event).next().text(jsonObj.video_name);
            } else if (3 === type) {
                add_file($(event).next().next(),contextPath+"/" + jsonObj.src,jsonObj.filename);
            }
            local_bar.css('display','none');
            closeWebSocket(websocket);
            /* $("#video").val(result.data.file);*/
        }
    })
}

function Delete_File(event) {
    $(event).parent().remove();
}

/*function new_Editor() {
    for (let i = 1; i <= ClassCount; i++) {
        let editor_ID = $('#editor'+i);
        let editor_ID_HTML = editor_ID.find('div:last').html();
        editor_ID.empty();
        let E = window.wangEditor;
        let editor = new E('#editor' + i);
        editor.create();
        editor_ID.find('div:last').empty();
        editor_ID.find('div:last').append(editor_ID_HTML);
    }
}*/

function Video_TEXT_FILE(event, type) {
    if (1 === type) { //显示视频
        $(event).parent().next().children().removeClass("active");
        $(event).parent().next().next().children().removeClass("active");
        $(event).addClass("active");
        $(event).parent().parent().next().css("display", "block");
        $(event).parent().parent().next().next().css("display", "none");
        $(event).parent().parent().next().next().next().css("display", "none");
    }
    if (2 === type) {
        $(event).parent().prev().children().removeClass("active");
        $(event).parent().next().children().removeClass("active");
        $(event).addClass("active");
        $(event).parent().parent().next().css("display", "none");
        $(event).parent().parent().next().next().css("display", "block");
        $(event).parent().parent().next().next().next().css("display", "none");
    }
    if (3 === type) {
        $(event).parent().prev().children().removeClass("active");
        $(event).parent().prev().prev().children().removeClass("active");
        $(event).addClass("active");
        $(event).parent().parent().next().next().next().css("display", "block");
        $(event).parent().parent().next().next().css("display", "none");
        $(event).parent().parent().next().css("display", "none");
    }
}

function Change_Release_Statu(event) {
    if ($(event).text()==="发布"){
        $(event).removeClass("btn-success").addClass("btn-warning").text("已发布");
    }else {
        $(event).removeClass("btn-warning").addClass("btn-success").text("发布");
    }
}
//获取素材库
function get_files(event) {
    files_list = $(event).next();
    if (files_flag===1) return;
    $.ajax({
        type: "POST",
        url: contextPath+"/SaveClassInfor",
        data: {
            Read_or_Save: "get_file",
            class_id: No
        },
        dataType: 'json',
        success: function (jsonObj) {
            let tbody = $('#file_box tbody');
            files_flag = 1;
            if (jsonObj.files.length===0){
                tbody.append(
                    '<div>暂无素材</div>'
                );
                return;
            }
            for (let i=0;i<jsonObj.files.length;i++){
                add_file_list(tbody,jsonObj.files[i].file_add,jsonObj.files[i].file_name);
            }
        }
    })
}

function add_file_list(tbody,file_add,file_name) {
    tbody.append(
        '<tr>' +
        '   <td data-add="'+file_add+'" onclick="add_file(files_list,\'/'+file_add+'\',\''+file_name+'\')">'+file_name+'</td>' +
        '   <td><button type="button" class="btn btn-outline-danger btn-sm" style="margin-top: 0" onclick="delete_files(this)">删除</button></td>' +
        '</tr>'
    )
}

function delete_files(event) {
    $.ajax({
        type: "POST",
        url: contextPath+"/SaveClassInfor",
        data: {
            Read_or_Save: "delete_file",
            address: $(event).parent().prev().data('add')
        },
        dataType: 'json',
        success: function (msg) {
            if (msg===true){
                $(event).parent().parent().remove();
            }else alert('删除失败')
        }
    })
}

function search_files() {
    let files_bank = $('#files_bank tr');
    files_bank.show();
    let searchText = $('#files_search').val();
    let regExp = new RegExp(searchText, 'g');
    files_bank.each(function ()//遍历文章；
    {
        let text = $(this).text();
        //let newHtml = html.replace(regExp, '1');//将找到的关键字替换，加上highlight属性；
        if (!regExp.test(text)){
            $(this).hide();
        }
        //$(this).text(newHtml);//更新文章；
    });
}
