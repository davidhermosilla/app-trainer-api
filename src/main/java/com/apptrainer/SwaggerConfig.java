package com.apptrainer;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.apptrainer.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}
	
	private springfox.documentation.service.ApiInfo getApiInfo() {
		return new ApiInfo(
				"App Trainer Management API",
				"API to manage athletes and trainings",
				"1.0",
				"http://apptrainer/terms",
				new Contact("AppTrainer", "https://apptrainer.com", "apis@apptrainer.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}