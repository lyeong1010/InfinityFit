package com.all.light.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.all.light.dto.CartDTO;
import com.all.light.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartSVC;
	
	//장바구니로 가기
	@RequestMapping("/mypage/cart")
	public ModelAndView cart(ModelAndView mv, HttpServletRequest request) {
		System.out.println("장바구니페이지 입장");
		String mid = (String)request.getSession().getAttribute("MID");
		List<CartDTO> list = cartSVC.cart(mid);
		mv.setViewName("shopping/user/cart");
		mv.addObject("clist",list);
		return mv;
	}
	
	//장바구니에 상품추가
	@ResponseBody
	@RequestMapping("/insertCart")
	public String insertCart(CartDTO cartdto, HttpServletRequest request) {
		String mid = (String)request.getSession().getAttribute("MID");
		System.out.println("Cart Controller입니다"+cartdto.toString());
		String result = null;

		//장바구니에 상품이 담겨 있는지???
		if(mid != null) {
			System.out.println("cart controller 들어왔니?");
			System.out.println("mid="+mid);
			cartSVC.insertCart(cartdto,mid);
			result = "success";
		}else if (mid == null) {
			System.out.println("로그인안한나쁜사람");
			result = "fail";
		}
		return result;
	}
	
	//장바구니 선택삭제
	@ResponseBody
	@RequestMapping("/mypage/deleteCart")
	public String deleteCart(HttpSession session, @RequestParam(value="chkBox[]") List<String> checkArr, CartDTO cartdto) throws Exception {
		//ajax에서 전송받는 chkBox를 리스트 checkArr로받기
		System.out.println("장바구니 선택 삭제 들어옴????");
		System.out.println("??"+session.getAttribute("MID"));
		
		String caid = (String)session.getAttribute("MID");
		System.out.println("mid: "+caid);
		
		String result = null;
		int cano = 0;
		
		if(caid != null) {
			cartdto.setCaid(caid);
			for (String i : checkArr) {
				System.out.println("사용자의 장바구니 삭제="+caid);
				cano = Integer.parseInt(i);
				cartdto.setCano(cano);
				cartSVC.deleteCart(cartdto);
			}
			result = "success";
		}
		return result;
	}
	
	//+,- 버튼 클릭하면 수량 업데이트 
	@ResponseBody
	@RequestMapping("/mypage/updateAmt")
	public String updateAmt(HttpSession session, CartDTO cartdto) throws Exception {
		System.out.println("수량 변경 저장하러~!~!~!");
		String caid = (String)session.getAttribute("MID");
		System.out.println(cartdto);
		String ok = cartSVC.updateAmt(cartdto,caid);
		return ok;
	}
}