package com.all.light.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dao.MemberDAO;
import com.all.light.dto.AddressDTO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.ItemDTO;
import com.all.light.dto.MemberDTO;
import com.all.light.util.PageUtil;

public class MemberService {
	@Autowired
	private MemberDAO memDAO;
	
	//이메일 유무 확인하기
	public int checkMail(String email) {
		
		int isMail=memDAO.checkMail(email);
		
		return isMail;
	}

	//인증코드생성
	public String getCode() throws Exception {
		
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			code += Integer.toString(rand.nextInt(10));
		}
		return code;
	}
	
	// 이메일 발송
	public void sendMail(String code,String memail) throws Exception {
		System.out.println("여기들어옴");
		System.out.println(memail);
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.gmail.com";
		int hostPort = 465;
		String hostSMTPid = "allight.adm@gmail.com";
		String hostSMTPpwd = "goallight!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "allight.adm@gmail.com";
		String fromName = "allight";
		String subject = "";
		String msg = "";

		subject = "Allight 본인 인증용 이메일 인증번호 입니다";
		msg += "<div style='text-align:center; margin:0 auto; width:350px; font-family:Helvetica Neue'>";
		msg += "<h1>안녕하세요! Allight입니다.</h1><br/>";
		msg += "<h3>아래의 이메일 인증번호를 입력하시면</h3>";
		msg += "<h3>인증절차가 완료되어 가입을 진행하실 수 있습니다.</h3>";
		msg += "<h3>앞으로도 많은 관심 부탁드립니다.</h3>";
		msg += "<h1 style='color: white; background:#ffc545;padding:10px;'>인증번호 : "+code+"</h1>";
		msg += "</div>";
		msg += "<div align='center' style='font-family:Helvetica Neue'>";
		msg += "<br/>";
		msg += "<h4 style='color: red;'>본 메일은 이메일 인증을 위해 발송된 발신 전용 메일입니다.</h4>";
		msg += "<h4 style='color: red;'>문의사항은 고객센터 또는 이메일 'allight.adm@gmail.com'로 문의주세요";
		msg += "</div>";

		// 받는 사람 E-Mail 주소
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(hostPort);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(memail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}
	}
	
	//일반로그인
	public HashMap login(MemberDTO memdto, HttpSession session, int cnt) {
		System.out.println("MemberService");
		HashMap map=new HashMap();
			map.put("mid",memdto.getMid());
			map.put("mpw",memdto.getMpw());
			HashMap result=memDAO.login(map);
			//날짜
//			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			if(result==null || result.size()==0) {
				//로그인실패
				cnt=cnt+1;
				session.setAttribute("mcnt", cnt);
				System.out.println("로그인실패");
			}else{
				//로그인성공
				System.out.println("로그인성공");
				session.setAttribute("MNO",result.get("MNO"));
				session.setAttribute("MID",result.get("MID"));
				session.setAttribute("MPW", result.get("MPW"));
				session.setAttribute("MEMAIL", result.get("MEMAIL"));
				session.setAttribute("MNAME", result.get("MNAME"));
				session.setAttribute("MBIRTH", result.get("MBIRTH"));
				session.setAttribute("MTEL", result.get("MTEL"));
				session.setAttribute("MTYPE", result.get("MTYPE"));
				session.setAttribute("MNICK", result.get("MNICK"));
				memDAO.logDate(result);
		}
			return result;
	}
	
	//카카오로그인
	public MemberDTO kakao(Map<String, Object> param, MemberDTO memdto, HttpSession session) {
		
		System.out.println(param.get("properties[nickname]"));//닉네임
		System.out.println(param.get("kakao_account[email]"));//이메일
		
		memdto.setMid((String) param.get("id"));
		memdto.setMname((String) param.get("properties[nickname]"));
		memdto.setMemail((String) param.get("kakao_account[email]"));
		MemberDTO result = memDAO.kakao(memdto);
		if (result == null) {
			// 로그인실패
			System.out.println("로그인실패");
			MemberDTO res = memDAO.kjoin(memdto);
			if (res != null) {
				session.setAttribute("MID", res.getMid());
				session.setAttribute("MEMAIL", res.getMemail());
				session.setAttribute("MNAME", res.getMname());
			}
			return res;
		} else {
			// 로그인성공
			System.out.println("로그인성공");
			session.setAttribute("MNO",result.getMno());
			session.setAttribute("MID", result.getMid());
			session.setAttribute("MEMAIL", result.getMemail());
			session.setAttribute("MNAME", result.getMname());
			session.setAttribute("MBIRTH", result.getMbirth());
			session.setAttribute("MTEL", result.getMtel());
			session.setAttribute("MTYPE", result.getMtype());
			session.setAttribute("MNICK", result.getMnick());
		}
		return result;
	}
	
	//카카오폼
	public MemberDTO kakaojoin(MemberDTO memdto, HttpSession session) {
		memdto.setMid((String) session.getAttribute("MID"));
		MemberDTO res = memDAO.kakao(memdto);
		return res;
	}
	
	public void kakaoup(MemberDTO memdto) {
		memdto.setMtel(memdto.getMtel()+"-"+memdto.getMtel1()+"-"+memdto.getMtel2());
		memDAO.kakaoup(memdto);
	}
	
	public void kakaose(MemberDTO memdto, HttpSession session) {
		MemberDTO mem=memDAO.kakao(memdto);
		session.setAttribute("MNO",mem.getMno());
		session.setAttribute("MID",mem.getMid());
		session.setAttribute("MEMAIL",mem.getMemail());
		session.setAttribute("MNAME", mem.getMname());
		session.setAttribute("MBIRTH", mem.getMbirth());
		session.setAttribute("MTEL", mem.getMtel());
		session.setAttribute("MTYPE", mem.getMtype());
		session.setAttribute("MNICK", mem.getMnick());
	}
	
	//로그아웃
	public void logout(HttpSession session) {
		if(session.getAttribute("MID")!=null) {
			session.invalidate();
		}else {
			System.out.println("null logout");
		}
	}

	// 회원가입
	public void join(MemberDTO memdto) {
		//email하나로 합치기
		memdto.setMemail(memdto.getMemail()+"@"+memdto.getMemail2());
		//핸드폰번호 합치기
		memdto.setMtel(memdto.getMtel()+"-"+memdto.getMtel1()+"-"+memdto.getMtel2());
		memDAO.join(memdto);
	}
	
	//아이디찾기
	public MemberDTO findId(MemberDTO memdto) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mname", memdto.getMname());
		map.put("memail", memdto.getMemail());
		MemberDTO mem=memDAO.findId(map);
		return mem;
	}
	
	//아이디 중복확인
	public MemberDTO getMemberID(MemberDTO memdto) {
		return memDAO.getMemberID(memdto);
	}
		
	//비밀번호 찾기 - 비밀번호 변경
	public void findPwChange(MemberDTO memdto) {
		System.out.println("findPwChange");
		memDAO.findPwChange(memdto);
	}
		
	//닉네임 중복확인
	public MemberDTO getMemberNICK(MemberDTO memdto) {
		return memDAO.getMemberNICK(memdto);
	}
		
	//검색 및 회원수 가져오기 메소드
	public PageUtil getPageInfo(int nowPage, String searchWord) {
		int totalCount = memDAO.getTotalCnt(searchWord);
		// PageUtil(보고싶은페이지, 전체게시물수);
		// 검색어에 따른 총 게시물 수를 구하고 페이지 정보를 리턴함
		PageUtil pInfo = new PageUtil(nowPage, totalCount);
		return pInfo;
	}
	
	//검색 및 리스트 출력 메소드
	public ArrayList<MemberDTO> searchList(PageUtil pInfo, String searchWord){
		pInfo.setSearchWord(searchWord);
		return memDAO.searchList(pInfo);
	}
	
	public MemberDTO getMInfo(int mno) {
		return memDAO.getMInfo(mno);
	}

	public void memModify(MemberDTO memDTO) {
		memDAO.memModify(memDTO);
	}
	
	public int memModify2(MemberDTO memDTO) {
		int i=memDAO.memModify2(memDTO);
		return i;
	}

	public void memDelete(int mno) {
		memDAO.memDelete(mno);
	}
	
	// 배송지 검색하기
	public ArrayList<AddressDTO> address(HttpSession session) {
		System.out.println("서비스 address() 진입");
		String mid = (String) session.getAttribute("MID");
		
		ArrayList<AddressDTO> list = memDAO.addresslist(mid);
		System.out.println("서비스 리스트 확인 list =" + list);
		return list;
	}
	
	// 배송지 추가하기
	public void addressinsert(AddressDTO aDTO, HttpSession session, ArrayList list) {
		System.out.println("서비스 addressinsert() 진입");
		aDTO.setMid((String)session.getAttribute("MID"));
		memDAO.addressinsert(aDTO);
		
		System.out.println("서비스 addressinsert aDTO" + aDTO);
	}

	// 배송지 삭제하기
	public void addressdelete(AddressDTO aDTO) {
		System.out.println("서비스 addressdelete() 진입");
		memDAO.addressdelete(aDTO);
	}

	public int delete2(MemberDTO memDTO) {
		int i=memDAO.memDelete2(memDTO);
		return i;
	}

}