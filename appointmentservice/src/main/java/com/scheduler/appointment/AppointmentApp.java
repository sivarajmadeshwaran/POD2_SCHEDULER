package com.scheduler.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Appointment creation service!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.scheduler.*")
@EntityScan(basePackages="com.scheduler.*")
@EnableJpaRepositories("com.scheduler.*")
//@EnableFeignClients
//@EnableEurekaClient
public class AppointmentApp extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
        SpringApplication.run(AppointmentApp.class, args);
    }
}
