package com.api.pickle.infra.config.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws")
@Getter
public class S3Properties {

    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
    private String endpoint;
}
