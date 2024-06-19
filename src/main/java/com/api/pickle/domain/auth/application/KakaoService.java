package com.api.pickle.domain.auth.application;

import com.api.pickle.domain.auth.dto.request.KakaoTokenRequest;
import com.api.pickle.domain.auth.dto.response.KakaoTokenResponse;
import com.api.pickle.infra.config.feign.KakaoLoginClient;
import com.api.pickle.infra.config.oauth.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoProperties properties;

    public KakaoTokenResponse getIdToken(String code){
        return kakaoLoginClient.getToken(KakaoTokenRequest.newInstance(properties, code).toString());
    }
}
