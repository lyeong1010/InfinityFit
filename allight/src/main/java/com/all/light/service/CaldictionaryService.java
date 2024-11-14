package com.all.light.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.CaldictionaryDAO;
import com.all.light.dto.CaldictionaryDTO;
import com.all.light.util.PageUtil;

@Service
public class CaldictionaryService {

	@Autowired
	CaldictionaryDAO calDAO;

	// 전체 게시물 수 조회 - 음식
	public PageUtil getPageInfo(int nowPage, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getPageInfo() 진입");
		
		int totalCount = calDAO.getTotalCnt(searchWord);

		PageUtil pInfo = new PageUtil(nowPage, totalCount, 15, 5);
		// PageUtil(보고싶은페이지, 전체게시물수);
		// 검색어에 따른 총 게시물 수를 구하고 페이지 정보를 리턴함
		return pInfo;
	}
	// 검색 및 리스트 조회 - 음식
	public ArrayList<CaldictionaryDTO> getListView(PageUtil pInfo, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getListView () 진입");
		pInfo.setSearchWord(searchWord);
		ArrayList<CaldictionaryDTO> list = calDAO.searchList(pInfo);
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() list " + list);
		return list;
	}
	
	// 전체 게시물 수 조회 - 운동
	public PageUtil getPageInfo2(int nowPage, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getPageInfo() 진입");
		
		int totalCount = calDAO.getTotalCnt2(searchWord);

		PageUtil pInfo = new PageUtil(nowPage, totalCount, 15, 5);
		// PageUtil(보고싶은페이지, 전체게시물수);
		// 검색어에 따른 총 게시물 수를 구하고 페이지 정보를 리턴함
		return pInfo;
	}
	
	// 검색 및 리스트 조회 - 운동
	public ArrayList<CaldictionaryDTO> getListView2(PageUtil pInfo, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() 진입");
		pInfo.setSearchWord(searchWord);
		ArrayList<CaldictionaryDTO> list = calDAO.searchList2(pInfo);
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() list " + list);
		return list;
	}
	
	
	// 전체 게시물 수 조회 - 관리자
	public PageUtil getPageInfo3(int nowPage, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getPageInfo() 진입");
		
		int totalCount = calDAO.getTotalCnt3(searchWord);

		PageUtil pInfo = new PageUtil(nowPage, totalCount, 15, 5);
		// PageUtil(보고싶은페이지, 전체게시물수);
		// 검색어에 따른 총 게시물 수를 구하고 페이지 정보를 리턴함
		return pInfo;
	}
	// 검색 및 리스트 조회 - 관리자
	public ArrayList<CaldictionaryDTO> getListView3(PageUtil pInfo, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() 진입");
		pInfo.setSearchWord(searchWord);
		ArrayList<CaldictionaryDTO> list = calDAO.searchList3(pInfo);
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() list " + list);
		return list;
	}
	public void delete(int cdno) {
		calDAO.delete(cdno);
	}
	public void insert(CaldictionaryDTO calDTO) {
		calDAO.insert(calDTO);
	}
	
}
