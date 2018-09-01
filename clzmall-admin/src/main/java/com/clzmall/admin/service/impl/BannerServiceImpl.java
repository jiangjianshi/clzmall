package com.clzmall.admin.service.impl;

import com.clzmall.admin.mapper.BannerMapper;
import com.clzmall.admin.service.BannerService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.Banner;
import com.clzmall.common.model.Banner;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */

@Service
@Transactional
public class BannerServiceImpl implements BannerService{

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public PagedList<Banner> listBanners(Integer uid, String title) {

        List<Banner> pos = bannerMapper.selectByShopId(uid,  title);
        PageInfo<Banner> pageInfo = new PageInfo<>(pos);
        PagedList<Banner> pagedList = PagedList.newMe(pageInfo);
        return pagedList;
    }

    @Override
    public int addBanner(Banner banner) {

        return  bannerMapper.insert(banner);
    }
}
