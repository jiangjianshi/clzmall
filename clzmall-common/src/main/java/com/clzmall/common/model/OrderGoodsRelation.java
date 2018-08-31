package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/19.
 */

@Data
@ToString
public class OrderGoodsRelation {


    private Integer id; //
    private Integer orderId; //
    private Integer goodsId; //
    private Integer number; //购买数量
    private String goodsProperties; //商品属性
    private String label; //商品标签
    private Integer logisticsType; //
    private Integer inviterId; //分享人ID
    private Integer status; //状态： 1 有效，0 无效
    private Date createTime; //
    private Date updateTime; //

}
