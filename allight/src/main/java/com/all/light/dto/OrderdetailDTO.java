package com.all.light.dto;

public class OrderdetailDTO {
	private int ono;//주문번호
	private int odno;//주문상세번호
	private int ino;//상품번호
	private String ocouriercompany;//택배회사
	private String oinvoicenumber;//송장번호
	private String ostatus;//현황
	private int odamount;//수량
	private int odprice;//가격
	private String type;
	private int okreview;//리뷰여부확인
	
	public int getOno() {
		return ono;
	}
	public void setOno(int ono) {
		this.ono = ono;
	}
	public int getOdno() {
		return odno;
	}
	public void setOdno(int odno) {
		this.odno = odno;
	}
	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public String getOcouriercompany() {
		return ocouriercompany;
	}
	public void setOcouriercompany(String ocouriercompany) {
		this.ocouriercompany = ocouriercompany;
	}
	public String getOinvoicenumber() {
		return oinvoicenumber;
	}
	public void setOinvoicenumber(String oinvoicenumber) {
		this.oinvoicenumber = oinvoicenumber;
	}
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	public int getOdamount() {
		return odamount;
	}
	public void setOdamount(int odamount) {
		this.odamount = odamount;
	}
	public int getOdprice() {
		return odprice;
	}
	public void setOdprice(int odprice) {
		this.odprice = odprice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOkreview() {
		return okreview;
	}
	public void setOkreview(int okreview) {
		this.okreview = okreview;
	}
	@Override
	public String toString() {
		return "OrderdetailDTO [ono=" + ono + ", odno=" + odno + ", ino=" + ino + ", ocouriercompany=" + ocouriercompany
				+ ", oinvoicenumber=" + oinvoicenumber + ", ostatus=" + ostatus + ", odamount=" + odamount
				+ ", odprice=" + odprice + ", type=" + type + ", okreview=" + okreview + "]";
	}
	
	
}
