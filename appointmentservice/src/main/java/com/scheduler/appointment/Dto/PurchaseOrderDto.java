package com.scheduler.appointment.Dto;

import java.util.List;


/**
 * <h1>Purchase order Data transfer Object</h1>
 *
 */
public class PurchaseOrderDto {
	
	private int poNbr;
	
	private int qty;

	public int getPoNbr() {
		return poNbr;
	}

	public void setPoNbr(int poNbr) {
		this.poNbr = poNbr;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public PurchaseOrderDto getPo(List<Integer> pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
