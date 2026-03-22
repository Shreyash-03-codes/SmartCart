package com.smartcart.ecommerce.common.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartCartOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartCart API")
                        .description("Backend API for SmartCart E-Commerce Platform")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Shreyash")
                                .email("your@email.com"))
                        .license(new License()
                                .name("Apache 2.0")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }
}
