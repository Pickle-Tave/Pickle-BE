package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.domain.bookmark.domain.QBookmark;
import com.api.pickle.domain.participant.domain.QParticipant;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Bookmark findByAlbumIdAndMemberId(Long albumId, Long memberId) {
        Bookmark result = queryFactory
                .selectFrom(QBookmark.bookmark)
                .join(QBookmark.bookmark.participant, QParticipant.participant)
                .where(QParticipant.participant.album.id.eq(albumId)
                        .and(QParticipant.participant.member.id.eq(memberId)))
                .fetchOne();

        if (result == null){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
