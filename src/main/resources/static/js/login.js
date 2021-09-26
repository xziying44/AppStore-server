const verificationCode = function () {
    $("#verificationCodeImg").attr("src", "/kaptcha/jpg?t=" + new Date().getTime())
}
$(function () {
    $("#login").click(function () {
        // 登录
        let account = $("#login-account").val();
        let password = $("#login-password").val();
        let verification = $("#login-verification").val();
        if (account === ""){
            layer.alert('请输入用户名', {
                icon: 3,
                skin: 'layer-ext-demo' //见：扩展说明
            })
            return;
        } else if (password === ""){
            layer.alert('请输入密码', {
                icon: 3,
                skin: 'layer-ext-demo' //见：扩展说明
            })
            return;
        } else if (verification === ""){
            layer.alert('请输入验证码', {
                icon: 3,
                skin: 'layer-ext-demo' //见：扩展说明
            })
            return;
        }
        $.post("user/login", {
            account : account,
            password : password,
            verification : verification
        }, function (data) {
            const json = $.parseJSON(data)
            if (json.code === 0){
                window.location = "/home.html"
            } else {
                verificationCode()
                layer.alert(json.msg, {
                    icon: 2,
                    skin: 'layer-ext-demo' //见：扩展说明
                })
            }
        })
    })
})
