package com.api.pickle.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),

    ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "ID 토큰 검증에 실패했습니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
