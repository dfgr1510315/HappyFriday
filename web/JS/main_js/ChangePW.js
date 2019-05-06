var flag1 = false;
var flag2 = false;
var flag3 = false;
var form_currentPassword;
var form_confirmPassword;
var form_newPassword;
var contextPath = getContextPath();
$(document).ready(function(){
    $('#navigation').load('navigation_dark.html',function () {
        $('#VerticalNav').load('VerticalNav.html',function () {
            addClass(1);
            $.when(get_user_infor()).done(function () {
                //console.log('done:'+head_image);
                $('#radio_cover_img').attr('src',contextPath+head_image);
            });
        });
    });
    chang_PW();
});

function chang_PW() {
    form_currentPassword = $("#form-currentPassword");
    var currentPassword_waring = $('#currentPassword_waring');
    form_currentPassword.focus(function(){
        currentPassword_waring.css('display','none');
        flag1 = false;
    });
    form_currentPassword.blur(function(){
        if (form_currentPassword.val().length<6 || form_currentPassword.val().indexOf(" ") !== -1){
            currentPassword_waring.css('display','block');
        }else flag1 = true;
    });

    form_newPassword = $('#form_newPassword');
    var form_newPassword_waring = $('#form_newPassword_waring');
    form_newPassword.focus(function () {
        form_newPassword_waring.css('display','none');
        flag2 = false;
    });
    form_newPassword.blur(function(){
        if (form_newPassword.val().length<6 || form_newPassword.val().indexOf(" ") !== -1){
            form_newPassword_waring.css('display','block');
        }else if (form_confirmPassword.val()!==form_newPassword.val()){
            form_confirmPassword_waring2.css('display','block');
            flag2 = true;
        }else {
            form_confirmPassword_waring2.css('display','none');
            flag2 = true;
        }
    });

    form_confirmPassword = $('#form_confirmPassword');
    var form_confirmPassword_waring1 = $('#form_confirmPassword_waring1');
    var form_confirmPassword_waring2 = $('#form_confirmPassword_waring2');
    form_confirmPassword.focus(function () {
        form_confirmPassword_waring1.css('display','none');
        form_confirmPassword_waring2.css('display','none');
        flag3 = false;
    });
    form_confirmPassword.blur(function(){
        if (form_confirmPassword.val().length<6 || form_confirmPassword.val().indexOf(" ") !== -1){
            form_confirmPassword_waring1.css('display','block');
        }else  if (form_confirmPassword.val()!==form_newPassword.val()) {
            form_confirmPassword_waring2.css('display','block');
            flag3 = true;
        }else flag3 = true;
    });
}

function post_PW() {
    //alert(flag1+','+flag2+','+flag3);
    if (flag1===true&&flag2===true&&flag3===true&&form_confirmPassword.val()===form_newPassword.val()) {
        $.ajax({
            url: "${pageContext.request.contextPath}/changepw",
            data: {
                username:user,
                form_currentPassword:form_currentPassword.val(),
                form_confirmPassword:form_confirmPassword.val()
            },
            type: "POST",
            dataType: "json",
            asynch: "false",
            success: function (state) {
                switch (state) {
                    case 0:
                        alert('修改成功');
                        form_currentPassword.val('');
                        form_confirmPassword.val('');
                        form_newPassword.val('');
                        break;
                    case 1:
                        alert('原密码错误');
                        break;
                    case 2:
                        alert('修改失败');
                        break;
                }
            }
        });
    }
}