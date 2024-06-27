package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.QAlbumSearchResponse;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.pickle.domain.album.domain.QAlbum.album;
import static com.api.pickle.domain.bookmark.domain.QBookmark.bookmark;
import static com.api.pickle.domain.participant.domain.QParticipant.participant;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Bookmark> findAllByMemberId(Long memberId){
        return queryFactory
                .selectFrom(bookmark)
                .join(bookmark.participant, participant)
                .where(participant.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<AlbumSearchResponse> findAlbumByBookmarks(List<Bookmark> bookmarks){
        return queryFactory
                .select(new QAlbumSearchResponse(
                        album.id,
                        album.name,
                        album.status.stringValue()
                ))
                .from(bookmark)
                .join(bookmark.participant, participant)
                .join(participant.album, album)
                .where(bookmark.in(bookmarks))
                .fetch();
    }
}
