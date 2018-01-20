package com.yizhen.coffee.biz.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author muying.xx
 * @Date 06/01/2018 14:14
 */
@Entity
@Table(name = "item")
@Data
public class ItemDO {


    @Id
    @GeneratedValue
    private long id;
    private Date gmtCreate;
    private Date gmtModified;

    private String title;
    private String desc;
    private String image;
    private long price;
    private long oldPrice;
    private int categoryId;
    private String categoryName;
    private int status; // item status;


}
