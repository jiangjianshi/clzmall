package com.clzmall.common.enums;


import com.clzmall.common.common.Consts;

/**
 * Created by jiangjianshi on 18/8/5.
 */
public enum OrderTypeEnum {


    WAITING_PAY(0, "待付款"),
    WAITING_SEND(1, "待发货"),
    WAITING_RECEIVE(2, "待收货"),
    WAITING_COMMENT(3, "待评价"),
    DONE(4, "已完成");

    private Integer code;
    private String desc;


    OrderTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(Integer code) {

        for (OrderTypeEnum type : OrderTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return Consts.UNKNOWN;
    }

}
