package com.all.light.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.ItemQuestionDTO;
import com.all.light.dto.NoticeDTO;
import com.all.light.dto.QuestionDTO;
import com.all.light.util.PageUtil;

public class QuestionDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 기업
	public ArrayList list(PageUtil pinfo) {
		return (ArrayList) session.selectList("question.list", pinfo);
	}

	public int getTotalCntById(String id) {
		int totalCnt = session.selectOne("question.totalCntById", id);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public void insertWrite(QuestionDTO qdto) {
		session.insert("question.insert", qdto);
	}

	public QuestionDTO datail(QuestionDTO qdto) {
		return session.selectOne("question.detail", qdto);
	}

	public ArrayList<QuestionDTO> detailcomm(QuestionDTO qdto) {
		ArrayList<QuestionDTO> comm = (ArrayList) session.selectList("question.detailcomm", qdto);
		return comm;
	}

	public void delete(QuestionDTO qdto) {
		session.delete("question.delete", qdto);
	}

	public void update(QuestionDTO qdto) {
		System.out.println(qdto);
		session.update("question.update", qdto);
	}

	// 관리자
	public int getTotalCnt() {
		int totalCnt = session.selectOne("question.totalCnt");
		System.out.println(totalCnt);
		return totalCnt;
	}

	public ArrayList<QuestionDTO> totalList(PageUtil pinfo) {
		return (ArrayList) session.selectList("question.totalList", pinfo);
	}

	public int getTotalCntByTitle(String word) {
		int totalCnt = session.selectOne("question.totalCntByTitle", word);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public ArrayList<QuestionDTO> listByTitle(PageUtil pinfo) {
		System.out.println("question.listByTitle" + pinfo);
		return (ArrayList) session.selectList("question.listByTitle", pinfo);
	}

	public int searchPageInfoById(String word) {
		int totalCnt = session.selectOne("question.totalCntByIdSearch", word);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public ArrayList<QuestionDTO> listById(PageUtil pinfo) {
		System.out.println("question.listById" + pinfo);
		return (ArrayList) session.selectList("question.listById", pinfo);
	}

	// 댓글
	public void insertComm(QuestionDTO qdto) {
		session.insert("question.insertComm", qdto);
	}

	public void deleteComm(QuestionDTO qdto) {
		session.delete("question.deleteComm", qdto);
	}

	// 유저
	public void userInsert(QuestionDTO qdto) {
		System.out.println("DAO.qdto = " + qdto);
		session.insert("question.userInsert", qdto);
	}

	public ArrayList<QuestionDTO> searchList(PageUtil pInfo) {
		return (ArrayList) session.selectList("question.searchList", pInfo);
	}

	public int getTotalCnt(PageUtil pInfo) {
		return session.selectOne("question.totalCntWithSearch", pInfo);
	}

	public void delete(int qno) {
		session.delete("question.delete", qno);
	}

	// 관리자(유저)
	public int getTotalCntUser(PageUtil pInfo) {
		int totalCnt = session.selectOne("question.totalCntUser",pInfo);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public ArrayList<QuestionDTO> totalListUser(PageUtil pinfo) {
		return (ArrayList) session.selectList("question.totalListUser", pinfo);
	}
	
	//마이페이지(유저)
	public int totalCntUserMyPage(PageUtil pInfo) {
		return session.selectOne("question.totalCntUserMyPage",pInfo);
	}

	public ArrayList<QuestionDTO> searchListMyPageUser(PageUtil pInfo) {
		return (ArrayList) session.selectList("question.searchListUserMyPage",pInfo);
	}

	
	// 기업 답변 - 류지혁
	public void insertiq(ItemQuestionDTO iqDTO) {
		session.insert("question.insertiq", iqDTO);
	}
	// 기업 답변 삭제 - 류지혁
	public void deleteiq(int iqcno) {
		session.delete("question.deleteiq", iqcno);
		
	}

	public int getTotalCntOfComm(int qno) {
			return session.selectOne("question.getTotalCntOfComm", qno);
	}

	public ArrayList<QuestionDTO> getCommDetail(PageUtil pInfo) {
		return (ArrayList) session.selectList("question.getCommDetail",pInfo);
	}
}
