package com.all.light.dto;

import java.sql.Date;
import java.util.Arrays;

public class CorporationDTO {
	private int cono;
	private String coid;
	private String copw;
	private String coname;
	private String cotel;
	private String coemail;
	private String[] arr;
	private String auto;
	private Date cojoindate;
	


	public Date getCojoindate() {
		return cojoindate;
	}

	public void setCojoindate(Date cojoindate) {
		this.cojoindate = cojoindate;
	}

	public int getCono() {
		return cono;
	}

	public void setCono(int cono) {
		this.cono = cono;
	}

	public String getCoid() {
		return coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
	}

	public String getCopw() {
		return copw;
	}

	public void setCopw(String copw) {
		this.copw = copw;
	}

	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getCotel() {
		return cotel;
	}

	public void setCotel(String cotel) {
		this.cotel = cotel;
	}

	public String getCoemail() {
		return coemail;
	}

	public void setCoemail(String coemail) {
		this.coemail = coemail;
	}
	
	public String[] getArr() {
		return arr;
	}

	public void setArr(String[] arr) {
		this.arr = arr;
	}



	@Override
	public String toString() {
		return "CorporationDTO [cono=" + cono + ", coid=" + coid + ", copw=" + copw + ", coname=" + coname + ", cotel="
				+ cotel + ", coemail=" + coemail + ", arr=" + Arrays.toString(arr) + ", auto=" + auto + ", joindate="
				+ cojoindate + "]";
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

}
