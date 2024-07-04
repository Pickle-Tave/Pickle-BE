package com.api.pickle.domain.membertag.dao;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.membertag.domain.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {

    List<MemberTag> findAllByMemberId(Long memberId);

    @Query("select mt from MemberTag mt where mt.member = :member and mt.tag.id = :tagId")
    Optional<MemberTag> findByMemberAndTagId(@Param("member") Member member, @Param("tagId") Long tagId);
}
