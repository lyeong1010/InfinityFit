package com.all.light.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.MemberDTO;
import com.all.light.dto.OrderDTO;
import com.all.light.dto.OrderData;
import com.all.light.dto.OrderdetailDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.dto.ShoppingDTO;
import com.all.light.util.PageUtil;

public class OrderDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	public ArrayList<OrderDTO> list(OrderDTO odto) {
		return (ArrayList)session.selectList("order.list", odto);
	}

	public ArrayList<OrderdetailDTO> listde(int ono) {
		return (ArrayList)session.selectList("order.listde", ono);
	}

	public ArrayList<ShoppingDTO> iteminfo(int ino) {
		return (ArrayList)session.selectList("order.iteminfo",ino);
	}

	public void change(OrderdetailDTO oddto) {
		System.out.println(oddto.getOstatus());
		System.out.println(oddto.getOdamount());
		if(oddto.getOstatus().equals("배송시작")) {
			session.update("order.change",oddto);
			session.update("order.changeStock",oddto);
			session.update("order.changeIsell",oddto);
		}else if(oddto.getOstatus().equals("반품완료")){
			session.update("order.change",oddto);
			session.update("order.Iselldown",oddto);
		}else {
			session.update("order.change",oddto);
		}
	}

	public MemberDTO check(MemberDTO mdto) {
		return session.selectOne("order.check",mdto);
	}
	
	public void confirm(MemberDTO mdto) {
		session.update("order.confirm", mdto);
	}

	
	public ArrayList<OrderDTO> backlist(OrderDTO odto) {
		ArrayList<OrderDTO> list=null;
		System.out.println(odto.getType());
		if(odto.getType()==null || odto.getType().isEmpty()) {
			list=(ArrayList)session.selectList("order.backlist", odto);
		}else if(odto.getType()!=null) {
			list=(ArrayList)session.selectList("order.backlist2", odto);
		}
		System.out.println("sgsgagagsas"+list);
		return list;
	}
	
	public ArrayList<OrderdetailDTO> back(OrderdetailDTO oddto) {
		ArrayList<OrderdetailDTO> listde=null;
		if(oddto.getType()==null || oddto.getType().isEmpty()) {
			listde=(ArrayList)session.selectList("order.back", oddto);
		}else if(oddto.getType()!=null) {
			listde=(ArrayList)session.selectList("order.back2", oddto);
		}
		return listde;
	}

	public int pageMemberId(String cono) {
		int totalCnt = session.selectOne("order.pageMemberId",cono);
		System.out.println(totalCnt);
		return totalCnt;
	}
	
	public int pageOrderCono(int cono) {
		int totalCnt = session.selectOne("order.pageOrderCono",cono);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public int pageOrderConoTerm(int cono, java.sql.Date start, java.sql.Date last) {
		HashMap map=new HashMap();
		map.put("cono", cono);
		map.put("start", start);
		map.put("last", last);
		System.out.println(map);
		int totalCnt = session.selectOne("order.pageOrderConoTerm",map);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public OrderDTO detail(OrderDTO odto) {
		return (OrderDTO)session.selectOne("order.detail", odto);
	}

	public ArrayList<OrderdetailDTO> listdeCorp(PageUtil pinfo) {
		ArrayList<OrderdetailDTO> listde=null;
		if(pinfo.getSearchType()==null || pinfo.getSearchType().isEmpty()) {
			listde=(ArrayList)session.selectList("order.listdeCorp", pinfo);
		}else {
			listde=(ArrayList)session.selectList("order.listdeCorpType", pinfo);
		}
		return listde;
	}

	public OrderDTO listCorp(int ono) {
		return (OrderDTO)session.selectOne("order.listCorp", ono);
	}

	public OrderdetailDTO detailCorp(int odno) {
		return (OrderdetailDTO)session.selectOne("order.detailCorp", odno);
	}

	public ShoppingDTO iteminfoCorp(int ino) {
		return (ShoppingDTO)session.selectOne("order.iteminfo",ino);
	}

	public OrderDTO detailByNo(int ono) {
		return (OrderDTO)session.selectOne("order.detailByNo", ono);
	}

	public MemberDTO memIdNo(MemberDTO mdto) {
		return (MemberDTO)session.selectOne("order.memIdNo", mdto);
	}

	public void delivery(OrderdetailDTO oddto) {
		session.update("order.delivery", oddto);
	}

	public int pageOrderConoType(int cono, String type) {
		HashMap map=new HashMap();
		map.put("cono", cono);
		map.put("type", type);
		System.out.println("MMMAP"+map);
		int totalCnt = session.selectOne("order.pageOrderConoType",map);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public int pageOrderConoTermType(int cono, Date start, Date last, String type) {
		HashMap map=new HashMap();
		map.put("cono", cono);
		map.put("start", start);
		map.put("last", last);
		map.put("type", type);
		System.out.println(map);
		int totalCnt = session.selectOne("order.pageOrderConoTermType",map);
		System.out.println(totalCnt);
		return totalCnt;
	}

	public void reviewr(ReviewDTO rdto) {
		session.insert("review.reviewWrite",rdto);
		session.update("order.review",rdto);
	}


	



}
