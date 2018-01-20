package com.yizhen.coffee.biz.dao;

import com.google.common.collect.Lists;
import com.yizhen.coffee.biz.data.BannerDO;
import com.yizhen.coffee.biz.repository.BannerRepository;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author muying.xx
 * @Date 01/01/2018 14:27
 */
@Component
public class BannerDAO {

    @Resource
    private BannerRepository bannerRepository;

    public List<BannerDO> findCofferBanner() {
        return this.findByTabIdentity("coffee");
    }


    public List<BannerDO> findByTabIdentity(String tabIdentity) {
        Try<List<BannerDO>> tryResult = Try.of(() -> bannerRepository.findByTabIdentity(tabIdentity));
        return tryResult.onFailure(e -> e.printStackTrace()).getOrElse(Lists.newArrayList());
    }


}
