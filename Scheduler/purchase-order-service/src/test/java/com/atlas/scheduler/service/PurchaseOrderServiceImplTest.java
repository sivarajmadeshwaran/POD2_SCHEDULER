package com.atlas.scheduler.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atlas.scheduler.model.PurchaseOrder;
import com.atlas.scheduler.model.PurchaseOrderLine;
import com.atlas.scheduler.repository.PurchaseOrderRepository;

@SpringBootTest
class PurchaseOrderServiceImplTest {

	@Autowired
	IPurchaseOrderService purchaseOrderService;
	
	 PurchaseOrder order;
	 
	 @Mock
	 PurchaseOrderRepository repo;
	
	@BeforeEach
	public void setup() {
		 order = new PurchaseOrder();
		 PurchaseOrderLine line =new PurchaseOrderLine();
		 line.setItemDesc("Test Item");
		 line.setPoLineNbr(2);
		 line.setQuantity(10);
		 line.setUpc(100011);
		 List<PurchaseOrderLine> lines=new ArrayList<>();
		 lines.add(line);
		 order.setAddress("Erode");
		 order.setPoDate(new Date());
		 order.setPoNbr(123456);
		 order.setPoLine(lines);
		 order.setVendorNbr(1);
	}
	
	
	@Test
	public void createPoSuccess() {
		purchaseOrderService.createPurchaseOrder(order);
	}

}
