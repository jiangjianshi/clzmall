package com.clzmall.app.mapper;

import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.common.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/8.
 */
@Repository
public interface GoodsMapper {

    Goods selectByPrimaryKey(@Param("id") Integer id);

    List<GoodsDto> selectByCategoryAndName(@Param("categoryId") Integer categoryId, @Param("name") String name);
}
