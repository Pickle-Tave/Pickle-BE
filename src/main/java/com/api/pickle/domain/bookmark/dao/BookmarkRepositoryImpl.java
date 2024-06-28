package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.QAlbumSearchResponse;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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
    public Slice<AlbumSearchResponse> findAlbumByBookmarks(List<Bookmark> bookmarks, int pageSize, Long lastAlbumId){
        List<AlbumSearchResponse> results =  queryFactory
                .select(new QAlbumSearchResponse(
                        album.id,
                        album.name,
                        album.status.stringValue(),
                        bookmark.markStatus.stringValue()
                ))
                .from(bookmark)
                .join(bookmark.participant, participant)
                .join(participant.album, album)
                .where(bookmark.in(bookmarks), lastAlbumId(lastAlbumId))
                .orderBy(album.createdDate.desc())
                .limit(pageSize + 1)
                .fetch();
        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.BOOKMARKED_ALBUM_NOT_FOUND);
        }

        return checkLastPage(pageSize, results);
    }

    private BooleanExpression lastAlbumId(Long albumId) {
        if (albumId == null) {
            return null;
        }

        return album.id.lt(albumId);
    }

    private Slice<AlbumSearchResponse> checkLastPage(int pageSize, List<AlbumSearchResponse> results) {

        boolean hasNext = false;

        if (results.size() > pageSize) {
            hasNext = true;
            results.remove(pageSize);
        }

        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }
}
