package com.all.light.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.AddressDTO;
import com.all.light.dto.MemberDTO;
import com.all.light.service.MemberService;
import com.all.light.util.Nologin;
import com.all.light.util.PageUtil;

@Controller
public class MemberController {
	@Autowired
	private MemberService memSVC;

	//메인화면에서 회원가입폼 이동
	@RequestMapping("/joinFrm")
	public String join() {
		return "common/user/join";
	}
	
	//회원가입
	@RequestMapping("/join")
	public ModelAndView join(MemberDTO memdto,ModelAndView mv) {
		memSVC.join(memdto);
		RedirectView rv=new RedirectView("./joinSuccess.com");
		mv.setView(rv);
		return mv;
	}
	
	//아이디 중복확인
	@ResponseBody
	@RequestMapping("/idChk")
	public String idChk(MemberDTO memdto) {
		String data=null;
		MemberDTO mem = memSVC.getMemberID(memdto);
		System.out.println(mem);
		if (mem != null) {
			data="fail";
		} else if(mem == null) {
			data="success";
		}
		return data;
	}
	
	//닉네임 중복확인
	@ResponseBody
	@RequestMapping("/nickChk")
	public String nickChk(MemberDTO memdto) {
		String data=null;
		MemberDTO mem = memSVC.getMemberNICK(memdto);
		System.out.println(mem);
		if (mem != null) {
			data="fail";
		} else if(mem == null) {
			data="success";
		}
		System.out.println(data);
		return data;
	}
	
	//회원가입용 이메일이 존재하는 이메일인지 확인하고 인증코드 발송
    @RequestMapping("/checkMail")
    @ResponseBody
	public String checkMail(HttpServletRequest request) throws Exception {
    	System.out.println("이메일 확인 들어옴!");
    	String email=request.getParameter("email");
    	
		int isMail=memSVC.checkMail(email);
		System.out.println(isMail);
		
		if(isMail==0) {//같은 이메일이 없으면 -> 코드번호 전송
			
			System.out.println("같은이메일 없어");
			String scode=memSVC.getCode();//코드생성
			System.out.println(scode);
			memSVC.sendMail(scode,email);//메일전송
			
			String code=scode;
			System.out.println("이메일 확인 완료!");
			
			return code;
		}else {//같은 이메일이 있으면 -> 1 전송
			System.out.println("같은이메일 있어");
			return "1";
		}
    }
	    
	//회원가입완료
	@RequestMapping("/joinSuccess")
	public String joinSuccess() {
		return "common/user/joinSuccess";
	}
		
	//아이디찾기 폼
	@RequestMapping("/findIdFrm")	
	public String findIdFrm(){
		System.out.println("요청 함수 findIdFrm()!"); 
		return "common/user/findId";
	}
		
	//아이디찾기
	@RequestMapping("/findId")
	public ModelAndView findId(MemberDTO memdto,ModelAndView mv) {
		MemberDTO mem = memSVC.findId(memdto);
		mv.addObject("MEM",mem);
		mv.setViewName("common/user/findIdResult");
		return mv;
	}
	
	//로그인 화면에서 비밀번호찾기 폼으로
	@RequestMapping("/findPwFrm")	
	public String findPwFrm(){
		System.out.println("요청 함수 findPwFrm!"); 
		return "common/user/findPw";
	}
	
	//비밀번호찾기
	@RequestMapping("/findPw")
	public ModelAndView findPw(MemberDTO memdto,ModelAndView mv) {
		MemberDTO mem = memSVC.findId(memdto);
		mv.addObject("MEM",mem);
		mv.setViewName("common/user/findPwChange");
		return mv;
	}
	
	//비밀번호 찾기용 이메일 인증 후 인증코드 발송
	@RequestMapping("/checkPwMail")
	@ResponseBody
	public String checkPwMail(HttpServletRequest request) throws Exception {
		System.out.println("비밀번호찾기 이메일인증들어옴");
		String email=request.getParameter("email");
		
		int isMail=memSVC.checkMail(email);
		System.out.println(isMail);
		
		if(isMail==1) {//같은 이메일이 있으면 -> 코드번호 전송
			
			System.out.println("같은이메일 있어");
			String scode=memSVC.getCode();//코드생성
			System.out.println(scode);
			memSVC.sendMail(scode,email);//메일전송
			
			String code = scode;
			System.out.println("이메일 확인 완료!");
			
			return code;
		}else {//같은 이메일이 없으면 ->
			System.out.println("같은이메일 없어");
			return "1";
		}
	}
	
	//비밀번호 찾기 - 비밀번호 변경
	@RequestMapping("/findPwChange")
	public ModelAndView findPwEmail(MemberDTO memdto,ModelAndView mv) {
		System.out.println("findPwChange");
		memSVC.findPwChange(memdto);
		RedirectView rv=new RedirectView("./findPwSuccess.com");
		mv.setView(rv);
		return mv;
	}
	
	//비밀번호찾기 완료
	@RequestMapping("/findPwSuccess")
	public String findPwSuccess() {
		return "common/user/findPwSuccess";
	}
		
	//로그인폼
	@RequestMapping("/login")
	public ModelAndView log(ModelAndView mv,HttpSession session,RedirectView rv,
			@RequestParam(value="reUrl", required=false)String reUrl) {
		mv.addObject("reUrl", reUrl);
		if("fail".equals(session.getAttribute("LoginCheck"))) {
			rv.setUrl("./LoginCheck.com"); //로그인 체크필터에 걸렸을 때 이동하는 페이지
			mv.setView(rv);
		}
		else {
			mv.setViewName("common/loginform");
		}
		return mv;
	}
	
	@RequestMapping("/LoginCheck")
	public ModelAndView LoginCheck(
			ModelAndView mv, HttpSession session,RedirectView rv,
			@RequestParam(value="reUrl", required=false)String reUrl) {
		session.setAttribute("LoginCheck","init");
		mv.setViewName("common/loginformCheck");
		return mv;
	}
	
	//로그아웃
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session,ModelAndView mv,RedirectView rv) {
		if(session.getAttribute("MID")==null) {
			rv.setUrl("./login.com");
			mv.setView(rv);
			return mv;
		}
		memSVC.logout(session);
		rv.setUrl("./main.com");
		mv.setView(rv);
		return mv;
	}
	
	//로그인
	@RequestMapping("/log")
	public ModelAndView login(MemberDTO memdto,HttpSession session,ModelAndView mv,RedirectView rv,
			@RequestParam(value="reUrl", required=false)String reUrl,
			@RequestParam(value = "mcnt", required = false, defaultValue = "0") int cnt) {
		
		HashMap result=null;
		if(memdto.getArr()!=null) {
			String[] a=memdto.getArr();
			String str=a[0];
			for(int i=1;i<a.length;i++) {
				str=str+a[i];
			}
			if(str.equals(memdto.getAuto())){
				result=memSVC.login(memdto,session,cnt);
			}
		}
		
		result=memSVC.login(memdto,session,cnt);
		String[] arr=null;	
		if(result==null || result.size()==0) {
			if(cnt>=3) {
				System.out.println("auto");
				arr=Nologin.auto();
				memdto.setArr(arr);
				mv.addObject("memdto", memdto);
				mv.setViewName("common/loginform");
			}else {
				rv.setUrl("./login.com");
				mv.setView(rv);	
			}
			return mv;
		}
		
		System.out.println(reUrl);
		if(reUrl==null || reUrl.equals("")) {
			rv.setUrl("./main.com");
		}else {
			rv.setUrl(reUrl);
		}
		mv.setView(rv);
		return mv;
	}
	
	//카카오로그인
	@RequestMapping(value = "/kakao", method = RequestMethod.POST)
	@ResponseBody
	public String kakao(@RequestParam Map<String, Object> param, HttpSession session, MemberDTO memdto) {
		MemberDTO re = memSVC.kakao(param, memdto, session);
		if (re.getMnick() == null) {
			return "check";
		}
		return null;
	}
	
	//카카오 회원가입 폼 kakaojoin
	@RequestMapping(value = "/kakaojoin")
	public ModelAndView kakaojoin(HttpSession session, MemberDTO memdto,ModelAndView mv) {
		MemberDTO mem = memSVC.kakaojoin(memdto, session);
		mv.addObject("mdto", mem);
		mv.setViewName("common/user/kakaojoin");
		return mv;
	}
	
	@RequestMapping(value = "/kakaoj")
	public ModelAndView kakaoj(HttpSession session,RedirectView rv, MemberDTO memdto,ModelAndView mv) {
		memSVC.kakaoup(memdto);
		memSVC.kakaose(memdto,session);
		rv.setUrl("./main.com");
		mv.setView(rv);;
		return mv;
	}
	
	
	// 카카오 로그아웃
	@RequestMapping(value="/kakaout", method=RequestMethod.POST)
	@ResponseBody
	public String kakaout(@RequestParam Map<String, Object> param, HttpSession session,MemberDTO memdto) {
		if(session.getAttribute("MID")==null) {
			return null;
		}
		memSVC.logout(session);
		return "out";
	}

	// 7.2유태우 작성, 7.3검색 메소드 추가
	//회원 리스트 및 검색 메소드
	@RequestMapping("/member/admin")
	public ModelAndView adminMember(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord,
			ModelAndView mv, RedirectView rv) {

		System.out.println("\nmemberController.adminMember");

		//페이지 객체에 검색어와 현재 페이지를 넘기고 회원 리스트를 반환
		PageUtil pInfo = memSVC.getPageInfo(nowPage, searchWord);
		ArrayList<MemberDTO> map = memSVC.searchList(pInfo, searchWord);
		
		System.out.println("list = "+map.toString());
		System.out.println("pinfo = "+pInfo.toString());
		mv.addObject("LIST", map); //회원 정보 리스트
		mv.addObject("PINFO", pInfo); //페이징 정보
		
		mv.setViewName("common/admin/member");
		return mv;
	}
	
	//7.3 관리자 회원 수정 메소드
	@RequestMapping(value="/member/modify/admin", method= RequestMethod.GET)
	public ModelAndView adminModifyMemberGet(
			// @RequestParam(value = "mno") int mno,
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord,
			MemberDTO memDTO,	ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("memberController.modify.Member,"+request.getMethod()+"method");
		//파라미터 받기, 비즈니스로직
		memDTO = memSVC.getMInfo(memDTO.getMno());
		System.out.println("memInfo = "+memDTO.toString());
		//모델지정
		mv.addObject("MEMINFO", memDTO); //회원 상세 정보
		//뷰지정
		//get메소드의 Requestparam의 정보가 그대로 넘어감
		//mv.setViewName("common/admin/memberModify?search="+searchWord+"&nowPage="+nowPage+"&mno="+mno);
		mv.setViewName("common/admin/memberModify");
		return mv;
	}
	
	@RequestMapping(value="/member/modify/admin", method = RequestMethod.POST)
	public ModelAndView adminModifyMemberPost(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			MemberDTO memDTO, ModelAndView mv, RedirectView rv, HttpServletRequest request) {
		System.out.println("memberController.modify.Member, "+request.getMethod()+"method");
		//파라미터 받기, 비즈니스로직
		System.out.println("memInfo = "+memDTO.toString());
		memSVC.memModify(memDTO);
		//뷰지정
		rv.setUrl(request.getContextPath()+"/member/admin.com?search="+searchWord+"&nowPage="+nowPage);
		//리다이렉트시 Requestparam의 정보를 뷰 경로에 설정해서 넘겨줘야함
		//rv.setUrl(request.getContextPath()+"/member/admin.com");
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping("/member/delete/admin")
	public ModelAndView adminDeleteMember(
			@RequestParam(value = "mno") int mno,
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false) String searchWord,
			ModelAndView mv, RedirectView rv,HttpServletRequest request) {
		System.out.println("memberController.delete.Member");
		//파라미터 받기, 비즈니스로직
		System.out.println("mno = "+mno);
		memSVC.memDelete(mno);
		//뷰지정
		rv.setUrl(request.getContextPath()+"/member/admin.com?search="+searchWord+"&nowPage="+nowPage);
		mv.setView(rv);
		return mv;
	}
	
	
	// 류지혁 작성
	// 회원 배송지 리스트, 추가 폼
	@GetMapping("/member/mypage/address")
	public ModelAndView address(HttpSession session, ModelAndView mv) {
		System.out.println("컨트롤러 회원 배송지 목록보기 - address() 요청");
		System.out.println(session.getAttribute("MID"));
		
		ArrayList<AddressDTO> list = memSVC.address(session);
		mv.addObject("LIST", list);
		mv.setViewName("common/user/address");
		System.out.println("컨트롤러 회원 배송지 보기 - list = " + list);
		return mv;	
	}
	
	// 회원 배송지 추가
	@RequestMapping("/member/mypage/addressinsert")
	public ModelAndView addressinsert(AddressDTO aDTO, HttpSession session, ModelAndView mv) {
		System.out.println("컨트롤러 배송지 추가 - addressinsert() 요청");
		ArrayList list = new ArrayList();
		memSVC.addressinsert(aDTO, session, list);
		System.out.println(aDTO);
		System.out.println(list);
		RedirectView rv = new RedirectView("../mypage/address.com");
		mv.setView(rv);	
		return mv;
	}
	
	// 회원 배송지 삭제
	@RequestMapping("/member/mypage/addressdelete")
	public ModelAndView addressdelete(
			@RequestParam(value="no") int no,
			AddressDTO aDTO, ModelAndView mv) {
		System.out.println("컨트롤러 배송지 삭제 - addressdelete() 요청");
		aDTO.setAno(no);
		memSVC.addressdelete(aDTO);
		RedirectView rv = new RedirectView("../mypage/address.com");
		mv.setView(rv);
		return mv;
	}
	
	// 장한별 작성
    // 회원정보수정
		@RequestMapping(value="/mypage/member/modify", method= RequestMethod.GET)
		public ModelAndView ModifyMemberGet(
				MemberDTO memDTO,	ModelAndView mv, RedirectView rv, HttpSession session) {
			memDTO = memSVC.getMInfo(Integer.parseInt(String.valueOf(session.getAttribute("MNO"))));
			System.out.println("memInfo = "+memDTO);
			mv.addObject("MEMINFO", memDTO);
			mv.setViewName("common/user/Modify member/Modifymember");
			return mv;
		}
		
		@RequestMapping(value="/mypage/member/modify", method= RequestMethod.POST)
		public ModelAndView ModifyMemberPost(
				MemberDTO memDTO,	ModelAndView mv, RedirectView rv,HttpServletRequest request) {
			System.out.println("memberController.modify.Member, Post method");
			System.out.println("memDTO = "+memDTO.toString());
			int i=memSVC.memModify2(memDTO);
			if(i==1) {//성공
				mv.setViewName("common/user/Modify member/Modifymemsuccess");
			}else {//i==0 실패		
				mv.setViewName("common/user/Modify member/Modifymemfail");
			}
			return mv;
		}
		
		//회원 탈퇴
		@RequestMapping(value="/mypage/member/delete", method= RequestMethod.GET)
		public ModelAndView deleteMemberGet(
				MemberDTO memDTO,	ModelAndView mv, RedirectView rv, HttpSession session) {
			memDTO = memSVC.getMInfo(Integer.parseInt(String.valueOf(session.getAttribute("MNO"))));
			System.out.println("memInfo = "+memDTO);
			mv.addObject("MEMINFO", memDTO);
			mv.setViewName("common/user/delete member/deletemember");
			return mv;
		}
		
		@RequestMapping(value="/mypage/member/delete", method= RequestMethod.POST)
		public ModelAndView deleteMemberPost(
				MemberDTO memDTO,	ModelAndView mv, RedirectView rv,HttpServletRequest request,HttpSession session) {
			System.out.println("memberController.modify.Member, Post method");
			System.out.println("memDTO = "+memDTO.toString());
			int i=memSVC.delete2(memDTO);
			if(i==1) {//성공
				session.invalidate();
				mv.setViewName("common/user/delete member/deletememsuccess");
			}else {//i==0 실패		
				mv.setViewName("common/user/delete member/deletememfail");
			}
			return mv;
		}
	}