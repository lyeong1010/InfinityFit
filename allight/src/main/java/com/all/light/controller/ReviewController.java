package com.all.light.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.ReviewDTO;
import com.all.light.service.ReviewService;
import com.all.light.util.PageUtil;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService revSVC;

	@RequestMapping("/mypage/home")
	public String home() {
		return "common/user/mypage/home";
	}
	
	@RequestMapping("/mypage/review/list")
	public ModelAndView itemList(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord, ModelAndView mv, RedirectView rv,
			HttpServletRequest request) {
		// 파라미터 받기
		String id = (String) request.getSession().getAttribute("MID");
		System.out.println("\nReviewController.itemList, 접속ID = " + id+searchWord);
		
		// 페이지 객체에 검색어와 현재 페이지를 넘기고 공지 리스트를 반환
		PageUtil pInfo = revSVC.getPageInfo(id, nowPage, searchWord);
		ArrayList<ReviewDTO> map = revSVC.getList(pInfo);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		mv.addObject("LIST", map); // 리뷰 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보

		mv.setViewName("shopping/user/mypageReview/itemList");
		return mv;
	}

	@RequestMapping(value="/mypage/review/write", method= RequestMethod.GET)
	public ModelAndView reviewWriteGet(
			@RequestParam(value = "no") int ino,
			@RequestParam(value = "nowPage", required=false, defaultValue="1") int nowPage,
			ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nReviewController.review.write"+request.getMethod()+"method");
		
		mv.setViewName("shopping/user/mypageReview/reviewWrite");
		return mv;
	}
	
	@RequestMapping(value="/mypage/review/write", method= RequestMethod.POST)
	public ModelAndView reviewWritePost(
			@RequestParam(value = "nowPage", required=false, defaultValue="1") int nowPage,
			ReviewDTO revDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nReviewController.review.write"+request.getMethod()+"method");
		System.out.println("DTO = "+revDTO);
		
		revSVC.reviewWrite(revDTO);
		
		rv.setUrl(request.getContextPath()+"/mypage/review/list.com?nowPage="+nowPage);
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping(value="/mypage/review/update", method= RequestMethod.GET)
	public ModelAndView reviewUpdateGet(
			@RequestParam(value = "no") int ino, 
			@RequestParam(value = "nowPage", required=false, defaultValue="1") int nowPage,
			ReviewDTO revDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nReviewController.review.Update"+request.getMethod()+"method");
		String id = (String)request.getSession().getAttribute("MID");
		//비즈니스 로직
		revDTO = revSVC.getReviewInfo(ino, id);
		//모델지정
		mv.addObject("LIST", revDTO); // 리뷰 정보
		System.out.println(revDTO); 
		//뷰지정
		mv.setViewName("shopping/user/mypageReview/reviewUpdate");
		return mv;
	}
	
	@RequestMapping(value="/mypage/review/update", method= RequestMethod.POST)
	public ModelAndView reviewUpdatePost(
			@RequestParam(value = "no") int ino, 
			@RequestParam(value = "nowPage", required=false, defaultValue="1") int nowPage,
			ReviewDTO revDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nReviewController.review.Update"+request.getMethod()+"method");
		revDTO.setIno(ino);
		System.out.println(revDTO);
		//비즈니스 로직
		revSVC.reviewUpdate(revDTO);
		//뷰지정
		rv.setUrl(request.getContextPath()+"/mypage/review/list.com?nowPage="+nowPage);
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping("/mypage/review/delete")
	public ModelAndView reviewDelete(
			@RequestParam(value = "no") int ino, 
			@RequestParam(value = "nowPage", required=false, defaultValue="1") int nowPage,
			ReviewDTO revDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nReviewController.review.delete, ino="+ino);
		revDTO.setIno(ino);
		revDTO.setRid( (String)request.getSession().getAttribute("MID"));
		System.out.println("전달 파라미터 = "+revDTO);
		revSVC.reviewDelete(revDTO);
		
		rv.setUrl(request.getContextPath()+"/mypage/review/list.com?nowPage="+nowPage);
		mv.setView(rv);
		return mv;
	}
	
	//기업
	@RequestMapping("/review/list/corp")
	public ModelAndView reviewListCorp(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue="") String searchWord, 
			ModelAndView mv, RedirectView rv,	HttpServletRequest request) {
		// 파라미터 받기
		String name = (String) request.getSession().getAttribute("CONAME");
		System.out.println("\nReviewController.reviewListCorp, 접속ID = " + name);

		// 페이지 객체에 검색어와 현재 페이지를 넘기고 공지 리스트를 반환
		PageUtil pInfo = revSVC.getPageInfoCorp(name, nowPage, searchWord);
		pInfo.setConame(name);
		ArrayList<ReviewDTO> map = revSVC.getListCorp(pInfo);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		mv.addObject("LIST", map); // 리뷰 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보

		mv.setViewName("shopping/corp/mypageReview/reviewList");
		return mv;
	}
	
	@RequestMapping("/review/delete/corp")
	public ModelAndView reviewdeleteCorp(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord, 
			@RequestParam(value = "no", required = false) int rno,
			ModelAndView mv, RedirectView rv,	HttpServletRequest request, ReviewDTO revDTO) {
		// 파라미터 받기
		System.out.println("\nReviewController.reviewdeleteCorp, 삭제번호 = " + rno);
		revDTO.setRno(rno);
		revSVC.reviewDeleteCorp(revDTO);
		rv.setUrl(request.getContextPath()+"/review/list/corp.com?nowPage="+nowPage);
		mv.setView(rv);
		return mv;
	}
}
