package com.api.pickle.domain.member.dao;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.OauthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthInfo(OauthInfo oauthInfo);
}
