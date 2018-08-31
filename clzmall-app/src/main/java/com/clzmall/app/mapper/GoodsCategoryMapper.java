package com.clzmall.app.mapper;

import com.clzmall.common.model.GoodsCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/7.
 */
@Repository
public interface GoodsCategoryMapper {

    List<GoodsCategory> selectAll();
}
