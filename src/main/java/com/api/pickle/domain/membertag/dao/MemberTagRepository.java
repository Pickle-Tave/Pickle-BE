package com.api.pickle.domain.membertag.dao;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.membertag.domain.MemberTag;
import com.api.pickle.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {

    List<MemberTag> findAllByMemberId(Long memberId);

    Optional<MemberTag> findByMemberAndTag(Member member, Tag tag);
}
