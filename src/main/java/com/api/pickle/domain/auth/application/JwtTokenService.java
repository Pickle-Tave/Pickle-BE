package com.api.pickle.domain.auth.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.auth.domain.RefreshToken;
import com.api.pickle.domain.member.domain.MemberRole;
import com.api.pickle.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessToken(Long memberId, MemberRole role) {
        return jwtUtil.generateAccessToken(memberId, role);
    }

    public String createRefreshToken(Long memberId) {
        String token = jwtUtil.generateRefreshToken(memberId);
        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).token(token).build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }
}
