package com.all.light.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.CartDAO;
import com.all.light.dto.CartDTO;

@Service
public class CartService {
	
	@Autowired
	private CartDAO cartDAO;
	
	//장바구니
	public List<CartDTO> cart(String mid) {
		//1. 로그인한 아이디로 카트 정보 가져오기 => list
		List<CartDTO> list = cartDAO.cart(mid);
		
		//2. 카트정보 안의 아이템번호로 아이템이름,가격을 for문 안에서 가져오기
		for(int i=0; i<list.size(); i++) {
			CartDTO cartdto = cartDAO.items(list.get(i).getIno());
			list.get(i).setIname(cartdto.getIname());
			list.get(i).setIprice(cartdto.getIprice());
			list.get(i).setImgimages(cartDAO.image(list.get(i).getIno()));
		}
		return list;
	}
	
	//장바구니에 상품추가
	public void insertCart(CartDTO cartdto, String mid) {
		System.out.println("cart service 에 insert cart 들어옴");
		//같은 상품이 있을 때
		int is = cartDAO.isIno(cartdto.getIno(),mid);
		int count = 0;
		
		if(is != 0) {
			//같은상품 있을때 기존 수량 가져오기
			count = cartDAO.inoCount(cartdto.getIno(),mid);
		}
		
		if(count >0){
			System.out.println("서비스에서 상품추가1");
			//같은상품 있을때 기존 합계 가져오기 
			int sum = cartDAO.sum(cartdto.getIno(), mid)+cartdto.getCaprice();
			int total = count + cartdto.getCaamount();
			System.out.println("count="+count);
			System.out.println("caamount="+cartdto.getCaamount());
			cartDAO.updateCart(cartdto.getIno(),total,mid,sum);
		}else {
			System.out.println("서비스에서 상품추가2");
			cartDAO.insertCart(cartdto,mid);
		}
	}

	//장바구니 삭제
	public void deleteCart(CartDTO cartdto) throws Exception{
		cartDAO.deleteCart(cartdto);
		System.out.println("CartService.deleteCart(): "+cartdto);
	}

	//+,- 버튼 클릭하면 수량 업데이트 
	public String updateAmt(CartDTO cartdto, String caid) {
		String ok = cartDAO.updateAmt(cartdto,caid);
		return ok;
	}
	
}