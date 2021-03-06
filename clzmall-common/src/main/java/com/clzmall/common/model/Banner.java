package com.clzmall.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/7.
 */
@Data
@ToString
public class Banner {


    private Integer id; //
    private Integer shopId; //
    private String backgroundImgUrl; //
    private String jumpUrl; //
    private Integer goodsId; //
    private String title; //
    private Integer sortNo; //
    private Integer status; //

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //
}
