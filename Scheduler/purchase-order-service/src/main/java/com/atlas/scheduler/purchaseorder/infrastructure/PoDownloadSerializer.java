package com.atlas.scheduler.purchaseorder.infrastructure;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj
 *  This is used to serialize the PurchaseOrderDto 
 */
@Slf4j
public class PoDownloadSerializer implements Serializer<PurchaseOrderDto> {

	@Value("${po.download.payload.type}")
	private String payloadType;
	
	public PoDownloadSerializer(String payloadType) {
		this.payloadType=payloadType;
	}

	@Override
	public byte[] serialize(String topic, PurchaseOrderDto data) {
		byte[] retVal = null;
		ObjectMapper objectMapper = null;
		
		if (payloadType != null && payloadType.equalsIgnoreCase("XML")) {
			objectMapper = new XmlMapper();
		} else {
			objectMapper = new ObjectMapper();
		}
		try {
			retVal = objectMapper.writeValueAsString(data).getBytes();
		} catch (Exception e) {
			log.error("Exception while Serializing " ,e);
		}
		return retVal;
	}

}