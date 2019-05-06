var contextPath = getContextPath();
var No = GetQueryString('class_id');

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function join_class(classification) {
    var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        url: contextPath + "/learn_list",
        data: {
            action: 'join_class',
            time: time2,
            No: No,
            classification:classification
        },
        dataType: 'json',
        success: function (jsonObject) {
            if (jsonObject===true) {
                window.location.href = "Play.html?" + No + "/1-1";
            }
        }
    })
}

function continue_class(event) {
    var last_time = $(event).prev().data('class');
    window.location.href = "Play.html?" + No + "/" + last_time.substring(0, last_time.indexOf(':'));
}


Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
