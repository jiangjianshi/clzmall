package com.clzmall.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.clzmall.admin.entity.dto.RrightResultDto;
import com.clzmall.admin.entity.dto.TreeMenuDto;
import com.clzmall.admin.mapper.SysMenuMapper;
import com.clzmall.admin.mapper.SysUserMapper;
import com.clzmall.admin.service.UserService;
import com.clzmall.admin.controller.BaseController;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.SysMenu;
import com.clzmall.common.model.SysUser;
import com.clzmall.common.util.EncryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class UserServiceImpl extends BaseController implements UserService {

	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysMenuMapper sysMenuMapper;

	@Override
	public RespMsg<SysUser> login(HttpServletRequest req) {

		String account = req.getParameter("account");
		String password = req.getParameter("password");
		if (StringUtils.isEmpty(account)) {
			return fail("用户名为空");
		}
		if (StringUtils.isEmpty(password)) {
			return fail("密码为空");
		}
		List<SysUser> userList = sysUserMapper.selectByAccount(account.trim());
		SysUser user = null;
		Date lastLoginTime = null;// 保存上次登录时间
		if (CollectionUtils.isEmpty(userList)) {
			return fail("用户不存在");
		} else {
			user = userList.get(0);
			lastLoginTime = user.getLastLoginTime();
		}
		String encryptPassword = EncryptUtil.md5(user.getAccount() + user.getSalt() + password.trim());
		if (!encryptPassword.equals(user.getPassword())) {
			return fail("密码错误");
		}
		String token = UUID.randomUUID().toString().replaceAll("\\-", "");

		SysUser loginUser = new SysUser();
		loginUser.setId(user.getId());
		loginUser.setToken(token);
		loginUser.setLoginIp(getIpAddr(req));
		loginUser.setLastLoginTime(new Date());
		sysUserMapper.updateSelective(loginUser);

		HttpSession session = req.getSession();
		loginUser.setLastLoginTime(lastLoginTime);
		loginUser.setRealName(user.getRealName());
		session.setAttribute(session.getId(), loginUser);
		return success("登录成功", loginUser);
	}

	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}


	@Override
	public RrightResultDto getTreeMenus(Integer userId) {

		List<SysMenu> menuList = sysMenuMapper.selectUserAllRight(userId);
		List<TreeMenuDto> treeList = new ArrayList<TreeMenuDto>();//菜单列表
		List<String> rightCodeList = new ArrayList<String>();//权限编码列表
		
		RrightResultDto rightDto = new RrightResultDto();
		if(CollectionUtils.isEmpty(menuList)){
			TreeMenuDto tree = new TreeMenuDto();
			tree.setId("1");
			tree.setPId("0");
			tree.setName("此用户无任何权限"); 
			treeList.add(tree);
		}
		for (SysMenu menu : menuList) {
			if(menu.getRightType() == 1){
				TreeMenuDto tree = new TreeMenuDto();
				tree.setId(menu.getThisCode());
				tree.setPId(menu.getParentCode());
				tree.setName(menu.getRightName());
				tree.setMenuUrl(menu.getRightUrl());
				tree.setRightType(0);
				tree.setOpen(true);
				treeList.add(tree);
			}
			rightCodeList.add(menu.getThisCode());
		}
		rightDto.setTreeMenuList(treeList);
		rightDto.setRightCodeList(rightCodeList);
		return rightDto;
	}

	@Override
	public PagedList<SysUser> queryAllUsers(String account) {
		
		List<SysUser> userList = sysUserMapper.selectAll(account);
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(userList);
		PagedList<SysUser> pagedList = PagedList.newMe(pageInfo);
		return pagedList;
	}

	@Override
	public int addUsers(SysUser user) {
		
		if (user.getId() != null) {//如果有则更新
			String encryptPassword = EncryptUtil.md5(user.getAccount() + user.getSalt() + user.getPassword().trim());
			user.setPassword(encryptPassword);
			return sysUserMapper.updateSelective(user);
		}
		String salt = UUID.randomUUID().toString().replaceAll("\\-", "");
		String encryptPassword = EncryptUtil.md5(user.getAccount() + salt + user.getPassword().trim());
		user.setSalt(salt);
		user.setPassword(encryptPassword);
		user.setStatus(1);
		return sysUserMapper.saveUser(user);
	}

	@Override
	public int updateUsers(SysUser user) {
		
		return sysUserMapper.updateSelective(user);
	}

}
