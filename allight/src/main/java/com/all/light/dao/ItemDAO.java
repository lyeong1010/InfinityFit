package com.all.light.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.ItemDTO;
import com.all.light.util.PageUtil;

//DB연동하는 클래스
//fileBoard - 누가, 언제, 제목, 내용...
//fileInfo  - 첨부파일들 정보(원래이름,저장된이름,용량,...)

//DAO가 되기위해서는 
//1. SqlSessionDaoSupport 상속받아야 한다
//2. SqlSessionTemplate를 자동주입
//SqlSessionDaoSupport클래스
// -커넥션풀을 이용해서 커넥션을 받고, 스테이트먼트를 만들어서 관리하는 클래스.
public class ItemDAO extends SqlSessionDaoSupport{
	
	//자동주입
	@Autowired
	SqlSessionTemplate session;
	
	
	// 상품추가처리
	public void insertItem(ItemDTO iDTO, String hint) {
		
		if(hint.equals("item")) {
			System.out.println("DAO insertItem(item)");
			session.insert("item.itemInsert", iDTO);
		}else if(hint.equals("iInfo")) {
			System.out.println("DAO insertItem(iInfo)");
			session.insert("item.itemInsertInfo", iDTO);
		}
		
	}

	// 상품삭제처리
	public void deleteItem(ItemDTO itemDTO) {
		System.out.println("DAO 상품삭제 처리 - deleteItem() 진입");
		session.delete("item.itemDelete", itemDTO);		
		System.out.println("SQL 삭제처리 완료");
	}
	
	
	
	// 전체 게시물 수 조회
	public int getTotalCnt() {
		System.out.println("DAO 전체 게시물 수 조회 - getTotalCnt() 진입");
		return (Integer)session.selectOne("item.totalCnt");
	}
	
	// 전체 게시물 수 조회
	public int getTotalCnt(String searchWord) {
		System.out.println("DAO 전체 게시물 수 조회 - getTotalCnt() 진입");
		int totalCnt = session.selectOne("item.totalCntWithSearch", searchWord);
		return totalCnt;
		
	}
	
	
	public ArrayList searchList(PageUtil pInfo) {
		System.out.println("DAO 상품 목록 조회 - searchList(pInfo) 진입 = " + pInfo);
		return (ArrayList)session.selectList("item.searchList", pInfo);
	}
	

	// 상품 목록 조회
	public ArrayList getListView(ItemDTO itemDTO) {
		System.out.println("DAO 상품 목록 조회 - getListView() 진입");
		return (ArrayList)session.selectList("item.listview", itemDTO);
	}
	
	// 상품 목록 조회 - 기업
	public ArrayList getListViewCo(ItemDTO itemDTO) {
		System.out.println("DAO 상품 목록 조회 - getListView() 진입");
		return (ArrayList)session.selectList("item.listviewco", itemDTO);
	}

	//상품 정보 조회
	public ItemDTO itemInfo(int ino) {
		System.out.println("DAO 상품 정보 조회 - itemInfo() 진입");
		return session.selectOne("item.iInfo", ino);
	}
	
	//상품 상세 조회
	public ItemDTO detailView(int ino) {
		System.out.println("DAO 상품 상세 조회 - detailView() 진입 ino =" + ino);
		return (ItemDTO)session.selectOne("item.iInfo", ino);
	}
	
	//이미지 조회
	public ItemDTO detailImg(int oriNo) {
		System.out.println("DAO 상품 이미지 조회 - detailImg() 진입 ino =" + oriNo);
		return (ItemDTO)session.selectOne("item.itemImg", oriNo);
	}
	
	//검색- 페이지정보처리
	public int searchCount(ItemDTO itemDTO) {
		return (Integer) getSqlSession().selectOne("item.searchCount", itemDTO);
	}

	public void modifyBoard(ItemDTO itemDTO) {
		System.out.println("DAO 상품 상세 조회 - modifyBoard() 진입 itemDTO =" + itemDTO);
		session.update("item.modifyBoard", itemDTO);
	}

	public ArrayList<ItemDTO> getFileInfo(int oriNo) {
		ArrayList<ItemDTO> list = null;
		list = (ArrayList)session.selectList("item.fileInfo", oriNo);
		return list;
	}
	
	//글 수정 - 기존 첨부파일정보 삭제
	public void deleteFileInfo(int no) {
		session.delete("item.deleteFileInfo", no);
		
	}





	
}








	
	

	








