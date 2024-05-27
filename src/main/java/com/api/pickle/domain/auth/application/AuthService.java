package com.api.pickle.domain.auth.application;

import com.api.pickle.domain.auth.dto.response.KakaoTokenResponse;
import com.api.pickle.domain.auth.dto.response.TokenPairResponse;
import com.api.pickle.domain.member.dao.MemberRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.MemberRole;
import com.api.pickle.domain.member.domain.OauthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final KakaoService kakaoService;
    private final IdTokenVerifier idTokenVerifier;
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;

    public TokenPairResponse socialLogin(String authCode){
        KakaoTokenResponse response = kakaoService.getIdToken(authCode);
        OidcUser oidcUser = idTokenVerifier.getOidcUser(response.getId_token());
        OauthInfo oauthInfo = OauthInfo.from(oidcUser);

        Member member = getMemberByOidcInfo(oidcUser, oauthInfo);
        setAuthenticationToken(member.getId(), member.getRole());
        return getLoginResponse(member);
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

    private TokenPairResponse getLoginResponse(Member member) {
        String accessToken = jwtTokenService.createAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenService.createRefreshToken(member.getId());
        return new TokenPairResponse(accessToken, refreshToken);
    }

    private String getUserSocialName(OidcUser oidcUser) {
        return oidcUser.getClaim("nickname");
    }

    private Member getMemberByOidcInfo(OidcUser oidcUser, OauthInfo oauthInfo) {
        return memberRepository
                .findByOauthInfo(oauthInfo)
                .orElseGet(() -> saveMember(oauthInfo, getUserSocialName(oidcUser)));
    }

    private Member saveMember(OauthInfo oauthInfo, String nickname) {
        return memberRepository.save(Member.createNormalMember(oauthInfo, nickname));
    }
}
