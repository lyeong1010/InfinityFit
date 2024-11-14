package com.all.light.dto;

public class CartDTO {
	private int cano;		//장바구니번호
	private String caid;	//아이디
	private int ino;		//상품번호(외부연결)
	private int caamount;	//수량
	private int caprice;	//상품가격
	private String iname;		// 상품 이름
	private int iprice;			// 상품 가격
	private int istock;			// 상품 재고
	private int[] canolist;
	private int ono;
	
	//image -상품이미지-
	private int imgno;  				// 이미지번호
	private String imgimages;	// 상품이미지 
	//private int	fileCount;				//첨부파일수	
	
	public int getIstock() {
		return istock;
	}
	public void setIstock(int istock) {
		this.istock = istock;
	}
	public int getIprice() {
		return iprice;
	}
	public void setIprice(int iprice) {
		this.iprice = iprice;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public int getCano() {
		return cano;
	}
	public void setCano(int cano) {
		this.cano = cano;
	}
	public String getCaid() {
		return caid;
	}
	public void setCaid(String caid) {
		this.caid = caid;
	}
	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public int getCaamount() {
		return caamount;
	}
	public void setCaamount(int caamount) {
		this.caamount = caamount;
	}
	public int getCaprice() {
		return caprice;
	}
	public void setCaprice(int caprice) {
		this.caprice = caprice;
	}
	public int getImgno() {
		return imgno;
	}
	public void setImgno(int imgno) {
		this.imgno = imgno;
	}
	public String getImgimages() {
		return imgimages;
	}
	public void setImgimages(String imgimages) {
		this.imgimages = imgimages;
	}
	
	public int[] getCanolist() {
		return canolist;
	}
	public void setCanolist(int[] canolist) {
		this.canolist = canolist;
	}
	
	@Override
	public String toString() {
		return "CartDTO [cano=" + cano + ", caid=" + caid + ", ino=" + ino + ", caamount=" + caamount + ", caprice="
				+ caprice + ", iname=" + iname + ", iprice=" + iprice + ", istock=" + istock + ", imgno=" + imgno
				+ ", imgimages=" + imgimages + ", canolist"+canolist+ ",ono"+ono+"]";
	}
	public int getOno() {
		return ono;
	}
	public void setOno(int ono) {
		this.ono = ono;
	}
	
/*	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
*/}
