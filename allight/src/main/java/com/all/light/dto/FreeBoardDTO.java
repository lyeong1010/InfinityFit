package com.all.light.dto;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class FreeBoardDTO {
	// 자유게시판 DB
	private int fno;
	private String ftype;
	private String ftitle;
	private String fid;
	private String fnick;
	private Date fdate;
	private String fcontent;
	private int fhit;

	private MultipartFile file;
	private MultipartFile[] files;
	// 자유게시판 댓글 DB
	private int fcno;
	private String fcid;
	private String fcnick;
	private String fccontent;
	private Date fcdate;
	private int fccount;
	// 자유게시판 댓글 좋아요DB
	private String flno;
	private String flid;
	private String flnick;
	
	private int amount;	// 좋아요수
	private Boolean isLiked; //좋아요여부

	// 파일 넣자
	private String fioriname; // 원래이름
	private String fiimg; // 저장된이름
	private long filength; // 파일용량
	private String fipath; // 파일저장위치

	
	public int getFccount() {
		return fccount;
	}

	public void setFccount(int fccount) {
		this.fccount = fccount;
	}

	
	public String getFioriname() {
		return fioriname;
	}

	public void setFioriname(String fioriname) {
		this.fioriname = fioriname;
	}

	public String getFiimg() {
		return fiimg;
	}

	public void setFiimg(String fiimg) {
		this.fiimg = fiimg;
	}

	public long getFilength() {
		return filength;
	}

	public void setFilength(long filength) {
		this.filength = filength;
	}

	public String getFipath() {
		return fipath;
	}

	public void setFipath(String fipath) {
		this.fipath = fipath;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getFcontent() {
		return fcontent;
	}

	public Date getFdate() {
		return fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFnick() {
		return fnick;
	}

	public void setFnick(String fnick) {
		this.fnick = fnick;
	}

	public int getFhit() {
		return fhit;
	}

	public void setFhit(int fhit) {
		this.fhit = fhit;
	}

	public int getFcno() {
		return fcno;
	}

	public void setFcno(int fcno) {
		this.fcno = fcno;
	}

	public String getFcid() {
		return fcid;
	}

	public void setFcid(String fcid) {
		this.fcid = fcid;
	}

	public String getFcnick() {
		return fcnick;
	}

	public void setFcnick(String fcnick) {
		this.fcnick = fcnick;
	}

	public String getFccontent() {
		return fccontent;
	}

	public void setFccontent(String fccontent) {
		this.fccontent = fccontent;
	}


	public Date getFcdate() {
		return fcdate;
	}

	public void setFcdate(Date fcdate) {
		this.fcdate = fcdate;
	}

	public String getFlno() {
		return flno;
	}

	public void setFlno(String flno) {
		this.flno = flno;
	}

	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public String getFlnick() {
		return flnick;
	}

	public void setFlnick(String flnick) {
		this.flnick = flnick;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "FreeBoardDTO [fno=" + fno + ", ftype=" + ftype + ", ftitle=" + ftitle + ", fid=" + fid + ", fnick="
				+ fnick + ", fdate=" + fdate + ", fcontent=" + fcontent + ", fhit=" + fhit + ", files="
				+ Arrays.toString(files) + ", fcno=" + fcno + ", fcid=" + fcid + ", fcnick=" + fcnick + ", fccontent="
				+ fccontent + ", fcdate=" + fcdate + ", fccount=" + fccount + ", flno=" + flno + ", flid=" + flid
				+ ", flnick=" + flnick + ", amount=" + amount + ", isLiked=" + isLiked + ", fioriname=" + fioriname
				+ ", fiimg=" + fiimg + ", filength=" + filength + ", fipath=" + fipath + "]";
	}


	


}