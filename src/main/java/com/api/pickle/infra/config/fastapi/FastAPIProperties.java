package com.api.pickle.infra.config.fastapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastapi")
@Getter
@Setter
public class FastAPIProperties {
    private String serverUrl;
    private String endpoint;
}
