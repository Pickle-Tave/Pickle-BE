package com.api.pickle.domain.member.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    @Embedded private OauthInfo oauthInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String nickname, OauthInfo oauthInfo, MemberRole role, MemberStatus status) {
        this.nickname = nickname;
        this.oauthInfo = oauthInfo;
        this.role = role;
        this.status = status;
    }

    public static Member createNormalMember(OauthInfo oauthInfo, String nickname) {
        return Member.builder()
                .nickname(nickname)
                .role(MemberRole.USER)
                .status(MemberStatus.NORMAL)
                .oauthInfo(oauthInfo)
                .build();
    }
}
