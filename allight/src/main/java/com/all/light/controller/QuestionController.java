package com.all.light.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.QuestionDTO;
import com.all.light.service.QuestionService;
import com.all.light.util.PageUtil;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService queSVC;
	
	//유저
	//글쓰기처리
	@RequestMapping(value="/question/write", method= RequestMethod.GET)
	public ModelAndView userWriteGet(HttpServletRequest request, ModelAndView mv) {
		System.out.println("USER/question/write, "+request.getMethod()+"method");
		mv.setViewName("diary/user/question/questionWrite");
		return mv;
	}
	@RequestMapping(value="/question/write", method= RequestMethod.POST)
	public ModelAndView userWritePost(QuestionDTO qdto, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("USER/question/write, "+request.getMethod()+"method");
		System.out.println(qdto);
		queSVC.userInsertWrite(qdto, request.getSession());
		rv.setUrl(request.getContextPath()+"/question/list.com");
		mv.setView(rv);
		return mv;
	}
	//목록보기
	@RequestMapping("/question/list")
	public ModelAndView list(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "nall") String searchType,
			HttpServletRequest request, ModelAndView mv) {
		System.out.println("USER/question/list");
		PageUtil pinfo = queSVC.getPageInfoBySearch(nowPage, searchWord, searchType);
		ArrayList<QuestionDTO> list = queSVC.searchList(pinfo, searchWord, searchType);
		mv.addObject("PINFO", pinfo); //페이징 정보
		mv.addObject("LIST", list); //문의사항 상세 정보
		mv.setViewName("diary/user/question/questionList");
		return mv;
	}
	
	//상세보기
	//글 상세보기
	@RequestMapping(value="/question/detail")
	public ModelAndView detail(
			@RequestParam("no") int qno,
			@RequestParam(value="commPage", required = false, defaultValue= "1") int commPage,
			QuestionDTO qdto, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("\nNoticeController.noticeDetail.QuestionDTO = "+qno);
		qdto.setQno(qno);
		
		QuestionDTO de=queSVC.detail(qdto);//게시글
		PageUtil pInfo = queSVC.getCommPageInfo(qno, commPage);
		ArrayList<QuestionDTO> decomm=queSVC.getCommDetail(pInfo);//댓글
		
		mv.addObject("DETAIL",de);//게시글
		mv.addObject("PINFO", pInfo); //페이징 정보
		mv.addObject("COMM",decomm);//댓글
		
		System.out.println(de);
		System.out.println(pInfo);
		System.out.println(decomm);

		mv.setViewName("diary/user/question/questionDetail");
		return mv;
	}
	//수정
	@RequestMapping(value="/question/update", method= RequestMethod.GET)
	public ModelAndView modifyGET(
			@RequestParam(value = "no", required = true) int qno,
			QuestionDTO qdto,ModelAndView mv, HttpServletRequest request) {
		System.out.println("USER/question/update, "+request.getMethod()+"method");
		qdto.setQno(qno);
		QuestionDTO de=queSVC.detail(qdto);//게시글
		mv.addObject("DETAIL",de);//게시글
		System.out.println(de);
		mv.setViewName("diary/user/question/questionModify");
		return mv;
	}
	@RequestMapping(value="/question/update", method= RequestMethod.POST)
	public ModelAndView modifyPOST(
			@RequestParam(value = "no", required = true) int qno,
			QuestionDTO qdto,ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("USER/question/update, "+request.getMethod()+"method");
		System.out.println(qno+" "+qdto);
		queSVC.update(qdto);
		rv.setUrl(request.getContextPath()+"/question/list.com");
		mv.setView(rv);
		return mv;
	}
	//삭제
	@RequestMapping("/question/delete")
	public ModelAndView delete(
			@RequestParam(value = "no", required = true) int qno,
			ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("USER/question/delete");
		queSVC.delete(qno);
		rv.setUrl(request.getContextPath()+"/question/list.com");
		mv.setView(rv);
		return mv;
	}
	
	
	//유저 마이페이지 7.16추가
	//글쓰기처리
	@RequestMapping(value="/mypage/question/write", method= RequestMethod.GET)
	public ModelAndView userWriteGetMypage(HttpServletRequest request, ModelAndView mv) {
		System.out.println("USER/question/writeMypage, "+request.getMethod()+"method");
		mv.setViewName("common/user/mypage/questionWrite");
		return mv;
	}
	@RequestMapping(value="/mypage/question/write", method= RequestMethod.POST)
	public ModelAndView userWritePostMypage(QuestionDTO qdto, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("USER/question/writeMypage, "+request.getMethod()+"method");
		System.out.println(qdto);
		queSVC.userInsertWrite(qdto, request.getSession());
		rv.setUrl(request.getContextPath()+"/mypage/question/list.com");
		mv.setView(rv);
		return mv;
	}
	@RequestMapping("/mypage/question/list")
	public ModelAndView listMyPageUser(
			@RequestParam(value = "nowPage", required = false, defaultValue="1") int nowPage,
			@RequestParam(value = "type", required = false, defaultValue="nall") String searchType,
			@RequestParam(value = "search", required = false, defaultValue="") String searchWord,
			ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("USER/Mypage/Question/list.mypageList");
		String qid = (String)request.getSession().getAttribute("MID");
		PageUtil pInfo = new PageUtil(searchWord,searchType);
		pInfo.setNowPage(nowPage);
		pInfo.setQid(qid);
		pInfo = queSVC.getPageInfoMyPageUser(pInfo);
		ArrayList<QuestionDTO> list = queSVC.searchListMyPageUser(pInfo, searchWord, searchType, qid);
		mv.addObject("PINFO", pInfo); //페이징 정보
		mv.addObject("LIST", list); //문의사항 상세 정보
		System.out.println(pInfo);
		System.out.println(list);
		
		mv.setViewName("common/user/mypage/questionList");
		return mv;
	}
	//글 상세보기
		@RequestMapping(value="/mypage/question/detail")
		public ModelAndView detailMypage(
				@RequestParam("no") int qno,
				@RequestParam(value="commPage", required = false, defaultValue= "1") int commPage,
				QuestionDTO qdto, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
			System.out.println("\nNoticeController.noticeDetailMypage.QuestionDTO = "+qno);
			qdto.setQno(qno);
			
			QuestionDTO de=queSVC.detail(qdto);//게시글
			PageUtil pInfo = queSVC.getCommPageInfo(qno, commPage);
			ArrayList<QuestionDTO> decomm=queSVC.getCommDetail(pInfo);//댓글
			
			mv.addObject("DETAIL",de);//게시글
			mv.addObject("PINFO", pInfo); //페이징 정보
			mv.addObject("COMM",decomm);//댓글
			
			System.out.println(de);
			System.out.println(pInfo);
			System.out.println(decomm);

			mv.setViewName("common/user/mypage/questionDetail");
			return mv;
		}
		//수정
		@RequestMapping(value="/mypage/question/update", method= RequestMethod.GET)
		public ModelAndView modifyGETMypage(
				@RequestParam(value = "no", required = true) int qno,
				QuestionDTO qdto,ModelAndView mv, HttpServletRequest request) {
			System.out.println("USER/question/updateMypage, "+request.getMethod()+"method");
			qdto.setQno(qno);
			QuestionDTO de=queSVC.detail(qdto);//게시글
			mv.addObject("DETAIL",de);//게시글
			System.out.println(de);
			mv.setViewName("common/user/mypage/questionModify");
			return mv;
		}
		@RequestMapping(value="/mypage/question/update", method= RequestMethod.POST)
		public ModelAndView modifyPOSTMypage(
				@RequestParam(value = "no", required = true) int qno,
				QuestionDTO qdto,ModelAndView mv, RedirectView rv, HttpServletRequest request) {
			System.out.println("USER/question/updateMypage, "+request.getMethod()+"method");
			System.out.println(qno+" "+qdto);
			queSVC.update(qdto);
			rv.setUrl(request.getContextPath()+"/mypage/question/list.com");
			mv.setView(rv);
			return mv;
		}
		//삭제
		@RequestMapping("/mypage/question/delete")
		public ModelAndView deleteMypage(
				@RequestParam(value = "no", required = true) int qno,
				ModelAndView mv, RedirectView rv, HttpServletRequest request) {
			System.out.println("USER/question/deleteMypage");
			queSVC.delete(qno);
			rv.setUrl(request.getContextPath()+"/mypage/question/list.com");
			mv.setView(rv);
			return mv;
		}
	
	
	//기업
	//목록보기
	@RequestMapping("/question/list/corp")
	public ModelAndView list(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			HttpSession session, ModelAndView mv) {
		String id=(String) session.getAttribute("COID");
		System.out.println("QuestionController list"+id);
		PageUtil pinfo = queSVC.getPageInfoById(nowPage,id);
		pinfo.setSearchWord(id);
		ArrayList<QuestionDTO> list = queSVC.list(pinfo);
		mv.addObject("PINFO", pinfo); //페이징 정보
		mv.addObject("LIST", list); //문의사항 상세 정보
		System.out.println(list.toString());
		mv.setViewName("/shopping/corp/question/list");
		return mv;
	}
	
	//글쓰기폼
	@RequestMapping("/question/write/corp")
	public String write() {
		return "shopping/corp/question/write";
	}
	
	//글쓰기처리
	@RequestMapping("/question/writepro/corp")
	public ModelAndView writepro(QuestionDTO qdto,HttpSession session,ModelAndView mv) {
		System.out.println(qdto);
		queSVC.insertWrite(qdto,session);
		RedirectView rv=new RedirectView("../list/corp.com");//목록보기로
		mv.setView(rv);
		return mv;
	}
	
	//상세보기
	@RequestMapping("/question/detail/corp")
	public ModelAndView detail(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		qdto.setQno(qno);
		System.out.println(qdto);
		QuestionDTO de=queSVC.detail(qdto);//게시글
		ArrayList<QuestionDTO> decomm=queSVC.detailcomm(qdto);//댓글
		mv.addObject("DETAIL",de);//게시글
		mv.addObject("COMM",decomm);//댓글
		System.out.println(de);
		System.out.println(decomm);
		mv.setViewName("/shopping/corp/question/detail");
		return mv;
	}
	
	//수정폼
	@RequestMapping("/question/update/corp")
	public ModelAndView update(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		qdto.setQno(qno);
		QuestionDTO de=queSVC.detail(qdto);//게시글
		ArrayList<QuestionDTO> decomm=queSVC.detailcomm(qdto);//댓글
		mv.addObject("DETAIL",de);//게시글
		mv.addObject("COMM",decomm);//댓글
		System.out.println(de);
		System.out.println(decomm);
		mv.setViewName("/shopping/corp/question/update");
		return mv;
		
	}
	
	//수정
	@RequestMapping("/question/up/corp")
	public ModelAndView up(QuestionDTO qdto,HttpSession session,ModelAndView mv) {
		System.out.println(qdto);
		queSVC.update(qdto,session);
		System.out.println(qdto.getQno());
		RedirectView rv=new RedirectView("../detail/corp.com?no="+qdto.getQno());//상세보기로
		mv.setView(rv);
		return mv;
	}
	
	//삭제
	@RequestMapping("/question/delete/corp")
	public ModelAndView delete(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		System.out.println(qdto);
		qdto.setQno(qno);
		queSVC.delete(qdto);
		RedirectView rv=new RedirectView("../list/corp.com");//목록보기로
		mv.setView(rv);
		return mv;
	}
	
	//댓글쓰기
	@RequestMapping("/question/wcomment")
	@ResponseBody
	public String writeComm(QuestionDTO qdto,HttpSession session) {
		System.out.println("000"+qdto);
		queSVC.insertComm(qdto);
		return "ok";
	}
	
	//삭제
	@RequestMapping("/question/dcomment")
	@ResponseBody
	public String deleteComm(QuestionDTO qdto) {
		System.out.println("del"+qdto);
		queSVC.deleteComm(qdto);
		return "ok";
	}
	
	//관리자(유저 단 7.13추가)
	//목록보기(유저)
	@RequestMapping("/question/list/user/admin")
	public ModelAndView listUserAdmin(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "qtitle") String searchType,
			HttpSession session, ModelAndView mv) {	
		PageUtil pInfo = queSVC.getPageInfoUser(nowPage,searchWord, searchType);
		ArrayList<QuestionDTO> list = queSVC.totalListUser(pInfo);
		mv.addObject("PINFO", pInfo); //페이징 정보
		mv.addObject("LIST", list); //문의사항 상세 정보
		System.out.println(list.toString());
		mv.setViewName("diary/admin/question/questionList");
		return mv;
	}
	//상세보기
	@RequestMapping("/question/detail/user/admin")
	public ModelAndView detailUserAdmin(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		qdto.setQno(qno);
		System.out.println(qdto);
		QuestionDTO de=queSVC.detail(qdto);//게시글
		ArrayList<QuestionDTO> decomm=queSVC.detailcomm(qdto);//댓글
		mv.addObject("DETAIL",de);//게시글
		mv.addObject("COMM",decomm);//댓글
		System.out.println(de);
		System.out.println(decomm);
		mv.setViewName("diary/admin/question/questionDetail");
		return mv;
	}
	//삭제
	@RequestMapping("/question/delete/user/admin")
	public ModelAndView deleteUserAdmin(@RequestParam(value = "no", required = true) int qno,
			QuestionDTO qdto,ModelAndView mv, RedirectView rv, HttpServletRequest request ) {
		System.out.println(qdto);
		qdto.setQno(qno);
		queSVC.delete(qdto);
		rv.setUrl(request.getContextPath()+"/question/list/user/admin.com");
		mv.setView(rv);
		return mv;
	}
	
	//목록 totalList
	@RequestMapping("/question/list/admin")
	public ModelAndView listAdmin(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			HttpSession session, ModelAndView mv) {	
		PageUtil pinfo = queSVC.getPageInfo(nowPage);
		ArrayList<QuestionDTO> list = queSVC.totalList(pinfo);
		mv.addObject("PINFO", pinfo); //페이징 정보
		mv.addObject("LIST", list); //문의사항 상세 정보
		System.out.println(list.toString());
		mv.setViewName("/shopping/admin/question/list");
		return mv;
	}
	
	//검색
	@RequestMapping("/question/search/admin")
	public ModelAndView search(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,ModelAndView mv,HttpServletRequest request) {	
		String type=request.getParameter("type");
		String word=request.getParameter("word");
		System.out.println(type+"////"+word+"////"+nowPage);
		if(type.equals("title")) {
			System.out.println("title");
			PageUtil pinfo = queSVC.getPageInfoByTitle(nowPage,word);
			pinfo.setSearchWord(word);
			ArrayList<QuestionDTO> list = queSVC.listByTitle(pinfo);
			mv.addObject("PINFO", pinfo); //페이징 정보
			mv.addObject("LIST", list); //문의사항 상세 정보
		}else if(type.equals("id")) {
			System.out.println("id");
			PageUtil pinfo = queSVC.searchPageInfoById(nowPage,word);
			pinfo.setSearchWord(word);
			ArrayList<QuestionDTO> list = queSVC.listById(pinfo);
			mv.addObject("PINFO", pinfo); //페이징 정보
			mv.addObject("LIST", list); //문의사항 상세 정보
		}
		mv.setViewName("/shopping/admin/question/list");
		return mv;
	}
	
	//상세보기
	@RequestMapping("/question/detail/admin")
	public ModelAndView detailAdmin(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		qdto.setQno(qno);
		System.out.println(qdto);
		QuestionDTO de=queSVC.detail(qdto);//게시글
		ArrayList<QuestionDTO> decomm=queSVC.detailcomm(qdto);//댓글
		mv.addObject("DETAIL",de);//게시글
		mv.addObject("COMM",decomm);//댓글
		System.out.println(de);
		System.out.println(decomm);
		mv.setViewName("/shopping/admin/question/detail");
		return mv;
	}
	
	//삭제
	@RequestMapping("/question/delete/admin")
	public ModelAndView deleteAdmin(@RequestParam(value = "no", required = true) int qno,QuestionDTO qdto,ModelAndView mv) {
		System.out.println(qdto);
		qdto.setQno(qno);
		queSVC.delete(qdto);
		RedirectView rv=new RedirectView("../list/admin.com");//목록보기로
		mv.setView(rv);
		return mv;
	}
}
