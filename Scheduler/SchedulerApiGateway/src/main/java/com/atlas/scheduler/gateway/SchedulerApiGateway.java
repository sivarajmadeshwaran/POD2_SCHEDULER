package com.atlas.scheduler.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atlas.scheduler.gateway.configuration.JWTUtils;

/**
 * @author sivaraj
 * 
 * This application acts as API gateway for TruckBooking Services
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class SchedulerApiGateway {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApiGateway.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // For encrypting user password
	}
	
	
	@Bean
	public JWTUtils jwtUtils() {
		return new JWTUtils();
	}
}
