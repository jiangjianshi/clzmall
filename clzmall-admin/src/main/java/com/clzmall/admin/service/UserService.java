package com.clzmall.admin.service;

import com.clzmall.admin.entity.dto.RrightResultDto;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.SysUser;

import javax.servlet.http.HttpServletRequest;


public interface UserService {
	
	RespMsg<SysUser> login(HttpServletRequest req);
	
	RrightResultDto getTreeMenus(Integer userId);
	
	PagedList<SysUser> queryAllUsers(String account);
	
	int addUsers(SysUser user);
	
	int updateUsers(SysUser user);
}
