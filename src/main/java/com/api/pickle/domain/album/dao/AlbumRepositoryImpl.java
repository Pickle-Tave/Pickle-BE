package com.api.pickle.domain.album.dao;
import com.api.pickle.domain.album.domain.SharingStatus;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.QAlbumSearchResponse;
import com.api.pickle.domain.participant.domain.QParticipant;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
import static com.api.pickle.domain.album.domain.QAlbum.album;

@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(Long memberId, String keyword) {
        return albumsFilter(
                memberId,
                album.name.containsIgnoreCase(keyword),
                ErrorCode.ALBUM_KEYWORD_NOT_FOUND
        );
    }

    @Override
    public List<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(Long memberId, String albumStatus) {
        return albumsFilter(
                memberId,
                album.status.eq(SharingStatus.valueOf(albumStatus)),
                ErrorCode.ALBUM_STATUS_NOT_FOUND
        );
    }

    @Override
    public List<AlbumSearchResponse> findAllAlbumOfMemberByCreatedDateDesc(Long memberId) {
        return albumsFilter(
                memberId,
                null,
                ErrorCode.ALBUM_NOT_EXISTS
        );
    }

    private List<AlbumSearchResponse> albumsFilter(Long memberId, BooleanExpression condition, ErrorCode errorCode) {
        List<AlbumSearchResponse> results = queryFactory
                .select(new QAlbumSearchResponse(
                        album.id,
                        album.name,
                        album.status.stringValue()))
                .from(QParticipant.participant)
                .join(QParticipant.participant.album, album)
                .where(QParticipant.participant.member.id.eq(memberId). and(condition))
                .orderBy(album.createdDate.desc())
                .fetch();

        if (results.isEmpty()) {
            throw new CustomException(errorCode);
        }

        return results;
    }
}