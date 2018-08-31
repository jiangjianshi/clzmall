
package com.clzmall.app.interceptor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 拦截器
 */
@Component
public class PageInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String page = "1"; // 第几页
		String rows = "20";// 每页显示几条
		if (StringUtil.isNotEmpty(request.getParameter("page"))) {
			page = request.getParameter("page");
		}
		if (StringUtil.isNotEmpty(request.getParameter("rows"))) {
			rows = request.getParameter("rows");
		}
		if (StringUtil.isNotEmpty(request.getParameter("page"))
				|| StringUtil.isNotEmpty(request.getParameter("rows"))) {
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		}

		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
