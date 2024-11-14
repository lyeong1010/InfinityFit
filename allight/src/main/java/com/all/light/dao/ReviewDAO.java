package com.all.light.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.ReviewDTO;
import com.all.light.util.PageUtil;

public class ReviewDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	public int getTotalCnt(PageUtil pInfo) {
		return session.selectOne("review.getTotalCnt", pInfo);
	}
	
	public ArrayList getList(PageUtil pInfo) {
		return (ArrayList) session.selectList("review.getList", pInfo); 
	}

	public void reviewWrite(ReviewDTO revDTO) {
		session.insert("review.reviewWrite",revDTO);
	}

	public void reviewDelete(ReviewDTO revDTO) {
		System.out.println(revDTO);
		session.delete("review.reviewDelete",revDTO);
	}

	public ReviewDTO getReviewInfo(ReviewDTO revDTO) {
		return session.selectOne("review.getReviewInfo", revDTO);
	}

	public void reviewUpdate(ReviewDTO revDTO) {
		session.update("review.reviewUpdate",revDTO);
	}

	public int getTotalCntCorp(PageUtil pInfo) {
		return session.selectOne("review.getTotalCntCorp", pInfo);
	}
	
	public ArrayList<ReviewDTO> getListCorp(PageUtil pInfo) {
		return (ArrayList) session.selectList("review.getListCorp", pInfo); 
	}

	public void reviewDeleteCorp(ReviewDTO revDTO) {
		session.delete("review.reviewDeleteCorp",revDTO);
	}
}
