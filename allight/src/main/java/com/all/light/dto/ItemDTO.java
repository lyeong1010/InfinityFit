package com.all.light.dto;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

//파일게시판 관련  DTO
public class ItemDTO {
	//받아서 관리해야할 데이터를 저장하기 위한 변수 선언
	
	//item -상품-
	private int ino;			// 상품 번호
	private String iname;		// 상품 이름
	private String icategory;	// 상품 카테고리
	private int iprice;			// 상품 가격
	private String icorp;		// 상품 기업명
	private int istock; 		// 재고
	private String idetail;		// 상세내용
	private int cono;			// 기업번호
	private int isellamount;	// 판매량
	
	//image -상품이미지-
	private int imgno;  				// 이미지번호
	//private int ino;					// 상품번호(원글번호)
	//private MultipartFile imgimage;		// 대표이미지
	private MultipartFile[] imgimages;	// 상품이미지 
	private int	fileCount;				//첨부파일수	
	private int	rno;
	
	// -페이징처리-
	private int	start; 		//시작글번호
	private	int	end;		//끝글번호
	private int	nowPage;	//보고싶은페이지
	
	// -검색처리-
	private String target;		//검색분야
	private String word;		//검색어
	
	private String	oriName;	//원래이름
	private String	saveName;	//저장된이름
	private long	len;		//파일용량
	private String 	path;		//파일저장위치
	private int 	select;		//대표사진여부
	
	public int getSelect() {
		return select;
	}
	public void setSelect(int select) {
		this.select = select;
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
	public int getImgno() {
		return imgno;
	}
	public void setImgno(int imgno) {
		this.imgno = imgno;
	}
	/*
	public MultipartFile getImgimage() {
		return imgimage;
	}
	public void setImgimage(MultipartFile imgimage) {
		this.imgimage = imgimage;
	}*/
	public MultipartFile[] getImgimages() {
		return imgimages;
	}
	public void setImgimages(MultipartFile[] imgimages) {
		this.imgimages = imgimages;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getLen() {
		return len;
	}
	public void setLen(long len) {
		this.len = len;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getIsellamount() {
		return isellamount;
	}
	public void setIsellamount(int isellamount) {
		this.isellamount = isellamount;
	}
	@Override
	public String toString() {
		return "ItemDTO [ino=" + ino + ", iname=" + iname + ", icategory=" + icategory + ", iprice=" + iprice
				+ ", icorp=" + icorp + ", istock=" + istock + ", idetail=" + idetail + ", cono=" + cono
				+ ", isellamount=" + isellamount + ", imgno=" + imgno + ", imgimages=" + Arrays.toString(imgimages)
				+ ", fileCount=" + fileCount + ", rno=" + rno + ", start=" + start + ", end=" + end + ", nowPage="
				+ nowPage + ", target=" + target + ", word=" + word + ", oriName=" + oriName + ", saveName=" + saveName
				+ ", len=" + len + ", path=" + path + ", select=" + select + "]";
	}
	


	
	
}








