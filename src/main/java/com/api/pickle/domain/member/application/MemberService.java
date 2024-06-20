package com.api.pickle.domain.member.application;

import com.api.pickle.domain.auth.dao.RefreshTokenRepository;
import com.api.pickle.domain.member.dao.MemberRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.dto.response.MyPageResponse;
import com.api.pickle.domain.membertag.dao.MemberTagRepository;
import com.api.pickle.domain.membertag.domain.MemberTag;
import com.api.pickle.domain.tag.dao.TagRepository;
import com.api.pickle.domain.tag.domain.Tag;
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
    private final TagRepository tagRepository;
    private final MemberTagRepository memberTagRepository;

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

    public void createMemberHashTag(String name) {
        final Member currentMember = memberUtil.getCurrentMember();
        Tag hashTag = Tag.createTag(name);
        MemberTag memberTag = MemberTag.createMemberTag(currentMember,hashTag);

        tagRepository.save(hashTag);
        memberTagRepository.save(memberTag);

    }
}
