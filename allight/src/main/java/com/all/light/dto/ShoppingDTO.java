package com.all.light.dto;

public class ShoppingDTO {
	private int ino;
	private String iname;//상품이름
	private String icategory;//상품카테고리
	private int iprice;//상품가격
	private String icorp;//상품회사
	private int istock;//재고
	private String idetail;//상세
	private int cono;//회사번호
	private int imgno;
	private int imgrepresent;
	private String imgimage;
	
	public int getImgno() {
		return imgno;
	}
	public void setImgno(int imgno) {
		this.imgno = imgno;
	}
	public int getImgrepresent() {
		return imgrepresent;
	}
	public void setImgrepresent(int imgrepresent) {
		this.imgrepresent = imgrepresent;
	}
	public String getImgimage() {
		return imgimage;
	}
	public void setImgimage(String imgimage) {
		this.imgimage = imgimage;
	}
	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public String getIcategory() {
		return icategory;
	}
	public void setIcategory(String icategory) {
		this.icategory = icategory;
	}
	public int getIprice() {
		return iprice;
	}
	public void setIprice(int iprice) {
		this.iprice = iprice;
	}
	public String getIcorp() {
		return icorp;
	}
	public void setIcorp(String icorp) {
		this.icorp = icorp;
	}
	public int getIstock() {
		return istock;
	}
	public void setIstock(int istock) {
		this.istock = istock;
	}
	public String getIdetail() {
		return idetail;
	}
	public void setIdetail(String idetail) {
		this.idetail = idetail;
	}
	public int getCono() {
		return cono;
	}
	public void setCono(int cono) {
		this.cono = cono;
	}
	@Override
	public String toString() {
		return "ShoppingDTO [ino=" + ino + ", iname=" + iname + ", icategory=" + icategory + ", iprice=" + iprice
				+ ", icorp=" + icorp + ", istock=" + istock + ", idetail=" + idetail + ", cono=" + cono + ", imgno="
				+ imgno + ", imgrepresent=" + imgrepresent + ", imgimage=" + imgimage + "]";
	}
	
	
}
