package com.atlas.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sivaraj
 * Purchase Order Service to Process with Purchase Order Domain 
 */
@SpringBootApplication()
@EnableEurekaClient
@EnableSwagger2
public class PurchaseOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseOrderServiceApplication.class, args);
	}

}
