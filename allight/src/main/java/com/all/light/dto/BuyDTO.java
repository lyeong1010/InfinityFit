package com.all.light.dto;

import java.sql.Date;
import java.util.Arrays;

public class BuyDTO {
	private int ono;//주문번호seq
	private String mid;
	private int mno;
	private Date odate;//주문날짜
	private String oaddno;//우편번호
	private String oaddress1;//주소
	private String oaddress2;//상세주소
	private String otel;
	private String oreceiver;
	private String orequirethings;//요구사항
	private Date searchdate;//찾는 날짜
	private String ordernum;
	private String sodate;//주문날짜
	private int sum;//총합
	private String type;
	private int[] arr;	//배열
	
	public int[] getArr() {
		return arr;
	}
	public void setArr(int[] arr) {
		this.arr = arr;
	}
	public int getOno() {
		return ono;
	}
	public void setOno(int ono) {
		this.ono = ono;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Date getOdate() {
		return odate;
	}
	public void setOdate(Date odate) {
		this.odate = odate;
	}
	public String getOaddno() {
		return oaddno;
	}
	public void setOaddno(String oaddno) {
		this.oaddno = oaddno;
	}
	public String getOaddress1() {
		return oaddress1;
	}
	public void setOaddress1(String oaddress1) {
		this.oaddress1 = oaddress1;
	}
	public String getOaddress2() {
		return oaddress2;
	}
	public void setOaddress2(String oaddress2) {
		this.oaddress2 = oaddress2;
	}
	public String getOtel() {
		return otel;
	}
	public void setOtel(String otel) {
		this.otel = otel;
	}
	public String getOreceiver() {
		return oreceiver;
	}
	public void setOreceiver(String oreceiver) {
		this.oreceiver = oreceiver;
	}
	public String getOrequirethings() {
		return orequirethings;
	}
	public void setOrequirethings(String orequirethings) {
		this.orequirethings = orequirethings;
	}
	public Date getSearchdate() {
		return searchdate;
	}
	public void setSearchdate(Date searchdate) {
		this.searchdate = searchdate;
	}
	
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	
	public String getSodate() {
		return sodate;
	}
	public void setSodate(String string) {
		this.sodate = string;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "BuyDTO [ono=" + ono + ", mid=" + mid + ", mno=" + mno + ", odate=" + odate + ", oaddno=" + oaddno
				+ ", oaddress1=" + oaddress1 + ", oaddress2=" + oaddress2 + ", otel=" + otel + ", oreceiver="
				+ oreceiver + ", orequirethings=" + orequirethings + ", searchdate=" + searchdate + ", ordernum="
				+ ordernum + ", sodate=" + sodate + ", sum=" + sum + ", type=" + type + ", arr=" + Arrays.toString(arr)
				+ "]";
	}
	
}
