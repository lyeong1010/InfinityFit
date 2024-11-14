package com.all.light.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.all.light.dto.AddressDTO;
import com.all.light.dto.BuyDTO;
import com.all.light.dto.CartDTO;
import com.all.light.dto.OrderDTO;
import com.all.light.dto.OrderdetailDTO;

public class BuyDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	//주문페이지로 이동
	public List<BuyDTO> buy(String mid) {
		List <BuyDTO> list = session.selectList("Buy.buy",mid);
		return list;
	}

	//회원번호로 상품내용가져오기
	public CartDTO items(int ino) {
		CartDTO cartdto = session.selectOne("Cart.items",ino);
		return cartdto;
	}

	//장바구니번호로 장바구니 불러오기
	public CartDTO cart(int cano) {
		CartDTO cartdto = session.selectOne("Buy.cart",cano);
		return cartdto;
	}
	
	//아이템번호 이용해서 장바구니로 상품이미지 가져오기
	public String image(int ino) {
		String image = session.selectOne("Buy.image",ino);
		return image;
	}

	//결제페이지에서 주소록에 저장되어있는 주소 가져오기
	public ArrayList<AddressDTO> addresslist(String mid) {
		return (ArrayList) session.selectList("member.memAddress", mid);
	}

	//장바구니비우기
	public void emptyCart(int cano) {
		session.delete("Buy.emptyCart",cano);
	}

	//orderDTO에 저장하기 위해 ono가져올때 필요
	public int onosel(String mid) {
		return session.selectOne("Buy.onosel",mid);
	}
	
	//결제 후 orderDTO에 저장
	public void ordersin(OrderDTO odto) {
		session.insert("Buy.ordersin",odto);
	}

	//결제후 orderdetailDTO에 저장
	public void orderdetailin(CartDTO li) {
		System.out.println("mm///"+li);
		int i=session.insert("Buy.detail",li);
		System.out.println("Buy.orderdetailin///"+i);
	}
	
}
