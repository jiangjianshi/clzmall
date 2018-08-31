package com.clzmall.app.entity.dto;

import com.clzmall.common.model.GoodsProperty;
import com.clzmall.common.model.GoodsPropertyType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/14.
 */
@Data
@ToString
public class PropertiesDto extends GoodsPropertyType {

    private List<GoodsProperty> childsCurGoods;
}
