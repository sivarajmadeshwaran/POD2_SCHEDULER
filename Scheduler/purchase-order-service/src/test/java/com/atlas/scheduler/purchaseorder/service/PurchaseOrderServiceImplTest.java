package com.atlas.scheduler.purchaseorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderLineDto;
import com.atlas.scheduler.purchaseorder.exception.BusinessException;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLine;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderLinePK;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderRepository;

class PurchaseOrderServiceImplTest {

	@Mock
	PurchaseOrderRepository repo;
	
	PurchaseOrderServiceImpl service = null;
	List<PurchaseOrder> pos = null;
	PurchaseOrderDto orderDto = null;
	PurchaseOrder order = null;

	@BeforeEach
	public void setUp() {
		Date now = new Date();

		MockitoAnnotations.initMocks(this);
		service = new PurchaseOrderServiceImpl();
		service.setPurchaseOrderRepo(repo);
		pos = new ArrayList<>();
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
		pos.add(order);

		orderDto = new PurchaseOrderDto();
		PurchaseOrderLineDto lineDto = new PurchaseOrderLineDto();
		lineDto.setItemDesc("Test Item");
		lineDto.setPoLineNbr(2);
		lineDto.setUpc(100011);
		lineDto.setQuantity(5);
		List<PurchaseOrderLineDto> lines = new ArrayList<>();
		lines.add(lineDto);
		orderDto.setPoNbr(11);
		orderDto.setAddress("Erode");
		orderDto.setPoDate(now);
		orderDto.setVendorNbr(1);
		orderDto.setPoLine(lines);
	}

	@DisplayName("Get Po Details By Po Nbs & Test for all pos are returned successfully")
	@Test
	void getPoDetailsByNbrsSuccess() throws BusinessException {
		List<Integer> requestPos = new ArrayList<>();
		when(repo.findAllById(requestPos)).thenReturn(pos);
		List<PurchaseOrderDto> dtos = service.getPoDetailsByNbrs(requestPos);
		assertEquals(1, dtos.size());
		assertEquals(11, dtos.get(0).getPoNbr());
		verify(repo).findAllById(requestPos);
	}

	@DisplayName("Po Details By Po Nbs No Content Scenario")
	@Test
	void getPoDetailsByNbrsNoContent() throws BusinessException {
		List<Integer> requestPos = new ArrayList<>();
		when(repo.findAllById(requestPos)).thenReturn(null);
		List<PurchaseOrderDto> dtos = service.getPoDetailsByNbrs(requestPos);
		assertEquals(0, dtos.size());
		verify(repo).findAllById(requestPos);
	}

	@DisplayName("Exception while retrieving data from DB Scenario")
	@Test
	void getPoDetailsByNbrsDbException() throws BusinessException {
		List<Integer> requestPos = new ArrayList<>();
		when(repo.findAllById(requestPos)).thenThrow(RuntimeException.class);
		Assertions.assertThrows(BusinessException.class, () -> {
			service.getPoDetailsByNbrs(requestPos);
		});
	}

	@DisplayName("Sucess scenario of persisting  Purchase Order")
	@Test
	void testCreatePurchaseOrder() throws BusinessException {
		when(repo.save(pos.get(0))).thenReturn(pos.get(0));
		service.createPurchaseOrder(orderDto);
		Optional<PurchaseOrder> ord = Optional.of(pos.get(0));
		when(repo.findById(11)).thenReturn(ord);
		assertEquals(11,ord.get().getPoNbr());
	}

	@DisplayName("Exception scenario while persisting  Purchase Order")
	@Test
	void testCreatePurchaseOrderException() throws BusinessException {
		when(repo.save(pos.get(0))).thenReturn(null);
		orderDto.setPoNbr(null);
		Assertions.assertThrows(BusinessException.class, () -> service.createPurchaseOrder(orderDto));
	}

	@AfterEach
	public void tearDown() {
		service = null;
		pos = null;
	}

}
