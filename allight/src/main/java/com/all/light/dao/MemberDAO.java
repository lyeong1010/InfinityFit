package com.all.light.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.AddressDTO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.MemberDTO;
import com.all.light.util.PageUtil;

public class MemberDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	//회원가입
	public void join(MemberDTO memdto) {
		System.out.println("MemberDAO - join");
		session.insert("member.join", memdto);
	}
	
	//아이디찾기
	public MemberDTO findId(HashMap map) {
		System.out.println("findId DAO 진입");	
		MemberDTO result = (MemberDTO)session.selectOne("member.findId",map);
		System.out.println(result);
		return result;
	}
	
	//아이디 중복확인
	public MemberDTO getMemberID(MemberDTO memdto) {
		return session.selectOne("member.getMemberID", memdto);
	}

	public HashMap login(HashMap map) {
		System.out.println("MemberDAO : " + map.toString());
		HashMap result = session.selectOne("member.login", map);
		return result;
	}

	//이메일 인증
	public int checkMail(String memail) {
		System.out.println("dao-"+memail);
		return session.selectOne("member.checkMail", memail);
	}
	
	//비밀번호 찾기 - 비밀번호 변경
	public void findPwChange(MemberDTO memdto) {
		System.out.println("MemberDAO - findPwChange");
		System.out.println(memdto);
		session.update("member.findPwChange", memdto);
	}

	//닉네임 중복확인
	public MemberDTO getMemberNICK(MemberDTO memdto) {
		return session.selectOne("member.getMemberNICK", memdto);
	}
		
	// 7.3 최근 접속일 갱신
	public void logDate(HashMap result) {
		System.out.println("map = "+result+"\nmap.get(MNO) = "+result.get("MNO").getClass());
		// 오류해결 java.math.BigDecimal cannot be cast to java.lang.Integer
			// 1. int mno =Integer.parseInt(String.valueOf(result.get("MNO"))); -(SQL.xml에서 int로 받을 시)
			// 2. session.update("member.logDate", result.get("MNO")); -(SQL.xml에서 HashMap으로 받을 시)
		session.update("member.logDate", result.get("MNO"));
	}

	//id확인
	public MemberDTO kakao(MemberDTO memdto) {
		MemberDTO result = session.selectOne("member.kakao", memdto);
		return result;
	}

	public MemberDTO kjoin(MemberDTO memdto) {
		// 수정삭제가 만들어진후에 변경
		int i = session.insert("member.kinsert", memdto);
		MemberDTO res = null;
		if (i == 1) {
			res = session.selectOne("member.kakao", memdto);
		}
		return res;
	}
	
	public void kakaoup(MemberDTO memdto) {
		session.update("member.kakaoup", memdto);
	}
	
	//7.7 검색 시, 검색 없을 시 회원 수 가져오기
	public int getTotalCnt(String searchWord) {
		int totalCnt = session.selectOne("member.totalCntWithSearch", searchWord);
		return totalCnt;
	}

	// 유태우7.3작성
	public ArrayList<MemberDTO> searchList(PageUtil pInfo) {
		System.out.println("MemberDAO.searchList = " + pInfo.toString());
		return (ArrayList) session.selectList("member.searchList", pInfo);
	}

	public MemberDTO getMInfo(int mno) {
		System.out.println("MemberDAO.mInfoList.mno = " + mno);
		return session.selectOne("member.memInfo", mno);
	}

	public void memModify(MemberDTO memDTO) {
		session.update("member.memUpdate", memDTO);
		System.out.println("수정 성공, ModifyInfo = "+memDTO);
	}
	
	public int memModify2(MemberDTO memDTO) {
		int i=session.update("member.memUpdate", memDTO);
		System.out.println("수정 성공, ModifyInfo = "+memDTO);
		return i;
	}

	public void memDelete(int mno) {
		session.delete("member.memDelete", mno);
	}

	
	// 배송지 불러오기
	public ArrayList<AddressDTO> addresslist(String mid) {
		System.out.println("배송지 불러오기");
		return (ArrayList) session.selectList("member.memAddress", mid);
	}

	// 배송지 추가하기
	public void addressinsert(AddressDTO aDTO) {
		System.out.println("배송지 추가하기");
		System.out.println("배송지 aDTO = " + aDTO);
		session.insert("member.memaddressinsert", aDTO);
		System.out.println("배송지 aDTO SQL 완료 = " + aDTO);
	}

	// 배송지 삭제하기
	public void addressdelete(AddressDTO aDTO) {
		System.out.println("배송지 삭제하기");
		session.delete("member.memaddressdelete", aDTO);
	}

	public CalrecipeDTO getRecipe(String mid) {
		return session.selectOne("calrecipe.getRecipe", mid);
	}

	public int memDelete2(MemberDTO memDTO) {
		int i=session.delete("member.memDelete2", memDTO);
		return i;
	}


}
