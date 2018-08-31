package com.clzmall.app.mapper;

import com.clzmall.common.model.GoodsPropertyType;
import org.springframework.stereotype.Repository;

/**
 * Created by bairong on 2018/8/8.
 */
@Repository
public interface GoodsPropertyTypeMapper {
    
    GoodsPropertyType selectByPrimaryKey(Integer id);
}
