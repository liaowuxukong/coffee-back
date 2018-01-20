package com.yizhen.coffee.web.model.dto;

import lombok.Data;

/**
 * @Author muying.xx
 * @Date 01/01/2018 13:28
 */
@Data
public class BannerDTO {

    private String picUrl;  // 图片链接
    private String link;    // 点击之后的跳转链接

    public BannerDTO() {
    }

    public BannerDTO(String picUrl, String link) {
        this.picUrl = picUrl;
        this.link = link;
    }
}
