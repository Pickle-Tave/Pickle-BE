package com.api.pickle.infra.config.properties;

import com.api.pickle.infra.config.fastapi.FastAPIProperties;
import com.api.pickle.infra.config.jwt.JwtProperties;
import com.api.pickle.infra.config.oauth.KakaoProperties;
import com.api.pickle.infra.config.redis.RedisProperties;
import com.api.pickle.infra.config.s3.S3Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        S3Properties.class,
        RedisProperties.class,
        FastAPIProperties.class})
@Configuration
public class PropertiesConfig {
}
