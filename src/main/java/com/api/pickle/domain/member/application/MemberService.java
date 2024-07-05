package com.api.pickle.domain.member.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.dto.request.UpdateFcmTokenRequest;
import com.api.pickle.domain.member.dto.response.MyPageResponse;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberUtil memberUtil;;

    public void memberLogout(){
        final Member currentMember = memberUtil.getCurrentMember();
        refreshTokenRepository.findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);
    }

    public void memberWithdrawal(){
        final Member currentMember = memberUtil.getCurrentMember();
        refreshTokenRepository.findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);
        currentMember.withdrawal();
    }

    public MyPageResponse getMemberMyPageInfo() {
        final Member currentMember = memberUtil.getCurrentMember();
        return MyPageResponse.builder()
                .nickname(currentMember.getNickname())
                .oauthInfo(currentMember.getOauthInfo())
                .role(currentMember.getRole())
                .status(currentMember.getStatus())
                .build();
    }

    public void toggleAppAlarm() {
        final Member currentMember = memberUtil.getCurrentMember();
        currentMember.toggleAppAlarmState(currentMember.getFcmInfo());
    }

    public void updateFcmToken(UpdateFcmTokenRequest updateFcmTokenRequest) {
        final Member currentMember = memberUtil.getCurrentMember();
        currentMember.updateFcmToken(currentMember.getFcmInfo(), updateFcmTokenRequest.getFcmToken());
    }
}
