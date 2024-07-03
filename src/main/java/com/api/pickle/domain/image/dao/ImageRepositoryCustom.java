package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.image.domain.Image;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> findByImageUrls(List<String> imageUrl);
}
