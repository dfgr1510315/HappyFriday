$(document).ready(function () {
    NewStudent(30);
    FromStudent();
    ClassStudent();
    TypeInclination()
});
function NewStudent(days){
    let time = [];
    for (let i = days; i > 0; i--) {
        let curDate = new Date();
        time.push(new Date(curDate.setDate(curDate.getDate() - i)).Format("yyyyMMdd"));
    }
    //console.log(time);
    specs(time);
    let config  = {
        type: 'line',
        data: {
            labels: time,
            datasets: [{
                label: "当日加入课程学习的人数",
                backgroundColor: "#FF6384",
                borderColor: "#FF6384",
                data: [
                    4, 7, 2, 2, 6, 4, 9, 1, 2, 1, 1, 3, 4, 2, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 0, 0,0,0,0,0
                ],
                fill: false,
            }/*, {
            label: "My Second dataset",
            fill: false,
            backgroundColor: "#36A2EB",
            borderColor: "#36A2EB",
            data: [
                -10,
                16,
                72,
                93,
                29,
                -74,
                64
            ],
        }*/]
        },
        options: {
            responsive: true,
            title:{
                display:true,
                text:'各维度每日凌晨12:00更新前一日数据，此处展示最近30天数据'
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: '日期'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: '人数'
                    }
                }]
            }
        }
    };
    if($('#NewStudent')[0]){
        let lineChartCanvas = $("#NewStudent").get(0).getContext("2d");
        let lineChart = new Chart(lineChartCanvas, config);
    }
}

function FromStudent(){
    if($('#FormStudent')[0]){
        // Get context with jQuery - using jQuery's .get() method.
        let pieChartCanvas = $("#FormStudent").get(0).getContext("2d");

        let config = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [
                        1,
                        0,
                        2,
                        1,
                        2,
                        0,
                        3
                    ],
                    backgroundColor: [
                        "#36A2EB",
                        "#4BC0C0",
                        "#68B3C8",
                        "#FF6384",
                        "#FF8911",
                        "#F16300",
                        "#116300",
                    ],
                    label: 'My dataset' // for legend
                }],
                labels: [
                    "Maven",
                    "课程测试",
                    "数据结构",
                    "计算机网络",
                    "微机原理",
                    "操作系统",
                    "软件开发技术"
                ]
            },
            options: {
                responsive: true
            }
        };
        let myPie = new Chart(pieChartCanvas, config);
    }
}

function ClassStudent(){
    if($('#ClassStudent')[0]){
        // Get context with jQuery - using jQuery's .get() method.
        let pieChartCanvas = $("#ClassStudent").get(0).getContext("2d");

        let config = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [
                        1,
                        1,
                        7,
                        1,
                        3,
                        5,
                        3
                    ],
                    backgroundColor: [
                        "#36A2EB",
                        "#4BC0C0",
                        "#68B3C8",
                        "#FF6384",
                        "#FF8911",
                        "#F16300",
                        "#116300",
                    ],
                    label: 'My dataset' // for legend
                }],
                labels: [
                    "Maven",
                    "课程测试",
                    "数据结构",
                    "计算机网络",
                    "微机原理",
                    "操作系统",
                    "软件开发技术"
                ]
            },
            options: {
                responsive: true
            }
        };
        let myPie = new Chart(pieChartCanvas, config);
    }
}

function TypeInclination(){
    if($('#TypeInclination')[0]){
        // Get context with jQuery - using jQuery's .get() method.
        let pieChartCanvas = $("#TypeInclination").get(0).getContext("2d");
        let config = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [
                        25,
                        14,
                        22,
                        11,
                        14,
                        33
                    ],
                    backgroundColor: [
                        "#36A2EB",
                        "#4BC0C0",
                        "#68B3C8",
                        "#FF6384",
                        "#FF8911",
                        "#F16300",
                    ],
                    label: 'My dataset' // for legend
                }],
                labels: [
                    "前端设计",
                    "后台开发",
                    "移动开发",
                    "嵌入式",
                    "基础理论",
                    "项目发布",
                ]
            },
            options: {
                responsive: true
            }
        };
        let myPie = new Chart(pieChartCanvas, config);
    }
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

function specs(time) {
    for (let i = 0; i < time.length; i++) {
        time[i] = time[i].slice(0, 4) + '-' + time[i].slice(4, 6) + '-' + time[i].slice(6)
    }
}