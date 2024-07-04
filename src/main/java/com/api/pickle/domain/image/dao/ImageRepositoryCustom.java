package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.image.domain.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepositoryCustom {
    Optional<List<Image>> findByImageUrls(List<String> imageUrl);
}
