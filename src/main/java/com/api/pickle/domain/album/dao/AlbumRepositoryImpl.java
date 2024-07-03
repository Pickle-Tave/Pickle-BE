package com.api.pickle.domain.album.dao;
import com.api.pickle.domain.album.domain.SharingStatus;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.QAlbumSearchResponse;
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
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(Long memberId, String keyword, int pageSize, Long lastAlbumId) {
        return albumsFilter(
                memberId,
                album.name.containsIgnoreCase(keyword),
                ErrorCode.ALBUM_KEYWORD_NOT_FOUND,
                pageSize,
                lastAlbumId
        );
    }

    @Override
    public Slice<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(Long memberId, String albumStatus, int pageSize, Long lastAlbumId) {
        return albumsFilter(
                memberId,
                album.status.eq(SharingStatus.valueOf(albumStatus)),
                ErrorCode.ALBUM_STATUS_NOT_FOUND,
                pageSize,
                lastAlbumId
        );
    }

    @Override
    public Slice<AlbumSearchResponse> findAllAlbumOfMemberByCreatedDateDesc(Long memberId, int pageSize, Long lastAlbumId) {
        return albumsFilter(
                memberId,
                null,
                ErrorCode.ALBUM_NOT_EXISTS,
                pageSize,
                lastAlbumId
        );
    }

    private Slice<AlbumSearchResponse> albumsFilter(Long memberId, BooleanExpression condition, ErrorCode errorCode, int pageSize, Long lastAlbumId) {
        List<AlbumSearchResponse> results = queryFactory
                .select(new QAlbumSearchResponse(
                        album.id,
                        album.name,
                        album.status.stringValue(),
                        bookmark.markStatus.stringValue()))
                .from(participant)
                .join(participant.album, album)
                .join(bookmark).on(participant.eq(bookmark.participant))
                .where(
                        lastAlbumId(lastAlbumId),
                        participant.member.id.eq(memberId). and(condition))
                .orderBy(album.createdDate.desc())
                .limit(pageSize + 1)
                .fetch();

        if (results.isEmpty()) {
            throw new CustomException(errorCode);
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