package com.yizhen.coffee.biz.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author muying.xx
 * @Date 01/01/2018 14:14
 */
@Entity
@Table(name = "banner")
@Data
public class BannerDO {

    @Id
    @GeneratedValue
    private long id;

    private Date gmtCreate;
    private Date gmtModified;

    private String picUrl;  // banner 图片链接
    private String link;    // banner 跳转链接
    private String tabIdentity; // 标识符

}
