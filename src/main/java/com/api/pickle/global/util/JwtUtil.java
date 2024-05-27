package com.api.pickle.global.util;

import com.api.pickle.domain.member.domain.MemberRole;
import com.api.pickle.infra.config.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.api.pickle.global.common.constants.SecurityConstants.TOKEN_ROLE_NAME;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long memberId, MemberRole memberRole) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        return buildAccessToken(memberId, memberRole, issuedAt, expiredAt);
    }

    public String generateRefreshToken(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());
        return buildRefreshToken(memberId, issuedAt, expiredAt);
    }

    private String buildAccessToken(
            Long memberId, MemberRole memberRole, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .claim(TOKEN_ROLE_NAME, memberRole.name())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    private String buildRefreshToken(Long memberId, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes());
    }

    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecret().getBytes());
    }
}
