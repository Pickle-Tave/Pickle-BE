package com.api.pickle.domain.participant.dao;

import com.api.pickle.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
