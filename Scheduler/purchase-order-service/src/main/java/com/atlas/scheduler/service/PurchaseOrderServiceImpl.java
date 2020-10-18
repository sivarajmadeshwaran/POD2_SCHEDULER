package com.atlas.scheduler.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlas.scheduler.entity.PurchaseOrderLinePK;
import com.atlas.scheduler.model.PurchaseOrder;
import com.atlas.scheduler.model.PurchaseOrderLine;
import com.atlas.scheduler.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	PurchaseOrderRepository purchaseOrderRepo;

	@Override
	public void createPurchaseOrder(PurchaseOrder order) {
		com.atlas.scheduler.entity.PurchaseOrder po = new com.atlas.scheduler.entity.PurchaseOrder();
		po.setPoNbr(order.getPoNbr());
		po.setAddress(order.getAddress());
		po.setVendor(order.getVendorNbr());
		po.setLastUpdatedTimestamp(LocalDateTime.now());
		po.setCreatedTimestamp(LocalDateTime.now());
		po.setPoDate(order.getPoDate());

		List<PurchaseOrderLine> lines = order.getPoLine();

		List<com.atlas.scheduler.entity.PurchaseOrderLine> linesEntity = lines.stream().map(ord -> {
			com.atlas.scheduler.entity.PurchaseOrderLine line = new com.atlas.scheduler.entity.PurchaseOrderLine();
			PurchaseOrderLinePK id = new PurchaseOrderLinePK();
			id.setPoLineNbr(ord.getPoLineNbr());
			id.setPoNbr(order.getPoNbr());
			line.setId(id);
			line.setOrderedQty(ord.getQuantity());
			line.setCreatedTimestamp(LocalDateTime.now());
			line.setLastUpdatedTimestamp(LocalDateTime.now());
			line.setOrderedQty(ord.getQuantity());
			line.setUpcNbr(ord.getUpc());
			line.setItemDescription(ord.getItemDesc());
			return line;
		}).collect(Collectors.toList());

		po.setPurchaseOrderLines(linesEntity);
		purchaseOrderRepo.save(po);
	}

	@Override
	public List<PurchaseOrder> getPoDetailsByNbrs(List<Integer> orders) {
		List<com.atlas.scheduler.entity.PurchaseOrder> poOrders=purchaseOrderRepo.findAllById(orders);
		List<PurchaseOrder> purchaseOrders=poOrders.stream().map(order -> {
			PurchaseOrder ord=new  PurchaseOrder();
			if(order!=null) {			
			ord.setPoNbr(order.getPoNbr());
			ord.setVendorNbr(order.getVendor());
			ord.setAddress(order.getAddress());
			ord.setPoDate(order.getPoDate());
			}
			return ord;
		} ).collect(Collectors.toList());
		return purchaseOrders;
	}

}
