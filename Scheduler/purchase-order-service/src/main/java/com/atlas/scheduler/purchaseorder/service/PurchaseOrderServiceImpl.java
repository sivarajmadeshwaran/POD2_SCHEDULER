package com.atlas.scheduler.purchaseorder.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderLineDto;
import com.atlas.scheduler.purchaseorder.exception.BusinessException;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLine;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLinePK;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderRepository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj This is for Purchase Order Domain Business Implementation
 */
@Service
@Slf4j
@Setter
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;

	/**
	 * This is to persist the purchase order domain
	 * 
	 * @param Purchase Order DTO
	 * @return void
	 * @throws BusinessException
	 */
	@Override
	public void createPurchaseOrder(PurchaseOrderDto order) throws BusinessException {
		log.debug(" createPurchaseOrder for po ", order.getPoNbr());
		PurchaseOrder po = null;
		try {
			po = convertPurchaseOrderDtoToEntity(order);
			po.setPurchaseOrderLines(convertPoLineDtoToEntity(order));
			purchaseOrderRepo.save(po);
		} catch (Exception e) {
			log.error("Exception while Creating Purchase order   Exception :: {} ", e);
			throw new BusinessException("Exception while creating Order");
		}
	}

	/**
	 * This is to get the Purchase Order Details for the List of Purchase Order
	 * Numbers
	 * 
	 * @param List of Purchase Order Numbers
	 * @return List of PurchaseOrder Dto
	 */
	@Override
	public List<PurchaseOrderDto> getPoDetailsByNbrs(List<Integer> orders) throws BusinessException {

		try {
			List<com.atlas.scheduler.purchaseorder.repository.PurchaseOrder> poOrders = purchaseOrderRepo
					.findAllById(orders);

			return collectionToStream(poOrders).filter(order -> order != null).map(ordr -> {
				PurchaseOrderDto ord = new PurchaseOrderDto();
				ord.setPoNbr(ordr.getPoNbr());
				ord.setVendorNbr(ordr.getVendor());
				ord.setAddress(ordr.getAddress());
				ord.setPoDate(ordr.getPoDate());
				ord.setPoLine(convertPoLineEntityToDto(ordr.getPurchaseOrderLines()));
				return ord;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Exception while Getting Purchase order Details ", e);
			throw new BusinessException("Exception while Getting Po Details");
		}
	}

	/**
	 * This is to convert the Purchase Order DTO object to PurchaseOrder Entity
	 * 
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
	 * This is to convert the PurchaseOrder Line DTO to List of Purchase Order Line
	 * Entities
	 * 
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

	private <T> Stream<T> collectionToStream(Collection<T> collection) {
		return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
	}

	/**
	 * This is to convert the PurchaseOrder Line DTO to List of Purchase Order Line
	 * Entities
	 * 
	 * @param order
	 * @return List of Purchase Order Line Entities
	 */
	private List<PurchaseOrderLineDto> convertPoLineEntityToDto(List<PurchaseOrderLine> orderLines) {
		return orderLines.stream().map(line -> {
			PurchaseOrderLineDto lineDto = new PurchaseOrderLineDto();
			lineDto.setPoLineNbr(line.getId().getPoLineNbr());
			lineDto.setQuantity(line.getOrderedQty());
			lineDto.setUpc(line.getUpcNbr());
			lineDto.setItemDesc(line.getItemDescription());
			return lineDto;
		}).collect(Collectors.toList());
	}

}
