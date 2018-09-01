package com.clzmall.admin.service.impl;

import com.clzmall.admin.mapper.GoodsCategoryMapper;
import com.clzmall.admin.service.GoodsService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.GoodsCategory;
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
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;


    @Override
    public PagedList<GoodsCategory> listCategry(Integer uid) {

        List<GoodsCategory> pos = goodsCategoryMapper.selectByShopId(uid);
        PageInfo<GoodsCategory> pageInfo = new PageInfo<>(pos);
        PagedList<GoodsCategory> pagedList = PagedList.newMe(pageInfo);
        return pagedList;
    }

    @Override
    public int saveCategory(GoodsCategory category) {

       return goodsCategoryMapper.insert(category);
    }

}
