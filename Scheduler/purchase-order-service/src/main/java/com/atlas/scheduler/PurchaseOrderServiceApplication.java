package com.atlas.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication()
@EnableEurekaClient
public class PurchaseOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseOrderServiceApplication.class, args);
	}

}
