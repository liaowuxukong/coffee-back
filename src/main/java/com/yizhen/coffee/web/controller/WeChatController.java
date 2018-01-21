package com.yizhen.coffee.web.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yizhen.coffee.biz.wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author muying.xx
 * @Date 21/01/2018 11:56
 */
public class WeChatController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);


    /**
 　　* 点击确认充值 统一下单,获得预付id(prepay_id)
 　　* @param request
 　　* @param response
 　　* @return
 　　 */
    @ResponseBody
    @RequestMapping({"pay"})
    public WxPaySendData prePay(HttpServletRequest request, HttpServletResponse response, String openId) {
        WxPaySendData result = new WxPaySendData();
        openId = des.getDesString(openId);
        try {
            //商户订单号
            String out_trade_no = WeChatUtil.getOut_trade_no();
            //产品价格,单位：分
            Integer total_fee = 1;
            //客户端ip
            String ip = HttpUtil.getIpAddr(request);
            //支付成功后回调的url地址
            String notify_url = "http://你的域名/odao-weixin-site/wxOfficialAccountsPay/callback.do";
            //统一下单
            String strResult = WeChatUtil.unifiedorder("testPay", out_trade_no, total_fee, ip, notify_url,openId);
            //解析xml
            XStream stream = new XStream(new DomDriver());
            stream.alias("xml", WxPaySendData.class);
            WxPaySendData wxReturnData = (WxPaySendData)stream.fromXML(strResult);
            //两者都为SUCCESS才能获取prepay_id
            if( wxReturnData.getResult_code().equals("SUCCESS") && wxReturnData.getReturn_code().equals("SUCCESS") ){
                //业务逻辑，写入订单日志(你自己的业务) .....
                String timeStamp = WeChatUtil.getTimeStamp();//时间戳
                String nonce_str = WeChatUtil.getNonceStr();//随机字符串 注：上面这两个参数，一定要拿出来作为后续的value，不能每步都创建新的时间戳跟随机字符串，不然H5调支付API，会报签名参数错误
                result.setResult_code(wxReturnData.getResult_code());
                result.setAppid("");
                result.setTimeStamp(timeStamp);
                result.setNonce_str(nonce_str);
                result.setPackageStr("prepay_id="+wxReturnData.getPrepay_id());
                result.setSignType("MD5");
                WeChatUtil.unifiedorder("",out_trade_no,total_fee,ip,notify_url,openId); // 下单操作中，也有签名操作，那个只针对统一下单，要区别于下面的paySign
                //第二次签名,将微信返回的数据再进行签名
                SortedMap<Object,Object> signMap = new TreeMap<Object,Object>();
                signMap.put("appId", "");
                signMap.put("timeStamp", timeStamp);
                signMap.put("nonceStr", nonce_str);
                signMap.put("package", "prepay_id="+wxReturnData.getPrepay_id());  //注：看清楚，值为：prepay_id=xxx,别直接放成了wxReturnData.getPrepay_id()
                signMap.put("signType", "MD5");
                String paySign = WxSign.createSign(signMap,  WeChatConst.KEY);//支付签名
                result.setSign(paySign);
            }else{
                result.setResult_code("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 授权方法
     * @param redirecUri 重定向链接
     *
     * */
    private ModelAndView authorization(String redirecUri) {
        String siteURL="redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                +GlobalThreadLocal.getSiteConfig().getWeixin_appId()
                +"&redirect_uri="+redirecUri+"&response_type=code&scope=snsapi_userinfo&state=1234#wechat_redirect";
        return new ModelAndView(siteURL);
    }


    @RequestMapping("toPay.do")
    public ModelAndView toPay(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        //重定向Url
        String redirecUri = URLEncoder.encode(GlobalThreadLocal.getSiteConfig().getBasePath() + "/wxOfficialAccountsPay/toInputAccountInfo.do");
        //用于获取成员信息的微信返回码
        String code = null;
        if( request.getParameter("code")!=null ){
            code =request.getParameter("code");
        }
        if( code == null) {
            //授权
            return authorization(redirecUri);
        }
        code =request.getParameter("code");
        // 获取用户信息
        WeixinLoginUser weixinLoginUser = getWeixinLoginUser(code);

        modelAndView.addObject("openId",des.getEncString(weixinLoginUser.getOpenID()));
        // 跳转到支付界面
        String viewName = "/wxOfficialAccountsPay/pay";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }



    /**
     * 获取微信授权登陆用户
     * @param code
     * @return
     * @throws Exception
     */
    private WeixinLoginUser getWeixinLoginUser(String code) throws Exception {
        logger.debug("由code获取授权用户信息");
        Oauth oauth = new Oauth();
        // 由code获取access_token等信息
        String str = oauth.getToken(code, GlobalThreadLocal.getSiteConfig().getWeixin_appId(), GlobalThreadLocal.getSiteConfig().getWeixin_appSecret());
        // 解析返回的json数据,获取所需的信息
        String openID = (String) JSON.parseObject(str, Map.class).get("openid");
        String accessToken = (String) JSON.parseObject(str, Map.class).get("access_token");
        String refreshToken = (String) JSON.parseObject(str, Map.class).get("refresh_token");
        // 用openid,access_token获取用户的信息,返回userinfo对象
        UserInfo userInfo = oauth.getSnsUserInfo(openID, accessToken);
        // 将用户信息放入登录session中
        WeixinLoginUser weixinLoginUser = new WeixinLoginUser();
        weixinLoginUser.setOpenID(openID);
        weixinLoginUser.setUnionID(userInfo.getUnionid());
        weixinLoginUser.setHeadImageUrl(userInfo.getHeadimgurl());
        weixinLoginUser.setNickName(userInfo.getNickname());
        weixinLoginUser.setRefreshToken(refreshToken);
        //
        int siteID = GlobalThreadLocal.getSiteConfig().getSiteId();
        weixinLoginUser.setSiteID(siteID);
        // 返回weixinLoginUser对象
        return weixinLoginUser;
    }




}
