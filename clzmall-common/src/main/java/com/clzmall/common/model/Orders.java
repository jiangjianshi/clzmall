package com.clzmall.common.model;

import com.clzmall.common.enums.OrderTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/5.
 */
@Data
@ToString
public class Orders {

    private Integer id; //
    private String orderCode; // 订单编号
    private Integer uid; //用户id
    private Integer addressId; //地址id
    private BigDecimal realAmount; //订单真是价格
    private int useScore; //使用积分
    private int gainScore; //获得积分
    private String remark; //评论
    private Integer status; //订单状态：0：待付款， 1代发货，2待收货，3 待评价，4 已完成 ， -1 已取消
    private String statusStr;
    private int logisticsFee;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm:ss", timezone = "GMT+8")
    private Date createTime; //订单创建时间

    @JsonFormat(pattern = "yyyy-MM-dd H:mm:ss", timezone = "GMT+8")
    private Date updateTime; //更新时间

    public String getStatusStr() {
        return OrderTypeEnum.getDesc(status);
    }
}
