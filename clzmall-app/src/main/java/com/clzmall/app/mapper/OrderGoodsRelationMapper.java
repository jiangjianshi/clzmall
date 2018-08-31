package com.clzmall.app.mapper;

import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.common.model.OrderGoodsRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/19.
 */

@Repository
public interface OrderGoodsRelationMapper {


    int insert(@Param("vo") OrderGoodsRelation odr);

    List<GoodsDto> selectGoodsByOrderIds(@Param("idsList") List<Integer> ids);

}
