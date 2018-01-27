package com.yizhen.coffee.biz.wechat;

import com.yizhen.coffee.biz.common.HttpUnit;

/**
 * @Author muying.xx
 * @Date 23/01/2018 22:49
 */
public class WeChatOauth {

    public static final String AUTH_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token";


    public String getToken(String code, String appId, String secret) {
        String param =  "appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        return HttpUnit.sendGet(AUTH_BASE,param);
    }




}
