package com.yizhen.coffee.biz.wechat;

import sun.applet.Main;

/**
 * @Author muying.xx
 * @Date 21/01/2018 11:53
 */
public class Demo {


    /**
     * 微信统一下单接口,获取预支付标示prepay_id
     * @param out_trade_no1 商户订单号
     * @param total_fee1 订单金额(单位:分)
     * @param openid1 网页授权取到的openid
     * @return
     */
    @ResponseBody
    public String getPrepayid(String out_trade_no1,String total_fee1,String openid1){

        String result = "";

        String appid = Common.appid;
        String mch_id = Common.mch_id;
        String nonce_str = Common.randString(16);//生成随机数，可直接用系统提供的方法
        String body = "E光学-商品订单";
        String out_trade_no = out_trade_no1;
        String total_fee = total_fee1;
        String spbill_create_ip = "xxx.xxx.38.47";//用户端ip,这里随意输入的
        String notify_url = "网页链接//支付回调地址
        String trade_type = "JSAPI";
        String openid = openid1;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("attach", "支付测试");
        map.put("device_info", "WEB");
        map.put("nonce_str", nonce_str);
        map.put("body", body);
        map.put("out_trade_no", out_trade_no);
        map.put("total_fee", total_fee);
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("trade_type", trade_type);
        map.put("notify_url", notify_url);
        map.put("openid", openid);
        String sign = Common.sign(map, Common.MchSecret);//参数加密
        System.out.println("sign秘钥:-----------"+sign);
        map.put("sign", sign);
        //组装xml(wx就这么变态，非得加点xml在里面)
        String content=Common.MapToXml(map);
        //System.out.println(content);
        String PostResult=HttpClient.HttpsPost("网页链接);
                JSONObject jsonObject=XmlUtil.XmlToJson(PostResult);//返回的的结果
        if(jsonObject.getString("return_code").equals("SUCCESS")&&jsonObject.getString("result_code").equals("SUCCESS")){
            result=jsonObject.get("prepay_id")+"";//这就是预支付id
        }
        return result;
    }



    public String fnc() {

        System.out.println("----------微信支付-------------");
        //1、通过网页授权接口，获取到的openid
        String openid=request.getSession().getAttribute("openid")+"";
        //处理价格单位为：分(请自行处理)
        WIDtotal_fee="1";
        String preid=getPrepayid(WIDout_trade_no, WIDtotal_fee, openid);//获取预支付标示
        System.out.println("预支付标示：----------------"+preid);
        //APPID
        String appId=Common.appid;
        request.setAttribute("appId", appId);
        //时间戳
        String timeStamp=(System.currentTimeMillis()/1000)+"";
        request.setAttribute("timeStamp", timeStamp);
        //随机字符串
        String nonceStr=Common.randString(16).toString();
        request.setAttribute("nonceStr", nonceStr);
        //预支付标识
        request.setAttribute("prepay_id", "prepay_id="+preid);
        //加密方式
        request.setAttribute("signType", "MD5");

        //组装map用于生成sign
        Map<String, String> map=new HashMap<String, String>();
        map.put("appId", appId);
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id="+preid);
        map.put("signType", "MD5");

        request.setAttribute("paySign", Common.sign(map, Common.MchSecret));//签名
        return "weixinpay";


    }

    public static void main(String[] args) {


    }
}
