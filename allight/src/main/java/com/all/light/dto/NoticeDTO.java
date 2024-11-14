package com.all.light.dto;
import java.time.LocalDate;

public class NoticeDTO {
	private int nno;
	private String nid;
	private String nnick;
	private LocalDate ndate;
	private String ncontent;
	private String ntitle;
	private String nhit;
	public int getNno() {
		return nno;
	}
	public void setNno(int nno) {
		this.nno = nno;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getNnick() {
		return nnick;
	}
	public void setNnick(String nnick) {
		this.nnick = nnick;
	}
	public LocalDate getNdate() {
		return ndate;
	}
	public void setNdate(LocalDate ndate) {
		this.ndate = ndate;
	}
	public String getNcontent() {
		return ncontent;
	}
	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}
	public String getNtitle() {
		return ntitle;
	}
	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}
	public String getNhit() {
		return nhit;
	}
	public void setNhit(String nhit) {
		this.nhit = nhit;
	}
	@Override
	public String toString() {
		return "NoticeDTO [nno=" + nno + ", nid=" + nid + ", nnick=" + nnick + ", ndate=" + ndate + ", ncontent="
				+ ncontent + ", ntitle=" + ntitle + ", nhit=" + nhit + "]";
	}
}
