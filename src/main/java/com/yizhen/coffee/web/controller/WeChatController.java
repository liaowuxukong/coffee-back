package com.yizhen.coffee.web.controller;

import com.yizhen.coffee.biz.common.TimeUtil;
import com.yizhen.coffee.biz.wechat.*;
import com.yizhen.coffee.web.helper.CookiesHelper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @Author muying.xx
 * @Date 21/01/2018 11:56
 */
@Controller
@RequestMapping("/wxpay")
public class WeChatController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    private static Logger log = LoggerFactory.getLogger(WeChatController.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @RequestMapping(value = "/submitWXOrderForm",method = RequestMethod.POST)
    @ResponseBody
    public WxPayData submitWXOrderForm(){
        System.out.println("--------------------------------预支付回调-------------------------------------------------");

        WxPayData result = new WxPayData();
        try {
            //商户订单号
            String outTradeNo = "123"; //WxPayUtil.getOrderFormNumber();
            //商品价格
            String totalFee = "1";
            //客户端ip
            String createIp = request.getRemoteAddr();
            //用户标识
            String openId = CookiesHelper.getOpenIdFromCookies(request.getCookies());
            //回调函数
            String basePath = "http://www.ealam.cn";
            String notifyUrl = basePath + "/wxpay/returnInfo";

            String nonceStr = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            String timeStamp = TimeUtil.getTimeStamp();

            WxPayData wxReturnData = WxPayUtil.release(outTradeNo,totalFee,createIp,openId,notifyUrl,nonceStr);

            result.setResult_code(wxReturnData.getResult_code());
            result.setAppid(WxPayConfig.APP_ID);
            result.setTimeStamp(timeStamp);
            result.setNonce_str(nonceStr);
            result.setPackageStr("prepay_id="+wxReturnData.getPrepay_id());
            result.setSignType("MD5");

            //两者都为SUCCESS才能获取prepay_id
            if( wxReturnData.getResult_code().equals("SUCCESS") && wxReturnData.getReturn_code().equals("SUCCESS") ){
                //第二次签名,将微信返回的数据再进行签名
                SortedMap<String,String> signMap = new TreeMap<String,String>();
                signMap.put("appId", WxPayConfig.APP_ID);
                signMap.put("timeStamp", timeStamp);
                signMap.put("nonceStr", nonceStr);
                signMap.put("package", "prepay_id="+wxReturnData.getPrepay_id());  //注：看清楚，值为：prepay_id=xxx,别直接放成了wxReturnData.getPrepay_id()
                signMap.put("signType", "MD5");
                String paySign = WeChatUtil.buildRequestSign(signMap,WxPayConfig.KEY);//支付签名
                log.info("paySign = {}",paySign);
                result.setSign(paySign);
            }else{
                result.setResult_code("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/returnInfo",method = RequestMethod.POST)
    @ResponseBody
    public void returnInfo(){

        try {
            InputStream is = request.getInputStream();
            String result = IOUtils.toString(is, "UTF-8");
            if("".equals(result)){
                response.getWriter().write("<xm><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数错误！]]></return_msg></xml>");
                return ;
            }
            log.info("接收的数据 xml:{}"+result.toString());
            WxPayData wxPayReq = WeChatUtil.convertXmlToObject(WxPayData.class,result);
            log.info("接收的数据Object:{}"+wxPayReq.toString());
            String openid = CookiesHelper.getOpenIdFromCookies(request.getCookies());
            String appid = wxPayReq.getAppid();
            String mch_id =wxPayReq.getMch_id();
            String nonce_str = wxPayReq.getNonce_str();
            String out_trade_no = wxPayReq.getOut_trade_no();
            String total_fee = wxPayReq.getTotal_fee();
            String trade_type = wxPayReq.getTrade_type();
            String return_code = wxPayReq.getReturn_code();
            String result_code = wxPayReq.getResult_code();
            String bank_type = wxPayReq.getBank_type();
            Integer cash_fee = wxPayReq.getCash_fee();
            String fee_type = wxPayReq.getFee_type();
            String is_subscribe = wxPayReq.getIs_subscribe();
            String time_end = wxPayReq.getTime_end();
            String transaction_id = wxPayReq.getTransaction_id();
            String sign = wxPayReq.getSign();

            //签名验证
            SortedMap<String,String> parameters = new TreeMap<String,String>();
            parameters.put("appid",appid);
            parameters.put("mch_id",mch_id);
            parameters.put("nonce_str",nonce_str);
            parameters.put("out_trade_no",out_trade_no);
            parameters.put("total_fee",total_fee);
            parameters.put("trade_type",trade_type);
            parameters.put("openid",openid);
            parameters.put("return_code",return_code);
            parameters.put("result_code",result_code);
            parameters.put("bank_type",bank_type);
            parameters.put("cash_fee",cash_fee+"");
            parameters.put("fee_type",fee_type);
            parameters.put("is_subscribe",is_subscribe);
            parameters.put("time_end",time_end);
            parameters.put("transaction_id",transaction_id);

            String sign2 = WeChatUtil.buildRequestSign(parameters,WxPayConfig.KEY);//支付签名

            log.info("sign2 = {}",sign2);

            if(sign.equals(sign2)){//校验签名,确认预支付与回调 重要信息一致
                if(return_code.equals("SUCCESS") && result_code.equals("SUCCESS")){
                    /**
                     *处理订单信息
                     */
                    log.info("--------------------------处理成功------------------------");
                    //通知腾讯服务器
                    response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
                }else{
                    response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[交易失败]]></return_msg></xml>");
                }
            }else{
                response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名校验失败]]></return_msg></xml>");
            }

            response.getWriter().flush();
            response.getWriter().close();
            return ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }






//    /**
// 　　* 点击确认充值 统一下单,获得预付id(prepay_id)
// 　　* @param request
// 　　* @param response
// 　　* @return
// 　　 */
//    @ResponseBody
//    @RequestMapping({"pay"})
//    public WxPayData prePay(HttpServletRequest request, HttpServletResponse response, String openId) {
//        WxPayData result = new WxPayData();
//        openId = des.getDesString(openId);
//        try {
//            //商户订单号
//            String out_trade_no = WeChatUtil.getOut_trade_no();
//            //产品价格,单位：分
//            Integer total_fee = 1;
//            //客户端ip
//            String ip = HttpUtil.getIpAddr(request);
//            //支付成功后回调的url地址
//            String notify_url = "http://你的域名/odao-weixin-site/wxOfficialAccountsPay/callback.do";
//            //统一下单
//            String strResult = WeChatUtil.unifiedorder("testPay", out_trade_no, total_fee, ip, notify_url,openId);
//            //解析xml
//            XStream stream = new XStream(new DomDriver());
//            stream.alias("xml", WxPayData.class);
//            WxPayData wxReturnData = (WxPayData)stream.fromXML(strResult);
//            //两者都为SUCCESS才能获取prepay_id
//            if( wxReturnData.getResult_code().equals("SUCCESS") && wxReturnData.getReturn_code().equals("SUCCESS") ){
//                //业务逻辑，写入订单日志(你自己的业务) .....
//                String timeStamp = WeChatUtil.getTimeStamp();//时间戳
//                String nonce_str = WeChatUtil.getNonceStr();//随机字符串 注：上面这两个参数，一定要拿出来作为后续的value，不能每步都创建新的时间戳跟随机字符串，不然H5调支付API，会报签名参数错误
//                result.setResult_code(wxReturnData.getResult_code());
//                result.setAppid("");
//                result.setTimeStamp(timeStamp);
//                result.setNonce_str(nonce_str);
//                result.setPackageStr("prepay_id="+wxReturnData.getPrepay_id());
//                result.setSignType("MD5");
//                WeChatUtil.unifiedorder("",out_trade_no,total_fee,ip,notify_url,openId); // 下单操作中，也有签名操作，那个只针对统一下单，要区别于下面的paySign
//                //第二次签名,将微信返回的数据再进行签名
//                SortedMap<Object,Object> signMap = new TreeMap<Object,Object>();
//                signMap.put("appId", "");
//                signMap.put("timeStamp", timeStamp);
//                signMap.put("nonceStr", nonce_str);
//                signMap.put("package", "prepay_id="+wxReturnData.getPrepay_id());  //注：看清楚，值为：prepay_id=xxx,别直接放成了wxReturnData.getPrepay_id()
//                signMap.put("signType", "MD5");
//                String paySign = WxSign.createSign(signMap,  WxPayConfig.KEY);//支付签名
//                result.setSign(paySign);
//            }else{
//                result.setResult_code("fail");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//

//
//
//    @RequestMapping("toPay.do")
//    public ModelAndView toPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        //重定向Url
//        String redirecUri = URLEncoder.encode(GlobalThreadLocal.getSiteConfig().getBasePath() + "/wxOfficialAccountsPay/toInputAccountInfo.do");
//        //用于获取成员信息的微信返回码
//        String code = null;
//        if( request.getParameter("code")!=null ){
//            code =request.getParameter("code");
//        }
//        if( code == null) {
//            //授权
//            return authorization(redirecUri);
//        }
//        code =request.getParameter("code");
//        // 获取用户信息
//        WeixinLoginUser weixinLoginUser = getWeixinLoginUser(code);
//
//        modelAndView.addObject("openId",des.getEncString(weixinLoginUser.getOpenID()));
//        // 跳转到支付界面
//        String viewName = "/wxOfficialAccountsPay/pay";
//        modelAndView.setViewName(viewName);
//        return modelAndView;
//    }
//
//
//





}
