package com.all.light.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CorpCheck extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		String id=(String) session.getAttribute("COID");
		if(id==null||id.length()==0){//로그인X
			System.out.println("CorpCheck 로그인폼으로 이동요청");
			session.setAttribute("LoginCheck","fail");
			System.out.println("권한 없음");
			response.sendRedirect(request.getContextPath()+"/login.com");
			return false;
		}else {//로그인O
			return true;
		}
	}
	
}
