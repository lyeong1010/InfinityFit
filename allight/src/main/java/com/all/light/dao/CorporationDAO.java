package com.all.light.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.CorporationDTO;
import com.all.light.util.PageUtil;

public class CorporationDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;
	
	public HashMap login(HashMap map) {
		System.out.println("CorporationDAO : "+map.toString());
		HashMap result=session.selectOne("corporation.login", map);
		return result;
	}

	public int getTotalCnt(String searchWord) {
		System.out.println("CorporationDAO.getTotalCnt.searchWord = "+searchWord);
		return session.selectOne("corporation.totalCntWithSearch", searchWord);
	}

	public ArrayList<CorporationDTO> getSearchList(PageUtil pInfo) {
		System.out.println("CorporationDAO.getSearchList.pInfo = "+pInfo);
		return (ArrayList)session.selectList("corporation.searchList", pInfo);
	}

	public CorporationDTO getCorpInfo(int cono) {
		System.out.println("CorporationDAO.getCorpInfo.cono= "+cono);
		return session.selectOne("corporation.corpInfo", cono);
	}

	public void corpModify(CorporationDTO corDTO) {
		System.out.println("CorporationDAO.CorpModify.corDTO= "+corDTO);
		session.update("corporation.corpUpdate",corDTO);
	}

	public void corpDelete(int cono) {
		System.out.println("CorporationDAO.CorpDelete.cono = "+cono);
		session.delete("corporation.corpDelete",cono);
	}

	public void corpInsert(CorporationDTO corDTO) {
		session.insert("corporation.corpInsert",corDTO);
	}

	public CorporationDTO getCorpID(CorporationDTO corDTO) {
		return session.selectOne("corporation.getCorpId",corDTO);
	}

	public int corpModify2(CorporationDTO corDTO) {
		System.out.println("CorporationDAO.CorpModify.corDTO= "+corDTO);
		int i=session.update("corporation.corpUpdate",corDTO);
		return i;
	}
}
