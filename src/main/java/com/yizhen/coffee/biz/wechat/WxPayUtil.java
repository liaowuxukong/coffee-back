package com.yizhen.coffee.biz.wechat;

import com.yizhen.coffee.biz.common.HttpUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class WxPayUtil {
    private static Logger log = LoggerFactory.getLogger(WxPayUtil.class);

    /**
     *构建请求参数 调用 微信支付接口
     *@param outTradeNo 商户订单号
     *@param totalFee 商品价格
     *@param createIp 客户端ip
     *@param openId 用户标识
     *@param notifyUrl 回调函数
     *@return WxPayResponse 参考微信开发文档接口返回数据
     *
     */
    public static WxPayData release(String outTradeNo,String totalFee,String createIp,String openId,String notifyUrl,String nonceStr){
        //构建请求参数对象
        WxPayData request = new WxPayData();
        request.setAppid(WxPayConfig.APP_ID);
        request.setMch_id(WxPayConfig.MCH_ID);
        request.setNonce_str(nonceStr);
        String sign = "";
        request.setBody("elan");
        request.setOut_trade_no(outTradeNo);
        request.setFee_type("CNY");
        request.setTotal_fee(totalFee);
        request.setSpbill_create_ip(createIp);
        request.setNotify_url(notifyUrl);
        request.setTrade_type(WxPayConfig.TRADE_TYPE);
        //request.setProduct_id(pid);
        request.setOpenid(openId);
        //request.setAttach("zl492958025@sina.com - 12345");
        try {
            sign = WeChatUtil.buildRequestSign(request,WxPayConfig.KEY);
        } catch (Exception e) {

        }
        request.setSign(sign);
        String wxml = WeChatUtil.convertObjectToXml(request, WxPayData.class);
        log.info("wxml = {}",wxml);
        String resp = HttpUnit.sendPost(WxPayConfig.WXFC_API_URL, wxml);

        WxPayData response = WeChatUtil.convertXmlToObject(WxPayData.class, resp);
        log.info("openId = {}, 错误码 = {},错误信息 = {}",openId,response.getReturn_code(),response.getReturn_msg());
        return response;
    }

//	/**
//	* 获取服务器IP地址
//	* @return
//	*/
//	public static String getServerIp(){
//		String SERVER_IP = null;
//		try {
//			@SuppressWarnings("rawtypes")
//			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
//			InetAddress ip = null;
//			while (netInterfaces.hasMoreElements()) {
//				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
//				ip = (InetAddress) ni.getInetAddresses().nextElement();
//				SERVER_IP = ip.getHostAddress();
//				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
//				&& ip.getHostAddress().indexOf(":") == -1) {
//					SERVER_IP = ip.getHostAddress();
//					break;
//				} else {
//					ip = null;
//				}
//			}
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return SERVER_IP;
//	}

    /**
     * 生成微信支付订单号
     */
    public static String getOrderFormNumber(){
        String chars = "0123456789";
        int maxPos = chars.length();
        String orderFormNumbe = "";
        for (int i = 0; i < 16; i++) {
            orderFormNumbe += chars.charAt((int) Math.floor(Math.random() * maxPos));
        }
        return orderFormNumbe;
    }

}