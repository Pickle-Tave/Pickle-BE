package com.api.pickle.global.config.feign;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableFeignClients(basePackages = "com.api.pickle.infra.config.feign")
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String contentType = template.headers()
                    .getOrDefault("Content-Type", Collections.emptyList())
                    .stream()
                    .findFirst()
                    .orElse("");
            if (contentType.equals("application/json")) {
                template.header("Content-Type", "application/json;charset=utf-8");
            } else {
                template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            }
        };
    }
}
