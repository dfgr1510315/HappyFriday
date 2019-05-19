let No = GetQueryString('class_id');
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(3);});
    get_Class();
});
function get_Class() {
    $.ajax({
        type: "POST",
        asynch: "false",
        url: contextPath+"/get_teaching",
        data:{
            type:'get_Class'
        },
        dataType: 'json',
        success: function (json) {
            let class_ul = $("tbody");
            let release_status;
            for (let i=0;i<json.class_list.length;i++){
                if (json.class_list[i].release_status===1) release_status = '已发布';
                else release_status = '未发布';
                class_ul.append(
                    '<tr>\n' +
                    '<td><img src="'+contextPath+json.class_list[i].cover_address +'" class="cover" alt="">'+json.class_list[i].class_title+'</td> \n'+
                    '<td>'+json.class_list[i].student_count+'</td> \n'+
                    '<td>'+release_status+'</td> \n'+
                    '<td><a target="_blank" href="Myclass.html?class_id='+ json.class_list[i].class_id+'"><button class="btn btn-outline-primary btn-sm" >管理</button></a></td>\n'+
                    '<tr>\n '
                )
            }
            if (class_ul.html().length === 0) {
                $('#class_table').append(
                    '<div class="no_find_class">暂无课程</div> '
                )
            }
        }
    });
}

function found_class() {
    let Class_Name = $('#found_class_name').val();
    let Class_Type = $('#sel1').val();
    if ('' === Class_Name||'类型' === Class_Type){
        alert('课程标题或类型不能为空');
    } else {
        $.ajax({
            type: "POST",
            asynch: "false",
            url: contextPath+"/get_teaching",
            data: {
                type:'found_class',
                Class_Name:Class_Name,
                Class_Type:Class_Type
            },
            dataType: 'json',
            success: function (jsonObj) {
                if (jsonObj.class_id===0) alert('创建失败') ;
                else {
                    $('#found_class_Close').click();
                    let class_ul = $("tbody");
                    class_ul.prepend(
                        '<tr>\n' +
                        '<td><img src="'+contextPath+'/image/efb37fee400582742424a4ce08951213.png" class="cover" alt="">'+Class_Name+'</td> \n'+
                        '<td>0</td> \n'+
                        '<td>未发布</td> \n'+
                        '<td><a target="_blank" href="Myclass.html?class_id='+jsonObj.class_id+'"><button class="btn btn-outline-primary btn-sm" >管理</button></a></td>\n'+
                        '<tr>\n '
                    );
                }
            }
        });
    }
}