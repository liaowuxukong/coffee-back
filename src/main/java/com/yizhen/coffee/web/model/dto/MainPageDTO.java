package com.yizhen.coffee.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author muying.xx
 * @Date 23/12/2017 11:57 PM
 */
@Data
public class MainPageDTO {


    private List<BannerDTO> bannerList;

    private List<CategoryDTO> categoryList;

    public MainPageDTO() {
    }

    public MainPageDTO(List<BannerDTO> bannerList, List<CategoryDTO> categoryList) {
        this.bannerList = bannerList;
        this.categoryList = categoryList;
    }
}
