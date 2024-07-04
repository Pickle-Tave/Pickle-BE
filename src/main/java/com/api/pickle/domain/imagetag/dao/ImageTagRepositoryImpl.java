package com.api.pickle.domain.imagetag.dao;

import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.domain.imagetag.domain.ImageTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.pickle.domain.imagetag.domain.QImageTag.imageTag;

@RequiredArgsConstructor
public class ImageTagRepositoryImpl implements ImageTagRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ImageTag> findByImage(List<Image> images) {
        return queryFactory
                .selectFrom(imageTag)
                .where(imageTag.image.in(images))
                .fetch();
    }
}
