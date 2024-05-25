package com.api.pickle.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pickleApi() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components());
    }

    private Info apiInfo() {
        return new Info()
                .title("Pickle API")
                .description("Pickle API 명세서")
                .version("1.0.0");
    }
}