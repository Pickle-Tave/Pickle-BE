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
    MISSING_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 정보가 존재하지 않습니다."),

    AUTH_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "시큐리티 인증 정보를 찾을 수 없습니다."),


    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다."),

    ALBUM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 앨범을 찾을 수 없습니다"),
    NOT_ALBUM_OWNER(HttpStatus.BAD_REQUEST, "해당 앨범의 소유자가 아닙니다."),

    ALREADY_SHARED_ALBUM(HttpStatus.BAD_REQUEST, "이미 공유된 앨범입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
