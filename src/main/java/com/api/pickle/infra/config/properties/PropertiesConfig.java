package com.api.pickle.infra.config.properties;

import com.api.pickle.infra.config.jwt.JwtProperties;
import com.api.pickle.infra.config.oauth.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({KakaoProperties.class, JwtProperties.class})
@Configuration
public class PropertiesConfig {}
