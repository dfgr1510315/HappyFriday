function getHTML() {
    $("#preview").attr("href","Learn_list.html?class_id="+No);
    $.ajax({
        type: "POST",
        url:contextPath+"/SaveClassInfor",
        data: {
            No:No,
            Read_or_Save: "read"
        },
        dataType: 'json',
        success: function (jsonObj) {
            $("#curriculum_Name").text(jsonObj.title);
            $("#teacher_Name").text(jsonObj.teacher);
            if (jsonObj.state==='1')  $("#curriculum_button").addClass('btn-success').text('已发布').attr('data-target','#Close_curriculum');
            else $("#curriculum_button").addClass('btn-outline-primary').text('发布课程').attr('data-target','#Open_curriculum');
        }
    });
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null) return  unescape(r[2]); return null;
}

