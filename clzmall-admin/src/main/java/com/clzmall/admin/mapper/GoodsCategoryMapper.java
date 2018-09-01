package com.clzmall.admin.mapper;

import com.clzmall.common.model.GoodsCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/7.
 */
@Repository
public interface GoodsCategoryMapper {

    List<GoodsCategory> selectByShopId(Integer shopId);

    int insert(@Param("vo") GoodsCategory category);
}
