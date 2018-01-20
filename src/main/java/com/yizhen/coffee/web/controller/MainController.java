package com.yizhen.coffee.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.yizhen.coffee.biz.manager.MainPageManager;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.web.bind.annotation.*;
import com.yizhen.coffee.web.helper.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author muying.xx
 * @Date 23/12/2017 11:20 PM
 */
@RestController
@Slf4j
public class MainController {

    @Resource
    private MainPageManager mainPageManager;

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(@RequestBody String param) {
        Map<String,Object> paramMap = new Gson().fromJson(param, Map.class);
        List<Long> idList = (List<Long>) paramMap.get("ids");
        log.info("ids="+StreamEx.of(idList).joining(";"));
        return "success";
    }

    @GetMapping("/goodList")
    public String goodList() {
        return new Gson().toJson(mainPageManager.getMainPage().getCategoryList()).toString();
    }

    @GetMapping("/itemList")
    public String itemList() {
        return new Gson().toJson(mainPageManager.getMainPage()).toString();
    }

    @GetMapping("/bannerList")
    public String bannerList() {
        return new Gson().toJson(mainPageManager.getMainPage().getBannerList()).toString();
    }

}
