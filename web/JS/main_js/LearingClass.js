$(document).ready(function () {
    getClass();
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(5);});
});
function addUL(_ul,class_one) {
    let star;
    let action;
    if (class_one.collection ==='1') {
        star='star';
        action = 0;
    }
    else {
        star = 'star-o';
        action = 1;
    }
    _ul.append(
        '<li id="'+class_one.class_id+'" class="course-one type' + class_one.class_type + '">\n' +
        '   <div class="course-list-img l">\n' +
        '   <a href="" target="_blank">\n' +
        '        <img width="200" height="113" src="' +contextPath+class_one.cover_address + '"  alt=""/>\n' +
        '   </a>\n' +
        '   </div>\n' +
        '   <div class="course-list-cont">\n' +
        '       <h3 class="study-hd">\n' +
        '            <a href="Learn_list.html?class_id=' + class_one.class_id + '" target="_blank">' + class_one.class_title + '</a>\n' +
        '            <div class="share-box">\n' +
        '                  <a href="javascript:void(0);" onclick="collection(this,'+action+')" title="收藏" ><i class="fa fa-'+star+'"></i></a>\n' +
        '                  <a href="javascript:void(0);" onclick="delete_class_no(' + class_one.class_id + ')" data-toggle="modal"  data-target="#Delete_Class"  title="删除" ><i class="fa fa-trash-o"></i></a>\n' +
        '            </div>\n' +
        '       </h3>\n' +
        '       <div class="study-points">\n' +
        '             <span class="i-left span-common">已学' + class_one.schedule + '%</span>\n' +
        '             <span class="i-right span-common">学习至' + class_one.last_time + '</span>\n' +
        '       </div>\n' +
        '       <div class="catog-points">\n' +
        '              <span class="i-left span-common"><a target="_blank" href=' + class_one.class_id + '"Learn_list.html?class_id=&page_note=1">笔记 ' + class_one.note_count + '</a></span>\n' +
        '              <span class="i-right span-common"><a target="_blank" href=' + class_one.class_id + '"Learn_list.html?class_id=&page_ask=1">问答 ' + class_one.ask_count + '</a></span>\n' +
        '              <button type="button" class="btn btn-outline-info btn-sm" style="right: 32px;position: absolute;" onclick="continue_class(' + class_one.class_id + ',\'' + class_one.last_time + '\')">继续学习</button>\n' +
        '       </div>\n' +
        '</li>'
    );
}

function getClass(){
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/get_teaching",
        data: {
            type: 'get_my_class'
        },
        dataType: 'json',
        success: function (json) {
            //console.log(json);
            let class_list = json.class_list;
            let collection_ul = $('#collection_ul');
            let learning_ul = $('#learning_ul');
            let learned_ul = $('#learned_ul');
            for (let i = 0; i < class_list.length; i++) {
                if (class_list[i].schedule === 100) {
                    addUL(learned_ul,class_list[i])
                } else {
                    addUL(learning_ul,class_list[i])
                }
                if (class_list[i].collection ==='1'){
                    addUL(collection_ul,class_list[i])
                }
            }
            if (learning_ul.html().length === 0) {
                learning_ul.append(
                    '<div class="no_find_class">暂无课程</div>'
                )
            }
            if (collection_ul.html().length === 0) {
                collection_ul.append(
                    '<div class="no_find_class">暂无课程</div>'
                )
            }
            if (learned_ul.html().length === 0) {
                learned_ul.append(
                    '<div class="no_find_class">暂无课程</div>'
                )
            }
        }
    });
}

function collection(event, action) {
    let class_no = $(event).parent().prev().attr("href");
    let type;
    if (action === 1) type = 'add_collection';
    else type = 'remove_collection';
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/get_teaching",
        data: {
            class_no: class_no.substring(class_no.lastIndexOf("=") + 1, class_no.length),
            type: type
        },
        dataType: 'json',
        success: function (json) {
            if (json === true) {
                if (action===1) $(event).children().removeClass("fa-star-o").addClass("fa-star");
                else  $(event).children().removeClass("fa-star").addClass("fa-star-o");
            } else {
                alert('修改收藏失败');
            }
        }
    });
}

$(function () {
    $('#Delete_Class').modal("hide");
});

function delete_class_no(ID) {
    $('#Delete_Class_sure_button').attr("onclick", 'delete_class(' + ID + ')');
}

function delete_class(ID) {
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/get_teaching",
        data: {
            class_no: ID,
            type: 'delete_class'
        },
        dataType: 'json',
        success: function (json) {
            if (json === true) {
                $('#Delete_Class_Close').click();
                $('#'+ID).remove()
            }
        }
    });
}

function screen(event, type) {
    $(event).parent().prev().children().text($(event).text());
    if (type !== 0) {
        for (let i = 1; i <= 6; i++) {
            if (i === type) $('.type' + i).css('display', 'block');
            else $('.type' + i).css('display', 'none');
        }
    } else {
        for (i = 1; i <= 6; i++) {
            $('.type' + i).css('display', 'block');
        }
    }
}

function continue_class(No, Unit_no) {
    window.location.href = "Play.html?" + No + "/" + Unit_no.substring(0, Unit_no.indexOf(':'));
}