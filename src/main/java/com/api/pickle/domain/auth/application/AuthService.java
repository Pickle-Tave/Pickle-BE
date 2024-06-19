package com.api.pickle.domain.auth.application;

import com.api.pickle.domain.auth.dto.AccessTokenDto;
import com.api.pickle.domain.auth.dto.RefreshTokenDto;
import com.api.pickle.domain.auth.dto.request.RefreshRequest;
import com.api.pickle.domain.auth.dto.response.KakaoTokenResponse;
import com.api.pickle.domain.auth.dto.response.TokenPairResponse;
import com.api.pickle.domain.member.dao.MemberRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.MemberStatus;
import com.api.pickle.domain.member.domain.OauthInfo;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
        jwtTokenService.setAuthenticationToken(member.getId(), member.getRole());
        return getLoginResponse(member);
    }

    public TokenPairResponse refreshToken(RefreshRequest request){
        RefreshTokenDto oldRefreshTokenDto = jwtTokenService.validateRefreshToken(request.getRefreshToken());

        if (oldRefreshTokenDto == null){
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
        RefreshTokenDto newRefreshTokenDto = jwtTokenService.refreshRefreshToken(oldRefreshTokenDto);
        AccessTokenDto accessTokenDto = jwtTokenService.refreshAccessToken(getMember(newRefreshTokenDto));
        return new TokenPairResponse(accessTokenDto.getToken(), newRefreshTokenDto.getToken());
    }

    private Member getMember(RefreshTokenDto refreshTokenDto){
        return memberRepository.findById(refreshTokenDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
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
        Member member =  memberRepository
                .findByOauthInfo(oauthInfo)
                .orElseGet(() -> saveMember(oauthInfo, getUserSocialName(oidcUser)));
        if (member.getStatus() == MemberStatus.DELETED){
            member.reEnroll();
        }
        return member;
    }

    private Member saveMember(OauthInfo oauthInfo, String nickname) {
        return memberRepository.save(Member.createNormalMember(oauthInfo, nickname));
    }
}
