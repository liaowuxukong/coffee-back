package com.yizhen.coffee.web.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.gson.Gson;
import com.yizhen.coffee.biz.wechat.WeChatOauth;
import com.yizhen.coffee.biz.wechat.WeixinLoginUser;
import com.yizhen.coffee.biz.wechat.WxPayConfig;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author muying.xx
 * @Date 07/01/2018 13:52
 */
@Controller
@Log
public class IndexController {

    @Resource
    private HttpSession httpSession;


    @RequestMapping("/index")
    public String index(@RequestParam("code") String code,@RequestParam("id") long id) {

        if (StringUtils.isEmpty(code)) {
            return this.authorizationUrl(id);
        }

        // 获取code之后，获取用户信息放入session中
        String openId = this.getOpenId(code);
        return "index?openId="+openId+"&id="+id;
    }

    private String  authorizationUrl(long state) {
        String redirectUri = URLEncoder.encode("http://www.ealam.cn/index");
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + WxPayConfig.APP_ID
                +"&redirect_uri="+redirectUri+"&response_type=code&scope=snsapi_userinfo&state="+
                state+"#wechat_redirect";

    }


    private String getOpenId(String code) {
        WeChatOauth weChatOauth = new WeChatOauth();
        // 由code获取access_token等信息
        String str = weChatOauth.getToken(code, WxPayConfig.APP_ID, WxPayConfig.APP_SECRET);
        log.info("str="+str);
        // 解析返回的json数据,获取所需的信息
        WeixinLoginUser weixinLoginUser = new WeixinLoginUser();
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(str, Map.class);
        String openID = (String) map.get("openid");
        return openID;
    }




}
