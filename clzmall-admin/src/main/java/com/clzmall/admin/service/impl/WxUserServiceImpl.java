package com.clzmall.admin.service.impl;

import com.clzmall.admin.mapper.WxUserMapper;
import com.clzmall.admin.service.WxUserService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.SysUser;
import com.clzmall.common.model.WxUser;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jiangjianshi on 18/10/29.
 */
@Service
@Transactional
@Slf4j
public class WxUserServiceImpl implements WxUserService{

    @Resource
    private WxUserMapper wxUserMapper;



    @Override
    public PagedList<WxUser> listWxUser() {

        List<WxUser> userList = wxUserMapper.selectAll();
        PageInfo<WxUser> pageInfo = new PageInfo<>(userList);
        PagedList<WxUser> pagedList = PagedList.newMe(pageInfo);
        return pagedList;
    }
}
