let user;

window.onload = function(){
    user = cookie.get('user');
    /* $('#navigation').load('navigation_dark.html', function () {
         get_user_infor();
         power_work();
     });*/
    $('#navigation').load('navigation_dark.html');
    power_work();
    get_work();
};

//权限判断
function power_work() {
    $.ajax({
        type: "POST",
        url: contextPath + "/HomeWork.do",
        data: {
            action: 'power_work',
            work_id: GetQueryString('work'),
            student:user
        },
        dataType: 'json',
        success: function (msg) {
            if (msg.title===undefined){
                window.location.href="404.html";
            }else $('title').text(msg.title)
        }
    });
}

function TiMu(){
    for(let i in data1){
        let div = document.createElement("div");
        div.className = "entrance-bottom-frame-line";
        document.querySelector(".entrance-bottom-frame").appendChild(div);

     /*   let beijing = document.createElement("div");
        beijing.className = "entrance-bottom-frame-beijing";
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(beijing);*/

        let prev = document.createElement("div");
        prev.className = "entrance-bottom-frame-line-prev fa fa-arrow-left";
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(prev);

        let next = document.createElement("div");
        next.className = "entrance-bottom-frame-line-next fa fa-arrow-right";
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(next);

        let div1 = document.createElement("div");
        div1.className = "entrance-bottom-frame-line-id";
        div1.innerHTML = data1[i].id;
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(div1);

        let div2 = document.createElement("div");
        div2.className = "entrance-bottom-frame-line-title";
        div2.innerHTML = data1[i].title;
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(div2);

        let code = document.createElement("code");
        let pre = document.createElement("pre");
        code.innerHTML = data1[i].code;
        pre.appendChild(code);
        pre.className = "pre_message";
        document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(pre);

        for(let j in data1[i].xuanxiang){
            let div3 = document.createElement("div");
            div3.className = "entrance-bottom-frame-line-button";
            let div3_id = document.createElement("div");
            div3_id.className = "entrance-bottom-frame-line-button-id";
            if(j == 0){
                div3_id.innerHTML = "A";
            }else if(j == 1){
                div3_id.innerHTML = "B";
            }else if(j == 2){
                div3_id.innerHTML = "C";
            }else{
                div3_id.innerHTML = "D";
            }
            let div4 = document.createElement("div");
            div4.className = "entrance-bottom-frame-line-button-frame";
            div4.innerHTML = data1[i].xuanxiang[j];
            div3.appendChild(div3_id);
            div3.appendChild(div4);
            document.querySelectorAll(".entrance-bottom-frame-line")[i].appendChild(div3);
        }
    }

}

function addClass(obj, cls){
    let obj_class = obj.className;//获取 class 内容.
    let blank = (obj_class !== '') ? ' ' : '';//判断获取到的 class 是否为空, 如果不为空在前面加个'空格'.
    //组合原来的 class 和需要添加的 class.
    obj.className = obj_class + blank + cls;//替换原来的 class.
}

function removeClass(obj, cls){
    let obj_class = ' '+obj.className+' ';//获取 class 内容, 并在首尾各加一个空格. ex) 'abc    bcd' -> ' abc    bcd '
    obj_class = obj_class.replace(/(\s+)/gi, ' ');//将多余的空字符替换成一个空格. ex) ' abc    bcd ' -> ' abc bcd '
    let removed = obj_class.replace(' '+cls+' ', ' ');//在原来的 class 替换掉首尾加了空格的 class. ex) ' abc bcd ' -> 'bcd '
    removed = removed.replace(/(^\s+)|(\s+$)/g, '');//去掉首尾空格. ex) 'bcd ' -> 'bcd'
    obj.className = removed;//替换原来的 class.
}

function hasClass(obj, cls){
    let obj_class = obj.className,//获取 class 内容.
        obj_class_lst = obj_class.split(/\s+/);//通过split空字符将cls转换成数组.
    x = 0;
    for(x in obj_class_lst) {
        if(obj_class_lst[x] === cls) {//循环数组, 判断是否包含cls
            return true;
        }
    }
    return false;
}


function getStyle(obj,attr){
    if(obj.currentStyle){
        return obj.currentStyle[attr];
    }
    else{
        return document.defaultView.getComputedStyle(obj,null)[attr];
    }
}

//计时用
//function CountDown() {
//        minutes = Math.floor(mintime / 60);
//       seconds = Math.floor(mintime % 60);
//       if(minutes < 10){
//       	minutes1 = "0" + minutes
//       }else{
//       	minutes1 = minutes
//       }
//       if(seconds < 10){
//       	seconds1 = "0" + seconds
//       }else{
//       	seconds1 = seconds
//       }
//       msg =   minutes1 + ":" + seconds1;
//       document.all["timer"].innerHTML = msg;
////       if (maxtime == 5 * 60)alert("还剩5分钟");
//           ++mintime;
//
////       clearInterval(timer);
//
// }

function add_Listener() {
    //let mintime = 1;
//  timer = setInterval("CountDown()", 1000);
    let dact = document.querySelector(".entrance-bottom-frame-line");
    let active = "active";
    //let none = "none";
    addClass(dact, active);
    let timu_id = 0;
    let line = $('.entrance-bottom-frame-line');
    let prev = $('.fa-arrow-left');
    let next = $('.fa-arrow-right');
    for (let i=0;i<line.length;i++) {
        let button = $('.entrance-bottom-frame-line:eq('+i+') .entrance-bottom-frame-line-button');
        for(let j = 0;j<button.length;j++){
            //console.log(i);
            button[j].onclick = function(){
                //removeClass($('.line_no'+i),'selection');
                $('.line_no'+i).removeClass('selection');
                addClass(this,'selection');
                timu_id = _next(timu_id,active)
            };
            button.addClass('line_no'+i);
        }

        next[i].onclick = function(){
            timu_id = _next(timu_id,active)
        };
        prev[i].onclick = function(){
            timu_id = _prev(timu_id,active)
        }
    }
}

function _prev(timu_id,active) {
    if(timu_id !== 0){
        let frame_right = getStyle(document.querySelector(".entrance-bottom-frame"),'marginLeft');
        document.querySelector(".entrance-bottom-frame").style.marginLeft = parseInt(frame_right) +1050 + "px";
        timu_id--;
        addClass(document.querySelectorAll(".entrance-bottom-frame-line")[timu_id], active);
        removeClass(document.querySelectorAll(".entrance-bottom-frame-line")[timu_id+1], active);
        //addClass(document.querySelectorAll(".entrance-bottom-frame-beijing")[timu_id-1],none)
    }else{
        alert("已经是第一题啦")
    }
    return timu_id;
}

function _next(timu_id,active){
    if(timu_id < document.querySelectorAll(".entrance-bottom-frame-line").length - 1){
        let frame_left = getStyle(document.querySelector(".entrance-bottom-frame"),'marginLeft');
        document.querySelector(".entrance-bottom-frame").style.marginLeft = parseInt(frame_left) -1050 + "px";
        timu_id++;
        addClass(document.querySelectorAll(".entrance-bottom-frame-line")[timu_id], active);
        removeClass(document.querySelectorAll(".entrance-bottom-frame-line")[timu_id-1], active);
        //addClass(document.querySelectorAll(".entrance-bottom-frame-beijing")[timu_id-1],none)
    }else{
        alert("最后一题啦")
    }
    return timu_id;
}

let data1 = [];
let data2 = []; //简答题数据
let select_answer = '';
let cal_answer = '';

function get_work() {
    $.ajax({
        type: "POST",
        url: contextPath + "/HomeWork.do",
        data: {
            action: 'get',
            /*class_id:class_id,
            unit:unit*/
            work_id: '1'
        },
        dataType: 'json',
        success: function (json) {
            console.log(json);
            choice(json);
            TiMu();
            questions_text();
            add_Listener();
        }
    });
}

function choice(json) {
    let work;
    for (let i = 0; i < json.work.length; i++) {
        work = json.work[i].split('~~');
        if (work[0]==='选择题'){
            data1.push({
                "id" : i+1,
                "title": "选择题",
                "code": work[1],
                "xuanxiang":[
                    work[2],
                    work[3],
                    work[4],
                    work[5],
                ]
            });
            select_answer = select_answer + work[6] + '|'
        }else{
            data2.push({
                "qu":work[1]
            });
            cal_answer = cal_answer + work[2] + '|'
        }
    }
}

function questions_text() {
    for (let i=0;i<data2.length;i++){
        $('.entrance-bottom-frame').append(
            '<div class="entrance-bottom-frame-line">\n' +
            '<div class="entrance-bottom-frame-line-prev fa fa-arrow-left"></div>\n' +
            '<div class="entrance-bottom-frame-line-next fa fa-arrow-right"></div>\n' +
            '                <div class="entrance-bottom-frame-beijing"></div>\n' +
            '                <div class="entrance-bottom-frame-line-id">'+(i+1)+'</div>\n' +
            '                <div class="entrance-bottom-frame-line-title">简答题</div>\n' +
            '                <pre class="pre_message">'+data2[i].qu+'</pre>\n' +
            '                <div class="form-group">\n' +
            '                    <label for="comment">作答:</label>\n' +
            '                    <textarea class="form-control" rows="5" id="comment'+i+'"></textarea>\n' +
            '                </div>\n' +
            '            </div>'
        )
    }
}

function post_work() {
    let post_button = $('#post');
    if(!post_button.hasClass(('btn--activated'))) {
        post_button.removeClass('btn--activate');
        //post_button.removeAttr('onclick');
    }
    if (post_button.hasClass(('btn--error'))) {
        post_button.removeClass('btn--error');
    }
    post_button.addClass('btn--waiting');
    let select = '';
    let question = '';
    let option = '';
    let calculation = '';
    let answer = '';
    for (let i = 0; i < data1.length; i++) { //选择题数据
        question = question + data1[i].code + '|';
        option = option + data1[i].xuanxiang[0] + data1[i].xuanxiang[1] + data1[i].xuanxiang[2]+ data1[i].xuanxiang[3] + '|';
        let value = $('.line_no'+i+'.selection').children().eq(0).text();
        if (value === undefined) value = '';
        select = select + value + '|'
    }
    for (let i = 0; i < data2.length; i++) { //选择题数据
        calculation = calculation + data2[i].qu + '|';
        answer = answer + $('#comment'+i).val() + '|';
    }
    let time = new Date().Format("yyyy-MM-dd HH:mm:ss");
    $.ajax({
        type: "POST",
        url: contextPath + "/HomeWork.do",
        data: {
            action: 'post_work',
            work_id: '1',
            student: user,
            time: time,
            question:question,
            option:option,
            select: select,
            sel_standard:select_answer,
            calculation: calculation,
            cal_standard:cal_answer,
            cal_answer:answer
        },
        dataType: 'json',
        success: function (msg) {
            setTimeout(function(){
                if (msg !== true) postError();
                else removeWaiting();
            }, 700);
        }
    });
}

function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
function postError() {
    let post_button = $('#post');
    post_button.children().eq(1).text('提交失败');
    post_button.removeClass('btn--waiting');
    post_button.addClass('btn--error');
   // post_button.attr('onclick','post_work()');
}

function removeWaiting(){
    let post_button = $('#post');
    post_button.removeClass('btn--waiting');
    post_button.addClass('btn--activated');
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
