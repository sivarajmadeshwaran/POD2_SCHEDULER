package com.atlas.scheduler.purchaseorder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLine;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLinePK;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj 
 * This is for Purchase Order Domain Business Implementation
 */
@Service
@Slf4j
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;


	/**
	 *  This is to persist the purchase order domain
	 *  
	 * @param Purchase Order DTO
	 * @return void
	 */
	@Override
	public void createPurchaseOrder(PurchaseOrderDto order) {
		log.debug(" createPurchaseOrder for po " , order.getPoNbr());
		PurchaseOrder po = convertPurchaseOrderDtoToEntity(order);
		po.setPurchaseOrderLines(convertPoLineDtoToEntity(order));
		purchaseOrderRepo.save(po);
	}

	/**
	 *  This is to get the Purchase Order Details for the List of Purchase Order Numbers 
	 * @param List of Purchase Order Numbers
	 * @return List of PurchaseOrder Dto
	 */
	@Override
	public List<PurchaseOrderDto> getPoDetailsByNbrs(List<Integer> orders) {
		List<com.atlas.scheduler.purchaseorder.repository.PurchaseOrder> poOrders = purchaseOrderRepo
				.findAllById(orders);
		return poOrders.stream().map(order -> {
			PurchaseOrderDto ord = new PurchaseOrderDto();
			if (order != null) {
				ord.setPoNbr(order.getPoNbr());
				ord.setVendorNbr(order.getVendor());
				ord.setAddress(order.getAddress());
				ord.setPoDate(order.getPoDate());
			}
			return ord;
		}).collect(Collectors.toList());
	}

	/**
	 *  This is to convert the  Purchase Order DTO object to PurchaseOrder Entity
	 * @param order
	 * @return PurchaseOrder Entity
	 */
	private PurchaseOrder convertPurchaseOrderDtoToEntity(PurchaseOrderDto order) {
		PurchaseOrder po = new PurchaseOrder();
		po.setPoNbr(order.getPoNbr());
		po.setAddress(order.getAddress());
		po.setVendor(order.getVendorNbr());
		po.setLastUpdatedTimestamp(LocalDateTime.now());
		po.setCreatedTimestamp(LocalDateTime.now());
		po.setPoDate(order.getPoDate());
		return po;
	}

	
	/**
	 *  This is to convert the PurchaseOrder Line DTO to List of Purchase Order Line Entities
	 * @param order
	 * @return List of Purchase Order Line Entities
	 */
	private List<PurchaseOrderLine> convertPoLineDtoToEntity(PurchaseOrderDto order) {
		return order.getPoLine().stream().map(ord -> {
			com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLine line = new com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLine();
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
	}

}
