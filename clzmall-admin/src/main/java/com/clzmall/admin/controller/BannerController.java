package com.clzmall.admin.controller;

import com.clzmall.admin.mapper.BannerMapper;
import com.clzmall.admin.service.BannerService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.Banner;
import com.clzmall.common.model.GoodsCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiangjianshi on 18/9/1.
 */


@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController {

    @Resource
    private BannerService bannerService;


    @RequestMapping(value = "/listBanners")
    public PagedList<Banner> listBanners(HttpServletRequest req, String title) {

        PagedList<Banner> pagedList = bannerService.listBanners(getUid(req), title);
        return pagedList;
    }
}
