package com.api.pickle.domain.member.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Embeddable
@Getter
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;
    private String profile;

    @Builder
    public OauthInfo(String oauthId, String oauthProvider, String oauthEmail, String profile) {
        this.oauthId = oauthId;
        this.profile = profile;
    }

    public static OauthInfo from(OidcUser user) {
        return OauthInfo.builder()
                .oauthId(user.getSubject())
                .profile(user.getAttributes().get("picture").toString())
                .build();
    }
}
