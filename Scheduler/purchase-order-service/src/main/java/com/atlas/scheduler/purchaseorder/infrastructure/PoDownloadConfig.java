package com.atlas.scheduler.purchaseorder.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.infrastructure.PoDeserializerFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@EnableRetry
@Slf4j
public class PoDownloadConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${po.download.payload.type}")
	private String payloadType;

	@Bean
	public Map<String, Object> configs() {
		Map<String, Object> poDownloadConsumerProps = new HashMap<>();
		poDownloadConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		poDownloadConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		poDownloadConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		poDownloadConsumerProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS,
				IntegerDeserializer.class.getName());
		poDownloadConsumerProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
				PoDownloadDeserializer.class.getName());
		poDownloadConsumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return poDownloadConsumerProps;
	}

	@Bean
	public Map<String, Object> retryConfigs() {
		Map<String, Object> retryProps = new HashMap<>();
		retryProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		retryProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
		retryProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PoDownloadDeserializer.class.getName());
		retryProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return retryProps;
	}

	@Bean
	public ConsumerFactory<Integer, PurchaseOrderDto> poConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(configs(), new IntegerDeserializer(), getDeserializer(payloadType));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrderDto> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrderDto> poDownloadfactory = new ConcurrentKafkaListenerContainerFactory<>();
		poDownloadfactory.setConsumerFactory(poConsumerFactory());
		poDownloadfactory.getContainerProperties().setAckMode(AckMode.RECORD);
		poDownloadfactory.setErrorHandler((exception, data) -> {
			log.error("Error in processing po download message with Exception {} and the record is {}", exception,
					data);
		});
		return poDownloadfactory;
	}

	@Bean
	public ConsumerFactory<Integer, PurchaseOrderDto> poRetryConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(retryConfigs(), new IntegerDeserializer(),
				getDeserializer(payloadType));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrderDto> kafkaListenerRetryContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, PurchaseOrderDto> poDownloadRetryfactory = new ConcurrentKafkaListenerContainerFactory<>();
		poDownloadRetryfactory.setConsumerFactory(poRetryConsumerFactory());
		poDownloadRetryfactory.setRetryTemplate(poDownloadRetryTopicPolicy());
		poDownloadRetryfactory.setRecoveryCallback(retryContext -> {
			ConsumerRecord<Integer, PurchaseOrderDto> consumerRecord = (ConsumerRecord<Integer, PurchaseOrderDto>) retryContext
					.getAttribute("record");
			log.info("------Recovery is called for message----- {} ", consumerRecord.value());
			return Optional.empty();
		});
		return poDownloadRetryfactory;
	}

	public RetryTemplate poDownloadRetryTopicPolicy() {
		RetryTemplate retryTemplate = new RetryTemplate();
		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(10000);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(4);
		retryTemplate.setRetryPolicy(retryPolicy);
		return retryTemplate;
	}

	@Bean
	public PoDownloadDeserializer getDeserializer(String payLoad) {
		PoDeserializerFactory deserializerFactory = new PoDeserializerFactory();
		return new PoDownloadDeserializer(deserializerFactory, payLoad);

	}

}
