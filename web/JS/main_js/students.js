let move_student;
let move_event;
let No = GetQueryString('class_id');
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html');
    $('#course_manag_nav').load('course_manag_nav.html',function () {addAction(5)});
    get_class();
});


function remove_student(event){
    if (confirm("确定移除此学员吗？")) {
        $.ajax({
            url: contextPath+"/students",
            data: {
                No:No,
                student:$(event).parent().prev().prev().children().eq(0).children().eq(1).text(),
                action:'remove_student'
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                if (jsonObj===true){
                    $(event).parent().parent().remove();
                }else alert('移除失败')
            }
        })
    }
}

function create_class() {
    let class_name = $('#create_class_name');
    $('#create_class_Close').click();
    $.ajax({
        url: contextPath+"/students",
        data: {
            No:No,
            class_name:class_name.val(),
            action:'create_class'
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            if (jsonObj!==0){
                class_table(class_name.val(),jsonObj);
                modal_class_table(class_name.val(),jsonObj);
                class_name.val('');
            }
        }
    })
}

function get_class() {
    $.ajax({
        url: contextPath+"/students",
        data: {
            No:No,
            action:'get_class'
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            $('#class_box').append(
                '<table id="class_table" class="table">\n' +
                '                    <thead>\n' +
                '                    <tr>\n' +
                '                        <th>班级</th>\n' +
                '                        <th>操作</th>\n' +
                '                    </tr>\n' +
                '                    </thead>\n' +
                '                    <tbody></tbody>\n' +
                '                </table>'
            );
            for (let i=0;i<jsonObj.class.length;i++){
                class_table(jsonObj.class[i].name,jsonObj.class[i].id);
                modal_class_table(jsonObj.class[i].name,jsonObj.class[i].id);
            }

        }
    })
}

function modal_class_table(name,id) {
    $('#modal_class_table').append(
        ' <tr>\n' +
        '     <td>'+name+'</td>\n' +
        '     <td><button type="button" style="margin-top: 0;float: right" class="btn btn-outline-primary btn-sm" onclick="join_class('+id+')">移动至此班级</button></td>\n' +
        '</tr>'
    );
}

function class_table(name,id) {
    $('#class_table').prepend(
        ' <tr>\n' +
        '        <td>'+name+'</td>\n' +
        '        <td>' +
        '           <button type="button" style="margin-top: 0;" class="btn btn-outline-primary btn-sm" onclick="get_class_students('+id+')">查看班级</button>\n' +
        '           <button type="button" style="margin-top:0;" class="btn btn-outline-danger btn-sm" onclick="delete_student_class(this,'+id+')">删除班级</button>\n' +
        '       </td>\n' +
        '      </tr>'
    );
}

function join_class(id) {
    $.ajax({
        url: contextPath+"/students",
        data: {
            student:move_student,
            id:id,
            action:'move_student'
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            if (jsonObj===true){
                $('#join_class_Close').click();
                let student = $(move_event).parent().parent().html();
                $(move_event).parent().parent().remove();
                $('#student_table'+id).prepend(
                    '<tr>\n' +
                    student+
                    '</tr>'
                )
            }else alert('学员移动失败')
        }
    })
}

function delete_student_class(event,id) {
    let delete_class = confirm("确定删除此班级吗?");
    if (delete_class) {
        $.ajax({
            url: contextPath+"/students",
            data: {
                id:id,
                action:'delete_class'
            },
            type: "POST",
            dataType: "json",
            success: function (jsonObj) {
                if (jsonObj===true){
                    $(event).parent().parent().remove();
                }else alert('删除失败')
            }
        })
    }
}

function add_page(page_id,count,page) {
    let page_ul =  $(page_id);
    page_length = Math.ceil(count/6);
    if ((parseInt(page)-1)>0) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)-1)+'">Previous</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Previous</a></li>');

    if (page-1>2&&page_length-page>=2){
        for (let i = page-2;i<=page+2;i++){
            if (i<page_length+1){
                if (i===parseInt(page)){
                    page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
            }
        }
    }else {
        if (page<=3){
            for (i=1;i<6;i++){
                if (i<page_length+1){
                    if (i===parseInt(page)){
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                }
            }
        }else {
            for (i=page_length-4;i<=page_length;i++){
                if (i<page_length+1){
                    if (i===parseInt(page)){
                        page_ul.append('<li class="page-item active"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                    }else page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+i+'">'+i+'</a></li>');
                }
            }
        }

    }

    if ((parseInt(page)+1)<=page_length) page_ul.append('<li class="page-item"><a class="page-link" href="?='+No+'&page_ask='+(parseInt(page)+1)+'">Next</a></li>');
    else page_ul.append('<li class="page-item disabled"><a class="page-link">Next</a></li>')
}

function get_class_students(id){
    $('.table-striped').hide();
    let student_table = $('#student_table'+id);
    console.log(student_table.length);
    if ( student_table.length > 0 ) {
        student_table.show();
        return;
    }
    $.ajax({
        url: contextPath+"/students",
        data: {
            No:No,
            id:id,
            action:'get_class_students'
        },
        type: "POST",
        dataType: "json",
        success: function (jsonObj) {
            console.log(jsonObj);
            let class_box = $('#class_box');
            if (jsonObj.students.length===0)  {
                class_box.append(
                    '<div id="student_table'+id+'" class="table table-striped">\n' +
                    '     <div style="text-align: center;color: #93999f;">此班级没有添加学员</div>\n' +
                    '</div>'
                );
                return;
            }
            class_box.append(
                '  <table id="student_table'+id+'" class="table table-striped">\n' +
                '                    <thead>\n' +
                '                    <tr>\n' +
                '                        <th>学员</th>\n' +
                '                        <th>学习进度</th>\n' +
                '                        <th>操作</th>\n' +
                '                    </tr>\n' +
                '                    </thead>\n' +
                '                    <tbody></tbody>\n' +
                '                </table>'
            );
            for (let i=0;i<jsonObj.students.length;i++){
                $('#student_table'+id).append(
                    '<tr>\n' +
                    '                        <td style="position: relative;">\n' +
                    '                            <a target="_blank" href="" >\n' +
                    '                                <img src="'+contextPath+jsonObj.students[i].head+'">\n' +
                    '                                <span>'+jsonObj.students[i].name+'</span>\n' +
                    '                            </a>\n' +
                    '                            <span class="text-sm" style="position: absolute;">'+jsonObj.students[i].time+' 加入</span>\n' +
                    '                        </td>\n' +
                    '                        <td>\n' +
                    '                            <div class="progress">\n' +
                    '                                <div class="progress-bar progress-bar-striped progress-bar-animated" style="width:'+jsonObj.students[i].schedule+'%"></div>\n' +
                    '                            </div>\n' +
                    '                        </td>\n' +
                    '                        <td>\n' +
                    '                            <button type="button" class="btn btn-outline-primary btn-sm">设为班长</button>\n' +
                    '                            <button type="button" class="btn btn-outline-info btn-sm" data-toggle="modal" data-target="#join_class" data-student="'+jsonObj.students[i].name+'" onclick="change_stu(this)">移动学员</button>\n' +
                    '                            <button type="button" class="btn btn-outline-danger btn-sm" onclick="remove_student(this)">移除学员</button>\n' +
                    '                        </td>\n' +
                    '                    </tr>'
                )
            }
            //add_page('#page',jsonObj.count,page);
        }
    })
}

function change_stu(event){
    move_event = event;
    move_student = $(event).data('student');
}

