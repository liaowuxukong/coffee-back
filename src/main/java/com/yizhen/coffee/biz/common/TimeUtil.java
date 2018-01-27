package com.yizhen.coffee.biz.common;

/**
 * @Author muying.xx
 * @Date 21/01/2018 15:57
 */
public class TimeUtil {
    //时间戳
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
