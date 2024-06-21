package com.api.pickle.domain.participant.dao;

import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByMemberAndAlbum(Member member, Album album);
}
