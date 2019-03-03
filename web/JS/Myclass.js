
/*function ifActive() {
    document.getElementById("idMyclass").className -= ' nav-link';
};*/

var PageContext = $("#PageContext").val();
var UnitCount = 0;
var ClassCount = 0;
var No = window.location.search.replace("?",'');


//关闭WebSocket连接
function closeWebSocket(websocket) {
    websocket.close();
}

//发送消息
function send(websocket) {
    var message = document.getElementById('text').value;
    websocket.send(message);
}


function saveClass() {
    var class_name = $('#curriculum_Name').html();
    var Total_data = {
        ClassName:'课程名',
        UUNIt:[]
    };

    for (var i=1;i<=UnitCount;i++){
        var flag  = $("#collapse"+i);
        var j=0;
        var Unit = {
            Unit_Name:flag.prev().children("h8").text(),
            Class:[]
        };
        while (flag.children("div").eq(j).html()!==undefined){
            var source_video_name = flag.children("div").eq(j).children('div').eq(1).find('label').eq(0).text();
            //alert(flag.children("div").eq(j).children('div').eq(1).find('video').data('cusrc'));
            if ('选择视频'===source_video_name)   source_video_name='';
            var Class = {
                Class_Name:flag.children("div").eq(j).children('div').eq(0).children('h10').text(),
                Video_Src:flag.children("div").eq(j).children('div').eq(1).find('video').attr('src'),
                Source_Video_Name:source_video_name,
                Source_Video_Src:flag.children("div").eq(j).children('div').eq(1).find('video').data('cusrc'),
                Editor:flag.children("div").eq(j).children('div').eq(1).find('video').parent().parent().next().children().eq(1).children().html(),
                State:flag.children().eq(j).find('button:first').text()
            };
            var File_Href="";
            var File_Name="";
            //alert(flag.children("div").eq(j).children('div').eq(1).find('video').parent().parent().next().next().find('a').text());
            flag.children("div").eq(j).children('div').eq(1).find('video').parent().parent().next().next().find('a').each(function(){
                File_Href=File_Href+this.href+'|';
                File_Name=File_Name+this.text+'|';
            });
           // alert(File_Name);
            Class.File_Href = File_Href;
            Class.File_Name = File_Name;
            Class.Serial_No = i+"-"+(j+1);

            Unit.Class.push(Class);
            //alert(Unit.Class[j].Serial_No);
            j++;
            //alert("第0个是"+Unit.Class[0].Class_Name)
        }
        Total_data.UUNIt.push(Unit);
        //alert(Total_data.UUNIt[i-1].Class[0].Video_Src);
    }
    $.ajax({
        type: "POST",
        asynch: "false",
        url: PageContext +"/getlearnfile",
        data: {
            No:No,
            ds:JSON.stringify(Total_data.UUNIt)},
        dataType: 'json'
    });

    var data = {
        Read_or_Save: "save",
        ClassInfor: $('.sections').html(),
        No: No,
        ClassCount: ClassCount + "",
        UnitCount: UnitCount + ""
    };
    $.ajax({
        type: "POST",
        asynch: "false",
        url: PageContext+"/SaveClassInfor",
        data: data,
        dataType: 'json',
        success: function () {
            alert("保存成功");
        }
    });
    alert("保存成功");
}

function live() {
    event.returnValue = '确认是否保存';
}

function Release(state) {
        $.ajax({
            url: PageContext+"/SaveClassInfor",
            data: {
                No:No,
                Read_or_Save:state
            },
            type: "POST",
            dataType: "json",
            asynch: "false"
        });
        if (state==='已发布') {
            $("#Open_curriculum_Close").click();
            $("#curriculum_button").removeClass('btn-outline-primary').addClass('btn-success').text('已发布').attr('data-target','#Close_curriculum');
        }else {
            $("#Close_curriculum_Close").click();
            $("#curriculum_button").removeClass('btn-success').addClass('btn-outline-primary').text('发布课程').attr('data-target','#Open_curriculum');
        }

}

function Change_Unit_Fun(UnitCount, chooseCount) {
   /* alert("UnitCount:" + UnitCount + " ChooseCount:" + chooseCount);*/
    var obj = document.getElementById("collapse" + chooseCount);
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
    var obj = document.getElementById("hour_button_collapse" + chooseCount);
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
}

function addSection() {
    UnitCount = UnitCount + 1;
    $(".sections").append(
        '<div class="card section Unit" draggable="false">\n' +
        '   <div class="card-header"><span class="badge badge-pill badge-primary">章节</span>\n' +
        '       <h8 id="h8_id">\n' +
        '       </h8>\n' +
        '       <div class="btn-group" style="float:right">\n' +
        '           <button id="button_add_class" data-id="button_add_class" type="button" class="btn btn-primary" data-toggle="modal" data-target="#add_class_hour" onclick="cancel_drag()">增加课时</button>\n' +
        '           <button id="button_Change_Unit" data-id="button_Change_Unit" type="button" class="btn btn-primary"  data-toggle="modal"  data-target="#Change_section">更改名称</button>\n' +
        '           <button id="button_Delete_Unit" data-id="button_Delete_Unit" type="button" class="btn btn-primary" data-toggle="modal"  data-target="#Delete_Unit">删除章节</button>\n' +
                  /*<button type="button" class="btn btn-primary">批量上传</button>*/
        '           <button id="Button_collapse" type="button" class="btn btn-prima  ry card-link" data-toggle="collapse" href="">折叠</button>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div id="collapse" class="collapse show" >\n' +
        '   </div>\n' +
        '</div>\n'
    );

    Change_Unit_Fun(UnitCount, "");

    var unit_name = $("#unit_name");
    $("#collapse" + UnitCount).prev().children("h8").text(unit_name.val());

    unit_name.val('');
    //document.getElementById("unit_name").autofocus; 无效果
    $("#unitClose").click();

}

function add_class_hour(add_id) {
    ClassCount = ClassCount + 1;
    $(add_id).parent().parent().next().append('' +
        '<div class="card Unit_class" draggable="false" >' +
        '   <div class="card-header">' +
        '       <span class="badge badge-secondary badge-pill">课时</span>' +
        '       <h10 id="h10_id"></h10>' +
        '       <div class="btn-group" style="float:right">\n' +
        '           <button  data-id="button_Release_Class_1"  type="button" class="btn btn-success btn-sm" onclick="Change_Release_Statu(this)">发布</button>\n' +
        '           <button id="button_Change_Class" data-id="button_Change_Class"  type="button" class="btn btn-secondary btn-sm"  data-toggle="modal"  data-target="#Change_class">更改名称</button>\n' +
        '           <button id="button_Delete_Class" data-id="button_Delete_Class" type="button" class="btn btn-secondary btn-sm" data-toggle="modal"  data-target="#Delete_Class">删除课时</button>\n' +
        '           <button id="hour_button_collapse" type="button" class="btn btn-secondary card-link btn-sm" data-toggle="collapse" href="">折叠</button>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div id="hour_collapse" class="collapse show" >\n' +
        '       <div class="card-body">\n' +
        '           <div class="container">\n' +
        '               <ul class="nav nav-tabs">\n' +
        '                   <li class="nav-item ">\n' +
        '                       <a class="nav-link active" href="javascript:void(0)" onclick="Video_TEXT_FILE(this,1)" id="a_video">视频</a>\n' +
        '                   </li>\n' +
        '                   <li class="nav-item ">\n' +
        '                       <a class="nav-link" href="javascript:void(0)"  onclick="Video_TEXT_FILE(this,2)" id="a_text" >图文</a>' +
        '                   </li>\n' +
        '                   <li class="nav-item">\n' +
        '                       <a class="nav-link" href="javascript:void(0)" onclick="Video_TEXT_FILE(this,3)" id="a_file" >文件</a>\n' +
        '                   </li>\n' +
        '               </ul>\n' +
        '               <div class="input-file-show" style="">\n' +
        '                   <div>' +
        '                       <div class="custom-file mb-3" style="margin-top: 25px;width: 40%">\n' +
        '                           <input type="file" class="custom-file-input" accept="video/*" onchange ="upload(this,1)">\n' +
        '                           <label class="custom-file-label" >选择视频</label>\n' +
        '                       </div>\n' +
        '                       <button type="button" class="btn btn-secondary" style="margin-top: 4px; margin-left: 10px" onclick="delete_video(this)">删除视频</button>\n' +
        '                   </div>\n' +
        '                   <div data-percent=""  class="progressbar">\n' +
        '                       <span class="bfb_span"></span>\n' +
        '                   </div>\n' +
        '                   <div>\n' +
        '                       <video height="180px" width="320px" data-cuSRC="" controls="controls" src="" type="video/*" />\n' +
        '                   </div>\n' +
        '               </div>' +
        '               <div id="editor" style="display: none;z-index: 0"></div>\n' +
        '               <div  class="custom-file mb-3" style="margin-top: 25px;height: auto;display: none">\n' +
        '                   <input type="file" class="custom-file-input" onchange ="upload(this,3)">\n' +
        '                   <label class="custom-file-label" >选择文件</label>\n' +
        '                   <div class="list-group" >\n' +
        '                   </div>\n' +
        '               </div>\n' +
        '           </div>\n' +
        '       </div>\n' +
        '   </div> \n' +
        '</div>');

    var class_name = $("#class_name");
    $("#hour_collapse" ).prev().children("h10").text(class_name.val());
    /*alert(($(add_id).parent().parent().parent().next().children("div:last-child").children("div:first-child").children("div:last-child").attr('id')));*/
    var Next_Class_id = $(add_id).parent().parent().parent().next().children("div:last-child").children("div:first-child").children("div:last-child").attr('id');
    if (Next_Class_id!=null){
        var Next_Class_No = parseInt(Next_Class_id.slice(-1));
        var i = ClassCount-1;
        for(i;i>=Next_Class_No;i--){
            Change_Class_Fun(i+1,i);
        }
        var E = window.wangEditor;
        var editor = new E('#editor' + Next_Class_No);
        Change_Class_Fun(Next_Class_No, "");
    }else{
        Change_Class_Fun(ClassCount,"");
        E = window.wangEditor;
        editor = new E('#editor' + ClassCount);
    }
    editor.create();

    class_name.val('');
    $("#class_Close").click();

}

function Change_Unit_Name(Change_button_id) {
    var text = $("#Change_unit_name");
    $(Change_button_id).parent().prev().text(text.val());
    text.val('');
    $("#Change_section_Close").click();
}


function Change_Class_Name(Change_button_id) {
    var text = $("#Change_class_name");
    $(Change_button_id).parent().prev().text(text.val());
    text.val('');
    $("#Change_Class_Close").click();
}

function Delete_Unit(Delete_button_id) {
    var i = parseInt(Delete_button_id.id.slice(-1));
    UnitCount = UnitCount - 1;
    var Son_div_length = parseInt($(Delete_button_id).parent().parent().next().children('div').length);
    var Last_Son_id = $(Delete_button_id).parent().parent().next().children("div:last-child").children("div:last-child").attr('id');
    if (Last_Son_id!=null){
        var Last_Son_no = parseInt(Last_Son_id.slice(-1));
        for (Last_Son_no + 1; Last_Son_no < ClassCount; Last_Son_no++) {
            Change_Class_Fun(Last_Son_no + 1 - Son_div_length, Last_Son_no + 1);
        }
    }
     for (i; i < UnitCount + 1; i++) {
         Change_Unit_Fun(i, i + 1);
     }
    ClassCount = ClassCount - (Son_div_length);
    $(Delete_button_id).parent().parent().parent().remove();
    $("#Delete_Unit_Close").click();
}

function Delete_Class(Delete_button_id) {
    ClassCount = ClassCount - 1;
    $(Delete_button_id).parent().parent().parent().remove();
    var i = parseInt(Delete_button_id.id.slice(-1));
    for (i; i < ClassCount + 1; i++) {
        Change_Class_Fun(i, i + 1);
    }
    $("#Delete_Class_Close").click();
}

function delete_video(event){
    $(event).parent().next().next().children().attr("src","");
    $(event).prev().find('label').text('选择视频');
}

$(function () {
    $('#add_class_hour').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('id'); //获取呼出模态框的按钮ID
        var obj = document.getElementById("Add_sure_button");
        obj.setAttribute("onclick", "add_class_hour(" + button_id + ")");
    })
});

$(function () {
    $('#Change_section').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('id');
        var obj = document.getElementById("Change_sure_button");
        obj.setAttribute("onclick", "Change_Unit_Name(" + button_id + ")");
    })
});

$(function () {
    $('#Change_class').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('id');
        var obj = document.getElementById("Change_class_sure_button");
        obj.setAttribute("onclick", "Change_Class_Name(" + button_id + ")");
    })
});

$(function () {
    $('#Delete_Unit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('id');
        var obj = document.getElementById("Delete_Unit_sure_button");
        obj.setAttribute("onclick", "Delete_Unit(" + button_id + ")");
    })
});

$(function () {
    $('#Delete_Class').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var button_id = button.data('id');
        var obj = document.getElementById("Delete_Class_sure_button");
        obj.setAttribute("onclick", "Delete_Class(" + button_id + ")");
    })
});

function onprogress(evt){
    var loaded = evt.loaded;                  //已经上传大小情况
    var tot = evt.total;                      //附件总大小
    var per = Math.floor(100*loaded/tot);     //已经上传的百分比
    local_bar.children().html( per +'%'+'('+loaded+'/'+tot+')' );
    local_bar.css('width' , per +'%');
    if (per===100)  local_bar.css('display','none');
}

function upload(event, type) {
    var fileObj = event.files[0]; // js 获取文件对象
    /*if ("undefined" === typeof (fileObj) || fileObj.size <= 0) {
        alert("请选择图片");
        return;
    }*/
    var formFile = new FormData();
    formFile.append("file", fileObj); //加入文件对象
    var data = formFile;
    data.newParam = "type";
    data.type = "1";
    var local_bar = $(event).parent().parent().next();
    var websocket = null;

    $.ajax({
        url: PageContext+"/uploadsec",
        data: data,
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,//上传文件无需缓存
        processData: false,//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        xhr: function(){
            //取得xmlhttp异步监听
            var xhr = $.ajaxSettings.xhr();
            local_bar.css('display','block');
            if(onprogress && xhr.upload) {
                xhr.upload.addEventListener('progress' , function (evt) {
                    var loaded = evt.loaded;                  //已经上传大小情况
                    var tot = evt.total;                      //附件总大小
                    var per = Math.floor(100*loaded/tot);     //已经上传的百分比
                    local_bar.children().html( per +'%'+'('+loaded+'/'+tot+')' );
                    local_bar.css('width' , per +'%');
                    if (per===100)  {
                        local_bar.css('width','0');
//判断当前浏览器是否支持WebSocket
                        if ('WebSocket' in window) {
                            websocket = new WebSocket("ws:"+window.location.host+PageContext+"/websocket/"+document.getElementById('user').value+fileObj.name);
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
                //alert(PageContext+"/" + jsonObj.src);
                $(event).parent().parent().next().next().children().attr("data-cuSRC", PageContext+"/" + jsonObj.cuSRC);
                $(event).parent().parent().next().next().children().attr("src", PageContext+"/" + jsonObj.src);
                $(event).next().text(jsonObj.video_name);
            } else if (3 === type) {
                $(event).next().next().append(
                    '<div class="list-group-item list-group-item-action"> ' +
                    '<a style="text-decoration: none;"  " target="_Blank" href="'+PageContext+"/" + jsonObj.src+ '">' + jsonObj.filename + '' +
                    '</a>'+
                    '<button type="button" class="btn btn-light" style="float: right;padding: 0;width: 26px" onclick="Delete_File(this)"><i class="fa fa-times"></i>' +
                    '</button>' +
                    '</div>'
                    )
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


function getHTML() {
    $("#preview").attr("href","Learn_list.jsp?="+No);
     $.ajax({
        type: "POST",
        asynch: "false",
        url:PageContext+"/SaveClassInfor",
        data: {
            No:No,
            Read_or_Save: "read"
        },
        dataType: 'json',
        success: function (jsonObj) {
            var sections = $(".sections");
            $("#curriculum_Name").text(jsonObj.Title);
            if (undefined!==sections.html()) {
                sections.append(jsonObj.Class_html);
                ClassCount = parseInt(jsonObj.ClassCount);
                UnitCount = parseInt(jsonObj.UnitCount);
            }
            $("#teacher_Name").text(jsonObj.教师用户名);
            if (jsonObj.state==='已发布')  $("#curriculum_button").addClass('btn-success').text('已发布').attr('data-target','#Close_curriculum');
            else $("#curriculum_button").addClass('btn-outline-primary').text('发布课程').attr('data-target','#Open_curriculum');
            new_Editor();
        }
    });
}


function new_Editor() {
    for (var i = 1; i <= ClassCount; i++) {
        var editor_ID = $('#editor'+i);
        var editor_ID_HTML = editor_ID.find('div:last').html();
        editor_ID.empty();
        var E = window.wangEditor;
        var editor = new E('#editor' + i);
        editor.create();
        editor_ID.find('div:last').empty();
        editor_ID.find('div:last').append(editor_ID_HTML);
    }
}

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
