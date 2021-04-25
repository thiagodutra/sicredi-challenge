package com.github.thiagodutra.coopvoteservice.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.thiagodutra"))
            .build()
            .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
            "Coop Vote Service", 
            "Simple API for creating Voting polls", 
            "1.0", 
            "Free to use", 
            new Contact("Thiago Dutra", "http://www.github.com/thiagodutra", "thiagodutra@gmail.com"), 
            "Api License", 
            "Some URL", Collections.emptyList()
            );
    }
}
