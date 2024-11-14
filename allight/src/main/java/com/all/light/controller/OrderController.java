package com.all.light.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.MemberDTO;
import com.all.light.dto.OrderDTO;
import com.all.light.dto.OrderData;
import com.all.light.dto.OrderdetailDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.dto.ShoppingDTO;
import com.all.light.service.OrderService;
import com.all.light.util.PageUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService ordSVC;
	
	//주문목록조회-기간
	@RequestMapping("/mypage/list")
	public ModelAndView list(@RequestParam(value = "term", required = false, defaultValue = "m1") String term,
			OrderDTO odto,OrderdetailDTO oddto,ShoppingDTO sdto,OrderData odata,HttpSession session, ModelAndView mv) {
		OrderData data=ordSVC.list(session,odata,odto,oddto,sdto,term);
		System.out.println("OrderController"+data);
		mv.addObject("ORDER", data);
		System.out.println("OrderController"+data);
		mv.setViewName("shopping/user/order/list");
		return mv;
	}
	
	//주문상세조회
	@RequestMapping("/mypage/detail")
	public ModelAndView detail(@RequestParam(value = "no", required = true) int ono,
			OrderDTO odto,OrderdetailDTO oddto,OrderData odata,ModelAndView mv,HttpSession session) {
		System.out.println("detail");
		OrderData data=ordSVC.detail(session,odata,odto,oddto,ono);
		mv.addObject("ORDER", data);
		mv.setViewName("shopping/user/order/detail");
		return mv;
	}
	
	//주문취소및반품조회 
	@RequestMapping("/mypage/back") //type o,x
	public ModelAndView back(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "term", required = false, defaultValue = "m1") String term,
			OrderDTO odto,OrderdetailDTO oddto,ShoppingDTO sdto,OrderData odata,HttpSession session, ModelAndView mv) {
		OrderData data=ordSVC.back(session,odata,odto,oddto,sdto,type,term);
		System.out.println(data);
		mv.addObject("ORDER", data);
		mv.setViewName("shopping/user/order/backlist");
		return mv;
	}
	
	//상태 변경
	@RequestMapping("/change")
	@ResponseBody
	public String change(OrderdetailDTO oddto) {
		System.out.println("change"+oddto);
		ordSVC.change(oddto);
		return "ok";
	}
	
	//취소 반품 페이지이동-주문관리 ajax 후?????
	@RequestMapping("/mypage/check")
	public ModelAndView check(OrderdetailDTO oddto,MemberDTO mdto,HttpSession session, ModelAndView mv) {
		System.out.println("check"+oddto);
		MemberDTO medto=ordSVC.check(session,mdto);
		mv.addObject("MDTO", medto);
		System.out.println("check22222"+oddto);
		mv.addObject("oddto", oddto);
		mv.setViewName("shopping/user/order/check");
		return mv;
	}
	
	@RequestMapping("/mypage/confirm")
	public ModelAndView confirm(OrderdetailDTO oddto,MemberDTO mdto,HttpSession session, ModelAndView mv) {
		System.out.println("confirm"+oddto);
		System.out.println("confirm"+mdto);
		ordSVC.confirm(session,mdto);
		MemberDTO medto=ordSVC.check(session,mdto);
		mv.addObject("oddto", oddto);
		mv.addObject("MDTO", medto);
		mv.setViewName("shopping/user/order/confirm");
		return mv;
	}
	
	//기업
	//주문관리 리스트 페이징, 기간(년월일)
	@RequestMapping("/list/corp")
	public ModelAndView listCorp(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "last", required = false) String last,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			OrderDTO odto,OrderdetailDTO oddto,ShoppingDTO sdto,OrderData odata,
			HttpSession session, ModelAndView mv) {
		int cono=Integer.parseInt(String.valueOf(session.getAttribute("CONO")));
		System.out.println("start"+start);
		System.out.println("last"+last);
		System.out.println("type"+type);
		PageUtil pinfo = ordSVC.pageOrderCono(nowPage,cono,start,last,type);
		pinfo.setSearchType(type);
		OrderData data=ordSVC.listCorp(session,odata,odto,oddto,pinfo);
		mv.addObject("PINFO", pinfo);
		mv.addObject("ORDER", data);
		System.out.println("OrderController"+data);
		mv.setViewName("shopping/corp/order/list");
		return mv;
	}
	
	//주문관리 상세 - select * from orders where ono=(select ono from orderdatail where odno=?)
	@RequestMapping("/detail/corp")
	public ModelAndView detailCorp(@RequestParam(value = "no", required = false) int odno,
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			OrderDTO odto,OrderdetailDTO oddto,ShoppingDTO sdto,MemberDTO mdto,OrderData odata,
			HttpSession session, ModelAndView mv) {
		OrderData data=ordSVC.detailCorp(session,odata,odto,oddto,mdto,odno);
		mv.addObject("ORDER", data);
		mv.addObject("NOW", nowPage);
		System.out.println("OrderController"+data);
		mv.setViewName("shopping/corp/order/detail");
		return mv;
	}
	
	//주문관리 현황 변경
	@RequestMapping("/change/corp")
	public ModelAndView changeCorp(OrderdetailDTO oddto,ModelAndView mv) {
		ordSVC.change(oddto);
		RedirectView rv=new RedirectView("../detail/corp.com?no="+oddto.getOdno());
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping("/delivery/corp")
	public ModelAndView delivery(OrderdetailDTO oddto,ModelAndView mv) {
		System.out.println(oddto);
		ordSVC.delivery(oddto);
		RedirectView rv=new RedirectView("../detail/corp.com?no="+oddto.getOdno());
		mv.setView(rv);
		return mv;
	}
	
	//이메일 발송
    @RequestMapping("/email/corp")
    @ResponseBody
	public String email(HttpServletRequest request) throws Exception {
    	System.out.println("이메일 확인 들어옴!");
    	String no=request.getParameter("no");
    	String email=request.getParameter("email");
    	String text=request.getParameter("text");
    	
    	String ok=null;
    	if(text.equals("배송시작")) {
    		ok=ordSVC.delemail(no,email);//메일전송
        	System.out.println("배송시작 이메일 확인 완료!");
        	return ok;
    	}else if(text.equals("반품")) {
    		ok=ordSVC.reemail(no,email);//메일전송
        	System.out.println("반품완료 이메일 확인 완료!");
        	return ok;
    	}else if(text.equals("주문취소")) {
    		ok=ordSVC.canemail(no,email);//메일전송
        	System.out.println("주문취소 이메일 확인 완료!");
        	return ok;
    	}
		return "x";
    }

    //리뷰쓰기
    @RequestMapping("/mypage/review")
    public ModelAndView review(@RequestParam(value = "no", required = true) String ino,
    		@RequestParam(value = "num", required = true) String odno,ModelAndView mv) {
    	System.out.println(ino+"...."+odno);
    	mv.addObject("ino", ino);
    	mv.addObject("odno", odno);
    	mv.setViewName("shopping/user/mypageReview/reviewWrite");
		return mv;
    }
    
    @RequestMapping("/reviewr")
    public ModelAndView reviewr(ReviewDTO rdto,HttpSession session,ModelAndView mv) {
    	System.out.println(rdto);
    	ordSVC.reviewr(rdto,session);
    	RedirectView rv=new RedirectView("./mypage/list.com");
		mv.setView(rv);
		return mv;
    }
    

	
	
	
}
