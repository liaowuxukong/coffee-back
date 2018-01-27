package com.yizhen.coffee.web.helper;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author muying.xx
 * @Date 27/01/2018 23:21
 */
public class CookiesHelper {

    public static boolean isCookiesHasOpenId(Cookie[] cookies) {
        return StringUtils.isNotEmpty(getOpenIdFromCookies(cookies));
    }

    public static String getOpenIdFromCookies(Cookie[] cookies) {
        if (cookies == null) {
            return StringUtils.EMPTY;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("openId")) {
                return cookie.getValue();
            }
        }
        return StringUtils.EMPTY;

    }

}
