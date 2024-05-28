package com.api.pickle.domain.auth.api;

import com.api.pickle.domain.auth.application.AuthService;
import com.api.pickle.domain.auth.dto.request.AuthCodeRequest;
import com.api.pickle.domain.auth.dto.request.RefreshRequest;
import com.api.pickle.domain.auth.dto.response.TokenPairResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "인증 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입 및 로그인", description = "회원가입 및 로그인을 진행합니다.")
    @PostMapping("/login")
    public TokenPairResponse memberOauthLogin(@RequestBody AuthCodeRequest request){
        return authService.socialLogin(request.getCode());
    }

    @Operation(summary = "토큰 재발급", description = "엑세스 토큰 및 리프테시 토큰을 모두 재발급합니다.")
    @PostMapping("/refresh")
    public TokenPairResponse refreshToken(@RequestBody RefreshRequest request){
        return authService.refreshToken(request);
    }
}
