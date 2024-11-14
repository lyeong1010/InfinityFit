package com.all.light.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import java.sql.Date;

public class DiaryDTO {
	
	private int dno;			//다이어리 번호
	private String mid;			//아이디
	private int dexercal=0;		//총운동칼로리
	private int dfoodcal=0;		//총음식칼로리
	private String dimage;		//사진
	private String doriimage;	//저장된 사진
	private Double dweight=0.0;	//체중
	private String ddiary;		//일기
	private Date ddate;			//날짜
	private Double rate=0.0;
	private int crno;
	private Double crcal;
	private int dsucc;
	private int mm;

	//달력 표시 위해..
	private String year="";
	private String month="";
	private String day="";
	private String value="";
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getDexercal() {
		return dexercal;
	}
	public void setDexercal(int dexercal) {
		this.dexercal = dexercal;
	}
	public Date getDdate() {
		return ddate;
	}
	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}
	public int getDfoodcal() {
		return dfoodcal;
	}
	public void setDfoodcal(int dfoodcal) {
		this.dfoodcal = dfoodcal;
	}
	public String getDimage() {
		return dimage;
	}
	public void setDimage(String dimage) {
		this.dimage = dimage;
	}
	public Double getDweight() {
		return dweight;
	}
	public void setDweight(Double dweight) {
		this.dweight = dweight;
	}
	public String getDdiary() {
		return ddiary;
	}
	public void setDdiary(String ddiary) {
		this.ddiary = ddiary;
	}
	
	
	
	// 날짜에 관련된 달력정보를 가지는 메서드
	public Map<String, Integer> today_info(DiaryDTO diaDTO) {
		// 날짜 캘린더 함수에 삽입.
		Map<String, Integer> today_Data = new HashMap<String, Integer>();
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(diaDTO.getYear()), Integer.parseInt(diaDTO.getMonth()), 1);

		int startDay = cal.getMinimum(java.util.Calendar.DATE);
		int endDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		
		Calendar todayCal = Calendar.getInstance();
		SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat msdf = new SimpleDateFormat("M");

		int today_year = Integer.parseInt(ysdf.format(todayCal.getTime()));
		int today_month = Integer.parseInt(msdf.format(todayCal.getTime()));

		int search_year = Integer.parseInt(diaDTO.getYear());
		int search_month = Integer.parseInt(diaDTO.getMonth()) + 1;

		int today = -1;
		if (today_year == search_year && today_month == search_month) {
			SimpleDateFormat dsdf = new SimpleDateFormat("dd");
			today = Integer.parseInt(dsdf.format(todayCal.getTime()));
		}
		
		search_month = search_month-1; 
		
		Map<String, Integer> before_after_calendar = before_after_calendar(search_year,search_month);
		
		//날짜 관련
		System.out.println("search_month : " + search_month);
		// 캘린더 함수 end
		today_Data.put("start", start);
		today_Data.put("startDay", startDay);
		today_Data.put("endDay", endDay);
		today_Data.put("today", today);
		today_Data.put("search_year", search_year);
		today_Data.put("search_month", search_month+1);
		today_Data.put("before_year", before_after_calendar.get("before_year"));
		today_Data.put("before_month", before_after_calendar.get("before_month"));
		today_Data.put("after_year", before_after_calendar.get("after_year"));
		today_Data.put("after_month", before_after_calendar.get("after_month"));
		return today_Data;
	}
	
	//이전달 다음달 및 이전년도 다음년도
	private Map<String, Integer> before_after_calendar(int search_year, int search_month){
		Map<String, Integer> before_after_data = new HashMap<String, Integer>();
		int before_year = search_year;
		int before_month = search_month-1;
		int after_year = search_year;
		int after_month = search_month+1;

		if(before_month<0){
			before_month=11;
			before_year=search_year-1;
		}
		
		if(after_month>11){
			after_month=0;
			after_year=search_year+1;
		}
		
		before_after_data.put("before_year", before_year);
		before_after_data.put("before_month", before_month);
		before_after_data.put("after_year", after_year);
		before_after_data.put("after_month", after_month);
		
		return before_after_data;
	}


	// 달력만 사용시 사용될 생성자
	public DiaryDTO(String year, String month, String day, String value) {
		if ((month != null && month != "") && (day != null && day != "")) {
			this.year = year;
			this.month = month;
			this.day = day;
			this.value = value;
		}
	}
	
	public DiaryDTO() {}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public int getCrno() {
		return crno;
	}
	public void setCrno(int crno) {
		this.crno = crno;
	}
	public Double getCrcal() {
		return crcal;
	}
	public void setCrcal(Double crcal) {
		this.crcal = crcal;
	}
	@Override
	public String toString() {
		return "DiaryDTO [dno=" + dno + ", mid=" + mid + ", dexercal=" + dexercal + ", dfoodcal=" + dfoodcal
				+ ", dimage=" + dimage + ", doriimage=" + doriimage + ", dweight=" + dweight + ", ddiary=" + ddiary
				+ ", ddate=" + ddate + ", rate=" + rate + ", crno=" + crno + ", crcal=" + crcal + ", year=" + year
				+ ", month=" + month + ", day=" + day + ", value=" + value + "]";
	}
	public int getDsucc() {
		return dsucc;
	}
	public void setDsucc(int dsucc) {
		this.dsucc = dsucc;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public String getDoriimage() {
		return doriimage;
	}
	public void setDoriimage(String doriimage) {
		this.doriimage = doriimage;
	}
	
	
}
