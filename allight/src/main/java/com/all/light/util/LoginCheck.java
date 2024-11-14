package com.all.light.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/* 인터셉터를 할때 실행할 클래스를 제잓한다
 * xml환경설정파일에 인터셉터 클래스를 틍록하면서 인터셉터가 발생되는 상황을 지정
 * 로그인체크를 통해 권한 체크
 * 
 * */
/*인터셉터클래스가 되기 위해서는
1.	HandlerInterceptorAdapter 상속받아야 한다
2.	 preHandle() 오버라이딩해야 한다*/

public class LoginCheck extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		String id=(String) session.getAttribute("MID");
		if(id==null||id.length()==0){//로그인X
			System.out.println("LoginCheck 로그인폼으로 이동요청");
			session.setAttribute("LoginCheck","fail");
			System.out.println("권한 없음");
			response.sendRedirect(request.getContextPath()+"/login.com");
			return false;
		}else {//로그인O
			return true;
		}
	}
	
}
