package com.atlas.scheduler.purchaseorder.infrastructure;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.service.IPurchaseOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj 
 * 
 *  This is to Interact with the third party system via Kafka Messaging Broker to download the Purchase orders
 */
@Component
@Slf4j
public class PoDownload {

	@Autowired
	private KafkaTemplate<Integer, PurchaseOrderDto> kafkaTemplate;

	@Autowired
	private IPurchaseOrderService poService;

	@Value("${po.download.retry.topic}")
	private String poDownloadRetryTopic;

	/**
	 * Listener is to download the purchase orders from the ${po.download.topic}
	 * topic & trigger the Persistance that in the Database
	 * 
	 * If it is a failure scenario while processing the message it will be posted to
	 * retry topic "${po.download.retry.topic}" to reprocess the persistance
	 * 
	 * @param order
	 */
	@KafkaListener(topics = "${po.download.topic}", groupId = "${po.download.consumer.group}")
	public void downloadPo(ConsumerRecord<Integer, PurchaseOrderDto> order) {
		try {
			log.debug("Po Download Message Received for PO {} & Partition{}", order.key(), order.partition());
			if (order.value() != null) {
				log.debug("Po is getting processed ");
				poService.createPurchaseOrder(order.value());
			}
		} catch (Exception e) {
			log.error("Exception while processing po download for po# {}  ", order.key(), e);
			order.value().setFailureReason(e.getLocalizedMessage());
			log.error("Error Po# {} is getting posted to retry topic  ", order.key());
			kafkaTemplate.send(poDownloadRetryTopic, order.key(), order.value());
		}
	}

	/**
	 * Listener is to process the messages from "${po.download.retry.topic}"
	 * topic(Basically reprocessing the failure messages of main topic)
	 * 
	 * @param order
	 * @throws Exception 
	 */
	@KafkaListener(topics = "${po.download.retry.topic}", groupId = "${po.download.consumer.group}", containerFactory = "kafkaListenerRetryContainerFactory")
	public void retryDownloadPo(ConsumerRecord<Integer, PurchaseOrderDto> order) throws Exception {
		try {

			log.debug("Retry the processing for Po# {}", order.key());
			if (order.value() != null) {
				poService.createPurchaseOrder(order.value());
			}
		} catch (Exception e) {
			log.error("Exception while processing in retry for  Po# {}  Exception {} ", order.key(), e);
			throw e;
		}
	}

}
