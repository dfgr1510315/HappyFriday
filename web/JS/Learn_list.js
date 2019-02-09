var PageContext = $("#PageContext").val();
var No = window.location.search.replace("?",'');



    function join_class() {
        $.ajax({
            type: "POST",
            asynch: "false",
            url: PageContext+"/learn_list",
            data: {
                action:'join_class',
                No:No
            },
            dataType: 'json',
            success: function (jsonObject) {
                if (jsonObject.msg==='1'){
                    window.location.href="Play.jsp?"+No+"/1-1";
                }
            }
        })
    }

    function continue_class() {
        var last_time = cookie.get('class_no_'+user+No);
        window.location.href="Play.jsp?"+No+"/"+last_time.substring(0,last_time.indexOf(','));
    }

