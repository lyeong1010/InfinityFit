package com.all.light.dto;

import java.time.LocalDate;

public class ReviewDTO {
	// REVIEW 테이블
	int rno;		//리뷰번호
	int ino;		//상품번호
	String rid;		//작성자 아이디
	String rnick;	//작성자 닉네임
	LocalDate rdate;		//작성일
	int rgrade;		//별점
	String rcontent;//내용
	int odno;
	
	public int getOdno() {
		return odno;
	}
	public void setOdno(int odno) {
		this.odno = odno;
	}
	// REVIEWLIKE 테이블
	int rlno;		//리뷰 좋아요 번호
	String rlid;	//리뷰 좋아요한 아이디
	
	// DB에는 없음
	int rlamount;	//리뷰에 대한 좋아요수
	Boolean isLiked;//로그인한 아이디의 해당 리뷰 좋아요 여부
	
	public ReviewDTO(){}
	//ReviewService.getReviewInfo메소드에서 쓰이는 생성자
	public ReviewDTO(int ino, String id) {
		this.ino=ino;
		this.rid=id;
	}
	public Boolean getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRnick() {
		return rnick;
	}
	public void setRnick(String rnick) {
		this.rnick = rnick;
	}
	public LocalDate getRdate() {
		return rdate;
	}
	public void setRdate(LocalDate rdate) {
		this.rdate = rdate;
	}
	public int getRgrade() {
		return rgrade;
	}
	public void setRgrade(int rgrade) {
		this.rgrade = rgrade;
	}
	public String getRcontent() {
		return rcontent;
	}
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	public int getRlno() {
		return rlno;
	}
	public void setRlno(int rlno) {
		this.rlno = rlno;
	}
	public String getRlid() {
		return rlid;
	}
	public void setRlid(String rlid) {
		this.rlid = rlid;
	}
	public int getRlamount() {
		return rlamount;
	}
	public void setRlamount(int rlamount) {
		this.rlamount = rlamount;
	}
	@Override
	public String toString() {
		return "ReviewDTO [rno=" + rno + ", ino=" + ino + ", rid=" + rid + ", rnick=" + rnick + ", rdate=" + rdate
				+ ", rgrade=" + rgrade + ", rcontent=" + rcontent + ", odno=" + odno + ", rlno=" + rlno + ", rlid="
				+ rlid + ", rlamount=" + rlamount + ", isLiked=" + isLiked + "]";
	}
	
	
	
}
