package com.all.light.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.MemberDTO;
import com.all.light.service.CalrecipeService;

@Controller
public class CalrecipeController {
	@Autowired
	private CalrecipeService creSVC;
	
	//칼로리처방
	@RequestMapping("/calorie_recipe")
	public ModelAndView recipe(ModelAndView mv,HttpSession session,MemberDTO mdto) {
		if (session.getAttribute("MNO")!=null) {
			CalrecipeDTO get=creSVC.getRecipe(session);
			System.out.println("g"+get);
			if(get!=null){
				System.out.println("ggggggggggg"+get);
				RedirectView rv=new RedirectView("./recipe.com");
				mv.setView(rv);
			}else {
				int mno=Integer.parseInt(String.valueOf(session.getAttribute("MNO")));
				MemberDTO list=creSVC.memberCheck(mno);
				mv.addObject("LIST",list);
				mv.setViewName("diary/user/calrecipe/before");
			}
		}else {
			mv.setViewName("diary/user/calrecipe/before");
		}
		return mv;
	}
	
	//계산
	@RequestMapping("/recipeCheck")
	public ModelAndView recipeCheck(ModelAndView mv,HttpSession session,CalrecipeDTO cdto) {
		System.out.println("cdto"+cdto);
		CalrecipeDTO list=creSVC.recipeCheck(session,cdto);
		mv.addObject("LIST", list);
		mv.setViewName("diary/user/calrecipe/after");
		return mv;
	}
	
	//있으면 값가져오기
	@RequestMapping("/recipe")
	public ModelAndView recipe(ModelAndView mv,HttpSession session,CalrecipeDTO cdto) {
		CalrecipeDTO list=creSVC.getRecipe(session);
		mv.addObject("LIST", list);
		mv.setViewName("diary/user/calrecipe/after");
		return mv;
	}
	
	//다시받기
	@RequestMapping("/recipeRe")
	public ModelAndView recipeRe(ModelAndView mv,HttpSession session,CalrecipeDTO cdto) {
		if (session.getAttribute("MNO")!=null) {
			int mno=Integer.parseInt(String.valueOf(session.getAttribute("MNO")));
			MemberDTO list=creSVC.memberCheck(mno);
			mv.addObject("LIST",list);
		}
		mv.setViewName("diary/user/calrecipe/before");
		return mv;
	}
	
}
