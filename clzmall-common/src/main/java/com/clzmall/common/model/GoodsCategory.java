package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/7.
 */
@Data
@ToString
public class GoodsCategory {

    private Integer id; //
    private String catCode; //
    private String catName; //类别名称
    private String catDesc; //
    private Date createTime; //
    private Date updateTime; //
}
