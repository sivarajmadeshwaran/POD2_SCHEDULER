package com.atlas.scheduler.purchaseorder.service;

import java.util.List;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.exception.BusinessException;


public interface IPurchaseOrderService {
	
	void createPurchaseOrder(PurchaseOrderDto order) throws BusinessException;

	List<PurchaseOrderDto> getPoDetailsByNbrs(List<Integer> orders)  throws BusinessException;

}
