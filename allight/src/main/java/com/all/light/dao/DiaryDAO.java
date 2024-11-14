package com.all.light.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.CaldictionaryDTO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.DiaryDTO;
import com.all.light.dto.MyExerciseDTO;
import com.all.light.dto.MyFoodDTO;

public class DiaryDAO extends SqlSessionDaoSupport {

	@Autowired
	SqlSessionTemplate session;
	
	
	// 메인화면에서 보일 그 달의 일별 음식칼로리,운동칼로리,체중 정보
	public List<DiaryDTO> main(String mid, int year, int month) {
		System.out.println("dao");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("mid", mid);
		map.put("year", year);
		map.put("month", month);
		
		List<DiaryDTO> list = session.selectList("diary.main", map);
		return list;
	}

	
	// 다이어리 번호로 다이어리 정보 가져오기
	public DiaryDTO diaryInfo(int dno) {
		DiaryDTO diary = session.selectOne("diary.diaryInfo", dno);
		return diary;
	}
	
	
	// 다이어리 번호로 myfood 정보 가져오기
	public List<MyFoodDTO> getMyFood(int dno) {
		List<MyFoodDTO> list = session.selectList("diary.myFood", dno);
		System.out.println(list.toString());
		return list;
	}

	// 섭취 칼로리 추가할 때 음식 검색
	public List<CaldictionaryDTO> searchFood(String searchWord) {
		List<CaldictionaryDTO> list = session.selectList("diary.searchFood", searchWord);
		return list;
	}
	
	// 섭취 칼로리 추가할 때 마이칼로리 전체목록(검색된 내용 없을때)
	public List<CaldictionaryDTO> AllMyFood(String mid) {
		List<CaldictionaryDTO> list = session.selectList("diary.AllMyFood", mid);
		return list;
	}

	// 내 음식 칼로리 추가
	public void insertMyFood(HashMap<String,Object> map) {
		int isok = session.insert("diary.insertMyFood", map);
		if(isok==1) {
			System.out.println("myfood 추가 성공:"+map.toString());
		}
	}

	// 사전번호로 사전정보 가져오기
	public CaldictionaryDTO getCdInfo(int cdno) {
		CaldictionaryDTO dto = session.selectOne("diary.getCdnameByCdno", cdno);
		return dto;
	}

	// 내 음식 칼로리 삭제
	public void myFoodDelete(int mfno) {
		int isok = session.delete("diary.myFoodDelete", mfno);
		if(isok==1) {
			System.out.println("myfood 삭제 성공 - mfno:" + mfno);
		}
	}

	// 다이어리 총음식칼로리 수정
	public void updateDiaryFoodcal(int dno, int caltotal) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", dno);
		map.put("dfoodcal", caltotal);
		int isok = session.update("diary.updateDiaryFoodcal", map);
		if(isok==1) {
			System.out.println("diary 수정 성공 - dno:" + dno + ", caltotal:" + caltotal);
		}
	}
	
	// 내 음식 번호(mfno)로 음식칼로리 가져오기
	public int getMfTototal(int mfno) {
		int mftotal = session.selectOne("diary.getMfTototal", mfno);
		return mftotal;
	}

	// 마이칼로리 등록(음식)
	public void insertMyCal(HashMap<String, Object> map) {
		int isok = session.insert("diary.insertMyCal", map);
		if(isok==1) {
			System.out.println("마이칼로리 등록 성공!");
		}else {
			System.out.println("마이칼로리 등록 실패");
		}
	}

	// 마이칼로리 삭제
	public void deleteMyCal(int cdno) {
		int isok = session.insert("diary.deleteMyCal", cdno);
		if(isok==1) {
			System.out.println("마이칼로리 삭제 성공!");
		}else {
			System.out.println("마이칼로리 삭제 실패");
		}
	}
	
	// 다이어리 등록(아이디로만)
	public void insertDiary(String mid, Date ddate) {
		System.out.println("diaDAO-insertDiary()-mid: "+mid+", date:"+ddate);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("mid", mid);
		map.put("ddate", ddate);
		
		int isok = session.insert("diary.insertDiaryIDOnly", map);
		if(isok==1) {
			System.out.println("다이어리 등록 성공!");
		}else {
			System.out.println("다이어리 등록 실패");
		}
	}
	
	// 다이어리 데이터 중 제일 최근 데이터 하나만 가져오기
	public DiaryDTO selectNewDiary() {
		DiaryDTO dto = session.selectOne("diary.selectNewDiary");
		System.out.println("diaDAO-selectNewDiary()-dto:"+dto.toString());
		return dto;
	}

	// 몸무게 업데이트
	public void updateDweight(int num, Double dweight) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", num);
		map.put("dweight", dweight);
		
		int isok = session.update("diary.updateDweight", map);
		if(isok==1) {
			System.out.println("몸무게 업데이트 성공-dweight:"+dweight);
		}else {
			System.out.println("몸무게 업데이트 실패-dweight:"+dweight);
		}
	}

	// 일기 업데이트
	public void updateDdiary(int num, String ddiary) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", num);
		map.put("ddiary", ddiary);
		
		int isok = session.update("diary.updateDdiary", map);
		if(isok==1) {
			System.out.println("일기 업데이트 성공!!");
		}else {
			System.out.println("일기 업데이트 실패~~");
		}
	}

	// 다이어리 번호로 myexercise 리스트 가져오기
	public List<MyExerciseDTO> getMyExer(int dno) {
		System.out.println("DAO-getMyExer()-dno:"+dno);
		List<MyExerciseDTO> list = session.selectList("diary.myExercise", dno);
		System.out.println("DAO-getMyExer():"+list.toString());
		return list;
	}

	// 소비 칼로리 추가할 때 운동 검색
	public List<CaldictionaryDTO> searchExer(String searchWord) {
		List<CaldictionaryDTO> list = session.selectList("diary.searchExer", searchWord);
		return list;
	}
	
	// 소비 칼로리 추가할 때 마이칼로리 전체목록(검색된 내용 없을때)
	public List<CaldictionaryDTO> AllMyExer(String mid) {
		List<CaldictionaryDTO> list = session.selectList("diary.AllMyExer", mid);
		return list;
	}
	
	// 마이칼로리 등록(운동)
	public void insertMyCal2(HashMap<String, Object> map) {
		int isok = session.insert("diary.insertMyCal2", map);
		if(isok==1) {
			System.out.println("마이칼로리 등록 성공!");
		}else {
			System.out.println("마이칼로리 등록 실패");
		}
	}
	
	// 내 운동 칼로리 추가
	public void insertMyExer(HashMap<String,Object> map) {
		System.out.println("map~~:"+map.toString());
		int isok = session.insert("diary.insertMyExer", map);
		if(isok==1) {
			System.out.println("myexercise 추가 성공:"+map.toString());
		}
	}
	
	// 다이어리 총운동칼로리 수정
	public void updateDiaryExercal(int dno, int caltotal) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", dno);
		map.put("dexercal", caltotal);
		int isok = session.update("diary.updateDiaryExercal", map);
		if(isok==1) {
			System.out.println("diary 수정 성공 - dno:" + dno + ", caltotal:" + caltotal);
		}
	}
	
	// 내 운동 번호(meno)로 음식칼로리 가져오기
	public int getMeTototal(int meno) {
		int mftotal = session.selectOne("diary.getMeTototal", meno);
		return mftotal;
	}

	// 내 운동 칼로리 삭제
	public void myExerDelete(int meno) {
		int isok = session.delete("diary.myExerDelete", meno);
		if(isok==1) {
			System.out.println("myexercise 삭제 성공 - meno:" + meno);
		}
	}

	// 내 운동 칼로리 모두 삭제
	public void myExerDeleteAll(int dno) {
		int isok = session.delete("diary.myExerDeleteAll", dno);
		if(isok==1) {
			System.out.println("myexercise 모두 삭제 성공 - dno:" + dno);
		}
	}
	
	// 내 음식 칼로리 모두 삭제
	public void myFoodDeleteAll(int dno) {
		int isok = session.delete("diary.myFoodDeleteAll", dno);
		if(isok==1) {
			System.out.println("myfood 모두 삭제 성공 - dno:" + dno);
		}
	}

	// 다이어리 삭제
	public void myDiaryDelete(int dno) {
		int isok = session.delete("diary.myDiaryDelete", dno);
		if(isok==1) {
			System.out.println("diary 삭제 성공 - dno:" + dno);
		}
	}

	// 이미지 업로드
	public void updateDimage(HashMap<String, Object> map) {
		int isok = session.update("diary.updateDimage", map);
		if(isok==1) {
			System.out.println("dimage 추가 성공~");
		}
	}

	// 해당 회원의 다이어리인지 확인
	public int IsMyDno(int dno, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", dno);
		map.put("mid", mid);
		int ok = session.selectOne("diary.IsMyDno", map);
		return ok;
	}

	// 다이어리 이미지 삭제
	public void myImgDelete(int dno) {
		int isok = session.update("diary.myImgDelete", dno);
		if(isok==1) {
			System.out.println("diary 이미지 삭제 성공");
		}
	}


	public ArrayList getchart(DiaryDTO ddto) {
		return (ArrayList)session.selectList("diary.getchart", ddto);
	}

	//calrecipe
	public CalrecipeDTO calrecipe(DiaryDTO diary) {
		int dno=diary.getDno();
		CalrecipeDTO cdto=session.selectOne("diary.calrecipe", diary);
		System.out.println(cdto);
		if(cdto!=null) {
			cdto.setAge(dno);
			session.update("diary.recipeup", cdto);
		}
		return cdto;
	}


	public void success(DiaryDTO diary) {
		session.update("diary.success",diary);
	}


	public List<DiaryDTO> getrate(DiaryDTO ddto) {
		return session.selectList("diary.getrate", ddto);
	}
	
	
	
	
	
	
	
}
