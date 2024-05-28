package com.api.pickle.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshRequest {
    @Schema(description = "갱신을 위한 리프레시 토큰")
    private String refreshToken;
}
