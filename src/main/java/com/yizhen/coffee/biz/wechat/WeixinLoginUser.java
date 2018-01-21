package com.yizhen.coffee.biz.wechat;

/**
 * @Author muying.xx
 * @Date 21/01/2018 12:28
 */
public class WeixinLoginUser {

    private String openID;
    private String unionID;
    private String headImageUrl;
    private String nickName;
    private String refreshToken;
    private int siteID;

    public String getOpenID() {
        return openID;
    }
    public void setOpenID(String openID) {
        this.openID = openID;
    }
    public String getUnionID() {
        return unionID;
    }
    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }
    public String getHeadImageUrl() {
        return headImageUrl;
    }
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public int getSiteID() {
        return siteID;
    }
    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }



}
