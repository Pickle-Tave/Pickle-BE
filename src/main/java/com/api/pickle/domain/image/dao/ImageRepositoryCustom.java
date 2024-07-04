package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.album.dto.response.FetchAlbumImagesResponse;
import com.api.pickle.domain.image.domain.Image;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ImageRepositoryCustom {
    Optional<List<Image>> findByImageUrls(List<String> imageUrl);

    Slice<FetchAlbumImagesResponse> findAllImagesByCreatedDateDesc(Long albumId, int pageSize, Long lastImageId);
}
