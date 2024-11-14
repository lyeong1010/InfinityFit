package com.all.light.dto;

public class CaldictionaryDTO {
	private int cdno;			//칼로리번호
	private String cdtype;		//구분(음식/운동)
	private int cdcal=0;		//칼로리
	private String cdname;		//음식/운동 이름
	private String mid;			//아이디
	private int mno;			//회원번호
	private Double cdtan=0.0;	//탄수화물(g)
	private Double cddan=0.0;	//단백질(g)
	private Double cdji=0.0;	//지방(g)
	private Double cdsik=0.0;	//식이섬유(g)
	private Double cdna=0.0;	//나트륨(mg)
	private int cdgram=0;			//그램(g)
	private int cdamount=0;		//수량(기본1 DB에서 설정)
	
	
	public int getCdno() {
		return cdno;
	}
	public void setCdno(int cdno) {
		this.cdno = cdno;
	}
	public String getCdtype() {
		return cdtype;
	}
	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
	}
	public int getCdcal() {
		return cdcal;
	}
	public void setCdcal(int cdcal) {
		this.cdcal = cdcal;
	}
	public String getCdname() {
		return cdname;
	}
	public void setCdname(String cdname) {
		this.cdname = cdname;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public Double getCdtan() {
		return cdtan;
	}
	public void setCdtan(Double cdtan) {
		this.cdtan = cdtan;
	}
	public Double getCddan() {
		return cddan;
	}
	public void setCddan(Double cddan) {
		this.cddan = cddan;
	}
	public Double getCdji() {
		return cdji;
	}
	public void setCdji(Double cdji) {
		this.cdji = cdji;
	}
	public Double getCdsik() {
		return cdsik;
	}
	public void setCdsik(Double cdsik) {
		this.cdsik = cdsik;
	}
	public Double getCdna() {
		return cdna;
	}
	public void setCdna(Double cdna) {
		this.cdna = cdna;
	}
	public int getCdgram() {
		return cdgram;
	}
	public void setCdgram(int cdgram) {
		this.cdgram = cdgram;
	}
	public int getCdamount() {
		return cdamount;
	}
	public void setCdamount(int cdamount) {
		this.cdamount = cdamount;
	}
	
	
	@Override
	public String toString() {
		return "CalDictionaryDTO [cdno=" + cdno + ", cdtype=" + cdtype + ", cdcal=" + cdcal + ", cdname=" + cdname
				+ ", mid=" + mid + ", mno=" + mno + ", cdtan=" + cdtan + ", cddan=" + cddan + ", cdji=" + cdji
				+ ", cdsik=" + cdsik + ", cdna=" + cdna + ", cdgram=" + cdgram + ", cdamount=" + cdamount + "]";
	}
	
}
