package com.yizhen.coffee.biz.repository;

import com.yizhen.coffee.biz.data.BannerDO;
import com.yizhen.coffee.biz.data.ItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author muying.xx
 * @Date 06/01/2018 14:16
 */
public interface ItemRepository  extends JpaRepository<ItemDO, Long> {

    List<ItemDO> findByStatus(int status);

    List<ItemDO> findByCategoryId(long categoryId);

}
