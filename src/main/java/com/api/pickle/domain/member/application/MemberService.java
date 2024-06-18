package com.api.pickle.domain.member.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.member.dao.MemberRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.MemberStatus;
import com.api.pickle.domain.member.domain.OauthInfo;
import com.api.pickle.domain.member.dto.response.MyPageResponse;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberUtil memberUtil;

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
        Map<String, Object> result = new HashMap<>();
        final Member currentMember = memberUtil.getCurrentMember();

        result.put("nickname",currentMember.getNickname());
        result.put("oauthInfo",currentMember.getOauthInfo());
        result.put("role",currentMember.getRole());
        result.put("status",currentMember.getStatus());

        MyPageResponse responseDto = new MyPageResponse();
        responseDto.setMemberInfo(result);
        return responseDto;
    }
}
