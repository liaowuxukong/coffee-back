package com.yizhen.coffee.web.controller;

import com.google.gson.Gson;
import com.yizhen.coffee.biz.wechat.WeChatOauth;
import com.yizhen.coffee.biz.wechat.WeixinLoginUser;
import com.yizhen.coffee.biz.wechat.WxPayConfig;
import com.yizhen.coffee.web.helper.CookiesHelper;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 1. 获取openId， 如果获取到了，说明已经同意协议了
         * 2. 获取code， 如果获取到了， 则去获取对应的openId， 再讲Openid放置于 cookies中
         * 3. 如果没有获取到code， 则跳转去获取code
         */
        if (CookiesHelper.isCookiesHasOpenId(request.getCookies())) {
            return "redirect:index?shelfId="+request.getParameter("shelfId");
        }

        if (request.getParameterMap().containsKey("code") && StringUtils.isNotEmpty(request.getParameter("code"))) {
            String openId = this.getOpenId(request.getParameter("code"));
            Cookie cookie = new Cookie("openId", openId);
            response.addCookie(cookie);
            return "redirect:index?shelfId="+request.getParameter("state");
        }
        return authorizationUrl(request.getParameter("shelfId"));
    }



    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 1. 获取openId， 如果获取到了，说明已经同意协议了
         * 2. 获取code， 如果获取到了， 则去获取对应的openId， 再讲Openid放置于 cookies中
         * 3. 如果没有获取到code， 则跳转去获取code
         */
        if (CookiesHelper.isCookiesHasOpenId(request.getCookies()) == false) {
            return "redirect:login?shelfId="+request.getParameter("shelfId");
        }
        return "index";
    }


    private String  authorizationUrl(String state) {
        String redirectUri = URLEncoder.encode("http://www.ealam.cn/login");
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
