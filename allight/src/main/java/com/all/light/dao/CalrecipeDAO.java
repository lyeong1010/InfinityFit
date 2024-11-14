package com.all.light.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.MemberDTO;

public class CalrecipeDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;
	
	public MemberDTO memberCheck(int mno) {
		return session.selectOne("calrecipe.memberCheck", mno);
	}

	public void recipeCheck(CalrecipeDTO cdto) {
		cdto.setCrterm(cdto.getCrterm()+cdto.getType());
		session.insert("calrecipe.recipeCheck", cdto);
	}
	
	public CalrecipeDTO getRecipe(String mid) {
		return session.selectOne("calrecipe.getRecipe", mid);
	}
}
