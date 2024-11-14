package com.all.light.dto;

// 주소관련 DTO
public class AddressDTO {
	
	private int ano; // 번호
	private String aaddno; // 우편번호
	private String aaddress1; // 주소
	private String aaddress2; // 상세주소
	private String aaddress3; // 상세주소
	private String mid; // 아이디
	private String aname; // 수취인
	private String aphone; // 수취인 연락처

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getAaddno() {
		return aaddno;
	}

	public void setAaddno(String aaddno) {
		this.aaddno = aaddno;
	}

	public String getAaddress1() {
		return aaddress1;
	}

	public void setAaddress1(String aaddress1) {
		this.aaddress1 = aaddress1;
	}

	public String getAaddress2() {
		return aaddress2;
	}

	public void setAaddress2(String aaddress2) {
		this.aaddress2 = aaddress2;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAphone() {
		return aphone;
	}

	public void setAphone(String aphone) {
		this.aphone = aphone;
	}



	public String getAaddress3() {
		return aaddress3;
	}

	public void setAaddress3(String aaddress3) {
		this.aaddress3 = aaddress3;
	}

	@Override
	public String toString() {
		return "AddressDTO [ano=" + ano + ", aaddno=" + aaddno + ", aaddress1=" + aaddress1 + ", aaddress2=" + aaddress2
				+ ", aaddress3=" + aaddress3 + ", mid=" + mid + ", aname=" + aname + ", aphone=" + aphone + "]";
	}


}








