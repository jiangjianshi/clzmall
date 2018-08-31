package com.clzmall.common.model;

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
    private String backgroundImgUrl; //
    private String jumpUrl; //
    private String title; //
    private Integer sortNo; //
    private Integer status; //
    private Date createTime; //
    private Date updateTime; //
}
