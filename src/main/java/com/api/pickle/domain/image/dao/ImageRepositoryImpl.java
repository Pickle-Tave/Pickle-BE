package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.album.dto.response.FetchAlbumImagesResponse;
import com.api.pickle.domain.album.dto.response.QFetchAlbumImagesResponse;
import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.api.pickle.domain.image.domain.QImage.image;
import static com.api.pickle.domain.imagetag.domain.QImageTag.imageTag;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Image> findByImageUrls(List<String> imageUrl) {
        return queryFactory
                .selectFrom(image)
                .where(image.imageUrl.in(imageUrl))
                .fetch();
    }

    @Override
    public Slice<FetchAlbumImagesResponse> findAllImagesByCreatedDateDesc(Long albumId, int pageSize, Long lastImageId) {
        List<FetchAlbumImagesResponse> results = queryFactory
                .select(new QFetchAlbumImagesResponse(
                        imageTag.image.id,
                        imageTag.tag.name,
                        imageTag.image.imageUrl
                ))
                .from(imageTag)
                .where(lastImageId(lastImageId),
                        imageTag.image.album.id.eq(albumId))
                .orderBy(imageTag.image.createdDate.desc())
                .limit(pageSize + 1)
                .fetch();

        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND_IN_ALBUM);
        }

        return checkLastPage(pageSize, results);
    }

    private BooleanExpression lastImageId(Long imageId) {
        if (imageId == null) {
            return null;
        }

        return image.id.lt(imageId);
    }

    private Slice<FetchAlbumImagesResponse> checkLastPage(int pageSize, List<FetchAlbumImagesResponse> results) {

        boolean hasNext = false;

        if (results.size() > pageSize) {
            hasNext = true;
            results.remove(pageSize);
        }

        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }
}
