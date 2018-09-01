package com.clzmall.admin.mapper;

import com.clzmall.common.model.Goods;

import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */
public interface GoodsMapper {

    List<Goods> selectByShopId(Integer shopId);
}
