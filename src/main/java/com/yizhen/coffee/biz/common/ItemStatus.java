package com.yizhen.coffee.biz.common;

import lombok.Getter;

/**
 * @Author muying.xx
 * @Date 06/01/2018 14:20
 */
public enum ItemStatus {

    NORMAL(0,"normal")

    ;

    @Getter
    private int status;
    @Getter
    private String desc;

    ItemStatus(int status, String desc) {
        this.status  = status;
        this.desc = desc;
    }


}
