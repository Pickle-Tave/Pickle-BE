package com.api.pickle.infra.config.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao.client")
@Getter
@Setter
public class KakaoProperties {
    private String id;
    private String secret;
    private String redirectUri;
    private String grantType;
}
