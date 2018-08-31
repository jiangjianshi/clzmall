package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/4.
 */
@Data
@ToString
public class Address {

    private Integer id; //
    private Integer uid; //用户ID
    private Integer provinceId; //
    private Integer cityId; //
    private Integer districtId; //
    private String linkMan; //
    private String address; //
    private String mobile; //手机号码
    private String code; //邮件编码
    private Integer isDefault; //是否默认地址
    private Integer status;// 1 有效 0 删除
    private Date createTime; //
    private Date updateTime; //
}
