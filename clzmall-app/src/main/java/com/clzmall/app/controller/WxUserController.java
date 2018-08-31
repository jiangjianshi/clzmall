package com.clzmall.app.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.clzmall.app.service.WxUserService;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.WxUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bairong on 2018/7/29.
 */
@Slf4j
@RestController
@RequestMapping("/user/")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;


    @RequestMapping("registerUser")
    public RespMsg<Map> registerUser(String code, String userinfo) {

        try {
            WxUser user = null;
            if (!StringUtils.isEmpty(userinfo)) {
                user = JSON.parseObject(userinfo, WxUser.class);
                user.setToken(code);
            }
            int uid = wxUserService.saveUser(code, user);
            if (uid != 0) {
                Map map = new HashMap();
                map.put("token", code);
                map.put("uid", uid);

                return success("注册成功", map);
            } else {
                return fail("注册失败");
            }
        } catch (Exception e) {
            log.error("注册异常", e);
            return fail("注册异常");
        }

    }

    @RequestMapping("checkToken")
    public RespMsg<String> checkToken(String uid, String token) {

        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)) {
            return fail("未登录");
        }
        WxUser user = wxUserService.checkLogin(uid);
        if (user != null) {
            return success("已登录");
        } else {
            return fail("未登录");
        }

    }


}
