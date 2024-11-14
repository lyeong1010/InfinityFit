package com.all.light.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.CaldictionaryDTO;
import com.all.light.dto.ItemDTO;
import com.all.light.service.CaldictionaryService;
import com.all.light.util.PageUtil;

@Controller
@RequestMapping("/cal")
public class CaldictionaryController {
	
	@Autowired
	CaldictionaryService calSVC;
	
	// 음식 사전 보기
	@RequestMapping("/dictionary/food")
	public ModelAndView foodview(
			@RequestParam(value="nowPage", required=false, defaultValue="1") int nowPage,
			@RequestParam(value = "searchWord", required = false, defaultValue="") String searchWord,
			ModelAndView mv){

		System.out.println("컨트롤러 상품 목록보기 - foodview() 요청");
		PageUtil pInfo = calSVC.getPageInfo(nowPage, searchWord);
		System.out.println("컨트롤러 상품 목록보기 - pInfo : " + pInfo);
		ArrayList<CaldictionaryDTO> list = calSVC.getListView(pInfo, searchWord);
		mv.addObject("LIST", list);		// 실제 조회 목록
		mv.addObject("PINFO", pInfo);	// 페이징 관련 정보
		mv.addObject("searchWord", searchWord);	// 페이징 관련 정보
		mv.setViewName("/diary/user/dictionary/food");
		System.out.println("컨트롤러 상품 목록보기 - list = " + list);
		System.out.println("컨트롤러 상품 목록보기 - pInfo = " + pInfo);
		System.out.println("컨트롤러 상품 목록보기 - searchWord = " + searchWord);
		System.out.println("컨트롤러 상품 목록보기 - mv =" + mv);
		return mv;
	}

	// 운동 사전 보기
	@RequestMapping("/dictionary/exercise")
	public ModelAndView exerciseview(
			@RequestParam(value="nowPage", required=false, defaultValue="1") int nowPage,
			@RequestParam(value = "searchWord", required = false, defaultValue="") String searchWord,
			ModelAndView mv){

		System.out.println("컨트롤러 상품 목록보기 - exerciseview() 요청");
		PageUtil pInfo = calSVC.getPageInfo2(nowPage, searchWord);
		System.out.println("컨트롤러 상품 목록보기 - pInfo : " + pInfo);
		ArrayList<CaldictionaryDTO> list = calSVC.getListView2(pInfo, searchWord);
		mv.addObject("LIST", list);		// 실제 조회 목록
		mv.addObject("PINFO", pInfo);	// 페이징 관련 정보
		mv.addObject("searchWord", searchWord);	// 페이징 관련 정보
		mv.setViewName("/diary/user/dictionary/exercise");
		System.out.println("컨트롤러 상품 목록보기 - list = " + list);
		System.out.println("컨트롤러 상품 목록보기 - pInfo = " + pInfo);
		System.out.println("컨트롤러 상품 목록보기 - searchWord = " + searchWord);
		System.out.println("컨트롤러 상품 목록보기 - mv = " + mv);
		return mv;
	}
	
	// 칼로리 사전 보기
	@RequestMapping("/dictionary/admin")
	public ModelAndView dictionary(
			@RequestParam(value="nowPage", required=false, defaultValue="1") int nowPage,
			@RequestParam(value = "searchWord", required = false, defaultValue="") String searchWord,
			ModelAndView mv){

		System.out.println("컨트롤러 상품 목록보기 - foodview() 요청");
		PageUtil pInfo = calSVC.getPageInfo3(nowPage, searchWord);
		System.out.println("컨트롤러 상품 목록보기 - pInfo : " + pInfo);
		ArrayList<CaldictionaryDTO> list = calSVC.getListView3(pInfo, searchWord);
		mv.addObject("LIST", list);		// 실제 조회 목록
		mv.addObject("PINFO", pInfo);	// 페이징 관련 정보
		mv.addObject("searchWord", searchWord);	// 페이징 관련 정보
		mv.setViewName("/diary/admin/dictionary/list");
		System.out.println("컨트롤러 상품 목록보기 - list = " + list);
		System.out.println("컨트롤러 상품 목록보기 - pInfo = " + pInfo);
		System.out.println("컨트롤러 상품 목록보기 - searchWord = " + searchWord);
		System.out.println("컨트롤러 상품 목록보기 - mv = " + mv);
		return mv;
	}
	
	// 칼로리 사전 내용 삭제
	@RequestMapping("/dictionary/delete")
	public ModelAndView dictionarydelete(int cdno,
			ModelAndView mv, RedirectView rv, HttpServletRequest request) 
	{
		System.out.println("컨트롤러 상품 목록보기 - dictionarydelete() 요청");
		System.out.println("컨트롤러 상품 목록보기 - dictionarydelete() 요청 cdno = " +cdno);
		
		calSVC.delete(cdno);
		return null;
	}
	
	// 음식 추가
	@RequestMapping("/dictionary/food/insert")
	public ModelAndView itemInsertPage(CaldictionaryDTO calDTO) {
		System.out.println("insert.com 폼 진입");
		System.out.println("calDTO = " + calDTO);
		
		calSVC.insert(calDTO);
		return null;
	}		
		
	
	// 운동 추가
	
	
	
}
