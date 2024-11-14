package com.all.light.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.DiaryDAO;
import com.all.light.dto.CaldictionaryDTO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.DiaryDTO;
import com.all.light.dto.MyExerciseDTO;
import com.all.light.dto.MyFoodDTO;

@Service
public class DiaryService {
	
	@Autowired
	private DiaryDAO diaDAO;
	
	/* String to Date
		String from = "2013-04-08 10:10:10";
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date to = transFormat.parse(from);
		
		// Date to String
		Date from = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String to = transFormat.format(from);
	*/
	
	// 메인화면(달력)
	public List<DiaryDTO> main(List<DiaryDTO> dateList, String mid, Integer year, Integer month) {
		System.out.println("서비스");
		List<DiaryDTO> list = diaDAO.main(mid,year,month);
		
		for(int i=0; i<dateList.size();i++) {
			for(int j=0;j<list.size();j++) {
				if(list.get(j).getDay().equals(dateList.get(i).getDay())) {
					System.out.println(list.get(j).getDay());
					dateList.get(i).setDno(list.get(j).getDno());
					dateList.get(i).setDexercal(list.get(j).getDexercal());
					dateList.get(i).setDfoodcal(list.get(j).getDfoodcal());
					dateList.get(i).setDweight(list.get(j).getDweight());
					dateList.get(i).setDimage(list.get(j).getDimage());
					dateList.get(i).setDdiary(list.get(j).getDdiary());
					dateList.get(i).setDdate(list.get(j).getDdate());
					dateList.get(i).setDsucc(list.get(j).getDsucc());
				}
			} 
		}
		return dateList;
	}

	// 다이어리 번호로 다이어리 정보 가져오기
	public DiaryDTO diaryInfo(int dno) {
		DiaryDTO diary = diaDAO.diaryInfo(dno);
		return diary;
	}
	
	// 다이어리 번호로 myfood 리스트 가져오기
	public List<MyFoodDTO> getMyFood(int dno) {
		List<MyFoodDTO> list = diaDAO.getMyFood(dno);
		return list;
	}

	// 섭취 칼로리 추가할 때 음식 검색
	public List<CaldictionaryDTO> searchFood(String searchWord) {
		List<CaldictionaryDTO> list = diaDAO.searchFood(searchWord);
		return list;
	}
	
	// 섭취 칼로리 추가할 때 마이칼로리 전체목록(검색된 내용 없을때)
	public List<CaldictionaryDTO> AllMyFood(String mid) {
		List<CaldictionaryDTO> list = diaDAO.AllMyFood(mid);
		return list;
	}

	// 내 음식 칼로리 추가(리스트로 온 경우..)
	public void insertMyFood(int dno, String cdtype, int[] cdnos) {
		DiaryDTO dto = diaryInfo(dno);
		int caltotal = dto.getDfoodcal();//총음식칼로리
		
		for (int i=0;i<cdnos.length;i++) {
			CaldictionaryDTO cdto = diaDAO.getCdInfo(cdnos[i]);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("dno", dno);
			map.put("cdtype", cdtype);
			map.put("cdno", cdnos[i]);
			map.put("mftotalcal", cdto.getCdcal());
			map.put("cdname", cdto.getCdname());
			map.put("cdamount", cdto.getCdamount());
			map.put("cdgram", cdto.getCdgram());
			
			diaDAO.insertMyFood(map);
			caltotal += cdto.getCdcal();
		}
		
		diaDAO.updateDiaryFoodcal(dno,caltotal); // 다이어리 총섭취칼로리 수정
	}
	
	// 내 음식 칼로리 추가(상세보기에서..)
	public void insertMyFood2(MyFoodDTO mfdto, String cdtype, int cdno) {
		DiaryDTO dto = diaryInfo(mfdto.getDno());
		int caltotal = dto.getDfoodcal();//총음식칼로리
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", mfdto.getDno());
		map.put("cdtype", cdtype);
		map.put("cdno", cdno);
		map.put("mftotalcal", mfdto.getMftotalcal());
		map.put("cdname", mfdto.getMfname());
		map.put("cdamount", mfdto.getMfamount());
		map.put("cdgram", mfdto.getMfgram());
		
		diaDAO.insertMyFood(map);
		
		// 다이어리 총섭취칼로리 수정
		caltotal += mfdto.getMftotalcal();
		diaDAO.updateDiaryFoodcal(mfdto.getDno(),caltotal);
	}

	// 내 음식 칼로리 삭제
	public void myFoodDelete(int dno, int mfno) {
		DiaryDTO dto = diaryInfo(dno);
		int caltotal = dto.getDfoodcal();//총음식칼로리
		
		// 다이어리 총섭취칼로리 수정
		caltotal -= diaDAO.getMfTototal(mfno);
		diaDAO.updateDiaryFoodcal(dno,caltotal); 
		
		// 삭제
		diaDAO.myFoodDelete(mfno);
	}

	// 다이어리 저장
	public void saveDiary(DiaryDTO dto) {
		//기존에 작성된 다이어리가 없으면 insert
		if(dto.getDno()==0) {
			//diaDAO.insertDiary();
		}else { //기존에 작성된 다이어리가 있으면 update
			
		}
	}

	// 마이칼로리 등록(음식)
	public void insertMyCal(String mid, int mno, CaldictionaryDTO dto) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("cdcal", dto.getCdcal());
		map.put("cdname", dto.getCdname());
		map.put("mid", mid);
		map.put("mno", mno);
		map.put("cdtan", dto.getCdtan());
		map.put("cddan", dto.getCddan());
		map.put("cdji", dto.getCdji());
		map.put("cdsik", dto.getCdsik());
		map.put("cdna", dto.getCdna());
		map.put("cdgram", dto.getCdgram());
		map.put("cdamount", dto.getCdamount());
		
		diaDAO.insertMyCal(map);
	}

	// 마이칼로리 삭제
	public void deleteMyCal(int cdno) {
		diaDAO.deleteMyCal(cdno);
	}
	
	// 다이어리 등록(아이디로만)하고 만든 그 데이터 가져오기
	public DiaryDTO insertDiary(String mid, Date date) {
		diaDAO.insertDiary(mid, date);
		DiaryDTO dto = diaDAO.selectNewDiary();
		return dto;
	}

	// 몸무게 업데이트
	public void updateDweight(int num, Double dweight) {
		diaDAO.updateDweight(num,dweight);
	}

	// 일기 업데이트
	public void updateDdiary(int num, String ddiary) {
		diaDAO.updateDdiary(num,ddiary);
	}

	// 다이어리 번호로 myexercise 리스트 가져오기
	public List<MyExerciseDTO> getMyExer(int dno) {
		System.out.println("SVC-getMyExer()-dno:"+dno);
		List<MyExerciseDTO> list = diaDAO.getMyExer(dno);
		return list;
	}
	
	// 소비 칼로리 추가할 때 사전에서 운동 검색
	public List<CaldictionaryDTO> searchExer(String searchWord) {
		List<CaldictionaryDTO> list = diaDAO.searchExer(searchWord);
		return list;
	}
	
	// 소비 칼로리 추가할 때 마이칼로리 전체목록(검색된 내용 없을때)
	public List<CaldictionaryDTO> AllMyExer(String mid) {
		List<CaldictionaryDTO> list = diaDAO.AllMyExer(mid);
		return list;
	}
	
	// 마이칼로리 등록(운동)
	public void insertMyCal2(String mid, int mno, CaldictionaryDTO dto) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("cdcal", dto.getCdcal());
		map.put("cdname", dto.getCdname());
		map.put("mid", mid);
		map.put("mno", mno);
		map.put("cdgram", dto.getCdgram());
		
		diaDAO.insertMyCal2(map);
	}
	
	// 내 운동 칼로리 추가(리스트로 온 경우..)
	public void insertMyExer(int dno, int[] cdnos) {
		DiaryDTO dto = diaryInfo(dno);
		int caltotal = dto.getDexercal();//총운동칼로리
		
		for (int i=0;i<cdnos.length;i++) {
			CaldictionaryDTO cdto = diaDAO.getCdInfo(cdnos[i]);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("dno", dno);
			map.put("cdno", cdnos[i]);
			map.put("metotalcal", cdto.getCdcal());
			map.put("mename", cdto.getCdname());
			map.put("metime", cdto.getCdgram());
			
			
			diaDAO.insertMyExer(map);
			caltotal += cdto.getCdcal();
		}
		
		diaDAO.updateDiaryExercal(dno,caltotal); // 다이어리 총섭취칼로리 수정
	}
	
	// 내 음식 칼로리 추가(상세보기에서..)
	public void insertMyExer2(MyExerciseDTO medto, int cdno) {
		DiaryDTO dto = diaryInfo(medto.getDno());
		int caltotal = dto.getDexercal();//총운동칼로리
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", medto.getDno());
		map.put("cdno", cdno);
		map.put("metotalcal", medto.getMetotalcal());
		map.put("mename", medto.getMename());
		map.put("metime", medto.getMetime());
		
		diaDAO.insertMyExer(map);
		
		// 다이어리 총섭취칼로리 수정
		caltotal += medto.getMetotalcal();
		diaDAO.updateDiaryExercal(medto.getDno(),caltotal);
	}
	
	// 내 음식 칼로리 삭제
	public void myExerDelete(int dno, int mfno) {
		DiaryDTO dto = diaryInfo(dno);
		int caltotal = dto.getDexercal();//총음식칼로리
		
		// 다이어리 총섭취칼로리 수정
		caltotal -= diaDAO.getMeTototal(mfno);
		diaDAO.updateDiaryExercal(dno,caltotal);
		
		// 삭제
		diaDAO.myExerDelete(mfno);
	}

	// 내 운동 칼로리 모두 삭제
	public void myExerDeleteAll(int dno) {
		diaDAO.myExerDeleteAll(dno);
		diaDAO.updateDiaryExercal(dno,0);
	}
	
	// 내 음식 칼로리 모두 삭제
	public void myFoodDeleteAll(int dno) {
		diaDAO.myFoodDeleteAll(dno);
		diaDAO.updateDiaryFoodcal(dno,0);
	}

	// 다이어리 삭제
	public void myDiaryDelete(int dno) {
		diaDAO.myExerDeleteAll(dno);
		diaDAO.myFoodDeleteAll(dno);
		diaDAO.myDiaryDelete(dno);
	}

	// 다이어리 이미지 업로드
	public void updateDimage(int dno, String dimage, String doriimage) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("dno", dno);
		map.put("dimage", dimage);
		map.put("doriimage", doriimage);
		
		diaDAO.updateDimage(map);
	}

	// 해당 회원의 다이어리인지 확인
	public int IsMyDno(int dno, String mid) {
		return diaDAO.IsMyDno(dno,mid);
	}

	// 다이어리 이미지 삭제
	public void myImgDelete(int dno) {
		diaDAO.myImgDelete(dno);
	}

	// 그래프
	public ArrayList<DiaryDTO> getchart(DiaryDTO ddto) {
		ArrayList<DiaryDTO> list = diaDAO.getchart(ddto);
		return list;
	}
	
	// 성공률 계산
	public HashMap getrate(HttpSession session, DiaryDTO ddto) {
		ArrayList<DiaryDTO> list = diaDAO.getchart(ddto);
		Calendar cal = Calendar.getInstance();
		//성공
		List<DiaryDTO> rate=diaDAO.getrate(ddto);
		for(int i=0;i<rate.size();i++) {
			Double mon=(double) Integer.parseInt(rate.get(i).getMonth());
			Double succ=(double) rate.get(i).getDsucc();
			int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			Double dsucc=(Math.round((succ/endDay)*100)/100.0)*100;
			rate.get(i).setRate(dsucc);
		}
		
		HashMap map=new HashMap();
		for(int i=0;i<12;i++) {
			map.put(i+1, 0.0);
			for(int j=0;j<rate.size();j++) {
				if(Integer.parseInt(rate.get(j).getMonth())==i+1) {
					map.put(i+1, rate.get(j).getRate());
				}
			}
		}
		return map;
	}

	//calrecipe
	public CalrecipeDTO calrecipe(DiaryDTO diary) {
		return diaDAO.calrecipe(diary);
	}

	public void calculation(DiaryDTO diary) {
		Double cal=diary.getCrcal();
		int food=diary.getDfoodcal();
		int exer=diary.getDexercal();
		if(food-exer<=cal) {
			diary.setDsucc(1);
			diaDAO.success(diary);
		}else {
			diary.setDsucc(0);
			diaDAO.success(diary);
		}
	}
}




