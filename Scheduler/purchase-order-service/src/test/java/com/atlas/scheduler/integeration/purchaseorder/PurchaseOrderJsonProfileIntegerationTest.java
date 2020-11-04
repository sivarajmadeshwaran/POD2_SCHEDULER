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
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("testJson")
@TestMethodOrder(OrderAnnotation.class)
class PurchaseOrderJsonProfileIntegerationTest {

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
		PurchaseOrderDto order1 = new PurchaseOrderDto();
		order.setAddress("Erode");
		order.setPoDate(new Date());
		order.setPoNbr(4567);
		order.setVendorNbr(1);
		order1.setAddress("Erode");
		order1.setPoDate(new Date());
		order1.setPoNbr(4567);
		order1.setVendorNbr(1);
		List<PurchaseOrderDto> dtos = new ArrayList<>();
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
	@DisplayName("Po Download for JSON ContentType Scenario")
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
		assertEquals(true, poList.stream().anyMatch(p -> p.getPoNbr() == 4567));
	}

}
