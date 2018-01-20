package com.yizhen.coffee.web.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @Author muying.xx
 * @Date 23/12/2017 11:30 PM
 */
@Data
@RequiredArgsConstructor
public class ItemDTO {
    private long id;
    private String name;
    private String price;
    private String oldPrice;
    private String description;
    private String image;

}
