package com.all.light.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.QuestionDAO;
import com.all.light.dto.ItemQuestionDTO;
import com.all.light.dto.NoticeDTO;
import com.all.light.dto.QuestionDTO;
import com.all.light.util.PageUtil;

public class QuestionService {
	@Autowired
	private QuestionDAO qesDAO;
	//기업
	public ArrayList<QuestionDTO> list(PageUtil pinfo) {
		return qesDAO.list(pinfo);
	}

	public PageUtil getPageInfoById(int nowPage, String id) {
		int totalCount = qesDAO.getTotalCntById(id);
		PageUtil pinfo = new PageUtil(nowPage, totalCount);
		return pinfo; 
	}

	public void insertWrite(QuestionDTO qdto, HttpSession session) {
		qdto.setQid((String) session.getAttribute("COID"));
		qesDAO.insertWrite(qdto);		
	}

	public QuestionDTO detail(QuestionDTO qdto) {
		return qesDAO.datail(qdto);
	}

	public ArrayList<QuestionDTO> detailcomm(QuestionDTO qdto) {
		return qesDAO.detailcomm(qdto);
	}

	public void delete(QuestionDTO qdto) {
		qesDAO.delete(qdto);
	}

	public void update(QuestionDTO qdto, HttpSession session) {
		qesDAO.update(qdto);
	}
	
	//관리자
	public PageUtil getPageInfo(int nowPage) {
		int totalCount = qesDAO.getTotalCnt();
		PageUtil pinfo = new PageUtil(nowPage, totalCount);
		return pinfo;
	}
	
	public PageUtil getPageInfoUser(int nowPage, String searchWord, String searchType) {
		PageUtil pInfo = new PageUtil(searchWord, searchType);
		int totalCount = qesDAO.getTotalCntUser(pInfo);
		PageUtil pInfo1 = new PageUtil(nowPage, totalCount);
		pInfo1.setSearchWord(searchWord);
		pInfo1.setSearchType(searchType);
		return pInfo1;
	}
	
	public ArrayList<QuestionDTO> totalList(PageUtil pinfo) {
		return qesDAO.totalList(pinfo);
	}

	public PageUtil getPageInfoByTitle(int nowPage, String word) {
		int totalCount = qesDAO.getTotalCntByTitle(word);
		PageUtil pinfo = new PageUtil(nowPage, totalCount);
		return pinfo;
	}
	public ArrayList<QuestionDTO> listByTitle(PageUtil pinfo) {
		return qesDAO.listByTitle(pinfo);
	}
	
	public PageUtil searchPageInfoById(int nowPage, String word) {
		int totalCount = qesDAO.searchPageInfoById(word);
		PageUtil pinfo = new PageUtil(nowPage, totalCount);
		return pinfo;
	}
	public ArrayList<QuestionDTO> listById(PageUtil pinfo) {
		return qesDAO.listById(pinfo);
	}

	//댓글
	public void insertComm(QuestionDTO qdto) {
		qesDAO.insertComm(qdto);
	}

	public void deleteComm(QuestionDTO qdto) {
		qesDAO.deleteComm(qdto);
	}

	//유저
	public void userInsertWrite(QuestionDTO qdto, HttpSession session) {
		qdto.setQid((String)session.getAttribute("MID"));
		qdto.setQnick((String)session.getAttribute("MNICK"));
		qesDAO.userInsert(qdto);
	}

	public PageUtil getPageInfoBySearch(int nowPage, String searchWord, String searchType) {
		PageUtil pInfo = new PageUtil(searchWord, searchType);
		int totalCount = qesDAO.getTotalCnt(pInfo); // 검색어에 따른 게시물 수를 구함
		pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}

	public ArrayList<QuestionDTO> searchList(PageUtil pInfo, String searchWord, String searchType) {
		pInfo.setSearchWord(searchWord);
		pInfo.setSearchType(searchType);
		return qesDAO.searchList(pInfo);
	}

	public void update(QuestionDTO qdto) {
		qesDAO.update(qdto);
	}

	public void delete(int qno) {
		qesDAO.delete(qno);
	}

	public ArrayList<QuestionDTO> totalListUser(PageUtil pinfo) {
		return qesDAO.totalListUser(pinfo);
	}


	public PageUtil getPageInfoMyPageUser(PageUtil pInfo) {
		int totalCount = qesDAO.totalCntUserMyPage(pInfo);
		int nowPage = pInfo.getNowPage();
		pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}

	public ArrayList<QuestionDTO> searchListMyPageUser(PageUtil pInfo, String searchWord, String searchType, String qid) {
		pInfo.setSearchType(searchType);
		pInfo.setSearchWord(searchWord);
		pInfo.setQid(qid);
		return qesDAO.searchListMyPageUser(pInfo);
	}

	
	// 기업 답변 - 류지혁
	public void insertiq(ItemQuestionDTO iqDTO) {
		qesDAO.insertiq(iqDTO);
	}
	// 기업 답변 삭제 - 류지혁
	public void deleteiq(int iqcno) {
		qesDAO.deleteiq(iqcno);
	}

	//댓글 페이징 처리 - 유태우
	public PageUtil getCommPageInfo(int qno, int commPage) {
		int Cnt = qesDAO.getTotalCntOfComm(qno);
		PageUtil pInfo = new PageUtil(commPage, Cnt);
		pInfo.setQno(qno);
		return pInfo;
	}

	public ArrayList<QuestionDTO> getCommDetail(PageUtil pInfo) {
		return qesDAO.getCommDetail(pInfo);
	}





}
