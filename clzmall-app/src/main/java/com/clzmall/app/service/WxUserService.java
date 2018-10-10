package com.clzmall.app.service;


import com.clzmall.common.model.WxUser;

/**
 * Created by bairong on 2018/7/29.
 */
public interface WxUserService {

    int saveUser(Integer inviterUid, String code, WxUser user);

    WxUser checkLogin(String uid);

    Integer getUserScore(String uid);


    WxUser getUserInfo(String uid);

    boolean bindMobile(String sessionKey, Integer uid, String encryptedData, String iv);
}
