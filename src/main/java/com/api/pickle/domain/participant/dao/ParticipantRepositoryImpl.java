package com.api.pickle.domain.participant.dao;

import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.domain.Participant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.api.pickle.domain.album.domain.QAlbum.album;
import static com.api.pickle.domain.participant.domain.QParticipant.participant;

@RequiredArgsConstructor
public class ParticipantRepositoryImpl implements ParticipantRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Participant> findParticipant(Member member, Long albumId) {

        Participant findMember = queryFactory.selectFrom(participant)
                .join(participant.album, album).fetchJoin()
                .where(participant.member.eq(member)
                        .and(album.id.eq(albumId)))
                .fetchOne();

        return Optional.ofNullable(findMember);
    }
}