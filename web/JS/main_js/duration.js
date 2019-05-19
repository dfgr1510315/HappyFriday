let No;
$(document).ready(function () {
    No = GetQueryString('class_id');
    $('#navigation').load('navigation_dark.html');
    $('#ui_box').load('ui_box.html');
    $('#course_manag_nav').load('course_manag_nav.html', function () {addAction(3)});
    get_viewed(0, 0, 7);
    choose_time();
});

function census() {
    let end = new Date($('#end').val());
    let start = new Date($('#start').val());
    let days = getDuration(end - start);
    get_viewed(start.Format('yyyyMMdd'), end.Format('yyyyMMdd'), days);
}

function choose_time() {
    let DATAPICKERAPI = {
        // 快捷选项option
        rangeShortcutOption1: [{
            name: '最近一周',
            day: '-7,0'
        }, {
            name: '最近一个月',
            day: '-30,0'
        }, {
            name: '最近三个月',
            day: '-90, 0'
        }]
    };
    //年月日范围
    $('.J-datepicker-range-day').datePicker({
        hasShortcut: true,
        format: 'YYYY-MM-DD',
        isRange: true,
        shortcutOptions: DATAPICKERAPI.rangeShortcutOption1
    });
}

function getDuration(my_time) {
    let days = my_time / 1000 / 60 / 60 / 24;
    //var hours = my_time / 1000 / 60 / 60 - (24 * daysRound);
    //var hoursRound = Math.floor(hours);
    //var minutes = my_time / 1000 / 60 - (24 * 60 * daysRound) - (60 * hoursRound);
    //var minutesRound = Math.floor(minutes);
    //var seconds = my_time / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound);
    return Math.floor(days);
}

function get_viewed(start, end, days) {
    let time = [];
    for (let i = days; i > 0; i--) {
        let curDate = new Date();
        time.push(new Date(curDate.setDate(curDate.getDate() - i)).Format("yyyyMMdd"));
    }
    if (start === 0) {
        start = time[0];
        end = time[time.length - 1];
    }
    let times = [];
    $.ajax({
        type: "POST",
        url: getContextPath() + "/play",
        data: {
            action: 'get_viewed',
            class_id: No,
            start: start,
            end: end
        },
        dataType: 'json',
        success: function (json) {
            //console.log(json.viewed);
            for (let i = 0; i < time.length; i++) {
                for (let j = 0; j < json.viewed.length; j++) {
                    if (time[i] === json.viewed[j].substr(0, 8)) {
                        times.push(json.viewed[j].substring(8, json.viewed[j].length));
                        break;
                    }
                }
                if (times[i] === undefined) times.push(0);
            }
            view(time, times);
        }
    });
}

//规范x轴上的数据
function specs(time) {
    for (let i = 0; i < time.length; i++) {
        time[i] = time[i].slice(0, 4) + '-' + time[i].slice(4, 6) + '-' + time[i].slice(6)
    }
}

function view(time, times) {
    specs(time);
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    let option = {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            name: '日期',
            data: time
        },
        yAxis: {
            minInterval: 1,
            name: '播放次数',
            type: 'value'
        },
        series: [{
            data: times,
            type: 'line'
            /*color:'#61a0a8'*/
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
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
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

