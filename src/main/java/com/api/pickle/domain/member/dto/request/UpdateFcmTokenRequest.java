package com.api.pickle.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateFcmTokenRequest {

    @Schema(description = "FCM 토큰")
    private String fcmToken;
}
