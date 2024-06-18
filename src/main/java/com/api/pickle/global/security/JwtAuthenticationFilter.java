package com.api.pickle.global.security;

import com.api.pickle.domain.auth.application.JwtTokenService;
import com.api.pickle.domain.auth.dto.AccessTokenDto;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.api.pickle.global.common.constants.SecurityConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenHeaderValue = extractAccessTokenFromHeader(request);

        if (accessTokenHeaderValue != null){
            try {
                AccessTokenDto accessTokenDto = jwtTokenService.retrieveAccessToken(accessTokenHeaderValue);
                if (accessTokenDto != null){
                    jwtTokenService.setAuthenticationToken(accessTokenDto.getMemberId(), accessTokenDto.getMemberRole());
                }
            } catch (ExpiredJwtException e) {
                throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)){
            return header.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
