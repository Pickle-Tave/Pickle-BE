package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.album.dto.response.FetchAlbumImagesResponse;
import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.domain.member.domain.Member;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> findByImageUrls(List<String> imageUrl);

    Slice<FetchAlbumImagesResponse> findAllImagesByCreatedDateDesc(Long albumId, int pageSize, Long lastImageId);

    List<Image> findByImageAndMember(List<Long> imageIds, Member member);
}
