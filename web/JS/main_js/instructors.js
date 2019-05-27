let one_work_id;
let stu_text = {};
let homework_title;
let class_name;
let result = {};//学生成绩
let random = {};
let work_file;
let No;
let get_class_flag = 0;
$(document).ready(function(){
    No = GetQueryString('class_id');
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html');
    $('#course_manag_nav').load('course_manag_nav.html',function () {addAction(4)});
    get_homework();
});

function get_homework() {
    $.ajax({
        url: contextPath+"/HomeWork.do",
        data: {
            action:'get_homework',
            course_id:No
        },
        type: "POST",
        dataType: "json",
        success: function (json) {
            if (json.HW.length===0){
                $('.table-responsive table').hide();
                $('.table-responsive').append('<div class="no_find_class">暂未布置作业</div>');
                return
            }
            let time;
            for (let i=0;i<json.HW.length;i++){
                time = specs(json.HW[i].time.toString());
                work_body(time,json.HW[i].class_name,json.HW[i].title,json.HW[i].id,json.HW[i].class_id,json.HW[i].file_add);
            }
        }
    });
}

function get_class() {
    if (get_class_flag===1) return;
    $.ajax({
        url: contextPath+"/students",
        data: {
            No:No,
            action:'get_class'
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            add_option(jsonObj);
            get_class_flag = 1
        }
    })
}

//获取此班级作业
function get_stu(event,work_id,class_id) {
    let stu_id = $('#stu'+work_id);
    if (stu_id.length!==0){
        one_work_id = work_id;
        class_name = $(event).children().children().text();
        homework_title = $(event).parent().next().children().children().text();
        stu_id.show();
        $('.work_table').hide();
        $('#class_nav').text(class_name);
        $('#return_class').show();
        $('#add_work').hide();
        return
    }
    //console.log('ajax!!!!');
    $.ajax({
        url: contextPath+"/HomeWork.do",
        data: {
            action:'get_stu',
            class_id:class_id,
            course_id:No,
            work_id:work_id
        },
        type: "POST",
        dataType: "json",
        success: function (json) {
            //console.log(json);
            add_table(work_id);
            stu_split(work_id,json.HW[0],json.HW[1],json.HW[2],json.HW[3]);
            one_work_id = work_id;
            class_name = $(event).children().children().text();
            homework_title = $(event).parent().next().children().children().text();
            $('.work_table').hide();
            $('#class_nav').text(class_name);
            $('#return_class').show();
            $('#add_work').hide();
        }
    });
}

//加载此班级作业表头
function add_table(work_id) {
    $('.table-responsive').append(' <table class="table table-hover mb-5 stu_table" id="stu'+work_id+'">\n' +
        '                                    <thead>\n' +
        '                                    <tr>\n' +
        '                                        <th><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">学生</span></span></th>\n' +
        '                                        <th><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">提交时间</span></span></th>\n' +
        '                                        <th><span style="vertical-align: inherit;"><span style="vertical-align: inherit;">提交状态</span></span></th>\n' +
        /*        '                                        <th><font style="vertical-align:inherit;"><font style="vertical-align:inherit;">批改状态</font></font></th>\n' +*/
        '                                    </tr>\n' +
        '                                    </thead>\n' +
        '                                    <tbody id="stu_body'+work_id+'"></tbody>\n' +
        '                                </table>')

}

//对数据进行分割处理
function stu_split(work_id,student,post_user,time,pass_flag) {
    let s_student = student.split('|');
    let s_post_user = post_user.split('|');
    let s_time = time.split('|');
    let s_pass_flag = pass_flag.split('|');
    let index;
    for (let i=0;i<s_student.length-1;i++){
        index = $.inArray(s_student[i],s_post_user);
        add_stuBody(work_id,s_student[i],s_time[index],index,s_pass_flag[index]);
    }
}


//加载此班级作业表体
function add_stuBody(work_id,student,time,post,pass) {
    let post_class = 'badge-warning';
/*    let pass_class = 'badge-warning';*/
    let data_target = '';
    let post_text = '未提交';
/*    let pass_text = '未批改';*/
    let onclick = '';
    if (post!==-1){
        post_class = 'badge-success';
        post_text = '已提交';
        data_target = 'sub_work';
        onclick = "onclick=\"get_text(\'"+student+"\')\"";
      /*  if (pass!=='0') {
            pass_class = 'badge-success';
            pass_text = '已批改'
        }*/
    }
    if (time===undefined) time='';
    $('#stu_body'+work_id).append('<tr>\n' +
        '<td><a '+onclick+' href="javascript:void(0);" data-toggle="modal" data-target="#'+data_target+'"><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+student+'</span></span></a></td>\n' +
        '<td><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+time+'</span></span></td>\n' +
        '<td><span class="badge '+post_class+'"><span style="vertical-align:inherit;"><span style="vertical-align: inherit;">'+post_text+'</span></span></span></td>\n' +
        /*            '<td><span class="badge '+pass_class+'"><font style="vertical-align:inherit;"><font style="vertical-align: inherit;">'+pass_text+'</font></font></span></td>\n' +*/
        '</tr>');
}

//获取学生作业
function get_text(student) {
    $('#student_title').text(class_name+' '+student+' '+homework_title);
    remove_table();
    if (stu_text[one_work_id+student]!==undefined) {
        choice(stu_text[one_work_id+student]);
        questions(stu_text[one_work_id+student]);
        return;
    }
    $.ajax({
        url: contextPath+"/HomeWork.do",
        data: {
            action:'get_text',
            stu_id:one_work_id+student
        },
        type: "POST",
        dataType: "json",
        success: function (json) {
            //console.log(json);
            stu_text[one_work_id+student] = json;
            //console.log(stu_text);
            choice(json);
            questions(json);
        }
    });
}

//日期格式处理
function specs(time) {
    time = time.slice(0,4)+'-'+time.slice(4,6)+'-'+time.slice(6);
    return time;
}

function work_body(time,class_name,title,work_id,class_id,file_add) {
    $('#work_body').prepend('<tr>\n' +
        ' <td><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+time+'</span></span></td>\n' +
        ' <td><a href="javascript:void(0);" onclick="get_stu(this,'+work_id+','+class_id+')"><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+class_name+'</span></span></a></td>\n' +
        ' <td><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+title+'</span></span></td>\n' +
        ' <td><span style="vertical-align:inherit;"><span style="vertical-align:inherit;">'+file_add+'</span></span></td>\n' +
        ' <td><span style="vertical-align:inherit;"><span style="vertical-align:inherit;"><a href="javascript:void(0);" data-toggle="modal" data-target="#random_modal" onclick="getRandom(this,'+work_id+')">随机抽题</a> <a href="javascript:void(0);" onclick="delete_work(this,'+work_id+')">删除</a></span></span></td>\n' +
        '</tr>')
}

function remove_table() {
    $('#choose_title p,#question_title p').remove();
    $('#choose,#question').empty();
}

//添加简答题
function questions(json) {
    let question = json.text[4].split('|');
    let standard_answer = json.text[5].split('|');
    let answer = json.text[6].split('|');
    let right_count = 0;
    for (let i = 0; i < question.length-1; i++) {
        $('#question').append(
            '<li id="text_'+i+'">\n' +
            '                                    <div class="test_content_nr_tt">\n' +
            '                                        <i>'+(i+1)+'</i><span>'+question[i]+'</span>\n' +
            '                                    </div>\n' +
            '                                    <div class="test_content_nr_main">\n' +
            '                                        <article class="textarea">\n' +
            '                                            <textarea readonly="readonly">'+answer[i]+'</textarea>\n' +
            '                                        </article>\n' +
            '                                    </div>\n' +
            '                                </li>'
        );
        if (standard_answer[i]!==answer[i]){
            $('#text_'+i+' textarea').css('background-color','#ffb1b1');
            $('#text_'+i+' article').append('<textarea readonly="readonly" style="background-color:#93f9aa">'+standard_answer[i]+'</textarea>');
        }else {
            $('#text_'+i+' textarea').css('background-color','#93f9aa');
            right_count++
        }
    }
    $('#question_title').append('<p><span>对'+right_count+'题 , 共'+(question.length-1)+'题</span></p>');
}

//添加选择题
function choice(json) {
    let question = json.text[0].split('|');
    let option = json.text[1].split('|');
    let _select = json.text[2].split('|');
    let sel_standard = json.text[3].split('|');
    let right_count = 0;
    //console.log(_select);
    for (let i = 0; i < question.length-1; i++) {
        let one_option = option[i].split('.');
        $('#choose').append('<li id="qu_' + i + '">\n' +
            '                                <div class="test_content_nr_tt">\n' +
            '                                    <i>' + (i + 1) + '</i><span>' + question[i] + '</span>\n' +
            '                                </div>\n' +
            '                                <div class="test_content_nr_main">\n' +
            '                                    <ul>\n' +
            '                                        <li class="option">\n' +
            '                                            <input disabled value="A" type="radio" class="radioOrCheck" name="answer' + i + '" id="0_answer_' + i + '_option_1"/>\n' +
            '                                            <label for="0_answer_' + i + '_option_1"><p class="ue" style="display: inline;">' + one_option[1].replace('B','') + '</p></label>\n' +
            '                                        </li>\n' +
            '                                        <li class="option">\n' +
            '                                            <input disabled value="B" type="radio" class="radioOrCheck" name="answer' + i + '" id="0_answer_' + i + '_option_2"/>\n' +
            '                                            <label for="0_answer_' + i + '_option_2"><p class="ue" style="display: inline;">' + one_option[2].replace('C','') + '</p></label>\n' +
            '                                        </li>\n' +
            '                                        <li class="option">\n' +
            '                                            <input disabled value="C" type="radio" class="radioOrCheck" name="answer' + i + '" id="0_answer_' + i + '_option_3"/>\n' +
            '                                            <label for="0_answer_' + i + '_option_3"><p class="ue" style="display: inline;">' + one_option[3].replace('D','') + '</p></label>\n' +
            '                                        </li>\n' +
            '                                        <li class="option">\n' +
            '                                            <input disabled value="D" type="radio" class="radioOrCheck" name="answer' + i + '" id="0_answer_' + i + '_option_4"/>\n' +
            '                                            <label for="0_answer_' + i + '_option_4"><p class="ue" style="display: inline;">' + one_option[4] + '</p></label>\n' +
            '                                        </li>\n' +
            '                                    </ul>\n' +
            '                                </div>\n' +
            '                            </li>');
        //let input = $('input:radio[name="answer' + i + '"]');
        if (_select[i]===sel_standard[i]){
            sel_switch(_select[i],'#93f9aa',i);
            right_count++
        }else {
            sel_switch(_select[i],'#ffb1b1',i);
            switch (sel_standard[i]){
                case 'A':
                    $('#0_answer_'+i+'_option_1').parent().css('background-color','#93f9aa');
                    break;
                case 'B':
                    $('#0_answer_'+i+'_option_2').parent().css('background-color','#93f9aa');
                    break;
                case 'C':
                    $('#0_answer_'+i+'_option_3').parent().css('background-color','#93f9aa');
                    break;
                case 'D':
                    $('#0_answer_'+i+'_option_4').parent().css('background-color','#93f9aa');
                    break;
            }
        }
    }
    $('#choose_title').append('<p><span>对'+right_count+'题 , 共'+(question.length-1)+'题</span></p>');
}

function sel_switch(select,color,i) {
    switch (select) {
        case 'A':
            $('#0_answer_'+i+'_option_1').attr("checked",'checked').parent().css('background-color',color);
            break;
        case 'B':
            $('#0_answer_'+i+'_option_2').attr("checked",'checked').parent().css('background-color',color);
            break;
        case 'C':
            $('#0_answer_'+i+'_option_3').attr("checked",'checked').parent().css('background-color',color);
            break;
        case 'D':
            $('#0_answer_'+i+'_option_4').attr("checked",'checked').parent().css('background-color',color);
            break;
    }
}

function return_class() {
    $('.stu_table').hide();
    $(".work_table").show();
    $('#return_class').hide();
    $('#add_work').show();
    $('#class_nav').text('班级作业')
}

function delete_work(event,work_id){
    if(confirm("删除后将不可恢复")) {
        $.ajax({
            type: "POST",
            url: contextPath + "/HomeWork.do",
            data: {
                action: 'delete_work',
                id: work_id
            },
            dataType: 'json',
            success: function (msg) {
                if (msg===true){
                    $(event).parent().parent().parent().parent().remove();
                    if ($('#work_body tr').length===0) {
                        $('.table-responsive table').hide();
                        $('.table-responsive').append('<div class="no_find_class">暂未布置作业</div>');
                    }
                }else alert('删除失败')
            }
        })
    }
}

//添加作业模态框的班级选项
function add_option(class_info) {
    for (let i=0;i<class_info.class.length;i++) $('#class').append('<option value="'+class_info.class[i].id+'">'+class_info.class[i].name+'</option>')
}

function work_upload(event) {
    if (event.files[0]===undefined) return;
    let name = event.files[0].name;
    if (name!==undefined) {
        work_file  = event;
        $('#work_input').val(name);
    }
}

function add_work(){
    let time = new Date().Format("yyyyMMdd");
    let class_selected = $('#class option:selected');
    let title = $('#title').val();
    if(title.trim()==='') {
        alert('标题不能为空');
        return;
    }
    if (work_file===undefined) {
        alert('请上传题库');
        return;
    }
    let file = work_file.files[0];
    let type = file.name.slice(file.name.lastIndexOf(".")+1).toLowerCase();
    if (type!=='xls'&&type!=='xlsx'){
        alert("只能上传Excle文件");
        return;
    }
    $('#add_work_close').click();
    let formFile = new FormData();
    formFile.append("path", 'homework');
    formFile.append("file", file); //加入文件对象
    $.ajax({
        url: contextPath+"/UploadFiles.do",
        data: formFile,
        type: "POST",
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,
        /*  xhr: function(){
              //取得xmlhttp异步监听
              let xhr = $.ajaxSettings.xhr();

          },*/
        success: function (jsonObj) {
            if (jsonObj===undefined) {
                alert('上传失败');
                return;
            }
            $.ajax({
                type: "POST",
                url: contextPath + "/HomeWork.do",
                data: {
                    action: 'add_work',
                    class_id: class_selected.val(),
                    course_id:No,
                    file_add:file.name,
                    time:time,
                    title:title
                },
                dataType: 'json',
                success: function (msg) {
                    //function work_body(time,class_name,title,work_id,class_id,file_add)
                    if (msg!==0){
                        $('.table-responsive table').show();
                        $('.no_find_class').remove();
                        work_body(specs(time),class_selected.text(),title,msg,No,file.name);
                        work_file = undefined;
                        $('#title').val('');
                        $('#class option:selected').val('');
                        $('#file').val('');
                        $('#work_input').val('');
                    }else alert('作业添加失败')
                }
            })
        }
    })
}
//获取题库数量及设置过的抽题
function getRandom(event,id){
    //console.log('random[id]'+random[id]);
    if (random[id]!==undefined){
        showRandom(random[id]);
        return
    }
    $.ajax({
        url: contextPath+"/HomeWork.do",
        data: {
            action:'getRandom',
            id:id
        },
        type: "POST",
        dataType: "json",
        success: function (json) {
            //console.log(json);
            showRandom(json);
            random[id] = json;
        }
    });
    $('#random_title').text($(event).parent().parent().parent().prev().prev().children().children().text());
    $('#postRandom').attr('onclick','postRandom('+id+')');
}

function showRandom(json){
    let sel = $('#sel');
    let cal = $('#cal');
    sel.attr('placeholder','共'+json[0]+'题').attr('max',json[0]);
    cal.attr('placeholder','共'+json[1]+'题').attr('max',json[1]);
    if (json[2]!==0)  sel.val(json[2]); else sel.val('');
    if (json[3]!==0)  cal.val(json[3]); else cal.val('');
}

function postRandom(id){
    let sel = $('#sel').val().trim();
    let cal = $('#cal').val().trim();
    if (sel===''||sel>random[id][0]||sel<=0){
        alert('设置的选择题题数应小于题库选择题题数'+random[id][0]+'且大于0');
        return
    }
    if (cal===''||cal>random[id][1]||cal<=0){
        alert('设置的计算题题数应小于题库计算题题数'+random[id][1]+'且大于0');
        return
    }
    $('#random_close').click();
    $.ajax({
        url: contextPath+"/HomeWork.do",
        data: {
            action:'postRandom',
            id:id,
            sel:sel,
            cal:cal
        },
        type: "POST",
        dataType: "json",
        success: function (json) {
            if (json===true){
                random[id][2] = sel;
                random[id][3] = cal;
                alert('设置成功')
            }else alert('设置失败')
        }
    });
}

Date.prototype.Format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};