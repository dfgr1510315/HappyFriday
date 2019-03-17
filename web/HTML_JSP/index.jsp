<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
</head>

<style type="text/css" >
    .title_list_box{
        position: relative;
        left: 10px;
        margin: 10px;
        width: 200px;
        background-color: #000;
        color: #fff;
        border: 2px solid #000;
    }

    .title_list{
        margin: 0;
        padding: 0;
        list-style: none;
    }

    .hover{
        background-color: cyan;
        color: red;
    }

</style>

<body>
<form>
    <span class="label">Enter username</span>
    <input type="text" name="userid" class="userid"/>
    <div class="title_list_box">
        <div class="title_list"></div>
    </div>
</form>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $('.title_list_box').hide();
        var timeout;
        $('.userid').keyup(function(){
            clearTimeout(timeout);
            var user = $('.userid').val();
            if(user==='') return;
            var data = {
                Read_or_Save:'search',
                keyword:user
            };
            timeout = setTimeout(function() {
                $.ajax({
                    type:"POST",
                    url:"${pageContext.request.contextPath}/SaveClassInfor",
                    data:data,
                    success:function(html){
                        $('.title_list_box').show();
                        $('.title_list').html(html);
                        $('li').hover(
                            function(){
                                $(this).addClass('hover');
                            },
                            function(){
                                $(this).removeClass('hover');
                            }
                        );
                        $('li').click(function(){
                            $('.userid').val($(this).text());
                            $('.title_list_box').hide();
                        });
                    }
                });
            }, 600);
            return false;
        });
    });

</script>

</html>
