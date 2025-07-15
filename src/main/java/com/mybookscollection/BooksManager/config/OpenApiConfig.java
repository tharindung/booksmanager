package com.mybookscollection.BooksManager.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() { //Defines a Bean of type OpenAPI for SpringBoot and SpringDoc to be picked up for generating custom API documentation
        return new OpenAPI() //Creates a new OpenAPI and sets its Info block
            .info(new Info()
                .title("Books Manager APIs")
                .version("1.0.0")
                .description("REST API documentation for BooksManager Project.")
                //.termsOfService("http://example.com/terms")
                .contact(new Contact() //Sets Contact info for API owner
                    .name("Tharindu Gardiyawasam")
                    .email("tngardiya@gmail.com")
                    .url("https://github.com/tharindung"))
                .license(new License() //Sets License info
                    .name("Apache 2.0")
                    .url("http://springdoc.org")));
    }
}