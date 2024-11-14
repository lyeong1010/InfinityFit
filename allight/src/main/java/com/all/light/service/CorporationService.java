package com.all.light.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.CorporationDAO;
import com.all.light.dto.CorporationDTO;
import com.all.light.util.PageUtil;

public class CorporationService {
	@Autowired
	private CorporationDAO corDAO;
	
	public HashMap login(CorporationDTO cordto, HttpSession session, int cnt) {
		System.out.println("CorporationService");
		//일반로그인
		HashMap map=new HashMap();
		map.put("coid",cordto.getCoid());
		map.put("copw",cordto.getCopw());
		HashMap result=corDAO.login(map);
		if(result==null || result.size()==0) {
			cnt=cnt+1;
			session.setAttribute("ccnt", cnt);
			System.out.println("로그인실패");
		}else{
			//로그인성공
			System.out.println("로그인성공");
			session.setAttribute("CONO",result.get("CONO"));
			session.setAttribute("COID",result.get("COID"));
			session.setAttribute("CONAME",result.get("CONAME"));	
		}
		return result;
		
	}
	
	public void logout(HttpSession session) {
		if(session.getAttribute("COID")!=null) {
			session.invalidate();
		}else {
			System.out.println("null logout");
		}
	}

	//페이징 처리 및 검색을 위한 메소드
	public PageUtil getPageInfo(int nowPage, String searchWord) {
		int totalCount = corDAO.getTotalCnt(searchWord);	
		
		// PageUtil(보고싶은페이지, 전체게시물수);
		PageUtil pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}

	public ArrayList<CorporationDTO> searchList(PageUtil pInfo, String searchWord) {
		pInfo.setSearchWord(searchWord);
		return corDAO.getSearchList(pInfo);
	}

	public CorporationDTO getCorpInfo(int cono) {
		return corDAO.getCorpInfo(cono);
	}

	public void corpModify(CorporationDTO corDTO) {
		corDAO.corpModify(corDTO);
	}

	public void corpDelete(int cono) {
		corDAO.corpDelete(cono);
	}

	public void corpInsert(CorporationDTO corDTO) {
		corDAO.corpInsert(corDTO);
	}

	public CorporationDTO getCorpID(CorporationDTO corDTO) {
		return corDAO.getCorpID(corDTO);
	}

	public int corpModify2(CorporationDTO corDTO) {
		int i=corDAO.corpModify2(corDTO);
		return i;
	}

}