package com.atlas.scheduler.purchaseorder.infrastructure;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Value;

import com.atlas.scheduler.purchaseorder.PurchaseOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PoDownloadDeserializer implements Deserializer<PurchaseOrder> {

	@Value("${po-download-payload-type}")
	private String payloadType;

	@Override
	public PurchaseOrder deserialize(String topic, byte[] data) {
		System.out.println("Received data for deserialization from topic " + topic);
		ObjectMapper mapper = null;
		PurchaseOrder po = null;
		if (payloadType.equalsIgnoreCase("XML")) {
			mapper = new XmlMapper();
			try {
				po = mapper.readValue(data, PurchaseOrder.class);
				System.out.println("Deserialized for XMl --- " + po.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			mapper = new ObjectMapper();
			try {
				po = mapper.readValue(data, PurchaseOrder.class);
				System.out.println("Deserialized for JSOn--- " + po.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return po;
	}

}
