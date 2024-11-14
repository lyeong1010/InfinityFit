package com.all.light.dto;

import java.sql.Date;

public class ItemQuestionDTO {
	
	//ITEMQUETION
	private int iqno;			//상품문의번호
	private int ino;			//상품번호
	private String iqid;		//아이디
	private String iqnick;		//닉네임
	private String iqtitle;		//문의 제목
	private String iqcontent;	//문의 내용
	private Date iqdate;		//문의 작성일
	private int iqsecret;		//문의 비밀여부
	
	//ITEMQUETIONCOMMENT
	private int iqcno;			//상품문의댓글번호
	private String iqcid;		//댓글작성 아이디
	private String iqcnick;		//댓글작성 닉네임
	private String iqccontent;	//댓글내용
	private Date iqcdate;		//댓글 작성일
	
	public int getIqno() {
		return iqno;
	}
	public void setIqno(int iqno) {
		this.iqno = iqno;
	}
	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public String getIqid() {
		return iqid;
	}
	public void setIqid(String iqid) {
		this.iqid = iqid;
	}
	public String getIqnick() {
		return iqnick;
	}
	public void setIqnick(String iqnick) {
		this.iqnick = iqnick;
	}
	public String getIqtitle() {
		return iqtitle;
	}
	public void setIqtitle(String iqtitle) {
		this.iqtitle = iqtitle;
	}
	public String getIqcontent() {
		return iqcontent;
	}
	public void setIqcontent(String iqcontent) {
		this.iqcontent = iqcontent;
	}
	public Date getIqdate() {
		return iqdate;
	}
	public void setIqdate(Date iqdate) {
		this.iqdate = iqdate;
	}
	public int getIqsecret() {
		return iqsecret;
	}
	public void setIqsecret(int iqsecret) {
		this.iqsecret = iqsecret;
	}
	public int getIqcno() {
		return iqcno;
	}
	public void setIqcno(int iqcno) {
		this.iqcno = iqcno;
	}
	public String getIqcid() {
		return iqcid;
	}
	public void setIqcid(String iqcid) {
		this.iqcid = iqcid;
	}
	public String getIqcnick() {
		return iqcnick;
	}
	public void setIqcnick(String iqcnick) {
		this.iqcnick = iqcnick;
	}
	public String getIqccontent() {
		return iqccontent;
	}
	public void setIqccontent(String iqccontent) {
		this.iqccontent = iqccontent;
	}
	public Date getIqcdate() {
		return iqcdate;
	}
	public void setIqcdate(Date iqcdate) {
		this.iqcdate = iqcdate;
	}
	
	
	@Override
	public String toString() {
		return "ItemQuestionDTO [iqno=" + iqno + ", ino=" + ino + ", iqid=" + iqid + ", iqnick=" + iqnick + ", iqtitle="
				+ iqtitle + ", iqcontent=" + iqcontent + ", iqdate=" + iqdate + ", iqsecret=" + iqsecret + ", iqcno="
				+ iqcno + ", iqcid=" + iqcid + ", iqccontent=" + iqccontent + ", iqcdate=" + iqcdate + "]";
	}
	
}
