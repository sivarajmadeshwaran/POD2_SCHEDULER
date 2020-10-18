package com.atlas.scheduler.service;

import java.util.List;

import com.atlas.scheduler.model.PurchaseOrder;

public interface IPurchaseOrderService {
	
	void createPurchaseOrder(PurchaseOrder order);

	List<PurchaseOrder> getPoDetailsByNbrs(List<Integer> orders);

}
