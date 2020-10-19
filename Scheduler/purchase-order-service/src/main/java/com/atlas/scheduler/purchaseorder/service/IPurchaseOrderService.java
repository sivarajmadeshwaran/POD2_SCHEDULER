package com.atlas.scheduler.purchaseorder.service;

import java.util.List;

import com.atlas.scheduler.purchaseorder.PurchaseOrder;

public interface IPurchaseOrderService {
	
	void createPurchaseOrder(PurchaseOrder order);

	List<PurchaseOrder> getPoDetailsByNbrs(List<Integer> orders);

}
