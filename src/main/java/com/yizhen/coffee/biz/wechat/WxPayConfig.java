package com.yizhen.coffee.biz.wechat;

/**
 * @Author muying.xx
 * @Date 21/01/2018 12:46
 */
public class WxPayConfig {

    // 微信支付 统一下单 接口地址
    public static final String WXFC_API_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //公众号支付APPID
    public static String APP_ID = "";

    public static String APP_SECRET = "";

    //公众号支付商户号
    public static String MCH_ID = "1494746502";
    //商户后台配置的一个32位的key,位置:微信商户平台-账户中心-API安全
    public static String KEY = "";
    //交易类型
    public static String TRADE_TYPE = "JSAPI";


}
