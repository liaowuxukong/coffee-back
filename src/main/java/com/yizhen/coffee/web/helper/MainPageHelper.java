package com.yizhen.coffee.web.helper;

import com.google.common.collect.Lists;
import com.yizhen.coffee.biz.data.BannerDO;
import com.yizhen.coffee.biz.data.ItemDO;
import com.yizhen.coffee.web.model.dto.BannerDTO;
import com.yizhen.coffee.web.model.dto.CategoryDTO;
import com.yizhen.coffee.web.model.dto.ItemDTO;
import com.yizhen.coffee.web.model.dto.MainPageDTO;
import lombok.NonNull;
import one.util.streamex.StreamEx;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author muying.xx
 * @Date 23/12/2017 11:47 PM
 */
public class MainPageHelper {

    public static List<BannerDTO> defaultBanner() {
        return Lists.newArrayList(new BannerDTO("http://img3.imgtn.bdimg.com/it/u=2645597901,3299484952&fm=27&gp=0.jpg","http://www.baidu.com"));
    }

    public static List<CategoryDTO> defaultCategory() {
        ItemDTO coffee1 = new ItemDTO();
        coffee1.setDescription("这里是描述");
        coffee1.setName("美式咖啡-130g");
        coffee1.setPrice("15.00");
        coffee1.setImage("http://fuss10.elemecdn.com/c/cd/c12745ed8a5171e13b427dbc39401jpeg.jpeg?imageView2/1/w/750/h/750");

        ItemDTO coffee2 = new ItemDTO();
        coffee2.setDescription("这里是描述123");
        coffee2.setName("美式咖啡-160g");
        coffee2.setPrice("29.00");
        coffee2.setImage("http://fuss10.elemecdn.com/c/cd/c12745ed8a5171e13b427dbc39401jpeg.jpeg?imageView2/1/w/750/h/750");

        CategoryDTO coffeeCate = new CategoryDTO();
        coffeeCate.setFoods(Lists.newArrayList(coffee1,coffee2));
        coffeeCate.setName("coffee");
        coffeeCate.setType(1);

        return Lists.newArrayList(coffeeCate);
    }


    public static MainPageDTO genMainPage(List<BannerDO> bannerDOList, List<ItemDO> itemDOList) {
        return new MainPageDTO(toBannerList(bannerDOList),toCategoryList(itemDOList));
    }

    private static List<CategoryDTO> toCategoryList(List<ItemDO> itemDOList) {
        if (CollectionUtils.isEmpty(itemDOList)) {
            return defaultCategory();
        }

        List<CategoryDTO> categoryDTOList = Lists.newArrayList();
        Map<Integer, List<ItemDO>> categoryMap = StreamEx.of(itemDOList).groupingBy(itemDO -> itemDO.getCategoryId());
        categoryMap.forEach((categoryId, valueList) -> {
            CategoryDTO oneCategory = new CategoryDTO();
            oneCategory.setType(categoryId);
            oneCategory.setName(valueList.get(0).getCategoryName());
            oneCategory.setFoods(StreamEx.of(valueList).map(MainPageHelper::toItemDTO).toList());
            categoryDTOList.add(oneCategory);
        });

        return categoryDTOList;
    }

    private static ItemDTO toItemDTO(@NonNull ItemDO itemDO) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(itemDO.getId());
        itemDTO.setName(itemDO.getTitle() + ",id:"+itemDO.getId());
        itemDTO.setDescription(itemDO.getDesc());
        itemDTO.setImage(itemDO.getImage());
        itemDTO.setPrice(fenToYuan(itemDO.getPrice()));
        if (itemDO.getOldPrice() > 0) {
            itemDTO.setOldPrice(fenToYuan(itemDO.getOldPrice()));
        }
        return itemDTO;
    }

    private static List<BannerDTO> toBannerList(List<BannerDO> bannerDOList) {
        if (CollectionUtils.isEmpty(bannerDOList)) {
            return defaultBanner();
        }
        return StreamEx.of(bannerDOList).map(MainPageHelper::toBannerDTO).toList();
    }


    public static BannerDTO toBannerDTO(@NonNull BannerDO bannerDO  ) {
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setLink(bannerDO.getLink());
        bannerDTO.setPicUrl(bannerDO.getPicUrl());
        return bannerDTO;
    }



    public static void main(String[] args) {


        ItemDO itemDO1 = new ItemDO();
        itemDO1.setCategoryId(1);
        itemDO1.setId(1);

        ItemDO itemDO2 = new ItemDO();
        itemDO2.setCategoryId(2);
        itemDO2.setId(2);


        ItemDO itemDO3 = new ItemDO();
        itemDO3.setCategoryId(1);
        itemDO3.setId(3);

        ItemDO itemDO4 = new ItemDO();
        itemDO4.setCategoryId(2);
        itemDO4.setId(4);

        Map<Integer, List<ItemDO>> longListMap = StreamEx.of(itemDO1, itemDO2, itemDO3, itemDO4).groupingBy(itemDO -> itemDO.getCategoryId());
        System.out.println(longListMap.getClass().getName());
    }

    /**
     * 分转元
     *
     * @param fen
     * @return
     */
    private static String fenToYuan(Long fen) {
        if (fen == null) {
            return null;
        }
        return new BigDecimal(fen).movePointLeft(2).toString();
    }



}
