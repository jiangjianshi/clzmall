package com.clzmall.admin.service;

import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.Banner;

import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */
public interface BannerService {

    PagedList<Banner> listBanners(Integer uid, String title);
}
