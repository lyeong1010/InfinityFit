package com.all.light.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.OrderdetailDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.dto.ShoppingDTO;
import com.all.light.util.PageUtil;
import com.all.light.dao.OrderDAO;
import com.all.light.dto.MemberDTO;
import com.all.light.dto.OrderDTO;
import com.all.light.dto.OrderData;

public class OrderService {
	
	@Autowired
	private OrderDAO ordDAO;
	
	public OrderData list(HttpSession session, OrderData odata, OrderDTO odto, OrderdetailDTO oddto,
			ShoppingDTO sdto, String term) {
		odto.setMid((String) session.getAttribute("MID"));
		Date udate=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(udate);
		if(term.equals("w")) {
			cal.add(Calendar.DATE, -7); //1주일
		}else if(term.equals("m1")) {
			cal.add(Calendar.MONTH, -1); //개월
		}else if(term.equals("m3")) {
			cal.add(Calendar.MONTH, -3);
		}else if(term.equals("m6")) {
			cal.add(Calendar.MONTH, -6); 
		}
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		odto.setSearchdate(date);
		
		ArrayList<OrderDTO> list=ordDAO.list(odto);

		
		System.out.println("OrderService odata"+odata);
		System.out.println("OrderService list"+list);
		
		ArrayList<OrderdetailDTO> listde=null;
		ArrayList<ShoppingDTO> shop=null;
		for(int i=0;i<list.size();i++) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			list.get(i).setOrdernum(format.format(list.get(i).getOdate())+
					list.get(i).getMno()+list.get(i).getOno());
			list.get(i).setSodate(format2.format(list.get(i).getOdate()));		
			
			odata.setOdto(list);
			System.out.println("LIST"+i+"///"+list.get(i));
			
			listde=ordDAO.listde(list.get(i).getOno());
			System.out.println(listde);
			if(odata.getOddto()==null) {
				odata.setOddto(listde);
			}else if(odata.getOddto()!=null) {
				odata.getOddto().addAll(listde);
			}
			
			System.out.println("OrderService listde"+listde);
			System.out.println("OrderService odata"+odata);
			
			for(int j=0;j<listde.size();j++) {
				System.out.println(odata);
				System.out.println("LISTDE"+j+"///"+listde.get(j));
				
				int ino=listde.get(j).getIno();
				shop= ordDAO.iteminfo(ino);
				if(odata.getSdto()==null) {
					odata.setSdto(shop);
				}else if(odata.getSdto()!=null) {
					odata.getSdto().addAll(shop);
				}

				System.out.println("OrderService odata"+odata);
				System.out.println("OrderService shop"+shop);
				
				System.out.println("DETAIL"+j+"///"+shop);
			}
		}
		System.out.println(odata);
		return odata;
	}
	//상세
	public OrderData detail(HttpSession session, OrderData odata, OrderDTO odto, OrderdetailDTO oddto, int ono) {
		odto.setMid((String) session.getAttribute("MID"));
		odto.setOno(ono);
		OrderDTO list=ordDAO.detail(odto);
		odata.setOdto1(list);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		list.setOrdernum(format.format(list.getOdate())+list.getMno()+list.getOno());
		list.setSodate(format2.format(list.getOdate()));		
		oddto.setOno(list.getOno());
		ArrayList<OrderdetailDTO> listde=ordDAO.listde(ono);
		odata.setOddto(listde);			
		ArrayList<ShoppingDTO> shop=null;
		int sum=0;
		for(int j=0;j<listde.size();j++) {	
			int price=listde.get(j).getOdprice();
			sum=sum+price;
			int ino=listde.get(j).getIno();
			shop= ordDAO.iteminfo(ino);
			if(odata.getSdto()==null) {
				odata.setSdto(shop);
			}else if(odata.getSdto()!=null) {
				odata.getSdto().addAll(shop);
			}
		}
		list.setSum(sum);
		System.out.println(odata);
		return odata;
	}
	
	//상태 변경
	public void change(OrderdetailDTO oddto) {
		System.out.println(oddto);
		ordDAO.change(oddto);
	}
	//취소반품조회
	public OrderData back(HttpSession session, OrderData odata, OrderDTO odto, OrderdetailDTO oddto, ShoppingDTO sdto, String type, String term) {
		odto.setMid((String) session.getAttribute("MID"));
		Date udate=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(udate);
		if(term.equals("w")) {
			cal.add(Calendar.DATE, -7); //1주일
		}else if(term.equals("m1")) {
			cal.add(Calendar.MONTH, -1); //개월
		}else if(term.equals("m3")) {
			cal.add(Calendar.MONTH, -3);
		}else if(term.equals("m6")) {
			cal.add(Calendar.MONTH, -6); 
		}
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		odto.setSearchdate(date);
		if(type==null) {
			oddto.setType(null);
			odto.setType(null);
		}else if(type.equals("cancel")) {
			oddto.setType("주문취소");
			odto.setType("주문취소");
		}else if(type.equals("back")) {
			oddto.setType("반품");
			odto.setType("반품");
		}
		ArrayList<OrderDTO> list=ordDAO.backlist(odto);
		System.out.println(list);
		ArrayList<OrderdetailDTO> listde=null;
		ArrayList<ShoppingDTO> shop=null;
		for(int i=0;i<list.size();i++) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			list.get(i).setOrdernum(format.format(list.get(i).getOdate())+
					list.get(i).getMno()+list.get(i).getOno());
			list.get(i).setSodate(format2.format(list.get(i).getOdate()));		
			
			odata.setOdto(list);
			oddto.setOno(list.get(i).getOno());
			listde=ordDAO.back(oddto);
			if(odata.getOddto()==null) {
				odata.setOddto(listde);
			}else if(odata.getOddto()!=null) {
				odata.getOddto().addAll(listde);
			}
			for(int j=0;j<listde.size();j++) {
				
				int ino=listde.get(j).getIno();
				shop= ordDAO.iteminfo(ino);
				if(odata.getSdto()==null) {
					odata.setSdto(shop);
				}else if(odata.getSdto()!=null) {
					odata.getSdto().addAll(shop);
				}
			}
		}
		System.out.println(odata);
		return odata;
	}
	
	//계좌번호,은행
	public MemberDTO check(HttpSession session, MemberDTO mdto) {
		int mno=Integer.parseInt(String.valueOf(session.getAttribute("MNO")));
		String mid=(String) session.getAttribute("MID");
		mdto.setMno(mno);
		mdto.setMid(mid);
		return ordDAO.check(mdto);
	}
	
	public void confirm(HttpSession session, MemberDTO mdto) {
		int mno=Integer.parseInt(String.valueOf(session.getAttribute("MNO")));
		String mid=(String) session.getAttribute("MID");
		mdto.setMno(mno);
		mdto.setMid(mid);
		ordDAO.confirm(mdto);
	}



	//기업
	//페이징
	public PageUtil pageOrderCono(int nowPage, int cono, String star, String las, String type) {
		int totalCount=0;
		if (star==null || star.isEmpty() || las==null || las.isEmpty() ) {
			System.out.println(cono);
			if(type==null || type.isEmpty()) {
				totalCount = ordDAO.pageOrderCono(cono);
			}else {
				System.out.println("pageOrderCono"+type);
				totalCount = ordDAO.pageOrderConoType(cono,type);
			}
		}else if(star!=null && las!=null) {
			java.sql.Date start=java.sql.Date.valueOf(star);
			java.sql.Date last=java.sql.Date.valueOf(las);
			if(type==null || type.isEmpty()) {
				totalCount = ordDAO.pageOrderConoTerm(cono,start,last);
			}else {
				totalCount = ordDAO.pageOrderConoTermType(cono,start,last,type);
			}
		}
		System.out.println(star+"////"+las+"////"+type+"////"+totalCount);
		PageUtil pinfo = new PageUtil(nowPage, totalCount,8,5);
		return pinfo;
	}
	
	public OrderData listCorp(HttpSession session, OrderData odata, OrderDTO odto, OrderdetailDTO oddto, PageUtil pinfo) {
		ArrayList<OrderdetailDTO> listde=ordDAO.listdeCorp(pinfo);
		System.out.println("llllll"+pinfo);
		OrderDTO list=null;
		ArrayList<ShoppingDTO> shop=null;
		for(int i=0;i<listde.size();i++) {
			odata.setOddto(listde);
			list=ordDAO.listCorp(listde.get(i).getOno());
			System.out.println(listde.get(i).getOno()+"/////"+list.getOno());
			if(odata.getOdto1()==null) {
				System.out.println("nullll");
				odata.setOdto1(list);
			}else if(odata.getOdto1()!=null && listde.get(i).getOno()!=list.getOno()) {
				System.out.println("not nullll");
				odata.getOdto().add(list);
			}
			int ino=listde.get(i).getIno();
			shop= ordDAO.iteminfo(ino);
			if(odata.getSdto()==null) {
				odata.setSdto(shop);
			}else if(odata.getSdto()!=null) {
				odata.getSdto().addAll(shop);
			}
		}
		System.out.println(odata);
		return odata;
	}
	
	public OrderData detailCorp(HttpSession session, OrderData odata, OrderDTO odto, OrderdetailDTO oddto,
			MemberDTO mdto, int odno) {
		OrderdetailDTO listde=ordDAO.detailCorp(odno);
		odata.setOddto1(listde);
		OrderDTO list=ordDAO.detailByNo(listde.getOno());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		list.setOrdernum(format.format(list.getOdate())+list.getMno()+list.getOno());
		odata.setOdto1(list);
		ShoppingDTO shop=ordDAO.iteminfoCorp(listde.getIno());
		odata.setSdto1(shop);
		mdto.setMid(list.getMid());
		mdto.setMno(list.getMno());
		MemberDTO mlist=ordDAO.memIdNo(mdto);
		odata.setMdto1(mlist);
		return odata;
	}
	
	
	public void delivery(OrderdetailDTO oddto) {
		ordDAO.delivery(oddto);
	}
	
	public String delemail(String no,String email) {
		System.out.println(no);
		System.out.println(email);
		OrderdetailDTO oddto=ordDAO.detailCorp(Integer.parseInt(no));
		ShoppingDTO sdto=ordDAO.iteminfoCorp(oddto.getIno());
		OrderDTO odto=ordDAO.listCorp(oddto.getOno());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		odto.setOrdernum(format.format(odto.getOdate())+odto.getMno()+odto.getOno());
		
		//sdto 상품이름, 
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.gmail.com";
		int hostPort = 465;
		String hostSMTPid = "allight.adm@gmail.com";
		String hostSMTPpwd = "goallight!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "allight.adm@gmail.com";
		String fromName = "allight";
		String subject = "";
		String msg = "";

		subject = "Allight 상품 배송이 시작되었습니다.";
		msg += "<div style='text-align:center; font-family:Helvetica Neue'>";
		msg += "<h1>안녕하세요. Allight입니다.</h1>";
		msg += "<h1 style='color: orange;'>고객님께서 주문하신 상품이 발송되었습니다.</h1><br/>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>주문번호 : "+odto.getOrdernum()+"</h1>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>상품명 : "+sdto.getIname()+"</h1>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>택배사 : "+oddto.getOcouriercompany()+"</h1>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>송장번호 : "+oddto.getOinvoicenumber()+"</h1>";
		msg += "</div>";
		msg += "<div align='center' style='font-family:Helvetica Neue'>";
		msg += "<br/>";
		msg += "<h4 style='color: red;'>본 메일은 상품 배송 안내를 위해 발송된 발신 전용 메일입니다.</h4>";
		msg += "<h4 style='color: red;'>문의사항은 고객센터 또는 이메일 'allight.adm@gmail.com'로 문의주세요";
		msg += "</div>";

		// 받는 사람 E-Mail 주소
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSL(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(hostPort);

			mail.setAuthentication(hostSMTPid, hostSMTPpwd);
			mail.setTLS(true);
			mail.addTo(email, charSet);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
			return "delemail";
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
			return null;
		}
	}
	
	public String canemail(String no,String email) {
		System.out.println(no);
		System.out.println(email);
		OrderdetailDTO oddto=ordDAO.detailCorp(Integer.parseInt(no));
		ShoppingDTO sdto=ordDAO.iteminfoCorp(oddto.getIno());
		OrderDTO odto=ordDAO.listCorp(oddto.getOno());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		odto.setOrdernum(format.format(odto.getOdate())+odto.getMno()+odto.getOno());
		
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.gmail.com";
		int hostPort = 465;
		String hostSMTPid = "allight.adm@gmail.com";
		String hostSMTPpwd = "goallight!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "allight.adm@gmail.com";
		String fromName = "allight";
		String subject = "";
		String msg = "";

		subject = "Allight 상품 주문이 취소되었습니다.";
		msg += "<div style='text-align:center; font-family:Helvetica Neue'>";
		msg += "<h1>안녕하세요. Allight입니다.</h1><br/>";
		msg += "<h1 style='color: orange;'>고객님께서 주문하신 상품이 취소되었습니다.</h1><br/>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>주문번호 : "+odto.getOrdernum()+"</h1>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>상품명 : "+sdto.getIname()+"</h1><br/>";
		msg += "<h1>상세내용은  <a href='http://192.168.56.48:9000/allight/main.com'> allight </a>홈페이지에 방문하셔서 확인 부탁드립니다.</h1>";
		msg += "</div>";
		msg += "<div align='center' style='font-family:Helvetica Neue'>";
		msg += "<br/>";
		msg += "<h4 style='color: red;'>본 메일은 주문 취소 안내를 위해 발송된 발신 전용 메일입니다.</h4>";
		msg += "<h4 style='color: red;'>문의사항은 고객센터 또는 이메일 'allight.adm@gmail.com'로 문의주세요";
		msg += "</div>";

		// 받는 사람 E-Mail 주소
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSL(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(hostPort);

			mail.setAuthentication(hostSMTPid, hostSMTPpwd);
			mail.setTLS(true);
			mail.addTo(email, charSet);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
			return "canemail";
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
			return null;
		}
	}
	
	public String reemail(String no, String email) {
		System.out.println(no);
		System.out.println(email);
		OrderdetailDTO oddto=ordDAO.detailCorp(Integer.parseInt(no));
		ShoppingDTO sdto=ordDAO.iteminfoCorp(oddto.getIno());
		OrderDTO odto=ordDAO.listCorp(oddto.getOno());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		odto.setOrdernum(format.format(odto.getOdate())+odto.getMno()+odto.getOno());
		
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.gmail.com";
		int hostPort = 465;
		String hostSMTPid = "allight.adm@gmail.com";
		String hostSMTPpwd = "goallight!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "allight.adm@gmail.com";
		String fromName = "allight";
		String subject = "";
		String msg = "";

		subject = "Allight 상품 반품이 완료되었습니다.";
		msg += "<div style='text-align:center; font-family:Helvetica Neue'>";
		msg += "<h1>안녕하세요. Allight입니다.</h1><br/>";
		msg += "<h1 style='color: orange;'>고객님께서 주문하신 상품의 반품이 완료되었습니다.</h1><br/>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>주문번호 : "+odto.getOrdernum()+"</h1>";
		msg += "<h1 style='background:#f8f8f8;padding:10px;'>상품명 : "+sdto.getIname()+"</h1><br/>";
		msg += "<h1>상세 내용은<a href='http://192.168.56.48:9000/allight/main.com'> allight </a>홈페이지에 방문하셔서 확인 부탁드립니다.</h1>";
		msg += "</div>";
		msg += "<div align='center' style='font-family:Helvetica Neue'>";
		msg += "<br/>";
		msg += "<h4 style='color: red;'>본 메일은 반품 안내를 위해 발송된 발신 전용 메일입니다.</h4>";
		msg += "<h4 style='color: red;'>문의사항은 고객센터 또는 이메일 'allight.adm@gmail.com'로 문의주세요";
		msg += "</div>";

		// 받는 사람 E-Mail 주소
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSL(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(hostPort);

			mail.setAuthentication(hostSMTPid, hostSMTPpwd);
			mail.setTLS(true);
			mail.addTo(email, charSet);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
			return "reemail";
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
			return null;
		}
	}

	
	public void reviewr(ReviewDTO rdto, HttpSession session) {
		rdto.setRid((String)session.getAttribute("MID"));
		rdto.setRnick((String)session.getAttribute("MNICK"));
		
		ordDAO.reviewr(rdto);
	}
	
	

}
