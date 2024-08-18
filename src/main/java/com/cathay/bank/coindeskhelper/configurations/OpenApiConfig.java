package com.cathay.bank.coindeskhelper.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Coin Desk Helper API", version = "v1"))
public class OpenApiConfig {
    
    @Bean
    OpenAPI commonOpenAPI() {
        Info info = new Info();
        info.setTitle("Cathay Bank Coin Desk Helper");
        info.setVersion("1.0.0");
        info.description("Cathay bank coin desk helper for interview");
        return new OpenAPI().info(info);
    }

}
