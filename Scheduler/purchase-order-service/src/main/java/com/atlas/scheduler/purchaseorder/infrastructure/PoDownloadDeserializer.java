package com.atlas.scheduler.purchaseorder.infrastructure;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.infrastructure.PoDeserializerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj
 *
 *         This is to deserialize the PurchaseOrderDto according to the content
 *         type passed by client(XML/JSON)
 */
@Slf4j
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PoDownloadDeserializer implements Deserializer<PurchaseOrderDto> {

	@Autowired
	private PoDeserializerFactory deserializerFactory;

	private String payloadType;

	@Override
	public PurchaseOrderDto deserialize(String topic, byte[] data) {
		log.info("Received data for deserialization from topic ", topic);
		ObjectMapper mapper = null;
		PurchaseOrderDto po = null;
		mapper = deserializerFactory.getMapper(payloadType);
		try {
			po = mapper.readValue(data, PurchaseOrderDto.class);
			log.info("Deserialized for  payloadType:: {} Content :: {} ", payloadType, po.toString());
		} catch (Exception e) {
			log.error("Error while Deserializing the Message ", e);
		}
		return po;
	}

}
