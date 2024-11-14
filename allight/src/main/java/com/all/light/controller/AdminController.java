package com.all.light.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AdminController {
	
	//권한이 없을 시 접속 실패
	@RequestMapping("/fail")
	public ModelAndView connectFail(HttpSession session,ModelAndView mv,RedirectView rv) {
		System.out.println("AdminController.connectFail\n -- non-authority access");
		mv.setViewName("common/admin/connectfail");
		return mv;
	}
	
	@RequestMapping("/admin")
	public ModelAndView adminHome(HttpSession session,ModelAndView mv,RedirectView rv) {
		System.out.println("AdminController.adminHome");
		mv.setViewName("common/admin/home");
		return mv;
	}
	

	
}
