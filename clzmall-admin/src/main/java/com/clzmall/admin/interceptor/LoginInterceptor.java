package com.clzmall.admin.interceptor;

import com.clzmall.admin.mapper.SysUserMapper;
import com.clzmall.common.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 登录拦截器
 * 
 * @author jjs
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			String uri = req.getRequestURI();
			List<String> exculdeList = Arrays.asList("/", "/error", "/index", "/login","/logout", "/IncJs.a");// 无需校验的URI
			if (exculdeList.contains(uri)) {
				return true;
			}
			HttpSession session = req.getSession();
			SysUser loginUser = (SysUser) session.getAttribute(session.getId());
			if (loginUser == null) {
				res.sendRedirect("/");
				return false;
			}
			SysUser dbUser = sysUserMapper.selectByPrimaryKey(loginUser.getId());
			if (loginUser.getToken().equals(dbUser.getToken())) {
				return true;
			} else {
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/html; charset=utf-8");
				PrintWriter writer = res.getWriter();
				session.invalidate();
				writer.write("<center>登录token已过期，请重新登录.</center>");
				return false;
			}
		} else {
			return true;
		}
//		return  true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mv)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception ex)
			throws Exception {

	}
}
