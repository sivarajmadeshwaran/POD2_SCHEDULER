package com.atlas.scheduler.purchaseorder.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;

/**
 * @author sivaraj
 *  This is  to set the producer configs to send the message to retry topic
 */
@Configuration
public class PoDownloadRetryProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${po.download.payload.type}")
	private String payloadType;

	public Map<String, Object> producerConfig() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PoDownloadSerializer.class.getName());
		return configs;
	}

	@Bean
	public ProducerFactory<Integer, PurchaseOrderDto> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig(),new IntegerSerializer(),new PoDownloadSerializer(payloadType));
	}

	@Bean
	public KafkaTemplate<Integer, PurchaseOrderDto> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
