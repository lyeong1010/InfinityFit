package com.all.light.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import com.all.light.dto.CartDTO;

public class CartDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	//장바구니 가져오기
	public List<CartDTO> cart(String mid) {
		List<CartDTO> list = session.selectList("Cart.cart", mid);
		return list;
	}
	
	//장바구니에 추가
	public void insertCart(CartDTO cartdto, String mid) {
		System.out.println("CartDAO - cart");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("mid", mid);
	    map.put("ino", cartdto.getIno());
	    map.put("caamount", cartdto.getCaamount());
	    map.put("caprice", cartdto.getCaprice());
		session.insert("Cart.insertCart", map);
	}
	
	//아이템번호로 아이템이름 가져오기
	public CartDTO items(int ino) {
		CartDTO cartdto = session.selectOne("Cart.items",ino);
		return cartdto;
	}

	//같은 상품이 장바구니에 존재하는지 확인
	public int isIno(int ino, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ino", ino);
		map.put("mid", mid);
		int isIno = session.selectOne("Cart.isIno",map);
		return isIno;
	}
		
	//카트에서 해당 아이디의 상품 수량 조회
	public int inoCount(int ino, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ino", ino);
		map.put("mid", mid);
		int thisIno = session.selectOne("Cart.inoCount",map);
		System.out.println("카트디A오="+thisIno);
		return thisIno;
	}

	//같은상품 있을때 수량 업데이트
	public void updateCart(int ino, int total, String mid, int sum) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ino", ino);
		map.put("total", total);
		map.put("mid", mid);
		map.put("sum", sum);
		session.update("Cart.updateCart",map);
	}
	
	//같은상품 있을때 기존 합계 가져오기
	public int sum(int ino, String mid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ino", ino);
		map.put("mid", mid);
		int sum = session.selectOne("Cart.sum",map);
		return sum;
	}

	//장바구니 삭제
	public void deleteCart(CartDTO cartdto) {
		session.delete("Cart.deleteCart",cartdto);
		System.out.println("CartDAO.deleteCart(): "+cartdto);
	}

	//장바구니로 상품이미지 가져오기
	public String image(int ino) {
		String image = session.selectOne("Cart.image",ino);
		return image;
	}

	//+,- 버튼 클릭하면 수량 업데이트 
	public String updateAmt(CartDTO cartdto, String caid) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("caid", caid);
		map.put("caamount", cartdto.getCaamount());
		map.put("caprice", cartdto.getCaprice());
		map.put("cano", cartdto.getCano());
		int ok = session.update("Cart.updateAmt",map);
		System.out.println("Cart.updateAmt():"+cartdto);
		return Integer.toString(ok);
	}

}
