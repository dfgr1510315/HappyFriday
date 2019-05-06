var email;
var email_model = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var contextPath = getContextPath();
$(document).ready(function () {
    $('#navigation').load('navigation_dark.html',function () {
        $('#VerticalNav').load('VerticalNav.html',function () {
            addClass(2);
            $.when(get_user_infor()).done(function () {
                $('#radio_cover_img').attr('src',contextPath+head_image);
                email = cookie.get('email');
                $('.font4').text(email);
            });
        });
    });
});

function changeBind() {
    var email = $('#ChangeEmail_email').val();
    if (!email_model.test(email)){
        alert('请输入正确的邮箱格式');
        return;
    }
    $.ajax({
        type:"POST",
        asynch :"false",
        url:contextPath+"/register",
        data:{
            state:'ChangeEmail',
            email:email,
            username:user
        },
        dataType:'json',
        success:function (state) {
            if (state===true){
                alert('请去邮箱确认');
                $('#ChangeEmailClose').click();
            } else{
                alert('邮箱已被使用');
            }
        }
    });
}