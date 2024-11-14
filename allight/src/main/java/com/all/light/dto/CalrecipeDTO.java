package com.all.light.dto;

import java.sql.Date;

public class CalrecipeDTO {
	private String sex;
	private Date birth;
	private int age;
	private String type;
	
	private int crno;
	private int crheight;//키
	private int crweight;//현재체중
	private double crcal;//하루 칼로리
	private double crbmi;//bmi
	private String mid;
	private double crbmr;//기초대사량
	private String crterm;//총감량기간
	private int cractive;//활동량
	private int crgoalweight;//목표체중
	private Date crdate;
	

	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Date getBirth() {
		return birth;
	}


	public void setBirth(Date birth) {
		this.birth = birth;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getCrno() {
		return crno;
	}


	public void setCrno(int crno) {
		this.crno = crno;
	}


	public double getCrbmr() {
		return crbmr;
	}


	public void setCrbmr(double crbmr) {
		this.crbmr = crbmr;
	}


	public double getCrcal() {
		return crcal;
	}


	public void setCrcal(double crcal) {
		this.crcal = crcal;
	}


	public double getCrbmi() {
		return crbmi;
	}


	public void setCrbmi(double crbmi) {
		this.crbmi = crbmi;
	}


	public String getCrterm() {
		return crterm;
	}


	public void setCrterm(String crterm) {
		this.crterm = crterm;
	}


	public int getCractive() {
		return cractive;
	}


	public void setCractive(int cractive) {
		this.cractive = cractive;
	}


	public int getCrheight() {
		return crheight;
	}


	public void setCrheight(int crheight) {
		this.crheight = crheight;
	}


	public int getCrweight() {
		return crweight;
	}


	public void setCrweight(int crweight) {
		this.crweight = crweight;
	}


	public int getCrgoalweight() {
		return crgoalweight;
	}


	public void setCrgoalweight(int crgoalweight) {
		this.crgoalweight = crgoalweight;
	}


	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}


	@Override
	public String toString() {
		return "CalrecipeDTO [sex=" + sex + ", birth=" + birth + ", age=" + age + ", type=" + type + ", crno=" + crno
				+ ", crbmr=" + crbmr + ", crcal=" + crcal + ", crbmi=" + crbmi + ", crterm=" + crterm + ", cractive="
				+ cractive + ", crheight=" + crheight + ", crweight=" + crweight + ", crgoalweight=" + crgoalweight
				+ ", mid=" + mid + "crdate"+ crdate+"]";
	}


	public Date getCrdate() {
		return crdate;
	}


	public void setCrdate(Date crdate) {
		this.crdate = crdate;
	}
	
	
	
	
	
}
