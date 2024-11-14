package com.all.light.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.NoticeDTO;
import com.all.light.service.NoticeService;
import com.all.light.util.PageUtil;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService notSVC;

	@RequestMapping("")
	public ModelAndView noticeList(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "nall") String searchType, ModelAndView mv,
			RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeList");

		// 페이지 객체에 검색어와 현재 페이지를 넘기고 공지 리스트를 반환
		PageUtil pInfo = notSVC.getPageInfo(nowPage, searchWord, searchType);
		ArrayList<NoticeDTO> map = notSVC.searchList(pInfo, searchWord, searchType);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		mv.addObject("LIST", map); // 공지 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보
		
		//한글 파라미터를 리다이렉트시 인코딩 처리를 내부적으로 해주어야함(원래는 웹에서 자동으로 해주지만 리다이렉트는..웹을 거치지 않기 때문에??)
		String encodedSearchWord="";
		if ("admin".equals(request.getSession().getAttribute("MID"))) {
			try {
				encodedSearchWord = URLEncoder.encode(searchWord, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			rv.setUrl(request.getContextPath() + "/notice/admin.com?type="+searchType+"&search="+encodedSearchWord+"&nowPage="+nowPage);
			mv.setView(rv);
		} else {
			mv.setViewName("diary/user/notice/noticeList");
		}
		return mv;
	}

	@RequestMapping("/admin")
	public ModelAndView noticeListAdmin(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "nall") String searchType, ModelAndView mv,
			RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeList");
		
		//URL은 자동으로 웹 서버에서 파싱할때 디코딩
/*		try {
			searchWord = URLDecoder.decode(request.getParameter("search"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		
		// 페이지 객체에 검색어와 현재 페이지를 넘기고 공지 리스트를 반환
		PageUtil pInfo = notSVC.getPageInfo(nowPage, searchWord, searchType);
		ArrayList<NoticeDTO> map = notSVC.searchList(pInfo, searchWord, searchType);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		mv.addObject("LIST", map); // 공지 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보

		mv.setViewName("diary/user/notice/noticeList");
		return mv;
	}

	// 글 상세보기
	@RequestMapping(value = "/detail")
	public ModelAndView noticeDetail(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "nall") String searchType,
	@RequestParam("nno") int nno, NoticeDTO notDTO, ModelAndView mv, RedirectView rv,
			HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeDetail");

		// 비즈니스 로직(정보 가져오기, 조회수 증가)
		notDTO = notSVC.getNotInfo(nno);
		notSVC.increaseHit(nno, request.getSession());
		System.out.println("LIST = " + notDTO.toString());

		// 공지 상세 내용 모델 지정
		mv.addObject("LIST", notDTO);
		if ("admin".equals(request.getSession().getAttribute("MID"))) {
			rv.setUrl(request.getContextPath() + "/notice/detail/admin.com?type="+searchType+"&search="+searchWord+"&nowPage="+nowPage+"&nno="+nno);
			mv.setView(rv);
		} else {
			mv.setViewName("diary/user/notice/noticeDetail");
		}
		return mv;
	}

	// 글 상세보기
	@RequestMapping(value = "/detail/admin")
	public ModelAndView noticeDetailAdmin(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "nall") String searchType,
			@RequestParam("nno") int nno, NoticeDTO notDTO, ModelAndView mv,
			RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeDetail");

		// 비즈니스 로직(정보 가져오기, 조회수 증가)
		notDTO = notSVC.getNotInfo(nno);
		notSVC.increaseHit(nno, request.getSession());
		System.out.println("LIST = " + notDTO.toString());

		// 공지 상세 내용 모델 지정
		mv.addObject("LIST", notDTO);
		mv.setViewName("diary/user/notice/noticeDetail");
		return mv;
	}

	@RequestMapping(value = "/write/admin", method = RequestMethod.GET)
	public ModelAndView noticeWriteGet(ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeWrite," + request.getMethod() + "method");
		mv.setViewName("diary/admin/notice/noticeWrite");
		return mv;
	}

	@RequestMapping(value = "/write/admin", method = RequestMethod.POST)
	public ModelAndView noticeWritePost(NoticeDTO notDTO, ModelAndView mv, RedirectView rv,
			HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeWrite," + request.getMethod() + "method");
		System.out.println("NoticeDTO = " + notDTO);
		notSVC.notWrite(notDTO);
		rv.setUrl(request.getContextPath() + "/notice.com");
		mv.setView(rv);
		return mv;
	}

	@RequestMapping(value = "/modify/admin", method = RequestMethod.GET)
	public ModelAndView noticeModifyGet(NoticeDTO notDTO, ModelAndView mv, RedirectView rv,
			HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeModify," + request.getMethod() + "method");
		System.out.println("글번호 : " + notDTO.getNno());
		// 비즈니스로직, 모델지정
		notDTO = notSVC.getNotInfo(notDTO.getNno());
		mv.addObject("LIST", notDTO);

		mv.setViewName("diary/admin/notice/noticeModify");
		return mv;
	}

	@RequestMapping(value = "/modify/admin", method = RequestMethod.POST)
	public ModelAndView noticeModifyPOST(NoticeDTO notDTO, ModelAndView mv, RedirectView rv,
			HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeModify," + request.getMethod() + "method");
		System.out.println("수정 DTO : " + notDTO);

		// 비즈니스로직
		notSVC.notModify(notDTO);

		rv.setUrl(request.getContextPath() + "/notice/detail.com?nno=" + notDTO.getNno());
		mv.setView(rv);
		return mv;
	}

	@RequestMapping(value = "/delete/admin")
	public ModelAndView noticeDelete(NoticeDTO notDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeDelete");
		System.out.println("글번호 : " + notDTO.getNno());
		// 비즈니스로직, 모델지정
		notSVC.notDelete(notDTO.getNno());

		rv.setUrl(request.getContextPath() + "/notice.com");
		mv.setView(rv);
		return mv;
	}
}
