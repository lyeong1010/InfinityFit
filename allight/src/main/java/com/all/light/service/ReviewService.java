package com.all.light.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.ReviewDAO;
import com.all.light.dto.ReviewDTO;
import com.all.light.util.PageUtil;

@Service
public class ReviewService {

	@Autowired
	ReviewDAO revDAO;

	public PageUtil getPageInfo(String id, int nowPage, String searchWord) {
		PageUtil pInfo = new PageUtil(searchWord);
		pInfo.setRid(id);
		int totalCount = revDAO.getTotalCnt(pInfo); // 게시물 수를 구함
		pInfo = new PageUtil(nowPage, totalCount); // 게시물 수에 따른 페이징정보를 구함
		pInfo.setRid(id);
		pInfo.setSearchWord(searchWord);
		System.out.println("reviewService.getPageInfo. pInfo = " + pInfo);
		return pInfo;
	}

	public ArrayList<ReviewDTO> getList(PageUtil pInfo) {
		System.out.println("reviewService.getList. pInfo = " + pInfo);
		return revDAO.getList(pInfo);
	}

	public void reviewWrite(ReviewDTO revDTO) {
		revDAO.reviewWrite(revDTO);
	}

	public void reviewDelete(ReviewDTO revDTO) {
		revDAO.reviewDelete(revDTO);
	}

	public ReviewDTO getReviewInfo(int ino, String id) {
		ReviewDTO revDTO = new ReviewDTO(ino, id);
		return revDAO.getReviewInfo(revDTO);
	}

	public void reviewUpdate(ReviewDTO revDTO) {
		revDAO.reviewUpdate(revDTO);
	}

	public PageUtil getPageInfoCorp(String name, int nowPage, String searchWord) {
		PageUtil pInfo = new PageUtil(searchWord);
		pInfo.setConame(name);
		int totalCount = revDAO.getTotalCntCorp(pInfo); // 게시물 수를 구함
		pInfo = new PageUtil(nowPage, totalCount); // 게시물 수에 따른 페이징정보를 구함
		pInfo.setConame(name);
		pInfo.setSearchWord(searchWord);
		System.out.println("reviewService.getPageInfo.pInfo = " + pInfo);
		return pInfo;
	}

	public ArrayList<ReviewDTO> getListCorp(PageUtil pInfo) {
		return revDAO.getListCorp(pInfo);
	}

	public void reviewDeleteCorp(ReviewDTO revDTO) {
		revDAO.reviewDeleteCorp(revDTO);
	}
}
