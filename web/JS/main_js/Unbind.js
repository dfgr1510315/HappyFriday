let email = cookie.get('email');
let user = cookie.get('user');
//let email_model = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//let contextPath = getContextPath();
$(document).ready(function () {
    $('.font4').text(email);
    $('#navigation').load('navigation_dark.html');
    $('#VerticalNav').load('VerticalNav.html',function () {addClass(2);});
});

function changeBind() {
    let email = $('#ChangeEmail_email').val();
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
            if (state===1){
                alert('请去邮箱确认');
                $('#ChangeEmailClose').click();
            } else{
                alert('邮箱已被使用');
            }
        }
    });
}