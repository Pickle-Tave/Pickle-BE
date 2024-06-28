package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> , BookmarkRepositoryCustom {
    Optional<Bookmark> findByParticipant(Participant participant);
}
