package com.yizhen.coffee.biz.manager;

import com.yizhen.coffee.biz.common.ItemStatus;
import com.yizhen.coffee.biz.dao.BannerDAO;
import com.yizhen.coffee.biz.data.BannerDO;
import com.yizhen.coffee.biz.data.ItemDO;
import com.yizhen.coffee.biz.repository.ItemRepository;
import com.yizhen.coffee.web.helper.MainPageHelper;
import com.yizhen.coffee.web.model.dto.MainPageDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author muying.xx
 * @Date 01/01/2018 14:28
 */
@Component
public class MainPageManager {

    @Resource
    private BannerDAO bannerDAO;

    @Resource
    private ItemRepository itemRepository;

    public MainPageDTO getMainPage() {
        List<BannerDO> bannerDOList = bannerDAO.findCofferBanner();
        List<ItemDO> itemDOList = itemRepository.findByStatus(ItemStatus.NORMAL.getStatus());
        return MainPageHelper.genMainPage(bannerDOList,itemDOList);
    }



}
