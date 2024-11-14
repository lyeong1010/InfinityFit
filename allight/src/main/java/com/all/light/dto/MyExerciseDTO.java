package com.all.light.dto;

public class MyExerciseDTO {

	private int meno;		//운동번호
	private int metime;	//운동시간
	private int metotalcal;	//총운동칼로리
	private int dno;		//다이어리 번호
	private int cdno;		//칼로리사전번호
	private String mename;	//운동명
	
	public int getMeno() {
		return meno;
	}
	public void setMeno(int meno) {
		this.meno = meno;
	}
	public int getMetime() {
		return metime;
	}
	public void setMetime(int metime) {
		this.metime = metime;
	}
	public int getMetotalcal() {
		return metotalcal;
	}
	public void setMetotalcal(int metotalcal) {
		this.metotalcal = metotalcal;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public int getCdno() {
		return cdno;
	}
	public void setCdno(int cdno) {
		this.cdno = cdno;
	}
	public String getMename() {
		return mename;
	}
	public void setMename(String mename) {
		this.mename = mename;
	}
	
	
	@Override
	public String toString() {
		return "MyExcerciseDTO [meno=" + meno + ", metime=" + metime + ", metotalcal=" + metotalcal + ", dno=" + dno
				+ ", cdno=" + cdno + ", mename=" + mename + "]";
	}
	
}
