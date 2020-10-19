package com.atlas.scheduler.purchaseorder.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.atlas.scheduler.purchaseorder.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.service.IPurchaseOrderService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PoDownload {
	

	@Autowired
	IPurchaseOrderService poService;
	
	@KafkaListener(topics= "${po.download.topic}",groupId="po-download")
	public void downloadPo(PurchaseOrder order) {
		log.debug("Po Download Message Received " + order);
		poService.createPurchaseOrder(order);
		log.debug("Po Download Completed");
	}

}
