package com.atlas.scheduler.purchaseorder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class PurchaseOrderRepositoryTest {

	@Autowired
	private PurchaseOrderRepository repository;

	PurchaseOrder order = null;

	@BeforeEach
	void setup() {
		order = new PurchaseOrder();
		Date now = new Date();
		order = new PurchaseOrder();
		order.setPoNbr(11);
		order.setPoDate(now);
		order.setVendor(1);
		order.setAddress("Erode");
		PurchaseOrderLine line = new PurchaseOrderLine();
		PurchaseOrderLinePK id = new PurchaseOrderLinePK(11, 1);
		line.setItemDescription("Test Item");
		line.setId(id);
		line.setOrderedQty(10);
		line.setUpcNbr(100011);
		List<PurchaseOrderLine> lineEntities = new ArrayList<>();
		order.setPurchaseOrderLines(lineEntities);
		order.setCreatedTimestamp(LocalDateTime.now());
		order.setLastUpdatedTimestamp(order.getCreatedTimestamp());
		line.setCreatedTimestamp(order.getCreatedTimestamp());
		line.setLastUpdatedTimestamp(order.getCreatedTimestamp());
		lineEntities.add(line);
	}

	@Test
	@Description("Save the Purchase Order & Po Line success scenario")
	@Order(1)
	void createPo() {
		PurchaseOrder savedEntity = repository.save(order);
		assertNotNull(savedEntity);
		assertEquals(11, savedEntity.getPoNbr());
	}

	@Test
	@Description("Get the Purchase Order Details by PO")
	@Order(2)
	void getPo() {
		Optional<PurchaseOrder> po = repository.findById(11);
		assertEquals(true, po.isPresent());
		assertEquals(11, po.get().getPoNbr());
	}

	@Test
	@Order(3)
	void testPurchaseOrderEquals() {
		PurchaseOrder order = new PurchaseOrder();
		order.setPoNbr(1);
		PurchaseOrder order1 = new PurchaseOrder();
		order1.setPoNbr(1);
		PurchaseOrder order2 = new PurchaseOrder();
		order2.setPoNbr(2);
		assertEquals(order.hashCode(), order1.hashCode());
		assertEquals(order, order1);
		assertNotEquals(order, order2);
	}

	@Test
	@Order(4)
	void testPurchaseLineEqual() {
		PurchaseOrderLine line1 = new PurchaseOrderLine();
		PurchaseOrderLinePK pk1 = new PurchaseOrderLinePK(1, 1);
		line1.setId(pk1);

		PurchaseOrderLine line2 = new PurchaseOrderLine();
		PurchaseOrderLinePK pk2 = new PurchaseOrderLinePK(1, 1);
		line2.setId(pk2);

		PurchaseOrderLine line3 = new PurchaseOrderLine();
		PurchaseOrderLinePK pk3 = new PurchaseOrderLinePK(1, 2);
		line3.setId(pk3);

		assertEquals(line1.hashCode(), line2.hashCode());
		assertEquals(pk1.hashCode(), pk2.hashCode());
		assertEquals(line1, line2);
		assertNotEquals(line1, line3);
	}

}
