package com.clzmall.admin.controller;

import javax.annotation.Resource;

import com.clzmall.admin.service.UserService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

	@Resource
	private UserService userService;


	/**
	 * 获取全部用户
	 * 
	 * @param detailDto
	 * @return
	 */
	@RequestMapping(value = "/queryAllUsers")
	public PagedList<SysUser> queryAllUsers(String account) {

		PagedList<SysUser> pageList = userService.queryAllUsers(account);
		return pageList;
	}

	/**
	 * 获取全部用户
	 * 
	 * @param detailDto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateUsers")
	public RespMsg<Integer> saveOrUpdateUsers(SysUser user) {
		
		if(!user.getPassword().equals(user.getConfirmPwd())){
			return fail("密码输入不一致");
		}
		int count = userService.addUsers(user);
		if (count == 1) {
			return success("保存成功");
		} else {
			return fail("保存失败");
		}
	}

	/**
	 * 将账号设为无效
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/setInvalidAccout")
	public RespMsg<Integer> setInvalidAccout(SysUser user) {
		
		user.setStatus(2);
		int count = userService.updateUsers(user);
		if (count == 1) {
			return success("操作成功");
		} else {
			return success("操作成功");
		}

	}
	
}
