package com.api.pickle.domain.auth.dto.request;

import com.api.pickle.infra.config.oauth.KakaoProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KakaoTokenRequest {

    private String code;
    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String grantType;

    public static KakaoTokenRequest newInstance(KakaoProperties properties, String code) {
        return KakaoTokenRequest.builder()
                .code(code)
                .clientId(properties.getId())
                .clientSecret(properties.getSecret())
                .redirectUri(properties.getRedirectUri())
                .grantType(properties.getGrantType())
                .build();
    }

    @Override
    public String toString() {
        return "grant_type=" + grantType +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&code=" + code +
                "&client_secret=" + clientSecret;
    }


}
