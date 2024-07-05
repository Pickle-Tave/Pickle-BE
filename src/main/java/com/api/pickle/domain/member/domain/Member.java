package com.api.pickle.domain.member.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
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

    @Embedded
    private OauthInfo oauthInfo;

    @Embedded
    private FcmInfo fcmInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String nickname, OauthInfo oauthInfo, FcmInfo fcmInfo, MemberRole role, MemberStatus status) {
        this.nickname = nickname;
        this.oauthInfo = oauthInfo;
        this.fcmInfo = fcmInfo;
        this.role = role;
        this.status = status;
    }

    public static Member createNormalMember(OauthInfo oauthInfo, String nickname) {
        return Member.builder()
                .nickname(nickname)
                .role(MemberRole.USER)
                .status(MemberStatus.NORMAL)
                .oauthInfo(oauthInfo)
                .fcmInfo(FcmInfo.createFcmInfo())
                .build();
    }

    public void withdrawal() {
        if (this.status == MemberStatus.DELETED) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_DELETED);
        }
        this.status = MemberStatus.DELETED;
        this.fcmInfo = FcmInfo.disableAlarm(FcmInfo.createFcmInfo());
    }

    public void toggleAppAlarmState(FcmInfo fcmState) {
        fcmInfo = FcmInfo.toggleAlarm(fcmState);
    }

    public void updateFcmToken(FcmInfo fcmState, String fcmToken) {
        fcmInfo = FcmInfo.updateToken(fcmState, fcmToken);
    }

    public void reEnroll(){
        this.status = MemberStatus.NORMAL;
    }
}
