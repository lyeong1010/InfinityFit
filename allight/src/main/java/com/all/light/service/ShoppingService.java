package com.all.light.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.ItemDAO;
import com.all.light.dao.ShoppingDAO;
import com.all.light.dto.ItemDTO;
import com.all.light.dto.ItemQuestionDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.dto.ShoppingDTO;
import com.all.light.util.PageUtil;

public class ShoppingService {
	
	@Autowired
	private ShoppingDAO shopDAO;
	
	// ------------상품 목록-----------------------------------------------------
	// 페이지정보
	public PageUtil getPageInfo(int nowPage, String icategory) {
		int totalCount = 0;
		
		if(icategory.equals("all")) {
			totalCount = shopDAO.getAllTotalCnt();
		}else {
			totalCount = shopDAO.getTotalCnt(icategory);
		}
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(nowPage, totalCount,40,5);
		
		return pInfo;
	}
	
	// 카테고리별 게시물 가져오기
	public ArrayList<ShoppingDTO> list(PageUtil pInfo, String icategory, int sort) {
		ArrayList<ShoppingDTO> list = shopDAO.list(pInfo,icategory,sort);
		return list;
	}
	
	// 모든 게시물 가져오기
	public ArrayList<ShoppingDTO> listAll(PageUtil pInfo,int sort) {
		ArrayList<ShoppingDTO> list = shopDAO.listAll(pInfo,sort);
		return list;
	}
	
	// 대표 이미지 가져오기
	public String getRepreImage(int ino) {
		String repreImg = shopDAO.getRepreImage(ino);
		return repreImg;
	}
	
	// 검색된 게시물 가져오기 
	public ArrayList<ShoppingDTO> searchList(String searchWord) {
		ArrayList<ShoppingDTO> list = shopDAO.searchList(searchWord);
		return list;
	}
	
	
	// ------------브랜드관-----------------------------------------------------
	// 브랜드관 브랜드 목록 가져오기
	public ArrayList<String> brandNames() {
		ArrayList<String> brandNames = shopDAO.brandNames();
		return brandNames;
	}

	// 해당 브랜드 페이지 정보
	public PageUtil getBrandPageInfo(int nowPage, String brand) {
		int totalCount = 0;
		totalCount = shopDAO.getBrandTotalCnt(brand);
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(nowPage, totalCount,40,5);
		
		return pInfo;
	}
	
	// 해당 브랜드 상품 리스트 가져오기
	public ArrayList<ShoppingDTO> brandContent(String brand,int sort,PageUtil pInfo) {
		ArrayList<ShoppingDTO> list = shopDAO.brandContent(brand,sort,pInfo);
		return list;
	}
	
	// ------------상세 페이지-----------------------------------------------------
	// 상품 상세 정보 가져오기
	public ShoppingDTO getDetail(int ino) {
		System.out.println("getDetail"+ino);
		ShoppingDTO shopDTO = shopDAO.getDetail(ino);
		return shopDTO;
	}
	
	
	// 상품 상세 이미지 가져오기
	public ArrayList<String> getImgs(int ino) {
		ArrayList<String> list = shopDAO.getImgs(ino);
		return list;
	}

	// 상품 후기 페이지 정보
	public PageUtil getRPageInfo(int ino, int rNowPage) {
		int totalCount = 0;
		totalCount = shopDAO.getRTotalCnt(ino);
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(rNowPage, totalCount,5,5);
		
		return pInfo;
	}

	// 상품 후기 전체 개수 가져오기
	public int getRTotalCnt(int ino) {
		int rTotalCnt = shopDAO.getRTotalCnt(ino);
		return rTotalCnt;
	}
	
	// 상품 후기 리스트 가져오기
	public ArrayList<ReviewDTO> getReview(int ino, PageUtil rPInfo, String mid) {
		ArrayList<ReviewDTO> list = shopDAO.getReview(ino,rPInfo);
		
		for(int i=0; i<list.size();i++) {
			if(this.isLike(list.get(i).getRno(), mid)==1){
				list.get(i).setIsLiked(Boolean.TRUE);
			}
		}
		return list;
	}
	
	// 상품 후기 좋아요 여부 체크
	public int isLike(int rno, String mid) {
		//isLike 이 아이디 사람이 이미 좋아요 했으면 1, 안했으면 0
		int isLike = shopDAO.getIsLike(rno,mid);
		
		return isLike;
	}
	
	// 상품 후기 추가/삭제
	public void reviewLike(int rno, String mid) {
		//isLike 이 아이디 사람이 이미 좋아요 했으면 1, 안했으면 0
		int isLike = this.isLike(rno,mid);
		
		switch (isLike) {
		case 0: //좋아요 추가
			shopDAO.rLikeIns(rno,mid);
			break;
		case 1: //좋아요 삭제
			shopDAO.rLikeDel(rno,mid);
		}
	}
	
	// 상품 문의 전체 개수
	public int getQTotalCnt(int ino) {
		int totalCount = shopDAO.getQTotalCnt(ino);
		return totalCount;
	}
	
	// 상품 문의 전체 개수 -류지혁
	public int getQTotalCnt(String mid) {
		System.out.println("서비스 - getQTotalCnt() mid =" + mid );
		int totalCount = shopDAO.getQTotalCnt(mid);
		return totalCount;
	}
	
	// 상품 문의 전체 개수 기업 -류지혁
	public int getQTotalCnt2(String mid) {
		System.out.println("서비스 - getQTotalCnt2() mid =" + mid );
		int totalCount = shopDAO.getQTotalCnt2(mid);
		return totalCount;
	}

	// 상품 문의 페이지 정보
	public PageUtil getQPageInfo(int ino, int qNowPage) {
		int totalCount = this.getQTotalCnt(ino);
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(qNowPage, totalCount,5,5);
		return pInfo;
	}
	
	
	
	// 상품 문의 페이지 정보 - 류지혁
	public PageUtil getQPageInfo2(int qNowPage, String mid) {
		System.out.println("서비스 - getQPageInfo2() qNowPage, mid = " + qNowPage + "," + mid );
		int totalCount = this.getQTotalCnt(mid);
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(qNowPage, totalCount,10,5);
		return pInfo;
	}
	
	// 상품 문의 페이지 정보 기업 - 류지혁 
	public PageUtil getQPageInfo3(int qNowPage, String mid) {
		System.out.println("서비스 - getQPageInfo3() qNowPage, mid = " + qNowPage + "," + mid );
		int totalCount = this.getQTotalCnt2(mid);
		
		// PageUtil(보고싶은페이지, 전체게시물수, 보여줄 게시물수, 페이징);
		PageUtil pInfo = new PageUtil(qNowPage, totalCount,10,5);
		return pInfo;
	}
	
	// 상품 문의 리스트 가져오기
	public ArrayList<ItemQuestionDTO> getQuestion(int ino, PageUtil qPInfo, String mid) {
		// 전체 공개 내용 가져오기
		ArrayList<ItemQuestionDTO> list = shopDAO.getQuestion(ino,qPInfo);
		for(int i=0; i<list.size();i++) {
			int secret = (int)list.get(i).getIqsecret();
			ItemQuestionDTO qDto;
			// 1. 비밀여부 1일때 (비밀) 작성자가 내 아이디랑 같거나 
			// 2. 비밀여부 0일때 (공개)
			if((secret==1 && list.get(i).getIqid().equals(mid))
					|| secret==0 ) {
				// 내용 가져오기
				int iqno = list.get(i).getIqno();
				list.get(i).setIqcontent(shopDAO.getQContent(iqno));
				// 답글 있으면 - 작성자 닉네임/내용/작성일 가져오기
				if(shopDAO.hasQComment(iqno)==1) {
					qDto = shopDAO.getQClist(list.get(i).getIqno());
					list.get(i).setIqcnick(qDto.getIqcnick());
					list.get(i).setIqccontent(qDto.getIqccontent());
					list.get(i).setIqcdate(qDto.getIqcdate());
					list.get(i).setIqcno(qDto.getIqcno());
				}
			} 
		}
		return list;
	}
	
	
	// 상품 문의 리스트 가져오기 - 류지혁
	public ArrayList<ItemQuestionDTO> getQuestion(PageUtil qPInfo, String mid) {
		// 전체 공개 내용 가져오기
		ArrayList<ItemQuestionDTO> list = shopDAO.getQuestion(qPInfo, mid);
		for(int i=0; i<list.size();i++) {
			int secret = (int)list.get(i).getIqsecret();
			ItemQuestionDTO qDto;
			// 1. 비밀여부 1일때 (비밀) 작성자가 내 아이디랑 같거나 
			// 2. 비밀여부 0일때 (공개)
			if((secret==1 && list.get(i).getIqid().equals(mid))
					|| secret==0 ) {
				// 내용 가져오기
				int iqno = list.get(i).getIqno();
				list.get(i).setIqcontent(shopDAO.getQContent(iqno));
				
				// 답글 있으면 - 작성자 닉네임/내용/작성일 가져오기
				if(shopDAO.hasQComment(iqno)==1) {
					qDto = shopDAO.getQClist(list.get(i).getIqno());
					list.get(i).setIqcnick(qDto.getIqcnick());
					list.get(i).setIqccontent(qDto.getIqccontent());
					list.get(i).setIqcdate(qDto.getIqcdate());
					list.get(i).setIqcno(qDto.getIqcno());
				}
			} 
		}
		return list;
	}
	
	// 상품 문의 리스트 가져오기 기업 - 류지혁
	public ArrayList<ItemQuestionDTO> getQuestion2(PageUtil qPInfo, String mid) {
		// 전체 공개 내용 가져오기
		ArrayList<ItemQuestionDTO> list = shopDAO.getQuestion2(qPInfo, mid);
		for(int i=0; i<list.size();i++) {
			int secret = (int)list.get(i).getIqsecret();
			ItemQuestionDTO qDto;
			// 1. 비밀여부 1일때 (비밀) 작성자가 내 아이디랑 같거나 
			// 2. 비밀여부 0일때 (공개)
			if((secret==1)
					|| secret==0 ) {
				// 내용 가져오기
				int iqno = list.get(i).getIqno();
				list.get(i).setIqcontent(shopDAO.getQContent(iqno));
				
				// 답글 있으면 - 작성자 닉네임/내용/작성일 가져오기
				if(shopDAO.hasQComment(iqno)==1) {
					qDto = shopDAO.getQClist(list.get(i).getIqno());
					list.get(i).setIqcnick(qDto.getIqcnick());
					list.get(i).setIqccontent(qDto.getIqccontent());
					list.get(i).setIqcdate(qDto.getIqcdate());
					list.get(i).setIqcno(qDto.getIqcno());
				}
			} 
		}
		return list;
	}
	
	// 상품 문의 작성
	public void iqWrite(ItemQuestionDTO dto) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ino", dto.getIno());
		map.put("iqid", dto.getIqid());
		map.put("iqnick", dto.getIqnick());
		map.put("iqtitle", dto.getIqtitle());
		map.put("iqcontent", dto.getIqcontent());
		map.put("iqsecret", dto.getIqsecret());
		
		shopDAO.iqWrite(map);
	}

	// 상품 문의 삭제
	public void iqDelete(int iqno) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("iqno", iqno);
		shopDAO.iqDelete(map);
	}

	// 상품 문의 수정
	public void iqModify(ItemQuestionDTO dto) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("iqno", dto.getIqno());
		map.put("iqtitle", dto.getIqtitle());
		map.put("iqcontent", dto.getIqcontent());
		map.put("iqsecret", dto.getIqsecret());
		
		shopDAO.iqModify(map);
	}

	// 상품 문의 상품명
	public ArrayList<ItemDTO> getItemName(String mid) {
		return shopDAO.getItemName(mid); 
	}
	
	// 상품 문의 상품명
	public ArrayList<ItemDTO> getItemName2(String mid) {
		return shopDAO.getItemName2(mid); 
	}

	
	
	
	
	
	
	
	
}
