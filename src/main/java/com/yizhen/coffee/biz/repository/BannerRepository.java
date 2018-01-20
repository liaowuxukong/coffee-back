package com.yizhen.coffee.biz.repository;

import com.yizhen.coffee.biz.data.BannerDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author muying.xx
 * @Date 01/01/2018 14:16
 */

public interface BannerRepository  extends JpaRepository<BannerDO, Long> {

    List<BannerDO> findByTabIdentity(String tabIdentity);

}
