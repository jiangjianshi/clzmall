package com.clzmall.app.service;

import com.clzmall.app.entity.dto.GoodsDetailDto;
import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.app.entity.dto.PriceDto;
import com.clzmall.common.model.Banner;
import com.clzmall.common.model.GoodsCategory;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/7.
 */
public interface GoodsService {

    List<GoodsCategory> listAllCat();

    List<Banner> listBanner();

    List<GoodsDto> listGoods(Integer catId, String name);

    GoodsDetailDto getGoodsDetail(Integer goodsId);

    PriceDto calSelectedPrice(Integer goodsId, String propertyChildIds);
}
