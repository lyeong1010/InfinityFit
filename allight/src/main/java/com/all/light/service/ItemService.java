package com.all.light.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.type.IntegerTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.ItemDAO;
import com.all.light.dto.ItemDTO;
import com.all.light.util.PageUtil;

@Service
public class ItemService {
		
	//이 클래스는  상품게시판의 요청처리를 하는 서비스클래스이다
	//ItemDAO<->SQL.xml->DB

	@Autowired
	ItemDAO itemDAO;
	
	
	// 상품추가하기
	public void insertItem(ItemDTO iDTO, HttpSession session, ArrayList list) {
		System.out.println("Itemservice의 insertItem() 진입");
		int cono = Integer.parseInt(String.valueOf(session.getAttribute("CONO")));
		iDTO.setCono(cono);
		System.out.println(cono);
		
		itemDAO.insertItem(iDTO, "item");
		System.out.println("Itemservice의 insertItem() iDto =" + iDTO);
		
		for(int i=0; i<list.size() ;i++) {
			HashMap map = (HashMap)list.get(i);

			iDTO.setPath((String)map.get("path"));
			iDTO.setOriName((String)map.get("oriName"));
			iDTO.setSaveName((String)map.get("saveName"));
			iDTO.setLen((Long)map.get("len"));
			iDTO.setSelect((Integer)map.get("select"));
			//iDTO.setIno(itemDTO.getIno());
			
			System.out.println("iDTO=" + iDTO);
			//DAO의 함수호출
			itemDAO.insertItem(iDTO,"iInfo");
		}
	}


	
	// 상품리스트 페이징관련 정보
	public PageUtil getPageInfo(int nowPage) {
		System.out.println("서비스 상품리스트 페이징 관련 - getPageInfo() 진입");
		int totalCount = itemDAO.getTotalCnt();
		PageUtil pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}
	
	// 상품리스트, 검색 페이징관련 정보
	public PageUtil getPageInfo(int nowPage, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getPageInfo() 진입");
		
		int totalCount = itemDAO.getTotalCnt(searchWord);

		PageUtil pInfo = new PageUtil(nowPage, totalCount);
		// PageUtil(보고싶은페이지, 전체게시물수);
		// 검색어에 따른 총 게시물 수를 구하고 페이지 정보를 리턴함
		return pInfo;
	}
	
	// 상품 리스트 페이지에 출력할 상품 목록 조회
	public ArrayList<ItemDTO> getListView(PageUtil pInfo, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() 진입");
		pInfo.setSearchWord(searchWord);
		
		/*
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() pInfo=" + pInfo);
		return itemDAO.searchList(pInfo);
		*/
		
		// /*
		ArrayList<ItemDTO> list = itemDAO.searchList(pInfo);
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() list " + list);
	
		return list;
		// */
	}
	

	
	public ItemDTO itemInfo(int ino) {
		return itemDAO.itemInfo(ino);
	}
	
	// 상품 상세보기
	public ItemDTO detailView(int ino) {
		System.out.println("서비스 상품상세보기 - detailView()");
		ItemDTO iDTO = itemDAO.detailView(ino);
		System.out.println("서비스 상품상세보기 - detailView() iDTO = " + iDTO);
		return iDTO;
	}
	
	// 상품 이미지
	public ItemDTO detailImg(int ino) {
		System.out.println("서비스 이미지 - detailImg()");
		ItemDTO iDTO2 = itemDAO.detailImg(ino);
		return iDTO2;
	}

	// 상품삭제하기
	public void deleteItem(ItemDTO itemDTO) {
		System.out.println("서비스 상품삭제 deleteItem() 진입");
		itemDAO.deleteItem(itemDTO);
		System.out.println("서비스 상품삭제 처리완료");
	}


	//파일검색- 페이지정보처리
	public PageUtil getSearchBoard(ItemDTO itemDTO, int nowPage) {
		int cnt = itemDAO.searchCount(itemDTO);
		PageUtil pInfo = new PageUtil(nowPage, cnt);
		//PageUtil객체생성자에서는 내부적으로
		//	한페이지당 보여주고 싶은 게시물의 개수는 3
		//	페이지 이동 기능은 3개까지 지정
		return pInfo;
	}


	//글 수정하기
	public void modifyBoard(ItemDTO itemDTO) {
		System.out.println("서비스 상품수정 modifyBoard() 진입");
		itemDAO.modifyBoard(itemDTO);		
	}


	// 글 수정하기 - 
	public ArrayList<ItemDTO> getFileInfo(int ino) {
		System.out.println("서비스 상품수정 getFileInfo(ino) 진입 ino =" + ino);
		ArrayList<ItemDTO> list =itemDAO.getFileInfo(ino);
		return list;
	}


	//글수정하기 - 기존 첨부파일정보 삭제
	public void deleteFileInfo(int no) {
		itemDAO.deleteFileInfo(no);
	}



	//글수정하기 - 첨부파일등록관련
	public void insertFileInfo(ItemDTO itemDTO) {
		itemDAO.insertItem(itemDTO,"iInfo");
	}


	// 상품 리스트 페이지에 출력할 상품 목록 조회 - 기업
	public ArrayList<ItemDTO> getListView(PageUtil pInfo, HttpSession session, String searchWord) {
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() 진입");
		int start = (pInfo.getNowPage()-1)*pInfo.getLineCount()+1;
		int end  = start + pInfo.getLineCount()-1;
		int cono = Integer.parseInt(String.valueOf(session.getAttribute("CONO")));
		System.out.println("서비스 cono=" + cono);
		String word = searchWord;
		
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setStart(start);
		itemDTO.setEnd(end);
		itemDTO.setCono(cono);
		itemDTO.setWord(word);
		
		System.out.println("서비스 상품 리스트 itemDTO = " + itemDTO);
		ArrayList<ItemDTO> list = itemDAO.getListViewCo(itemDTO);
		System.out.println("서비스 상품리스트 페이징 관련 - getListView() list " + list);
		
		return list;
	}
}



	
	
				
	

