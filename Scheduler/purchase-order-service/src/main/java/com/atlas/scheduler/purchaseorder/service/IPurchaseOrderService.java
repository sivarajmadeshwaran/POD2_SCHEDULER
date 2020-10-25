package com.atlas.scheduler.purchaseorder.service;

import java.util.List;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;


public interface IPurchaseOrderService {
	
	void createPurchaseOrder(PurchaseOrderDto order);

	List<PurchaseOrderDto> getPoDetailsByNbrs(List<Integer> orders);

}
