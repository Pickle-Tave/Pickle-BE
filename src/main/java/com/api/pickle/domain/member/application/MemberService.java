package com.api.pickle.domain.member.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.member.dao.MemberRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
