package com.clzmall.app.service;


import com.clzmall.common.model.WxUser;

/**
 * Created by bairong on 2018/7/29.
 */
public interface WxUserService {

    int saveUser(String code, WxUser user);

    WxUser checkLogin(String uid);
}
