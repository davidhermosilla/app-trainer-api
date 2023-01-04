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
				.apis(RequestHandlerSelectors.basePackage(AppTrainerMessages.getString("SwaggerConfig.package"))) //$NON-NLS-1$
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}
	
	private springfox.documentation.service.ApiInfo getApiInfo() {
		return new ApiInfo(
				AppTrainerMessages.getString("SwaggerConfig.api"), //$NON-NLS-1$
				AppTrainerMessages.getString("SwaggerConfig.descriptionAPI"), //$NON-NLS-1$
				AppTrainerMessages.getString("SwaggerConfig.apiVersion"), //$NON-NLS-1$
				AppTrainerMessages.getString("SwaggerConfig.terms"), //$NON-NLS-1$
				new Contact(AppTrainerMessages.getString("SwaggerConfig.appName"), AppTrainerMessages.getString("SwaggerConfig.appUrl"), AppTrainerMessages.getString("SwaggerConfig.appEmail")), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AppTrainerMessages.getString("SwaggerConfig.license"), //$NON-NLS-1$
				AppTrainerMessages.getString("SwaggerConfig.licenseUrl"), //$NON-NLS-1$
				Collections.emptyList()
				);
	}
}