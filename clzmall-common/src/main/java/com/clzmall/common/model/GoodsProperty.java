package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by bairong on 2018/8/8.
 */
@Data
@ToString
public class GoodsProperty {

    private Integer id; //
    private Integer goodsId; //商品ID
    private Integer propTypeId; //类型ID
    private String propValue; //
    private double addedPrice; //附件价格
    private Integer addedAmount; //附件数量
    private Date createTime; //
    private Date updateTime; //
}
