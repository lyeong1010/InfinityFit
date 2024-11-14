package com.all.light.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.ItemQuestionDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.dto.ShoppingDTO;
import com.all.light.service.ShoppingService;
import com.all.light.util.PageUtil;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {
	
	@Autowired
	private ShoppingService shopSVC;
	
	//list - 쇼핑목록 보여주기
	@RequestMapping("/list")
	public ModelAndView shopList(
			ModelAndView mv,
			@RequestParam(value="icategory", 
						  required=false,
						  defaultValue="all") String icategory, // 카테고리
			@RequestParam(value="sort", 
						  required=false,
						  defaultValue="0") int sort, 	 // 정렬 0:인기순, 1:낮은가격순, 2:높은가격순
			@RequestParam(value="nowPage", 
						  required=false, 
						  defaultValue="1") int nowPage, // 현재 페이지
			@RequestParam(value="searchWord", 
						  required=false,
						  defaultValue="") String searchWord //검색 단어
			) {
		System.out.println("ShoppingController-shoplist()");
		
		ArrayList<ShoppingDTO> list = null;
		PageUtil pInfo = null;

		// 검색 내용 없을 때...나중에 여러 경우(스페이스만 쳤을때...등) 추가하자~~
		if(searchWord.equals(null) || searchWord==null || searchWord.equals("")) {
			// 페이지 정보 가져오기
			pInfo=shopSVC.getPageInfo(nowPage,icategory);
			
			// 전체 상품 보기
			if(icategory.equals("all")) {
				list = shopSVC.listAll(pInfo,sort);
			// 카테고리별 상품 보기
			}else { 
				list = shopSVC.list(pInfo,icategory,sort);
			}
			
		// 검색 내용 있을 때
		}else {
			list = shopSVC.searchList(searchWord);
		}
		
		// 가져온 상품들의 대표 이미지 가져오기
		for(int i=0;i<list.size();i++) {
			list.get(i).setImgimage(shopSVC.getRepreImage(list.get(i).getIno()));
		}
		
		// Model
		mv.addObject("LIST",list); //실제조회목록
		mv.addObject("PINFO",pInfo); //페이징관련 정보
		mv.addObject("ICATEGORY",icategory); //현재 카테고리 정보
		mv.addObject("SORT",sort); //정렬방식
		mv.addObject("SEARCHWORD",searchWord); //검색단어
		if(list.size()<1) {
			mv.addObject("NoSearch",Boolean.TRUE); //검색내용없음을 알려줌
		}
		
		// View
		mv.setViewName("shopping/user/shop/list");
		return mv;
	}
	
	
	//브랜드관 - 브랜드별 상품목록 보여주기
	@RequestMapping("/brand")
	public ModelAndView brandList(
			ModelAndView mv,
			@RequestParam(value="brand", 
						  required=false,
						  defaultValue="뮬라웨어") String brand, // 브랜드명
			@RequestParam(value="nowPage", 
			  			  required=false, 
			  			  defaultValue="1") int nowPage, 	  // 현재 페이지
			@RequestParam(value="sort", 
						  required=false,
						  defaultValue="0") int sort		  // 정렬
			) {
		System.out.println("ShoppingController-brandList()");
		
		PageUtil pInfo = null;
		ArrayList<String> brandNames = null;
		ArrayList<ShoppingDTO> list = null;

		// 브랜드 이름 목록 가져오기
		brandNames = shopSVC.brandNames();

		//해당 브랜드 상품 가져오기
		pInfo=shopSVC.getBrandPageInfo(nowPage,brand);
		list = shopSVC.brandContent(brand,sort,pInfo);

		// 가져온 상품들의 대표 이미지 가져오기
		for(int i=0;i<list.size();i++) {
			list.get(i).setImgimage(shopSVC.getRepreImage(list.get(i).getIno()));
		}
		
		// Model
		mv.addObject("BRAND",brand); //현재 브랜드
		mv.addObject("BRANDS",brandNames); //모든브랜드목록
		mv.addObject("LIST",list);   //실제조회목록
		mv.addObject("SORT",sort);   //정렬방식
		mv.addObject("PINFO",pInfo); //페이징관련 정보
		
		// View
		mv.setViewName("shopping/user/shop/brand");
		return mv;
	}
	
	
	//상세페이지 - 한 상품의 상세페이지
	@RequestMapping("/detail")
	public ModelAndView detail(
			ModelAndView mv,
			@RequestParam(value="ino", 
						  required=false,
						  defaultValue="0") int ino, 	  //상품번호
			@RequestParam(value="rNowPage", 
			  			  required=false,
			  			  defaultValue="1") int rNowPage, //상품후기 현재 페이지
			@RequestParam(value="qNowPage", 
			  			  required=false,
			  			  defaultValue="1") int qNowPage,  //상품문의 현재 페이지
			HttpServletRequest request
			) {
		System.out.println("ShoppingController-detail()");
		
		String mid = (String)request.getSession().getAttribute("MID");
		//상품번호가 없으면 쇼핑리스트로 돌려보내기
		if(ino==0) {
			RedirectView rv = new RedirectView("./list.com");
			mv.setView(rv);
			return mv;
		}
		
		// 1. 상품 상세 내용
		ShoppingDTO detail = null;
		String repreImg = null;
		ArrayList<String> imgs = null;
		
		detail = shopSVC.getDetail(ino); 	   //상세내용
		repreImg = shopSVC.getRepreImage(ino); //대표이미지
		imgs = shopSVC.getImgs(ino);		   //상세이미지
		
		// 2. 상품후기
		ArrayList<ReviewDTO> rList = null;
		PageUtil rPInfo = shopSVC.getRPageInfo(ino,rNowPage); //상품후기 페이지 정보
		rList = shopSVC.getReview(ino,rPInfo,mid);	   		  //상품후기 리스트
		int rTotalCnt = shopSVC.getRTotalCnt(ino); 			  //상품후기 개수
		
		
		// 3. 상품문의
		ArrayList<ItemQuestionDTO> qList = null;
		PageUtil qPInfo = shopSVC.getQPageInfo(ino,qNowPage); //상품문의 페이지 정보
		qList = shopSVC.getQuestion(ino,qPInfo,mid);	   	  //상품문의 리스트
		int qTotalCnt = shopSVC.getQTotalCnt(ino); 			  //상품문의 개수
		
		// Model
		mv.addObject("DETAIL",detail);		//상세정보
		mv.addObject("REPREIMG",repreImg);	//대표이미지
		mv.addObject("IMGS",imgs);			//상세이미지
		mv.addObject("RSIZE",rTotalCnt); 	//상품후기 개수
		mv.addObject("RLIST",rList); 		//상품후기 리스트
		mv.addObject("RPINFO",rPInfo);		//상품후기 페이지 정보
		mv.addObject("QSIZE",qTotalCnt);	//상품문의 개수
		mv.addObject("QLIST",qList);		//상품문의 개수
		mv.addObject("QPINFO",qPInfo);		//상품문의 페이징 정보
		
		// View
		mv.setViewName("shopping/user/shop/detail");
		return mv;
	}
	
	//상세페이지 리뷰 좋아요 처리
	@RequestMapping("/reviewLike")
	public ModelAndView reviewLike(
			ModelAndView mv,
			@RequestParam(value="ino", 
						  required=false,
						  defaultValue="0") int ino, 	  //상품번호
			@RequestParam(value="rNowPage", 
			  			  required=false,
			  			  defaultValue="1") int rNowPage, //상품후기 현재 페이지
			@RequestParam(value="qNowPage", 
			  			  required=false,
			  			  defaultValue="1") int qNowPage,  //상품문의 현재 페이지
			@RequestParam(value="rno", 
			  			  required=false,
			  			  defaultValue="0") int rno,  	    //리뷰글번호
			HttpServletRequest request
			) {
		System.out.println("ShoppingController-reviewLike()");
		
		String mid = (String)request.getSession().getAttribute("MID");
		
		// 뷰 먼저 설정... 미로그인시 다시 보내려고 
		String view = Integer.toString(ino);
		
		if(rNowPage!=1) {
			view+="&rNowPage="+Integer.toString(rNowPage);
		}
		if(qNowPage!=1) {
			view+="&qNowPage="+Integer.toString(qNowPage);
		}
		
		if(mid==null) {
			System.out.println("mid널");
			RedirectView rv = new RedirectView("./detail.com?ino="+view+"#review");
			mv.setView(rv);
			return mv;
		}
		
		//리뷰번호가 없으면 쇼핑리스트로 돌려보내기
		if(rno==0) {
			System.out.println("rno 0");
			RedirectView rv = new RedirectView("./list.com");
			mv.setView(rv);
			return mv;
		}
		
		//해당 리뷰넘버에 이 아이디의 좋아요가 있으면 좋아요 삭제
		//해당 리뷰넘버에 이 아이디의 좋아요가 없으면 좋아요 생성
		shopSVC.reviewLike(rno,mid);
		
		// View
		RedirectView rv = new RedirectView("./detail.com?ino="+view+"#review");
		mv.setView(rv);
		return mv;
	} 
	
	// 상품 문의 작성
	@RequestMapping("/iqWrite")
	public ModelAndView iqWrite(
			ModelAndView mv,
			ItemQuestionDTO dto
			) {
		System.out.println("ShoppingController-iqWrite()");
		System.out.println(dto.toString());
		
		int ino = dto.getIno();
		
		shopSVC.iqWrite(dto);
		
		// View
		RedirectView rv = new RedirectView("./detail.com?ino="+ino+"#question");
		mv.setView(rv);
		return mv;
	} 
	
	// 상품 문의 삭제
	@RequestMapping("/iqDelete")
	@ResponseBody
	public void iqDelete(String iqno) {
		System.out.println("ShoppingController-iqDelete() iqno="+iqno);
		
		shopSVC.iqDelete(Integer.parseInt(iqno));
		
	} 
	
	// 상품 문의 수정
	@RequestMapping("/iqModify")
	public ModelAndView iqModify(
			ModelAndView mv,
			ItemQuestionDTO dto) {
		System.out.println("ShoppingController-iqModify() dto="+dto.toString());
		
		shopSVC.iqModify(dto);
		
		// View
		RedirectView rv = new RedirectView("./detail.com?ino="+dto.getIno()+"#question");
		mv.setView(rv);
		return mv;
	}
}
