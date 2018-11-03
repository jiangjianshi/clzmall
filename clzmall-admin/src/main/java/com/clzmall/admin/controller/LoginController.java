package com.clzmall.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.clzmall.admin.entity.dto.RrightResultDto;
import com.clzmall.admin.entity.dto.TreeMenuDto;
import com.clzmall.admin.service.UserService;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController extends BaseController {

	@Resource
	private UserService userService;

	@RequestMapping(path = "/")
	public String index(HttpServletRequest req, HttpServletResponse res) {
		return "login";
	}

	@RequestMapping(path = "/main")
	public String main(HttpServletRequest req, HttpServletResponse res) {
		return "main";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseBody
	public RespMsg<SysUser> login(HttpServletRequest req) {

		return userService.login(req);
	}

	@RequestMapping(path = "/getLoginUser", method = RequestMethod.POST)
	@ResponseBody
	public RespMsg<SysUser> getLoginUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SysUser user = (SysUser) session.getAttribute(session.getId());
		return success("获取成功", user);
	}

	@RequestMapping(path = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public RespMsg<SysUser> logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return success("登出成功");
	}

	@RequestMapping(path = "/getTreeMenu", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeMenuDto> getTreeMenu(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SysUser user = (SysUser) session.getAttribute(session.getId());
		RrightResultDto rightDto = userService.getTreeMenus(user.getId());
		//获取用户所有权限，缓存到session中，用做BaseController校验用户权限
		session.setAttribute(user.getToken(), rightDto.getRightCodeList());
		return rightDto.getTreeMenuList();
	}
}
