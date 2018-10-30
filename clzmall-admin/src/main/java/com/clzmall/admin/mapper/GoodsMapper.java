package com.clzmall.admin.mapper;

import com.clzmall.common.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Repository
public interface GoodsMapper {

    List<Goods> selectByShopId(@Param("shopId") Integer shopId, @Param("name") String name);
}
