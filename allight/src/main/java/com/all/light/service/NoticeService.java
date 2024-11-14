package com.all.light.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.NoticeDAO;
import com.all.light.dto.NoticeDTO;
import com.all.light.util.PageUtil;

public class NoticeService {

	@Autowired
	private NoticeDAO notDAO;

	public PageUtil getPageInfo(int nowPage, String searchWord, String searchType) {
		PageUtil pInfo = new PageUtil(searchWord, searchType);
		int totalCount = notDAO.getTotalCnt(pInfo); // 검색어에 따른 게시물 수를 구함
		pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}

	public ArrayList<NoticeDTO> searchList(PageUtil pInfo, String searchWord, String searchType) {
		pInfo.setSearchWord(searchWord);
		pInfo.setSearchType(searchType);
		return notDAO.searchList(pInfo);
	}

	public void notModify(NoticeDTO notDTO) {
		notDAO.notModify(notDTO);
	}

	public NoticeDTO getNotInfo(int nno) {
		return notDAO.getNotInfo(nno);
	}

	public void notWrite(NoticeDTO notDTO) {
		notDAO.notWrite(notDTO);
	}

	public void notDelete(int nno) {
		notDAO.notDelete(nno);
	}

	public void increaseHit(int nno, HttpSession session) {
		if (increaseHitKey(nno, session) == true) {
			notDAO.increaseHit(nno);
		}
	}

	private boolean increaseHitKey(int nno, HttpSession session) {
		// 세션 arrayList map => 글번호 조회 이력
		ArrayList map = (ArrayList) session.getAttribute("NOTICEHITCHECK");
		System.out.println("map = "+map);
		// 기록이 없을 경우
		if (map == null) {
			map = new ArrayList();
			map.add(nno);
			session.setAttribute("NOTICEHITCHECK", map);
			return true;
		} // 기록이 있을 경우 -> 내 기록이 있는경우
		else if (map.contains(nno)) {
			return false;
		} else {// 기록이 있는 경우 -> 내 기록이 없는경우
			map.add(nno);
			session.setAttribute("NOTICEHITCHECK", map);
			return true;
		}
	}
}
