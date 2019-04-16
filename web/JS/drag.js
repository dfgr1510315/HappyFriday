var flag ;
var dragEl = null;
var save_dragEl = null;
var save_this = null;
var Unit_columns ;
var Class_columns ;


function add_Listener(columns,domdrapend) {
    [].forEach.call(columns,function(column){
        column.setAttribute('draggable','true');
        column.addEventListener("dragstart",domdrugstart,false);
        column.addEventListener('dragenter', domdrugenter, false);
        column.addEventListener('dragover', domdrugover, false);
        column.addEventListener('dragleave', domdrugleave, false);
        column.addEventListener('drop', domdrop, false);
        column.addEventListener('dragend', domdrapend, false);
    });
}

function remove_Listener(columns,domdrapend) {
    [].forEach.call(columns,function(column){
        column.setAttribute('draggable','false');
        //column.children().setAttribute('draggable','false');
        column.removeEventListener("dragstart",domdrugstart,false);
        column.removeEventListener('dragenter', domdrugenter, false);
        column.removeEventListener('dragover', domdrugover, false);
        column.removeEventListener('dragleave', domdrugleave, false);
        column.removeEventListener('drop', domdrop, false);
        column.removeEventListener('dragend', domdrapend, false);
    });
}

function set_Class_Listener(event) {
    if (Unit_columns!==undefined) remove_Listener(Unit_columns,Unit_domdrapend);
    Class_columns = document.querySelectorAll('.Unit_class');
    var main_button = $(event).parent().prev().prev();
   /* if (main_button.text()==='取消拖动') {
        $(event).removeClass("btn-warning").addClass("btn-primary").text("拖动排序");
        remove_Listener(Class_columns,domdrapend);
        //remove_Listener(Unit_columns);
    }else {
        $(event).removeClass("btn-primary").addClass("btn-warning").text("取消拖动");
        for(var count=1;count<=ClassCount;count++){
            var collapse_id = $("#hour_button_collapse"+count);
            if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        }
        add_Listener(Class_columns,domdrapend);
        //add_Listener(Unit_columns);
    }*/
    for(var count=1; count<=ClassCount; count++){
        var collapse_id = $("#hour_button_collapse"+count);
        if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        collapse_id.removeAttr("href");
    }
    for(count=1;count<=UnitCount;count++){
        collapse_id = $("#Button_collapse"+count);
        collapse_id.attr("href","#collapse"+count);
        if (collapse_id.hasClass('collapsed'))  collapse_id.click();
    }
    add_Listener(Class_columns,domdrapend);
    if (main_button.text()!=="取消拖动")  main_button.removeClass("btn-primary").addClass("btn-warning").text("取消拖动");
}

function set_Unit_Listener(event) {
    if (Class_columns!==undefined) remove_Listener(Class_columns,domdrapend);
    var main_button = $(event).parent().prev().prev();
    Unit_columns = document.querySelectorAll('.Unit');
    for(var count=1;count<=UnitCount;count++){
        var collapse_id = $("#Button_collapse"+count);
        if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        collapse_id.removeAttr("href");
    }
    add_Listener(Unit_columns,Unit_domdrapend);
    if (main_button.text()!=="取消拖动")  main_button.removeClass("btn-primary").addClass("btn-warning").text("取消拖动");
}

function drag(event) {
    if ($(event).text()==="取消拖动") {
        $(event).removeClass("btn-warning").addClass("btn-primary").text("拖动排序");
        if (Unit_columns!==undefined) remove_Listener(Unit_columns,Unit_domdrapend);
        if (Class_columns!==undefined) remove_Listener(Class_columns,domdrapend);
        for(var count=1;count<=UnitCount;count++){
            var collapse_id = $("#Button_collapse"+count);
            collapse_id.attr("href","#collapse"+count);
            if (collapse_id.hasClass('collapsed'))  collapse_id.click();
        }
        for(count=1; count<=ClassCount; count++){
            collapse_id = $("#hour_button_collapse"+count);
            collapse_id.attr("href","#hour_collapse"+count);
            //if (collapse_id.hasClass('collapsed'))  collapse_id.click();
        }
    }
}



function domdrugstart(e) {
    flag = 0;
    dragEl = this;
    save_dragEl = $(dragEl).html();//存储被拖动元素的html
    //alert(save_dragEl);
}

function domdrapend(e) {
    if(flag!==1) $(dragEl).empty().append(save_dragEl);
    //sort_Class('.Unit_class');
}

function Unit_domdrapend(e) {
    if(flag!==1) $(dragEl).empty().append(save_dragEl);
    //sort_Unit('.Unit');
    //sort_Class('.Unit_class');
    for(var count=1;count<=UnitCount;count++){
        var collapse_id = $("#Button_collapse"+count);
        if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        collapse_id.removeAttr("href");
    }
}

function domdrugenter(e) { //拖动后鼠标进入另一个可接受区域,
    if (dragEl !== this) {
        //dragEl是被拖动的元素
        save_this = $(this).html(); //存储当前元素的html
        $(dragEl).empty().append(save_this);
    }else {
        $(dragEl).empty().append(save_dragEl);
        //dragEl.firstChild.classList.add('over');
        $(dragEl).children("div").eq(0).addClass("over")
    }
    //alert(e.target.classList);
    e.target.classList.add('over');
}

function domdrugover(e) {  //阻止一些类似超链接拖动跳转的默认动作
    if (e.preventDefault) {
        e.preventDefault();
    }
    e.dataTransfer.dropEffect = 'move';
    return false;
}

function domdrugleave(e) { //当拖拽鼠标划出区域时
    e.target.classList.remove('over');
}

function domdrop(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    }
    if (dragEl !== this) {
        $(this).empty().append(save_dragEl)
    }
    flag = 1;
    //alert($(this).html());
    return false;
}

/*function sort_Unit(unitName) {
    var i = 1;
    $(unitName).each(function(){
        /!* $(this).children().eq(1).attr('id',"hour_collapse"+i);
         i++;*!/
        var flag_id = $(this).children().eq(1).attr('id');
        var flag_id_no = flag_id.substr(flag_id.length-1,1);
        //alert(flag_id_no);
        Change_Unit_Fun(i,flag_id_no);
        Change_Unit_Fun(flag_id_no,i);
        i++;
    });
    for(var count=1;count<=UnitCount;count++){
        var collapse_id = $("#Button_collapse"+count);
        if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        collapse_id.removeAttr("href");
    }
}*/

/*function sort_Class(className) {
    var i = 1;
    $(className).each(function(){
       /!* $(this).children().eq(1).attr('id',"hour_collapse"+i);
        i++;*!/
       var flag_id = $(this).children().eq(1).attr('id');
       var flag_id_no = flag_id.substr(flag_id.length-1,1);
       //alert('将'+flag_id_no+'变成'+i);
        Change_Class_Fun(flag_id_no,i);
        Change_Class_Fun(i,flag_id_no);
        i++;
    });
    for(var count=1; count<=ClassCount; count++){
        var collapse_id = $("#hour_button_collapse"+count);
        if (!(collapse_id.hasClass('collapsed')))  collapse_id.click();
        collapse_id.removeAttr("href");
    }
}*/

function cancel_drag() {  //点击增加课时，退出拖动模式
    var user_drag = $("#use_drag");
    if (user_drag.text()==="取消拖动")  drag(user_drag);
}