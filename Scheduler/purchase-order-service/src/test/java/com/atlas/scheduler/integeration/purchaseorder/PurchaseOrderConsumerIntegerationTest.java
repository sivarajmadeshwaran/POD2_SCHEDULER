package com.atlas.scheduler.integeration.purchaseorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Description;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderLineDto;
import com.atlas.scheduler.purchaseorder.exception.BusinessException;
import com.atlas.scheduler.purchaseorder.infrastructure.PoDownload;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrder;
import com.atlas.scheduler.purchaseorder.repository.PurchaseOrderRepository;
import com.atlas.scheduler.purchaseorder.service.PurchaseOrderServiceImpl;

@SpringBootTest
@EmbeddedKafka(topics = { "po_download", "test_retry" }, partitions = 3)
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@TestMethodOrder(OrderAnnotation.class)
class PurchaseOrderConsumerIntegerationTest {

	@Autowired
	EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	KafkaTemplate<Integer, PurchaseOrderDto> kafkaTemplate;

	@Autowired
	KafkaListenerEndpointRegistry endpointRegistry;

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@SpyBean
	PoDownload poConsumerSpy;

	@SpyBean
	PurchaseOrderServiceImpl poServiceSpy;

	@BeforeEach
	void setUp() {
		for (MessageListenerContainer messageListenerContainer : endpointRegistry.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
		}
	}

	PurchaseOrderDto getOrder() {
		PurchaseOrderDto order = new PurchaseOrderDto();
		order.setAddress("Erode");
		order.setPoDate(new Date());
		order.setPoNbr(123456);
		order.setVendorNbr(1);
		PurchaseOrderLineDto lineDto = new PurchaseOrderLineDto();
		lineDto.setItemDesc("Test Item");
		lineDto.setPoLineNbr(2);
		lineDto.setUpc(100011);
		lineDto.setQuantity(5);
		List<PurchaseOrderLineDto> lines = new ArrayList<>();
		lines.add(lineDto);
		order.setPoLine(lines);
		return order;
	}

	@Test
	@Order(1)
	@DisplayName("Po Download Success Scenario")
	void downloadPo() throws InterruptedException, ExecutionException, BusinessException {
		// Post Message from third party to po_download topic
		kafkaTemplate.send("po-download", getOrder()).get();
		// Just holding the process for 3 seconds
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(3, TimeUnit.SECONDS);
		// then
		verify(poConsumerSpy, times(1)).downloadPo(isA(ConsumerRecord.class));
		verify(poServiceSpy, times(1)).createPurchaseOrder(isA(PurchaseOrderDto.class));
		List<PurchaseOrder> poList = (List<PurchaseOrder>) purchaseOrderRepository.findAll();

		assertNotNull(poList);
		Predicate<PurchaseOrder> poPred = p -> p.getPoNbr() == 123456;
		assertEquals(true, poList.stream().anyMatch(poPred));
	}

	@Test
	@Order(2)
	@DisplayName("Po Download processing in retry topic Scenario")
	void testPoDownloadRetry() throws Exception {
		// Post Message from third party to po_download topic
		kafkaTemplate.send("test_retry", getOrder()).get();
		// Just holding the process for 3 seconds
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(3, TimeUnit.SECONDS);
		// then
		verify(poConsumerSpy, times(1)).retryDownloadPo(isA(ConsumerRecord.class));
		verify(poServiceSpy, times(1)).createPurchaseOrder(isA(PurchaseOrderDto.class));
		List<PurchaseOrder> poList = (List<PurchaseOrder>) purchaseOrderRepository.findAll();
		assertNotNull(poList);
		Predicate<PurchaseOrder> poPred = p -> p.getPoNbr() == 123456;
		assertEquals(true, poList.stream().anyMatch(poPred));
	}

	@Test
	@Order(3)
	@DisplayName("Po Download Failure Scenario")
	void downloadPoFailure() throws Exception {
		// Post Message from third party to po_downloadtopic
		PurchaseOrderDto oder = getOrder();
		oder.getPoLine().get(0).setPoLineNbr(null);
		System.out.println("Order ==== " + oder.getPoLine().get(0));
		kafkaTemplate.send("po-download", oder).get();
		// Just holding the process for 3 seconds
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(3, TimeUnit.SECONDS);
		// then
		verify(poConsumerSpy, times(1)).downloadPo(isA(ConsumerRecord.class));
		verify(poServiceSpy, times(2)).createPurchaseOrder(isA(PurchaseOrderDto.class));
		verify(poConsumerSpy, times(1)).retryDownloadPo(isA(ConsumerRecord.class));
	}

}
