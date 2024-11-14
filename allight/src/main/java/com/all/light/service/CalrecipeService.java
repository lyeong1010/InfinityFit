package com.all.light.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.CalrecipeDAO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.MemberDTO;

public class CalrecipeService {
	@Autowired
	private CalrecipeDAO creDAO;
	
	public MemberDTO memberCheck(int mno) {
		return creDAO.memberCheck(mno);
	}
	
	public CalrecipeDTO recipeCheck(HttpSession session, CalrecipeDTO cdto) {
		double hi=cdto.getCrheight();
		int we=cdto.getCrweight();
		
		//BMI계산
		double bmi=we/Math.pow(hi*0.01, 2);
		bmi=Math.round(bmi*100)/100.0;
		cdto.setCrbmi(bmi);
		
		//나이계산
		int byy=cdto.getBirth().getYear();
		Date today=new Date();
		int tyy=today.getYear();
		int age=tyy-byy;
		cdto.setAge(age);
		
		//기초대사량 계산
		String se=cdto.getSex();
		double bmr=0; 
		if(se.equals("F")) {
			bmr=655.1 + 9.563*we + 1.85*hi - 4.676*age;
		}else if(se.equals("M")) {
			bmr=66.5 + 13.75*we + 5.003*hi - 6.755*age;
		}
		
		switch(cdto.getCractive()){
			case 1://앉아서
				bmr=bmr*1.2;
				break;
			case 2://가벼운
				bmr=bmr*1.375;
				break;
			case 3://적당한
				bmr=bmr*1.55;
				break;
			case 4://많은
				bmr=bmr*1.725;
				break;
			case 5://매우많은
				bmr=bmr*1.9;
				break;
		}
		bmr=Math.round(bmr*100)/100.0;
		cdto.setCrbmr(bmr);
		
		//감량기간에 따른 하루 칼로리 계산
		int mcal=(we-cdto.getCrgoalweight())*7000;
		int term=Integer.parseInt(cdto.getCrterm());
		if(cdto.getType().equals("개월")) {//개월
			term=term*30;
		}else if(cdto.getType().equals("주")) {//주
			term=term*7;
		}
		double cal=bmr-(mcal/term);
		cal=Math.round(cal*100)/100.0;
		cdto.setCrcal(cal);
		
		if(session.getAttribute("MID")!=null) {
			cdto.setMid((String) session.getAttribute("MID"));
			creDAO.recipeCheck(cdto);	
		}
		System.out.println("CalrecipeService recipeCheck \n"+cdto);
		session.setAttribute("CAL", cdto.getCrcal());
		return cdto;
	}

	public CalrecipeDTO getRecipe(HttpSession session) {
		String mid=(String) session.getAttribute("MID");
		return creDAO.getRecipe(mid);
	}
}
