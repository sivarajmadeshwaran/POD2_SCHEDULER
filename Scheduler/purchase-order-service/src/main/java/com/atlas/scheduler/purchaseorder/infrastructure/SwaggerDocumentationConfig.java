package com.atlas.scheduler.purchaseorder.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Purchase Order Service")
				.description("API to Get the Purchase Order Domain Related Details").license("")
				.licenseUrl("http://atlas-casestudy.com").termsOfServiceUrl("").version("1.0.0")
				.contact(new Contact("Atlas_Dev", "", "atlas_dev@cognizant.com")).build();
	}

	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.atlas.scheduler.purchaseorder.api")).build()
				.apiInfo(apiInfo());
	}

}
