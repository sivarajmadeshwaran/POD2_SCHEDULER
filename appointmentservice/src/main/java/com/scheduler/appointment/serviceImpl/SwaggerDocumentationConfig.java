package com.scheduler.appointment.serviceImpl;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerDocumentationConfig {

	 ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Appointment Service")
	                .description("API to Get the Dc Master Data setup Domain Related Details").license("")
	                .licenseUrl("http://atlas-casestudy.com").termsOfServiceUrl("").version("1.0.0")
	                .contact(new Contact("Atlas_Dev", "", "atlas_dev@cognizant.com")).build();
	        }

	 

	    @Bean
	    public Docket swaggerConfig() {
	        return new Docket(DocumentationType.SWAGGER_2).select()
	                .apis(RequestHandlerSelectors.basePackage("com.scheduler.controller")).build()
	                .apiInfo(apiInfo());
	        }
}
