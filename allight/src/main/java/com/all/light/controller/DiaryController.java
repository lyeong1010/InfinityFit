package com.all.light.controller;


import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.CaldictionaryDTO;
import com.all.light.dto.CalrecipeDTO;
import com.all.light.dto.DiaryDTO;
import com.all.light.dto.MyExerciseDTO;
import com.all.light.dto.MyFoodDTO;
import com.all.light.service.DiaryService;
import com.all.light.util.FileUtil;

@Controller
public class DiaryController {
	@Autowired
	private DiaryService diaSVC;
	
	//메인화면
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, ModelAndView mv, DiaryDTO ddto) {
		
		Calendar cal = Calendar.getInstance();
		DiaryDTO calendarData;
		
		//검색 날짜
		if(ddto.getDay().equals("")&&ddto.getMonth().equals("")){
			ddto = new DiaryDTO(String.valueOf(cal.get(Calendar.YEAR)),String.valueOf(cal.get(Calendar.MONTH)),String.valueOf(cal.get(Calendar.DATE)),null);
		}

		Map<String, Integer> today_info =  ddto.today_info(ddto);
		List<DiaryDTO> dateList = new ArrayList<DiaryDTO>();
		
		//실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		//일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		for(int i=1; i<today_info.get("start"); i++){
			calendarData= new DiaryDTO(null, null, null, null);
			dateList.add(calendarData);
		}
		
		//날짜 삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			if(i==today_info.get("today")){
				calendarData= new DiaryDTO(String.valueOf(ddto.getYear()), String.valueOf(ddto.getMonth()), String.valueOf(i), "today");
			}else{
				calendarData= new DiaryDTO(String.valueOf(ddto.getYear()), String.valueOf(ddto.getMonth()), String.valueOf(i), "normal_date");
			}
			dateList.add(calendarData);
		}

		//달력 빈곳 빈 데이터로 삽입
		int index = 7-dateList.size()%7;
		
		if(dateList.size()%7!=0){
			for (int i = 0; i < index; i++) {
				calendarData= new DiaryDTO(null, null, null, null);
				dateList.add(calendarData);
			}
		}
		System.out.println(dateList);
		
		// 로그인 했으면 메인에 띄울 정보 가져오기
		String mid = (String)request.getSession().getAttribute("MID");
		if(mid!=null && !mid.equals("")) {
			dateList = diaSVC.main(dateList,mid,today_info.get("search_year"),today_info.get("search_month"));
		}
		
		mv.addObject("dateList", dateList);		//날짜 데이터 배열
		mv.addObject("today_info", today_info);
		mv.setViewName("/index");
		return mv;
	}
	
	
	// 해당 날짜의 다이어리/섭취/소비칼로리 정보 가져오기
	@RequestMapping("/myFAE")
	public ModelAndView myFoodAndExer(
			HttpServletRequest request,
			ModelAndView mv, 
			int num, 
			DiaryDTO dto) {
		String mid = (String)request.getSession().getAttribute("MID");
		if(mid==null) { //일반회원/관리자가 아니면 main으로 보내기
			System.out.println("myFAE()-mid 널");
			RedirectView rv = new RedirectView("./main.com");
			mv.setView(rv);
			return mv;
		}else { //해당 회원의 다이어리인지 확인
			int ok = diaSVC.IsMyDno(num,mid);
			if(ok==0 && num!=0) {
				System.out.println("myFAE()-이 회원의 다이어리가 아님!!!");
				RedirectView rv = new RedirectView("./main.com");
				mv.setView(rv);
				return mv;
			}
		}
		
		System.out.println("myFoodAndExer()-dno:"+num);
		System.out.println("dto:"+dto.toString());
		
		DiaryDTO diary = null;
		List<MyFoodDTO> fList = null;
		List<MyExerciseDTO> eList = null;
		Date date = null;
		//기존 데이터가 없는데 myFAE에 처음 왔을때
		if(num==0 && dto.getYear()!=null && dto.getYear()!="") { 
			String y = dto.getYear();
			String m = dto.getMonth();
			String d = dto.getDay();
			if(m.length()==1) {
				m = "0"+m;
			}
			if(d.length()==1) {
				d = "0"+d;
			}
			String dateStr = y+"-"+m+"-"+d;
			System.out.println(dateStr);
			try{
				date = Date.valueOf(dateStr);
			}catch(Exception e) {
				System.out.println("myFAE()-잘못된 접근!!!");
				RedirectView rv = new RedirectView("./main.com");
				mv.setView(rv);
				return mv;
			}
		}else {
			diary = diaSVC.diaryInfo(num);
			fList = diaSVC.getMyFood(num);
			eList = diaSVC.getMyExer(num);
			if( diary.getCrno()==0 || diary.getCrcal()==null) {
				System.out.println("처음이야");
				CalrecipeDTO cdto=diaSVC.calrecipe(diary);
				if(cdto!=null)
					diaSVC.calculation(diary);
			}else {
				System.out.println("이미했어");
				diaSVC.calculation(diary);
			}
		}
		
		mv.addObject("num",num);	 //num(dno)-다이어리 번호
		mv.addObject("DATE",date);   //해당하는 날짜
		mv.addObject("DIARY",diary); //다이어리 정보
		mv.addObject("FLIST",fList); //섭취한 칼로리 정보
		mv.addObject("ELIST",eList); //소비한 칼로리 정보
		
		mv.setViewName("/diary/user/diary/myFAE");
		return mv;
		
	}
	
	// 음식 칼로리 추가 폼으로 보내주기
	@RequestMapping("/insertMyFoodFrm")
	public ModelAndView insertMyFoodFrm(
			HttpServletRequest request,
			ModelAndView mv, 
			int num,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			@RequestParam(value="searchWord", 
			  			  required=false,
			  			  defaultValue="") String searchWord ) {
		System.out.println("insertMyFoodFrm()-num:"+num+", ddate:"+ddate);
		
		String mid = (String)request.getSession().getAttribute("MID");

		if(mid==null) {
			RedirectView rv = new RedirectView("./main.com");
			mv.setView(rv);
			return mv;
		}
		
		List<CaldictionaryDTO> list = null;
		List<CaldictionaryDTO> myList = null;
		DiaryDTO dto = new DiaryDTO();
		dto.setDno(num);
		dto.setDdate(ddate);
		
		if(!searchWord.equals(null) && searchWord!=null && !searchWord.equals("")) {
			list = diaSVC.searchFood(searchWord);
		}
		myList = diaSVC.AllMyFood(mid);
		
		mv.addObject("DTO",dto);  			//다이어리 번호
		mv.addObject("WORD",searchWord); 	//검색한 단어
		mv.addObject("LIST",list);       	//칼로리 사전 검색내용
		mv.addObject("MYLIST",myList);   	//마이칼로리 내용
		
		mv.setViewName("/diary/user/diary/insertFoodFrm");
		return mv;
		
	}
	
	// 음식 칼로리 추가
	@RequestMapping("/insertMyFood")
	public ModelAndView insertMyFood(
			ModelAndView mv, 
			HttpServletRequest request,
			int dno,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			String cdtype,
			int[] cdnos,
			@RequestParam(value="sort", 
			  			  defaultValue="0") int sort) {
		System.out.println("insertMyFood()-dno:"+dno);
		
		if(dno==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			dno = dto.getDno();
		}
		diaSVC.insertMyFood(dno,cdtype,cdnos);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+Integer.toString(dno));
		mv.setView(rv);
		return mv;
	}
	
	// 음식 칼로리 추가2(상세보기에서)
	@RequestMapping("/insertMyFood2")
	public ModelAndView insertMyFood2(
			ModelAndView mv,
			HttpServletRequest request,
			MyFoodDTO mfdto,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			String cdtype,
			int cdno) {
		System.out.println("insertFood2()-dno:"+mfdto.getDno());
		
		if(mfdto.getDno()==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			mfdto.setDno(dto.getDno());
		}
		
		diaSVC.insertMyFood2(mfdto,cdtype,cdno);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+mfdto.getDno());
		mv.setView(rv);
		return mv;
		
	}
	
	// 내 음식 칼로리 삭제
	@RequestMapping("/myFoodDelete")
	public ModelAndView myFoodDelete(ModelAndView mv,DiaryDTO dto,int mfno) {
		System.out.println("myFoodDelete()-dto="+dto.toString()+", mfno="+mfno);
		
		diaSVC.myFoodDelete(dto.getDno(), mfno);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+dto.getDno());
		mv.setView(rv);
		return mv;
	}
	
	// 마이칼로리 등록(음식)
	@RequestMapping("/insertMyCal")
	@ResponseBody
	public void insertMyCal(
			ModelAndView mv, 
			HttpServletRequest request,
			CaldictionaryDTO dto, 
			int dno) {
		System.out.println("insertMyCal()dto="+dto.toString());
		
		String mid = (String)request.getSession().getAttribute("MID");
		String mnoStr = String.valueOf(request.getSession().getAttribute("MNO"));
		int mno = Integer.parseInt(mnoStr);
		
		diaSVC.insertMyCal(mid, mno, dto);
		
	}
	
	// 마이칼로리 삭제
	@RequestMapping("/deleteMyCal")
	@ResponseBody
	public void deleteMyCal(
			ModelAndView mv, 
			HttpServletRequest request,
			int cdno) {
		
		diaSVC.deleteMyCal(cdno);
		
	}
	
	// 다이어리 저장
	@RequestMapping("/saveDiary")
	public ModelAndView saveDiary(ModelAndView mv,DiaryDTO dto) {
		System.out.println("saveDiary()dto="+dto.toString());
		
		diaSVC.saveDiary(dto);
		
		mv.setViewName("/index");
		return mv;
	}
	
	// 몸무게 업데이트
	@RequestMapping("/updateDweight")
	@ResponseBody
	public String updateDweight(
			HttpServletRequest request,
			ModelAndView mv, 
			int num,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			Double dweight) {
		System.out.println("updateDweight()-num:"+num+", ddate:"+ddate+",dweight:"+dweight);
		
		if(num==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			num = dto.getDno();
		}
		
		diaSVC.updateDweight(num, dweight);
		
		return Integer.toString(num);
	}
	
	// 일기 업데이트
	@RequestMapping("/updateDdiary")
	@ResponseBody
	public String updateDdiary(
			HttpServletRequest request,
			ModelAndView mv, 
			int num,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			String ddiary) {
		System.out.println("updateDdiary()-num:"+num+", ddate:"+ddate+",dweight:"+ddiary);
		
		if(num==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			num = dto.getDno();
		}
		
		diaSVC.updateDdiary(num, ddiary);
		
		return Integer.toString(num);
	}
	
	// 운동 칼로리 추가 폼으로 보내주기
	@RequestMapping("/insertMyExerFrm")
	public ModelAndView insertMyExerFrm(
			HttpServletRequest request,
			ModelAndView mv, 
			int num,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			@RequestParam(value="searchWord", 
			  			  required=false,
			  			  defaultValue="") String searchWord ) {
		String mid = (String)request.getSession().getAttribute("MID");

		if(mid==null) {
			RedirectView rv = new RedirectView("./main.com");
			mv.setView(rv);
			return mv;
		}
		
		System.out.println("insertMyExerFrm()-num:"+num+", ddate:"+ddate);
		
		List<CaldictionaryDTO> list = null;
		List<CaldictionaryDTO> myList = null;
		DiaryDTO dto = new DiaryDTO();
		dto.setDno(num);
		dto.setDdate(ddate);
		
		if(!searchWord.equals(null) && searchWord!=null && !searchWord.equals("")) {
			list = diaSVC.searchExer(searchWord);
		}
		myList = diaSVC.AllMyExer(mid);
		
		mv.addObject("DTO",dto);  			//다이어리 번호
		mv.addObject("WORD",searchWord); 	//검색한 단어
		mv.addObject("LIST",list);       	//칼로리 사전 검색내용
		mv.addObject("MYLIST",myList);   	//마이칼로리 내용
		
		mv.setViewName("/diary/user/diary/insertExerFrm");
		return mv;
	}

	// 마이칼로리 등록(운동)
	@RequestMapping("/insertMyCal2")
	@ResponseBody
	public void insertMyCal2(
			ModelAndView mv, 
			HttpServletRequest request,
			CaldictionaryDTO dto, 
			int dno) {
		System.out.println("insertMyCal2()dto="+dto.toString());
		
		String mid = (String)request.getSession().getAttribute("MID");
		String mnoStr = String.valueOf(request.getSession().getAttribute("MNO"));
		int mno = Integer.parseInt(mnoStr);
		
		diaSVC.insertMyCal2(mid, mno, dto);
		
	}
	
	// 운동 칼로리 추가
	@RequestMapping("/insertMyExer")
	public ModelAndView insertMyExer(
			ModelAndView mv, 
			HttpServletRequest request,
			int dno,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			int[] cdnos,
			@RequestParam(value="sort", 
			  			  defaultValue="0") int sort) {
		System.out.println("insertMyExer()-dno:"+dno);
		
		if(dno==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			dno = dto.getDno();
		}
		diaSVC.insertMyExer(dno,cdnos);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+Integer.toString(dno));
		mv.setView(rv);
		return mv;
	}
	
	// 운동 칼로리 추가2(상세보기에서)
	@RequestMapping("/insertMyExer2")
	public ModelAndView insertMyExer2(
			ModelAndView mv,
			HttpServletRequest request,
			MyExerciseDTO medto,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			int cdno) {
		System.out.println("insertExer2()-dno:"+medto.getDno());
		
		if(medto.getDno()==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			medto.setDno(dto.getDno());
		}
		
		diaSVC.insertMyExer2(medto,cdno);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+medto.getDno());
		mv.setView(rv);
		return mv;
		
	}
	
	// 내 음식 칼로리 삭제
	@RequestMapping("/myExerDelete")
	public ModelAndView myExerDelete(ModelAndView mv,DiaryDTO dto,int meno) {
		System.out.println("myFoodDelete()-dto="+dto.toString()+", mfno="+meno);
		
		diaSVC.myExerDelete(dto.getDno(), meno);
		
		RedirectView rv = new RedirectView("./myFAE.com?num="+dto.getDno());
		mv.setView(rv);
		return mv;
	}
	
	// 내 운동 칼로리 모두 삭제
	@RequestMapping("/myExerDeleteAll")
	@ResponseBody
	public void myExerDeleteAll(int dno) {
		System.out.println("myExerDeleteAll()-dno="+dno);
		diaSVC.myExerDeleteAll(dno);
	}
	
	// 내 음식 칼로리 모두 삭제
	@RequestMapping("/myFoodDeleteAll")
	@ResponseBody
	public void myFoodDeleteAll(int dno) {
		System.out.println("myFoodDeleteAll()-dno="+dno);
		diaSVC.myFoodDeleteAll(dno);
	}
	
	// 다이어리 삭제
	@RequestMapping("/myDiaryDelete")
	public ModelAndView myDiaryDelete(ModelAndView mv,int dno) {
		System.out.println("myFoodDeleteAll()-dno="+dno);
		
		diaSVC.myDiaryDelete(dno);
		
		RedirectView rv = new RedirectView("./main.com");
		mv.setView(rv);
		return mv;
	}
	
	// 다이어리 이미지 업로드
	@RequestMapping("/updateDimage")
	@ResponseBody
	public String updateDimage(
			HttpServletRequest request,
			@RequestParam(value="ddate", 
			  			  required = false) Date ddate,
			int num, 
			MultipartFile dimageFile) {
		System.out.println("updateDimage()-dno="+num+",ddate:"+ddate);
		
		if(num==0) {
			String mid = (String)request.getSession().getAttribute("MID");
			DiaryDTO dto = diaSVC.insertDiary(mid,ddate);
			num = dto.getDno();
		}
		
		// 파일 저장
		String savedimage = FileUtil.renameTo("D:\\upload", dimageFile.getOriginalFilename());
		File file = new File("D:\\upload", savedimage);
		try {
			dimageFile.transferTo(file);
		}catch (Exception e) {
			System.out.println(e);
		}
		
		String dimage ="/item/img/"+savedimage;
		
		diaSVC.updateDimage(num,dimage,dimageFile.getOriginalFilename());
		
		return Integer.toString(num);
	}
		
	// 다이어리 이미지 삭제
	@RequestMapping("/myImgDelete")
	@ResponseBody
	public void myImgDelete(int dno) {
		System.out.println("myImgDelete()-dno="+dno);
		diaSVC.myImgDelete(dno);
	}
		
	// 그래프
	@RequestMapping("/chart")
	public ModelAndView weightchart(@RequestParam(value="yy", required = false) String yy,
			@RequestParam(value="mon", required = false) String mon,DiaryDTO ddto,ModelAndView mv, HttpSession session){
		ddto.setYear(yy);
		ddto.setMonth(mon);
		ddto.setMid((String)session.getAttribute("MID"));
		ArrayList<DiaryDTO> list = diaSVC.getchart(ddto);
		HashMap rate = diaSVC.getrate(session,ddto);
		System.out.println(rate);
		mv.addObject("RATE", rate);
		mv.addObject("LIST", list);		// 실제 조회 목록
		mv.setViewName("/diary/user/graphy/chart");
		return mv;
	}
	
}









