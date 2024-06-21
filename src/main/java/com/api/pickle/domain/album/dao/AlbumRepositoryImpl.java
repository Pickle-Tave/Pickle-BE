package com.api.pickle.domain.album.dao;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.QAlbumSearchResponse;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
import static com.api.pickle.domain.album.domain.QAlbum.album;

@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(String keyword) {
        List<AlbumSearchResponse> results = queryFactory
                .select(new QAlbumSearchResponse(
                        album.id,
                        album.name,
                        album.status.stringValue()))
                .from(album)
                .where(
                        album.name.containsIgnoreCase(keyword)
                )
                .orderBy(album.createdDate.desc())
                .fetch();

        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.ALBUM_KEYWORD_NOT_FOUND);
        }

        return results;
    }
}