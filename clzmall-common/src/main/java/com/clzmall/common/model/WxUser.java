package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/8/1.
 */
@Data
@ToString
public class WxUser {

    private Integer id; //用户_id
    private Integer score; //用户积分
    private String mobile; //手机号
    private Integer inviterUid; //邀请人ID
    private String openId; //微信的open_id
    private String nickName; //用户昵称
    private Integer gender; //性别
    private String city; //城市
    private String province; //省份
    private String country; //国家
    private String language; //用户的语言
    private String userRemark; //用户_备注
    private Integer grayStatus; //灰度用户状态，0是正常登陆用户，1是不需要登陆的用户
    private String avatarUrl; //用户头像
    private String token; // 登录token
    private Date loginTime; //登录时间
    private Date createTime; //
    private Date updateTime; //


}
