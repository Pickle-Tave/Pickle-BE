package com.api.pickle.domain.participant.dao;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.domain.Participant;

import java.util.Optional;

public interface ParticipantRepositoryCustom {
    Optional<Participant> findParticipant(Member currentMember, Long albumId);
}
