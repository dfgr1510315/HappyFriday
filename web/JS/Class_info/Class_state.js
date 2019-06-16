function getHTML() {
    //console.log('!!111');
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
            //console.log('!!11');
            //console.log(jsonObj+'!!');
            $("#curriculum_Name").text(jsonObj.classList[0].class_title);
            $("#teacher_Name").text(jsonObj.classList[0].teacher);
            if (jsonObj.classList[0].release_status===1)  $("#curriculum_button").addClass('btn-success').text('已发布').attr('data-target','#Close_curriculum');
            else $("#curriculum_button").addClass('btn-outline-primary').text('发布课程').attr('data-target','#Open_curriculum');
        }
    });
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null) return  unescape(r[2]); return null;
}

