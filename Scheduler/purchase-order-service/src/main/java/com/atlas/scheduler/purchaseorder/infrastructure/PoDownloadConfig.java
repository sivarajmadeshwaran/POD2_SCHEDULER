package com.atlas.scheduler.purchaseorder.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.atlas.scheduler.purchaseorder.PurchaseOrder;

@Configuration
@EnableKafka
public class PoDownloadConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> configs() {
		Map<String, Object> poDownloadConsumerProps = new HashMap<>();
		poDownloadConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		poDownloadConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
		poDownloadConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				PoDownloadDeserializer.class.getName());
		poDownloadConsumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return poDownloadConsumerProps;
	}

	@Bean
	public ConsumerFactory<Integer, PurchaseOrder> poConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(configs(), new IntegerDeserializer(),
				new PoDownloadDeserializer());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrder> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrder> poDownloadfactory = new ConcurrentKafkaListenerContainerFactory<>();
		poDownloadfactory.setConsumerFactory(poConsumerFactory());
		return poDownloadfactory;
	}

}
