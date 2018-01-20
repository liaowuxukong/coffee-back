package com.yizhen.coffee.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author muying.xx
 * @Date 23/12/2017 11:55 PM
 */
@Data
public class CategoryDTO {

    public String name;
    public int type;
    public List<ItemDTO> foods;

}
