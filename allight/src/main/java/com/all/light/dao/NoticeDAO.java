package com.all.light.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.NoticeDTO;
import com.all.light.util.PageUtil;

public class NoticeDAO extends SqlSessionDaoSupport{
	@Autowired
	SqlSessionTemplate session;


	public ArrayList<NoticeDTO> searchList(PageUtil pInfo) {
		return (ArrayList)session.selectList("notice.searchList", pInfo);
	}


	public int getTotalCnt(PageUtil pInfo) {
		return session.selectOne("notice.totalCntWithSearch",pInfo);
	}


	public NoticeDTO getNotInfo(int nno) {
		return session.selectOne("notice.notInfo",nno);
	}


	public void notWrite(NoticeDTO notDTO) {
		session.insert("notice.notWrite",notDTO);
	}


	public void notModify(NoticeDTO notDTO) {
		session.update("notice.notUpdate",notDTO);
	}


	public void notDelete(int nno) {
		session.delete("notice.notDelete",nno);
	}


	public void increaseHit(int nno) {
		session.update("notice.hitIncrease", nno);
	}

}
