package com.api.pickle.domain.auth.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.auth.domain.RefreshToken;
import com.api.pickle.domain.auth.dto.AccessTokenDto;
import com.api.pickle.domain.auth.dto.RefreshTokenDto;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.MemberRole;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
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

    public void setAuthenticationToken(Long memberId, MemberRole role) {
        UserDetails userDetails =
                User.withUsername(memberId.toString())
                        .authorities(role.toString())
                        .password("")
                        .build();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public AccessTokenDto retrieveAccessToken(String accessTokenValue) throws ExpiredJwtException{
        try {
            return jwtUtil.parseAccessToken(accessTokenValue);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public RefreshTokenDto validateRefreshToken(String refreshToken){
        return jwtUtil.parseRefreshToken(refreshToken);
    }

    public RefreshTokenDto refreshRefreshToken(RefreshTokenDto oldRefreshTokenDto){
        RefreshToken refreshToken = refreshTokenRepository.findById(oldRefreshTokenDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MISSING_JWT_TOKEN));
        RefreshTokenDto refreshTokenDto = jwtUtil.generateRefreshTokenDto(refreshToken.getMemberId());
        refreshToken.updateRefreshToken(refreshTokenDto.getToken());
        refreshTokenRepository.save(refreshToken);
        return refreshTokenDto;
    }

    public AccessTokenDto refreshAccessToken(Member member){
        return jwtUtil.generateAccessTokenDto(member.getId(), member.getRole());
    }
}
