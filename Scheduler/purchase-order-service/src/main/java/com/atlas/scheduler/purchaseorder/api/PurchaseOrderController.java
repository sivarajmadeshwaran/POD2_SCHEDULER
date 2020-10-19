package com.atlas.scheduler.purchaseorder.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.scheduler.purchaseorder.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.service.IPurchaseOrderService;


@RestController
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {
	
	
	@Autowired
	IPurchaseOrderService orderService;
	
	@PostMapping()
	public ResponseEntity createOrder(@RequestBody PurchaseOrder order) {
		orderService.createPurchaseOrder(order);
		System.out.println("Order Created ===");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/find/poNbrs")
	public ResponseEntity<List<PurchaseOrder>> createOrder(@RequestBody List<Integer> orders) {
		System.out.println("Order details ===" + orders);
		return new ResponseEntity<>(orderService.getPoDetailsByNbrs(orders), HttpStatus.OK);
	}
	
	
}
