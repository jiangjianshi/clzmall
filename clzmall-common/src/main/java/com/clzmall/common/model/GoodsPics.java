package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/14.
 */
@Data
@ToString
public class GoodsPics {

    private Integer id; //
    private Integer goodsId; //
    private String picUrl; //
    private Integer isDefault; //是否是默认图片： 1是，0 否
    private Integer status; //状态：1 有效，0 删除
    private Date createTime; //
    private Date updateTime; //
}
