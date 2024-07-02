package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.domain.image.domain.QImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.api.pickle.domain.image.domain.QImage.image;

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
}
