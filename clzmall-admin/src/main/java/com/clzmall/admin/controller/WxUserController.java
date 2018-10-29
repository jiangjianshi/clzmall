package com.clzmall.admin.controller;

import com.clzmall.admin.service.WxUserService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.SysUser;
import com.clzmall.common.model.WxUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by jiangjianshi on 18/10/29.
 */
@RestController
@RequestMapping("/wx")
public class WxUserController {

    @Resource
    private WxUserService wxUserService;


    /**
     *
     * @param phone
     * @param nickName
     * @return
     */
    @RequestMapping(value = "/queryWxUsers")
    public PagedList<WxUser> queryWxUsers(String phone, String nickName) {

        PagedList<WxUser> pageList = wxUserService.listWxUser();
        return pageList;
    }

}
