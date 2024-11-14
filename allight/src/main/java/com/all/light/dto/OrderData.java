package com.all.light.dto;

import java.util.ArrayList;

public class OrderData {
	private ArrayList<OrderDTO> odto;
	private OrderDTO odto1;
	private ArrayList<OrderdetailDTO> oddto;
	private OrderdetailDTO oddto1;
	private ArrayList<ShoppingDTO> sdto;
	private ShoppingDTO sdto1;
	private MemberDTO mdto1;
		
	public ArrayList<OrderDTO> getOdto() {
		return odto;
	}
	public void setOdto(ArrayList<OrderDTO> odto) {
		this.odto = odto;
	}
	public ArrayList<OrderdetailDTO> getOddto() {
		return oddto;
	}
	public void setOddto(ArrayList<OrderdetailDTO> oddto) {
		this.oddto = oddto;
	}
	public ArrayList<ShoppingDTO> getSdto() {
		return sdto;
	}
	public void setSdto(ArrayList<ShoppingDTO> sdto) {
		this.sdto = sdto;
	}
	public OrderDTO getOdto1() {
		return odto1;
	}
	public void setOdto1(OrderDTO odto1) {
		this.odto1 = odto1;
	}
	public OrderdetailDTO getOddto1() {
		return oddto1;
	}
	public void setOddto1(OrderdetailDTO oddto1) {
		this.oddto1 = oddto1;
	}
	public ShoppingDTO getSdto1() {
		return sdto1;
	}
	public void setSdto1(ShoppingDTO sdto1) {
		this.sdto1 = sdto1;
	}
	@Override
	public String toString() {
		return "OrderData [odto=" + odto + ", odto1=" + odto1 + ", oddto=" + oddto + ", oddto1=" + oddto1 + ", sdto="
				+ sdto + ", sdto1=" + sdto1 +", mdto1=" + mdto1 + "]";
	}
	public MemberDTO getMdto1() {
		return mdto1;
	}
	public void setMdto1(MemberDTO mdto1) {
		this.mdto1 = mdto1;
	}

	
	

}
