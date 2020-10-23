package com.atlas.scheduler.purchaseorder.infrastructure;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sivaraj
 *  This is to get the Deserializer according to the content type processed by client
 */
@Component
@Slf4j
public class PoDeserializerFactory {

	public static final String XML_CONTENT = "XML";
	public static final String JSON_CONTENT = "JSON";

	public ObjectMapper getMapper(String contentType) {

		ObjectMapper mapper = null;

		switch (contentType) {
		case XML_CONTENT:
			mapper = new XmlMapper();
			break;
		case JSON_CONTENT:
			mapper = new ObjectMapper();
			break;
		default:
			log.info("Default Deserializer  type is for JSON");
			mapper = new ObjectMapper();
			break;
		}

		return mapper;
	}

}
