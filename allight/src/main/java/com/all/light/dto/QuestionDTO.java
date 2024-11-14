package com.all.light.dto;

import java.sql.Date;

public class QuestionDTO {
	private int qno;
	private String qtitle;
	private String qid;
	private String qnick;
	private Date qdate;
	private String qcontent;
	private int qtype;
	private int qcno;
	private String qcid;
	private String qcnick;
	private String qccontent;
	private Date qcdate;
	private int qcount;
	
	
	
	public String getQnick() {
		return qnick;
	}
	public void setQnick(String qnick) {
		this.qnick = qnick;
	}
	public String getQcnick() {
		return qcnick;
	}
	public void setQcnick(String qcnick) {
		this.qcnick = qcnick;
	}
	public int getQno() {
		return qno;
	}
	public void setQno(int qno) {
		this.qno = qno;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public Date getQdate() {
		return qdate;
	}
	public void setQdate(Date qdate) {
		this.qdate = qdate;
	}
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}
	public int getQtype() {
		return qtype;
	}
	public void setQtype(int qtype) {
		this.qtype = qtype;
	}
	public int getQcno() {
		return qcno;
	}
	public void setQcno(int qcno) {
		this.qcno = qcno;
	}
	public String getQcid() {
		return qcid;
	}
	public void setQcid(String acid) {
		this.qcid = acid;
	}
	public String getQccontent() {
		return qccontent;
	}
	public void setQccontent(String qccontent) {
		this.qccontent = qccontent;
	}
	public Date getQcdate() {
		return qcdate;
	}
	public void setQcdate(Date qcdate) {
		this.qcdate = qcdate;
	}
	public int getQcount() {
		return qcount;
	}
	public void setQcount(int qcount) {
		this.qcount = qcount;
	}
	@Override
	public String toString() {
		return "QuestionDTO [qno=" + qno + ", qtitle=" + qtitle + ", qid=" + qid + ", qnick=" + qnick + ", qdate="
				+ qdate + ", qcontent=" + qcontent + ", qtype=" + qtype + ", qcno=" + qcno + ", qcid=" + qcid
				+ ", qcnick=" + qcnick + ", qccontent=" + qccontent + ", qcdate=" + qcdate + ", qcount=" + qcount + "]";
	}

	
}
