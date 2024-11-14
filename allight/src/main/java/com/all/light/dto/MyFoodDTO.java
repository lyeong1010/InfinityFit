package com.all.light.dto;

public class MyFoodDTO {
	
	private int mfno;		//음식번호
	private int mfamount;	//수량
	private int mftotalcal;	//총음식칼로리
	private String mftype;	//구분
	private String mfname;	//음식이름
	private int mfgram;		//분량(g)
	
	private int dno;		//다이어리번호
	private int cdno;		//칼로리번호
	
	
	public int getMfno() {
		return mfno;
	}
	public void setMfno(int mfno) {
		this.mfno = mfno;
	}
	public int getMfamount() {
		return mfamount;
	}
	public void setMfamount(int mfamount) {
		this.mfamount = mfamount;
	}
	public int getMftotalcal() {
		return mftotalcal;
	}
	public void setMftotalcal(int mftotalcal) {
		this.mftotalcal = mftotalcal;
	}
	public String getMftype() {
		return mftype;
	}
	public void setMftype(String mftype) {
		this.mftype = mftype;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getMfname() {
		return mfname;
	}
	public void setMfname(String mfname) {
		this.mfname = mfname;
	}
	public void setCdno(int cdno) {
		this.cdno = cdno;
	}
	public int getCdno() {
		return cdno;
	}
	public int getMfgram() {
		return mfgram;
	}
	public void setMfgram(int mfgram) {
		this.mfgram = mfgram;
	}
	
	
	@Override
	public String toString() {
		return "MyFoodDTO [mfno=" + mfno + ", mfamount=" + mfamount + ", mftotalcal=" + mftotalcal + ", mftype="
				+ mftype + ", mfname=" + mfname + ", mfgram=" + mfgram + ", dno=" + dno + ", cdno=" + cdno + "]";
	}
	
}
