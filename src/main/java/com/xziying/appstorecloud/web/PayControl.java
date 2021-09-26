package com.xziying.appstorecloud.web;

import com.alibaba.fastjson.JSONObject;
import com.xziying.appstorecloud.constant.KeyField;
import com.xziying.appstorecloud.constant.SessionKey;
import com.xziying.appstorecloud.domain.pay.AlipayBean;
import com.xziying.appstorecloud.domain.pay.Bill;
import com.xziying.appstorecloud.domain.pay.Wallet;
import com.xziying.appstorecloud.domain.pay.WeChatPayBean;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.service.WebWalletService;
import com.xziying.appstorecloud.utils.StringUtil;
import com.xziying.appstorecloud.utils.pay.AlipayUtil;
import com.xziying.appstorecloud.utils.pay.WeChatPayUtil;
import com.xziying.wechatpay.service.WeChatPayService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * PayControl
 *
 * @author : xziying
 * @create : 2021-05-02 20:03
 */
@RestController
@RequestMapping("/pay")
public class PayControl {

    @Resource
    WebWalletService walletService;

    @Resource
    AlipayUtil alipayUtil;

    @Resource
    WeChatPayService weChatPayService;

    @RequestMapping(value = "getWallet", method = RequestMethod.POST)
    public String getWallet(
            HttpServletRequest request
    ){
        JSONObject reply = new JSONObject();
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(SessionKey.USER_INFO);
            Integer uid = user.getUid();
            Wallet wallet = walletService.queryWallet(uid);
            reply.put("code", 0);
            reply.put("wallet", wallet);
        } catch (Exception e){
            reply.put("code", 1);
            reply.put("msg", "未知错误");
        }
        return reply.toJSONString();
    }

    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    public String recharge(
            HttpServletRequest request,
            Double amount,
            Integer type,
            String phone
    ){
        JSONObject reply = new JSONObject();
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(SessionKey.USER_INFO);
            Integer uid = user.getUid();
            Bill bill = walletService.createBill(uid, user.getAccount(), amount, type);
            session.setAttribute(SessionKey.SESSION_PAY_BILL, bill);
            if (bill.getType() == KeyField.PAY_WECHAT){
                // 微信支付

                if (phone == null || !phone.equals("true")) {
                    // PC端扫码
                    JSONObject x = JSONObject.parseObject(weChatPayService.nativePay(bill.getOrderid(), bill.getAmount(), bill.getOrdername()));
                    reply.put("code", 1);
                    reply.put("order", bill.getOrderid());
                    reply.put("codeUrl", x.getString("codeUrl"));
                } else {
                    // 移动端浏览器
                    JSONObject x = JSONObject.parseObject(weChatPayService.nativePay_phone(bill.getOrderid(), bill.getAmount(), bill.getOrdername()));
                    String mwebUrl = x.getString("mwebUrl") + "&";
                    String prepay_id = StringUtil.getSubString(mwebUrl, "prepay_id=", "&");
                    String packAge = StringUtil.getSubString(mwebUrl, "package=", "&");
                    reply.put("code", 2);
                    reply.put("order", bill.getOrderid());
                    reply.put("mwebUrl", mwebUrl);
                    reply.put("prepay_id", prepay_id);
                    reply.put("packAge", packAge);
                }


            } else {
                reply.put("code", 0);
                reply.put("order", bill.getOrderid());
                reply.put("msg", "正在跳转支付页面");
            }
        } catch (Error e){
            e.printStackTrace();
            reply.put("code", 3);
            reply.put("msg", e.getMessage());
        } catch (Exception e){
            reply.put("code", 4);
            reply.put("msg", "支付模块出现异常，请联系管理员");
        }
        return reply.toJSONString();
    }

    /**
     * 微信h5支付，要求referer是白名单的地址，这里做个重定向
     */
    @ResponseBody
    @GetMapping("wxpay_mweb_redirect")
    public String wxpayMweb(@RequestParam("prepay_id") String prepayId,
                            @RequestParam("package") String packAge) {
        String url = String.format("https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=%s&package=%s", prepayId, packAge);
        return "<meta http-equiv=\"refresh\" content=\"0; url='" +url +"'\">";
    }


    @RequestMapping(value = "pay", method = RequestMethod.GET)
    public String pay(
            HttpServletRequest request,
            String phone
    ){
        HttpSession session = request.getSession();
        try {
            Bill bill = (Bill) session.getAttribute(SessionKey.SESSION_PAY_BILL);
            if (bill.getType() == KeyField.PAY_ALIPAY){
                // 支付宝
                AlipayBean alipayBean = new AlipayBean()
                        .setOut_trade_no(bill.getOrderid())
                        .setSubject(bill.getOrdername())
                        .setTotal_amount(new StringBuffer().append(bill.getAmount()));
                String defray;
                if (phone == null || !phone.equals("true")) {
                    defray = alipayUtil.defray(alipayBean);
                } else {
                    defray = alipayUtil.defray_phone(alipayBean);
                }
                session.setAttribute(SessionKey.SESSION_PAY_BILL, null);
                return defray;
            }
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

    @RequestMapping(value = "alipayCallback")
    public String alipayCallback(
            HttpServletRequest request
    ){
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            if (alipayUtil.validationCallback(parameterMap)){
                // 验签成功
                String out_trade_nos = parameterMap.get("out_trade_no")[0];
                walletService.completeBill(out_trade_nos);
            }
        } catch (Exception e){
        }
        return "";
    }

    @RequestMapping(value = "queryOrder")
    public String queryOrder(
            HttpServletRequest request,
            String orderid

    ){
        JSONObject reply = new JSONObject();
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(SessionKey.USER_INFO);
            if (user != null){
                walletService.askOrder(orderid);
                reply.put("code", 0);
                reply.put("msg", "支付已完成");
            }
        } catch (Exception e){
            reply.put("code", 1);
            reply.put("msg", "状态未知");
        }
        return reply.toJSONString();
    }


    /**
     * 查询所有充值订单
     */
    @RequestMapping(value = "queryOrderList")
    public String queryOrderList(
            HttpServletRequest request
    ){
        JSONObject reply = new JSONObject();
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(SessionKey.USER_INFO);
            walletService.pollOrderStatus(user.getUid());   // 轮询查询订单状态
            List<Bill> bills = walletService.queryByUid(user.getUid());
            reply.put("code", 0);
            reply.put("bills", bills);
        } catch (Exception e){
            reply.put("code", 1);
            reply.put("msg", "未知错误");
        }
        return reply.toJSONString();
    }

}
