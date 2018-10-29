package com.clzmall.admin.service;

import com.clzmall.common.common.PagedList;
import com.clzmall.common.model.WxUser;

import java.util.List;

/**
 * Created by jiangjianshi on 18/10/29.
 */
public interface WxUserService {

    PagedList<WxUser> listWxUser();
}
