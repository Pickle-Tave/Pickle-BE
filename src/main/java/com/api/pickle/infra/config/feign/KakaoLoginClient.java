package com.api.pickle.infra.config.feign;

import com.api.pickle.domain.auth.dto.response.KakaoTokenResponse;
import com.api.pickle.global.common.constants.SecurityConstants;
import com.api.pickle.global.config.feign.KakaoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "kakaoLoginClient",
        url = SecurityConstants.KAKAO_LOGIN_URL,
        configuration = KakaoFeignConfig.class)
public interface KakaoLoginClient {
    @PostMapping(value = SecurityConstants.KAKAO_LOGIN_ENDPOINT)
    KakaoTokenResponse getToken(@RequestBody String KakaoTokenRequest);
}

