$(function () {
    if (isMobile()){
        mobilePhoneAdaptation()
    }

    rechargeYourBalance.getBills()
    homePage.refresh()  // 初始化

})
// 手机适配
const mobilePhoneAdaptation = function () {
    $('.kit-side-fold').click()

    // 更新首页的4个卡片框
    let card = $("#homePage .layui-col-xs3");
    for (let i = 0; i < card.length; i++) {
        $(card[i]).removeClass('layui-col-xs3')
            .addClass('layui-col-xs6')
    }

    // 更新充值栏
    let recharge_card = $("#rechargeYourBalance .layui-col-xs3");
    $(recharge_card[0]).removeClass('layui-col-xs3').addClass('layui-col-xs6')
    $(recharge_card[1]).removeClass('layui-col-xs3').addClass('layui-col-xs6')
    for (let i = 2; i < recharge_card.length; i++) {
        $(recharge_card[i]).addClass('layui-hide')
    }
}
const isMobile = function () {
    var userAgentInfo = navigator.userAgent;

    var mobileAgents = [ "Android", "iPhone", "SymbianOS", "Windows Phone", "iPad","iPod"];

    var mobile_flag = false;

    //根据userAgent判断是否是手机
    for (var v = 0; v < mobileAgents.length; v++) {
        if (userAgentInfo.indexOf(mobileAgents[v]) > 0) {
            mobile_flag = true;
            break;
        }
    }

    var screen_width = window.screen.width;
    var screen_height = window.screen.height;

    //根据屏幕分辨率判断是否是手机
    if(screen_width < 500 && screen_height < 800){
        mobile_flag = true;
    }

    return mobile_flag;
}

// 刷新订单
const refreshOrder = function(orderid) {
    $.post("pay/queryOrder", {orderid : orderid}, function () {
        // 刷新余额
        homePage.refresh()
    })
    layer.close(rechargeYourBalance.popUps)
}
/**
 * 时间戳转化为年 月 日 时 分 秒
 * number: 传入时间戳
 * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
 */
function formatTime(number,format) {

    var formateArr  = ['Y','M','D','h','m','s'];
    var returnArr   = [];

    var date = new Date(number);
    returnArr.push(date.getFullYear());
    returnArr.push(formatNumber(date.getMonth() + 1));
    returnArr.push(formatNumber(date.getDate()));

    returnArr.push(formatNumber(date.getHours()));
    returnArr.push(formatNumber(date.getMinutes()));
    returnArr.push(formatNumber(date.getSeconds()));

    for (var i in returnArr)
    {
        format = format.replace(formateArr[i], returnArr[i]);
    }
    return format;
}

//数据转化
function formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n
}

/*导航栏*/
const navigation = new Vue({
    el : "#navigation",
    data : function () {
        return {
            show : {
                index : 0
            }
        }
    },
    methods : {
        select : function (index) {
            // 0 首页 1 插件商城 2 已购插件 3 我的返利
            // 4 余额充值 5 我的订单 6 个人中心
            this.show.index = index
            console.log(index)
            console.log(index === 0)
            if (index === 0){
                homePage.refresh()
            }
            if (index === 4){
                rechargeYourBalance.getBills()
            }
        }
    }
})

/*首页*/
const homePage  = new Vue({
    el : "#homePage",
    data : function () {
        return {
            show : navigation.show,
            wallet : {
                balance : 0,
                integral : 0,
                consume : 0
            }
        }
    },
    methods : {
        /*刷新数据*/
        refresh : function () {
            $.post("pay/getWallet", function (data) {
                const json = $.parseJSON(data)
                if (json.code === 0){
                    homePage.wallet.balance = json['wallet']['balance'].toFixed(2);
                    homePage.wallet.integral = json['wallet']['integral'];
                    homePage.wallet.consume = json['wallet']['consume'].toFixed(2);
                }
            })
        }
    }
})

/*插件商城*/
const pluginMall  = new Vue({
    el : "#pluginMall",
    data : function () {
        return {
            show : navigation.show
        }
    },
    methods : {

    }
})

/*已购插件*/
const purchasedPlugIns  = new Vue({
    el : "#purchasedPlugIns",
    data : function () {
        return {
            show : navigation.show
        }
    },
    methods : {

    }
})

/*我的返利*/
const myRebate  = new Vue({
    el : "#myRebate",
    data : function () {
        return {
            show : navigation.show
        }
    },
    methods : {

    }
})

/*余额充值*/
const rechargeYourBalance  = new Vue({
    el : "#rechargeYourBalance",
    data : function () {
        return {
            show : navigation.show,
            wallet : homePage.wallet,
            popUps : null,
            bills : []
        }
    },
    methods : {
        recharge : function () {

            let amount = $("#price_min").val();
            let type = $('#pay-type input[name="pay"]:checked').val();
            const re = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            if (!re.test(amount)){
                layer.tips('请输入充值金额!', '#price_min', {
                    tips: [1, '#c21d5d'],
                    time: 3000
                });
                return
            }
            let winRef
            if (isMobile()) {
                winRef = window.open("", "_blank")
            }
            $.post("pay/recharge", {
                amount : amount,
                type : type,
                phone : isMobile()
            }, function (data) {
                const json = $.parseJSON(data)
                rechargeYourBalance.getBills()
                if (json.code === 0){
                    let order = json.order;
                    // 跳转支付页面
                    if (isMobile()){
                        let timer = setInterval(function () {
                            winRef.location = "pay/pay?phone=true"
                            clearInterval(timer)
                        }, 500)
                    } else {
                        window.open("pay/pay")
                    }

                    //询问
                    let confirm = layer.confirm('是否完成支付？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.post("pay/queryOrder", {orderid : order}, function () {
                            // 刷新余额
                            homePage.refresh()
                        })
                        layer.close(confirm)
                    });
                } else if (json.code === 1){
                    // 微信支付
                    let url = json['codeUrl'];
                    let order = json['order'];
                    rechargeYourBalance.popUps = layer.open({
                        type: 1,
                        title: '微信支付',
                        skin: '', //加上边框
                        area: ['240px', '340px'], //宽高
                        content:
                            '<div id="qrcode" style="width:200px; height:200px; margin:20px; text-align: center"></div>' +
                            '<div style="text-align: center; width: 240px">' +
                            '<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="refreshOrder(\''+ order +'\')">完成支付</button>' +
                            '</div>'
                    });
                    let qrcode = new QRCode("qrcode", {
                        text: url,
                        width: 200,
                        height: 200,
                        colorDark : "#000000",
                        colorLight : "#ffffff",
                        correctLevel : QRCode.CorrectLevel.H
                    });
                }else if (json.code === 2){
                    // 微信手机支付
                    let order = json.order;
                    let prepay_id = json['prepay_id'];
                    let packAge = json['packAge'];
                    // 跳转支付页面
                    let timer = setInterval(function () {
                        winRef.location = 'pay/wxpay_mweb_redirect?prepay_id=' + prepay_id + '&package=' + packAge
                        clearInterval(timer)
                    }, 500)
                    //询问
                    let confirm = layer.confirm('是否完成支付？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.post("pay/queryOrder", {orderid : order}, function () {
                            // 刷新余额
                            homePage.refresh()
                        })
                        layer.close(confirm)
                    });
                } else {
                    layer.alert(json.msg, {
                        icon: 2,
                        skin: 'layer-ext-demo' //见：扩展说明
                    })
                }
            })
        },
        getBills : function () {
            $.post('pay/queryOrderList', function (data) {
                const json = $.parseJSON(data)
                if (json.code === 0){
                    // 刷新列表
                    let json_bills = json.bills;
                    let bills = rechargeYourBalance.bills;
                    bills.splice(0,bills.length)    // 清空数组
                    for (let i = json_bills.length - 1; i >= 0; i--) {
                        if (json_bills[i].type === 0){
                            json_bills[i]['type_str'] = '支付宝'
                        } else {
                            json_bills[i]['type_str'] = '微信'
                        }
                        if (json_bills[i].status === 0){
                            json_bills[i]['status_str'] = '等待支付'
                        } else if (json_bills[i].status === 1){
                            json_bills[i]['status_str'] = '支付成功'
                        } else {
                            json_bills[i]['status_str'] = '支付失败'
                        }

                        json_bills[i]['amount_str'] = json_bills[i].amount.toFixed(2)
                        json_bills[i]['time_str'] = formatTime( json_bills[i].time,"Y年M月D日 h:m")

                        bills.push(json_bills[i])

                    }
                    homePage.refresh()

                } else {
                    layer.alert('刷新流水列表失败!', {
                        icon: 2,
                        skin: 'layer-ext-demo' //见：扩展说明
                    })
                }
            })
        }
    }
})

/*我的订单*/
const myOrder  = new Vue({
    el : "#myOrder",
    data : function () {
        return {
            show : navigation.show
        }
    },
    methods : {

    }
})

/*个人中心*/
const personalCenter  = new Vue({
    el : "#personalCenter",
    data : function () {
        return {
            show : navigation.show
        }
    },
    methods : {

    }
})