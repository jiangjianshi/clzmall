package com.clzmall.admin.service;

import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.Goods;
import com.clzmall.common.model.GoodsCategory;

import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */
public interface GoodsService {

    PagedList<GoodsCategory> listCategry(Integer uid);

    int saveCategory(GoodsCategory category);

    PagedList<Goods> listGoods(String name);
}
