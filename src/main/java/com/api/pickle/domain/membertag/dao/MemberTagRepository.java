package com.api.pickle.domain.membertag.dao;

import com.api.pickle.domain.membertag.domain.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {
}
