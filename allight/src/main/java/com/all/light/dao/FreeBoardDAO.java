package com.all.light.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.DiaryDTO;
import com.all.light.dto.FreeBoardDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.util.PageUtil;

public class FreeBoardDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	public void write(FreeBoardDTO fdto, String string) {
		if ("fboard".equals(string)) {
			session.insert("freeboard.write", fdto);
			System.out.println("자유게시판 입력완료");
		} else if ("fInfo".equals(string)) {
			session.insert("freeboard.writeFile", fdto);
			System.out.println("자유게시판 파일입력완료");
		}

	}

	public int getPageInfo(PageUtil pInfo) {
		return session.selectOne("freeboard.getPageInfo", pInfo);
	}

	public ArrayList<FreeBoardDTO> searchList(PageUtil pInfo) {
		return (ArrayList) session.selectList("freeboard.searchList", pInfo);
	}

	// Detail페이지
	public FreeBoardDTO getDetail(int fno) {
		return session.selectOne("freeboard.getDetail", fno);
	}

	public ArrayList<FreeBoardDTO> getFile(int fno) {
		return (ArrayList) session.selectList("freeboard.getFile", fno);
	}

	public int getTotalCntOfComm(int fno) {
		return session.selectOne("freeboard.getTotalCntOfComm", fno);
	}

	public ArrayList<FreeBoardDTO> getCommDetail(PageUtil pInfo) {
		System.out.println(pInfo);
		ArrayList<FreeBoardDTO> list=(ArrayList) session.selectList("freeboard.getCommList", pInfo);
		for(int i=0;i<list.size();i++) {
			int likeCnt = session.selectOne("freeboard.LikeCnt",list.get(i).getFcno());
			list.get(i).setAmount(likeCnt);
		}
		return list;
	}

	public void insertComm(FreeBoardDTO freDTO) {
		session.insert("freeboard.insertComm", freDTO);
	}

	public void deleteComm(int fcno) {
		session.delete("freeboard.deleteComm", fcno);
	}

	public void update(FreeBoardDTO freDTO, String string) {
			session.update("freeboard.update", freDTO);
	}
	public void updateFile(FreeBoardDTO freDTO, String string) {
		session.update("freeboard.updateFile", freDTO);
}

	public void deleteFile(FreeBoardDTO freDTO) {
		session.delete("freeboard.deleteFile", freDTO);
	}

	public void delete(FreeBoardDTO freDTO) {
		session.delete("freeboard.delete", freDTO);
	}

	public int getPageInfoMyPage(PageUtil pInfo) {
		return session.selectOne("freeboard.getPageInfoMyPage", pInfo);
	}

	public ArrayList<FreeBoardDTO> searchListMyPage(PageUtil pInfo) {
		return (ArrayList) session.selectList("freeboard.searchListMyPage", pInfo);
	}

	public void increaseHit(int fno) {
		session.update("freeboard.hitIncrease", fno);
	}

	
	// 좋아요 정보 가져오기
	public int getIsLike(int fcno, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("fcno", fcno);
		map.put("mid", mid);
		
		return session.selectOne("freeboard.isLike",map);
	}
	
	// 좋아요 insert
	public void LikeIns(int fcno, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("fcno", fcno);
		map.put("mid", mid);
		
		int isOk = session.insert("freeboard.LikeIns",map); //1이면 성공/0이면 실패
		if(isOk==1)System.out.println(fcno+"리뷰에 "+mid+"의 좋아요 insert 성공");
		if(isOk==0)System.out.println("리뷰 좋아요 insert 실패");
	}
	
	// 좋아요 delete
	public void LikeDel(int fcno, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("fcno", fcno);
		map.put("mid", mid);
		
		int isOk = session.delete("freeboard.LikeDel",map); //1이면 성공/0이면 실패
		if(isOk==1)System.out.println(fcno+"리뷰에 "+mid+"의 좋아요 delete 성공");
		if(isOk==0)System.out.println("리뷰 좋아요 delete 실패");
	}

	//해당 다이어리의 이미지,원래이름 가져오기
	public DiaryDTO getByDno(int dno) {
		DiaryDTO dto = session.selectOne("diary.diaryInfo",dno);
		return dto;
	}

}