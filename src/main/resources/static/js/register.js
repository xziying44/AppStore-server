const verificationCode = function () {
    $("#verificationCodeImg").attr("src", "/kaptcha/jpg?t=" + new Date().getTime())
}

$(function () {
    const sendCodeClick = function () {
        // 发送验证码

        let phone = $("#register-phone").val();
        let verification = $("#register-verification").val();
        if (phone === ""){
            layer.tips('请输入手机号码!', '#register-phone', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        } else if (verification === ""){
            layer.tips('请输入验证码!', '#register-verification', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return;
        }
        $.post("user/sendCode", {
            phone : phone,
            verification : verification
        }, function (data) {
            const json = $.parseJSON(data)
            
            if (json.code === 0){
                let time = 60
                $("#sendCode")
                    .off("click")
                    .attr("class", "layui-btn layui-btn-disabled")
                    .html(time + "秒后可重新获取")

                const timer = setInterval(function () {
                    time--;
                    if (time > 0){
                        $("#sendCode").html(time + "秒后可重新获取")
                    } else {
                        $("#sendCode")
                            .html("发送验证码")
                            .on("click", sendCodeClick)
                            .attr("class", "layui-btn layui-btn-normal")
                        clearInterval(timer)
                    }
                }, 1000)
                layer.alert(json.msg, {
                    icon: 1,
                    skin: 'layer-ext-demo' //见：扩展说明
                })
            } else {
                layer.alert(json.msg, {
                    icon: 2,
                    skin: 'layer-ext-demo' //见：扩展说明
                })
            }
        })
    }
    $("#sendCode").click(sendCodeClick)
    $("#register-account").blur(function () {
        var account = $("#register-account").val();
        if (account === ""){
            layer.tips('用户名不能为空!', '#register-account', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        }
        if (!/^[a-zA-Z][a-zA-Z0-9_]{5,15}$/.test(account)){
            layer.tips('用户名不合法,请以字母开头，且包含字母数字下划线，长度为6-16位作为用户名!', '#register-account', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
        } else {
            $.post("/user/detectUserName", {
                account: account
            }, function (data) {
                const json = $.parseJSON(data)
                if (json.code === 0){
                    layer.tips(json.msg, '#register-account', {
                        tips: [1, '#1dc27a'],
                        time: 3000
                    });
                } else {
                    layer.tips(json.msg, '#register-account', {
                        tips: [1, '#c21d5d'],
                        time: 3000
                    });
                }
            })
        }
    })
    $("#register-password").blur(function () {
        const password = $("#register-password").val();
        if (password === ""){
            layer.tips('密码不能为空!', '#register-password', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        }
        if (!/^[a-zA-Z0-9_]{8,20}$/.test(password)){
            layer.tips('密码格式不正确,密码包含字母数字下划线，且长度为8-20位!', '#register-password', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
        }
    })
    $("#register").click(function () {
        const account = $("#register-account").val();
        const password = $("#register-password").val();
        const code = $("#register-code").val();

        if (account === ""){
            layer.tips('用户名不能为空!', '#register-account', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        }
        if (password === ""){
            layer.tips('密码不能为空!', '#register-password', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        }
        if (code === ""){
            layer.tips('手机验证码不能为空!', '#register-code', {
                tips: [1, '#c21d5d'],
                time: 3000
            });
            return
        }

        $.post('user/register', {
            account: account,
            password: password,
            code: code
        }, function (data) {
            const json = $.parseJSON(data)
            if (json.code === 0){
                layer.alert(json.msg, {
                    icon: 1,
                    skin: 'layer-ext-demo' //见：扩展说明
                })
                setInterval(function () {
                    window.location = "/"
                }, 2000)
            } else {
                layer.alert(json.msg, {
                    icon: 2,
                    skin: 'layer-ext-demo' //见：扩展说明
                })
            }
        })

    })
})