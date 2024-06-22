package com.api.pickle.domain.tag.application;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.membertag.dao.MemberTagRepository;
import com.api.pickle.domain.membertag.domain.MemberTag;
import com.api.pickle.domain.membertag.dto.MemberTagResponse;
import com.api.pickle.domain.tag.dao.TagRepository;
import com.api.pickle.domain.tag.domain.Tag;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final MemberUtil memberUtil;
    private final MemberTagRepository memberTagRepository;
    private final TagRepository tagRepository;

    public void createMemberHashTag(String name) {
        final Member currentMember = memberUtil.getCurrentMember();
        List<MemberTag> memberTagList = memberTagRepository.findAllByMemberId(currentMember.getId());
        validateTagSize(memberTagList);
        validateAlreadyExist(name, memberTagList);
        Tag hashTag = Tag.createTag(name);
        MemberTag memberTag = MemberTag.createMemberTag(currentMember,hashTag);

        tagRepository.save(hashTag);
        memberTagRepository.save(memberTag);
    }

    public List<MemberTagResponse> showMemberHashTag() {
        final Member currentMember = memberUtil.getCurrentMember();
        List<MemberTag> memberTagList = memberTagRepository.findAllByMemberId(currentMember.getId());
        return memberTagList.stream()
                .map(memberTag -> MemberTagResponse.builder()
                        .id(memberTag.getTag().getId())
                        .text(memberTag.getTag().getName()).build())
                .collect(Collectors.toList());

    }

    private void validateAlreadyExist(String name, List<MemberTag> memberTagList) {
        for (MemberTag memberTag : memberTagList) {
            if (name.equals(memberTag.getTag().getName())) {
                throw new CustomException(ErrorCode.HASHTAG_ALREADY_EXIST);
            }
        }
    }
    private void validateTagSize(List<MemberTag> memberTagList) {
        if (memberTagList.size() == 5) {
            throw new CustomException(ErrorCode.EXCEED_HASHTAG_NUMBER);
        }
    }
}
